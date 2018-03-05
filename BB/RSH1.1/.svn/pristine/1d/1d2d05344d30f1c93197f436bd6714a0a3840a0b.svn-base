package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan;

import android.content.Intent;
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
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 类描述：ZNCategoryAddActivity 我的-管理-指南管理-类别添加
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 16:32
 * 修改人：
 * 修改时间：2017/12/7 16:32
 * 修改备注：
 *
 * @version 1.0
 */
public class ZNCategoryAddActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_zncategory_add_edt)
    EditText activityZncategoryAddEdt;
    @BindView(R.id.content_zncategory_add)
    RelativeLayout contentZncategoryAdd;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_zncategoryadd_rootlayout)
    CoordinatorLayout activityZncategoryaddRootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zncategory_add);
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
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("添加类别");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ZNCategoryAddActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (StringUtil.isEmpty(activityZncategoryAddEdt.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "请输入您要添加的类别名称");
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("categoryName", activityZncategoryAddEdt.getText().toString());
                setResult(Constants.ADDCATEGORY_RETURNCODE, intent);

                ZNCategoryAddActivity.this.finish();
                break;
        }
    }
}
