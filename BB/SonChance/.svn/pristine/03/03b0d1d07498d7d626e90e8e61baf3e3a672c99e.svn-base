package com.tastebychance.sonchance.personal.personalcenter;

import android.content.Intent;
import android.os.Bundle;
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
import com.tastebychance.sonchance.util.UiHelper;

/**
 * 修改手机号信息
 *
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PersonalInformationPhoneNoActivity extends MyBaseActivity{
    private TextView person_information_error_tv;
    //请输入当前绑定的完整的手机号
    private TextView personal_information_tv;
    private EditText person_information_inputfullphoneno_edt;

    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_phoneno);

        userInfo = (UserInfo) SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(),"userInfo");

        setTitle();
        bindViews();
    }

    private void bindViews() {
        person_information_error_tv = (TextView) findViewById(R.id.person_information_error_tv);
        personal_information_tv = (TextView) findViewById(R.id.personal_information_tv);
        person_information_inputfullphoneno_edt = (EditText) findViewById(R.id.person_information_inputfullphoneno_edt);
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
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    PersonalInformationPhoneNoActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }

        if (center_tv != null){
            center_tv.setText("手机绑定");
        }

        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("下一步");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fullPhoneNo = person_information_inputfullphoneno_edt.getText().toString();
                    if (StringUtil.isEmpty(fullPhoneNo)){
                        UiHelper.ShowOneToast(getApplicationContext(),"请输入完整的手机号码");
                        return;
                    }
                    if (!StringUtil.isMobileNO(fullPhoneNo)){
                        UiHelper.ShowOneToast(getApplicationContext(),"手机号码格式不正确");
                        return;
                    }

                    //本地校验输入的手机号码是否跟已经保存的用户信息中的手机号码一致
                    if (null != userInfo){
                        if (userInfo.getMobile().equals(fullPhoneNo)){
                            //本地校验通过，跳转到输入新号码的界面
                            Intent intent = new Intent(PersonalInformationPhoneNoActivity.this,PersonalInformationNewPhoneNoActivity.class);
                            startActivityForResult(intent,Constants.BIND_MOBILE_SUCCESS);
                        }else{
                            UiHelper.ShowOneToast(getApplicationContext(),"您输入的手机号不正确，请重新输入!");
                        }
                    }else{
                        UiHelper.ShowOneToast(getApplicationContext(),"本地保存的用户信息已失效,请重登!");
                    }

                }
            });
        }
    }

    private void initData(UserInfo userInfo) {
        if (null != personal_information_tv){
            personal_information_tv.setText("当前绑定 "+StringUtil.secrecyPwd(userInfo.getMobile()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != userInfo){
            initData(userInfo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.BIND_MOBILE_SUCCESS){
           PersonalInformationPhoneNoActivity.this.finish();
        }
    }
}
