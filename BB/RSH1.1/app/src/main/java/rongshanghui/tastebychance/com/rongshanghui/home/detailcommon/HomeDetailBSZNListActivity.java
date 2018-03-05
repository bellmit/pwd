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
import android.widget.LinearLayout;
import android.widget.ListView;
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
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailBSZNBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：HomeDetailListActivity 首页-信息-办事指南
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间：2017-12-6 09:19:19
 * 修改人：
 * 修改时间：2017-12-6 09:19:23
 * 修改备注：
 *
 * @version 1.0
 */
public class HomeDetailBSZNListActivity extends MyAppCompatActivity {

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
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.content_home_zi_xun)
    RelativeLayout contentHomeZiXun;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.home_detail_bszn_rootlayout)
    CoordinatorLayout homeDetailBsznRootlayout;


    private List<HomeDetailBSZNBean> list = new ArrayList<>();
    private CommonAdapter<HomeDetailBSZNBean> adapter;
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
                        case Constants.WHAT_GETDATA:
                            if (null != resInfo) {
                                if (((HomeDetailBSZNListActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(HomeDetailBSZNBean.class).size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "没有更多");
                                    return;
                                }
                                ((HomeDetailBSZNListActivity) t).list.addAll(resInfo.getDataList(HomeDetailBSZNBean.class));
                            }
                            ((HomeDetailBSZNListActivity) t).lv.setAdapter(((HomeDetailBSZNListActivity) t).adapter = new CommonAdapter<HomeDetailBSZNBean>(
                                    t.getApplicationContext(), ((HomeDetailBSZNListActivity) t).list, R.layout.item_detail_bszn
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final HomeDetailBSZNBean item) {
                                    viewHolder.setText(R.id.item_detail_bszn_title_tv, item.getModule().getName());
                                    TextView moreTv = viewHolder.getView(R.id.item_detail_bszn_more_tv);
                                    moreTv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            WeakReference<HomeDetailListBean> wf = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                                            wf.get().setType(Constants.PULISHTYPE_ZN);
                                            wf.get().setTitle(((HomeDetailBSZNListActivity) t).homeDetailListBean.getTitle());
                                            wf.get().setUserId(((HomeDetailBSZNListActivity) t).homeDetailListBean.getUserId());
                                            wf.get().setModuleId(item.getModule().getId());
                                            SystemUtil.getInstance().intentToHomeDetailListActivity(t, wf.get());
                                        }
                                    });


                                    LinearLayout ll1 = viewHolder.getView(R.id.item_detail_bszn_content_1_ll);
                                    LinearLayout ll2 = viewHolder.getView(R.id.item_detail_bszn_content_2_ll);
                                    if (item.getData().size() == 0) {
                                        ll1.setVisibility(View.GONE);
                                        ll2.setVisibility(View.GONE);
                                        return;
                                    } else if (item.getData().size() == 1) {
                                        ll2.setVisibility(View.GONE);
                                    }

                                    if (item.getData().size() >= 1) {
                                        viewHolder.setText(R.id.item_detail_bszn_content_title1_tv, item.getData().get(0).getPost_title());
                                        viewHolder.setText(R.id.item_detail_bszn_content_time1_tv, item.getData().get(0).getCreate_time());

                                        ll1.setOnClickListener(new NoDoubleClickListener() {
                                            @Override
                                            public void onNoDoubleClick(View v) {
                                                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                                wf.get().setUrl(item.getData().get(0).getDetail());
                                                wf.get().setTitle("详情");
                                                SystemUtil.getInstance().intentToWeb2Activity(t, wf.get());
                                            }
                                        });
                                    }

                                    if (item.getData().size() >= 2) {
                                        viewHolder.setText(R.id.item_detail_bszn_content_title2_tv, item.getData().get(1).getPost_title());
                                        viewHolder.setText(R.id.item_detail_bszn_content_time2_tv, item.getData().get(1).getCreate_time());

                                        ll2.setOnClickListener(new NoDoubleClickListener() {
                                            @Override
                                            public void onNoDoubleClick(View v) {
                                                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                                wf.get().setUrl(item.getData().get(1).getDetail());
                                                wf.get().setTitle("详情");
                                                SystemUtil.getInstance().intentToWeb2Activity(t, wf.get());
                                            }
                                        });
                                    }
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
        setContentView(R.layout.activity_home_banshizhinan);
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
    }

    private void setTitle() {
        if (null != headLeftIv) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(homeDetailListBean.getTitle());
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
    }

    /**
     * 主体信息-办事指南接口
     * token 	否 	string 	token
     * user_id 	是 	string 	主体对应用户id
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.home_detail_bszn_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_WORKGUIDE;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("user_id", homeDetailListBean.getUserId() + "");
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

    @Override
    protected void onResume() {
        super.onResume();
        if (null != homeDetailListBean) {
            if (null != list) {
                list.clear();
            }
            getData();
        }
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        HomeDetailBSZNListActivity.this.finish();
    }

}
