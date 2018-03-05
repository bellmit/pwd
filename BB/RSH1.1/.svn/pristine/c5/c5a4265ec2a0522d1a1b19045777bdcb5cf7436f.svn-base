package rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragmentActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.fabu.FabuActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.richeditor.RichTextEditor;
import rongshanghui.tastebychance.com.rongshanghui.view.richeditor.RichTextEditor.EditData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 富文本编辑
 *
 * @author shenbh
 * @time 2018/1/24 20:09
 */
@SuppressLint("SimpleDateFormat")
public class RichEditActivity extends MyBaseFragmentActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private RichTextEditor editor;
    private View btn1, btn2, btn3;
    private OnClickListener btnListener;
    private EditText titleEdt;

    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片

    private ToDetailBean toDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_richedit);

        if (getIntent() != null) {
            toDetailBean = (ToDetailBean) getIntent().getSerializableExtra("toDetailBean");
        }
        editor = (RichTextEditor) findViewById(R.id.richEditor);
        btnListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.hideKeyBoard();
                if (v.getId() == btn1.getId()) {
                    // 打开系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");// 相片类型
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                } else if (v.getId() == btn2.getId()) {
                    // 打开相机
                    openCamera();
                } else if (v.getId() == btn3.getId()) {
                    List<EditData> editList = editor.buildEditData();
                    // 下面的代码可以上传、或者保存，请自行实现
                    dealEditData(editList);
                }
            }
        };

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        btn1.setOnClickListener(btnListener);
        btn2.setOnClickListener(btnListener);
        btn3.setOnClickListener(btnListener);

        titleEdt = (EditText) findViewById(R.id.title_edt);
        initTitle();
    }

    private void initTitle() {
        ImageView headLeftIv = (ImageView) findViewById(R.id.head_left_iv);
        TextView headTitleTv = (TextView) findViewById(R.id.head_center_title_tv);
        final TextView headRightTv = (TextView) findViewById(R.id.head_right_tv);
        headTitleTv.setVisibility(View.VISIBLE);
        headTitleTv.setText("征求发布");

        headLeftIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                RichEditActivity.this.finish();
            }
        });

        headRightTv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (TextUtils.isEmpty(titleEdt.getText().toString())) {
                    ToastUtils.showOneToast(RichEditActivity.this, "请输入标题");
                    return;
                }

                List<EditData> editList = editor.buildEditData();
                if (editList == null || editList.size() <= 0) {
                    ToastUtils.showOneToast(RichEditActivity.this, "请输入内容");
                    return;
                }
                SoftInputUtil.hideSoftInput(RichEditActivity.this, headRightTv);
                //下面代码可以上传、或者保存，可自行实现
                dealEditData(editList);
            }
        });

        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("发布");
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    protected void dealEditData(List<EditData> editList) {
        for (EditData itemData : editList) {
            if (itemData.inputStr != null) {
                Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);

                commit(itemData.inputStr);
            } else if (itemData.imagePath != null) {
                Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
            }

        }
    }

    private void commit(String content) {
        if (null == toDetailBean) {
            return;
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.richedit_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_ARTICLEPOST;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("post_title", titleEdt.getText().toString());
        builder.add("publish_cate", SystemUtil.getInstance().castPulishCate(toDetailBean.getPublishCate()) + "");
        builder.add("publish_type", SystemUtil.getInstance().castPublishType(toDetailBean.getPublishType()) + "");
        if (StringUtil.isNotEmpty(content)) {
            builder.add("m_post_text1", content);
        }
        /*if (StringUtil.isNotEmpty(file1Path)) {
            builder.add("m_post_image1", file1Path);
        }*/

        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
        }

        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = Constants.COMMIT_SUCCES;
                                myHandler.sendMessage(msg);

                                RichEditActivity.this.finish();
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    protected void openCamera() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            insertBitmap(getRealFilePath(uri));
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            insertBitmap(mCurrentPhotoFile.getAbsolutePath());
        }
    }

    /**
     * 添加图片到富文本剪辑器
     *
     * @param imagePath
     */
    private void insertBitmap(String imagePath) {
        editor.insertImage(imagePath);
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
