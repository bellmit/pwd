package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.SubjectCount;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToRHSQBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 商会通-商会-商会信息
 */
public class SHTSHDetailActivity extends MyBaseActivity {

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
    @BindView(R.id.activity_detail_head_iv)
    ImageView activityShtshdetailHeadIv;
    @BindView(R.id.activity_detail_hynumshow_tv)
    TextView activityShtshdetailHynumshowTv;
    @BindView(R.id.activity_detail_hynum_tv)
    TextView activityShtshdetailHynumTv;
    @BindView(R.id.activity_detail_hynum_ll)
    LinearLayout activityShtshdetailHynumLl;
    @BindView(R.id.activity_detail_fsnumshow_tv)
    TextView activityShtshdetailFsnumshowTv;
    @BindView(R.id.activity_detail_fsnum_tv)
    TextView activityShtshdetailFsnumTv;
    @BindView(R.id.activity_detail_fsnum_ll)
    LinearLayout activityShtshdetailFsnumLl;
    @BindView(R.id.activity_detail_shxnumshow_tv)
    TextView activityShtshdetailShxnumshowTv;
    @BindView(R.id.activity_detail_shxnum_tv)
    TextView activityShtshdetailShxnumTv;
    @BindView(R.id.activity_detail_shxnum_ll)
    LinearLayout activityShtshdetailShxnumLl;
    @BindView(R.id.activity_detail_llshow_tv)
    TextView activityShtshdetailLlshowTv;
    @BindView(R.id.activity_detail_llnum_tv)
    TextView activityShtshdetailLlnumTv;
    @BindView(R.id.activity_detail_llnum_ll)
    LinearLayout activityShtshdetailLlnumLl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.activity_detail_jj_iv)
    ImageView activityShtshdetailJjIv;
    @BindView(R.id.activity_detail_zx_iv)
    ImageView activityShtshdetailZxIv;
    @BindView(R.id.activity_detail_rzxm_iv)
    ImageView activityShtshdetailRhsqIv;
    @BindView(R.id.activity_detail_lxwm_iv)
    ImageView activityShtshdetailLxwmIv;
    @BindView(R.id.activity_detail_qyfc_iv)
    ImageView activityShtshdetailShxIv;
    @BindView(R.id.activity_detail_bszn_iv)
    ImageView activityShtshdetailTcshIv;
    @BindView(R.id.activity_shtshdetail_rootlayout)
    RelativeLayout activityShtshdetailRootlayout;

    private ToDetailBean toDetailBean;

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
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ((SHTSHDetailActivity) t).subjectCount = resInfo.getClass(SubjectCount.class);
                            ((SHTSHDetailActivity) t).shId = ((SHTSHDetailActivity) t).subjectCount.getId();

                            PicassoUtils.getinstance().loadNormalByPath(t, ((SHTSHDetailActivity) t).subjectCount.getImage(), ((SHTSHDetailActivity) t).activityShtshdetailHeadIv);
                            ((SHTSHDetailActivity) t).activityShtshdetailHynumshowTv.setText(((SHTSHDetailActivity) t).subjectCount.getMember_count() + "");
                            ((SHTSHDetailActivity) t).activityShtshdetailFsnumshowTv.setText(((SHTSHDetailActivity) t).subjectCount.getFans_count() + "");
                            ((SHTSHDetailActivity) t).activityShtshdetailShxnumshowTv.setText(((SHTSHDetailActivity) t).subjectCount.getShx() + "");
                            ((SHTSHDetailActivity) t).activityShtshdetailLlshowTv.setText(((SHTSHDetailActivity) t).subjectCount.getHits_count() + "");

                            ((SHTSHDetailActivity) t).toDetailBean.setIsCared(((SHTSHDetailActivity) t).subjectCount.getIs_cared());
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
        setContentView(R.layout.activity_home_sht_shdetail);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            toDetailBean = (ToDetailBean) getIntent().getSerializableExtra("toDetailBean");
        }
        if (null != toDetailBean) {
            setTitle();
        }

        initView();
    }

    private void initView() {
        activityShtshdetailJjIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailJjIv, 97, 120));
        activityShtshdetailZxIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailZxIv, 90, 120));
        activityShtshdetailShxIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailShxIv, 151, 191));
        activityShtshdetailRhsqIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailRhsqIv, 114, 112));
        activityShtshdetailLxwmIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailLxwmIv, 72, 112));
        activityShtshdetailTcshIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtshdetailTcshIv, 151, 40));
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(/*StringUtil.isNotEmpty(toDetailBean.getTitle()) ? toDetailBean.getTitle() : */"商会信息");

        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            updateIsCared();
        }
    }

    /**
     * 主体相关信息数量接口
     * token 	是 	string 	token值
     * id 	是 	int 	查看主体所对应的用户id
     */
    private void getData() {
        if (null != SystemUtil.getInstance().getUserInfo()) {
            userId = SystemUtil.getInstance().getUserInfo().getId();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_shtshdetail_rootlayout), new CustomLoadingFactory());
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
        builder.add("id", toDetailBean.getId() + "");
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
     * 加关注、取消关注
     * <p>
     * token 	是 	string 	token值
     * user_id 	是 	int 	被关注的用户id
     * type 	是 	int 	1 关注 2 取消关注
     *
     * @param userId
     */
    private void userCare(String userId, final boolean isAddCare) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_shtshdetail_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_USERCARER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("user_id", userId)
                .add("type", isAddCare ? "1" : "2")
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
                                if (isAddCare) {
                                    toDetailBean.setIsCared(1); //是否已关注 0 ： 未关注 1 ：已关注
                                } else {
                                    toDetailBean.setIsCared(0);
                                }
                                updateIsCared();
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

    private void updateIsCared() {
        if (toDetailBean.getIsCared() == 0) {//是否已关注 0 ： 未关注 1 ：已关注
            headRightTv.setText("未关注");
        } else {
            headRightTv.setText("已关注");
        }
    }

    /**
     * 退出商会
     * token 	是 	string 	token值
     * user_id 	是 	int 	要退出的商会的用户id
     */
    private void tcsh() {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_SHTSH_LEAVE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("user_id", toDetailBean.getId() + "")
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
                                msg.obj = "成功退出商会";
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
        if (null != toDetailBean) {
            getData();
        }
    }

    private int userId;//用户id
    private int shId;//商会id

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_detail_head_iv, R.id.activity_detail_jj_iv, R.id.activity_detail_zx_iv, R.id.activity_detail_rzxm_iv, R.id.activity_detail_lxwm_iv, R.id.activity_detail_qyfc_iv, R.id.activity_detail_bszn_iv})
    public void onViewClicked(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                SHTSHDetailActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (toDetailBean.getIsCared() == 0) {//是否已关注 0 ： 未关注 1 ：已关注
                    userCare(toDetailBean.getId() + "", true);//1 关注 2 取消关注
                } else {
                    userCare(toDetailBean.getId() + "", false);
                }
                break;
            case R.id.activity_detail_head_iv:
                break;
            case R.id.activity_detail_jj_iv:
                if (null == subjectCount) {
                    return;
                }
                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                wf.get().setTitle("简介");
                wf.get().setJJ(true);
                wf.get().setVideoUrl(subjectCount.getVideo());
                wf.get().setUrl(subjectCount.getIntro() + SystemUtil.getInstance().getJJVideoType(subjectCount.getVideo()));
                SystemUtil.getInstance().intentToWeb2Activity(SHTSHDetailActivity.this, wf.get());
                break;
            case R.id.activity_detail_zx_iv:
                WeakReference<HomeDetailListBean> wf2 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf2.get().setType(Constants.PULISHTYPE_ZX);
                wf2.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTSHDetailActivity.this, wf2.get());
                break;
            case R.id.activity_detail_rzxm_iv:
                //判断入会申请的状态
                if (subjectCount == null) {
                    return;
                }

                //无法退出自己的商会
                if (userId == shId) {
                    ToastUtils.showOneToast(getApplicationContext(), "无法申请本账号管理的商会");
                    return;
                }

                if (subjectCount.getApply_status() == Constants.SHTSH_RHSQ_WAITAUDITE) {
                    ToastUtils.showOneToast(getApplicationContext(), "您提交的资料正在审核中...");
                    return;
                }

                if (subjectCount.getApply_status() == Constants.SHTSH_RHSQ_MEMBER) {
                    ToastUtils.showOneToast(getApplicationContext(), "您已经是该商会的会员不需要再次申请");
                }
                if (subjectCount.getApply_status() == Constants.SHTSH_RHSQ_REMOVE || subjectCount.getApply_status() == Constants.SHTSH_RHSQ_REJECT
                        || subjectCount.getApply_status() == Constants.SHTSH_RHSQ_NORELATION) {//-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)(其中2是被移除过)


                    intent = new Intent(SHTSHDetailActivity.this, RHSQActivity.class);
                    WeakReference<ToRHSQBean> wf3 = new WeakReference<ToRHSQBean>(new ToRHSQBean());
                    wf3.get().setId(toDetailBean.getId());
                    wf3.get().setStatus(subjectCount.getApply_status());
                    wf3.get().setReason(subjectCount.getReason());
//                    wf3.get().setStatus(Constants.SHTSH_RHSQ_REJECT);
//                    wf3.get().setReason("长得太帅\n长得太高");
                    intent.putExtra("toRHSQBean", wf3.get());
                    startActivity(intent);
                } else if (subjectCount.getApply_status() == 0) {
                    ToastUtils.showOneToast(getApplicationContext(), "审核中...");
                } else if (subjectCount.getApply_status() == 1) {
                    ToastUtils.showOneToast(getApplicationContext(), "已通过审核,不需要再申请");
                }
                break;
            case R.id.activity_detail_lxwm_iv:
                if (null == subjectCount) {
                    return;
                }
                SystemUtil.getInstance().contactUs(SHTSHDetailActivity.this, subjectCount.getArea_code() + subjectCount.getMobile());
                break;
            case R.id.activity_detail_qyfc_iv:
                WeakReference<HomeDetailListBean> wf3 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf3.get().setType(Constants.PULISHTYPE_SHX);
                wf3.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTSHDetailActivity.this, wf3.get());
                break;
            case R.id.activity_detail_bszn_iv:
                if (null == subjectCount) {
                    return;
                }

                //无法退出自己的商会
                if (userId == shId) {
                    ToastUtils.showOneToast(getApplicationContext(), "不能退出自己的商会");
                    return;
                }

                if (subjectCount.getApply_status() != 1) {//-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
                    ToastUtils.showOneToast(getApplicationContext(), "对不起，您还不是该商会的会员无法退出商会");
                    return;
                }


                DialogUtils.getInstance().AlertMessage(SHTSHDetailActivity.this, "", "确定退出商会？", "容我三思", "心意已决", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tcsh();
                    }
                });
                break;
        }
    }

}
