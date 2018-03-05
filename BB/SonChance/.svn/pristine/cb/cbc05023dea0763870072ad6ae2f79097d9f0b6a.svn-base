package com.tastebychance.sonchance.homepage.opinionfeedback;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 意见反馈
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class OpinionFeedbackActivity extends MyBaseActivity {

    private EditText inputText;//意见内容
    private TextView count_txt;//字数统计
    private ImageView feedback_success_iv;//反馈成功
    private LinearLayout feedback_success_ll;
    private TextView feedback_success_title_tv,feedback_success_content_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_opinion_feedback);
        setTitle();
        initView();
    }

    /**
     * 设置标题
     */
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
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        //重新设置view的高度

        if (center_tv != null) {
            center_tv.setText("意见反馈");
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    OpinionFeedbackActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("发表");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏输入法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0); //强制隐藏键盘

                    //点击发表
                    if (StringUtil.isEmpty(inputText.getText().toString())) {
                        Toast.makeText(OpinionFeedbackActivity.this, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        postFeedBace();
                    }
                }
            });
        }
    }

    private void initView() {
        inputText = (EditText) findViewById(R.id.feedback_content);// 输入框
        count_txt = (TextView) findViewById(R.id.feedback_count);// 剩余字数
        inputText.addTextChangedListener(mTextWatcher);
        inputText.setSelection(inputText.length()); // 将光标移动最后一个字符后面
        feedback_success_iv = (ImageView) findViewById(R.id.feedback_success_iv);
        feedback_success_ll = (LinearLayout) findViewById(R.id.feedback_success_ll);
        feedback_success_title_tv = (TextView) findViewById(R.id.feedback_success_title_tv);
        feedback_success_content_tv = (TextView) findViewById(R.id.feedback_success_content_tv);
    }

    /**
     * 提交反馈意见
     */
    private void postFeedBace(){
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_HOME_FEEDBACK;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).add("content", inputText.getText().toString()).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                inputText.setVisibility(View.VISIBLE);//意见内容
                count_txt.setVisibility(View.VISIBLE);//字数统计
                feedback_success_ll.setVisibility(View.GONE);//反馈成功

                Message msg = new Message();
                msg.what = ERROR_WHAT;
                msg.obj = "提交失败,请重新提交!";
                myHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str.toString(),ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showFeedBackSuccess();
                            }
                        });

                    }else{
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void showFeedBackSuccess() {
        Message msg = new Message();
        msg.what = INFO_WHAT;
        msg.obj = "提交成功,感谢您的反馈!";
        myHandler.sendMessage(msg);

        inputText.setVisibility(View.GONE);//意见内容
        count_txt.setVisibility(View.GONE);//字数统计
        feedback_success_ll.setVisibility(View.VISIBLE);//反馈成功

        new CountDownTimer(3 * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                OpinionFeedbackActivity.this.finish();
            }
        }.start();
    }

    /*--------------------------------------- 监听输入字数 ------------------------------------------*/
    private static final int MAX_COUNT = 240;
    private TextWatcher mTextWatcher = new TextWatcher() {

        private int editStart;

        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = inputText.getSelectionStart();
            editEnd = inputText.getSelectionEnd();

            inputText.removeTextChangedListener(mTextWatcher);
            while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            inputText.setText(s);
            inputText.setSelection(editStart);

            inputText.addTextChangedListener(mTextWatcher);

            setLeftCount();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    };

    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private void setLeftCount() {
        count_txt.setText("(" + getInputCount() +"/" + MAX_COUNT +")");
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(inputText.getText().toString());
    }
    /*--------------------------------------- 监听输入字数 ------------------------------------------*/
}
