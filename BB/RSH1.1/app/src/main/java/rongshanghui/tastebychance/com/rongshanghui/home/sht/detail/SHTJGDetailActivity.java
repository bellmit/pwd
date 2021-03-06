package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail;

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
 * ????????????SHTJGDetailActivity ??????-?????????-????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/11/30 19:09
 * ????????????
 * ???????????????2017/11/30 19:09
 * ???????????????
 *
 * @version 1.0
 */
public class SHTJGDetailActivity extends MyBaseActivity {

    @BindView(R.id.mine_myfollow_underline)
    View mineMyfollowUnderline;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightiv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.activity_shtdetail_head_iv)
    ImageView activityShtdetailHeadIv;
    @BindView(R.id.activity_shtdetail_fsnumshow_tv)
    TextView activityShtdetailFsnumshowTv;
    @BindView(R.id.activity_shtdetail_fsnum_tv)
    TextView activityShtdetailFsnumTv;
    @BindView(R.id.activity_shtdetail_fsnum_ll)
    LinearLayout activityShtdetailFsnumLl;
    @BindView(R.id.activity_shtdetail_fangdainumshow_tv)
    TextView activityShtdetailFangdainumshowTv;
    @BindView(R.id.activity_shtdetail_fangdainum_tv)
    TextView activityShtdetailFangdainumTv;
    @BindView(R.id.activity_shtdetail_fangdainum_ll)
    LinearLayout activityShtdetailFangdainumLl;
    @BindView(R.id.activity_shtdetail_llshow_tv)
    TextView activityShtdetailLlshowTv;
    @BindView(R.id.activity_shtdetail_llnum_tv)
    TextView activityShtdetailLlnumTv;
    @BindView(R.id.activity_shtdetail_llnum_ll)
    LinearLayout activityShtdetailLlnumLl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.activity_shtdetail_jianjie_iv)
    ImageView activityShtdetailJianjieIv;
    @BindView(R.id.activity_shtdetail_zixun_iv)
    ImageView activityShtdetailZixunIv;
    @BindView(R.id.activity_shtdetail_banlixinyongka_iv)
    ImageView activityShtdetailBanlixinyongkaIv;
    @BindView(R.id.activity_shtdetail_gerenxinyongdai_iv)
    ImageView activityShtdetailGerenxinyongdaiIv;
    @BindView(R.id.activity_shtdetail_cailiaoxiazai_iv)
    ImageView activityShtdetailCailiaoxiazaiIv;
    @BindView(R.id.activity_shtdetail_cailiaoshangbao_iv)
    ImageView activityShtdetailCailiaoshangbaoIv;
    @BindView(R.id.activity_shtdetail_banshizhinan_iv)
    ImageView activityShtdetailBanshizhinanIv;
    @BindView(R.id.activity_shtdetail_fangdaixinxi_iv)
    ImageView activityShtdetailFangdaixinxiIv;
    @BindView(R.id.activity_shtjgdetail)
    RelativeLayout activityShtjgdetail;


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
                            ((SHTJGDetailActivity) t).subjectCount = resInfo.getClass(SubjectCount.class);
                            PicassoUtils.getinstance().loadNormalByPath(t, ((SHTJGDetailActivity) t).subjectCount.getImage(), ((SHTJGDetailActivity) t).activityShtdetailHeadIv);
                            ((SHTJGDetailActivity) t).activityShtdetailFsnumshowTv.setText(((SHTJGDetailActivity) t).subjectCount.getFans_count() + "");
                            ((SHTJGDetailActivity) t).activityShtdetailFangdainumshowTv.setText(((SHTJGDetailActivity) t).subjectCount.getShx() + "");
                            ((SHTJGDetailActivity) t).activityShtdetailLlnumTv.setText(((SHTJGDetailActivity) t).subjectCount.getHits_count() + "");

                            ((SHTJGDetailActivity) t).toDetailBean.setIsCared(((SHTJGDetailActivity) t).subjectCount.getIs_cared());
                            ((SHTJGDetailActivity) t).updateIsCared();
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
        setContentView(R.layout.activity_home_sht_jgdetail);
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
        activityShtdetailJianjieIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailJianjieIv, 97, 102));
        activityShtdetailZixunIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailZixunIv, 90, 102));
        activityShtdetailBanshizhinanIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailBanshizhinanIv, 151, 132));
        activityShtdetailBanlixinyongkaIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailBanlixinyongkaIv, 84, 168));
        activityShtdetailGerenxinyongdaiIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailGerenxinyongdaiIv, 102, 94));
        activityShtdetailFangdaixinxiIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailFangdaixinxiIv, 151, 64));
        activityShtdetailCailiaoxiazaiIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailCailiaoxiazaiIv, 121, 64));
        activityShtdetailCailiaoshangbaoIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityShtdetailCailiaoshangbaoIv, 130, 64));
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
//            headCenterTitleTv.setText(type + "??????");
            headCenterTitleTv.setText(/*StringUtil.isNotEmpty(toDetailBean.getTitle())? toDetailBean.getTitle():*/
                    SystemUtil.getInstance().castValueToType(toDetailBean.getToType()) + "??????");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            updateIsCared();
        }
    }

    /**
     * ??????????????????????????????
     * token 	??? 	string 	token???
     * id 	??? 	int 	??????????????????????????????id
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_shtjgdetail), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//??????okhttp3?????????????????????
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    /**
     * ????????????????????????
     * <p>
     * token 	??? 	string 	token???
     * user_id 	??? 	int 	??????????????????id
     * type 	??? 	int 	1 ?????? 2 ????????????
     *
     * @param userId
     */
    private void userCare(String userId, final boolean isAddCare) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_shtjgdetail), new CustomLoadingFactory());
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
                                    toDetailBean.setIsCared(1); //??????????????? 0 ??? ????????? 1 ????????????
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    private void updateIsCared() {
        if (toDetailBean.getIsCared() == 0) {//??????????????? 0 ??? ????????? 1 ????????????
            headRightTv.setText("?????????");
        } else {
            headRightTv.setText("?????????");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != toDetailBean) {
            getData();
        }
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_shtdetail_jianjie_iv, R.id.activity_shtdetail_zixun_iv, R.id.activity_shtdetail_banlixinyongka_iv, R.id.activity_shtdetail_gerenxinyongdai_iv, R.id.activity_shtdetail_cailiaoxiazai_iv, R.id.activity_shtdetail_cailiaoshangbao_iv, R.id.activity_shtdetail_banshizhinan_iv, R.id.activity_shtdetail_fangdaixinxi_iv})
    public void onViewClicked(View view) {
        if (null == toDetailBean) {
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                SHTJGDetailActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (toDetailBean.getIsCared() == 0) {//??????????????? 0 ??? ????????? 1 ????????????
                    userCare(toDetailBean.getId() + "", true);//1 ?????? 2 ????????????
                } else {
                    userCare(toDetailBean.getId() + "", false);
                }
                break;
            case R.id.activity_shtdetail_jianjie_iv:
                if (null == subjectCount) {
                    return;
                }

                WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                wf.get().setTitle("??????");
                wf.get().setJJ(true);
                wf.get().setVideoUrl(subjectCount.getVideo());
                wf.get().setUrl(subjectCount.getIntro() + SystemUtil.getInstance().getJJVideoType(subjectCount.getVideo()));
                SystemUtil.getInstance().intentToWeb2Activity(SHTJGDetailActivity.this, wf.get());
                break;
            case R.id.activity_shtdetail_zixun_iv:
                WeakReference<HomeDetailListBean> wf2 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf2.get().setType(Constants.PULISHTYPE_ZX);
                wf2.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTJGDetailActivity.this, wf2.get());
                break;
            case R.id.activity_shtdetail_banlixinyongka_iv:
                if (StringUtil.isEmpty(subjectCount.getCreditcard())) {
                    ToastUtils.showOneToast(getApplicationContext(), "??????????????????");
                    return;
                }
                SystemUtil.getInstance().intentToSystemBrowser(SHTJGDetailActivity.this, subjectCount.getCreditcard());
                break;
            case R.id.activity_shtdetail_gerenxinyongdai_iv:
                if (StringUtil.isEmpty(subjectCount.getPersonalcredit())) {
                    ToastUtils.showOneToast(getApplicationContext(), "??????????????????");
                    return;
                }
                SystemUtil.getInstance().intentToSystemBrowser(SHTJGDetailActivity.this, subjectCount.getPersonalcredit());
                break;
            case R.id.activity_shtdetail_cailiaoxiazai_iv:
                if (null == toDetailBean) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }

                WeakReference<HomeDetailListBean> wf6 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf6.get().setUserId(toDetailBean.getId());
                intent = new Intent(SHTJGDetailActivity.this, CLXZActivity.class);
                intent.putExtra("homeDetailListBean", wf6.get());
                startActivity(intent);
                break;
            case R.id.activity_shtdetail_cailiaoshangbao_iv:
                if (null == toDetailBean) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }
                WeakReference<HomeDetailListBean> wf5 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf5.get().setType(Constants.PULISHTYPE_SB);
                wf5.get().setUserId(toDetailBean.getId());
                wf5.get().setTitle("????????????");
                Intent intent1 = new Intent(SHTJGDetailActivity.this, CLSBActivity.class);
                intent1.putExtra("homeDetailListBean", wf5.get());
                startActivity(intent1);
                break;
            case R.id.activity_shtdetail_banshizhinan_iv:
                WeakReference<HomeDetailListBean> wf4 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf4.get().setType(Constants.PULISHTYPE_ZN);
                wf4.get().setUserId(toDetailBean.getId());
                wf4.get().setTitle("????????????");
                intent = new Intent(SHTJGDetailActivity.this, HomeDetailBSZNListActivity.class);
                intent.putExtra("homeDetailListBean", wf4.get());
                startActivity(intent);
                break;
            case R.id.activity_shtdetail_fangdaixinxi_iv:
                WeakReference<HomeDetailListBean> wf3 = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf3.get().setType(Constants.PULISHTYPE_FDXX);
                wf3.get().setUserId(toDetailBean.getId());
                SystemUtil.getInstance().intentToHomeDetailListActivity(SHTJGDetailActivity.this, wf3.get());
                break;
        }
    }
}
