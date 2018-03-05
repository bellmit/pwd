package com.tastebychance.sonchance.personal.locate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.locate.adapter.GoodsReceiptAddressManagerAdapter;
import com.tastebychance.sonchance.personal.locate.bean.GoodsReceiptInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.QQListView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**收货地址管理界面
 * Created by shenbh on 2017/8/23.
 */

public class GoodsReceiptAddressManagerActivity extends MyBaseActivity {
    private GoodsReceiptAddressManagerAdapter adapter;
    private List<GoodsReceiptInfo> goodsReceiptInfos;
    private QQListView qqListView;

    public QQListView getQqListView() {
        return qqListView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_goodsreceiptaddress);

        bindViews();

        setTitle();
    }


    private void bindViews() {
        qqListView = (QQListView) findViewById(R.id.person_goodsreceiptaddress_qqlistview);

        //
        qqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (qqListView.canClick()){
                    Intent intent = new Intent();
                    intent.setAction(Constants.CHOSEADDRESS_ACTION);
                    intent.putExtra("goodsReceiptInfo",goodsReceiptInfos.get(position));
                    sendBroadcast(intent);

                    GoodsReceiptAddressManagerActivity.this.finish();
                }
            }
        });
    }

    //删除地址
    public void deleteAddress(int position) {
        //提交给服务器
        postDeleDataToServer(goodsReceiptInfos.get(position).getId());
    }

    //删除远程数据
    private void postDeleDataToServer(int id){
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
        String url = UrlManager.URL_PERSON_DELETEADDRESS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).add("id",id+"").build();
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = ERROR_WHAT;
                            msg.obj = "删除成功";
                            myHandler.sendMessage(msg);
                            getAddressData();
                        }
                    });
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
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        if (center_tv != null) {
            center_tv.setText("收货地址");
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    GoodsReceiptAddressManagerActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.VISIBLE);
            right_btn.setBackground(getResources().getDrawable(R.drawable.person_goodsreceipt_add));
            right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GoodsReceiptAddressManagerActivity.this, GoodsReceiptAddressAddOrEditActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    private void initData() {
        goodsReceiptInfos = new ArrayList<>();

        if (Constants.IS_LOCALDATA){
            for (int i = 0; i< 3;i++){
                WeakReference<GoodsReceiptInfo> wf = new WeakReference<GoodsReceiptInfo>(new GoodsReceiptInfo());
                wf.get().setAddress("收货地址"+i);
                wf.get().setTel("15112333633");
                wf.get().setUsername("收货人"+i);
                if (i == 1){
                    wf.get().setIs_check("1");
                }else{
                    wf.get().setIs_check("0");
                }
                goodsReceiptInfos.add(wf.get());
            }
            adapter = new GoodsReceiptAddressManagerAdapter(GoodsReceiptAddressManagerActivity.this,goodsReceiptInfos);
            qqListView.setAdapter(adapter);
        }else{
            getAddressData();
        }

    }

    /**用户地址列表接口
     *
     *  /api/UserApp/addressList
     */
    private void getAddressData(){
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
        String url =   UrlManager.URL_PERSON_ADDRESSLIST;
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

                        goodsReceiptInfos = resInfo.getDataList(GoodsReceiptInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new GoodsReceiptAddressManagerAdapter(GoodsReceiptAddressManagerActivity.this, goodsReceiptInfos);
                                qqListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
    //                            qqListView.turnToNormal();
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

    public void intentToAddressEditActivity(GoodsReceiptInfo goodsReceiptInfo){
        if (null != goodsReceiptInfo){
            Intent intent = new Intent(GoodsReceiptAddressManagerActivity.this, GoodsReceiptAddressAddOrEditActivity.class);
            intent.putExtra("goodsReceiptInfo",goodsReceiptInfo);
            startActivity(intent);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
