package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

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
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

/**
 * 类描述：RegisterBMZJEntranceActivity 部门镇街手机验证
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/23 18:53
 * 修改人：
 * 修改时间：2017/11/23 18:53
 * 修改备注：
 *
 * @version 1.0
 */
public class RegisterBMZJEntranceActivity extends MyBaseActivity {


    @BindView(R.id.mine_myfollow_underline)
    View mineMyfollowUnderline;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.register_bmzj_selectareacode_tv)
    EditText registerBmzjSelectareacodeTv;
    @BindView(R.id.register_bmzj_getverifycode_tv)
    EditText registerBmzjGetverifycodeTv;
    private String registerType;

    //    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bmzj_entrance);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            registerType = getIntent().getStringExtra("registerType");
        }
        setTitle();
        EventBusUtils.register(this);
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("验证");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("下一步");
        }
    }

    private void next() {
        if (StringUtil.isEmpty(registerBmzjSelectareacodeTv.getText().toString())) {
            ToastUtils.showOneToast(RegisterBMZJEntranceActivity.this, "账户不能为空");
            return;
        }
        if (StringUtil.isEmpty(registerBmzjGetverifycodeTv.getText().toString())) {
            ToastUtils.showOneToast(RegisterBMZJEntranceActivity.this, "口令不能为空");
            return;
        }
        validate();
    }

    /**
     * 部门/镇街验证接口
     * user_login 	是 	string 	账号
     * user_pass 	是 	string 	密码
     */
    private void validate() {
        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_CHECKPASS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_login", registerBmzjSelectareacodeTv.getText().toString())
                .add("user_pass", registerBmzjGetverifycodeTv.getText().toString())
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
                        /**Looper.prepare();
                         UiHelper.ShowOneToast(getApplicationContext(),"数据请求成功");
                         Looper.loop();*/

                        //AfterPayInfo afterPayInfo = JSONObject.parseObject(resInfo.getData().toString(),AfterPayInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(RegisterBMZJEntranceActivity.this, RegisterBMZJActivity.class);
                                intent.putExtra("registerType", registerType);
                                intent.putExtra("name", (String) resInfo.getData());
                                startActivity(intent);

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThisActivity(@NonNull EventRegisterSuc eventRegisterSuc) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBusUtils.unregister(this);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                RegisterBMZJEntranceActivity.this.finish();
                break;
            case R.id.head_right_tv:
                next();
                break;
        }
    }
}
