package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseTransparentThemeActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;

/**
 * 类描述：RegisterEntranceTypeActivity 注册入口类型选择界面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/23 17:44
 * 修改人：
 * 修改时间：2017/11/23 17:44
 * 修改备注：
 *
 * @version 1.0
 */
public class RegisterEntranceTypeActivity extends MyBaseTransparentThemeActivity {


    @BindView(R.id.mine_myfollow_underline)
    View mineMyfollowUnderline;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.registertype_sh_tv)
    TextView registertypeShTv;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.REGISTERENTRANCETYPE_JG_tv)
    TextView REGISTERENTRANCETYPEJGTv;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.registertype_qy_tv)
    TextView registertypeQyTv;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.REGISTERENTRANCETYPE_BMZJ_tv)
    TextView REGISTERENTRANCETYPEBMZJTv;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.registertype_yy_tv)
    TextView registertypeYyTv;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.registertype_xx_tv)
    TextView registertypeXxTv;
    @BindView(R.id.view7)
    View view7;
    @BindView(R.id.registertype_qt_tv)
    TextView registertypeQtTv;
    @BindView(R.id.view8)
    View view8;

    //    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_entrancetype);
        ButterKnife.bind(this);

   /*     intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.REGISTER_SUCCESS_ACTION);*/

        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.head_left_iv, R.id.registertype_sh_tv, R.id.REGISTERENTRANCETYPE_JG_tv, R.id.registertype_qy_tv, R.id.registertype_yy_tv, R.id.registertype_xx_tv, R.id.registertype_qt_tv})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterEntranceTypeActivity.this.finish();
                break;
            case R.id.REGISTERENTRANCETYPE_JG_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_JG);
                break;
            case R.id.registertype_sh_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_SH);
                break;
            case R.id.registertype_qy_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_QY);
                break;
            case R.id.registertype_yy_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_YY);
                break;
            case R.id.registertype_xx_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_XX);
                break;
            case R.id.registertype_qt_tv:
                intentToRegister(Constants.REGISTERENTRANCETYPE_QT);
                break;
        }
    }

    private void intentToRegister(String registerType) {
        Intent intent = new Intent(RegisterEntranceTypeActivity.this, RegisterValidatePhoneNoActivity.class);
        intent.putExtra("registerType", registerType);
        startActivity(intent);
    }


    /*BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RegisterEntranceTypeActivity.this.finish();
        }
    };*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThisActivity(@NonNull EventRegisterSuc eventRegisterSuc) {
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if (null != broadcastReceiver){
            registerReceiver(broadcastReceiver, intentFilter);
        }*/
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (null != broadcastReceiver){
            unregisterReceiver(broadcastReceiver);
        }*/

        EventBusUtils.unregister(this);
    }

    @OnClick(R.id.REGISTERENTRANCETYPE_BMZJ_tv)
    public void onViewClicked() {
        startActivity(new Intent(RegisterEntranceTypeActivity.this, RegisterBMZJEntranceActivity.class));
    }

}
