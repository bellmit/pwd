package company.webdemo.agile.com.technologycompany.personal;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import company.webdemo.agile.com.technologycompany.R;
import company.webdemo.agile.com.technologycompany.login.LoginActivity;
import company.webdemo.agile.com.technologycompany.personal.changepwd.ChangePWDActivity;
import company.webdemo.agile.com.technologycompany.util.CommomDialog;
import company.webdemo.agile.com.technologycompany.util.StringUtil;
import company.webdemo.agile.com.technologycompany.view.PengTextView;
import company.webdemo.agile.com.technologycompany.view.RoundImageView;
/**
 * 类描述：PersonalActivity 个人中心 
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/4 15:29
 * 修改人：
 * 修改时间：2017/11/4 15:29
 * 修改备注：
 * @version 1.0
 */
public class PersonalActivity extends AppCompatActivity {

    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.person_changeheadportrait_ptv)
    PengTextView personChangeheadportraitPtv;
    @BindView(R.id.person_enterpriseinfo_ptv)
    PengTextView personEnterpriseinfoPtv;
    @BindView(R.id.person_changepasswd_ptv)
    PengTextView personChangepasswdPtv;
    @BindView(R.id.person_filecache_ptv)
    PengTextView personFilecachePtv;
    @BindView(R.id.person_interactiveinfo_ptv)
    PengTextView personInteractiveinfoPtv;
    @BindView(R.id.person_subaccount_ptv)
    PengTextView personSubaccountPtv;
    @BindView(R.id.person_businessschedule_ptv)
    PengTextView personBusinessschedulePtv;
    @BindView(R.id.person_logout_ptv)
    PengTextView personLogoutPtv;
    @BindView(R.id.person_headportrait_riv)
    RoundImageView personHeadportraitRiv;
    @BindView(R.id.person_login_tv)
    TextView personLoginTv;
    @BindView(R.id.person_register_tv)
    TextView personRegisterTv;
    @BindView(R.id.person_loginstatus_tv)
    TextView personLoginstatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
    }

    private void call(){
        String phoneStr = phoneTv.getText().toString().replace("科技局电话:","");
        if (StringUtil.isNotEmpty(phoneStr)) {
            //弹出框提示是否拨打电话
            new CommomDialog(PersonalActivity.this,R.style.dialog, "是否拨打" + phoneStr + "?",new CommomDialog.OnCloseListener(){
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm){
                        //TODO:拨打电话
//                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr)));
                        dialog.dismiss();
                    }
                }
            }).setTitle("提示").show();
        } else {
           /* Message msg = new Message();
            msg.what = ERROR_WHAT;
            msg.obj = "商家未留电话,请走意见反馈渠道!";
            myHandler.sendMessage(msg);*/
        }
    }

    @OnClick({R.id.phone_iv, R.id.phone_tv, R.id.person_changeheadportrait_ptv, R.id.person_enterpriseinfo_ptv, R.id.person_changepasswd_ptv, R.id.person_filecache_ptv, R.id.person_interactiveinfo_ptv, R.id.person_subaccount_ptv, R.id.person_businessschedule_ptv, R.id.person_logout_ptv, R.id.person_headportrait_riv, R.id.person_login_tv, R.id.person_register_tv, R.id.person_loginstatus_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_iv:
            case R.id.phone_tv:
                call();
                break;
            case R.id.person_changeheadportrait_ptv:
                break;
            case R.id.person_enterpriseinfo_ptv:
                break;
            case R.id.person_changepasswd_ptv:
                startActivity(new Intent(PersonalActivity.this, ChangePWDActivity.class));
                break;
            case R.id.person_filecache_ptv:
                break;
            case R.id.person_interactiveinfo_ptv:
                break;
            case R.id.person_subaccount_ptv:
                break;
            case R.id.person_businessschedule_ptv:
                break;
            case R.id.person_logout_ptv:
                break;
            case R.id.person_headportrait_riv:
                break;
            case R.id.person_login_tv:
                startActivity(new Intent(PersonalActivity.this, LoginActivity.class));
                break;
            case R.id.person_register_tv:
                break;
            case R.id.person_loginstatus_tv:
                break;
        }
    }
}
