package rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailListActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq.bean.SJFQBean;

/**
 * 数据福清
 *
 * @author shenbh
 * @time 2018/1/16 16:08
 */
public class SJFQActivity extends MyAppCompatActivity {

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
    @BindView(R.id.sjfq_sjyw_tv)
    TextView sjfqSjywTv;
    @BindView(R.id.sjfq_sjyw_more_iv)
    ImageView sjfqSjywMoreIv;
    @BindView(R.id.sjfq_sjyw_rl)
    RelativeLayout sjfqSjywRl;
    @BindView(R.id.sjyw_mlv)
    MyListView sjywMlv;
    @BindView(R.id.sjfq_sjjd_tv)
    TextView sjfqSjjdTv;
    @BindView(R.id.sjfq_sjjd_more_iv)
    ImageView sjfqSjjdMoreIv;
    @BindView(R.id.sjjd_rl)
    RelativeLayout sjjdRl;
    @BindView(R.id.sjjd_mlv)
    MyListView sjjdMlv;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private CommonAdapter<SJFQBean.DataBean.NewsBean> sjfqNewsAdapter;
    private CommonAdapter<SJFQBean.DataBean.UnscrambleBean> sjfqJDsAdapter;
    private List<SJFQBean.DataBean.NewsBean> sjfqNews;
    private List<SJFQBean.DataBean.UnscrambleBean> sjfqJDs;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                ResInfo resInfo = (ResInfo) msg.obj;
                switch (msg.what) {
                    case Constants.WHAT_GETDATA:
                        try {
                            ((SJFQActivity) t).sjfqNews = resInfo.getListFromStrByKey(resInfo.getDataToStr(), "news", SJFQBean.DataBean.NewsBean.class);
                            ((SJFQActivity) t).sjywMlv.setAdapter(((SJFQActivity) t).sjfqNewsAdapter = new CommonAdapter<SJFQBean.DataBean.NewsBean>(
                                    ((SJFQActivity) t).getApplicationContext(), ((SJFQActivity) t).sjfqNews, R.layout.item_sjfq
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, SJFQBean.DataBean.NewsBean item) {
                                    viewHolder.setText(R.id.item_sjfq_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.item_sjfq_time_tv, item.getCreate_time());
                                }
                            });
                            ((SJFQActivity) t).sjywMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((SJFQActivity) t).sjfqNews.get(position).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(((SJFQActivity) t).sjfqNews.get(position).getDetail());
                                    wf.get().setIsCollect(((SJFQActivity) t).sjfqNews.get(position).getIs_collect());
                                    wf.get().setIsLike(((SJFQActivity) t).sjfqNews.get(position).getIs_like());

                                    wf.get().setShareTitle(((SJFQActivity) t).sjfqNews.get(position).getPost_title());
                                    //                            wf.get().setShareImgUrl(sjfqNews.get(position).getM_post_image1());
                                    wf.get().setShareUrl(((SJFQActivity) t).sjfqNews.get(position).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(t, wf.get());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            ((SJFQActivity) t).sjfqJDs = resInfo.getListFromStrByKey(resInfo.getDataToStr(), "unscramble", SJFQBean.DataBean.UnscrambleBean.class);
                            ((SJFQActivity) t).sjjdMlv.setAdapter(((SJFQActivity) t).sjfqJDsAdapter = new CommonAdapter<SJFQBean.DataBean.UnscrambleBean>(
                                    ((SJFQActivity) t).getApplicationContext(), ((SJFQActivity) t).sjfqJDs, R.layout.item_sjfq
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, SJFQBean.DataBean.UnscrambleBean item) {
                                    viewHolder.setText(R.id.item_sjfq_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.item_sjfq_time_tv, item.getCreate_time());
                                }
                            });
                            ((SJFQActivity) t).sjjdMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((SJFQActivity) t).sjfqJDs.get(position).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(((SJFQActivity) t).sjfqJDs.get(position).getDetail());
                                    wf.get().setIsCollect(((SJFQActivity) t).sjfqJDs.get(position).getIs_collect());
                                    wf.get().setIsLike(((SJFQActivity) t).sjfqJDs.get(position).getIs_like());

                                    wf.get().setShareTitle(((SJFQActivity) t).sjfqJDs.get(position).getPost_title());
                                    //                            wf.get().setShareImgUrl(sjfqJDs.get(position).getM_post_image1());
                                    wf.get().setShareUrl(((SJFQActivity) t).sjfqJDs.get(position).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(t, wf.get());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfq);
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

        setTitle();

        getData();
    }

    private void getData() {
        if (null == sjfqNews) {
            sjfqNews = new ArrayList<>();
        }
        if (null == sjfqJDs) {
            sjfqJDs = new ArrayList<>();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.sjfq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SJFQ_FUQINGNEWS;
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

    private void setTitle() {
        headCenterTitleTv.setText("数据福清");
    }


    @OnClick({R.id.head_left_iv, R.id.sjfq_sjyw_more_iv, R.id.sjfq_sjjd_more_iv})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, SJFQListActivity.class);
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
            case R.id.sjfq_sjyw_more_iv:
                intent.putExtra("type", "14");//数据要闻
                startActivity(intent);
                break;
            case R.id.sjfq_sjjd_more_iv:
                intent.putExtra("type", "13");//数据解读
                startActivity(intent);
                break;
        }
    }
}
