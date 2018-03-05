package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager;

import android.app.Activity;
import android.content.DialogInterface;
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
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager.bean.DownloadCKBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.ZNCKActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCKBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：DownloadCKActivity 我的-部门、镇街、机构-下载上传-查看
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/18 11:30
 * 修改人：
 * 修改时间：2017/12/18 11:30
 * 修改备注：
 *
 * @version 1.0
 */
public class DownloadCKActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.content_znck)
    RelativeLayout contentZnck;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_downloadmanagerck_rootlayout)
    CoordinatorLayout activityDownloadmanagerckRootlayout;

    private List<DownloadCKBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<DownloadCKBean.DataBean> adapter;
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
                            ((DownloadCKActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((DownloadCKActivity) t).adapter.notifyDataSetChanged();
                            ((DownloadCKActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((DownloadCKActivity) t).pageIndex;
                            ((DownloadCKActivity) t).adapter.notifyDataSetChanged();
                            ((DownloadCKActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            if (((DownloadCKActivity) t).emptyLayout != null) {
                                if (((DownloadCKActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(DownloadCKBean.class).getData() == null || resInfo.getClass(DownloadCKBean.class).getData().size() == 0)) {
                                    emptyLayoutShowOrHide(((DownloadCKActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((DownloadCKActivity) t).emptyLayout, false);
                                }
                            }

                            if (null != resInfo) {
                                if (((DownloadCKActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZNCKBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "没有更多");
                                    return;
                                }
                                ((DownloadCKActivity) t).list.addAll(resInfo.getClass(DownloadCKBean.class).getData());
                            }
                            ((DownloadCKActivity) t).plv.setAdapter(((DownloadCKActivity) t).adapter = new CommonAdapter<DownloadCKBean.DataBean>(
                                    ((DownloadCKActivity) t).getApplicationContext(), ((DownloadCKActivity) t).list, R.layout.item_znck_deleteable
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final DownloadCKBean.DataBean item) {
                                    viewHolder.setText(R.id.item_znck_title_tv, item.getPost_title());
                                    ImageView iv = viewHolder.getView(R.id.item_znck_delete_iv);
                                    iv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //删除文章
                                            if (StringUtil.isEmpty(SystemUtil.getInstance().getToken())) {
                                                ToastUtils.showOneToast(t.getApplicationContext(), "删除失败," + Constants.LOGIN_INVALID);
                                                return;
                                            }

                                            DialogUtils.getInstance().AlertMessage(t, "", "确定移除/n" + item.getPost_title(), "取消", "确定", null,
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            ((DownloadCKActivity) t).delete(item.getId());
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                        /*plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                wf.get().setId(list.get(position - 1).getId());
                                wf.get().setTitle("信息");
                                wf.get().setUrl(list.get(position - 1).getDetail());
                                //                            wf.get().setIsCollect(list.get(position - 1).getIs_collect());
                                //                            wf.get().setIsLike(list.get(position - 1).getIs_like());
                                SystemUtil.getInstance().intentToWeb2Activity(DownloadCKActivity.this, wf.get());
                            }
                        });*/
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emptyLayoutShowOrHide(((DownloadCKActivity) t).emptyLayout, false);
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private EmptyLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_ck);
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
            headCenterTitleTv.setText("下载查看");
        }
    }

    /**
     * 部门/机构/镇街-下载资料删除
     * token 	是 	string 	token
     * id 	是 	int 	文章id
     */
    private void delete(int postId) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_downloadmanagerck_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_DELETEDOWN;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("id", postId + "");
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
                                msg.obj = "删除成功";
                                myHandler.sendMessage(msg);

                                //刷新页面
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
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }


    /**
     * 部门/机构/镇街-下载列表获取
     * token 	否 	string 	token值 当用户自己在主体管理中查看下载列表时传token
     * user_id 	否 	int 	当其他用户在主体也查看下载列表时 传 主体对应用户id
     * page 	是 	int 	页码
     * keyword 	否 	string 	有需要搜索时传
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_downloadmanagerck_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_DOWNLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("page", pageIndex + "");
//        builder.add("keyword", keyword);
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

    private void onLoad() {
        plv.stopRefresh();
        plv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        plv.setRefreshTime(date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != list) {
            list.clear();
        }
        getData();
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
        DownloadCKActivity.this.finish();
    }
}
