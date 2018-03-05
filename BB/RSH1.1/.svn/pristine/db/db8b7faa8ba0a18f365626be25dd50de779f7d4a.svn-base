package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.MyBaseHandler;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullableview.PullToRefreshRelativeLayout;
import rongshanghui.tastebychance.com.rongshanghui.view.pullableview.PullableScrollView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.bean.HDLYBean;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ckhf.CKHFListActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ly.LYActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.yjzq.YJZQActivity;

/**
 * 互动留言
 *
 * @author shenbh
 * @time 2018/1/15 20:55
 */
public class HDLYActivity extends MyAppCompatActivity {

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
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_hdly_rootlayout)
    CoordinatorLayout activityHdlyRootlayout;
    @BindView(R.id.hdly_child1__bg_iv)
    ImageView hdlyChild1BgIv;
    @BindView(R.id.hdly_child1_ly_tv)
    TextView hdlyChild1LyTv;
    @BindView(R.id.hdly_child1_ckhf_tv)
    TextView hdlyChild1CkhfTv;
    @BindView(R.id.unread)
    TextView unread;
    @BindView(R.id.mlv)
    MyListView mlv;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;
    @BindView(R.id.refresh_view)
    PullToRefreshRelativeLayout refreshView;
    @BindView(R.id.pullablescrollview)
    PullableScrollView pullablescrollview;

    private HDLYBean.DataBeanX.BannerBean bannerBean;
    private List<HDLYBean.DataBeanX.ListBean.DataBean> items;
    private CommonAdapter<HDLYBean.DataBeanX.ListBean.DataBean> adapter;
    private int pageIndex = Constants.PAGE_START_INDEX;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends MyBaseHandler {
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
                        case Constants.WHAT_GETDATA:
                            try {
                                ((HDLYActivity) t).bannerBean = resInfo.getClassByKey("banner", HDLYBean.DataBeanX.BannerBean.class);
                                if (null != ((HDLYActivity) t).bannerBean) {
                                    PicassoUtils.getinstance().loadNormalByPath(t, ((HDLYActivity) t).bannerBean.getImage(), ((HDLYActivity) t).hdlyChild1BgIv);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (((HDLYActivity) t).pageIndex > Constants.PAGE_START_INDEX && ((ResInfo) resInfo).getListFromStrByKey(((JSONObject) ((ResInfo) resInfo).getData()).get("list").toString(), "data", HDLYBean.DataBeanX.ListBean.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((HDLYActivity) t).emptyLayout, false);

                                ((HDLYActivity) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                if (((HDLYActivity) t).adapter != null) {
                                    ((HDLYActivity) t).adapter.notifyDataSetChanged();
                                }
                                return;
                            }
                            if (((HDLYActivity) t).emptyLayout != null) {
                                if (((HDLYActivity) t).pageIndex == Constants.PAGE_START_INDEX && (((ResInfo) resInfo).getListFromStrByKey(((JSONObject) ((ResInfo) resInfo).getData()).get("list").toString(), "data", HDLYBean.DataBeanX.ListBean.DataBean.class) == null || ((ResInfo) resInfo).getListFromStrByKey(((JSONObject) ((ResInfo) resInfo).getData()).get("list").toString(), "data", HDLYBean.DataBeanX.ListBean.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((HDLYActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((HDLYActivity) t).emptyLayout, false);
                                }
                            }

                            ((HDLYActivity) t).items.addAll(((ResInfo) resInfo).getListFromStrByKey(((JSONObject) ((ResInfo) resInfo).getData()).get("list").toString(), "data", HDLYBean.DataBeanX.ListBean.DataBean.class));


                            if (((HDLYActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((HDLYActivity) t).scrollMyListViewToBottom();
                            }

                            if (((HDLYActivity) t).pageIndex == Constants.PAGE_START_INDEX) {
                                ((HDLYActivity) t).scrollMyListViewToTop();
                            }

                            ((HDLYActivity) t).mlv.setAdapter(((HDLYActivity) t).adapter = new CommonAdapter<HDLYBean.DataBeanX.ListBean.DataBean>(
                                    t.getApplicationContext(), ((HDLYActivity) t).items, R.layout.item_hdly_child2
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, HDLYBean.DataBeanX.ListBean.DataBean item) {
                                    viewHolder.setText(R.id.hdly_child2_name_tv, item.getPost_title());
                                    viewHolder.setText(R.id.hdly_child2_time_tv, item.getCreate_time());

                                }
                            });

                            ((HDLYActivity) t).mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(t, YJZQActivity.class);
                                    intent.putExtra("post_id", ((HDLYActivity) t).items.get(position).getId());
                                    ((HDLYActivity) t).startActivity(intent);
                                }
                            });

                            if (null != ((HDLYActivity) t).onRefreshLister.getPullToRefreshLayout()) {
                                if (((HDLYActivity) t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((HDLYActivity) t).onRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                                } else if (((HDLYActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((HDLYActivity) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                }

                                if (((HDLYActivity) t).adapter != null) {
                                    ((HDLYActivity) t).adapter.notifyDataSetChanged();
                                }
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private MyOnRefreshLister onRefreshLister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdly);
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

        onRefreshLister = new MyOnRefreshLister();
        refreshView.setOnRefreshListener(onRefreshLister);
        settitle();
    }

    private void settitle() {
        headCenterTitleTv.setText("互动留言");
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

    @Override
    protected void onResume() {
        super.onResume();
        if (null == items) {
            items = new ArrayList<>();
        }

        if (items.size() > 0) {
            items.clear();
        }

        getData();
    }

    @Override
    protected void onStart() {
        EventBusUtils.register(this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        if (eventLoginSuc.getToActivity().equals(Constants.TO_HDLY_CKHF)) {
            startActivity(new Intent(this, CKHFListActivity.class));
        } else if (eventLoginSuc.getToActivity().equals(Constants.TO_HDLY_LY)) {
            startActivity(new Intent(this, LYActivity.class));
        }
    }

    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_hdly_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HDLY_FEEDBACKCOLLECT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("page", pageIndex + "")
                .build();
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

                        runOnUiThread(new Runnable() {
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

    @OnClick({R.id.head_left_iv, R.id.hdly_child1_ckhf_tv, R.id.hdly_child1_ly_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
            case R.id.hdly_child1_ckhf_tv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(HDLYActivity.this, Constants.TO_HDLY_CKHF);
                } else {
                    startActivity(new Intent(HDLYActivity.this, CKHFListActivity.class));
                }
                break;
            case R.id.hdly_child1_ly_tv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(HDLYActivity.this, Constants.TO_HDLY_LY);
                } else {
                    startActivity(new Intent(HDLYActivity.this, LYActivity.class));
                }
                break;
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
            getData();
        }

        @Override
        public void onLoadMore(final PullToRefreshRelativeLayout pullToRefreshLayout) {
            //加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            pageIndex++;
            getData();
        }
    }
}
