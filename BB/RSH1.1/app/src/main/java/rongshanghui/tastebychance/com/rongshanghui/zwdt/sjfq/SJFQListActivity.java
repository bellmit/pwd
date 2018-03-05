package rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.MyBaseHandler;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq.bean.SJFQBean;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq.bean.SJFQListBean;

/**
 * 数据福清-数据要闻
 *
 * @author shenbh
 * @time 2018/1/16 17:54
 */
public class SJFQListActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.plv)
    PullRefreshPushAddListView plv;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private int pageIndex = Constants.PAGE_START_INDEX;
    private List<SJFQListBean.DataBeanX.DataBean> list;
    private CommonAdapter<SJFQListBean.DataBeanX.DataBean> adapter;

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

                            if (((SJFQListActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getListFromStrByKey(resInfo.getDataToStr(), "data", SJFQListBean.DataBeanX.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((SJFQListActivity) t).emptyLayout, false);
                                return;
                            }

                            if (((SJFQListActivity) t).emptyLayout != null) {
                                if (((SJFQListActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getListFromStrByKey(resInfo.getDataToStr(), "data", SJFQListBean.DataBeanX.DataBean.class) == null || resInfo.getListFromStrByKey(resInfo.getDataToStr(), "data", SJFQListBean.DataBeanX.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((SJFQListActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((SJFQListActivity) t).emptyLayout, false);
                                }
                            }
                            ((SJFQListActivity) t).list.addAll(resInfo.getListFromStrByKey(resInfo.getDataToStr(), "data", SJFQListBean.DataBeanX.DataBean.class));

                            if (((SJFQListActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((SJFQListActivity) t).scrollMyListViewToBottom();
                            }

                            ((SJFQListActivity) t).plv.setAdapter(((SJFQListActivity) t).adapter = new CommonAdapter<SJFQListBean.DataBeanX.DataBean>(
                                    t.getApplicationContext(), ((SJFQListActivity) t).list, R.layout.item_sjyw
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, SJFQListBean.DataBeanX.DataBean item) {
                                    viewHolder.setText(R.id.item_sjfq_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.item_sjfq_time_tv, item.getCreate_time());
                                }
                            });

                            ((SJFQListActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((SJFQListActivity) t).list.get(position - 1).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(((SJFQListActivity) t).list.get(position - 1).getDetail());
                                    wf.get().setIsCollect(((SJFQListActivity) t).list.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(((SJFQListActivity) t).list.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(((SJFQListActivity) t).list.get(position - 1).getPost_title());
                                    //                            wf.get().setShareImgUrl(sjfqJDs.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(((SJFQListActivity) t).list.get(position - 1).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(t, wf.get());
                                }
                            });

                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private EmptyLayout emptyLayout;
    private String type = "14";//数据解读：13 数据要闻：14

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfqlist);
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
        emptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        setTitle();

        if (null != plv) {
            plv.setPullLoadEnable(true);
            plv.setPullRefreshEnable(true);
            plv.setXListViewListener(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != list) {
            list.clear();
        }
        getData();
    }

    /**
     * 滚动到底部
     */
    private void scrollMyListViewToBottom() {
        plv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                plv.setSelection(adapter.getCount() - 1);
            }
        });
    }

    /**
     * token	否	string	token值 有token时传，没有不传
     * publish_location	是	int	数据解读：13 数据要闻：14
     * page	是	int	页码
     */
    private void getData() {
        if (list == null) {
            list = new ArrayList<>();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.sjfqlist_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SJFQ_FUQINGMORENEWS;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("publish_location", type);
        builder.add("page", pageIndex + "");
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

                        if (emptyLayout != null) {
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private void setTitle() {
        headCenterTitleTv.setText("数据要闻");
    }

    private void onLoad() {

        String str = Thread.currentThread().getName();
        System.out.println("str = " + str);

        if (StringUtil.isNotEmpty(str) && str.equals("main") && null != plv) {
            plv.stopRefresh();
            plv.stopLoadMore();
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
            plv.setRefreshTime(date);
        }
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != list) {
            list.clear();
        }
        getData();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        getData();
        onLoad();
    }
}
