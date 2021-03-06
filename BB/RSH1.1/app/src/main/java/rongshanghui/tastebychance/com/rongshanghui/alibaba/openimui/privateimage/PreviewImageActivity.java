package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.privateimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWFileManager;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.utility.IMNotificationUtils;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.Constant;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.ChattingOperationCustomSample;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;

import com.alibaba.wxlib.thread.WXThreadPoolMgr;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PreviewImageActivity extends Activity {

    private static final String TAG = "PreviewImageActivity";
    public static final String MESSAGE = "message";
    private ImageView mImage;
    private ImageView mDefaultImage;
    private ImageView mFailImage;
    private String mImageUrl;
    private YWMessage mMessage;
    private YWIMKit mIMKit;
    private Bitmap mBitmap;

    private static int mScreenWidth = 0;
    private static int mScreenHeight = 0;
    private static final int MAX_SIZE = 4096;

    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        mIMKit = LoginSampleHelper.getInstance().getIMKit();

        Intent intent = getIntent();
        mMessage = (YWMessage) intent.getSerializableExtra(MESSAGE);
        if (mMessage != null) {
            mImageUrl = getImageUrl(mMessage);
        }

        initView();
        initImageView();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image_detail_view);
        mDefaultImage = (ImageView) findViewById(R.id.image_detail_default_view);
        mFailImage = (ImageView) findViewById(R.id.image_detail_download_fail_view);

        mImage.setVisibility(View.GONE);
        mFailImage.setVisibility(View.GONE);
        mDefaultImage.setVisibility(View.VISIBLE);

        initScreenWidthAndHeight();
        mImage.setMaxWidth(mScreenWidth);
        mImage.setMaxHeight(mScreenHeight);
    }

    private void initImageView() {
        if (TextUtils.isEmpty(mImageUrl)) {
            return;
        }
        YWFileManager fileManager = LoginSampleHelper.getInstance().getIMKit().getIMCore().getFileManager();
        final String name = mImageUrl.substring(mImageUrl.lastIndexOf("/") + 1);
        final String filePath = Constant.STORE_PATH + name;
        File file = new File(filePath);
        //?????????????????????????????????????????????????????????????????????????????????
        if (mMessage.getMsgReadStatus() == YWMessage.MSG_READED_STATUS || file.exists()) {
            showImage(filePath);
        } else { //?????????????????????????????????????????????????????????????????????
            fileManager.downloadFile(mImageUrl, Constant.STORE_PATH, name, new IWxCallback() {
                @Override
                public void onSuccess(Object... result) {
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showImage(filePath);
                        }
                    });
                }

                @Override
                public void onError(int code, String info) {
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mImage.setVisibility(View.GONE);
                            mDefaultImage.setVisibility(View.GONE);
                            mFailImage.setVisibility(View.VISIBLE);
                        }
                    });
                    WxLog.e(TAG, "download error, code = " + code + ", info = " + info);
                    IMNotificationUtils.getInstance().showToast(PreviewImageActivity.this, "???????????????????????????????????????");
                }

                @Override
                public void onProgress(int progress) {

                }
            });
        }
    }

    private void initScreenWidthAndHeight() {
        if (mScreenWidth == 0 || mScreenHeight == 0) {
            WindowManager manager = getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            mScreenWidth = outMetrics.widthPixels;
            mScreenHeight = outMetrics.heightPixels;
        }
    }

    private void showImage(final String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = findBestSampleSize(options.outWidth, options.outHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmap = BitmapFactory.decodeFile(filePath, options);
        mImage.setImageBitmap(mBitmap);
        mImage.setVisibility(View.VISIBLE);
        mDefaultImage.setVisibility(View.GONE);

        //5????????????????????????????????????????????????????????????????????????????????????
        IMNotificationUtils.getInstance().showToast(R.string.aliwx_message_will_destroy, this);
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                WXThreadPoolMgr.getInstance().doAsyncRun(new Runnable() {
                    @Override
                    public void run() {
                        YWFileManager manager = mIMKit.getIMCore().getFileManager();
                        File file = new File(filePath);
                        manager.deleteFile(file);
                    }
                });
            }
        }, 5000);

        //?????????????????????????????????????????????
        mMessage.getMessageBody().setExtraData(YWMessage.MSG_READED_STATUS);
        YWConversation conversation = mIMKit.getConversationService().getConversationByConversationId(mMessage.getConversationId());
        if (conversation != null) {
            conversation.updateCustomMessageExtraData(conversation, mMessage);
        }

        //??????????????????????????????????????????????????????
        if (!mIMKit.getIMCore().getLoginUserId().equals(mMessage.getAuthorUserId())) {
            sendPrivateImageMsgReadStatus(conversation);
        }
    }

    /**
     * ????????????????????????????????????url
     *
     * @param message
     * @return
     */
    private String getImageUrl(YWMessage message) {
        String url = null;
        if (message.getMessageBody() instanceof YWCustomMessageBody) {
            try {
                String content = message.getMessageBody().getContent();
                JSONObject object = new JSONObject(content);
                url = object.getString("url");
            } catch (Exception e) {

            }
            return url;
        }
        return url;
    }

    private void sendPrivateImageMsgReadStatus(YWConversation conversation) {
        JSONObject object = new JSONObject();
        try {
            object.put("customizeMessageType", ChattingOperationCustomSample.CustomMessageType.READ_STATUS);
            object.put("PrivateImageRecvReadMessageId", String.valueOf(mMessage.getMsgId()));
        } catch (JSONException e) {
        }
        YWCustomMessageBody body = new YWCustomMessageBody();
        body.setContent(object.toString());
        body.setSummary("????????????????????????");
        body.setTransparentFlag(1);
        YWMessage message = YWMessageChannel.createCustomMessage(body);
        if (conversation != null) {
            conversation.getMessageSender().sendMessage(message, 120, null);
        }
    }

    private int findBestSampleSize(int actualWidth, int actualHeight) {
        double ratio = 0.0;
        double wr = (double) actualWidth / mScreenWidth;
        double hr = (double) actualHeight / mScreenHeight;
        if (actualHeight >= MAX_SIZE || actualWidth >= MAX_SIZE) {
            ratio = Math.max(wr, hr);
        } else {
            ratio = Math.min(wr, hr);
        }
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }

    @Override
    protected void onDestroy() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        super.onDestroy();
    }
}
