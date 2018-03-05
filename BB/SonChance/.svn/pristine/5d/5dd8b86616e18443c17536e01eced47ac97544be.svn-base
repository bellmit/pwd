package com.tastebychance.sonchance.personal.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 修改昵称
 *
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PersonalInformationNameActivity extends MyBaseActivity{
    private TextView person_information_error_tv;
    //修改昵称
    private EditText personal_information_nicename_edt;

    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_name);

        userInfo = (UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo");

        setTitle();
        bindViews();
    }

    private void bindViews() {
        person_information_error_tv = (TextView) findViewById(R.id.person_information_error_tv);
        personal_information_nicename_edt = (EditText) findViewById(R.id.personal_information_nicename_edt);
    }

    private void initData(UserInfo userInfo) {
        if (null != personal_information_nicename_edt){
            personal_information_nicename_edt.setText(userInfo.getUser_nicename());
        }
    }

    /**
     * 上传数据
     */
    private void uploadNewUserName() {
        final String newNiceName = personal_information_nicename_edt.getText().toString();
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =   UrlManager.URL_PERSON_UPDATEUSERINFO;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        //上传昵称
        RequestBody formBody = new FormBody.Builder().add("token", token).add("user_nicename",newNiceName).build();

        //上传完成的手机号进行验证
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);
                    Message msg = new Message();
                    msg.what = ERROR_WHAT;
                    msg.obj = "更新昵称成功";
                    myHandler.sendMessage(msg);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
    //                        SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"userName",newUserName);
                            //TODO:
                            WeakReference<UserInfo> wf;
                            if (null != userInfo){
                                wf = new WeakReference<UserInfo>(userInfo);
                            }else{
                                wf = new WeakReference<UserInfo>(new UserInfo());
                            }
                            wf.get().setUser_nicename(newNiceName);
                            SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",wf.get());
                            PersonalInformationNameActivity.this.finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置标题
     */
//    private TextView center_tv,right_tv;
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
        if (center_tv != null){
            center_tv.setText("昵称设置");
        }

        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("保存");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtil.isEmpty(personal_information_nicename_edt.getText().toString())){
                        UiHelper.ShowOneToast(getApplicationContext(),"昵称不能为空");
                        return;
                    }
                    String niceName = "";
                    if (null != userInfo){
                        niceName = userInfo.getUser_nicename();
                    }
                    if (personal_information_nicename_edt.getText().toString().equals(StringUtil.noNull(niceName))){
                        UiHelper.ShowOneToast(getApplicationContext(),"昵称没有变化,不需要提交!");
                        return;
                    }
                    uploadNewUserName();
                }
            });
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //TODO:如果修改过，未保存，提示用户保存
//                    UiHelper.showCustomConfirmCancelDialog(PersonalInformationActivity.this,null,"")
                    PersonalInformationNameActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != userInfo){
            initData(userInfo);
        }
    }
}
