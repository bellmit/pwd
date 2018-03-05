package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail;

import android.app.Activity;
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
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：SHTQYDetailActivity 商会通-企业-企业信息
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/27 20:19
 * 修改人：
 * 修改时间：2017/11/27 20:19
 * 修改备注：
 *
 * @version 1.0
 */
public class SHTQYDetailActivity extends MyBaseActivity {

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
    ImageView activityDetailHeadIv;
    @BindView(R.id.activity_detail_hynumshow_tv)
    TextView activityDetailHynumshowTv;
    @BindView(R.id.activity_detail_hynum_tv)
    TextView activityDetailHynumTv;
    @BindView(R.id.activity_detail_hynum_ll)
    LinearLayout activityDetailHynumLl;
    @BindView(R.id.activity_detail_fsnumshow_tv)
    TextView activityDetailFsnumshowTv;
    @BindView(R.id.activity_detail_fsnum_tv)
    TextView activityDetailFsnumTv;
    @BindView(R.id.activity_detail_fsnum_ll)
    LinearLayout activityDetailFsnumLl;
    @BindView(R.id.activity_detail_shxnumshow_tv)
    TextView activityDetailShxnumshowTv;
    @BindView(R.id.activity_detail_shxnum_tv)
    TextView activityDetailShxnumTv;
    @BindView(R.id.activity_detail_shxnum_ll)
    LinearLayout activityDetailShxnumLl;
    @BindView(R.id.activity_detail_llshow_tv)
    TextView activityDetailLlshowTv;
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
    @BindView(R.id.activity_detail_rzxm_iv)
    ImageView activityDetailRzxmIv;
    @BindView(R.id.activity_detail_lxwm_iv)
    ImageView activityDetailLxwmIv;
    @BindView(R.id.activity_detail_qyfc_iv)
    ImageView activityDetailQyfcIv;
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
                            ((SHTQYDetailActivity) t).subjectCount = resInfo.getClass(SubjectCount.class);
                            PicassoUtils.getinstance().loadNormalByPath(t, ((SHTQYDetailActivity) t).subjectCount.getImage(), ((SHTQYDetailActivity) t).activityDetailHeadIv);
                            ((SHTQYDetailActivity) t).activityDetailHynumshowTv.setText(((SHTQYDetailActivity) t).subjectCount.getMember_count() + "");
                            ((SHTQYDetailActivity) t).activityDetailFsnumshowTv.setText(((SHTQYDetailActivity) t).subjectCount.getFans_count() + "");
                            ((SHTQYDetailActivity) t).activityDetailShxnumshowTv.setText(((SHTQYDetailActivity) t).subjectCount.getShx() + "");
                            ((SHTQYDetailActivity) t).activityDetailLlshowTv.setText(((SHTQYDetailActivity) t).subjectCount.getHits_count() + "");

                            ((SHTQYDetailActivity) t).toDetailBean.setIsCared(((SHTQYDetailActivity) t).subjectCount.getIs_cared());
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
        setContentView(R.layout.activity_home_sht_qydetail);
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
        activityDetailQyfcIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailQyfcIv, 151, 241));
        activityDetailRzxmIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailRzxmIv, 114, 112));
        activityDetailLxwmIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityDetailLxwmIv, 72, 112));
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(/*StringUtil.isNotEmpty(toDetailBean.getTitle()) ? toDetailBean.getTitle() :*/ "企业信息");
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

    @Override
    protected void onResume() {
        super.onResume();
        if (null != toDetailBean) {
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_detail_jj_iv, R.id.activity_detail_zx_iv, R.id.activity_detail_rzxm_iv, R.id.activity_detail_lxwm_iv, R.id.activity_detail_qyfc_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                SHTQYDetailActivity.this.finish();
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
                SystemUtil.getInstance().intentToWeb2Activity(SHTQYDetailActivity.this, wf.get());
                break;
            case R.id.activity_detail_zx_iv:
                WeakReference<HomeDetailListBean> wf2 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf2.get().setType(Constants.PULISHTYPE_ZX);
                wf2.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTQYDetailActivity.this, wf2.get());
                break;
            case R.id.activity_detail_rzxm_iv:
                WeakReference<HomeDetailListBean> wf4 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf4.get().setType(Constants.PULISHTYPE_RZXM);
                wf4.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTQYDetailActivity.this, wf4.get());
                break;
            case R.id.activity_detail_lxwm_iv:
                if (null == subjectCount) {
                    return;
                }
                SystemUtil.getInstance().contactUs(SHTQYDetailActivity.this, subjectCount.getArea_code() + subjectCount.getMobile());
                break;
            case R.id.activity_detail_qyfc_iv:
                WeakReference<HomeDetailListBean> wf3 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf3.get().setType(Constants.PULISHTYPE_QYFC);
                wf3.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTQYDetailActivity.this, wf3.get());
                break;
        }
    }
}
