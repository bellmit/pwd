package rongshanghui.tastebychance.com.rongshanghui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.HostActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventSysNewsNum;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToSysNews;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.bean.BannerRes;
import rongshanghui.tastebychance.com.rongshanghui.home.bean.FlipperBean;
import rongshanghui.tastebychance.com.rongshanghui.home.bean.HomeListRes;
import rongshanghui.tastebychance.com.rongshanghui.home.bean.RMTJBean;
import rongshanghui.tastebychance.com.rongshanghui.home.rsnews.RSNewsActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.rzy.RZYActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.search.HomeSearch1Activity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.SHTActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.home.zsb.ZSBActivity;
import rongshanghui.tastebychance.com.rongshanghui.news.NewsActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageDownLoad;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.ScreenUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.banner.ImageViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullableview.PullableScrollView;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/21 14:23
 * 修改人：Administrator
 * 修改时间：2017/12/21 14:23
 * 修改备注：
 */

public class HomeFragment extends MyBaseFragment {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.home_ad_cb_iv)
    ImageView homeAdCbIv;
    @BindView(R.id.home_ad_cb)
    ConvenientBanner homeAdCb;
    @BindView(R.id.home_rsnews_iv)
    ImageView homeRsnewsIv;
    @BindView(R.id.home_sht_iv)
    ImageView homeShtIv;
    @BindView(R.id.home_zsb_iv)
    ImageView homeZsbIv;
    @BindView(R.id.home_rzy_iv)
    ImageView homeRzyIv;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.flipper_tv)
    TextView flipperTv;
    @BindView(R.id.rsnews_tv)
    TextView rsnewsTv;
    @BindView(R.id.rsnews_more_iv)
    ImageView rsnewsMoreIv;
    @BindView(R.id.rsnews_more_tv)
    TextView rsnewsMoreTv;
    @BindView(R.id.rsnews_mlv)
    MyListView rsnewsMlv;
    @BindView(R.id.sht_tv)
    TextView shtTv;
    @BindView(R.id.sht_more_iv)
    ImageView shtMoreIv;
    @BindView(R.id.sht_more_tv)
    TextView shtMoreTv;
    @BindView(R.id.home_1_iv)
    ImageView home1Iv;
    @BindView(R.id.home_1_tv)
    TextView home1Tv;
    @BindView(R.id.home_1_ll)
    LinearLayout home1Ll;
    @BindView(R.id.home_2_iv)
    ImageView home2Iv;
    @BindView(R.id.home_2_tv)
    TextView home2Tv;
    @BindView(R.id.home_2_ll)
    LinearLayout home2Ll;
    @BindView(R.id.home_3_iv)
    ImageView home3Iv;
    @BindView(R.id.home_3_tv)
    TextView home3Tv;
    @BindView(R.id.home_3_ll)
    LinearLayout home3Ll;
    @BindView(R.id.home_4_iv)
    ImageView home4Iv;
    @BindView(R.id.home_4_tv)
    TextView home4Tv;
    @BindView(R.id.home_4_ll)
    LinearLayout home4Ll;
    @BindView(R.id.rmtj_ll)
    LinearLayout rmtjLl;
    @BindView(R.id.zsb_more_iv)
    ImageView zsbMoreIv;
    @BindView(R.id.zsb_more_tv)
    TextView zsbMoreTv;
    @BindView(R.id.zsb_tv)
    TextView zsbTv;
    @BindView(R.id.zsb_mlv)
    MyListView zsbMlv;
    @BindView(R.id.rzy_tv)
    TextView rzyTv;
    @BindView(R.id.rzy_more_iv)
    ImageView rzyMoreIv;
    @BindView(R.id.rzy_more_tv)
    TextView rzyMoreTv;
    @BindView(R.id.rzy_mlv)
    MyListView rzyMlv;
    @BindView(R.id.activity_home)
    ScrollView activityHome;
    Unbinder unbinder;


    private List<RMTJBean> rmtjBeens;
    private static final int FLIPPER_WHAT = 20;
    private static final int RMTJ_WHAT = 21;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends android.support.v4.app.Fragment> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case FLIPPER_WHAT:
                            ((HomeFragment) t).initFlipper(resInfo);
                            break;
                        case RMTJ_WHAT:
                            ((HomeFragment) t).rmtjBeens = resInfo.getDataList(RMTJBean.class);
                            if (null != ((HomeFragment) t).rmtjBeens && null != ((HomeFragment) t).rmtjLl) {
                                ((HomeFragment) t).rmtjLl.setVisibility(View.VISIBLE);
                                switch (((HomeFragment) t).rmtjBeens.size()) {
                                    case 4:
                                        if (null != ((HomeFragment) t).home4Tv) {
                                            PicassoUtils.getinstance().loadCircleImage(t.getActivity(), ((HomeFragment) t).rmtjBeens.get(3).getAvatar(), ((HomeFragment) t).home4Iv, 40);
                                            ((HomeFragment) t).home4Tv.setText(((HomeFragment) t).rmtjBeens.get(3).getUnit_name());
                                        }
                                    case 3:
                                        if (null != ((HomeFragment) t).home3Tv) {
                                            PicassoUtils.getinstance().loadCircleImage(t.getActivity(), ((HomeFragment) t).rmtjBeens.get(2).getAvatar(), ((HomeFragment) t).home3Iv, 40);
                                            ((HomeFragment) t).home3Tv.setText(((HomeFragment) t).rmtjBeens.get(2).getUnit_name());
                                        }
                                    case 2:
                                        if (null != ((HomeFragment) t).home2Tv) {
                                            PicassoUtils.getinstance().loadCircleImage(t.getActivity(), ((HomeFragment) t).rmtjBeens.get(1).getAvatar(), ((HomeFragment) t).home2Iv, 40);
                                            ((HomeFragment) t).home2Tv.setText(((HomeFragment) t).rmtjBeens.get(1).getUnit_name());
                                        }
                                    case 1:
                                        if (null != ((HomeFragment) t).home1Tv) {
                                            PicassoUtils.getinstance().loadCircleImage(t.getActivity(), ((HomeFragment) t).rmtjBeens.get(0).getAvatar(), ((HomeFragment) t).home1Iv, 40);
                                            ((HomeFragment) t).home1Tv.setText(((HomeFragment) t).rmtjBeens.get(0).getUnit_name());
                                        }
                                        break;
                                }
                            } else {
                                if (null != ((HomeFragment) t).rmtjLl) {
                                    ((HomeFragment) t).rmtjLl.setVisibility(View.GONE);
                                }
                            }
                            break;
                        case Constants.WHAT_GETDATA:
                            final List<HomeListRes> rsNewsList = resInfo.getListByKey("rsxw", HomeListRes.class);
                            final List<HomeListRes> zsbList = resInfo.getListByKey("zsb", HomeListRes.class);
                            final List<HomeListRes> rzyList = resInfo.getListByKey("rzy", HomeListRes.class);

                            if (rsNewsList != null && rsNewsList.size() > 0) {
                                ((HomeFragment) t).setRSNewsLvData(rsNewsList);
                            }
                            if (zsbList != null && zsbList.size() > 0) {
                                ((HomeFragment) t).setZSBLvData(zsbList);
                            }
                            if (rzyList != null && rzyList.size() > 0) {
                                ((HomeFragment) t).setRZYLvData(rzyList);
                            }
                            break;
                        default:
                            break;
                    }

                    if (null == ((HomeFragment) t).headLeftIv) {
                        return;
                    }

                    ((HomeFragment) t).scrollMyListViewToTop();
                    ((HomeFragment) t).updateNewsUnread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private static final String EXTRA_CONTENT = "content";

    public static HomeFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    private View rootView;
    private HomeFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_home, null);
        unbinder = ButterKnife.bind(this, contentView);
        rootView = contentView;
        fragment = this;

        initTitle();
        initLL();
        initBanner();

        isPrepare = true;

//        initData();
        return contentView;
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepare = false;
    }

    private boolean isLoginSuc = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        isLoginSuc = true;

        if (StringUtil.isEmpty(eventLoginSuc.getToActivity())) {
            return;
        }
        if (Constants.TO_NEWS.equals(eventLoginSuc.getToActivity())) {
            startActivity(new Intent(getActivity(), NewsActivity.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateSysUnread(@NonNull EventSysNewsNum eventSysNewsNum) {
        updateNewsUnread();
    }

    private void updateNewsUnread() {
        if (null != headLeftIv) {
            if (SystemUtil.getInstance().getIntFromSP(Constants.KEY_SYSNEWSUNREADNUM) > 0) {
                headLeftIv.setImageDrawable(getResources().getDrawable(R.drawable.msg_red));
            } else {
                headLeftIv.setImageDrawable(getResources().getDrawable(R.drawable.msgicon));
            }
        }
    }

    /**
     * 滚动到底部
     */
    private void scrollMyListViewToBottom() {
        activityHome.post(new Runnable() {
            @Override
            public void run() {
                activityHome.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /**
     * 滚动到顶部
     */
    private void scrollMyListViewToTop() {
        activityHome.post(new Runnable() {
            @Override
            public void run() {
                activityHome.fullScroll(ScrollView.FOCUS_UP);
            }
        });

    }

    private void initData() {

        if (Constants.IS_LOCALDATA) {
            //融商新闻
            List<HomeListRes> rsnewsList = new ArrayList<HomeListRes>();
            for (int i = 0; i < 3; i++) {
                WeakReference<HomeListRes> wf = new WeakReference<HomeListRes>(new HomeListRes());
                wf.get().setM_post_image1(Constants.PIC1);
                wf.get().setPost_title("福清新旧城区将迎来“大外环时代");
                wf.get().setPost_hits("1.6万");
                wf.get().setPublish_location("头条");
                rsnewsList.add(wf.get());
            }

            setRSNewsLvData(rsnewsList);
            setZSBLvData(rsnewsList);
            setRZYLvData(rsnewsList);
//        热门推荐

        } else {
            getListData();
            getFlipperData();
            getRMTJData();
            SystemUtil.getInstance().hasNewNotify();
        }

    }

    /**
     * 获取热门推荐数据
     */
    private void getRMTJData() {
        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_SHT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = RMTJ_WHAT;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * 获取通知公告数据
     */
    private void getFlipperData() {
        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_NOTIFY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = FLIPPER_WHAT;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * 设置融资易列表数据
     *
     * @param rsnewsArrayList
     */
    private void setRZYLvData(final List<HomeListRes> rsnewsArrayList) {
        if (null == rzyMlv) {
            return;
        }
        //        融资易
        CommonAdapter<HomeListRes> rzyAdapter;
        rzyMlv.setAdapter(rzyAdapter = new CommonAdapter<HomeListRes>(
                getActivity().getApplicationContext(), rsnewsArrayList, R.layout.item_rsnewslist
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, HomeListRes item) {
                PicassoUtils.getinstance().loadNormalByPath(getActivity(), item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                tv.setText(item.getPublish_location());
                tv.setTextColor(getResources().getColor(R.color.font_blue));
            }
        });
        rzyAdapter.notifyDataSetChanged();

        rzyMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                wf.get().setId(rsnewsArrayList.get(position).getId());
                wf.get().setTitle("信息");
                wf.get().setUrl(rsnewsArrayList.get(position).getDetail());
                wf.get().setIsCollect(rsnewsArrayList.get(position).getIs_collect());
                wf.get().setIsLike(rsnewsArrayList.get(position).getIs_like());

                wf.get().setShareTitle(rsnewsArrayList.get(position).getPost_title());
                wf.get().setShareImgUrl(rsnewsArrayList.get(position).getM_post_image1());
                wf.get().setShareUrl(rsnewsArrayList.get(position).getDetail());
                SystemUtil.getInstance().intentToWebActivity(getActivity(), wf.get());
            }
        });
    }

    /**
     * 设置融商新闻列表数据
     *
     * @param rsnewsArrayList
     */
    private void setRSNewsLvData(final List<HomeListRes> rsnewsArrayList) {
        if (null == rsnewsMlv) {
            return;
        }

        CommonAdapter<HomeListRes> rsnewsAdapter;
        rsnewsMlv.setAdapter(rsnewsAdapter = new CommonAdapter<HomeListRes>(
                getActivity().getApplicationContext(), rsnewsArrayList, R.layout.item_rsnewslist
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, HomeListRes item) {
//                ImageDownLoad.getDownLoadSmallImg(item.getM_post_image1(),(ImageView)viewHolder.getView(R.id.item_cardclip_headportrait_riv));
                PicassoUtils.getinstance().loadNormalByPath(getActivity(), item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                tv.setText(item.getPublish_location());
                tv.setTextColor(getResources().getColor(R.color.font_blue));
            }
        });
        rsnewsAdapter.notifyDataSetChanged();

        rsnewsMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                wf.get().setId(rsnewsArrayList.get(position).getId());
                wf.get().setTitle("信息");
                wf.get().setUrl(rsnewsArrayList.get(position).getDetail());
                wf.get().setIsCollect(rsnewsArrayList.get(position).getIs_collect());
                wf.get().setIsLike(rsnewsArrayList.get(position).getIs_like());

                wf.get().setShareTitle(rsnewsArrayList.get(position).getPost_title());
                wf.get().setShareImgUrl(rsnewsArrayList.get(position).getM_post_image1());
                wf.get().setShareUrl(rsnewsArrayList.get(position).getDetail());
                SystemUtil.getInstance().intentToWebActivity(getActivity(), wf.get());
            }
        });
    }

    /**
     * 设置招商宝数据
     *
     * @param rsnewsArrayList
     */
    private void setZSBLvData(final List<HomeListRes> rsnewsArrayList) {
        if (null == zsbMlv) {
            return;
        }
        //      招商宝
        CommonAdapter<HomeListRes> zsbAdapter;
        zsbMlv.setAdapter(zsbAdapter = new CommonAdapter<HomeListRes>(
                getActivity().getApplicationContext(), rsnewsArrayList, R.layout.item_rsnewslist
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, HomeListRes item) {
                ImageDownLoad.getDownLoadSmallImg(item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                tv.setText(item.getPublish_location());
                tv.setTextColor(getResources().getColor(R.color.font_blue));
            }
        });
        zsbAdapter.notifyDataSetChanged();

        zsbMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                wf.get().setId(rsnewsArrayList.get(position).getId());
                wf.get().setTitle("信息");
                wf.get().setUrl(rsnewsArrayList.get(position).getDetail());
                wf.get().setIsCollect(rsnewsArrayList.get(position).getIs_collect());
                wf.get().setIsLike(rsnewsArrayList.get(position).getIs_like());

                wf.get().setShareTitle(rsnewsArrayList.get(position).getPost_title());
                wf.get().setShareImgUrl(rsnewsArrayList.get(position).getM_post_image1());
                wf.get().setShareUrl(rsnewsArrayList.get(position).getDetail());
                SystemUtil.getInstance().intentToWebActivity(getActivity(), wf.get());
            }
        });
    }

    /*-------------------------------------------设置竖直滚动文字控件------------------------------------------*/
    private void initFlipper(final ResInfo resInfo) {
        if (null != resInfo && null != resInfo.getData() && null != flipperTv) {
            final FlipperBean flipperBean = resInfo.getClass(FlipperBean.class);
            if (null == flipperBean) {
                return;
            }
            flipperTv.setText(flipperBean.getTitle());
            flipperTv.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
//                    EventBusUtils.post(new EventToSysNews());

                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                    wf.get().setId(flipperBean.getId());
                    wf.get().setTitle("信息");
                    wf.get().setUrl(flipperBean.getDetail());
                    SystemUtil.getInstance().intentToWeb2Activity(getActivity(), wf.get());
                }
            });
        }
    }/*-------------------------------------------设置竖直滚动文字控件------------------------------------------*/


    /*-------------------------------------------顶部轮播广告------------------------------------------*/
    private void initBanner() {
        initBannerData();
    }


    private void initBannerData() {
        //initData
        if (Constants.IS_LOCALDATA) {
            List<String> mImageList = new ArrayList<>();//存放服务器获取回来的图片的url
            List<String> mLinkList = new ArrayList<>();//存放服务器获取回来的图片对应的跳转链接

            String mImages[] = {Constants.PIC1, Constants.PIC2};
            for (int i = 0; i < mImages.length; i++) {
                mImageList.add(mImages[i]);
            }

            setBanner(mImageList, mLinkList);
        } else {
            geBannerData();
        }

    }

    private void setBanner(List<String> mImageList, final List<String> mLinkList) {
        if (null == homeAdCb) {
            return;
        }
        homeAdCb.setPages(new CBViewHolderCreator<ImageViewHolder>() {

            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, mImageList)
                .setPageIndicator(new int[]{R.drawable.ponit_normal, R.drawable.point_select})//设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器位置为水平居中
        homeAdCb.setCanLoop(true);

        homeAdCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mLinkList && mLinkList.size() > position) {
                    //点击图片进行链接跳转
//                    UiHelper.ShowOneToast(getApplicationContext(),"点击了条目" + position);

                    if (StringUtil.isEmpty(mLinkList.get(position))) {
                        return;
                    }
                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                    wf.get().setTitle("信息");
                    wf.get().setUrl(mLinkList.get(position));
                    SystemUtil.getInstance().intentToWeb2Activity(getActivity(), wf.get());
                }
            }
        });
    }
     /*-------------------------------------------顶部轮播广告------------------------------------------*/

    private void initLL() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.height = ScreenUtils.getScreenWidth(getActivity()) / 4;
        if (null != ll1) {
            ll1.setLayoutParams(lp);
        }
    }

    private void initTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setText("融商汇");
        }
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
            headLeftIv.setImageResource(R.drawable.msgicon);
        }
        if (headRightIv != null) {
            headRightIv.setVisibility(View.VISIBLE);
        }
        if (headRightIv != null) {
            Bitmap kefuIcon = BitmapFactory.decodeResource(getResources(), R.drawable.home_search);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headRightIv.getLayoutParams();
            lp.width = kefuIcon.getWidth() + kefuIcon.getWidth() / 2;
            lp.height = kefuIcon.getHeight() + kefuIcon.getHeight() / 2;
            headRightIv.setLayoutParams(lp);
            headRightIv.setImageDrawable(getResources().getDrawable(R.drawable.home_search));
        }
        if (headBottomline != null) {
            headBottomline.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //start banner play
        if (null != homeAdCb) {
            homeAdCb.startTurning(2000);//设置开始轮播以及轮播时间
        }

        initData();
    }

    @Override
    public void onStop() {
        if (null != homeAdCb) {
            homeAdCb.stopTurning();//停止轮播
        }
        super.onStop();
    }

    /**
     * 获取广告数据
     * <p>
     * position 	是 	int 	广告图位置 1.首页’2 文章内广告
     */
    private void geBannerData() {

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_BANNER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("position", "1").build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
//                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                        final List<BannerRes> bannerResList = resInfo.getDataList(BannerRes.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bannerResList != null && bannerResList.size() > 0) {
                                    List<String> mImageList = new ArrayList<>();//存放服务器获取回来的图片的url
                                    List<String> mLinkList = new ArrayList<>();//存放服务器获取回来的图片对应的跳转链接

                                    for (BannerRes bannerRes : bannerResList) {
                                        mImageList.add(bannerRes.getImage());
                                        mLinkList.add(bannerRes.getUrl());
                                    }
                                    setBanner(mImageList, mLinkList);
                                }
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });

    }

    /**
     * 获取几个列表的数据
     */
    private void getListData() {
        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_GETINDEXPOSTLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
        }

        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
//                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = Constants.WHAT_GETDATA;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();
        }

        EventBusUtils.unregister(this);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_iv, R.id.rsnews_more_iv, R.id.rsnews_more_tv, R.id.sht_more_iv, R.id.sht_more_tv, R.id.zsb_more_iv, R.id.zsb_more_tv, R.id.rzy_more_iv, R.id.rzy_more_tv
            , R.id.home_rsnews_iv, R.id.home_sht_iv, R.id.home_zsb_iv, R.id.home_rzy_iv,
            R.id.home_1_ll, R.id.home_2_ll, R.id.home_3_ll, R.id.home_4_ll})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_NEWS);
                } else {
                    startActivity(new Intent(getActivity(), NewsActivity.class));
                }
                break;
            case R.id.head_right_iv:
                startActivity(new Intent(getActivity(), HomeSearch1Activity.class));
                break;
            case R.id.rsnews_more_iv:
            case R.id.rsnews_more_tv:
            case R.id.home_rsnews_iv:
                startActivity(new Intent(getActivity(), RSNewsActivity.class));
                break;
            case R.id.sht_more_iv:
            case R.id.sht_more_tv:
            case R.id.home_sht_iv:
                startActivity(new Intent(getActivity(), SHTActivity.class));
                break;
            case R.id.zsb_more_iv:
            case R.id.zsb_more_tv:
            case R.id.home_zsb_iv:
                startActivity(new Intent(getActivity(), ZSBActivity.class));
                break;
            case R.id.rzy_more_iv:
            case R.id.rzy_more_tv:
            case R.id.home_rzy_iv:
                startActivity(new Intent(getActivity(), RZYActivity.class));
                break;
            //4个热门推荐
            case R.id.home_1_ll:
                if (null != rmtjBeens && rmtjBeens.size() > 0) {

                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                    wf.get().setId(rmtjBeens.get(0).getId());
                    wf.get().setIsCared(rmtjBeens.get(0).getIs_cared());
                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("商会"));
                    SystemUtil.getInstance().intentToDetail(getActivity(), wf.get());
                }
                break;
            case R.id.home_2_ll:
                if (null != rmtjBeens && rmtjBeens.size() > 1) {
                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                    wf.get().setId(rmtjBeens.get(1).getId());
                    wf.get().setIsCared(rmtjBeens.get(1).getIs_cared());
                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("商会"));
                    SystemUtil.getInstance().intentToDetail(getActivity(), wf.get());
                }
                break;
            case R.id.home_3_ll:
                if (null != rmtjBeens && rmtjBeens.size() > 2) {
                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                    wf.get().setId(rmtjBeens.get(2).getId());
                    wf.get().setIsCared(rmtjBeens.get(2).getIs_cared());
                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("商会"));
                    SystemUtil.getInstance().intentToDetail(getActivity(), wf.get());
                }
                break;
            case R.id.home_4_ll:
                if (null != rmtjBeens && rmtjBeens.size() > 3) {
                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                    wf.get().setId(rmtjBeens.get(3).getId());
                    wf.get().setIsCared(rmtjBeens.get(3).getIs_cared());
                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("商会"));
                    SystemUtil.getInstance().intentToDetail(getActivity(), wf.get());
                }
                break;
        }
    }
}
