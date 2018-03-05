package com.tastebychance.sfj.apply.mywaitdealwith;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.mywaitdealwith.bean.UserJsonBean;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.commonpopupwindow.CommonPopupWindow;

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
 * 变更审核人
 *
 * @author shenbinghong
 * @time 2018/2/7 16:31
 * @path com.tastebychance.sfj.apply.mywaitdealwith.ChangeAuditorActivity.java
 */
public class ChangeAuditorActivity extends MyAppCompatActivity {

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
    @BindView(R.id.activity_apply_item_changetype_tv)
    TextView activityApplyItemChangetypeTv;
    @BindView(R.id.activity_apply_item_leadertype_tv)
    TextView activityApplyItemLeadertypeTv;
    @BindView(R.id.activity_apply_item_job_tv)
    TextView activityApplyItemJobTv;
    @BindView(R.id.activity_apply_item_jurisdiction_tv)
    TextView activityApplyItemJurisdictionTv;
    @BindView(R.id.activity_apply_item_name_tv)
    TextView activityApplyItemNameTv;
    @BindView(R.id.activity_apply_item_changereason_tv)
    TextView activityApplyItemChangereasonTv;
    @BindView(R.id.activity_login_remember_cb)
    CheckBox activityLoginRememberCb;
    @BindView(R.id.activity_login_remember_tv)
    TextView activityLoginRememberTv;
    @BindView(R.id.activity_apply_item_otherreason_ll)
    LinearLayout activityApplyItemOtherreasonLl;
    @BindView(R.id.activity_apply_item_otherreason_edt)
    EditText activityApplyItemOtherreasonEdt;

    private List<UserJsonBean.DataBeanXXX> types;
    private List<UserJsonBean.DataBeanXXX.DataBeanXX> leaders;
    private List<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX> jobs;
    private List<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX.DataBean> names;
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
                        case Constants.WHAT_GETDATA:
                            ((ChangeAuditorActivity)t).types = resInfo.getDataList(UserJsonBean.DataBeanXXX.class);
                            try {
                                SystemUtil.getInstance().saveObjectToSP(Constants.KEY_EXAMINETYPE, resInfo.getDataList(UserJsonBean.DataBeanXXX.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

    private int affairId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_auditor);
        ButterKnife.bind(this);

        if (getIntent() != null){
            affairId = getIntent().getIntExtra(Constants.KEY_AFFAIRID, 0);
        }
        setTitle();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.getInstance().saveObjectToSP(Constants.KEY_EXAMINETYPE, null);
    }

    private void getData() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_change_auditor_rootlayout));

        final String url = UrlManager.URL_USERJSON;
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
        headCenterTitleTv.setText("变更审核人");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("确认");
    }

    private CommonPopupWindow listViewPopupWindow;
    private void showListViewPopupWindow(@NonNull final TextView showTv, final List<String> list) {
        listViewPopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        final TextView confirmTv =  view.findViewById(R.id.popupwindow_listview_confirm_tv);
                        final ListView listView =  view.findViewById(R.id.popupwindow_listview_lv);

                        confirmTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                setContent(showTv);
                                if (listViewPopupWindow != null) {
                                    listViewPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        listView.setAdapter(new CommonAdapter<String>(getApplicationContext(), list, R.layout.popupwindow_listview_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, String item) {
                                TextView itemTv = viewHolder.getView(R.id.popupwindow_item_tv);
                                itemTv.setText(item);
                                itemTv.setTextColor(getResources().getColor(R.color.font_blue));
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setContent(showTv, list.get(position));
                                if (listViewPopupWindow != null) {
                                    listViewPopupWindow.dismiss();
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
        listViewPopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //type
    private CommonPopupWindow typePopupWindow;
    private CommonAdapter<UserJsonBean.DataBeanXXX> typeAdapter;
    private void showTypePopupWindow(@NonNull final TextView showTv, final List<UserJsonBean.DataBeanXXX> list) {
        typePopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        final ListView listView =  view.findViewById(R.id.popupwindow_listview_lv);

                        listView.setAdapter(typeAdapter = new CommonAdapter<UserJsonBean.DataBeanXXX>(getApplicationContext(), list, R.layout.popupwindow_listview_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, UserJsonBean.DataBeanXXX item) {
                                TextView itemTv = viewHolder.getView(R.id.popupwindow_item_tv);
                                itemTv.setText(item.getName());
                                itemTv.setTextColor(getResources().getColor(R.color.font_blue));
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setContent(showTv, list.get(position).getName());
                                if (typePopupWindow != null) {
                                    typePopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }

                                listClear(leaders);
                                listClear(jobs);
                                listClear(names);

                                resetTVContent(activityApplyItemLeadertypeTv);
                                resetTVContent(activityApplyItemJobTv);
                                resetTVContent(activityApplyItemNameTv);
                                leaders = list.get(position).getData();
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        typePopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void resetTVContent(@NonNull TextView textView){
        if (null != textView){
            textView.setText("--请选择--");
        }
    }
    private void listClear(List list){
        if (null != list){
            list.clear();
        }
    }

    //leader
    private CommonPopupWindow leaderPopupWindow;
    private CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX> leaderAdapter;
    private void showLeaderPopupWindow(@NonNull final TextView showTv, final List<UserJsonBean.DataBeanXXX.DataBeanXX> list) {
        leaderPopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        final ListView listView = view.findViewById(R.id.popupwindow_listview_lv);

                        listView.setAdapter(leaderAdapter = new CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX>(getApplicationContext(), list, R.layout.popupwindow_listview_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, UserJsonBean.DataBeanXXX.DataBeanXX item) {
                                TextView itemTv = viewHolder.getView(R.id.popupwindow_item_tv);
                                itemTv.setText(item.getName());
                                itemTv.setTextColor(getResources().getColor(R.color.font_blue));
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setContent(showTv, list.get(position).getName());
                                if (leaderPopupWindow != null) {
                                    leaderPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }

                                listClear(jobs);
                                listClear(names);
                                resetTVContent(activityApplyItemJobTv);
                                resetTVContent(activityApplyItemNameTv);
                                jobs = list.get(position).getData();
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        leaderPopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //job
    private CommonPopupWindow jobPopupWindow;
    private CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX> jobAdapter;
    private void showJobPopupWindow(@NonNull final TextView showTv, final List<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX> list) {
        jobPopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        final ListView listView = view.findViewById(R.id.popupwindow_listview_lv);

                        listView.setAdapter(jobAdapter = new CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX>(getApplicationContext(), list, R.layout.popupwindow_listview_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX item) {
                                TextView itemTv = viewHolder.getView(R.id.popupwindow_item_tv);
                                itemTv.setText(item.getName());
                                itemTv.setTextColor(getResources().getColor(R.color.font_blue));
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setContent(showTv, list.get(position).getName());
                                if (jobPopupWindow != null) {
                                    jobPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }

                                listClear(names);
                                resetTVContent(activityApplyItemNameTv);
                                names = list.get(position).getData();
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.3f);
        jobPopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    //name
    private CommonPopupWindow namePopupWindow;
    private CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX.DataBean> nameAdapter;
    private int userId;
    private void showNamePopupWindow(@NonNull final TextView showTv, final List<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX.DataBean> list) {
        namePopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.anim.ppwindow_show_anim)
                //设置背景颜色,取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                .setBackGroundLevel(0.3f)
                //设置popupwindow里子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {

                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        final ListView listView =  view.findViewById(R.id.popupwindow_listview_lv);

                        listView.setAdapter(nameAdapter = new CommonAdapter<UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX.DataBean>(getApplicationContext(), list, R.layout.popupwindow_listview_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, UserJsonBean.DataBeanXXX.DataBeanXX.DataBeanX.DataBean item) {
                                TextView itemTv = viewHolder.getView(R.id.popupwindow_item_tv);
                                itemTv.setText(item.getName());
                                itemTv.setTextColor(getResources().getColor(R.color.font_blue));
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setContent(showTv, list.get(position).getName());
                                userId = list.get(position).getValue();
                                if (namePopupWindow != null) {
                                    namePopupWindow.dismiss();
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
        namePopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 变更原因下拉框
     */
    private CommonPopupWindow reasonPopupWindow;
    private void showReasonPopupWindow() {
        reasonPopupWindow = new CommonPopupWindow.Builder(ChangeAuditorActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_changeauditor_reason)
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
                        final TextView outTv =  view.findViewById(R.id.popupwindow_changeauditor_reason_out_tv);
                        final TextView processErrorTv =  view.findViewById(R.id.popupwindow_changeauditor_reason_processerror_tv);
                        final TextView jobAdjustmentTv =  view.findViewById(R.id.popupwindow_changeauditor_reason_jobadjustment_tv);
                        final TextView otherTv =  view.findViewById(R.id.popupwindow_changeauditor_reason_other_tv);
                        outTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContent(activityApplyItemChangereasonTv, outTv.getText().toString());
                                if (reasonPopupWindow != null) {
                                    reasonPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        processErrorTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContent(activityApplyItemChangereasonTv, processErrorTv.getText().toString());
                                if (reasonPopupWindow != null) {
                                    reasonPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });


                        jobAdjustmentTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContent(activityApplyItemChangereasonTv, jobAdjustmentTv.getText().toString());
                                if (reasonPopupWindow != null) {
                                    reasonPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        otherTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContent(activityApplyItemChangereasonTv, otherTv.getText().toString());
                                if (reasonPopupWindow != null) {
                                    reasonPopupWindow.dismiss();
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

        reasonPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (null != activityApplyItemChangereasonTv && activityApplyItemChangereasonTv.getText().toString().equals("其他")) {
                    activityApplyItemOtherreasonLl.setVisibility(View.VISIBLE);
                }else {
                    activityApplyItemOtherreasonLl.setVisibility(View.GONE);
                }
            }
        });
        reasonPopupWindow.showAtLocation(this.findViewById(R.id.activity_change_auditor_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void setContent(@NonNull TextView textView, String str){
        if (TextUtils.isEmpty(str)){
            textView.setText("请选择");
            return;
        }
        textView.setText(str);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    private boolean canCommit(){
        if (TextUtils.isEmpty(activityApplyItemChangetypeTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择类型");
            return false;
        }

        if (TextUtils.isEmpty(activityApplyItemLeadertypeTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择部门/领导");
            return false;
        }

        if (TextUtils.isEmpty(activityApplyItemJobTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择职务");
            return false;
        }

        if (TextUtils.isEmpty(activityApplyItemNameTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择姓名");
            return false;
        }

        if (TextUtils.isEmpty(activityApplyItemJurisdictionTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择权限");
            return false;
        }

        if (TextUtils.isEmpty(activityApplyItemChangereasonTv.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), "请选择原因");
            return false;
        }

        if (activityApplyItemChangereasonTv.getText().toString().equals("其他")){
            activityApplyItemOtherreasonLl.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(activityApplyItemOtherreasonEdt.getText().toString())){
                ToastUtils.showOneToast(getApplicationContext(), "请输入其他原因");
                return false;
            }
        }

        return true;
    }

    /**
     * token	是	string	token值
     affair_id	是	int	事件id
     user_id	是	int	变更用户的id
     auth	是	string	权限
     change_reason	是	string	变更原因
     */
    private void commit(){
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_change_auditor_rootlayout));

        final String url = UrlManager.URL_CHANGEPROCESS;
        Map<String, String> map = new HashMap<String, String>(5);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("affair_id", affairId + "");
        map.put("user_id", userId + "");
        map.put("auth", activityApplyItemJurisdictionTv.getText().toString());
        map.put("change_reason", activityApplyItemChangereasonTv.getText().toString().equals("其他") ? activityApplyItemOtherreasonEdt.getText().toString() : activityApplyItemChangereasonTv.getText().toString());
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

    List<String> jurisdictions = new ArrayList<>();
    private void generateJrusdictions(){
        if (null == jurisdictions){
            jurisdictions = new ArrayList<>();
        } else {
            jurisdictions.clear();
        }
        jurisdictions.add("签字");
        jurisdictions.add("盖章");
        jurisdictions.add("签章");
        jurisdictions.add("审核");
        jurisdictions.add("归档");
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_login_remember_cb, R.id.activity_login_remember_tv,
            R.id.activity_apply_item_changetype_tv, R.id.activity_apply_item_leadertype_tv, R.id.activity_apply_item_job_tv, R.id.activity_apply_item_name_tv, R.id.activity_apply_item_jurisdiction_tv, R.id.activity_apply_item_changereason_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                ChangeAuditorActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canCommit()){
                    commit();
                }
                break;
            case R.id.activity_login_remember_cb:
                break;
            case R.id.activity_login_remember_tv:
                break;
            case R.id.activity_apply_item_changetype_tv:
                if (null != types){
                    types.clear();
                    try {
                        types = (List<UserJsonBean.DataBeanXXX>) SystemUtil.getInstance().getObjectFromSP(Constants.KEY_EXAMINETYPE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (null == types){
                    return;
                }
                showTypePopupWindow(activityApplyItemChangetypeTv, types);
                break;
            case R.id. activity_apply_item_leadertype_tv:
                if (null == leaders || leaders.size() == 0){
                    ToastUtils.showOneToast(getApplicationContext(), "请先选择类型");
                    return;
                }
                showLeaderPopupWindow(activityApplyItemLeadertypeTv, leaders);
                break;
            case R.id.activity_apply_item_job_tv:
                if (null == jobs || jobs.size() == 0){
                    ToastUtils.showOneToast(getApplicationContext(), "请先选择部门/领导");
                    return;
                }
                showJobPopupWindow(activityApplyItemJobTv, jobs);
                break;
            case R.id.activity_apply_item_name_tv:
                if (null == names || names.size() == 0){
                    ToastUtils.showOneToast(getApplicationContext(), "请先选择职务");
                    return;
                }
                showNamePopupWindow(activityApplyItemNameTv, names);
                break;
            case R.id.activity_apply_item_jurisdiction_tv:
//                showJurisdictionPopupWindow();
                if (null == jurisdictions || jurisdictions.size() <= 6){
                    generateJrusdictions();
                }
                showListViewPopupWindow(activityApplyItemJurisdictionTv, jurisdictions);
                break;
            case R.id.activity_apply_item_changereason_tv:
                showReasonPopupWindow();
                break;
            default:break;
        }
    }
}
