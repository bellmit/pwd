package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager;

import android.app.Activity;
import android.content.Intent;
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
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberInfoBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberListBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：MemberInfoActivity 我的-商会管理-会员管理-成员资料
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/8 10:00
 * 修改人：
 * 修改时间：2017/12/8 10:00
 * 修改备注：
 *
 * @version 1.0
 */
public class MemberInfoActivity extends MyAppCompatActivity {


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
    @BindView(R.id.shmanager_memberinfo_name_tv)
    TextView shmanagerMemberinfoNameTv;
    @BindView(R.id.shmanager_memberinfo_sex_tv)
    TextView shmanagerMemberinfoSexTv;
    @BindView(R.id.shmanager_memberinfo_birth_tv)
    TextView shmanagerMemberinfoBirthTv;
    @BindView(R.id.shmanager_memberinfo_jiguan_tv)
    TextView shmanagerMemberinfoJiguanTv;
    @BindView(R.id.shmanager_memberinfo_minzu_tv)
    TextView shmanagerMemberinfoMinzuTv;
    @BindView(R.id.shmanager_memberinfo_zhuanye_tv)
    TextView shmanagerMemberinfoZhuanyeTv;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.shmanager_memberinfo_address_tv)
    TextView shmanagerMemberinfoAddressTv;
    @BindView(R.id.shmanager_memberinfo_phone_tv)
    TextView shmanagerMemberinfoPhoneTv;
    @BindView(R.id.shmanager_memberinfo_zhiwu_tv)
    TextView shmanagerMemberinfoZhiwuTv;
    @BindView(R.id.shmanager_memberinfo_photo_iv)
    ImageView shmanagerMemberinfoPhotoIv;
    @BindView(R.id.content_member_info)
    RelativeLayout contentMemberInfo;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_memberinfo_rootlayout)
    CoordinatorLayout activityMemberinfoRootlayout;
    private MemberInfoBean memberInfoBean;
    private static final int POST_REMOVE_WHAT = 22;//移除会员
    private static final int POST_ADOPT_WHAT = 23;//通过审核

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
                                ((MemberInfoActivity) t).memberInfoBean = resInfo.getClass(MemberInfoBean.class);
                                if (null != ((MemberInfoActivity) t).memberInfoBean) {
                                    ((MemberInfoActivity) t).shmanagerMemberinfoNameTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getName());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoSexTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getSex() == 1 ? "男" : "女");
                                    ((MemberInfoActivity) t).shmanagerMemberinfoBirthTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getBirthday());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoJiguanTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getNative_place());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoMinzuTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getNation());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoZhuanyeTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getCollege());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoAddressTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getAddress());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoPhoneTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getMobile());
                                    ((MemberInfoActivity) t).shmanagerMemberinfoZhiwuTv.setText(((MemberInfoActivity) t).memberInfoBean.getApply_data().getFunc());
                                    PicassoUtils.getinstance().loadNormalByPath(t, ((MemberInfoActivity) t).memberInfoBean.getApply_data().getAvatar(), ((MemberInfoActivity) t).shmanagerMemberinfoPhotoIv);
                                }
                            }
                            break;
                        case POST_REMOVE_WHAT:
                        case POST_ADOPT_WHAT:
                            Intent intent = new Intent();
                            intent.setAction(Constants.CLOSE_MEMBERLIST_ACTION);
                            t.sendBroadcast(intent);

                            t.finish();
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

    private MemberListBean memberListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
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
            memberListBean = (MemberListBean) getIntent().getSerializableExtra("memberBean");
        }

        if (null != memberListBean) {
            setTitle();
        }
    }

    private void setTitle() {
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("成员资料");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            if (memberListBean.getToType().equals(Constants.JG_TO_SHCY)) {
                headRightTv.setText("移除");
            } else if (memberListBean.getToType().equals(Constants.JG_TO_DSH)) {
                headRightTv.setText("通过");
            }
        }
    }

    /**
     * 商会-成员信息信息接口
     * id 	否 	int 	成员id号 (商会成员列表 根据id 查会员信息 )
     * token 	否 	string 	token值 （申请人获取自己填写的资料）
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_memberinfo_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_MEMBERINFO;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        /*if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }*/
        builder.add("id", memberListBean.getUser_id() + "");
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

    /**
     * 操作会员(此页面只有移除、通过)
     * token 	是 	string 	token值
     * id 	是 	int 	成员id号
     * status 	是 	int 	状态;0:待审核,1:入会,2:解除，3.拒绝入会,4.退出
     * reason 	否 	string 	拒绝入会是需填写拒绝理由
     */
    private void operateMember(int id) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_memberinfo_rootlayout), new CustomLoadingFactory());
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
        if (Constants.JG_TO_SHCY.equals(memberListBean.getToType())) {
            builder.add("status", "2");
        } else if (Constants.JG_TO_DSH.equals(memberListBean.getToType())) {
            builder.add("status", "1");
        }
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
                                if (Constants.JG_TO_SHCY.equals(memberListBean.getToType())) {
                                    msg.what = POST_REMOVE_WHAT;
                                } else if (Constants.JG_TO_DSH.equals(memberListBean.getToType())) {
                                    msg.what = POST_ADOPT_WHAT;
                                }
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
        if (null != memberListBean) {
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                MemberInfoActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (null != memberListBean && memberListBean.getUser_id() != -1) {
                    operateMember(memberListBean.getUser_id());
                }
                break;
        }
    }
}
