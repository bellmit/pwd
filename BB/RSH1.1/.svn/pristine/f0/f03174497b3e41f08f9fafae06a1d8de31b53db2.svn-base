package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.SHManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberNumBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：MemberNumActivity 我的-商会管理-会员管理-总数量
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 20:39
 * 修改人：
 * 修改时间：2017/12/7 20:39
 * 修改备注：
 *
 * @version 1.0
 */
public class MemberNumActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_shmanager_membernum_name_tv)
    TextView activityShmanagerMembernumNameTv;
    @BindView(R.id.activity_shmanager_membernum_num_tv)
    TextView activityShmanagerMembernumNumTv;
    @BindView(R.id.activity_shmanager_membernum_add_iv)
    ImageView activityShmanagerMembernumAddIv;
    @BindView(R.id.activity_shmanager_membernum_rl)
    RelativeLayout activityShmanagerMembernumRl;
    @BindView(R.id.activity_shmanager_membernumtobeaudited_name_tv)
    TextView activityShmanagerMembernumtobeauditedNameTv;
    @BindView(R.id.activity_shmanager_membernumtobeaudited_num_tv)
    TextView activityShmanagerMembernumtobeauditedNumTv;
    @BindView(R.id.activity_shmanager_membernumtobeaudited_add_iv)
    ImageView activityShmanagerMembernumtobeauditedAddIv;
    @BindView(R.id.activity_shmanager_membernumtobeaudited_rl)
    RelativeLayout activityShmanagerMembernumtobeauditedRl;
    @BindView(R.id.content_member_num)
    RelativeLayout contentMemberNum;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private MemberNumBean memberNumBean;

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
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            if (null != resInfo) {
                                ((MemberNumActivity) t).memberNumBean = resInfo.getClass(MemberNumBean.class);
                                ((MemberNumActivity) t).activityShmanagerMembernumNumTv.setText(((MemberNumActivity) t).memberNumBean.getMember() + "人");
                                ((MemberNumActivity) t).activityShmanagerMembernumtobeauditedNumTv.setText(((MemberNumActivity) t).memberNumBean.getWait_member() + "人");
                            }
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
        setContentView(R.layout.activity_member_num);
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

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("会员管理");
        }
    }


    /**
     * 商会-成员数接口
     * token 	是 	string 	token值
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_membernum_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_MEMBERCOUNT;
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick({R.id.head_left_iv, R.id.activity_shmanager_membernum_rl, R.id.activity_shmanager_membernumtobeaudited_rl})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                MemberNumActivity.this.finish();
                break;
            case R.id.activity_shmanager_membernum_rl:
                if (memberNumBean.getMember() <= 0) {
                    ToastUtils.showOneToast(getApplicationContext(), "没有会员");
                    return;
                }
                intent = new Intent(MemberNumActivity.this, MemberListActivity.class);
                intent.putExtra("toType", Constants.JG_TO_SHCY);
                startActivity(intent);
                break;
            case R.id.activity_shmanager_membernumtobeaudited_rl:
                if (memberNumBean.getWait_member() <= 0) {
                    ToastUtils.showOneToast(getApplicationContext(), "没有待审核人员");
                    return;
                }
                intent = new Intent(MemberNumActivity.this, MemberListActivity.class);
                intent.putExtra("toType", Constants.JG_TO_DSH);
                startActivity(intent);
                break;
        }
    }
}
