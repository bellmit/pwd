package com.tastebychance.sfj.apply.myapply;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.DateTimepickDialogUtils;
import com.tastebychance.sfj.util.SoftInputUtil;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.commonpopupwindow.CommonPopupWindow;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 申请
 *
 * @author shenbinghong
 * @time 2018/2/5 09:34
 * @path com.tastebychance.sfj.apply.ApplyActivity.java
 */
public class ApplyActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_apply_item_leavetype_tv)
    TextView activityApplyItemLeavetypeTv;
    @BindView(R.id.activity_apply_item_daytype_tv)
    TextView activityApplyItemDaytypeTv;
    @BindView(R.id.activity_apply_item_applytime_tv)
    TextView activityApplyItemApplytimeTv;
    @BindView(R.id.activity_apply_item_name_edt)
    EditText activityApplyItemNameEdt;
    @BindView(R.id.activity_apply_item_sex_tv)
    TextView activityApplyItemSexTv;
    @BindView(R.id.activity_apply_item_birthdate_tv)
    TextView activityApplyItemBirthdateTv;
    @BindView(R.id.activity_apply_item_job_edt)
    EditText activityApplyItemJobEdt;
    @BindView(R.id.activity_apply_item_leavedays_edt)
    EditText activityApplyItemLeavedaysEdt;
    @BindView(R.id.activity_apply_item_leavefromdate_tv)
    TextView activityApplyItemLeavefromdateTv;
    @BindView(R.id.activity_apply_item_leavetodate_tv)
    TextView activityApplyItemLeavetodateTv;
    @BindView(R.id.activity_apply_item_leavereason_edt)
    EditText activityApplyItemLeavereasonEdt;

    private static class MyHandler<T extends Activity> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t){
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_POSTDATA:
                            ToastUtils.showOneToast(t.getApplicationContext(), Constants.COMMIT_SUCCES);
                            t.finish();
                            break;
                        default:break;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_apply);

        ButterKnife.bind(this);

        setTitle();
    }

    private void setTitle() {
        headCenterTitleTv.setText("申请");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("确认");
    }

    //请假类型
    private CommonPopupWindow leaveTypePopupWindow;

    private void showLeaveTypePopupWindow() {
        leaveTypePopupWindow = new CommonPopupWindow.Builder(ApplyActivity.this)
                //设置popupwindow布局
                .setView(R.layout.popupwindow_apply_leavetype)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色，取值范围0.0f～1.0f， 值越小越暗，1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子view及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView cancel =  view.findViewById(R.id.popupwindow_item_leavetype_confirm_tv);
                        final TextView shijia =  view.findViewById(R.id.popupwindow_item_leavetype_shijia_tv);
                        final TextView bingjia =  view.findViewById(R.id.popupwindow_item_leavetype_bingjia_tv);
                        final TextView nianxiujia =  view.findViewById(R.id.popupwindow_item_leavetype_nianxiujia_tv);
                        final TextView shangjia =  view.findViewById(R.id.popupwindow_item_leavetype_shangjia_tv);
                        final TextView hunjia =  view.findViewById(R.id.popupwindow_item_leavetype_hunjia_tv);
                        final TextView chanjia =  view.findViewById(R.id.popupwindow_item_leavetype_chanjia_tv);
                        final TextView tanqinjia =  view.findViewById(R.id.popupwindow_item_leavetype_tanqinjia_tv);

                        shijia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(shijia.getText().toString());
                            }
                        });

                        bingjia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(bingjia.getText().toString());
                            }
                        });

                        nianxiujia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(nianxiujia.getText().toString());
                            }
                        });
                        shangjia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(shangjia.getText().toString());
                            }
                        });
                        hunjia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(hunjia.getText().toString());
                            }
                        });
                        chanjia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(chanjia.getText().toString());
                            }
                        });
                        tanqinjia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                                setLeaveType(tanqinjia.getText().toString());
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(leaveTypePopupWindow);
                            }
                        });
                    }
                })
                //设置外部师傅可以点击，默认是true
                .setOutsideTouchable(true)
                .create();
        setBackgroundAlpha(0.3f);
        leaveTypePopupWindow.showAtLocation(this.findViewById(R.id.activity_writefile_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void setLeaveType(String leaveType) {
        activityApplyItemLeavetypeTv.setText(leaveType);
    }

    //天数类型
    private CommonPopupWindow dayTypePopupWindow;
    private void showDayTypePopupWindow() {
        dayTypePopupWindow = new CommonPopupWindow.Builder(ApplyActivity.this)
                //设置popupwindow布局
                .setView(R.layout.popupwindow_apply_daytype)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色，取值范围0.0f～1.0f， 值越小越暗，1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子view及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView cancel =  view.findViewById(R.id.popupwindow_item_daytype_confirm_tv);
                        final TextView day05Type =  view.findViewById(R.id.popupwindow_item_daytype_05_tv);
                        final TextView day10Type =  view.findViewById(R.id.popupwindow_item_daytype_10_tv);
                        final TextView day15Type =  view.findViewById(R.id.popupwindow_item_daytype_15_tv);
                        final TextView day20Type =  view.findViewById(R.id.popupwindow_item_daytype_20_tv);
                        final TextView day25Type =  view.findViewById(R.id.popupwindow_item_daytype_25_tv);
                        final TextView day30Type =  view.findViewById(R.id.popupwindow_item_daytype_30_tv);
                        final TextView day35Type =  view.findViewById(R.id.popupwindow_item_daytype_35_tv);
                        final TextView day40Type =  view.findViewById(R.id.popupwindow_item_daytype_40_tv);
                        final TextView day45Type =  view.findViewById(R.id.popupwindow_item_daytype_45_tv);
                        final TextView day50Type =  view.findViewById(R.id.popupwindow_item_daytype_50_tv);
                        final TextView day55Type =  view.findViewById(R.id.popupwindow_item_daytype_55_tv);
                        final TextView day60Type =  view.findViewById(R.id.popupwindow_item_daytype_60_tv);

                        day05Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day05Type.getText().toString());
                            }
                        });

                        day10Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day10Type.getText().toString());
                            }
                        });

                        day15Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day15Type.getText().toString());
                            }
                        });
                        day20Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day20Type.getText().toString());
                            }
                        });
                        day25Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day25Type.getText().toString());
                            }
                        });
                        day30Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day30Type.getText().toString());
                            }
                        });
                        day35Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day35Type.getText().toString());
                            }
                        });
                        day40Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day40Type.getText().toString());
                            }
                        });
                        day45Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day45Type.getText().toString());
                            }
                        });
                        day50Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day50Type.getText().toString());
                            }
                        });
                        day55Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day55Type.getText().toString());
                            }
                        });
                        day60Type.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                                setDayType(day60Type.getText().toString());
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(dayTypePopupWindow);
                            }
                        });
                    }
                })
                //设置外部师傅可以点击，默认是true
                .setOutsideTouchable(true)
                .create();
        setBackgroundAlpha(0.3f);
        dayTypePopupWindow.showAtLocation(this.findViewById(R.id.activity_writefile_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void setDayType(String type) {
        activityApplyItemDaytypeTv.setText(type);
    }


    //性别类型
    private CommonPopupWindow sexPopupWindow;
    private void showSexPopupWindow() {
        sexPopupWindow = new CommonPopupWindow.Builder(ApplyActivity.this)
                //设置popupwindow布局
                .setView(R.layout.popupwindow_personalcenter_sex)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色，取值范围0.0f～1.0f， 值越小越暗，1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子view及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        final TextView malTv =  view.findViewById(R.id.personalcenter_sexpopupwindow_male_tv);
                        final TextView femaleTv =  view.findViewById(R.id.personalcenter_sexpopupwindow_female_tv);

                        malTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(sexPopupWindow);
                                setSexType(malTv.getText().toString());
                            }
                        });

                        femaleTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindowCancle(sexPopupWindow);
                                setSexType(femaleTv.getText().toString());
                            }
                        });
                    }
                })
                //设置外部师傅可以点击，默认是true
                .setOutsideTouchable(true)
                .create();
        setBackgroundAlpha(0.3f);
        sexPopupWindow.showAtLocation(this.findViewById(R.id.activity_writefile_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void setSexType(@NonNull String type){
        activityApplyItemSexTv.setText(type);
    }


    private void popupWindowCancle(@NonNull PopupWindow popupWindow) {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    private boolean canApply() {
        if (TextUtils.isEmpty(activityApplyItemLeavetypeTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemDaytypeTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemApplytimeTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemNameEdt.getText().toString())
                || TextUtils.isEmpty(activityApplyItemSexTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemBirthdateTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemJobEdt.getText().toString())
                || TextUtils.isEmpty(activityApplyItemLeavedaysEdt.getText().toString())
                || TextUtils.isEmpty(activityApplyItemLeavefromdateTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemLeavetodateTv.getText().toString())
                || TextUtils.isEmpty(activityApplyItemLeavereasonEdt.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), Constants.INPUT_HAS_EMPTY);
            return false;
        }
        return true;
    }

    /**
     token	是	string	token
     h_type	是	int	请假类型 1:事假，2:病假，3:年休假，4:丧假，5：婚假，6：产假，7：探亲假
     days	是	int	天数类型 1:0.5天，2:1天，3:1.5，4:2，5:2.5，6:3，7:3.5，8:4，9:4.5,10:5,11:5.5,12:6及以上
     name	是	string	姓名
     apply_time	是	string	申请时间
     sex	是	int	1:男 2：女
     birthday	是	string	2018.1
     work	是	string	现工作单位及职务
     h_holiday_days	是	int	请假天数
     begin	是	string	开始日期 例：01.14
     end	是	string	结束日期 例：01.16
     reason	是	string	请假理由及去处
     */
    private void apply() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_writefile_rootlayout));

        final String url = UrlManager.URL_APPLY;
        Map<String, String> map = new HashMap<String, String>(12);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("h_type", SystemUtil.getInstance().castLeaveTypeToInt(activityApplyItemLeavetypeTv.getText().toString()) + "");
        map.put("days", SystemUtil.getInstance().castDayTypeToInt(activityApplyItemDaytypeTv.getText().toString()) + "");
        map.put("name", activityApplyItemNameEdt.getText().toString());
        map.put("apply_time", activityApplyItemApplytimeTv.getText().toString());
        map.put("sex", activityApplyItemSexTv.getText().toString().equals("男") ? "1" : "2");
        map.put("birthday", activityApplyItemBirthdateTv.getText().toString());
        map.put("work", activityApplyItemJobEdt.getText().toString());
        map.put("h_holiday_days", activityApplyItemLeavedaysEdt.getText().toString());
        map.put("begin", activityApplyItemLeavefromdateTv.getText().toString());
        map.put("end", activityApplyItemLeavetodateTv.getText().toString());
        map.put("reason", activityApplyItemLeavereasonEdt.getText().toString());
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    CommonOkGo.getInstance().dialogCancel();
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = Constants.WHAT_POSTDATA;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                CommonOkGo.getInstance().dialogCancel();
                super.onError(call, response, e);
            }
        });
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_apply_item_leavetype_tv, R.id.activity_apply_item_daytype_tv, R.id.activity_apply_item_applytime_tv, R.id.activity_apply_item_sex_tv, R.id.activity_apply_item_birthdate_tv, R.id.activity_apply_item_leavefromdate_tv, R.id.activity_apply_item_leavetodate_tv})
    public void onViewClicked(View view) {
        DateTimepickDialogUtils mDTPicker = new DateTimepickDialogUtils(this);
        mDTPicker.setDateFormat("yyyy-MM-dd");
        mDTPicker.setmTheme(1);
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
            case R.id.head_right_tv:
                if (canApply()) {
                    apply();
                }
                break;
            case R.id.activity_apply_item_leavetype_tv:
                showLeaveTypePopupWindow();
                break;
            case R.id.activity_apply_item_daytype_tv:
                showDayTypePopupWindow();
                break;
            case R.id.activity_apply_item_applytime_tv:
                mDTPicker.dateTimePickDialog(activityApplyItemApplytimeTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            case R.id.activity_apply_item_sex_tv:
                SoftInputUtil.hideSoftInput(this, activityApplyItemSexTv);
                showSexPopupWindow();
                break;
            case R.id.activity_apply_item_birthdate_tv:
                mDTPicker.dateTimePickDialog(activityApplyItemBirthdateTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            case R.id.activity_apply_item_leavefromdate_tv:
                mDTPicker.dateTimePickDialog(activityApplyItemLeavefromdateTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            case R.id.activity_apply_item_leavetodate_tv:
                mDTPicker.dateTimePickDialog(activityApplyItemLeavetodateTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            default:break;
        }
    }
}
