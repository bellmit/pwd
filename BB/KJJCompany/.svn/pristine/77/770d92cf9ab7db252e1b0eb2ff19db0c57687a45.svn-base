package company.webdemo.agile.com.technologycompany.register;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import company.webdemo.agile.com.technologycompany.MyBaseActivity;
import company.webdemo.agile.com.technologycompany.R;

public class RegisterActivity extends MyBaseActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_left_tv)
    TextView headLeftTv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.register_account_edt)
    EditText registerAccountEdt;
    @BindView(R.id.register_passwd_edt)
    EditText registerPasswdEdt;
    @BindView(R.id.register_confirmpasswd_edt)
    EditText registerConfirmpasswdEdt;
    @BindView(R.id.register_enterprisename_edt)
    EditText registerEnterprisenameEdt;
    @BindView(R.id.register_time_rl)
    RelativeLayout registerTimeRl;
    @BindView(R.id.register_industry_rl)
    RelativeLayout registerIndustryRl;
    @BindView(R.id.register_upload_tv)
    TextView registerUploadTv;
    @BindView(R.id.register_upload_iv)
    ImageView registerUploadIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.register_time_rl, R.id.register_industry_rl, R.id.register_upload_tv, R.id.register_upload_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_time_rl:
                break;
            case R.id.register_industry_rl:
                break;
            case R.id.register_upload_tv:
            case R.id.register_upload_iv:

                break;
        }
    }
}
