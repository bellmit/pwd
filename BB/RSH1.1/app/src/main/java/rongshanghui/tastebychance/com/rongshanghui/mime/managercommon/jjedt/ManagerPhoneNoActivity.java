package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt;

import android.content.Intent;
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
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.AreaCodeActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 类描述：ManagerPhoneNoActivity 我的-管理-简介管理-电话编辑
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/28 14:28
 * 修改人：
 * 修改时间：2017/11/28 14:28
 * 修改备注：
 *
 * @version 1.0
 */
public class ManagerPhoneNoActivity extends AppCompatActivity {

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
    @BindView(R.id.manager_jj_areacode_tv)
    TextView managerJjAreacodeTv;
    @BindView(R.id.manager_jj_phoneno_edt)
    EditText managerJjPhonenoEdt;
    @BindView(R.id.content_manager_phone_no)
    RelativeLayout contentManagerPhoneNo;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_phone_no);
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

        setTitle();
    }

    private void setTitle() {
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setText("电话");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }

    private boolean canSave() {
        if (StringUtil.isEmpty(managerJjAreacodeTv.getText().toString()) || "区号选择".equals(managerJjAreacodeTv.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请选择一个区号");
            return false;
        }

        if (StringUtil.isEmpty(managerJjPhonenoEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入完整的手机号码");
            return false;
        }

        return true;
    }

    private void save() {
        Intent intent = getIntent();
        intent.putExtra("areaCode", selectedAreaCode);
        intent.putExtra("phoneNo", managerJjPhonenoEdt.getText().toString());
        setResult(Constants.GETAREACODE_PHONENO_RETURNCODE, intent);

        ManagerPhoneNoActivity.this.finish();
    }

    private String selectedAreaCode = "86";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Constants.GETAREACODE_RETURNCODE) {
            if (null != data) {
                AreaCodeInfo selectedAreaData = (AreaCodeInfo) data.getSerializableExtra("selectedAreaData");
                if (managerJjAreacodeTv != null) {
                    managerJjAreacodeTv.setText("+" + selectedAreaData.getCode() + "(" + selectedAreaData.getName() + ")");
                    selectedAreaCode = selectedAreaData.getCode();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.manager_jj_areacode_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ManagerPhoneNoActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canSave()) {
                    save();
                }
                break;
            case R.id.manager_jj_areacode_tv:
                //区号选择
                Intent intent = new Intent(ManagerPhoneNoActivity.this, AreaCodeActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
