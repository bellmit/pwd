package toptabdemo.ab.com.toptabdemo.underlinepageindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import toptabdemo.ab.com.library.TabPageIndicator;
import toptabdemo.ab.com.toptabdemo.R;
import toptabdemo.ab.com.toptabdemo.normalindicator.ItemFragment;

/**
 * 项目名称：TopTabDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/15 11:06
 * 修改人：Administrator
 * 修改时间：2017/11/15 11:06
 * 修改备注：
 */

public class ULActivity extends FragmentActivity {
    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[] { "头条", "房产", "另一面", "女人",
            "财经", "数码", "情感", "科技" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ul);

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);


        TabPageIndicator tabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        tabPageIndicator.setViewPager(pager);//绑定

        //实例化UnderlinePageIndicator然后设置ViewPager与之关联
        /*UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator)findViewById(R.id.underline_indicator);
        underlinePageIndicator.setViewPager(pager);//绑定
        underlinePageIndicator.setFades(false);//一直显示
*/
        //设置指示器
//        tabPageIndicator.setOnPageChangeListener(underlinePageIndicator);
        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(),TITLE[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    /**
     * ViewPager适配器
     * @author len
     *
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
            args.putString("arg", TITLE[position]);
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
}
