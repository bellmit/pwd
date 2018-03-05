package rongshanghui.tastebychance.com.rongshanghui.mime.feedback.yjfkckhf;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
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
import rongshanghui.tastebychance.com.rongshanghui.mime.feedback.yjfkckhf.bean.YJFKCKHFBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 我的-意见反馈-查看回复
 *
 * @author shenbh
 * @time 2018/1/19 09:37
 */
public class YJFKCKHFActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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

    private List<YJFKCKHFBean.DataBean> list;
    private int pageIndex = Constants.PAGE_START_INDEX;
    private CommonAdapter<YJFKCKHFBean.DataBean> adapter;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends MyBaseHandler {
        private final WeakReference<T> mActivity;

        public MyHandler(T t) {
            this.mActivity = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            final T t = mActivity.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            if (((YJFKCKHFActivity) t).emptyLayout != null) {
                                if (((YJFKCKHFActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getDataList(YJFKCKHFBean.DataBean.class) == null || resInfo.getDataList(YJFKCKHFBean.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((YJFKCKHFActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((YJFKCKHFActivity) t).emptyLayout, false);
                                }
                            }

                            //此处不做分页处理，直接赋值
                            ((YJFKCKHFActivity) t).list = resInfo.getDataList(YJFKCKHFBean.DataBean.class);
                            System.out.println("list = " + ((YJFKCKHFActivity) t).list);


                            if (((YJFKCKHFActivity) t).pageIndex > Constants.PAGE_START_INDEX && ((YJFKCKHFActivity) t).list.size() == 0) {
                                ToastUtils.showOneToast(((YJFKCKHFActivity) t).getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((YJFKCKHFActivity) t).emptyLayout, false);
                                return;
                            }

                            if (((YJFKCKHFActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((YJFKCKHFActivity) t).scrollMyListViewToBottom();
                            }

                            ((YJFKCKHFActivity) t).plv.setAdapter(((YJFKCKHFActivity) t).adapter = new CommonAdapter<YJFKCKHFBean.DataBean>(
                                    ((YJFKCKHFActivity) t).getApplicationContext(), ((YJFKCKHFActivity) t).list, R.layout.item_ckhf
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, YJFKCKHFBean.DataBean item) {
                                    viewHolder.setText(R.id.item_ly_title_tv, "反馈：");
                                    viewHolder.setText(R.id.item_ly_time_tv, item.getAdd_time());
                                    viewHolder.setText(R.id.item_ly_content_tv, item.getContent());

                                    viewHolder.setText(R.id.item_hf_title_tv, "回复：");
                                    TextView backTimeTv = viewHolder.getView(R.id.item_hf_time_tv);
                                    TextView backContentTv = viewHolder.getView(R.id.item_hf_content_tv);
                                    if (StringUtil.isEmpty(item.getBack_content())) {
                                        backTimeTv.setText("");
                                        viewHolder.setText(R.id.item_hf_content_tv, "暂无回复");
                                    } else {
                                        viewHolder.setText(R.id.item_hf_time_tv, item.getBack_time());
                                        backContentTv.setText(item.getBack_content());
                                        backContentTv.setTextColor(((YJFKCKHFActivity) t).getResources().getColor(R.color.textgray));
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
        setContentView(R.layout.activity_yjfkckhf);
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

        setTitle();

        if (plv != null) {
            plv.setPullRefreshEnable(false);
            plv.setPullLoadEnable(false);
            plv.setXListViewListener(this);
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

    private void setTitle() {
        headCenterTitleTv.setText("查看回复");
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

    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.yjfkckhf_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_REPLY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
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
