package rongshanghui.tastebychance.com.rongshanghui.home.sht;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragmentActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import toptabdemo.ab.com.library.TabPageIndicator;

/**
 * 类描述：SHTActivity 商会通
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/21 13:46
 * 修改人：
 * 修改时间：2017/11/21 13:46
 * 修改备注：
 *
 * @version 1.0
 */
public class SHTActivity extends MyBaseFragmentActivity {

    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[]{"我的", "商会", "企业", /*"部门",*/
            "机构", "学校", "医院", "其他"};

    private int currentPageIndex = 0;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsnews);
        AppManager.getAppManager().addActivity(this);

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(TITLE.length);
        pager.setCurrentItem(1);

        TabPageIndicator tabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        tabPageIndicator.setViewPager(pager);//绑定

        //设置指示器
        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(getApplicationContext(), TITLE[position], Toast.LENGTH_LONG).show();
                if (position == 0 && null != right_tv) {
                    right_tv.setVisibility(View.VISIBLE);
                    right_tv.setText("添加关注");
                } else {
                    right_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initTitle();

        pager.setCurrentItem(1);
    }

    /**
     * ViewPager适配器
     *
     * @author len
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ItemDepartmentFragment();
            if (position == 0) {//我的
                fragment = new ItemMineFragment();
            } else if (position == 1) {//商会
                fragment = new ItemSHFragment();
            } else if (position == 2) {//企业
                fragment = new ItemQYFragment();
            } /*else if (position == 3) {//部门
                fragment = new ItemDepartmentFragment();
            }*/

            Bundle args = new Bundle();
            args.putString("type", TITLE[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

    private TextView right_tv;

    private void initTitle() {
        //动态设置状态栏下方的控件（view）的高度

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageView right_btn = (ImageView) findViewById(R.id.head_right_iv);
        right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("商会通");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SHTActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("添加关注");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加关注切换到商会界面
                    pager.setCurrentItem(1);
                }
            });
        }
    }


}
