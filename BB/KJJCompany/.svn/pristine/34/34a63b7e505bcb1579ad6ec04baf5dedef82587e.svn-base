package company.webdemo.agile.com.technologycompany.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import company.webdemo.agile.com.technologycompany.MyBaseActivity;
import company.webdemo.agile.com.technologycompany.R;
import company.webdemo.agile.com.technologycompany.common.adapter.CommonAdapter;
import company.webdemo.agile.com.technologycompany.common.adapter.ViewHolder;
import company.webdemo.agile.com.technologycompany.home.banner.ImageViewHolder;
import company.webdemo.agile.com.technologycompany.home.bean.TrendsRes;
import company.webdemo.agile.com.technologycompany.home.technologybureau.TechnologyBureauActivity;
import company.webdemo.agile.com.technologycompany.home.technologytrends.TechnologyTrendsActivity;
import company.webdemo.agile.com.technologycompany.util.Constants;
import company.webdemo.agile.com.technologycompany.util.UiHelper;
import company.webdemo.agile.com.technologycompany.util.UrlManager;
import company.webdemo.agile.com.technologycompany.view.MyListView;
import company.webdemo.agile.com.technologycompany.view.PengTextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 首页
 *
 * @author shenbh
 * @time 2017/10/27 11:49
 */
public class HomeActivity extends MyBaseActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_btn)
    ImageButton headRightBtn;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.id_cb)
    ConvenientBanner mCb;
    @BindView(R.id.flipper)
    ViewFlipper flipper;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.mylistview)
    MyListView mylistview;
    @BindView(R.id.home_technologybureau_ptv)
    PengTextView ptvHomeTechnologybureau;
    @BindView(R.id.home_technologytrends_ptv)
    PengTextView ptvHomeTechnologytrends;
    @BindView(R.id.home_technologypolicy_ptv)
    PengTextView ptvHomeTechnologypolicy;
    @BindView(R.id.home_serviceguide_ptv)
    PengTextView ptvHomeServiceguide;
    @BindView(R.id.home_onlinecommunication_ptv)
    PengTextView ptvHomeOnlinecommunication;
    @BindView(R.id.home_guestbook_ptv)
    PengTextView ptvHomeGuestbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        initTitle();//标题
        setBanner();//广告
        initViewFlipper();//通知公告，竖直滚动
        init9Lattice();
        initTrends();
    }

    /**
     * 科技动态
     */
    private CommonAdapter<TrendsRes> trendsInfoCommonAdapter;
    private List<TrendsRes> list = new ArrayList<TrendsRes>();

    private void initTrends() {
        /*TODO:科技动态列表数据
        for (int i = 0; i < 3; i++) {
            WeakReference<TrendsRes> wf = new WeakReference<TrendsRes>(new TrendsRes());
            wf.get().
        }*/

        mylistview.setAdapter(trendsInfoCommonAdapter = new CommonAdapter<TrendsRes>(
                getApplicationContext(), list, R.layout.item_trends
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, TrendsRes item) {

            }
        });
    }

    /*-------------------------------------------9宫格------------------------------------------*/
    private void init9Lattice() {
/*        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = Util.getScreenWidth() / 3;
        layoutParams.width = Util.getScreenWidth();
        ll1.setLayoutParams(layoutParams);
        ll2.setLayoutParams(layoutParams);
        ll3.setLayoutParams(layoutParams);*/

    }
    /*-------------------------------------------9宫格------------------------------------------*/


    /*-------------------------------------------设置竖直滚动文字控件------------------------------------------*/
    private List testList;
    private int count;

    private void initViewFlipper() {
//初始化list数据
        testList = new ArrayList();
        testList.add(0, "爸妈爱的“白”娃娃，真是孕期吃出来的吗？");
        testList.add(1, "如果徒步真的需要理由，十四个够不够？");
//        testList.add(2, "享受清爽啤酒的同时，这些常识你真的了解吗？");
//        testList.add(3, "三星Galaxy S8定型图无悬念");
//        testList.add(4, "家猫为何如此高冷？");
//        testList.add(5, "家猫为何如此高冷222？");
        count = testList.size();

        if (count % 2 == 0) {//偶数个消息

            for (int i = 0; i < count; i += 2) {
                final View ll_content = View.inflate(HomeActivity.this, R.layout.item_flipper, null);
                TextView tv_content1 = (TextView) ll_content.findViewById(R.id.tv_content1);
                TextView tv_content2 = (TextView) ll_content.findViewById(R.id.tv_content2);
                //            ImageView iv_closebreak = (ImageView) ll_content.findViewById(R.id.iv_closebreak);
                tv_content1.setText(testList.get(i).toString());
                tv_content2.setText(testList.get(i + 1).toString());
                //            iv_closebreak.setOnClickListener(new View.OnClickListener() {
                //                @Override
                //                public void onClick(View v) {
                //                    //对当前显示的视图进行移除
                //                    flipper.removeView(ll_content);
                //                    count--;
                //                    //当删除后仅剩 一条 新闻时，则取消滚动
                //                    if (count == 1) {
                //                        flipper.stopFlipping();
                //                    }
                //                }
                //            });
                flipper.addView(ll_content);
            }
        } else {
            /*for (int i = 0; i < count; i+=2) {
                final View ll_content = View.inflate(HomeActivity.this, R.layout.item_flipper, null);
                TextView tv_content1 = (TextView) ll_content.findViewById(R.id.tv_content1);
                TextView tv_content2 = (TextView) ll_content.findViewById(R.id.tv_content2);
                tv_content1.setText(testList.get(i).toString());
                tv_content2.setText(testList.get(i+1).toString());
                flipper.addView(ll_content);
            }*/
        }
    }/*-------------------------------------------设置竖直滚动文字控件------------------------------------------*/

/*-------------------------------------------顶部轮播广告------------------------------------------*/

    /**
     * 获取顶部广告图片
     */
    private void getDiscount() {
        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_HOME_BANNER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String str = response.body().string();
                    Log.i(Constants.TAG, str);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                            if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                                List<BannerInfo> bannerInfos = resInfo.getDataList(BannerInfo.class);
                                for (BannerInfo bannerInfo : bannerInfos) {
                                    mImageList.add(bannerInfo.getImage());
                                    mLinkList.add(bannerInfo.getLink());
                                }
                                if (null != mImageList && mImageList.size() > 0) {
                                    initBannerData();
                                }
                            } else {
                                Message msg = new Message();
                                msg.what = ERROR_WHAT;
                                msg.obj = resInfo.getError_message();
                                myHandler.sendMessage(msg);
                            }*/
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private List<String> mImageList = new ArrayList<>();//存放服务器获取回来的图片的url
    //    private int[] mImages = {R.drawable.item01,R.drawable.item02,R.drawable.item03};
    private List<String> mLinkList = new ArrayList<>();//存放服务器获取回来的图片对应的跳转链接

    private void setBanner() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = UiHelper.getScreenWidth(HomeActivity.this);
        lp.height = lp.width * 9 / 16;
        mCb.setLayoutParams(lp);

//        getDiscount();

        initBannerData();

        setBannerListener();
    }

    private void initBannerData() {
        //initData
        String mImages[] = {"http://www.n63.com/zutu/n63/?N=X2hiJTI2MS4lNUMlNURZMVklNUIlNUQtWTAxJTJBJTJCLSUyODAlNUIlMjklMkMlMjklNUMlNUUwJTVDJTVFLS0xJTJBJTVDJTVCJTJGJTI3JTVDJTVCJTJG&v=.jpg",
                "http://www.n63.com/zutu/n63/?N=X2hiJTI2LkYlNUJiTiUyQjAlMkYlMkYlMkElMkYuJTI4JTJDJTI5JTI3LjExJTI5&v=.jpg"};
        for (int i = 0; i < mImages.length; i++) {
            mImageList.add(mImages[i]);
        }


        mCb.setPages(new CBViewHolderCreator<ImageViewHolder>() {

            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, mImageList)
                .setPageIndicator(new int[]{R.drawable.ponit_normal, R.drawable.point_select})//设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器位置为水平居中
        mCb.setCanLoop(true);

    }

    private void setBannerListener() {
        mCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mLinkList && mLinkList.size() > position) {
                    //TODO:点击图片进行链接跳转

//                    UiHelper.ShowOneToast(getApplicationContext(),"点击了条目" + position);
                }
            }
        });
    }
    /*-------------------------------------------顶部轮播广告------------------------------------------*/


    private void initTitle() {
        headCenterTitleTv.setText("首页");

    }

    @Override
    protected void onResume() {
        super.onResume();

        //start banner play
        if (null != mCb) {
            mCb.startTurning(2000);//设置开始轮播以及轮播时间
        }
    }

    @Override
    protected void onStop() {
        if (null != mCb) {
            mCb.stopTurning();//停止轮播
        }
        super.onStop();
    }

    @OnClick(R.id.tv_more)
    public void onViewClicked() {
        startActivity(new Intent(this, TechnologyTrendsActivity.class));
    }

    @OnClick({R.id.home_technologybureau_ptv, R.id.home_technologytrends_ptv, R.id.home_technologypolicy_ptv, R.id.home_serviceguide_ptv, R.id.home_onlinecommunication_ptv, R.id.home_guestbook_ptv})
    public void ptvClick(PengTextView ptv){
        switch (ptv.getId()) {
            case R.id.home_technologybureau_ptv:
                startActivity(new Intent(HomeActivity.this,TechnologyBureauActivity.class));
                break;
            case R.id.home_technologytrends_ptv:
                startActivity(new Intent(HomeActivity.this,TechnologyTrendsActivity.class));
                break;
            case R.id.home_technologypolicy_ptv:
                break;
            case R.id.home_serviceguide_ptv:
                break;
            case R.id.home_onlinecommunication_ptv:
                break;
            case R.id.home_guestbook_ptv:
                break;
        }
    }

}