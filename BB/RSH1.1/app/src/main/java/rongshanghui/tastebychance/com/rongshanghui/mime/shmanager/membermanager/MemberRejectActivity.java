package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：MemberRejectActivity 我的-商会管理-会员管理-拒绝入会
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 22:27
 * 修改人：
 * 修改时间：2017/12/7 22:27
 * 修改备注：activity_memberreject_rootlayout
 *
 * @version 1.0
 */
public class MemberRejectActivity extends MyAppCompatActivity {

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
    @BindView(R.id.feedback_content)
    EditText feedbackContent;
    @BindView(R.id.feedback_count)
    TextView feedbackCount;
    @BindView(R.id.content_member_reject)
    RelativeLayout contentMemberReject;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_memberreject_rootlayout)
    CoordinatorLayout activityMemberrejectRootlayout;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_reject);
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
            id = getIntent().getIntExtra("id", -1);
        }

        setTitle();
        initView();
    }

    private void initView() {
        feedbackContent.addTextChangedListener(mTextWatcher);
    }

    /*--------------------------------------- 监听输入字数 ------------------------------------------*/
    private static final int MAX_COUNT = 50;
    private TextWatcher mTextWatcher = new TextWatcher() {

        private int editStart;

        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = feedbackContent.getSelectionStart();
            editEnd = feedbackContent.getSelectionEnd();

            feedbackContent.removeTextChangedListener(mTextWatcher);
            while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            feedbackContent.setText(s);
            feedbackContent.setSelection(editStart);

            //feedbackContent.addTextChangedListener(mTextWatcher);

            setLeftCount();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    };

    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private void setLeftCount() {
        feedbackCount.setText("(" + getInputCount() + "/" + MAX_COUNT + ")");
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(feedbackContent.getText().toString());
    }
    /*--------------------------------------- 监听输入字数 ------------------------------------------*/


    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("添加成员");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确定");
        }
    }

    /**
     * 操作会员(此页面只有移除、通过)
     * token 	是 	string 	token值
     * id 	是 	int 	成员id号
     * status 	是 	int 	状态;0:待审核,1:入会,2:解除，3.拒绝入会,4.退出
     * reason 	否 	string 	拒绝入会是需填写拒绝理由
     */
    private void operateMember() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_memberreject_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_ACTIONMEMBER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        builder.add("id", id + "");
        builder.add("status", "3");
        builder.add("reason", feedbackContent.getText().toString());
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
                                MemberRejectActivity.this.finish();
                                //需要把前一个页面也关闭掉
                                Intent intent = new Intent();
                                intent.setAction(Constants.CLOSE_MEMBERLIST_ACTION);
                                sendBroadcast(intent);
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


    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                MemberRejectActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (StringUtil.isEmpty(feedbackContent.getText().toString())) {
                    ToastUtils.showOneToast(getApplicationContext(), "请说明拒绝添加该用户为成员的原因");
                    return;
                }
                if (id != -1) {
                    operateMember();
                }
                break;
        }
    }
}
