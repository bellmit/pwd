package com.tastebychance.sfj.apply.myapply;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.bean.MyApplyDetailBean;
import com.tastebychance.sfj.apply.bean.ToApplyDetailBean;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.MyListView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 申请审核-我的申请-详情
 *
 * @author shenbinghong
 * @time 2018/2/6 14:35
 * @path com.tastebychance.sfj.apply.ExamineStatusActivity.java
 */
public class ExamineStatusActivity extends MyAppCompatActivity {

    @BindView(R.id.mine_topblank_iv)
    View mineTopblankIv;
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
    @BindView(R.id.activity_examine_status_status_tv)
    TextView activityExamineStatusStatusTv;
    @BindView(R.id.activity_examine_status_leavetype_tv)
    TextView activityExamineStatusLeavetypeTv;
    @BindView(R.id.activity_examine_status_applydate_tv)
    TextView activityExamineStatusApplydateTv;
    @BindView(R.id.activity_examine_status_applydays_tv)
    TextView activityExamineStatusApplydaysTv;
    @BindView(R.id.activity_examine_status_applyfromdate_todate_tv)
    TextView activityExamineStatusApplyfromdateTodateTv;
    @BindView(R.id.activity_examine_status_reason_tv)
    TextView activityExamineStatusReasonTv;
    @BindView(R.id.mlv)
    MyListView mlv;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;


    private static class MyHandler<T extends Activity> extends MyBaseHandler {
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
                            MyApplyDetailBean.DataBean dataBean = resInfo.getClass(MyApplyDetailBean.DataBean.class);
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusStatusTv, dataBean.getStatus());
                            if (!TextUtils.isEmpty(dataBean.getStatus())){
                                if (dataBean.getStatus().contains(Constants.PASS)){
                                    ((ExamineStatusActivity)t).activityExamineStatusStatusTv.setTextColor(t.getResources().getColor(R.color.green));
                                }else if (dataBean.getStatus().contains(Constants.REJECT)){
                                    ((ExamineStatusActivity)t).activityExamineStatusStatusTv.setTextColor(t.getResources().getColor(R.color.red));
                                }else{
                                    ((ExamineStatusActivity)t).activityExamineStatusStatusTv.setTextColor(t.getResources().getColor(R.color.colorPrimaryDark));
                                }
                            }
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusLeavetypeTv, dataBean.getH_type());
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusApplydateTv, dataBean.getAdd_time());
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusApplydaysTv, dataBean.getH_holiday_days());
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusApplyfromdateTodateTv, dataBean.getBegin() + "至" + dataBean.getEnd());
                            ((ExamineStatusActivity)t).setTextValue(((ExamineStatusActivity)t).activityExamineStatusReasonTv, dataBean.getTextarea());

                            if (null != ((ExamineStatusActivity)t).emptyLayout){
                                if (resInfo.getListByKey(Constants.PROCESS, MyApplyDetailBean.DataBean.ProcessBean.class).size() == 0){
                                    emptyLayoutShowOrHide(((ExamineStatusActivity) t).emptyLayout, true);
                                }else {
                                    emptyLayoutShowOrHide(((ExamineStatusActivity) t).emptyLayout, false);
                                }
                            }
                            final List<MyApplyDetailBean.DataBean.ProcessBean> processBeans = resInfo.getListByKey(Constants.PROCESS, MyApplyDetailBean.DataBean.ProcessBean.class);
                            ((ExamineStatusActivity)t).mlv.setAdapter(new CommonAdapter<MyApplyDetailBean.DataBean.ProcessBean>(
                                    t.getApplicationContext(), processBeans, R.layout.item_exainestatusactivity_process
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, MyApplyDetailBean.DataBean.ProcessBean item) {
                                    ImageView statusIv = viewHolder.getView(R.id.item_exainestatusa_status_iv);
                                    //0 处理中；1处理完成； 3被驳回
                                    if (item.getStatus() == Constants.EXAMINE_REJECT){
                                        ((ExamineStatusActivity)t).setImageView(statusIv, R.drawable.lose);
                                    }else if (item.getStatus() == Constants.EXAMINE_SUCCESS){
                                        ((ExamineStatusActivity)t).setImageView(statusIv, R.drawable.pass);
                                    }else {
                                        ((ExamineStatusActivity)t).setImageView(statusIv, R.drawable.wait);
                                    }
                                    if (viewHolder.getPosition() == processBeans.size() - 1 && processBeans.size() != 1){
                                        ((ExamineStatusActivity)t).setImageView(statusIv, R.drawable.start);
                                    }

                                    TextView nameTv = viewHolder.getView(R.id.item_exainestatusa_name_tv);
                                    nameTv.setTextColor(t.getResources().getColor(R.color.gray));
                                    ((ExamineStatusActivity)t).setTextValue(nameTv, item.getCheck_name());
                                    TextView dateTv = viewHolder.getView(R.id.item_exainestatusa_date_tv);
                                    dateTv.setTextColor(t.getResources().getColor(R.color.gray));
                                    ((ExamineStatusActivity)t).setTextValue(dateTv, item.getAdd_time());

                                    TextView reasonTv = viewHolder.getView(R.id.item_exainestatusa_reason_tv);
                                    if (TextUtils.isEmpty(item.getReason())){
                                        reasonTv.setVisibility(View.GONE);
                                    }else {
                                        reasonTv.setVisibility(View.VISIBLE);
                                        reasonTv.setText(item.getReason());
                                        reasonTv.setTextColor(t.getResources().getColor(R.color.lightgray));
                                    }
                                }
                            });

                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setImageView(@NonNull ImageView imageView, int id){
        imageView.setImageResource(id);
    }
    private void setTextValue(@NonNull TextView textView, String value){
        textView.setText(value);
    }

    private MyHandler handler = new MyHandler(this);


    private ToApplyDetailBean toApplyDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_status);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            toApplyDetailBean = (ToApplyDetailBean) getIntent().getSerializableExtra(Constants.KEY_TOMYAPPLYDETAIL);
        }

        setTitle();
        getData();
    }

    private void getData() {
        if (null == toApplyDetailBean) {
            return;
        }

        if (null != emptyLayout) {
            emptyLayout.showLoading(R.drawable.iv_loading, Constants.LOADING);
        }
        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_APPLYDETAIL;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("id", toApplyDetailBean.getId() + "");
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

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
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);

                        if (null != emptyLayout) {
                            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    if (emptyLayout != null) {
                        emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        emptyLayout.showError();
                    }
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);

                if (emptyLayout != null) {
                    emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                    emptyLayout.showError();
                }
            }
        });
    }

    private void setTitle() {
        headCenterTitleTv.setText("审核情况");
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        ExamineStatusActivity.this.finish();
    }
}
