package rongshanghui.tastebychance.com.rongshanghui.mime;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.mime.bean.ToRegisterFailReasonBean;
import rongshanghui.tastebychance.com.rongshanghui.register.ModifyRegisterActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.CommomDialog;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;

/**
 * RegisterFailActivity 注册失败展示失败原因的界面
 *
 * @author Administrator-shenbh
 * @time 2018/1/26 21:17
 * @path rongshanghui.tastebychance.com.rongshanghui.mime.RegisterFailActivity.java
 */
public class RegisterFailActivity extends MyBaseActivity {

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
    @BindView(R.id.view)
    View view;
    @BindView(R.id.registerfail_reason_icon_iv)
    ImageView registerfailReasonIconIv;
    @BindView(R.id.registerfail_reason_tcontent_v)
    TextView registerfailReasonTcontentV;
    @BindView(R.id.call_service_tv)
    TextView callServiceTv;

    private ToRegisterFailReasonBean toRegisterFailReasonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fail);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            toRegisterFailReasonBean = (ToRegisterFailReasonBean) getIntent().getSerializableExtra("toRegisterFailReasonBean");
        }
        if (null == toRegisterFailReasonBean) {
            return;
        }
        setTitle();

        initData();
    }

    private void initData() {
        if (registerfailReasonTcontentV != null) {
            registerfailReasonTcontentV.setText(toRegisterFailReasonBean.getRefusal_reason());
        }
        if (null != callServiceTv) {
            callServiceTv.setText("联系客服：" + toRegisterFailReasonBean.getContact());
        }
    }

    private void setTitle() {
        headCenterTitleTv.setText("注册失败");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("重新注册");
    }

    private void call() {
        //弹出框提示是否拨打电话
        new CommomDialog(RegisterFailActivity.this, R.style.dialog, "是否拨打" + SystemUtil.getInstance().getStrFromSP("kfcontact"), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    if (ActivityCompat.checkSelfPermission(RegisterFailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SystemUtil.getInstance().getStrFromSP("kfcontact"))));
                    dialog.dismiss();
                }
            }
        }).setTitle("提示").show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void registerSuc(@NonNull EventRegisterSuc eventRegisterSuc) {
        RegisterFailActivity.this.finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.call_service_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterFailActivity.this.finish();
                break;
            case R.id.head_right_tv:
                Intent intent = new Intent(RegisterFailActivity.this, ModifyRegisterActivity.class);
                intent.putExtra("registerType", toRegisterFailReasonBean.getRegisterType());
                intent.putExtra("from", toRegisterFailReasonBean.getFrom());
                startActivity(intent);
                break;
            case R.id.call_service_tv:
                call();
                break;
        }
    }
}
