package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.yjzq;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.SHTMineRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullableview.PullToRefreshRelativeLayout;
import rongshanghui.tastebychance.com.rongshanghui.view.pullableview.PullableScrollView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.FWDTActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.yjzq.bean.YJZQBean;

/**
 * 政务大厅-互动留言-意见反馈
 *
 * @author shenbh
 * @time 2018/1/17 14:16
 */
public class YJZQActivity extends MyAppCompatActivity {


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
    @BindView(R.id.yjzq_title_tv)
    TextView yjzqTitleTv;
    @BindView(R.id.yjzq_dw_logo_iv)
    ImageView yjzqDwLogoIv;
    @BindView(R.id.yjzq_dw_name_tv)
    TextView yjzqDwNameTv;
    @BindView(R.id.yjzq_dw_time_tv)
    TextView yjzqDwTimeTv;
    @BindView(R.id.yjzq_dw_guanzhu_tv)
    TextView yjzqDwGuanzhuTv;
    @BindView(R.id.yjzq_dw_content_tv)
    TextView yjzqDwContentTv;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.yjzq_hf_title_tv)
    TextView yjzqHfTitleTv;
    @BindView(R.id.view2)
    View view2;
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
    @BindView(R.id.yjzq_input_edt)
    EditText yjzqInputEdt;
    @BindView(R.id.yjzq_input_send_tv)
    TextView yjzqInputSendTv;
    @BindView(R.id.yjzq_input_ll)
    LinearLayout yjzqInputLl;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.pullablescrollview)
    PullableScrollView pullablescrollview;

    private MyOnRefreshLister myOnRefreshLister;
    private int pageIndex = Constants.PAGE_START_INDEX;
    private List<YJZQBean.DataBeanX.RepliesBean.DataBean> list;
    private CommonAdapter<YJZQBean.DataBeanX.RepliesBean.DataBean> adapter;

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
                try {
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;

                            if (resInfo != null) {
                                final YJZQBean.DataBeanX.InfoBean infoBean = resInfo.getClassByKey("info", YJZQBean.DataBeanX.InfoBean.class);
                                ((YJZQActivity) t).yjzqTitleTv.setText(infoBean.getPost_title());
                                PicassoUtils.getinstance().LoadImageWithWidtAndHeight(t, infoBean.getAvatar(), ((YJZQActivity) t).yjzqDwLogoIv, -1, -1, PicassoUtils.PICASSO_BITMAP_SHOW_ROUND_TYPE, 10);
                                ((YJZQActivity) t).yjzqDwNameTv.setText(infoBean.getUnit_name());
                                ((YJZQActivity) t).yjzqDwTimeTv.setText(infoBean.getCreate_time());
                                if (infoBean.getIs_cared() == 0) {//是否关注 1：是 0 ：否
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setText("关注");
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setTextColor(Color.WHITE);
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setBackgroundDrawable(t.getResources().getDrawable(R.drawable.circle25_bluebg_style));
                                } else {
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setText("已关注");
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setTextColor(Color.WHITE);
                                    ((YJZQActivity) t).yjzqDwGuanzhuTv.setBackgroundDrawable(t.getResources().getDrawable(R.drawable.circle25_lightgraybg_style));
                                }
                                ((YJZQActivity) t).yjzqDwGuanzhuTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (StringUtil.isEmpty(SystemUtil.getInstance().getToken())) {
                                            ToastUtils.showOneToast(t.getApplicationContext(), Constants.LOGIN_INVALID);
                                            return;
                                        }

                                        if (((YJZQActivity) t).yjzqDwGuanzhuTv.getText().equals("已关注")) {
                                            ((YJZQActivity) t).userCare(infoBean.getUser_id() + "", false);
                                        } else {
                                            ((YJZQActivity) t).userCare(infoBean.getUser_id() + "", true);
                                        }
                                    }
                                });

                                ((YJZQActivity) t).yjzqDwContentTv.setText(infoBean.getPost_content());

                                if (((YJZQActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClassByKey("replies", YJZQBean.DataBeanX.RepliesBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "没有更多了");
                                    ((YJZQActivity) t).myOnRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                    return;
                                }

                                if (((YJZQActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((YJZQActivity) t).pullablescrollview.fullScroll(ScrollView.FOCUS_DOWN);
                                }

                                ((YJZQActivity) t).list.addAll(resInfo.getClassByKey("replies", YJZQBean.DataBeanX.RepliesBean.class).getData());
                                ((YJZQActivity) t).mlv.setAdapter(((YJZQActivity) t).adapter = new CommonAdapter<YJZQBean.DataBeanX.RepliesBean.DataBean>(
                                        t.getApplicationContext(), ((YJZQActivity) t).list, R.layout.item_yjzq_hf
                                ) {
                                    @Override
                                    protected void convert(ViewHolder viewHolder, YJZQBean.DataBeanX.RepliesBean.DataBean item) {
                                        PicassoUtils.getinstance().LoadImageWithWidtAndHeight(t, item.getAvatar(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv), -1, -1, PicassoUtils.PICASSO_BITMAP_SHOW_ROUND_TYPE, 10);
                                        //                                    viewHolder.setText(R.id.item_yjzq_hf_name_tv, item.getUser_name());
                                        //                                    viewHolder.setText(R.id.item_yjzq_hf_date_tv, item.getAdd_time());
                                        //                                    viewHolder.setText(R.id.item_yjzq_hf_content_tv, item.getReply());

                                        TextView nameTv = viewHolder.getView(R.id.item_yjzq_hf_name_tv);
                                        TextView timeTv = viewHolder.getView(R.id.item_yjzq_hf_date_tv);
                                        TextView contentTv = viewHolder.getView(R.id.item_yjzq_hf_content_tv);

                                        nameTv.setText(item.getUser_name());
                                        timeTv.setText(item.getAdd_time());
                                        contentTv.setText(item.getReply());
                                        nameTv.setTextColor(t.getResources().getColor(R.color.textgray));
                                        timeTv.setTextColor(t.getResources().getColor(R.color.textgray));
                                        contentTv.setTextColor(t.getResources().getColor(R.color.textgray));
                                    }
                                });
                            }

                            if (null != ((YJZQActivity) t).myOnRefreshLister.getPullToRefreshLayout()) {
                                if (((YJZQActivity) t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((YJZQActivity) t).myOnRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                                } else if (((YJZQActivity) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((YJZQActivity) t).myOnRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                }

                                if (((YJZQActivity) t).adapter != null) {
                                    ((YJZQActivity) t).adapter.notifyDataSetChanged();
                                }
                            }

                            break;
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private String from;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yjzq);
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

        if (null != getIntent()) {
            postId = getIntent().getIntExtra("post_id", 0);
        }

        if (getIntent() != null) {
            from = getIntent().getStringExtra("from");
        }

        setTitle();
        myOnRefreshLister = new MyOnRefreshLister();
        refreshView.setOnRefreshListener(myOnRefreshLister);

        initView();
        initData();
    }

    private void initView() {
        if (StringUtil.isNotEmpty(from) && Constants.FROMYJZQMANAGERTOYJZQ.equals(from)) {
            yjzqDwGuanzhuTv.setVisibility(View.GONE);
            yjzqInputLl.setVisibility(View.GONE);
        } else {
            yjzqDwGuanzhuTv.setVisibility(View.VISIBLE);
            yjzqInputLl.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (list != null) {
            list.clear();
        }
        getData();
    }

    private void setTitle() {
        headCenterTitleTv.setText("意见征求");
    }

    /**
     * token	否	string	token 有token传， 没有不传
     * post_id	是	int	文章id
     * page	是	int	页码
     */
    private void getData() {
        if (null == list) {
            list = new ArrayList<>();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_yjzq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HDLY_FEEDBACKDETAIL;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("post_id", postId + "");
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private void userCare(String userId, boolean isAddCare) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_yjzq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_USERCARER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("user_id", userId)
                .add("type", isAddCare ? "1" : "2")
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
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
     * token	是	string	token值
     * post_id	是	int	文章id
     * reply_content	时	string	留言内容
     */
    private void send() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_yjzq_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HDLY_REPLYFEEDBACK;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("post_id", postId + "")
                .add("reply_content", yjzqInputEdt.getText().toString())
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提交完毕清空输入框
                                yjzqInputEdt.setText("");
                                initData();
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

    @OnClick({R.id.head_left_iv, R.id.yjzq_dw_guanzhu_tv, R.id.yjzq_input_send_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
            case R.id.yjzq_dw_guanzhu_tv:
                break;
            case R.id.yjzq_input_send_tv:
                SoftInputUtil.hideSoftInput(YJZQActivity.this, yjzqInputSendTv);
                if (canSend()) {
                    send();
                }
                break;
        }
    }

    private boolean canSend() {
        if (TextUtils.isEmpty(yjzqInputEdt.getText().toString().trim())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入您的意见");
            return false;
        }

        if (!SystemUtil.getInstance().getIsLogin()) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
            return false;
        }

        return true;
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
            initData();
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
