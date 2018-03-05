package com.tastebychance.sonchance.personal.myevaluate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.myevaluate.adapter.MyEvaluateAdapter;
import com.tastebychance.sonchance.personal.myevaluate.bean.MyEvaluateInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.PullRefreshListView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 类描述：MyEvaluateActivity 我的评价页面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/11 10:21
 * 修改人：
 * 修改时间：2017/9/11 10:21
 * 修改备注：
 * @version 1.0
 */
public class MyEvaluateActivity extends MyBaseActivity implements PullRefreshListView.IXListViewListener{

    private PullRefreshListView pullrefreshlistview;
    private MyEvaluateAdapter adapter;
    private List<MyEvaluateInfo> mDatas;

    private int pageIndex = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.WHAT_REFRESH:
                    pageIndex = 0;
                    adapter.notifyDataSetChanged();
                    onLoad();
                    break;
                case Constants.WHAT_LOADMORE:
                    ++pageIndex;
                    adapter.notifyDataSetChanged();
                    onLoad();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_my_evaluate);

        bindViews();
        setTitle();
        mDatas = new ArrayList<MyEvaluateInfo>();
    }

    private void bindViews() {
        pullrefreshlistview = (PullRefreshListView) findViewById(R.id.pullrefreshlistview);
        pullrefreshlistview.setPullLoadEnable(true);
        pullrefreshlistview.setXListViewListener(this);
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
        if (center_tv != null)
            center_tv.setText("我的评价");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyEvaluateActivity.this.finish();
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


    private void onLoad(){
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    private void getData(){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PERSON_MYCOMMENT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
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
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        mDatas = resInfo.getDataList(MyEvaluateInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            adapter = new MyEvaluateAdapter(MyEvaluateActivity.this,mDatas);
                            pullrefreshlistview.setAdapter(adapter);
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
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.IS_LOCALDATA){
            for (int i = 0; i < 3; i++) {
                WeakReference<MyEvaluateInfo> wf = new WeakReference<MyEvaluateInfo>(new MyEvaluateInfo());
                wf.get().setId(29);
                wf.get().setUid(71);
                wf.get().setDishes_id(58);
                wf.get().setOrder_id(181);
                wf.get().setGrade(7);
                wf.get().setContent("好吃");
                wf.get().setCreate_time("2017-09-09 13:46:01");
                wf.get().setStatus(0);
                wf.get().setType(1);
                wf.get().setDishes_name("西式泡汁猪里脊肉健身减脂套餐+饮品");
                mDatas.add(wf.get());
            }

            adapter = new MyEvaluateAdapter(MyEvaluateActivity.this,mDatas);
            pullrefreshlistview.setAdapter(adapter);
        }else{
            getData();
        }
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = 0;
        getData();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++ pageIndex ;
        getData();
        onLoad();
    }
}
