package rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt;

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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.mycare.bean.MyCareBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.RoundImageView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.bean.FWDTGZBean;

/**
 * ????????????MineMyCareActivity ??????-????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/12/6 16:44
 * ????????????
 * ???????????????2017/12/6 16:44
 * ???????????????
 *
 * @version 1.0
 */
public class FWDTMoreGZActivity extends MyAppCompatActivity {

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
    @BindView(R.id.content_mine_my_care)
    RelativeLayout contentMineMyCare;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine_mycare_rootlayout)
    CoordinatorLayout activityMineMycareRootlayout;

    private CommonAdapter<FWDTGZBean.DataBean> adapter;
    private List<FWDTGZBean.DataBean> list = new ArrayList<>();

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
                        case Constants.WHAT_GETDATA:
                            if (null == resInfo) {
                                return;
                            }
                            if (((FWDTMoreGZActivity) t).emptyLayout != null) {
                                if (resInfo.getDataList(FWDTGZBean.DataBean.class) == null || resInfo.getDataList(FWDTGZBean.DataBean.class).size() == 0) {
                                    emptyLayoutShowOrHide(((FWDTMoreGZActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((FWDTMoreGZActivity) t).emptyLayout, false);
                                }
                            }
                            ((FWDTMoreGZActivity) t).list = resInfo.getDataList(FWDTGZBean.DataBean.class);


                            ((FWDTMoreGZActivity) t).lv.setAdapter(((FWDTMoreGZActivity) t).adapter = new CommonAdapter<FWDTGZBean.DataBean>(
                                    t.getApplicationContext(), ((FWDTMoreGZActivity) t).list, R.layout.item_sht_mine
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final FWDTGZBean.DataBean item) {
                                    RoundImageView rtv = viewHolder.getView(R.id.item_head_rtv);
                                    PicassoUtils.getinstance().loadCircleImage(t, item.getAvatar(), rtv, 31);
                                    viewHolder.setText(R.id.item_name_tv, item.getUnit_name());
                                    TextView followTv = viewHolder.getView(R.id.item_adopt_tv);

                                    followTv.setText("[????????????]");
                                    followTv.setTextColor(t.getResources().getColor(R.color.light_blue));

                                    followTv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //????????????????????????????????????????????????????????????
                                            DialogUtils.getInstance().AlertMessage(t, "", "??????????????????", "????????????", "????????????", null, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ((FWDTMoreGZActivity) t).userCare(item.getUser_id() + "");
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                            ((FWDTMoreGZActivity) t).lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                    wf.get().setId(Integer.valueOf(((FWDTMoreGZActivity) t).list.get(position).getUser_id()));
                                    wf.get().setTitle(((FWDTMoreGZActivity) t).list.get(position).getUnit_name());
                                    wf.get().setIsCared(1);//??????????????? 0 ??? ????????? 1 ????????????
                                    wf.get().setToType(((FWDTMoreGZActivity) t).list.get(position).getSubject_type());
                                    SystemUtil.getInstance().intentToDetail(t, wf.get());
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

    /**
     * token 	??? 	string 	token???
     * user_id 	??? 	int 	??????????????????id
     * type 	??? 	int 	1 ?????? 2 ????????????
     */
    private void userCare(String userId) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_mycare_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_USERCARER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("user_id", userId)
                .add("type", "2")
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

    private EmptyLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_my_care);
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

    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_mycare_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_FWDT_DEPARTCARED;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("type", "2") //1 : ??????9??? 2?????????
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        FWDTMoreGZActivity.this.finish();
    }
}
