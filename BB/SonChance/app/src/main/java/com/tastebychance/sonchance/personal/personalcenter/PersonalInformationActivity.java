package com.tastebychance.sonchance.personal.personalcenter;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.TabHostMainActivity;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.PicassoUtils;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.RoundImageView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 个人中心
 * Created by shenbh on 2017/8/15.
 */

public class PersonalInformationActivity extends MyBaseActivity {

    private RoundImageView person_information_headportrait_riv;
    private TextView person_information_username_tv;
    private RadioGroup person_information_sex_rg;
    private RadioButton person_information_sex_man_rbt;
    private RadioButton person_information_sex_lady_rbt;
    private TextView person_information_tel_tv;
//    private TextView person_information_email_tv;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final String PHOTO_FILE_NAME = "temp_photo.png";//头像名称
    private Uri uriImg;// 获取到了图片的Uri
    private File tempFile;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.person_information);
        bindViews();
        setTitle();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    private void bindViews() {
        person_information_headportrait_riv = (RoundImageView) findViewById(R.id.person_information_headportrait_riv);
        person_information_username_tv = (TextView) findViewById(R.id.person_information_username_tv);
        person_information_sex_rg = (RadioGroup) findViewById(R.id.person_information_sex_rg);
        person_information_sex_man_rbt = (RadioButton) findViewById(R.id.person_information_sex_man_rbt);
        person_information_sex_lady_rbt = (RadioButton) findViewById(R.id.person_information_sex_lady_rbt);
        person_information_tel_tv = (TextView) findViewById(R.id.person_information_tel_tv);
//        person_information_email_tv = (TextView) findViewById(R.id.person_information_email_tv);
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("账户设置");

        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    PersonalInformationActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("保存");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPersonalData();
                }
            });
        }
    }

    /**
     * 头像点击
     *
     * @param view
     */
    private PopupWindow popupWindow;

    public void headPortraitClick(View view) {
        getPopupWindow();
    }

    public void getPopupWindow() {
        if (null != popupWindow) {
            //如果popupWindow正在显示，接下来隐藏
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                popupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        popupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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


    /**
     * 昵称
     *
     * @param view
     */
    public void userNameClick(View view) {
        Intent intent = new Intent(PersonalInformationActivity.this,PersonalInformationNameActivity.class);
        startActivity(intent);
    }

    /**
     * 电话
     *
     * @param view
     */
    public void telClick(View view) {
        Intent intent = new Intent(PersonalInformationActivity.this,PersonalInformationPhoneNoActivity.class);
        startActivity(intent);
    }

    /**
     * 邮箱
     *
     * @param view
     */
    public void emailClick(View view) {
       /* Intent intent = new Intent(PersonalInformationActivity.this,PersonalInformationPhoneNoActivity.class);
//        intent.putExtra("widgetFlag","email");
        if (null != userInfo){
            intent.putExtra("userInfo",userInfo);
        }
        startActivity(intent);*/
    }

    /**
     * 退出登录
     * @param view
     */
    public void logoutClick(View view){
        SystemUtil.getInstance().clearData();
        //切到首页
        startActivity(new Intent(PersonalInformationActivity.this, TabHostMainActivity.class));
        //通知TabHost切换到首页
        Intent intent = new Intent(Constants.CHANGETOHOME_ACTION);
        sendBroadcast(intent);
        PersonalInformationActivity.this.finish();
    }

    private Uri imgUri = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                uriImg = uri;

              /*  Picasso.with(PersonalInformationActivity.this)
                        .load(uriImg)
                        .resize(48,48)
                        .centerCrop()
                        .into(person_information_headportrait_riv);*/

                PicassoUtils.getinstance().loadCircleImageByUri(PersonalInformationActivity.this,uriImg, person_information_headportrait_riv,24);

                if (StringUtil.isNotEmpty(uriImg +"")){
                    uploadHeadPortrait(uriImg);
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    uriImg = Uri.fromFile(tempFile);

/*                    Picasso.with(PersonalInformationActivity.this)
                            .load(uriImg)
                            .resize(48,48)
                            .centerCrop()
                            .into(person_information_headportrait_riv);*/

                    PicassoUtils.getinstance().loadCircleImageByUri(PersonalInformationActivity.this,uriImg, person_information_headportrait_riv,24);

                    if (StringUtil.isNotEmpty(uriImg +"")){

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
                    person_information_headportrait_riv.setImageDrawable(getResources().getDrawable(R.drawable.person_defaultheadportrait));
                }
            } else {
                Toast.makeText(PersonalInformationActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PersonalInformation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    private void initData(UserInfo userInfo){
        if (null != person_information_headportrait_riv){
//            ImageDownLoad.getDownLoadCircleImg(userInfo.getAvatar(),person_information_headportrait_riv);

            String imgUrl;
            if (userInfo != null && userInfo.getAvatar() != null && userInfo.getAvatar().contains("http:") || userInfo.getAvatar().contains("https")){
                imgUrl = userInfo.getAvatar();
            }else{
                imgUrl = (Constants.IS_DEVELOPING? UrlManager.REQUESTURL_HEAD_TEST : UrlManager.REQUESTIMGURL) + userInfo.getAvatar();
            }

           /* Picasso.with(PersonalInformationActivity.this)
                    .load(imgUrl)
                    .resize(48,48)
                    .centerCrop()
                    .into(person_information_headportrait_riv);*/
            PicassoUtils.getinstance().loadCircleImage(PersonalInformationActivity.this,imgUrl,person_information_headportrait_riv, 24);
        }
        if (null != person_information_username_tv){
            person_information_username_tv.setText(userInfo.getUser_nicename());
        }
        if (null != person_information_sex_rg){
            if (userInfo.getSex() == 0){
                person_information_sex_rg.check(R.id.person_information_sex_man_rbt);
            }else if(userInfo.getSex() == 1){
                person_information_sex_rg.check(R.id.person_information_sex_lady_rbt);
            }
        }
        if (null != person_information_tel_tv){
            person_information_tel_tv.setText(StringUtil.secrecyPwd(userInfo.getMobile()));
        }
    }

    /**
     * 上传头像
     */
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String filePath = "";
    private void uploadHeadPortrait(Uri imgUri) {

        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        final String filepath;
        if (imgUri.toString().startsWith("file")){
            filepath = imgUri.getPath();
        }else{
            filepath = UiHelper.getFilePathFromContentUri(imgUri, getContentResolver());
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PERSON_UPLOAD;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title","Square Logo")
                .addFormDataPart("image","logo-square.png",RequestBody.create(MEDIA_TYPE_PNG,new File(filepath))).build();
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
                String str = response.body().string();
                Log.i(Constants.TAG, str);

                JSONObject jsonObject = JSONObject.parseObject(str);

                filePath = (String) jsonObject.get("preview_url");

                String url = (String)jsonObject.get("url");
                if (null != url){
                    url = url.replaceAll("/upload","").replaceAll("/data","");
                    if (null != userInfo){
                        WeakReference<UserInfo> wf = new WeakReference<UserInfo>(userInfo);
                        wf.get().setAvatar(url);
                        SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",wf.get());
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != person_information_headportrait_riv){
                            initData((UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo"));
                        }
                    }
                });
            }

        });
    }

    /**
     * 上传个人资料
     */
    private void uploadPersonalData() {
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PERSON_UPDATEUSERINFO;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        //上传性别
        final String sex = ((RadioButton) findViewById(person_information_sex_rg.getCheckedRadioButtonId())).getText().toString().equals("先生")?"0":"1";
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("user_nicename",person_information_username_tv.getText().toString())
                .add("avatar",filePath)
                .add("sex", sex).build();

        //上传完成的手机号进行验证
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

                    ResInfo resInfo = JSONObject.parseObject(str.toString(),ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
    //                        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"userName",newUserName);
                                WeakReference<UserInfo> wf;
                                if (null != userInfo){
                                    wf = new WeakReference<UserInfo>(userInfo);
                                }else{
                                    wf = new WeakReference<UserInfo>(new UserInfo());
                                }
                                wf.get().setSex(Integer.valueOf(sex));
                                SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",wf.get());
                                PersonalInformationActivity.this.finish();
                            }
                        });
                    }else{
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());

        userInfo = (UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo");
        if (null != userInfo){
            initData(userInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
