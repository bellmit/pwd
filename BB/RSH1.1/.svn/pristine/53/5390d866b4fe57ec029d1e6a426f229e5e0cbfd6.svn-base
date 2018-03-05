package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;

/**
 * 注册类型选择：银行、证券、信托。。。
 *
 * @author shenbh
 * @time 2017/11/24 13:55
 */
public class RegisterTypeActivity extends MyBaseActivity {

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
    @BindView(R.id.register_type_bank_cb)
    CheckBox registerTypeBankCb;
    @BindView(R.id.register_type_bank_tv)
    TextView registerTypeBankTv;
    @BindView(R.id.register_type_bank_ll)
    LinearLayout registerTypeBankLl;
    @BindView(R.id.register_type_negotiablesecurities_cb)
    CheckBox registerTypeNegotiablesecuritiesCb;
    @BindView(R.id.register_type_negotiablesecurities_tv)
    TextView registerTypeNegotiablesecuritiesTv;
    @BindView(R.id.register_type_negotiablesecurities_ll)
    LinearLayout registerTypeNegotiablesecuritiesLl;
    @BindView(R.id.register_type_insurance_cb)
    CheckBox registerTypeInsuranceCb;
    @BindView(R.id.register_type_insurance_tv)
    TextView registerTypeInsuranceTv;
    @BindView(R.id.register_type_insurance_ll)
    LinearLayout registerTypeInsuranceLl;
    @BindView(R.id.register_type_trust_cb)
    CheckBox registerTypeTrustCb;
    @BindView(R.id.register_type_trust_tv)
    TextView registerTypeTrustTv;
    @BindView(R.id.register_type_trust_ll)
    LinearLayout registerTypeTrustLl;
    @BindView(R.id.register_type_fund_cb)
    CheckBox registerTypeFundCb;
    @BindView(R.id.register_type_fund_tv)
    TextView registerTypeFundTv;
    @BindView(R.id.register_type_fund_ll)
    LinearLayout registerTypeFundLl;
    @BindView(R.id.activity_register_xxyyqt)
    LinearLayout activityRegisterXxyyqt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);
        ButterKnife.bind(this);

        setTitle();
        resetView();
        registerTypeBankCb.setChecked(true);
        registerTypeBankTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        chosedType = registerTypeBankTv.getText().toString();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setText("类型");

        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确定");
        }
    }

    private String chosedType = "银行";

    private void confirm() {
        Intent intent = getIntent();
        intent.putExtra("getRegisterType", chosedType);
        setResult(Constants.GETREGISTERTYPE_RETURNCODE, intent);
        RegisterTypeActivity.this.finish();
    }

    private void resetView() {
        registerTypeBankCb.setChecked(false);
        registerTypeFundCb.setChecked(false);
        registerTypeInsuranceCb.setChecked(false);
        registerTypeNegotiablesecuritiesCb.setChecked(false);
        registerTypeTrustCb.setChecked(false);
        registerTypeBankTv.setTextColor(getResources().getColor(R.color.font_gray));
        registerTypeNegotiablesecuritiesTv.setTextColor(getResources().getColor(R.color.font_gray));
        registerTypeInsuranceTv.setTextColor(getResources().getColor(R.color.font_gray));
        registerTypeTrustTv.setTextColor(getResources().getColor(R.color.font_gray));
        registerTypeFundTv.setTextColor(getResources().getColor(R.color.font_gray));
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.register_type_bank_ll, R.id.register_type_negotiablesecurities_ll, R.id.register_type_insurance_ll, R.id.register_type_trust_ll, R.id.register_type_fund_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterTypeActivity.this.finish();
                break;
            case R.id.head_right_tv:
                confirm();
                break;
            case R.id.register_type_bank_ll:
                resetView();
                registerTypeBankCb.setChecked(true);
                registerTypeBankTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                chosedType = registerTypeBankTv.getText().toString();
                break;
            case R.id.register_type_negotiablesecurities_ll:
                resetView();
                registerTypeNegotiablesecuritiesCb.setChecked(true);
                registerTypeNegotiablesecuritiesTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                chosedType = registerTypeNegotiablesecuritiesTv.getText().toString();
                break;
            case R.id.register_type_insurance_ll:
                resetView();
                registerTypeInsuranceCb.setChecked(true);
                registerTypeInsuranceTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                chosedType = registerTypeInsuranceTv.getText().toString();
                break;
            case R.id.register_type_trust_ll:
                resetView();
                registerTypeTrustCb.setChecked(true);
                registerTypeTrustTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                chosedType = registerTypeTrustTv.getText().toString();
                break;
            case R.id.register_type_fund_ll:
                resetView();
                registerTypeFundCb.setChecked(true);
                registerTypeFundTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                chosedType = registerTypeFundTv.getText().toString();
                break;
        }
    }
}
