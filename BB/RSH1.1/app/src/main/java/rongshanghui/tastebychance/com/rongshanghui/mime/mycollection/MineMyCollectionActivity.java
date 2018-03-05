package rongshanghui.tastebychance.com.rongshanghui.mime.mycollection;

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
import rongshanghui.tastebychance.com.rongshanghui.mime.mycollection.bean.MyCollectionBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：MineMyCollectionActivity 我的-我的收藏
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/6 16:49
 * 修改人：
 * 修改时间：2017/12/6 16:49
 * 修改备注：
 *
 * @version 1.0
 */
public class MineMyCollectionActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.content_mine_my_collection)
    RelativeLayout contentMineMyCollection;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine_mycollection_rootlayout)
    CoordinatorLayout activityMineMycollectionRootlayout;

    private List<MyCollectionBean.DataBean> dataBean = new ArrayList<>();
    private CommonAdapter<MyCollectionBean.DataBean> adapter;
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
                    switch (msg.what) {
                        case Constants.WHAT_REFRESH:
                            ((MineMyCollectionActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((MineMyCollectionActivity) t).adapter.notifyDataSetChanged();
                            ((MineMyCollectionActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((MineMyCollectionActivity) t).pageIndex;
                            ((MineMyCollectionActivity) t).adapter.notifyDataSetChanged();
                            ((MineMyCollectionActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:

                            ResInfo resInfo = (ResInfo) msg.obj;

                            if (((MineMyCollectionActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(MyCollectionBean.class).getData().size() == 0) {
                                ToastUtils.showOneToast(t.getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((MineMyCollectionActivity) t).emptyLayout, false);
                                return;
                            }

                            if (((MineMyCollectionActivity) t).emptyLayout != null) {
                                if (((MineMyCollectionActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(MyCollectionBean.class).getData() == null || resInfo.getClass(MyCollectionBean.class).getData().size() == 0)) {
                                    emptyLayoutShowOrHide(((MineMyCollectionActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((MineMyCollectionActivity) t).emptyLayout, false);
                                }
                            }

                            ((MineMyCollectionActivity) t).dataBean.addAll(resInfo.getClass(MyCollectionBean.class).getData());

                            ((MineMyCollectionActivity) t).plv.setAdapter(((MineMyCollectionActivity) t).adapter = new CommonAdapter<MyCollectionBean.DataBean>(
                                    t.getApplicationContext(), ((MineMyCollectionActivity) t).dataBean, R.layout.item_rsnewslist
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final MyCollectionBean.DataBean item) {
                                    PicassoUtils.getinstance().loadNormalByPath(t, item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                                    viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                                    TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                                    tv.setText(item.getUnit_name());
                                    tv.setTextColor(t.getResources().getColor(R.color.font_blue));
                                }
                            });
                            ((MineMyCollectionActivity) t).plv.setPullLoadEnable(true);
                            ((MineMyCollectionActivity) t).plv.setXListViewListener(((MineMyCollectionActivity) t));

                            ((MineMyCollectionActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((MineMyCollectionActivity) t).dataBean.get(position - 1).getPost_id());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(((MineMyCollectionActivity) t).dataBean.get(position - 1).getDetail());
                                    wf.get().setIsCollect(((MineMyCollectionActivity) t).dataBean.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(((MineMyCollectionActivity) t).dataBean.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(((MineMyCollectionActivity) t).dataBean.get(position - 1).getPost_title());
                                    wf.get().setShareImgUrl(((MineMyCollectionActivity) t).dataBean.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(((MineMyCollectionActivity) t).dataBean.get(position - 1).getDetail());
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

    private EmptyLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_my_collection);
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
        initView();
    }

    private void initView() {
        if (null != plv) {
            plv.setPullRefreshEnable(true);
            plv.setPullLoadEnable(true);
            plv.setXListViewListener(this);
        }
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("我的收藏");
        }
    }

    private void onLoad() {
        plv.stopRefresh();
        plv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        plv.setRefreshTime(date);
    }

    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_mycollection_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_MINE_COLLECT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
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
    protected void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBean) {
            dataBean.clear();
        }
        getData();
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBean) {
            dataBean.clear();
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
        MineMyCollectionActivity.this.finish();
    }
}
