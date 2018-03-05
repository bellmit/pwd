package rongshanghui.tastebychance.com.rongshanghui.home.zsb.detail;

import android.app.Activity;
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
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.Constant;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailBSZNListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.SubjectCount;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb.CLSBActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clxz.CLXZActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：ZSBZJDetailActivity 首页-招商宝-国内商机-镇街信息
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/2 12:02
 * 修改人：
 * 修改时间：2017/12/2 12:02
 * 修改备注：
 *
 * @version 1.0
 */
public class ZSBZJDetailActivity extends MyBaseActivity {

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
    @BindView(R.id.activity_detail_fsnumshow_tv)
    TextView activityDetailFsnumshowTv;
    @BindView(R.id.activity_detail_fsnum_tv)
    TextView activityDetailFsnumTv;
    @BindView(R.id.activity_detail_fsnum_ll)
    LinearLayout activityDetailFsnumLl;
    @BindView(R.id.activity_detail_zcnumshow_tv)
    TextView activityDetailZcnumshowTv;
    @BindView(R.id.activity_detail_zcnum_tv)
    TextView activityDetailZcnumTv;
    @BindView(R.id.activity_detail_zcnum_ll)
    LinearLayout activityDetailZcnumLl;
    @BindView(R.id.activity_detail_shxnumshow_tv)
    TextView activityDetailShxnumshowTv;
    @BindView(R.id.activity_detail_shxnum_tv)
    TextView activityDetailShxnumTv;
    @BindView(R.id.activity_detail_shxnum_ll)
    LinearLayout activityDetailShxnumLl;
    @BindView(R.id.activity_detail_llnumshow_tv)
    TextView activityDetailLlNumshowTv;
    @BindView(R.id.activity_detail_llnum_tv)
    TextView activityDetailLlnumTv;
    @BindView(R.id.activity_detail_llnum_ll)
    LinearLayout activityDetailLlnumLl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.activity_detail_jj_iv)
    ImageView activityDetailJjIv;
    @BindView(R.id.activity_detail_zx_iv)
    ImageView activityDetailZxIv;
    @BindView(R.id.activity_detail_shx_iv)
    ImageView activityDetailShxIv;
    @BindView(R.id.activity_detail_lxwm_iv)
    ImageView activityDetailLxwmIv;
    @BindView(R.id.activity_detail_bszn_iv)
    ImageView activityDetailBsznIv;
    @BindView(R.id.activity_detail_clxz_iv)
    ImageView activityDetailClxzIv;
    @BindView(R.id.activity_detail_clsb_iv)
    ImageView activityDetailClsbIv;
    @BindView(R.id.activity_detail_head_iv)
    ImageView activityDetailHeadIv;
    @BindView(R.id.activity_zsbzjdetail_rootlayout)
    RelativeLayout activityZsbzjdetailRootlayout;

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
                            ((ZSBZJDetailActivity) t).subjectCount = resInfo.getClass(SubjectCount.class);
                            PicassoUtils.getinstance().loadNormalByPath(t, ((ZSBZJDetailActivity) t).subjectCount.getImage(), ((ZSBZJDetailActivity) t).activityDetailHeadIv);
                            ((ZSBZJDetailActivity) t).activityDetailFsnumshowTv.setText(((ZSBZJDetailActivity) t).subjectCount.getFans_count() + "");
                            ((ZSBZJDetailActivity) t).activityDetailZcnumshowTv.setText(((ZSBZJDetailActivity) t).subjectCount.getZc() + "");
                            ((ZSBZJDetailActivity) t).activityDetailShxnumshowTv.setText(((ZSBZJDetailActivity) t).subjectCount.getShx() + "");
                            ((ZSBZJDetailActivity) t).activityDetailLlNumshowTv.setText(((ZSBZJDetailActivity) t).subjectCount.getHits_count() + "");

                            ((ZSBZJDetailActivity) t).toDetailBean.setIsCared(((ZSBZJDetailActivity) t).subjectCount.getIs_cared());
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
        setContentView(R.layout.activity_home_zsb_zjdetail);
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
        activityDetailJjIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailJjIv, 97, 120));
        activityDetailZxIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailZxIv, 90, 120));
        activityDetailShxIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailShxIv, 151, 120));
        activityDetailLxwmIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailLxwmIv, 72, 112));
        activityDetailBsznIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailBsznIv, 91, 112));
        activityDetailClxzIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailClxzIv, 78, 112));
        activityDetailClsbIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailClsbIv, 89, 112));
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(/*StringUtil.isNotEmpty(toDetailBean.getTitle()) ? toDetailBean.getTitle() :*/ "镇街信息");
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
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_zsbzjdetail_rootlayout), new CustomLoadingFactory());
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
            loadingBar = LoadingBar.make(findViewById(R.id.activity_zsbzjdetail_rootlayout), new CustomLoadingFactory());
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

    @Override
    protected void onResume() {
        super.onResume();
        if (null != toDetailBean) {
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_detail_jj_iv, R.id.activity_detail_zx_iv, R.id.activity_detail_shx_iv, R.id.activity_detail_lxwm_iv, R.id.activity_detail_bszn_iv, R.id.activity_detail_clxz_iv, R.id.activity_detail_clsb_iv, R.id.activity_detail_head_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ZSBZJDetailActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (toDetailBean.getIsCared() == 0) {//是否已关注 0 ： 未关注 1 ：已关注
                    userCare(toDetailBean.getId() + "", true);//1 关注 2 取消关注
                } else {
                    userCare(toDetailBean.getId() + "", false);
                }
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
                SystemUtil.getInstance().intentToWeb2Activity(ZSBZJDetailActivity.this, wf.get());
                break;
            case R.id.activity_detail_zx_iv:
                WeakReference<HomeDetailListBean> wf2 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf2.get().setType(Constants.PULISHTYPE_ZX);
                wf2.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(ZSBZJDetailActivity.this, wf2.get());
                break;
            case R.id.activity_detail_shx_iv:
                WeakReference<HomeDetailListBean> wf3 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf3.get().setType(Constants.PULISHTYPE_ZSX);
                wf3.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(ZSBZJDetailActivity.this, wf3.get());
                break;
            case R.id.activity_detail_lxwm_iv:
                if (null == subjectCount) {
                    return;
                }
                SystemUtil.getInstance().contactUs(ZSBZJDetailActivity.this, subjectCount.getArea_code() + subjectCount.getMobile());
                break;
            case R.id.activity_detail_bszn_iv:
                WeakReference<HomeDetailListBean> wf4 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf4.get().setType(Constants.PULISHTYPE_ZN);
                wf4.get().setUserId(toDetailBean.getId());
                wf4.get().setTitle("办事指南");
                Intent intent = new Intent(ZSBZJDetailActivity.this, HomeDetailBSZNListActivity.class);
                intent.putExtra("homeDetailListBean", wf4.get());
                startActivity(intent);
                break;
            case R.id.activity_detail_clxz_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }

                if (null == toDetailBean) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }

                WeakReference<HomeDetailListBean> wf6 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf6.get().setUserId(toDetailBean.getId());
                intent = new Intent(ZSBZJDetailActivity.this, CLXZActivity.class);
                intent.putExtra("homeDetailListBean", wf6.get());
                startActivity(intent);
                break;
            case R.id.activity_detail_clsb_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }

                if (null == toDetailBean) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }
                WeakReference<HomeDetailListBean> wf5 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf5.get().setType(Constants.PULISHTYPE_SB);
                wf5.get().setUserId(toDetailBean.getId());
                wf5.get().setTitle("材料上报");
                Intent intent1 = new Intent(ZSBZJDetailActivity.this, CLSBActivity.class);
                intent1.putExtra("homeDetailListBean", wf5.get());
                startActivity(intent1);
                break;
            case R.id.activity_detail_head_iv:
                break;
        }
    }
}
