package rongshanghui.tastebychance.com.rongshanghui.mime.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 类描述：SettingPhoneValidatePhoneNoActivity 我的-设置-手机绑定-验证当前手机号
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/8 18:37
 * 修改人：
 * 修改时间：2017/12/8 18:37
 * 修改备注：
 *
 * @version 1.0
 */
public class SettingPhoneValidatePhoneNoActivity extends MyAppCompatActivity {

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
    @BindView(R.id.setting_phonebind_currentphone_tv)
    TextView settingPhonebindCurrentphoneTv;
    @BindView(R.id.setting_changeaccount_phone_edt)
    EditText settingChangeaccountPhoneEdt;
    @BindView(R.id.content_setting_phone_validate_phone_no)
    RelativeLayout contentSettingPhoneValidatePhoneNo;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_phone_validate_phone_no);
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
        settingPhonebindCurrentphoneTv.setText("当前绑定：" + StringUtil.secrecyPwd(SystemUtil.getInstance().getUserInfo().getMobile()));
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("手机绑定");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("下一步");
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SettingPhoneValidatePhoneNoActivity.this.finish();
        }
    };

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

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                SettingPhoneValidatePhoneNoActivity.this.finish();
                break;
            case R.id.head_right_tv:
                String fullPhoneNo = settingChangeaccountPhoneEdt.getText().toString();
                if (StringUtil.isEmpty(fullPhoneNo)) {
                    ToastUtils.showOneToast(getApplicationContext(), "请输入完整的手机号码");
                    return;
                }

                //本地校验输入的手机号码是否跟已经保存的用户信息中的手机号码一致
                if (null != SystemUtil.getInstance().getUserInfo()) {
                    if (SystemUtil.getInstance().getUserInfo().getMobile().equals(fullPhoneNo)) {
                        //本地校验通过，跳转到输入新号码的界面
                        startActivity(new Intent(SettingPhoneValidatePhoneNoActivity.this, SettingPhoneBindActivity.class));
                    } else {
                        ToastUtils.showOneToast(getApplicationContext(), "您输入的手机号不正确，请重新输入!");
                    }
                } else {
                    ToastUtils.showOneToast(getApplicationContext(), "本地保存的用户信息已失效,请重登!");
                }

                break;
        }
    }
}
