package com.tastebychance.sonchance.homepage.toevaluate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.TabHostMainActivity;
import com.tastebychance.sonchance.homepage.toevaluate.adapter.ToEvaluateAdapter;
import com.tastebychance.sonchance.homepage.toevaluate.bean.CommentInfo;
import com.tastebychance.sonchance.homepage.toevaluate.bean.CommentRes;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 类描述：ToEvaluateActivity 去评价界面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/19 15:38
 * 修改人：
 * 修改时间：2017/9/19 15:38
 * 修改备注：
 *
 * @version 1.0
 */
public class ToEvaluateActivity extends MyBaseActivity {
    private String order_sn;//订单编号

    private ListView listview;
    private TextView home_toevaluate_getgrade_tv;//评价得分


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_to_evaluate);

        Intent intent = getIntent();
        if (null != intent) {
            order_sn = intent.getStringExtra("order_sn");
        }

        bindViews();
        setTitle();
    }

    private void bindViews() {
        listview = (ListView) findViewById(R.id.listview);
        home_toevaluate_getgrade_tv = (TextView) findViewById(R.id.home_toevaluate_getgrade_tv);
    }

    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("评价");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToEvaluateActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    private void getData() {
        if (Constants.IS_LOCALDATA) {
            CommentRes commentRes = new CommentRes();
            List<CommentInfo> mDatas = new ArrayList<>();
            //本地构造
            for (int i = 0; i < 10; i++) {
                WeakReference<CommentInfo> wf = new WeakReference<CommentInfo>(new CommentInfo());
                wf.get().setDishes_name("秘制香煎龙利鱼套餐" + i);
                wf.get().setId(86 + i);
                wf.get().setOrder_id(76 + i);
                wf.get().setUid(59);
                wf.get().setDishes_id(45);
                wf.get().setCount(2 + i);
                wf.get().setUnit_price(22.0f);
                wf.get().setGive("汉堡包");
                mDatas.add(wf.get());
            }
            commentRes.setList(mDatas);
            commentRes.setScore(128);
            initData(commentRes);
        } else {
            if (StringUtil.isNotEmpty(order_sn)) {
                getInitDataFromServer();
            }
        }
    }

    /**
     * 菜品评价页面(从服务器获取去评价界面的初始数据)
     */
    private void getInitDataFromServer() {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_COMMENT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("order_sn", order_sn + "").build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        final CommentRes commentRes = JSONObject.parseObject(resInfo.getData().toString(), CommentRes.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(commentRes);
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
     * 初始化页面数据
     *
     * @param commentInfos
     */
    private ToEvaluateAdapter adapter;

    private void initData(CommentRes commentInfos) {
        String scoreStr = "评价后可获得" + commentInfos.getScore() + "积分";
        int endIndex = ("评价后可获得" + commentInfos.getScore() + "").length();
        home_toevaluate_getgrade_tv.setText(StringUtil.setTextSizeColor(scoreStr, Color.GREEN, "评价后可获得".length(), endIndex, 16));

        if (null != commentInfos && commentInfos.getList().size() > 0) {
            listview.setAdapter(adapter = new ToEvaluateAdapter(ToEvaluateActivity.this, commentInfos.getList()));
        }
    }

    /**
     * 菜品评价提交接口
     */
    private void commentPost(String comments) {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_COMMENTPOST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("order_sn", order_sn + "")
                .add("comments", comments).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //显示评价成功，几秒后跳转到我的评价界面
                                showCommentSuccess();
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
     * 显示评价成功，几秒后跳转到我的评价界面
     */
    private void showCommentSuccess() {
        Context context = ToEvaluateActivity.this;
        final Dialog dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.login_success);
        ImageView imageView = (ImageView) dia.findViewById(R.id.login_success_iv);
        imageView.setBackgroundResource(R.drawable.evaluatesucces);
        //选择true的话点击其他地方可以使dialog消失，为false的话不会消失
        dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dia.onWindowAttributesChanged(lp);
        dia.show();

        new CountDownTimer(2 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                dia.dismiss();
                ToEvaluateActivity.this.finish();

               /* Intent intent = new Intent();
                intent.setAction(Constants.LOGINSUCCESS_ACTION);
                intent.putExtra("toActivity",toActivity);
                sendBroadcast(intent);*/
            }
        }.start();
    }

    /**
     * 提交
     *
     * @param view
     */
    public void submitClick(View view) {
        //判断是否可以提交
        List<CommentInfo> finalList = adapter.getList();
        for (CommentInfo commentInfo : finalList) {
            if (commentInfo.getGrade() == 0) {
                UiHelper.ShowOneToast(ToEvaluateActivity.this, "请进行星级评价");
                return;
            }
        }

        StringBuffer comments = new StringBuffer();//dishes_id:content:grade,dishes_id:content:grade,
        HashMap<Integer, CommontPostReq> postReqHashMap = new HashMap<>();//最后提交数据集合
        //最终提交的数据
        for (CommentInfo commentInfo : finalList) {
            WeakReference<CommontPostReq> wf = new WeakReference<CommontPostReq>(new CommontPostReq());
            wf.get().setOrder_id(commentInfo.getDishes_id());
            wf.get().setGrade(commentInfo.getGrade() * 2);//在adapter中取到的星级数据并未 * 2，需要在提交的时候进行 *2操作
            wf.get().setContent(commentInfo.getContent());
            postReqHashMap.put(commentInfo.getId(), wf.get());
        }

        for (Map.Entry<Integer, CommontPostReq> entry : postReqHashMap.entrySet()) {
            comments.append(StringUtil.noNull(entry.getValue().getOrder_id()) + ":" + StringUtil.noNull(entry.getValue().getContent()) + ":" + StringUtil.noNull(entry.getValue().getGrade()) + ",");
        }

        commentPost(comments.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void finish() {
        startActivity(new Intent(ToEvaluateActivity.this, TabHostMainActivity.class));
        super.finish();
    }

    /**
     * 菜品评价提交接口的参数
     */
    class CommontPostReq {
        private int order_id;
        private float grade;
        private String content;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public float getGrade() {
            return grade;
        }

        public void setGrade(float grade) {
            this.grade = grade;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
