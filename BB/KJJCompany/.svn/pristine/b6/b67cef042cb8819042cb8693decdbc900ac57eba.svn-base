package company.webdemo.agile.com.technologycompany.home.technologytrends;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.webdemo.agile.com.technologycompany.MyBaseActivity;
import company.webdemo.agile.com.technologycompany.R;
/**
 * 类描述：TechnologyTrendsActivity 科技动态列表 
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/10/31 16:47
 * 修改人：
 * 修改时间：2017/10/31 16:47
 * 修改备注：
 * @version 1.0
 */
public class TechnologyTrendsActivity extends MyBaseActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_btn)
    ImageButton headRightBtn;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.lv_trends)
    ListView lvTrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_trends);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        initTitle();

    }

    private void initTitle() {
        headCenterTitleTv.setText("科技动态");

    }

}
