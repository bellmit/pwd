package rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.mycare.MineMyCareActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageDownLoad;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyGridView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.FWDTMoreGZActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.bean.FWDTDWBean;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.bean.FWDTGZBean;

/**
 * 服务大厅
 *
 * @author shenbh
 * @time 2018/1/15 14:18
 */
public class FWDTActivity extends MyAppCompatActivity {

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
    @BindView(R.id.title_guanzhu_tv)
    TextView titleGuanzhuTv;
    @BindView(R.id.guanzhu_gridview)
    MyGridView guanzhuGridview;
    @BindView(R.id.title_rzdw_tv)
    TextView titleRzdwTv;
    @BindView(R.id.rzdw_gridview)
    MyGridView rzdwGridview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.title_guanzhu_more_tv)
    TextView titleGuanzhuMoreTv;
    @BindView(R.id.title_guanzhu_more_iv)
    ImageView titleGuanzhuMoreIv;
    @BindView(R.id.title_rzdw_more_tv)
    TextView titleRzdwMoreTv;
    @BindView(R.id.activity_fwdt_rootlayout)
    CoordinatorLayout activityFwdtRootlayout;

    private List<FWDTGZBean.DataBean> guanzhus;
    private List<FWDTDWBean.DataBean> ruzhudanweis;

    private CommonAdapter<FWDTGZBean.DataBean> guanzhuAdapter;
    private CommonAdapter<FWDTDWBean.DataBean> ruzhudanweiAdapter;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mActivity;

        public MyHandler(T t) {
            this.mActivity = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mActivity.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            if (null == ((FWDTActivity) t).guanzhus) {
                                ((FWDTActivity) t).guanzhus = new ArrayList<FWDTGZBean.DataBean>();
                            }
                            ((FWDTActivity) t).guanzhus = resInfo.getDataList(FWDTGZBean.DataBean.class);
                            if (((FWDTActivity) t).guanzhus.size() > 9) {
                                for (int i = 9; i < ((FWDTActivity) t).guanzhus.size(); i++) {
                                    ((FWDTActivity) t).guanzhus.remove(i);
                                    --i;
                                }
                            }
                            ((FWDTActivity) t).guanzhuGridview.setAdapter(((FWDTActivity) t).guanzhuAdapter = new CommonAdapter<FWDTGZBean.DataBean>(
                                    ((FWDTActivity) t).getApplicationContext(), ((FWDTActivity) t).guanzhus, R.layout.item_fwdt_gridview
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, FWDTGZBean.DataBean item) {
                                    ImageView icon = viewHolder.getView(R.id.item_fwdt_gridview_iv);
                                    viewHolder.setText(R.id.item_fwdt_gridview_tv, item.getUnit_name());
                                    PicassoUtils.getinstance().LoadImageWithWidtAndHeight(t, item.getAvatar(), icon, -1, -1, PicassoUtils.PICASSO_BITMAP_SHOW_ROUND_TYPE, 10);
                                }
                            });

                            ((FWDTActivity) t).guanzhuGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                    wf.get().setId(Integer.valueOf(((FWDTActivity) t).guanzhus.get(position).getUser_id()));
                                    wf.get().setTitle(((FWDTActivity) t).guanzhus.get(position).getUnit_name());
                                    wf.get().setIsCared(1);//是否已关注 0 ： 未关注 1 ：已关注
                                    wf.get().setToType(((FWDTActivity) t).guanzhus.get(position).getSubject_type());
                                    SystemUtil.getInstance().intentToDetail(t, wf.get());
                                }
                            });
                            break;
                        case Constants.DOMESTIC_WHAT:
                            if (((FWDTActivity) t).ruzhudanweis == null) {
                                ((FWDTActivity) t).ruzhudanweis = new ArrayList<>();
                            }
                            ((FWDTActivity) t).ruzhudanweis = resInfo.getDataList(FWDTDWBean.DataBean.class);
                            ((FWDTActivity) t).rzdwGridview.setAdapter(((FWDTActivity) t).ruzhudanweiAdapter = new CommonAdapter<FWDTDWBean.DataBean>(
                                    ((FWDTActivity) t).getApplicationContext(), ((FWDTActivity) t).ruzhudanweis, R.layout.item_fwdt_gridview
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, FWDTDWBean.DataBean item) {
                                    ImageView icon = viewHolder.getView(R.id.item_fwdt_gridview_iv);
                                    viewHolder.setText(R.id.item_fwdt_gridview_tv, item.getUnit_name());

                                    PicassoUtils.getinstance().LoadImageWithWidtAndHeight(t, item.getAvatar(), icon, -1, -1, PicassoUtils.PICASSO_BITMAP_SHOW_ROUND_TYPE, 10);
                                }
                            });

                            ((FWDTActivity) t).rzdwGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (((FWDTActivity) t).ruzhudanweis.get(position).getSubject_type() == 0) {
                                        ToastUtils.showOneToast(t, "该单位暂未入驻");
                                        return;
                                    } else {
                                        //跳转到镇街信息
                                        WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                        wf.get().setId(((FWDTActivity) t).ruzhudanweis.get(position).getUser_id());
                                        wf.get().setIsCared(((FWDTActivity) t).ruzhudanweis.get(position).getIs_cared());
                                        wf.get().setToType(((FWDTActivity) t).ruzhudanweis.get(position).getSubject_type());
                                        SystemUtil.getInstance().intentToDetail(t, wf.get());
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
        setContentView(R.layout.activity_fwdt);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        getGuanzhu();
        getRZDW();
    }

    private void getRZDW() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(this.findViewById(R.id.activity_fwdt_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_FWDT_DEPAETLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody formBody = builder.build();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
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
                                msg.what = Constants.DOMESTIC_WHAT;
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

    private void getGuanzhu() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_fwdt_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_FWDT_DEPARTCARED;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("type", "1") //1 : 最新9条 2：列表
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private void setTitle() {
        headCenterTitleTv.setText("服务大厅");
    }

    @OnClick({R.id.head_left_iv, R.id.title_guanzhu_more_tv, R.id.title_guanzhu_more_iv, R.id.title_rzdw_more_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
            case R.id.title_guanzhu_more_tv:
            case R.id.title_guanzhu_more_iv:
                startActivity(new Intent(FWDTActivity.this, FWDTMoreGZActivity.class));
                break;
            case R.id.title_rzdw_more_tv:
//                startActivity(new Intent(FWDTActivity.this, FWDTGZListActivity.class));
                break;
        }
    }
}
