package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.sbmanager;

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
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.sbmanager.bean.SBManagerBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：SBManagerActivity 我的-单位管理-上报管理
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/18 9:59
 * 修改人：
 * 修改时间：2017/12/18 9:59
 * 修改备注：
 *
 * @version 1.0
 */
public class SBManagerActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
    @BindView(R.id.content_sbmanager)
    RelativeLayout contentSbmanager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_sbmanager_rootlayout)
    CoordinatorLayout activitySbmanagerRootlayout;

    private List<SBManagerBean> list = new ArrayList<>();
    private CommonAdapter<SBManagerBean> adapter;
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
                            ((SBManagerActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((SBManagerActivity) t).adapter.notifyDataSetChanged();
                            ((SBManagerActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((SBManagerActivity) t).pageIndex;
                            ((SBManagerActivity) t).adapter.notifyDataSetChanged();
                            ((SBManagerActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            if (null == resInfo) {
                                return;
                            }
                            if (((SBManagerActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(SBManagerBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getApplicationContext(), "没有更多");
                                return;
                            }

                            ((SBManagerActivity) t).list.addAll(resInfo.getDataList(SBManagerBean.class));
                            ((SBManagerActivity) t).plv.setAdapter(((SBManagerActivity) t).adapter = new CommonAdapter<SBManagerBean>(
                                    ((SBManagerActivity) t).getApplicationContext(), ((SBManagerActivity) t).list, R.layout.item_sbmanager
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, SBManagerBean item) {
                                    TextView titleTv = viewHolder.getView(R.id.item_sbmanager_title_tv);
                                    titleTv.setText(item.getPost_title());

                                    TextView statusTv = viewHolder.getView(R.id.item_sbmanager_status_tv);

                                    if (item.getIs_read() == 1) {//is_read 	int 	0 未查看 1 已查看
                                        titleTv.setTextColor(t.getResources().getColor(R.color.gray));
                                        statusTv.setText("[已查看]");
                                        statusTv.setTextColor(t.getResources().getColor(R.color.gray));
                                    } else {
                                        titleTv.setTextColor(t.getResources().getColor(R.color.text_black));
                                        statusTv.setText("[未查看]");
                                        statusTv.setTextColor(t.getResources().getColor(R.color.font_blue));
                                    }
                                }
                            });

                            ((SBManagerActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //调用查看接口
                                    ((SBManagerActivity) t).seen(position - 1);

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
        setContentView(R.layout.activity_sbmanager);
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

        plv.setXListViewListener(this);
        plv.setPullLoadEnable(false);
        plv.setPullRefreshEnable(false);

        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("上报管理");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
    }

    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_sbmanager_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SUBMITLIST;
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * id 	是 	int 	查看的文章对应id
     * token 	是 	string 	token值
     *
     * @param positionId
     */
    private void seen(final int positionId) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_sbmanager_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SEEN;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("id", list.get(positionId).getId() + "");
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
                                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                wf.get().setId(list.get(positionId).getId());
                                wf.get().setTitle("查看");
                                wf.get().setUrl(list.get(positionId).getDetail());
                                SystemUtil.getInstance().intentToWeb2Activity(SBManagerActivity.this, wf.get());
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

    @Override
    protected void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != list) {
            list.clear();
        }
        getData();
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        SBManagerActivity.this.finish();
    }
}
