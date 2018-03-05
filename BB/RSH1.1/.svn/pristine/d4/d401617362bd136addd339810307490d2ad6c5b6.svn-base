package rongshanghui.tastebychance.com.rongshanghui.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.HostActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyFragmentTabs;
import rongshanghui.tastebychance.com.rongshanghui.R;

/**
 * 引导页
 *
 * @author shenbh
 *         <p>
 *         2017年8月9日
 */
public class GuideActivity extends MyAppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    public GestureDetector mGestureDetector;

    private int currentItem = 0;
    private int flaggingWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.startup_guide);


        slideToMain();
        //获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        initViewPager();
    }

    private void initViewPager() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        View guide_one = inflater.inflate(R.layout.startup_guide_one, null);
//		View guide_two = inflater.inflate(R.layout.guide_two,null);
//		View guide_three = inflater.inflate(R.layout.guide_three,null);

//		guide_three.setOnClickListener(this);

        views.add(guide_one);
//		views.add(guide_two);
//		views.add(guide_three);

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(this);
    }

    public void intentToLogin() {

    }

    public void intentToTabHost(View view) {
        startActivity(new Intent(GuideActivity.this, HostActivity.class));
        this.finish();
    }

    private void slideToMain() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (currentItem == 3) {
                    if ((e1.getRawX() - e2.getRawX() >= flaggingWidth)) {
                        Intent intent = new Intent(GuideActivity.this, HostActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        System.out.println("-------------" + currentItem);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
//		SharedPreferencesUtil.getInstance(this).setBoolean("isFirstOpen", false);


        // Intent intent = new Intent(this, MainActivity.class);
        // startActivity(intent);
        // this.finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev)) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(ev);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
