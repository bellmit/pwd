package com.tastebychance.sonchance.personal.ordercoupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.ordercoupon.adapter.PersonCouponAdapter;
import com.tastebychance.sonchance.personal.ordercoupon.bean.CouponInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.TimeOrDateUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.PullRefreshListView;
import com.tastebychance.sonchance.view.pullrefresh.ui.PullToRefreshListView;

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

/**优惠券界面
 * Created by shenbh on 2017/8/29.
 */
public class PersonCouponActivity extends MyBaseActivity implements PullRefreshListView.IXListViewListener{

    private PullRefreshListView pullrefreshlistview;

    private PersonCouponAdapter adapter;
    private List<CouponInfo> mDatas;

    private String flag = "";
    private String originPrice = "0";

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
        setContentView(R.layout.person_coupon);

        //接收传递的Activity标识
        Intent intent = getIntent();
        if (null != intent) {
            flag = intent.getStringExtra("flag");
            originPrice = intent.getStringExtra("origin_price");
        }

        bindViews();

        setTitle();

        initObject();
    }

    private void bindViews() {
        pullrefreshlistview = (PullRefreshListView) findViewById(R.id.pullrefreshlistview);
        pullrefreshlistview.setPullLoadEnable(true);
        pullrefreshlistview.setXListViewListener(this);

        pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (StringUtil.isEmpty(flag)){

                }else if(flag.equals("OrderFormActivity")){
                    -- position ;//因为ListView 有头，所以要减去1

                    if (mDatas.get(position).getCan_use() == 0){//不可用的点击不给反应
                        return;
                    }

                    intent = new Intent();
                    intent.putExtra("coupon_id", mDatas.get(position).getId()+"");//返回选中的购物券id
                    PersonCouponActivity.this.setResult(1000, intent);
                    PersonCouponActivity.this.finish();
                }
            }
        });
    }

    private void initObject() {
        mDatas = new ArrayList<CouponInfo>();

        if (Constants.IS_LOCALDATA){
            for (int i =0; i < 6 ;i ++) {
                WeakReference<CouponInfo> wf = new WeakReference<CouponInfo>(new CouponInfo());
                wf.get().setBegin_time(TimeOrDateUtil.StringToDate("0000-00-00 00:00:00",TimeOrDateUtil.YYYY_MM_DD_HH_MM_SS));
                wf.get().setDiscount(3.0f);
                wf.get().setIs_used(0);
                if (i < 2){
                    wf.get().setCan_use(1);
                }else{
                    wf.get().setCan_use(0);
                }
                wf.get().setEnd_time(TimeOrDateUtil.StringToDate("2017-08-01 11:52:26",TimeOrDateUtil.YYYY_MM_DD_HH_MM_SS));

                mDatas.add(wf.get());
            }

            adapter = new PersonCouponAdapter(PersonCouponActivity.this,flag,mDatas);
            pullrefreshlistview.setAdapter(adapter);
        }else{
            getCoupons();
        }

    }

    /**
     * 优惠券接口
     */
    private void getCoupons(){
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
        String url =  UrlManager.URL_PERSON_COUPON;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = null;
        if(StringUtil.isEmpty(flag)){
            formBody = new FormBody.Builder().add("token", token).build();
        }else if (flag.equals("OrderFormActivity")){//配送页进来
            formBody = new FormBody.Builder().add("token", token).add("origin_price",originPrice).build();
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        mDatas = resInfo.getDataList(CouponInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<CouponInfo> notUsedDatas = new ArrayList<CouponInfo>();

                                if (pageIndex > 0 && notUsedDatas != null && notUsedDatas.size() <= 0){
                                    Toast.makeText(PersonCouponActivity.this,"无更多优惠券",Toast.LENGTH_SHORT).show();
                                }

                                //TODO:未展示不可用的优惠券
                                for (int i = 0, size = mDatas.size(); i < size; i++) {
                                    if (mDatas.get(i).getIs_used() == 0) {
                                        notUsedDatas.add(mDatas.get(i));
                                    }
                                }
                                adapter = new PersonCouponAdapter(PersonCouponActivity.this, flag, notUsedDatas);
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
            center_tv.setText("我的优惠券");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonCouponActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
            /*right_tv.setText("兑换");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.getInstance().makeToastInfo(PersonCouponActivity.this);
                }
            });*/
        }
    }

    private void onLoad(){
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onListViewRefresh() {
        pageIndex = 0;
        //TODO:网络请求
        initObject();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++ pageIndex ;
        //TODO:网络请求
        initObject();
        onLoad();
    }
}
