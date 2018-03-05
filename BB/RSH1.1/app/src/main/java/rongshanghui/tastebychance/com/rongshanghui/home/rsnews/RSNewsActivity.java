package rongshanghui.tastebychance.com.rongshanghui.home.rsnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragmentActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.home.rsnews.search.RSNewsSearchActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import toptabdemo.ab.com.library.TabPageIndicator;

/**
 * 类描述：RSNewsActivity 融商新闻
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/21 13:46
 * 修改人：
 * 修改时间：2017/11/21 13:46
 * 修改备注：
 *
 * @version 1.0
 */
public class RSNewsActivity extends MyBaseFragmentActivity {

    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[]{"头条", "融商风采", "商会活动", "文化寻根"/*,
            "财经", "数码", "情感", "科技" */};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsnews);
        AppManager.getAppManager().addActivity(this);

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(TITLE.length);

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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initTitle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        this.finish();
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
            Fragment fragment = new ItemFragment();
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

    private void initTitle() {
        //动态设置状态栏下方的控件（view）的高度

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageView right_btn = (ImageView) findViewById(R.id.head_right_iv);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("融商新闻");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RSNewsActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.VISIBLE);
            right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RSNewsActivity.this, RSNewsSearchActivity.class));
                }
            });
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

}
