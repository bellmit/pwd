package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 类描述：ManagerJJUserNameActivity 管理-简介-新名称编辑
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/28 14:12
 * 修改人：
 * 修改时间：2017/11/28 14:12
 * 修改备注：
 *
 * @version 1.0
 */
public class ManagerJJUserNameActivity extends MyAppCompatActivity {

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
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.manager_jjusername_edt)
    EditText managerJjusernameEdt;
    @BindView(R.id.content_manager_jjuser_name)
    RelativeLayout contentManagerJjuserName;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_jjuser_name);
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
            headCenterTitleTv.setText("名称");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }

    private boolean canSave() {
        if (StringUtil.isEmpty(managerJjusernameEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入新名称");
            return false;
        }
        return true;
    }

    private void save() {
        Intent intent = getIntent();
        intent.putExtra("userName", managerJjusernameEdt.getText().toString());
        setResult(Constants.USERNAME_CHANGE_RETURNCODE, intent);
        ManagerJJUserNameActivity.this.finish();
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ManagerJJUserNameActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canSave()) {
                    save();
                }
                break;
        }
    }
}
