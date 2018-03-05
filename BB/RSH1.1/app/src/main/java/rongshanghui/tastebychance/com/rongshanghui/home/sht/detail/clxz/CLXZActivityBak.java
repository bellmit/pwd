package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clxz;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager.bean.DownloadCKBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCKBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

public class CLXZActivityBak extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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

    private HomeDetailListBean homeDetailListBean;
    private List<DownloadCKBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<DownloadCKBean.DataBean> adapter;
    private int pageIndex = Constants.PAGE_START_INDEX;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                ResInfo resInfo = (ResInfo) msg.obj;
                switch (msg.what) {
                    case Constants.WHAT_REFRESH:
                        pageIndex = Constants.PAGE_START_INDEX;
                        adapter.notifyDataSetChanged();
                        onLoad();
                        break;
                    case Constants.WHAT_LOADMORE:
                        ++pageIndex;
                        adapter.notifyDataSetChanged();
                        onLoad();
                        break;
                    case Constants.WHAT_GETDATA:
                        if (null != resInfo) {
                            if (pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZNCKBean.class).getData().size() == 0) {
                                ToastUtils.showOneToast(getApplicationContext(), "没有更多");
                                return;
                            }
                            list.addAll(resInfo.getClass(DownloadCKBean.class).getData());
                        }
                        plv.setAdapter(adapter = new CommonAdapter<DownloadCKBean.DataBean>(
                                getApplicationContext(), list, R.layout.item_sht_clxz
                        ) {
                            @Override
                            protected void convert(final ViewHolder viewHolder, final DownloadCKBean.DataBean item) {
                                viewHolder.setText(R.id.item_sht_clxz_title_tv, item.getPost_title());
                                TextView timeTv = viewHolder.getView(R.id.item_sht_clxz_time_tv);
                                timeTv.setTextColor(getResources().getColor(R.color.gray));
                                timeTv.setText(item.getCreate_time());

                                final TextView progressTv = viewHolder.getView(R.id.item_sht_clxz_progressvalue_tv);
                                final ProgressBar progressBar = viewHolder.getView(R.id.item_sht_clxz_progress);

                                if (null != progressTv) {
                                    progressTv.setVisibility(View.GONE);
                                }
                                if (null != progressBar) {
                                    progressBar.setVisibility(View.GONE);
                                }

                                RelativeLayout rl = viewHolder.getView(R.id.item_sht_clxz_rl);
                                rl.setOnClickListener(new NoDoubleClickListener() {
                                    @Override
                                    public void onNoDoubleClick(View v) {
                                        DialogUtils.getInstance().AlertMessage(CLXZActivityBak.this, null, "确定下载该文档？"
                                                , "返回", "确定", null, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        String url = item.getAccessory();
                                                        String destFileDir = "";

                                                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                                            File mDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "融商汇资料下载");
                                                            Log.v(Constants.TAG, mDirectory.toString());
                                                            if (!mDirectory.exists()) {
                                                                mDirectory.mkdir();
                                                            }

                                                            destFileDir = mDirectory.getAbsolutePath();
                                                        }

                                                        final String finalDestFileDir = destFileDir;
                                                        OkGo.get(url)//
                                                                .tag(this)//
                                                                .execute(new FileCallback(finalDestFileDir, item.getPost_title()) {  //文件下载时，可以指定下载的文件目录和文件名
                                                                    @Override
                                                                    public void onSuccess(File file, Call call, Response response) {
                                                                        // file 即为文件数据，文件保存在指定目录
                                                                        ToastUtils.showOneToast(getApplicationContext(), "文件保存在:" + finalDestFileDir + item.getPost_title());
                                                                        if (null != progressTv) {
                                                                            progressTv.setVisibility(View.VISIBLE);
                                                                            progressTv.setTextColor(CLXZActivityBak.this.getResources().getColor(R.color.gray));
                                                                            progressTv.setText("已下载");
                                                                        }
                                                                        if (null != progressBar) {
                                                                            progressBar.setVisibility(View.GONE);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                                                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                                                                        //currentSize totalSize以字节byte为单位
                                                                        System.out.println("currentSize = " + currentSize);
                                                                        if (null != progressTv) {
                                                                            progressTv.setVisibility(View.VISIBLE);
                                                                            progressTv.setText("下载中" + (currentSize * 100 / totalSize) + "%");
                                                                            progressTv.setTextColor(CLXZActivityBak.this.getResources().getColor(R.color.gray));
                                                                        }
                                                                        if (null != progressBar) {
                                                                            progressBar.setVisibility(View.VISIBLE);
                                                                            progressBar.setProgress((int) (100 * progress));
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
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
    };

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

        if (null != getIntent()) {
            homeDetailListBean = (HomeDetailListBean) getIntent().getSerializableExtra("homeDetailListBean");
        }

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
            headCenterTitleTv.setText("材料下载");
        }
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
        if (null != SystemUtil.getInstance().getUserInfo()) {
            builder.add("user_id", homeDetailListBean.getUserId() + "");
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
        if (null != homeDetailListBean) {
            getData();
        }
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != list) {
            list.clear();
        }
        if (null != homeDetailListBean) {
            getData();
        }
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        if (null != homeDetailListBean) {
            getData();
        }
        onLoad();
    }


    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        CLXZActivityBak.this.finish();
    }
}