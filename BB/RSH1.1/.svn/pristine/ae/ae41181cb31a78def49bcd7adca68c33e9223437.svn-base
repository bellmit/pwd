package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan;

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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCKBean;
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
 * 类描述：ZNCKActivity 我的-指南管理-指南查看
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/6 21:04
 * 修改人：
 * 修改时间：2017/12/6 21:04
 * 修改备注：
 *
 * @version 1.0
 */
public class ZNCKActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.activity_znck_rootlayout)
    CoordinatorLayout activityZnckRootlayout;

    private List<ZNCKBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<ZNCKBean.DataBean> adapter;
    private int pageIndex = Constants.PAGE_START_INDEX;
    private final int LOAD_DATA_WHAT = 20;

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
                            ((ZNCKActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ZNCKActivity) t).adapter.notifyDataSetChanged();
                            ((ZNCKActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ZNCKActivity) t).pageIndex;
                            ((ZNCKActivity) t).adapter.notifyDataSetChanged();
                            ((ZNCKActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            if (null != resInfo) {
                                if (((ZNCKActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZNCKBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "没有更多");
                                    return;
                                }
                                ((ZNCKActivity) t).list.addAll(resInfo.getClass(ZNCKBean.class).getData());
                            }
                            ((ZNCKActivity) t).plv.setAdapter(((ZNCKActivity) t).adapter = new CommonAdapter<ZNCKBean.DataBean>(
                                    t.getApplicationContext(), ((ZNCKActivity) t).list, R.layout.item_znck_deleteable
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final ZNCKBean.DataBean item) {
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
                                                            ((ZNCKActivity) t).delete(item.getId());
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                            ((ZNCKActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((ZNCKActivity) t).list.get(position - 1).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(((ZNCKActivity) t).list.get(position - 1).getDetail());
                                    //                            wf.get().setIsCollect(list.get(position - 1).getIs_collect());
                                    //                            wf.get().setIsLike(list.get(position - 1).getIs_like());
                                    SystemUtil.getInstance().intentToWeb2Activity(t, wf.get());
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
        setContentView(R.layout.activity_znck);
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
            headCenterTitleTv.setText("指南查看");
        }
    }

    private void onLoad() {
        plv.stopRefresh();
        plv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        plv.setRefreshTime(date);
    }

    /**
     * keyword 	否 	string 	关键词
     * user_id 	是 	int 	所查看的主体用户id
     */
    private void getData() {
        LoginRes.UserInfoBean userInfoBean = SystemUtil.getInstance().getUserInfo();
        if (null == userInfoBean) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
            return;
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_znck_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_GUIDESEARCH;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", userInfoBean.getId() + "");
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

    /**
     * 删除文章
     * token 	是 	string 	token值
     * post_id 	是 	int 	要删除的文章id
     */
    private void delete(int postId) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_znck_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
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

    @Override
    protected void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
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
        ZNCKActivity.this.finish();
    }
}
