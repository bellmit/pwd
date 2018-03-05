package rongshanghui.tastebychance.com.rongshanghui.mime.jgmanager.xinyong;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;

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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.SubjectCount;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：GRXYDActivity 个人信用贷、信用卡链接
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 18:21
 * 修改人：
 * 修改时间：2017/12/7 18:21
 * 修改备注：
 *
 * @version 1.0
 */
public class GRXYDActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_grxyd_edt)
    EditText activityGrxydEdt;
    @BindView(R.id.activity_grxyd_edticon_iv)
    ImageView activityGrxydEdticonIv;
    @BindView(R.id.content_grxyd)
    RelativeLayout contentGrxyd;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private String toType;

    private SubjectCount subjectCount;

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
                    try {
                        switch (msg.what) {
                            case Constants.WHAT_GETDATA:
                                ((GRXYDActivity) t).subjectCount = resInfo.getClass(SubjectCount.class);
                                if (((GRXYDActivity) t).toType.equals(Constants.JG_TO_GRXYD)) {
                                    ((GRXYDActivity) t).activityGrxydEdt.setText(((GRXYDActivity) t).subjectCount.getPersonalcredit());
                                } else {
                                    ((GRXYDActivity) t).activityGrxydEdt.setText(((GRXYDActivity) t).subjectCount.getCreditcard());
                                }
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
        setContentView(R.layout.activity_grxyd);
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
            toType = getIntent().getStringExtra("toType");
        }

        if (null == toType) {
            GRXYDActivity.this.finish();
        }

        if (null != toType) {
            setTitle();
        }
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            if (toType.equals(Constants.JG_TO_GRXYD)) {
                headCenterTitleTv.setText("个人信用贷");
            } else {
                headCenterTitleTv.setText("信用卡");
            }
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }


    /**
     * 提交个人信用贷接口
     * token 	是 	string 	token值
     * personalcredit 	是 	string 	个人信用贷
     */
    private void commitPersonalCredit() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_grxyd_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_PERSONALCREDIT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("personalcredit", activityGrxydEdt.getText().toString())
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
                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = "提交成功";
                                myHandler.sendMessage(msg);

                                GRXYDActivity.this.finish();
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
     * 提交信用卡链接接口
     * token 	是 	string 	token值
     * credit 	是 	string 	信用卡链接
     */
    private void commitCredit() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_grxyd_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_CREDIT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("credit", activityGrxydEdt.getText().toString())
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
                                msg.what = INFO_WHAT;
                                msg.obj = "提交成功";
                                myHandler.sendMessage(msg);

                                GRXYDActivity.this.finish();
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
     * 主体相关信息数量接口
     * token 	是 	string 	token值
     * id 	是 	int 	查看主体所对应的用户id
     */
    private void getData() {
        if (null == SystemUtil.getInstance().getUserInfo()) {
            ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
            return;
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_grxyd_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SUBJECTCOUNT;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("id", SystemUtil.getInstance().getUserInfo().getId() + "");
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
        if (null != toType) {
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                GRXYDActivity.this.finish();
                break;
            case R.id.head_right_tv:
                SoftInputUtil.hideSoftInput(GRXYDActivity.this, headRightTv);
                if (StringUtil.isEmpty(activityGrxydEdt.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "请输入链接");
                    return;
                }
                if (null == toType) {
                    ToastUtils.showOneToast(getApplicationContext(), "传进来的类型为空，请返回重进");
                    return;
                }
                if (toType.equals(Constants.JG_TO_GRXYD)) {
                    commitPersonalCredit();
                } else {
                    commitCredit();
                }
                break;
        }
    }

}
