package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.changepwd;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

/**
 * 类描述：ChangePwdActivity 更改密码
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/4 13:38
 * 修改人：
 * 修改时间：2017/12/4 13:38
 * 修改备注：
 *
 * @version 1.0
 */
public class ChangePwdActivity extends MyAppCompatActivity {

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
    @BindView(R.id.mine_changepwd_account_tv)
    TextView mineChangepwdAccountTv;
    @BindView(R.id.mine_changepwd_pwd_edt)
    EditText mineChangepwdPwdEdt;
    @BindView(R.id.mine_changepwd_pwdconfirm_edt)
    EditText mineChangepwdPwdconfirmEdt;
    @BindView(R.id.content_change_pwd)
    RelativeLayout contentChangePwd;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine_changepwd_rootlayout)
    CoordinatorLayout activityMineChangepwdRootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_change_pwd);
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
        if (null != headLeftIv) {
            headRightTv.setVisibility(View.VISIBLE);
        }

        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("修改密码");
        }

        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("完成");
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ChangePwdActivity.this.finish();
                break;
            case R.id.head_right_tv:
                //TODO:修改密码提交

                break;
        }
    }
}
