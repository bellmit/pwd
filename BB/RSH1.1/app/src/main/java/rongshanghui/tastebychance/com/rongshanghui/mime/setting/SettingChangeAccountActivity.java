package rongshanghui.tastebychance.com.rongshanghui.mime.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.login.YWLoginState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;

/**
 * 类描述：SettingChangeAccountActivity 我的-设置-变更账户
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/8 18:20
 * 修改人：
 * 修改时间：2017/12/8 18:20
 * 修改备注：
 *
 * @version 1.0
 */
public class SettingChangeAccountActivity extends MyAppCompatActivity {

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
    @BindView(R.id.setting_changeaccount_phone_tv)
    TextView settingChangeaccountPhoneTv;
    @BindView(R.id.setting_changeaccount_change_tv)
    TextView settingChangeaccountChangeTv;
    @BindView(R.id.content_setting_change_account)
    RelativeLayout contentSettingChangeAccount;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_account);
        ButterKnife.bind(this);
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

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.CHANGEACCOUNT_ACTION);

        setTitle();

        initView();
    }

    private void initView() {
        settingChangeaccountPhoneTv.setText(StringUtil.secrecyPwd(SystemUtil.getInstance().getUserInfo().getMobile()));
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("设置");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("退出登录");
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SettingChangeAccountActivity.this.finish();
        }
    };

    public void logout() {
        // openIM SDK提供的登录服务
        IYWLoginService mLoginService = LoginSampleHelper.getInstance().getIMKit().getLoginService();
        mLoginService.logout(new IWxCallback() {
            //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
            @Override
            public void onSuccess(Object... arg0) {
                YWLog.i(Constants.TAG, "退出成功");
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {
                YWLog.i(Constants.TAG, "退出失败,请重新登录");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (null != broadcastReceiver) {
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != broadcastReceiver) {
            unregisterReceiver(broadcastReceiver);
        }
    }


    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.setting_changeaccount_change_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                SettingChangeAccountActivity.this.finish();
                break;
            case R.id.head_right_tv:
                //退出登录
                logout();
                SystemUtil.getInstance().clearData();

                Intent intent = new Intent();
                intent.setAction(Constants.LOGOUT_ACTION);
                sendBroadcast(intent);
                SettingChangeAccountActivity.this.finish();
                break;
            case R.id.setting_changeaccount_change_tv:
                startActivity(new Intent(SettingChangeAccountActivity.this, SettingPhoneValidatePhoneNoActivity.class));
                break;
        }
    }
}
