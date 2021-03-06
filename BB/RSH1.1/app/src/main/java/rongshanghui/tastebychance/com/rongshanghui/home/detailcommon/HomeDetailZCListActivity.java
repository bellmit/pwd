package rongshanghui.tastebychance.com.rongshanghui.home.detailcommon;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * ????????????HomeDetailListActivity ??????-?????????-??????-??????-??????
 * ?????????: shenbh Email:shenbh@qq.com
 * ???????????????2017-12-5 19:22:27
 * ????????????
 * ???????????????2017-12-5 19:22:32
 * ???????????????
 *
 * @version 1.0
 */
public class HomeDetailZCListActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.content_home_zi_xun)
    RelativeLayout contentHomeZiXun;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.home_detail_zixun_rootlayout)
    CoordinatorLayout homeDetailZixunRootlayout;

    private List<HomeZixunBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<HomeZixunBean.DataBean> adapter;
    private HomeDetailListBean homeDetailListBean;
    private int pageIndex = Constants.PAGE_START_INDEX;

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
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_REFRESH:
                            ((HomeDetailZCListActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((HomeDetailZCListActivity) t).adapter.notifyDataSetChanged();
                            ((HomeDetailZCListActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((HomeDetailZCListActivity) t).pageIndex;
                            ((HomeDetailZCListActivity) t).adapter.notifyDataSetChanged();
                            ((HomeDetailZCListActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            if (null != resInfo) {
                                if (((HomeDetailZCListActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(HomeZixunBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "????????????");
                                    return;
                                }
                                ((HomeDetailZCListActivity) t).list.addAll(resInfo.getClass(HomeZixunBean.class).getData());
                            }
                            ((HomeDetailZCListActivity) t).plv.setAdapter(((HomeDetailZCListActivity) t).adapter = new CommonAdapter<HomeZixunBean.DataBean>(
                                    t.getApplicationContext(), ((HomeDetailZCListActivity) t).list, R.layout.item_sht_bm_zc
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, HomeZixunBean.DataBean item) {
                                    viewHolder.setText(R.id.item_sht_clxz_title_tv, item.getPost_title());
                                    TextView timeTv = viewHolder.getView(R.id.item_sht_clxz_time_tv);
                                    timeTv.setText(item.getCreate_time());
                                    timeTv.setTextColor(t.getResources().getColor(R.color.gray));
                                }
                            });
                            ((HomeDetailZCListActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((HomeDetailZCListActivity) t).list.get(position - 1).getId());
                                    wf.get().setTitle("??????");
                                    wf.get().setUrl(((HomeDetailZCListActivity) t).list.get(position - 1).getDetail());
                                    wf.get().setIsCollect(((HomeDetailZCListActivity) t).list.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(((HomeDetailZCListActivity) t).list.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(((HomeDetailZCListActivity) t).list.get(position - 1).getPost_title());
                                    wf.get().setShareImgUrl(((HomeDetailZCListActivity) t).list.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(((HomeDetailZCListActivity) t).list.get(position - 1).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(t, wf.get());
                                }
                            });
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_zi_xun);
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
            homeDetailListBean = (HomeDetailListBean) getIntent().getSerializableExtra("homeDetailListBean");
        }

        plv.setXListViewListener(this);
        plv.setPullLoadEnable(true);

        setTitle();
    }

    private void setTitle() {
        if (null != headLeftIv) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(homeDetailListBean.getType());
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
    }

    /**
     * publish_type 	??? 	int 	0:??????????????????;1:????????????;2:????????????;3:??????;4:??????;5:??????;6:?????????;7:?????????;8:????????????;9:????????????,10:?????????
     * token 	??? 	string 	token???
     * user_id 	??? 	int 	?????????????????????id
     * page 	??? 	int 	??????
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.home_detail_zixun_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//??????okhttp3?????????????????????
        final String url = UrlManager.URL_POSTLISTBYCATE;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("publish_type", SystemUtil.getInstance().castPostListByCate(homeDetailListBean.getType()) + "");
        builder.add("page", pageIndex + "");
        if (homeDetailListBean.getUserId() != 0) {
            builder.add("user_id", homeDetailListBean.getUserId() + "");
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    private void onLoad() {
        plv.stopRefresh();
        plv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy???MM???dd??? HH???mm???").format(new Date());
        plv.setRefreshTime(date);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != homeDetailListBean) {
            getData();
        }
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        HomeDetailZCListActivity.this.finish();
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
