package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ckhf;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.Constant;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.MyBaseHandler;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.SHTMineRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ckhf.bean.CKHFBean;

/**
 * 查看回复列表界面
 *
 * @author shenbh
 * @time 2018/1/16 15:41
 */
public class CKHFListActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    private List<CKHFBean.DataBean> list;
    private CommonAdapter<CKHFBean.DataBean> adapter;

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
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;


                            if (((CKHFListActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(CKHFBean.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(((CKHFListActivity) t).getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((CKHFListActivity) t).emptyLayout, false);
                                return;
                            }

                            if (((CKHFListActivity) t).emptyLayout != null) {
                                if (((CKHFListActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getDataList(CKHFBean.DataBean.class) == null || resInfo.getDataList(CKHFBean.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((CKHFListActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((CKHFListActivity) t).emptyLayout, false);
                                }
                            }
                            ((CKHFListActivity) t).list.addAll(resInfo.getDataList(CKHFBean.DataBean.class));

                            if (((CKHFListActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((CKHFListActivity) t).scrollMyListViewToBottom();
                            }

                            ((CKHFListActivity) t).plv.setAdapter(((CKHFListActivity) t).adapter = new CommonAdapter<CKHFBean.DataBean>(
                                    ((CKHFListActivity) t).getApplicationContext(), ((CKHFListActivity) t).list, R.layout.item_ckhf
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, CKHFBean.DataBean item) {
                                    viewHolder.setText(R.id.item_ly_title_tv, "留言内容：");
                                    viewHolder.setText(R.id.item_ly_time_tv, item.getAdd_time());
                                    viewHolder.setText(R.id.item_ly_content_tv, item.getContent());

                                    LinearLayout itemHfTitleLl = viewHolder.getView(R.id.item_hf_ll);
                                    TextView itemHfContentTv = viewHolder.getView(R.id.item_hf_content_tv);
                                    if (StringUtil.isEmpty(item.getBack_content())) {
                                        itemHfTitleLl.setVisibility(View.GONE);
                                    } else {
                                        itemHfTitleLl.setVisibility(View.VISIBLE);

                                        viewHolder.setText(R.id.item_hf_title_tv, "回复内容：");
                                        viewHolder.setText(R.id.item_hf_time_tv, item.getBack_time());
                                        viewHolder.setText(R.id.item_hf_content_tv, item.getBack_content());
                                    }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckhflist);
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

        plv.setPullLoadEnable(false);
        plv.setPullRefreshEnable(false);
        plv.setXListViewListener(this);

        setTitle();
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

    private void getData() {
        if (null == list) {
            list = new ArrayList<>();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(this.findViewById(R.id.zwdt_hdly_ckhf_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HDLY_SEEREPLY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
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
        headCenterTitleTv.setText("查看回复");
    }

    private void onLoad() {
        plv.stopRefresh();
        plv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        plv.setRefreshTime(date);
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

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        this.finish();
    }
}
