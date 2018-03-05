package rongshanghui.tastebychance.com.rongshanghui.mime;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.login.bean.LoginRes;
import rongshanghui.tastebychance.com.rongshanghui.mime.bean.ContactBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.bean.ToRegisterFailReasonBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.bmmanager.BMManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.feedback.MineFeedBackActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.jgmanager.JGManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.mycare.MineMyCareActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.mycollection.MineMyCollectionActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter.PersonalCenterActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.qymanager.QYManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.setting.SettingChangeAccountActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.SHManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.xxyyqtmanager.XXYYQTManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.zjmanager.ZJManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.register.ModifyRegisterActivity;
import rongshanghui.tastebychance.com.rongshanghui.register.RegisterBMZJActivity;
import rongshanghui.tastebychance.com.rongshanghui.register.RegisterJGActivity;
import rongshanghui.tastebychance.com.rongshanghui.register.RegisterSHQYActivity;
import rongshanghui.tastebychance.com.rongshanghui.register.RegisterXXYYQTActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.CommomDialog;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.RoundImageView;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/21 15:18
 * 修改人：Administrator
 * 修改时间：2017/12/21 15:18
 * 修改备注：
 */

public class MineFragment extends MyBaseFragment {


    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_head_riv)
    RoundImageView itemHeadRiv;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.account_tv)
    TextView accountTv;
    @BindView(R.id.login_account_tv)
    TextView loginAccountTv;
    @BindView(R.id.mine_personinfo_iv)
    ImageView minePersoninfoIv;
    @BindView(R.id.mine_personinfo_tv)
    TextView minePersoninfoTv;
    @BindView(R.id.mine_personinfo_turn_iv)
    ImageView minePersoninfoTurnIv;
    @BindView(R.id.mine_personinfo_rl)
    RelativeLayout minePersoninfoRl;
    @BindView(R.id.mine_myfollow_iv)
    ImageView mineMyfollowIv;
    @BindView(R.id.mine_myfollow_tv)
    TextView mineMyfollowTv;
    @BindView(R.id.mine_myfollow_turn_iv)
    ImageView mineMyfollowTurnIv;
    @BindView(R.id.mine_myfollow_rl)
    RelativeLayout mineMyfollowRl;
    @BindView(R.id.mine_mycollection_iv)
    ImageView mineMycollectionIv;
    @BindView(R.id.mine_mycollection_tv)
    TextView mineMycollectionTv;
    @BindView(R.id.mine_mycollection_turn_iv)
    ImageView mineMycollectionTurnIv;
    @BindView(R.id.mine_mycollection_rl)
    RelativeLayout mineMycollectionRl;
    @BindView(R.id.mine_opinonfeedback_iv)
    ImageView mineOpinonfeedbackIv;
    @BindView(R.id.mine_opinonfeedback_tv)
    TextView mineOpinonfeedbackTv;
    @BindView(R.id.mine_opinonfeedback_turn_iv)
    ImageView mineOpinonfeedbackTurnIv;
    @BindView(R.id.mine_opinonfeedback_rl)
    RelativeLayout mineOpinonfeedbackRl;
    @BindView(R.id.mine_filecache_iv)
    ImageView mineFilecacheIv;
    @BindView(R.id.mine_filecache_tv)
    TextView mineFilecacheTv;
    @BindView(R.id.mine_filecache_turn_iv)
    ImageView mineFilecacheTurnIv;
    @BindView(R.id.mine_filecache_rl)
    RelativeLayout mineFilecacheRl;
    @BindView(R.id.mine_shmanager_iv)
    ImageView mineShmanagerIv;
    @BindView(R.id.mine_shmanager_tv)
    TextView mineShmanagerTv;
    @BindView(R.id.mine_shmanager_turn_iv)
    ImageView mineShmanagerTurnIv;
    @BindView(R.id.mine_shmanager_rl)
    RelativeLayout mineShmanagerRl;
    @BindView(R.id.mine_qymanager_iv)
    ImageView mineQymanagerIv;
    @BindView(R.id.mine_qymanager_tv)
    TextView mineQymanagerTv;
    @BindView(R.id.mine_qymanager_turn_iv)
    ImageView mineQymanagerTurnIv;
    @BindView(R.id.mine_qymanager_rl)
    RelativeLayout mineQymanagerRl;
    @BindView(R.id.mine_jgmanager_iv)
    ImageView mineJgmanagerIv;
    @BindView(R.id.mine_jgmanager_tv)
    TextView mineJgmanagerTv;
    @BindView(R.id.mine_jgmanager_turn_iv)
    ImageView mineJgmanagerTurnIv;
    @BindView(R.id.mine_jgmanager_rl)
    RelativeLayout mineJgmanagerRl;
    @BindView(R.id.mine_bmmanager_iv)
    ImageView mineBmmanagerIv;
    @BindView(R.id.mine_bmmanager_tv)
    TextView mineBmmanagerTv;
    @BindView(R.id.mine_bmmanager_turn_iv)
    ImageView mineBmmanagerTurnIv;
    @BindView(R.id.mine_bmmanager_rl)
    RelativeLayout mineBmmanagerRl;
    @BindView(R.id.mine_zjmanager_iv)
    ImageView mineZjmanagerIv;
    @BindView(R.id.mine_zjmanager_tv)
    TextView mineZjmanagerTv;
    @BindView(R.id.mine_zjmanager_turn_iv)
    ImageView mineZjmanagerTurnIv;
    @BindView(R.id.mine_zjmanager_rl)
    RelativeLayout mineZjmanagerRl;
    @BindView(R.id.mine_xxmanager_iv)
    ImageView mineXxmanagerIv;
    @BindView(R.id.mine_xxmanager_tv)
    TextView mineXxmanagerTv;
    @BindView(R.id.mine_xxmanager_turn_iv)
    ImageView mineXxmanagerTurnIv;
    @BindView(R.id.mine_xxmanager_rl)
    RelativeLayout mineXxmanagerRl;
    @BindView(R.id.mine_yymanager_iv)
    ImageView mineYymanagerIv;
    @BindView(R.id.mine_yymanager_tv)
    TextView mineYymanagerTv;
    @BindView(R.id.mine_yymanager_turn_iv)
    ImageView mineYymanagerTurnIv;
    @BindView(R.id.mine_yymanager_rl)
    RelativeLayout mineYymanagerRl;
    @BindView(R.id.mine_qtmanager_iv)
    ImageView mineQtmanagerIv;
    @BindView(R.id.mine_qtmanager_tv)
    TextView mineQtmanagerTv;
    @BindView(R.id.mine_qtmanager_turn_iv)
    ImageView mineQtmanagerTurnIv;
    @BindView(R.id.mine_qtmanager_rl)
    RelativeLayout mineQtmanagerRl;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.content_mine)
    RelativeLayout contentMine;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine)
    CoordinatorLayout activityMine;

    @BindView(R.id.mine_bmmanager_underline)
    View mineBmmanagerUnderline;
    @BindView(R.id.mine_shmanager_underline)
    View mineShmanagerUnderline;
    @BindView(R.id.mine_qymanager_underline)
    View mineQymanagerUnderline;
    @BindView(R.id.mine_jgmanager_underline)
    View mineJgmanagerUnderline;
    @BindView(R.id.mine_zjmanager_underline)
    View mineZjmanagerUnderline;
    @BindView(R.id.mine_xxmanager_underline)
    View mineXxmanagerUnderline;
    @BindView(R.id.mine_yymanager_underline)
    View mineYymanagerUnderline;
    @BindView(R.id.mine_qtmanager_underline)
    View mineQtmanagerUnderline;

    @BindView(R.id.mine_shmanager_state_tv)
    TextView mineShmanagerStateTv;
    @BindView(R.id.mine_qymanager_state_tv)
    TextView mineQymanagerStateTv;
    @BindView(R.id.mine_jgmanager_state_tv)
    TextView mineJgmanagerStateTv;
    @BindView(R.id.mine_bmmanager_state_tv)
    TextView mineBmmanagerStateTv;
    @BindView(R.id.mine_zjmanager_state_tv)
    TextView mineZjmanagerStateTv;
    @BindView(R.id.mine_xxmanager_state_tv)
    TextView mineXxmanagerStateTv;
    @BindView(R.id.mine_yymanager_state_tv)
    TextView mineYymanagerStateTv;
    @BindView(R.id.mine_qtmanager_state_tv)
    TextView mineQtmanagerStateTv;
    Unbinder unbinder;

    private IntentFilter intentFilter;
    private View rootView;
    private MineFragment fragment;

    private static final String EXTRA_CONTENT = "content";

    public static MineFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        MineFragment homeFragment = new MineFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_mine, null);
        unbinder = ButterKnife.bind(this, contentView);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.LOGOUT_ACTION);

        rootView = contentView;
        fragment = this;

        setTitle();

        return contentView;
    }

    private void getContactPhoneNo() {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_CONTACT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ContactBean contactBean = resInfo.getClass(ContactBean.class);
                                    SystemUtil.getInstance().saveStrToSP("reason", contactBean.getRefusal_reason());
                                    SystemUtil.getInstance().saveStrToSP("kfcontact", contactBean.getContact());
                                    SystemUtil.getInstance().saveIntToSP("registatus", contactBean.getRegi_status());
                                    initView();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
    public void onResume() {
        super.onResume();
        getContactPhoneNo();
        initView();
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);

            Bitmap kefuIcon = BitmapFactory.decodeResource(getResources(), R.drawable.kefuicon);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) headLeftIv.getLayoutParams();
            lp.width = Math.round(kefuIcon.getWidth() * (Constants.scale - 0.2f));
            lp.height = Math.round(kefuIcon.getHeight() * (Constants.scale - 0.2f));
            headLeftIv.setLayoutParams(lp);
            headLeftIv.setImageDrawable(getResources().getDrawable(R.drawable.kefuicon));
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("我的");
        }
        if (null != headRightIv) {
            headRightIv.setVisibility(View.VISIBLE);
            Bitmap kefuIcon = BitmapFactory.decodeResource(getResources(), R.drawable.head_right_setting);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) headRightIv.getLayoutParams();
            lp.width = Math.round(kefuIcon.getWidth() * Constants.scale);
            lp.height = Math.round(kefuIcon.getHeight() * Constants.scale);
            headRightIv.setLayoutParams(lp);
            headRightIv.setImageDrawable(getResources().getDrawable(R.drawable.head_right_setting));
        }
    }

    private void hideManagerEntrance() {
        mineShmanagerRl.setVisibility(View.GONE);
        mineShmanagerUnderline.setVisibility(View.GONE);
        mineQymanagerRl.setVisibility(View.GONE);
        mineQymanagerUnderline.setVisibility(View.GONE);
        mineBmmanagerRl.setVisibility(View.GONE);
        mineBmmanagerUnderline.setVisibility(View.GONE);
        mineJgmanagerRl.setVisibility(View.GONE);
        mineJgmanagerUnderline.setVisibility(View.GONE);
        mineZjmanagerRl.setVisibility(View.GONE);
        mineZjmanagerUnderline.setVisibility(View.GONE);
        mineXxmanagerRl.setVisibility(View.GONE);
        mineXxmanagerUnderline.setVisibility(View.GONE);
        mineYymanagerRl.setVisibility(View.GONE);
        mineYymanagerUnderline.setVisibility(View.GONE);
        mineQtmanagerRl.setVisibility(View.GONE);
        mineQtmanagerUnderline.setVisibility(View.GONE);
    }

    private void initView() {
        if (!SystemUtil.getInstance().getIsLogin()) {
            loginTv.setText("[点击登录]");
            loginTv.setTextColor(getResources().getColor(R.color.font_blue));
            loginTv.setVisibility(View.VISIBLE);
            accountTv.setVisibility(View.GONE);
            loginAccountTv.setVisibility(View.GONE);
            PicassoUtils.getinstance().loadNormalById(getActivity(), R.drawable.person_defaultheadportrait, itemHeadRiv);
            hideManagerEntrance();
            return;
        } else {
            loginTv.setVisibility(View.GONE);
            accountTv.setVisibility(View.VISIBLE);
            loginAccountTv.setVisibility(View.VISIBLE);
        }

        LoginRes.UserInfoBean userInfo = SystemUtil.getInstance().getUserInfo();
        if (userInfo == null) {
            return;
        }
        if (StringUtil.isNotEmpty(userInfo.getAvatar())) {
            PicassoUtils.getinstance().loadCircleHead(getActivity(), userInfo.getAvatar(), itemHeadRiv, 24);
        } else {
            PicassoUtils.getinstance().loadNormalById(getActivity(), R.drawable.person_defaultheadportrait, itemHeadRiv);
        }


        accountTv.setText(userInfo.getUser_nickname());
        if (StringUtil.isEmpty(userInfo.getUser_nickname())) {
            accountTv.setText("匿名用户");
        }
        accountTv.setTextColor(getResources().getColor(R.color.font_gray));
        loginAccountTv.setText("账号：" + SystemUtil.getInstance().getUserInfo().getUser_login());

        //主体类型;0:个人,1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
        if (userInfo.getSubject_type() == 0) {//个人

        }

        hideManagerEntrance();
        if (userInfo.getSubject_type() == 1) {
            mineShmanagerRl.setVisibility(View.VISIBLE);
            mineShmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineShmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineShmanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 2) {
            mineQymanagerRl.setVisibility(View.VISIBLE);
            mineQymanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineQymanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineQymanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 3) {
            mineBmmanagerRl.setVisibility(View.VISIBLE);
            mineBmmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineBmmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineBmmanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 4) {
            mineJgmanagerRl.setVisibility(View.VISIBLE);
            mineJgmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineJgmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineJgmanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 5) {
            mineZjmanagerRl.setVisibility(View.VISIBLE);
            mineZjmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineZjmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineZjmanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 6) {
            mineXxmanagerRl.setVisibility(View.VISIBLE);
            mineXxmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineXxmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineXxmanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 7) {
            mineYymanagerRl.setVisibility(View.VISIBLE);
            mineYymanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineYymanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineYymanagerStateTv.setVisibility(View.GONE);
            }
        }
        if (userInfo.getSubject_type() == 8) {
            mineQtmanagerRl.setVisibility(View.VISIBLE);
            mineQtmanagerUnderline.setVisibility(View.VISIBLE);
            if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {
                mineQtmanagerStateTv.setVisibility(View.VISIBLE);
            } else {
                mineQtmanagerStateTv.setVisibility(View.GONE);
            }
        }
    }

    private void call() {
        //弹出框提示是否拨打电话
        new CommomDialog(getActivity(), R.style.dialog, "是否拨打" + SystemUtil.getInstance().getStrFromSP("kfcontact"), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SystemUtil.getInstance().getStrFromSP("kfcontact"))));
                    dialog.dismiss();
                }
            }
        }).setTitle("提示").show();
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        if (StringUtil.isEmpty(eventLoginSuc.getToActivity())) {
            return;
        }

        intentTo(eventLoginSuc.getToActivity());
    }

    private void intentTo(String intentTo) {
        if (StringUtil.isEmpty(intentTo)) {
            return;
        }

        Intent intent;
        switch (intentTo) {
            case Constants.TO_SETTING:
                intent = new Intent(getActivity(), SettingChangeAccountActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_PERSONCENTER:
                intent = new Intent(getActivity(), PersonalCenterActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_MYFOLLOW:
                intent = new Intent(getActivity(), MineMyCareActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_MYCOLLOECTION:
                intent = new Intent(getActivity(), MineMyCollectionActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_OPINIONFEEDBACK:
                intent = new Intent(getActivity(), MineFeedBackActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_FILECACHE:

                break;
        }

        if (SystemUtil.getInstance().getIntFromSP("registatus") == 0) {//未审核
            switch (intentTo) {
                case Constants.TO_SHMANAGER:
                case Constants.TO_QYMANAGER:
                case Constants.TO_JGMANAGER:
                case Constants.TO_BMMANAGER:
                case Constants.TO_XXMANAGER:
                case Constants.TO_YYMANAGER:
                case Constants.TO_ZJMANAGER:
                case Constants.TO_QTMANAGER:
                    ToastUtils.showOneToast(getActivity(), "等待注册审核通过");
                    break;
            }
            return;
        }

        intentDWManager(intentTo);
    }

    private void intentDWManager(String intentTo) {
        if (SystemUtil.getInstance().getIntFromSP("registatus") == 2) {//不通过
            //管理类
            WeakReference<ToRegisterFailReasonBean> wf = new WeakReference<ToRegisterFailReasonBean>(new ToRegisterFailReasonBean());
            wf.get().setContact(SystemUtil.getInstance().getStrFromSP("kfcontact"));
            wf.get().setFrom(Constants.REGISTER_FROM);
            wf.get().setRefusal_reason(SystemUtil.getInstance().getStrFromSP("reason"));
            switch (intentTo) {
                case Constants.TO_SHMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_SH);
                    break;
                case Constants.TO_QYMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_QY);
                    break;
                case Constants.TO_JGMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_JG);
                    break;
                case Constants.TO_BMMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_BM);
                    break;
                case Constants.TO_ZJMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_ZJ);
                    break;
                case Constants.TO_XXMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_XX);
                    break;
                case Constants.TO_YYMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_YY);
                    break;
                case Constants.TO_QTMANAGER:
                    wf.get().setRegisterType(Constants.REGISTERENTRANCETYPE_QT);
                    break;
            }
            Intent intentToRegisterFail = new Intent(getActivity(), RegisterFailActivity.class);
            intentToRegisterFail.putExtra("toRegisterFailReasonBean", wf.get());
            startActivity(intentToRegisterFail);
            return;
        }

        Intent intent;
        //管理类
        switch (intentTo) {
            case Constants.TO_SHMANAGER:
                intent = new Intent(getActivity(), SHManagerActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_QYMANAGER:
                intent = new Intent(getActivity(), QYManagerActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_JGMANAGER:
                intent = new Intent(getActivity(), JGManagerActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_BMMANAGER:
                intent = new Intent(getActivity(), BMManagerActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_ZJMANAGER:
                intent = new Intent(getActivity(), ZJManagerActivity.class);
                startActivity(intent);
                break;
            case Constants.TO_XXMANAGER:
                intent = new Intent(getActivity(), XXYYQTManagerActivity.class);
                intent.putExtra("type", Constants.PUBLISHCATE_XX);
                startActivity(intent);
                break;
            case Constants.TO_YYMANAGER:
                intent = new Intent(getActivity(), XXYYQTManagerActivity.class);
                intent.putExtra("type", Constants.PUBLISHCATE_YY);
                startActivity(intent);
                break;
            case Constants.TO_QTMANAGER:
                intent = new Intent(getActivity(), XXYYQTManagerActivity.class);
                intent.putExtra("type", Constants.PUBLISHCATE_QT);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != broadcastReceiver) {
            getActivity().registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != broadcastReceiver) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_iv, R.id.item_head_riv, R.id.login_tv, R.id.mine_personinfo_rl, R.id.mine_personinfo_tv, R.id.mine_personinfo_turn_iv,
            R.id.mine_myfollow_rl, R.id.mine_myfollow_turn_iv, R.id.mine_mycollection_rl, R.id.mine_mycollection_turn_iv, R.id.mine_opinonfeedback_rl, R.id.mine_opinonfeedback_turn_iv,
            R.id.mine_filecache_rl, R.id.mine_filecache_turn_iv, R.id.mine_shmanager_rl, R.id.mine_shmanager_turn_iv, R.id.mine_qymanager_rl, R.id.mine_qymanager_turn_iv,
            R.id.mine_jgmanager_rl, R.id.mine_jgmanager_turn_iv, R.id.mine_bmmanager_rl, R.id.mine_bmmanager_turn_iv, R.id.mine_zjmanager_rl, R.id.mine_zjmanager_turn_iv,
            R.id.mine_xxmanager_rl, R.id.mine_xxmanager_turn_iv, R.id.mine_yymanager_rl, R.id.mine_yymanager_turn_iv, R.id.mine_qtmanager_rl, R.id.mine_qtmanager_turn_iv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                call();
                break;
            case R.id.head_right_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_SETTING);
                } else {
                    intentTo(Constants.TO_SETTING);
                }
                break;
            case R.id.login_tv:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.item_head_riv:
            case R.id.mine_personinfo_rl:
            case R.id.mine_personinfo_tv:
            case R.id.mine_personinfo_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_PERSONCENTER);
                } else {
                    intentTo(Constants.TO_PERSONCENTER);
                }
                break;
            case R.id.mine_myfollow_rl:
            case R.id.mine_myfollow_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_MYFOLLOW);
                } else {
                    intentTo(Constants.TO_MYFOLLOW);
                }
                break;
            case R.id.mine_mycollection_rl:
            case R.id.mine_mycollection_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_MYCOLLOECTION);
                } else {
                    intentTo(Constants.TO_MYCOLLOECTION);
                }
                break;
            case R.id.mine_opinonfeedback_rl:
            case R.id.mine_opinonfeedback_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_OPINIONFEEDBACK);
                } else {
                    intentTo(Constants.TO_OPINIONFEEDBACK);
                }
                break;
            case R.id.mine_filecache_rl:
            case R.id.mine_filecache_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_FILECACHE);
                } else {
                    intentTo(Constants.TO_FILECACHE);
                }
                break;
            case R.id.mine_shmanager_rl:
            case R.id.mine_shmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_SHMANAGER);
                } else {
                    intentTo(Constants.TO_SHMANAGER);
                }
                break;
            case R.id.mine_qymanager_rl:
            case R.id.mine_qymanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_QYMANAGER);
                } else {
                    intentTo(Constants.TO_QYMANAGER);
                }
                break;
            case R.id.mine_jgmanager_rl:
            case R.id.mine_jgmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_JGMANAGER);
                } else {
                    intentTo(Constants.TO_JGMANAGER);
                }
                break;
            case R.id.mine_bmmanager_rl:
            case R.id.mine_bmmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_BMMANAGER);
                } else {
                    intentTo(Constants.TO_BMMANAGER);
                }
                break;
            case R.id.mine_zjmanager_rl:
            case R.id.mine_zjmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_ZJMANAGER);
                } else {
                    intentTo(Constants.TO_ZJMANAGER);
                }
                break;
            case R.id.mine_xxmanager_rl:
            case R.id.mine_xxmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_XXMANAGER);
                } else {
                    intentTo(Constants.TO_XXMANAGER);
                }
                break;
            case R.id.mine_yymanager_rl:
            case R.id.mine_yymanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_YYMANAGER);
                } else {
                    intentTo(Constants.TO_YYMANAGER);
                }
                break;
            case R.id.mine_qtmanager_rl:
            case R.id.mine_qtmanager_turn_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(getActivity(), Constants.TO_QTMANAGER);
                } else {
                    intentTo(Constants.TO_QTMANAGER);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
