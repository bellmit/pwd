package com.tastebychance.sfj.apply.mywaitdealwith;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.bean.ToApplyDetailBean;
import com.tastebychance.sfj.apply.mywaitdealwith.bean.KeyValueBean;
import com.tastebychance.sfj.apply.mywaitdealwith.bean.TipsBean;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
import com.tastebychance.sfj.util.ApplyDialog;
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
 * 类描述：申请审核-我的待办-处理
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/10/31 18:43
 * 修改人：
 * 修改时间：2017/10/31 18:43
 * 修改备注：
 *
 * @version 1.0
 */
public class WaitDealWithActivity extends MyAppCompatActivity {
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
    @BindView(R.id.webview)
    WebView webview;


    private static final int WHAT_GENERATE_EXAMINEDIALOGVIEW = 20001;
    private String initrRabc = "签章";
    private TipsBean tipsBean;
    private List<KeyValueBean> tipsList = new ArrayList<>();

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
                            ((WaitDealWithActivity) t).initrRabc = resInfo.convertDataToStr().replaceAll("\"", "");
                            break;
                        case Constants.WHAT_GETDATA2:
                            ((WaitDealWithActivity) t).tipsBean = resInfo.getClass(TipsBean.class);

                            WeakReference<KeyValueBean> wf = new WeakReference<KeyValueBean>(new KeyValueBean());
                            wf.get().setKey("用户属性");
                            wf.get().setObject(((WaitDealWithActivity) t).tipsBean.getAttr_type());
                            ((WaitDealWithActivity) t).tipsList.add(wf.get());

                            WeakReference<KeyValueBean> wf1 = new WeakReference<KeyValueBean>(new KeyValueBean());
                            wf1.get().setKey("已请天数");
                            wf1.get().setObject(((WaitDealWithActivity) t).tipsBean.getH_holiday_total());
                            ((WaitDealWithActivity) t).tipsList.add(wf1.get());

                            break;
                        case Constants.WHAT_POSTDATA:
                            t.finish();
                            break;
                        case Constants.WHAT_LOADING_SHOW:
                            try {
                                CommonOkGo.getInstance().generateLoading(((WaitDealWithActivity) t).findViewById(R.id.activity_waitdealwith_rootlayout));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case Constants.WHAT_LOADING_HIDE:
                            try {
                                CommonOkGo.getInstance().dialogCancel();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case WHAT_GENERATE_EXAMINEDIALOGVIEW:
                            ((WaitDealWithActivity) t).generateExamineDialog();
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

    private ToApplyDetailBean toApplyDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_webincludejs);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            toApplyDetailBean = (ToApplyDetailBean) getIntent().getSerializableExtra(Constants.KEY_TOMYAPPLYDETAIL);
        }

        builder = new ApplyDialog.Builder(WaitDealWithActivity.this);

        setTitle();
        setWebView();

        getData();
        getTipsData();

    }

    private void setTitle() {
        headCenterTitleTv.setText("处理");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("提示板");
    }

    private void setWebView() {
        //允许JavaScript执行
        webview.getSettings().setJavaScriptEnabled(true);
        //向js传递对象
        webview.addJavascriptInterface(new CallMe(), "toastandroid");
        //不会node的小伙伴可以保存到assets
//        webview.loadUrl("file:///android_asset/deal.html");
        //访问网页
//        webview.loadUrl("http://192.168.253.1:8000/");
        webview.loadUrl(toApplyDetailBean.getDetail());
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                WaitDealWithActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (null == tipsList || tipsList.size() <= 0) {
                    getTipsData();
                    return;
                }
                showTipsPopupwindow(tipsList);
                break;
            default:break;
        }
    }

    private CommonPopupWindow tipsPopupWindow;

    private void showTipsPopupwindow(final List<KeyValueBean> list) {
        tipsPopupWindow = new CommonPopupWindow.Builder(WaitDealWithActivity.this)
                //设置Popupwindow布局
                .setView(R.layout.popupwindow_listview_includehead)
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
                        ImageView headLeftIv = view.findViewById(R.id.head_left_iv);
                        headLeftIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tipsPopupWindow != null) {
                                    tipsPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                                WaitDealWithActivity.this.finish();
                            }
                        });

                        TextView headCenterTitleTv = view.findViewById(R.id.head_center_title_tv);
                        headCenterTitleTv.setText("处理");

                        TextView headRightTv = view.findViewById(R.id.head_right_tv);
                        headRightTv.setVisibility(View.VISIBLE);
                        headRightTv.setText("提示板");
                        headRightTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tipsPopupWindow != null) {
                                    tipsPopupWindow.dismiss();
                                    setBackgroundAlpha(1.0f);
                                }
                            }
                        });

                        final ListView listView = view.findViewById(R.id.popupwindow_listview_lv);
                        listView.setAdapter(new CommonAdapter<KeyValueBean>(getApplicationContext(), list, R.layout.popupwindow_listview_item_keyvalue) {
                            @Override
                            protected void convert(ViewHolder viewHolder, KeyValueBean item) {
                                try {
                                    TextView itemKeyTv = viewHolder.getView(R.id.popupwindow_item_key_tv);
                                    itemKeyTv.setText(item.getKey());
                                    itemKeyTv.setTextColor(getResources().getColor(R.color.gray));

                                    TextView itemValueTv = viewHolder.getView(R.id.popupwindow_item_value_tv);
                                    itemValueTv.setText(item.getObject() + "");
                                    itemValueTv.setTextColor(getResources().getColor(R.color.gray));
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可以点击，默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        setBackgroundAlpha(0.7f);
        tipsPopupWindow.showAsDropDown(this.findViewById(R.id.mine_topblank_iv), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }


    private View rejectDialogView;
    private ApplyDialog rejectDialog;

    private View examineDialogView;
    private ApplyDialog examineDialog;

    private ApplyDialog.Builder builder;

    public final class CallMe {
        //Html调用此方法传递数据，注解一定要留着否则会出错
        @JavascriptInterface
        public void callme1() {//变更审批人
            Intent intent = new Intent(WaitDealWithActivity.this, ChangeAuditorActivity.class);
            intent.putExtra(Constants.KEY_AFFAIRID, toApplyDetailBean.getId());
            WaitDealWithActivity.this.startActivity(intent);
        }

        @JavascriptInterface
        public void callme2() {//通过
            Message msg = new Message();
            msg.what = WHAT_GENERATE_EXAMINEDIALOGVIEW;
            handler.sendMessage(msg);
        }

        @JavascriptInterface
        public void callme3() {//驳回
            generateRejectDialog();
        }
    }

    private void generateRejectDialog() {
        if (builder == null) {
            builder = new ApplyDialog.Builder(WaitDealWithActivity.this);
        }
        rejectDialog = builder
                .style(R.style.Dialog)
                .heightDimenRes(R.dimen.dilog_identitychange_height)
                .widthDimenRes(R.dimen.dilog_identitychange_width)
                .cancelTouchout(false)
                .view(R.layout.dialog_applyreject)
                .addViewOnclick(R.id.dialog_confirm_tv, listener)
                .addViewOnclick(R.id.dialog_cancel_tv, listener)
                .build();
        rejectDialogView = builder.getView();
        rejectDialog.show();
    }

    private TextView examineDialogSignTv, examineDialogSealTv;

    private void generateExamineDialog() {
        if (builder == null) {
            builder = new ApplyDialog.Builder(WaitDealWithActivity.this);
        }
        examineDialog = builder
                .style(R.style.Dialog)
                .heightdp(240)
                .widthDimenRes(R.dimen.dilog_identitychange_width)
                .cancelTouchout(false)
                .view(R.layout.dialog_examine)
                .addViewOnclick(R.id.dialog_examine_confirm_tv, listener)
                .addViewOnclick(R.id.dialog_examine_cancel_tv, listener)
                .addViewOnclick(R.id.dialog_sign_tv, listener)
                .addViewOnclick(R.id.dialog_seal_tv, listener)
                .build();
        examineDialogView = builder.getView();

        examineDialogSignTv =  examineDialogView.findViewById(R.id.dialog_sign_tv);
        examineDialogSealTv =  examineDialogView.findViewById(R.id.dialog_seal_tv);
        if (TextUtils.isEmpty(initrRabc)) {
            return;
        }
        if (initrRabc.equals("签字")) {
            examineDialogSignTv.setVisibility(View.VISIBLE);
            examineDialogSealTv.setVisibility(View.GONE);
        } else if (initrRabc.equals("盖章")) {
            examineDialogSignTv.setVisibility(View.GONE);
            examineDialogSealTv.setVisibility(View.VISIBLE);
        } else if (initrRabc.equals("签章")) {
            examineDialogSignTv.setVisibility(View.VISIBLE);
            examineDialogSealTv.setVisibility(View.VISIBLE);
        } else {
            examineDialogSignTv.setVisibility(View.GONE);
            examineDialogSealTv.setVisibility(View.GONE);
        }

        examineDialog.show();
    }

    private void dialogCancel(@NonNull ApplyDialog dialog) {
        dialog.dismiss();
    }


    /**
     * 提示板
     */
    private void getTipsData() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_waitdealwith_rootlayout));

        final String url = UrlManager.URL_GETTIPS;
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
                                msg.what = Constants.WHAT_GETDATA2;
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

    /**
     * token	是	string	token值
     * id	是	int	事件id
     */
    private void getData() {
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_waitdealwith_rootlayout));

        final String url = UrlManager.URL_RABC;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("id", toApplyDetailBean.getId() + "");
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

    private boolean canCommit(int status) {
        if (status == Constants.EXAMINE_SUCCESS){
            if (TextUtils.isEmpty(((EditText) examineDialogView.findViewById(R.id.dialog_examine_reason_edt)).getText().toString())) {
                ToastUtils.showOneToast(getApplicationContext(), Constants.NULL_CONTENT);
                return false;
            }
        }

        if (status == Constants.EXAMINE_REJECT){
            if (TextUtils.isEmpty(((EditText) rejectDialogView.findViewById(R.id.dialog_reason_edt)).getText().toString())) {
                ToastUtils.showOneToast(getApplicationContext(), Constants.NULL_CONTENT);
                return false;
            }
        }


        return true;
    }

    /**
     * token	是	string	token值
     * id	是	int	事件id
     * type	是	int	1:签名 2：盖章
     * status	是	int	1：通过 3：驳回
     * text 否	string	通过文字
     * es_pass	是	string	电子签章密码
     * reason	否	string	驳回理由 驳回时传
     */
    private int status = Constants.EXAMINE_SUCCESS;
    private String reason = "";
    private String es_pass = "";

    private void commit() {
        Message message = new Message();
        message.what = Constants.WHAT_LOADING_SHOW;
        handler.sendMessage(message);

        final String url = UrlManager.URL_CHECK;
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("id", toApplyDetailBean.getId() + "");
        map.put("type", type + "");
        map.put("status", status + "");
        if (null != examineDialogView) {
            if (status == Constants.EXAMINE_SUCCESS && !TextUtils.isEmpty(((EditText) examineDialogView.findViewById(R.id.dialog_examine_reason_edt)).getText().toString())) {
                map.put("text", ((EditText) examineDialogView.findViewById(R.id.dialog_examine_reason_edt)).getText().toString());
            }
        }
        map.put("es_pass", es_pass + "");
        if (null != rejectDialogView) {
            if (status == Constants.EXAMINE_REJECT && !TextUtils.isEmpty(((EditText) rejectDialogView.findViewById(R.id.dialog_reason_edt)).getText().toString())) {
                map.put("reason", ((EditText) rejectDialogView.findViewById(R.id.dialog_reason_edt)).getText().toString());
            }
        }

        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    Message message1 = new Message();
                    message1.what = Constants.WHAT_LOADING_HIDE;
                    handler.sendMessage(message1);

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
                Message message1 = new Message();
                message1.what = Constants.WHAT_LOADING_HIDE;
                handler.sendMessage(message1);
                super.onError(call, response, e);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String returnType = data.getStringExtra("type");
            es_pass = data.getStringExtra("pwd");
            if (TextUtils.isEmpty(returnType)) {
                return;
            }

            try {
                if (null == examineDialogView) {
                    generateExamineDialog();
                }
                if (null == examineDialogSignTv) {
                    examineDialogSignTv =  examineDialogView.findViewById(R.id.dialog_sign_tv);
                }
                if (null == examineDialogSealTv) {
                    examineDialogSealTv =  examineDialogView.findViewById(R.id.dialog_seal_tv);
                }
                if (returnType.equals("sign")) {
                    examineDialogSignTv.setText("已签字");
                    examineDialogSealTv.setText("盖章");
                    type = 1;
                } else if (returnType.equals("seal")) {
                    examineDialogSignTv.setText("签字");
                    examineDialogSealTv.setText("已盖章");
                    type = 2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int type = -1;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.dialog_confirm_tv://驳回
                    if (null != rejectDialogView) {
                        reason = ((EditText) rejectDialogView.findViewById(R.id.dialog_reason_edt)).getText().toString();
                    }
                    status = Constants.EXAMINE_REJECT;
                    dialogCancel(rejectDialog);

                    if (canCommit(status)) {
                        commit();
                    }
                    break;
                case R.id.dialog_cancel_tv:
                    dialogCancel(rejectDialog);
                    break;
                case R.id.dialog_examine_confirm_tv://通过
                    status = Constants.EXAMINE_SUCCESS;
                    dialogCancel(examineDialog);

                    if (canCommit(status)) {
                        commit();
                    }
                    break;
                case R.id.dialog_examine_cancel_tv:
                    dialogCancel(examineDialog);
                    break;
                case R.id.dialog_sign_tv://1:签名 2：盖章
                    intent = new Intent(WaitDealWithActivity.this, CheckPWDActivity.class);
                    intent.putExtra("type", "sign");
                    startActivityForResult(intent, 1);

                    break;
                case R.id.dialog_seal_tv:
                    intent = new Intent(WaitDealWithActivity.this, CheckPWDActivity.class);
                    intent.putExtra("type", "seal");
                    startActivityForResult(intent, 1);

                    break;
                default:break;
            }
        }
    };

}
