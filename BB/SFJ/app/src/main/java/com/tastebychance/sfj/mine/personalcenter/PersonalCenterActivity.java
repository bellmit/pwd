package com.tastebychance.sfj.mine.personalcenter;

import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.MyApplication;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.mine.personalcenter.bean.PersonalBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.DateTimepickDialogUtils;
import com.tastebychance.sfj.util.SoftInputUtil;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.commonpopupwindow.CommonPopupWindow;
import com.tastebychance.sfj.view.wheel.WheelView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 个人信息
 *
 * @author shenbinghong
 * @time 2018/1/31 17:08
 * @path com.tastebychance.sfj.mine.personalcenter.PersonalCenterActivity.java
 */
public class PersonalCenterActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_personalcenter_name_tv)
    TextView activityPersonalcenterNameTv;
    @BindView(R.id.activity_personalcenter_account_tv)
    TextView activityPersonalcenterAccountTv;
    @BindView(R.id.activity_personalcenter_sex_tv)
    TextView activityPersonalcenterSexTv;
    @BindView(R.id.activity_personalcenter_birthdate_tv)
    TextView activityPersonalcenterBirthdateTv;
    @BindView(R.id.activity_personalcenter_politicalclimate_tv)
    TextView activityPersonalcenterPoliticalclimateTv;

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
                            PersonalBean.DataBean personalBean = resInfo.getClass(PersonalBean.DataBean.class);
                            if (personalBean == null) {
                                return;
                            }

                            ((PersonalCenterActivity)t).activityPersonalcenterNameTv.setText(personalBean.getUser_name());
                            ((PersonalCenterActivity)t).activityPersonalcenterAccountTv.setText(personalBean.getUser_login());
                            ((PersonalCenterActivity)t).activityPersonalcenterSexTv.setText(personalBean.getSex() == 1 ? "男" : "女");
                            ((PersonalCenterActivity)t).activityPersonalcenterBirthdateTv.setText(personalBean.getBirthday());
                            ((PersonalCenterActivity)t).activityPersonalcenterPoliticalclimateTv.setText(SystemUtil.getInstance().castPolicsStatusToStr(personalBean.getPolitics_status()));
                            break;
                        case Constants.WHAT_POSTDATA:
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
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);

        setTitle();

        getData();
    }

    private void getData() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_personalcenter_rootlayout));
        final String url = UrlManager.URL_MINE_PERSONALINFO;
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
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

    private void setTitle() {
        headCenterTitleTv.setText("个人信息");
        headRightTv.setText("保存");
        headRightTv.setVisibility(View.VISIBLE);
    }

    /**
     * 性别下拉框
     */
    private CommonPopupWindow sexPopupWindow;

    private void showSexPopupWindow() {
        sexPopupWindow = new CommonPopupWindow.Builder(PersonalCenterActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_personalcenter_sex)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView male =  view.findViewById(R.id.personalcenter_sexpopupwindow_male_tv);
                        TextView female =  view.findViewById(R.id.personalcenter_sexpopupwindow_female_tv);
                        male.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityPersonalcenterSexTv.setText("男");
                                if (sexPopupWindow != null) {
                                    sexPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        female.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityPersonalcenterSexTv.setText("女");
                                if (sexPopupWindow != null) {
                                    sexPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        sexPopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    /**
     * 设置政治面貌
     */
    private void setPoliticalClimate() {
        initPoliticalClimate();
        getPoliticalClimatePopupWindow();
    }

    private PopupWindow politicalClimatePopupWindow;

    private void getPoliticalClimatePopupWindow() {
        if (null != politicalClimatePopupWindow) {
            //如果minzuPopupWindow正在显示，接下来隐藏
            if (politicalClimatePopupWindow.isShowing()) {
                politicalClimatePopupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                politicalClimatePopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        } else {
            initPoliticalClimatePopupWindow();
        }
    }

    private void initPoliticalClimatePopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View politicalClimatePopupWindow_view = inflater.inflate(R.layout.popupwindow_personalcenter_politicalclimate, null, false);
        politicalClimatePopupWindow = new PopupWindow(politicalClimatePopupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        politicalClimatePopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        politicalClimatePopupWindow.setBackgroundDrawable(new PaintDrawable());
        politicalClimatePopupWindow.setOutsideTouchable(true);
        politicalClimatePopupWindow.setFocusable(true);
        politicalClimatePopupWindow_view.setFocusable(true);
        politicalClimatePopupWindow_view.setFocusableInTouchMode(true);

        activityPersonalcenterPoliticalclimateTv.setText(politicalClimates.get(0));

        TextView minzuConfirmTv =  politicalClimatePopupWindow_view.findViewById(R.id.minzu_confirm_tv);
        final WheelView wheelView =  politicalClimatePopupWindow_view.findViewById(R.id.wheelview);
        wheelView.lists(politicalClimates)
                .fontSize(35)
                .showCount(9)
//                .selectTip("民族")
                .select(0)
                .listener(new WheelView.OnWheelViewItemSelectListener() {

                    @Override
                    public void onItemSelect(int index) {
                        System.out.println("wheelView.getSelectItem():" + wheelView.getSelectItem()
                                + "---index:" + index + "---minzus.get(index):" + politicalClimates.get(index));
                        activityPersonalcenterPoliticalclimateTv.setText(politicalClimates.get(index));
                    }
                }).build();

        minzuConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                politicalClimatePopupWindow.dismiss();
            }
        });

        politicalClimatePopupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    politicalClimatePopupWindow.dismiss();
                    politicalClimatePopupWindow = null;
                    return true;
                }
                return false;
            }
        });

        politicalClimatePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                politicalClimatePopupWindow = null;
            }
        });


        setBackgroundAlpha(0.3f);
        politicalClimatePopupWindow.showAtLocation(this.findViewById(R.id.activity_personalcenter_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    List<String> politicalClimates = new ArrayList<String>();

    private void initPoliticalClimate() {
        politicalClimates.add(Constants.POLICSSTATUS_ZGDY);
        politicalClimates.add(Constants.POLICSSTATUS_ZGYBDY );
        politicalClimates.add(Constants.POLICSSTATUS_GQTY );
        politicalClimates.add(Constants.POLICSSTATUS_MGDY );
        politicalClimates.add(Constants.POLICSSTATUS_MMMY);
        politicalClimates.add(Constants.POLICSSTATUS_MJIANHY);
        politicalClimates.add(Constants.POLICSSTATUS_MJINHY );
        politicalClimates.add(Constants.POLICSSTATUS_NGDDY );
        politicalClimates.add(Constants.POLICSSTATUS_ZGDDY );
        politicalClimates.add(Constants.POLICSSTATUS_JSXSSY );
        politicalClimates.add(Constants.POLICSSTATUS_TMMY);
        politicalClimates.add(Constants.POLICSSTATUS_WDPRS );
        politicalClimates.add(Constants.POLICSSTATUS_QZ );
    }

    private boolean canCommit(){
        if (TextUtils.isEmpty(SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN))){
            ToastUtils.showOneToast(MyApplication.getContext(), Constants.LOGIN_INVALID);
            return false;
        }
        if (TextUtils.isEmpty(activityPersonalcenterSexTv.getText().toString()) || TextUtils.isEmpty(activityPersonalcenterBirthdateTv.getText().toString()) || TextUtils.isEmpty(activityPersonalcenterPoliticalclimateTv.getText().toString())){
            ToastUtils.showOneToast(PersonalCenterActivity.this, Constants.INPUT_HAS_EMPTY);
            return false;
        }

        return true;
    }

    /**
     * token	是	string	token
     sex	是	int	1:男 2：女
     birthday	是	string	日期 例 2018-1-31 17:30:00
     politics_status	是	int	1 中共党员，2 中共预备党员，3共青团员，4 民革党员，5民盟盟员，6民建会员，7民进会员，8农工党党员，9致公党党员，10九三学社社员，11台盟盟员，12无党派人士，13群众（现称普通居民，与居民身份证相对应）
     */
    private void commit(){
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_personalcenter_rootlayout));

        final String url = UrlManager.URL_MINE_PERSONALEDIT;
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("sex", activityPersonalcenterSexTv.getText().toString().equals("男") ? "1" : "2");
        map.put("birthday", activityPersonalcenterBirthdateTv.getText().toString());
        map.put("politics_status", SystemUtil.getInstance().castPolicsStatusToInt(activityPersonalcenterPoliticalclimateTv.getText().toString()) + "");
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


    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_personalcenter_sex_tv, R.id.activity_personalcenter_birthdate_tv, R.id.activity_personalcenter_politicalclimate_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                PersonalCenterActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canCommit()){
                    commit();
                }
                break;
            case R.id.activity_personalcenter_sex_tv:
                SoftInputUtil.hideSoftInput(PersonalCenterActivity.this, activityPersonalcenterSexTv);
                showSexPopupWindow();
                break;
            case R.id.activity_personalcenter_birthdate_tv:
                DateTimepickDialogUtils mDTPicker = new DateTimepickDialogUtils(this);
                mDTPicker.setDateFormat("yyyy-MM-dd");
                mDTPicker.setmTheme(1);
                mDTPicker.dateTimePickDialog(activityPersonalcenterBirthdateTv, DateTimepickDialogUtils.TYPE.DATE);
                break;
            case R.id.activity_personalcenter_politicalclimate_tv:
                setPoliticalClimate();
                break;
            default:break;
        }
    }
}
