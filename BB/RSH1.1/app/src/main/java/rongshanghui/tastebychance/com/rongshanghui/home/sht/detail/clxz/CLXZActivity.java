package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clxz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
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
import android.widget.AdapterView;
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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.SHTMineRes;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb.SBCKListActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager.DownloadCKActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager.bean.DownloadCKBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCKBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.FileUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

public class CLXZActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {

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
                            ((CLXZActivity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((CLXZActivity) t).adapter.notifyDataSetChanged();
                            ((CLXZActivity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((CLXZActivity) t).pageIndex;
                            ((CLXZActivity) t).adapter.notifyDataSetChanged();
                            ((CLXZActivity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:

                            if (((CLXZActivity) t).emptyLayout != null) {
                                if (((CLXZActivity) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(DownloadCKBean.class).getData() == null || resInfo.getClass(DownloadCKBean.class).getData().size() == 0)) {
                                    emptyLayoutShowOrHide(((CLXZActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((CLXZActivity) t).emptyLayout, false);
                                }
                            }

                            if (null != resInfo) {
                                if (((CLXZActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZNCKBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(((CLXZActivity) t).getApplicationContext(), "????????????");
                                    emptyLayoutShowOrHide(((CLXZActivity) t).emptyLayout, false);
                                    return;
                                }
                                ((CLXZActivity) t).list.addAll(resInfo.getClass(DownloadCKBean.class).getData());
                            }

                            ((CLXZActivity) t).plv.setAdapter(((CLXZActivity) t).adapter = new CommonAdapter<DownloadCKBean.DataBean>(
                                    ((CLXZActivity) t).getApplicationContext(), ((CLXZActivity) t).list, R.layout.item_sht_clxz
                            ) {
                                @Override
                                protected void convert(final ViewHolder viewHolder, final DownloadCKBean.DataBean item) {
                                    viewHolder.setText(R.id.item_sht_clxz_title_tv, item.getPost_title());
                                    TextView timeTv = viewHolder.getView(R.id.item_sht_clxz_time_tv);
                                    timeTv.setTextColor(((CLXZActivity) t).getResources().getColor(R.color.gray));
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
                                            DialogUtils.getInstance().AlertMessage(t, null, "????????????????????????"
                                                    , "??????", "??????", null, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            String url = item.getAccessory();
                                                            String destFileDir = "";

                                                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                                                File mDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "?????????????????????");
                                                                Log.v(Constants.TAG, mDirectory.toString());
                                                                if (!mDirectory.exists()) {
                                                                    mDirectory.mkdir();
                                                                }

                                                                destFileDir = mDirectory.getAbsolutePath();
                                                            }

                                                            final String finalDestFileDir = destFileDir;
                                                            OkGo.get(url)//
                                                                    .tag(this)//
                                                                    .execute(new FileCallback(finalDestFileDir, item.getPost_title()) {  //???????????????????????????????????????????????????????????????
                                                                        @Override
                                                                        public void onSuccess(File file, Call call, Response response) {
                                                                            // file ????????????????????????????????????????????????
                                                                            ToastUtils.showOneToast(t.getApplicationContext(), "???????????????:" + finalDestFileDir + item.getPost_title());
                                                                            if (null != progressTv) {
                                                                                progressTv.setVisibility(View.VISIBLE);
                                                                                progressTv.setTextColor(t.getResources().getColor(R.color.gray));
                                                                                progressTv.setText("?????????");
                                                                            }
                                                                            if (null != progressBar) {
                                                                                progressBar.setVisibility(View.GONE);
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                                                            //????????????????????????(?????????????????????,??????????????????ui)
                                                                            //currentSize totalSize?????????byte?????????
                                                                            System.out.println("currentSize = " + currentSize);
                                                                            if (null != progressTv) {
                                                                                progressTv.setVisibility(View.VISIBLE);
                                                                                progressTv.setText("?????????" + (currentSize * 100 / totalSize) + "%");
                                                                                progressTv.setTextColor(t.getResources().getColor(R.color.gray));
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
            headCenterTitleTv.setText("????????????");
        }
    }

    /**
     * ??????/??????/??????-??????????????????
     * token 	??? 	string 	token??? ?????????????????????????????????????????????????????????token
     * user_id 	??? 	int 	???????????????????????????????????????????????? ??? ??????????????????id
     * page 	??? 	int 	??????
     * keyword 	??? 	string 	?????????????????????
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_downloadmanagerck_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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

                        if (emptyLayout != null) {
                            emptyLayout.showEmpty(Constants.EMPTY_NULL_PIC, Constants.EMPTY_NULL_STR);
                        }
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
        CLXZActivity.this.finish();
    }
}