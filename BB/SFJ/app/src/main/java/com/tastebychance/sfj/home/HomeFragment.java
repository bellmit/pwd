package com.tastebychance.sfj.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.web.bean.ShowWebBean;
import com.tastebychance.sfj.home.bean.NoticeBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.MyListView;
import com.tastebychance.sfj.view.pullableview.PullToRefreshRelativeLayout;
import com.tastebychance.sfj.view.pullableview.PullableScrollView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 首页
 *
 * @author shenbinghong
 * @time 2018/1/30 20:14
 * @path com.tastebychance.sfj.home.HomeFragment.java
 */
public class HomeFragment extends MyBaseFragment {


    @BindView(R.id.pull_icon)
    ImageView pullIcon;
    @BindView(R.id.refreshing_icon)
    ImageView refreshingIcon;
    @BindView(R.id.state_tv)
    TextView stateTv;
    @BindView(R.id.state_iv)
    ImageView stateIv;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.home_ad_iv)
    ImageView homeAdIv;
    @BindView(R.id.mlv)
    MyListView mlv;
    @BindView(R.id.pullup_icon)
    ImageView pullupIcon;
    @BindView(R.id.loading_icon)
    ImageView loadingIcon;
    @BindView(R.id.loadstate_tv)
    TextView loadstateTv;
    @BindView(R.id.loadstate_iv)
    ImageView loadstateIv;
    @BindView(R.id.loadmore_view)
    RelativeLayout loadmoreView;
    @BindView(R.id.refresh_view)
    PullToRefreshRelativeLayout refreshView;
    @BindView(R.id.fragment_home_rootlayout)
    RelativeLayout fragmentHomeRootlayout;
    @BindView(R.id.pullablescrollview)
    PullableScrollView pullablescrollview;
    Unbinder unbinder;

    private int pageIndex = Constants.PAGE_START_INDEX;
    private List<NoticeBean.DataBeanX.DataBean> items = new ArrayList<>();
    private HomeNoticeAdapter adapter;

    private static class MyHandler<T extends Fragment> extends Handler{
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t){
                ResInfo resInfo = (ResInfo) msg.obj;
                switch (msg.what) {
                    case Constants.WHAT_GETDATA://动态公告

                        if (((HomeFragment)t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getListByKey("data", NoticeBean.DataBeanX.DataBean.class).size() == 0){
                            ToastUtils.showOneToast(t.getActivity(), "没有更多了");

                            ((HomeFragment)t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                            ((HomeFragment) t).emptyLayout.hide();
                            if (((HomeFragment)t).adapter != null) {
                                ((HomeFragment)t).adapter.notifyDataSetChanged();
                            }
                            return;
                        }

                        //空布局
                        if (((HomeFragment) t).pageIndex == Constants.PAGE_START_INDEX && resInfo.getListByKey("data", NoticeBean.DataBeanX.DataBean.class).size() == 0) {
                            ((HomeFragment) t).emptyLayout.showEmpty(R.drawable.nothing, "");
                        } else {
                            ((HomeFragment) t).emptyLayout.hide();
                        }

                        ((HomeFragment)t).items.addAll(resInfo.getListByKey("data", NoticeBean.DataBeanX.DataBean.class));
                        if (((HomeFragment)t).pageIndex > Constants.PAGE_START_INDEX) {
                            ((HomeFragment)t).scrollMyListViewToBottom();
                        }

                        if (((HomeFragment)t).pageIndex == Constants.PAGE_START_INDEX){
                            ((HomeFragment)t).scrollMyListViewToTop();
                        }

                        ((HomeFragment)t).mlv.setAdapter(((HomeFragment)t).adapter = new HomeNoticeAdapter(t.getActivity(), ((HomeFragment)t).items));

                        ((HomeFragment)t).mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                wf.get().setUrl(((HomeFragment)t).items.get(position).getDetail());
                                wf.get().setTitle("详情");
                                SystemUtil.getInstance().intentToWebInfoActivity(t.getActivity(), wf.get());
                            }
                        });

                        if (null != ((HomeFragment)t).onRefreshLister.getPullToRefreshLayout()) {
                            if (((HomeFragment)t).pageIndex == Constants.PAGE_START_INDEX) {
                                ((HomeFragment)t).onRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                            } else if (((HomeFragment)t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((HomeFragment)t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                            }

                            if (((HomeFragment)t).adapter != null) {
                                ((HomeFragment)t).adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    default:break;
                }
            }
        }
    }
    private MyHandler handler = new MyHandler(this);

    public static HomeFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.EXTRA_CONTENT, content);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    private View rootView;
    private HomeFragment fragment;
    private MyOnRefreshLister onRefreshLister;

    private EmptyLayout emptyLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            unbinder = ButterKnife.bind(this, rootView);
            initTitle();
            isPrepare = true;
            fragment = this;
            onRefreshLister = new MyOnRefreshLister();
            refreshView.setOnRefreshListener(onRefreshLister);
            emptyLayout = rootView.findViewById(R.id.emptyLayout);

            initData();
        }

        return rootView;
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
//            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepare = false;
    }

    /**
     * 滚动到底部
     */
    private void scrollMyListViewToBottom() {
        pullablescrollview.post(new Runnable() {
            @Override
            public void run() {
                pullablescrollview.fullScroll(PullableScrollView.FOCUS_DOWN);
            }
        });
    }

    /**
     * 滚动到顶部
     */
    private void scrollMyListViewToTop() {
        pullablescrollview.post(new Runnable() {
            @Override
            public void run() {
                pullablescrollview.fullScroll(PullableScrollView.FOCUS_UP);
            }
        });

    }

    private void initData() {
        initNotice();
    }

    /**
     * 动态公告
     * token	是	string	token值
     page	是	int	页码
     */
    private void initNotice() {
        if (null != emptyLayout){
            emptyLayout.showLoading(R.drawable.iv_loading, Constants.LOADING);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_NOTICE;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("page", pageIndex + "");
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

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
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);

                        if (null != emptyLayout){
                            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initNotice();
                                }
                            });
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initNotice();
                    }
                });
                emptyLayout.showError();
            }
        });
    }

    private void initTitle() {
        /*if (null != headCenterTitleTv) {
            headCenterTitleTv.setText("首页");
            headCenterTitleTv.setTextColor(Color.BLACK);
        }
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.GONE);
        }*/
    }

    /*@Override
    public void onResume() {
        super.onResume();
        initData();
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    class MyOnRefreshLister implements PullToRefreshRelativeLayout.OnRefreshListener {
        private PullToRefreshRelativeLayout pullToRefreshLayout;

        public PullToRefreshRelativeLayout getPullToRefreshLayout() {
            return pullToRefreshLayout;
        }

        @Override
        public void onRefresh(PullToRefreshRelativeLayout pullToRefreshLayout) {
            //下拉刷新操作
            pageIndex = Constants.PAGE_START_INDEX;
            this.pullToRefreshLayout = pullToRefreshLayout;

            if (null == items) {
                items = new ArrayList<>();
            }

            if (items.size() > 0) {
                items.clear();
            }
            initNotice();
        }

        @Override
        public void onLoadMore(final PullToRefreshRelativeLayout pullToRefreshLayout) {
            //加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            pageIndex++;
            initNotice();
        }
    }

}
