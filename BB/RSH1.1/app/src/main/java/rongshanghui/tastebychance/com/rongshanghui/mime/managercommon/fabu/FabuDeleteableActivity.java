package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.fabu;

import android.app.Activity;
import android.content.DialogInterface;
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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * ????????????FabuDeleteableActivity ??????-??????-??????????????????-??????-?????????;??????-??????-???????????????????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/12/6 17:30
 * ????????????
 * ???????????????2017/12/6 17:30
 * ???????????????
 *
 * @version 1.0
 */
public class FabuDeleteableActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightnIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.plv)
    PullRefreshPushAddListView plv;
    @BindView(R.id.content_fabu_deleteable)
    RelativeLayout contentFabuDeleteable;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_fabu_deleteable_rootlayout)
    CoordinatorLayout activityFabuDeleteableRootlayout;

    private List<HomeZixunBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<HomeZixunBean.DataBean> adapter;
    private ToDetailBean toDetailBean;
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
                        case Constants.WHAT_REFRESH:
                            ((FabuDeleteableActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((FabuDeleteableActivity) t).adapter.notifyDataSetChanged();
                            ((FabuDeleteableActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((FabuDeleteableActivity) t).pageIndex;
                            ((FabuDeleteableActivity) t).adapter.notifyDataSetChanged();
                            ((FabuDeleteableActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            if (null == resInfo) {
                                return;
                            }

                            if (((FabuDeleteableActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(HomeZixunBean.class).getData().size() == 0) {
                                ToastUtils.showOneToast(t.getApplicationContext(), "????????????");
                                emptyLayoutShowOrHide(((FabuDeleteableActivity) t).emptyLayout, false);
                                return;
                            }
                            if (((FabuDeleteableActivity) t).emptyLayout != null) {
                                if (((FabuDeleteableActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(HomeZixunBean.class).getData() == null || resInfo.getClass(HomeZixunBean.class).getData().size() == 0)) {
                                    emptyLayoutShowOrHide(((FabuDeleteableActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((FabuDeleteableActivity) t).emptyLayout, false);
                                }
                            }

                            ((FabuDeleteableActivity) t).list.addAll(resInfo.getClass(HomeZixunBean.class).getData());

                            ((FabuDeleteableActivity) t).plv.setAdapter(((FabuDeleteableActivity) t).adapter = new CommonAdapter<HomeZixunBean.DataBean>(
                                    t.getApplicationContext(), ((FabuDeleteableActivity) t).list, R.layout.item_rsnewslist_deleteable
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final HomeZixunBean.DataBean item) {
                                    PicassoUtils.getinstance().loadNormalByPath(t, item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                                    viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                                    TextView tv = viewHolder.getView(R.id.item_znck_delete_iv);
                                    tv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //????????????
                                            if (StringUtil.isEmpty(SystemUtil.getInstance().getToken())) {
                                                ToastUtils.showOneToast(t.getApplicationContext(), "????????????," + Constants.LOGIN_INVALID);
                                                return;
                                            }

                                            DialogUtils.getInstance().AlertMessage(t, "", "????????????/n" + item.getPost_title(), "??????", "??????", null, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ((FabuDeleteableActivity) t).delete(item.getId());
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            ((FabuDeleteableActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((FabuDeleteableActivity) t).list.get(position - 1).getId());
                                    wf.get().setTitle("??????");
                                    wf.get().setUrl(((FabuDeleteableActivity) t).list.get(position - 1).getDetail());
                                    wf.get().setIsCollect(((FabuDeleteableActivity) t).list.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(((FabuDeleteableActivity) t).list.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(((FabuDeleteableActivity) t).list.get(position - 1).getPost_title());
                                    wf.get().setShareImgUrl(((FabuDeleteableActivity) t).list.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(((FabuDeleteableActivity) t).list.get(position - 1).getDetail());
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
        setContentView(R.layout.activity_fabu_deleteable);
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

        if (null != getIntent()) {
            toDetailBean = (ToDetailBean) getIntent().getSerializableExtra("toDetailBean");
        }
        plv.setXListViewListener(this);
        plv.setPullLoadEnable(true);

        if (null != toDetailBean) {
            setTitle();
        }
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(StringUtil.isNotEmpty(toDetailBean.getTitle()) ? toDetailBean.getTitle() : "??????");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("??????");
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
            loadingBar = LoadingBar.make(findViewById(R.id.activity_fabu_deleteable_rootlayout), new CustomLoadingFactory());
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
        builder.add("publish_type", SystemUtil.getInstance().castPublishType(toDetailBean.getPublishType()) + "");
        builder.add("page", pageIndex + "");
        if (null != SystemUtil.getInstance().getUserInfo() && SystemUtil.getInstance().getUserInfo().getId() != 0) {
            builder.add("user_id", SystemUtil.getInstance().getUserInfo().getId() + "");
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


                        if (emptyLayout != null) {
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    /**
     * ????????????
     * token 	??? 	string 	token???
     * post_id 	??? 	int 	??????????????????id
     */
    private void delete(int postId) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_fabu_deleteable_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//??????okhttp3?????????????????????
        final String url = UrlManager.URL_ARTICLEDELETE;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("post_id", postId + "");
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
                                msg.what = INFO_WHAT;
                                msg.obj = "????????????";
                                myHandler.sendMessage(msg);

                                //????????????
                                if (null != list) {
                                    list.clear();
                                }
                                getData();
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
    protected void onResume() {
        super.onResume();
        if (null != toDetailBean) {
            if (null != list) {
                list.clear();
            }
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                FabuDeleteableActivity.this.finish();
                break;
            case R.id.head_right_tv:
                //??????
                if (null != toDetailBean) {
                    Intent intent = new Intent(FabuDeleteableActivity.this, FabuActivity.class);
                    intent.putExtra("toDetailBean", toDetailBean);
                    startActivity(intent);
                }
                break;
        }
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
