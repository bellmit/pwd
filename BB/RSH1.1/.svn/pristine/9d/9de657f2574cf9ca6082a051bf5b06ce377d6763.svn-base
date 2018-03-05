package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.fabu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.BitmapUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageCompress;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：FabuActivity 发布-项目或风采、政策、资讯类
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/4 10:11
 * 修改人：
 * 修改时间：2017/12/4 10:11
 * 修改备注：
 *
 * @version 1.0
 */
public class FabuActivity extends MyAppCompatActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mine_fabu_title_edt)
    EditText mineFabuTitleEdt;
    @BindView(R.id.mine_fabu_img1_iv)
    ImageView mineFabuImg1Iv;
    @BindView(R.id.mine_fabu_content1_edt)
    EditText mineFabuContent1Edt;
    @BindView(R.id.mine_fabu_img2_iv)
    ImageView mineFabuImg2Iv;
    @BindView(R.id.mine_fabu_content2_edt)
    EditText mineFabuContent2Edt;
    @BindView(R.id.mine_fabu_img3_iv)
    ImageView mineFabuImg3Iv;
    @BindView(R.id.mine_fabu_content3_edt)
    EditText mineFabuContent3Edt;
    @BindView(R.id.content_fabu)
    RelativeLayout contentFabu;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine_fabu_rootlayout)
    CoordinatorLayout activityMineFabuRootlayout;

    private ToDetailBean toDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_managerfabu);
        ButterKnife.bind(this);

        if (null != getIntent()) {
            toDetailBean = (ToDetailBean) getIntent().getSerializableExtra("toDetailBean");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setTitle();
    }

    private void setTitle() {
        if (null != headLeftIv) {
            headRightTv.setVisibility(View.VISIBLE);
        }

        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.INVISIBLE);
        }

        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("发布");
        }
    }


    private boolean canFabu() {
        if (StringUtil.isEmpty(mineFabuTitleEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "标题不能为空");
            return false;
        }
        return true;
    }

    /**
     * 发布
     * token 	是 	string 	token值
     * post_title 	是 	string 	标题
     * m_post_text1 	否 	string 	文字1
     * m_post_text2 	否 	string 	文字2
     * m_post_text3 	否 	string 	文字3
     * m_post_image1 	否 	string 	图片1
     * m_post_image2 	否 	string 	图片2
     * m_post_image3 	否 	string 	图片3
     * publish_cate 	是 	int 	0:平台（后台）;1:商会;2:企业;3:机构;4:部门（政府）;5:个人;6:镇街
     * publish_type 	是 	int 	0:平台（后台）;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报
     */
    private void fabu() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_fabu_rootlayout), new CustomLoadingFactory());
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
        builder.add("post_title", mineFabuTitleEdt.getText().toString());
        builder.add("publish_cate", SystemUtil.getInstance().castPulishCate(toDetailBean.getPublishCate()) + "");
        builder.add("publish_type", SystemUtil.getInstance().castPublishType(toDetailBean.getPublishType()) + "");
        if (StringUtil.isNotEmpty(mineFabuContent1Edt.getText().toString())) {
            builder.add("m_post_text1", mineFabuContent1Edt.getText().toString());
        }
        if (StringUtil.isNotEmpty(mineFabuContent2Edt.getText().toString())) {
            builder.add("m_post_text2", mineFabuContent2Edt.getText().toString());
        }
        if (StringUtil.isNotEmpty(mineFabuContent3Edt.getText().toString())) {
            builder.add("m_post_text3", mineFabuContent3Edt.getText().toString());
        }
        if (StringUtil.isNotEmpty(file1Path)) {
            builder.add("m_post_image1", file1Path);
        }
        if (StringUtil.isNotEmpty(file2Path)) {
            builder.add("m_post_image2", file2Path);
        }
        if (StringUtil.isNotEmpty(file3Path)) {
            builder.add("m_post_image3", file3Path);
        }

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
                                msg.obj = "发布成功";
                                myHandler.sendMessage(msg);

                                FabuActivity.this.finish();
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

    /*****************************************上传图片***************************************/
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static String PHOTO_FILE_NAME = "temp_photo.png";//头像名称
    private Uri uriImg;// 获取到了图片的Uri
    private File tempFile;
    private Uri imgUri = null;
    private PopupWindow popupWindow;

    public void getPopupWindow() {
        if (null != popupWindow) {
            //如果popupWindow正在显示，接下来隐藏
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                popupWindow.showAtLocation(this.findViewById(R.id.activity_mine_fabu_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        } else {
            initPopupWindow();
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.person_information_chooseheadportrait, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        TextView person_information_changeheadportrait_photo_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_photo_tv);
        TextView person_information_changeheadportrait_fromalbum_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_fromalbum_tv);
        TextView person_information_changeheadportrait_cancel_tv = (TextView) popupWindow_view.findViewById(R.id.person_information_changeheadportrait_cancel_tv);

        //拍照
        person_information_changeheadportrait_photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                popupWindow.dismiss();
            }
        });

        //从相册中选择
        person_information_changeheadportrait_fromalbum_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                popupWindow.dismiss();
            }
        });

        person_information_changeheadportrait_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                popupWindow = null;
            }
        });


        setBackgroundAlpha(0.3f);
        popupWindow.showAtLocation(this.findViewById(R.id.activity_mine_fabu_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    // 启动手机相机拍摄照片作为头像
    /* 头像文件 */
    private final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    private void choseHeadImageFromCameraCapture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            PHOTO_FILE_NAME = SystemUtil.getInstance().generateImgName();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            System.err.println();
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                uriImg = uri;

                if (whichImgUpload == 1) {
                    PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg1Iv);
                } else if (whichImgUpload == 2) {
                    PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg2Iv);
                } else if (whichImgUpload == 3) {
                    PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg3Iv);
                }

                if (StringUtil.isNotEmpty(uriImg + "")) {
                    uploadHeadPortrait(uriImg);
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    uriImg = Uri.fromFile(tempFile);
                    if (whichImgUpload == 1) {
                        PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg1Iv);
                    } else if (whichImgUpload == 2) {
                        PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg2Iv);
                    } else if (whichImgUpload == 3) {
                        PicassoUtils.getinstance().loadNormalByUri(FabuActivity.this, uriImg, mineFabuImg3Iv);
                    }
                    if (StringUtil.isNotEmpty(uriImg + "")) {

                        try {
                            Uri capturedImage = Uri.parse(
                                    MediaStore.Images.Media.insertImage(
                                            getContentResolver(),
                                            tempFile.getAbsolutePath(), null, null));

                            uploadHeadPortrait(capturedImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    // "未执行拍照操作!");
                    if (whichImgUpload == 1) {
                        mineFabuImg1Iv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                    } else if (whichImgUpload == 2) {
                        mineFabuImg2Iv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                    } else if (whichImgUpload == 3) {
                        mineFabuImg3Iv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                    }
                }
            } else {
                Toast.makeText(FabuActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 上传图片
     */
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String file1Path = "";
    private String file2Path = "";
    private String file3Path = "";

    private void uploadHeadPortrait(Uri imgUri) {
        compressPic(imgUri);
    }

    private void compressPic(Uri uri) {
        ImageCompress compress = new ImageCompress();
        ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
//        options.uri = Uri.fromFile(new File(sourcePath));
        options.uri = uri;
        options.maxWidth = Constants.RESIZEBITMAP_WIDTH;
        options.maxHeight = Constants.RESIZEBITMAP_HEIGHT;
        Bitmap bitmap = compress.compressFromUri(FabuActivity.this, options);
        saveCompressedPic(bitmap, uri);
    }

    private void saveCompressedPic(Bitmap bitmap, Uri uri) {
        boolean isSaved = BitmapUtils.setBitmapToFile(bitmap, Constants.COMPRESSED_PIC_PATH);
        if (isSaved) {
            Uri uri1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "compress.jpg"));
            uploadPic(uri1);
        } else {
            uploadPic(uri);
        }
    }

    private void uploadPic(Uri imgUri) {

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_fabu_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        final String filepath;
        if (imgUri.toString().startsWith("file")) {
            filepath = imgUri.getPath();
        } else {
            filepath = UiHelper.getFilePathFromContentUri(imgUri, getContentResolver());
        }

        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_IMAGEUPLOAD;
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.OKHTTPS_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.OKHTTPS_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.OKHTTPS_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("file", "logo-square.png", RequestBody.create(MEDIA_TYPE_PNG, new File(filepath))).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();

                Message msg = new Message();
                msg.what = INFO_WHAT;
                msg.obj = Constants.UPLOAD_FAIL;
                myHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                String str = response.body().string();
                Log.i(Constants.TAG, str);

                JSONObject jsonObject = JSONObject.parseObject(str);
                if (whichImgUpload == 1) {
                    file1Path = (String) jsonObject.get("data");
                } else if (whichImgUpload == 2) {
                    file2Path = (String) jsonObject.get("data");
                } else if (whichImgUpload == 3) {
                    file3Path = (String) jsonObject.get("data");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = Constants.UPLOAD_SUCCES;
                        myHandler.sendMessage(msg);
                    }
                });
            }

        });
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /*****************************************上传图片***************************************/

    private int whichImgUpload = 1;

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.mine_fabu_img1_iv, R.id.mine_fabu_img2_iv, R.id.mine_fabu_img3_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                FabuActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(FabuActivity.this, headRightTv);
                if (canFabu() && null != toDetailBean) {
                    fabu();
                }
                break;
            case R.id.mine_fabu_img1_iv:
                whichImgUpload = 1;
                getPopupWindow();
                break;
            case R.id.mine_fabu_img2_iv:
                whichImgUpload = 2;
                getPopupWindow();
                break;
            case R.id.mine_fabu_img3_iv:
                whichImgUpload = 3;
                getPopupWindow();
                break;
        }
    }

}
