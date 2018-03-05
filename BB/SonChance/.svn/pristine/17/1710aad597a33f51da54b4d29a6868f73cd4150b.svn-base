package com.tastebychance.sonchance.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.personal.balance.BalanceActivity;
import com.tastebychance.sonchance.personal.bankcard.BankCardActivity;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.personal.locate.GoodsReceiptAddressManagerActivity;
import com.tastebychance.sonchance.personal.membercenter.MemberCenterActivity;
import com.tastebychance.sonchance.personal.myevaluate.MyEvaluateActivity;
import com.tastebychance.sonchance.personal.ordercoupon.PersonCouponActivity;
import com.tastebychance.sonchance.personal.personalcenter.PersonalInformationActivity;
import com.tastebychance.sonchance.personal.richscan.RichScanActivity;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.PicassoUtils;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.RoundImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的界面
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PersonalActivity extends MyBaseActivity implements android.view.View.OnClickListener {

    private TextView person_item_username_tv;
    private RoundImageView person_item_userimg_iv;
    private RelativeLayout person_item_jifen_rl;
    private TextView person_item_grade_tv;//会员等级
    private TextView person_item_jifen_tv;
    private RelativeLayout person_item_ye_rl;
    private TextView person_item_ye_tv;
    private RelativeLayout person_item_bindbankcard_rl;
    private RelativeLayout person_item_creditcard_rl;
    private RelativeLayout person_item_mycoupon_rl;
    private RelativeLayout person_item_ceiveaddress_rl;
    private RelativeLayout person_item_safe_rl;
    private RelativeLayout person_item_membercenter_rl;
    private RelativeLayout person_item_myevaluate_rl;
    private RelativeLayout person_item_scan_rl;
    private RelativeLayout person_item_checkversion_rl;


    private IntentFilter intentfilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);

        intentfilter = new IntentFilter();
        intentfilter.addAction(Constants.LOGINSUCCESS_ACTION);
        intentfilter.addAction(Constants.RECHARGE_CANCEL_ACTION);
        intentfilter.addAction(Constants.RECHARGE_ERROR_ACTION);
        intentfilter.addAction(Constants.RECHARGE_SUCCESS_ACTION);


        bindViews();
        setListener();

        initData(null);
    }

    private void bindViews() {
        person_item_grade_tv = (TextView) findViewById(R.id.person_item_grade_tv);
        person_item_username_tv = (TextView) findViewById(R.id.person_item_username_tv);
        person_item_userimg_iv = (RoundImageView) findViewById(R.id.person_item_headportrait_iv);
        person_item_jifen_rl = (RelativeLayout) findViewById(R.id.person_item_jifen_rl);
        person_item_jifen_tv = (TextView) findViewById(R.id.person_item_jifen_tv);
        person_item_ye_rl = (RelativeLayout) findViewById(R.id.person_item_ye_rl);
        person_item_ye_tv = (TextView) findViewById(R.id.person_item_ye_tv);
        person_item_bindbankcard_rl = (RelativeLayout) findViewById(R.id.person_item_bindbankcard_rl);
        person_item_creditcard_rl = (RelativeLayout) findViewById(R.id.person_item_creditcard_rl);
        person_item_mycoupon_rl = (RelativeLayout) findViewById(R.id.person_item_mycoupon_rl);
        person_item_ceiveaddress_rl = (RelativeLayout) findViewById(R.id.person_item_ceiveaddress_rl);
        person_item_safe_rl = (RelativeLayout) findViewById(R.id.person_item_safe_rl);
        person_item_membercenter_rl = (RelativeLayout) findViewById(R.id.person_item_membercenter_rl);
        person_item_myevaluate_rl = (RelativeLayout) findViewById(R.id.person_item_myevaluate_rl);
        person_item_scan_rl = (RelativeLayout) findViewById(R.id.person_item_scan_rl);
        person_item_checkversion_rl = (RelativeLayout) findViewById(R.id.person_item_checkversion_rl);
    }

    private void setListener() {
        person_item_userimg_iv.setOnClickListener(this);
        person_item_jifen_rl.setOnClickListener(this);
        person_item_ye_rl.setOnClickListener(this);
        person_item_bindbankcard_rl.setOnClickListener(this);
        person_item_creditcard_rl.setOnClickListener(this);
        person_item_mycoupon_rl.setOnClickListener(this);
        person_item_ceiveaddress_rl.setOnClickListener(this);
        person_item_safe_rl.setOnClickListener(this);
        person_item_membercenter_rl.setOnClickListener(this);
        person_item_myevaluate_rl.setOnClickListener(this);
        person_item_scan_rl.setOnClickListener(this);
        person_item_checkversion_rl.setOnClickListener(this);
    }

    private UserInfo userInfo;
    private void initData(UserInfo userInfo) {
        this.userInfo = userInfo;
        if (null == userInfo) {
            resetViews();
            return;
        }


        if (StringUtil.isNotEmpty(userInfo.getAvatar()) && null != person_item_userimg_iv) {//头像
//            ImageDownLoad.getDownLoadCircleImgNoWaitPic(userInfo.getAvatar(), person_item_userimg_iv);
           String imgUrl;
            if (userInfo != null && userInfo.getAvatar() != null && userInfo.getAvatar().contains("http:") || userInfo.getAvatar().contains("https")){
                imgUrl = userInfo.getAvatar();
            }else{
                imgUrl = (Constants.IS_DEVELOPING? UrlManager.REQUESTURL_HEAD_TEST : UrlManager.REQUESTIMGURL) + userInfo.getAvatar();
            }

           /* Picasso.with(PersonalActivity.this)
                    .load(imgUrl)
                    .resize(48,48)
                    .centerCrop()
                    .into(person_item_userimg_iv);*/

            PicassoUtils.getinstance().loadCircleImage(PersonalActivity.this,imgUrl,person_item_userimg_iv, 24);
        }

        person_item_grade_tv.setText("V"+userInfo.getGrade());

        if (StringUtil.isNotEmpty(userInfo.getUser_nicename()) && null != person_item_username_tv){
            person_item_username_tv.setText(userInfo.getUser_nicename());
        }

        if (StringUtil.isNotEmpty(userInfo.getScore()+"") && null != person_item_jifen_tv){
            person_item_jifen_tv.setText(userInfo.getScore()+"");
        }

        if (StringUtil.isNotEmpty(userInfo.getMoney()+"") && null != person_item_ye_tv){
            person_item_ye_tv.setText(userInfo.getMoney()+"");
        }
    }
    /**
     * 控件信息复位
     */
    private void resetViews(){
        if (null != person_item_userimg_iv) {
//            person_item_userimg_iv.setBackground(getResources().getDrawable(R.drawable.person_defaultheadportrait));
            PicassoUtils.getinstance().loadCircleImageById(PersonalActivity.this,R.drawable.person_defaultheadportrait, person_item_userimg_iv,24);
        }
        if (null != person_item_grade_tv) {
            person_item_grade_tv.setText("V0");
        }
        if (null != person_item_username_tv){
            person_item_username_tv.setText("请登录");
        }
        if (null != person_item_jifen_tv) {
            person_item_jifen_tv.setText("0");
        }
        if (null != person_item_ye_tv) {
            person_item_ye_tv.setText("0");
        }
    }

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if(action.equals(Constants.LOGINSUCCESS_ACTION)) {
                    intentTo(intent.getStringExtra("toActivity"));
                }else if (action.equals(Constants.RECHARGE_CANCEL_ACTION) || action.equals(Constants.RECHARGE_ERROR_ACTION) || action.equals(Constants.RECHARGE_SUCCESS_ACTION)){

                    startActivity(new Intent(PersonalActivity.this,BalanceActivity.class));
                }
            }
        }
    };

    /**
     * 跳转到某个界面
     * @param toActivity
     */
    private void intentTo(String toActivity){

        if (StringUtil.isNotEmpty(toActivity) && toActivity.equals(Constants.DO_NOTHING)){
            return;
        }

        switch (toActivity) {
            case Constants.TO_PERSONCENTER://个人中心
                startActivity(new Intent(PersonalActivity.this,PersonalInformationActivity.class));
                break;
            case Constants.TO_BALANCE://账户余额
                startActivity(new Intent(PersonalActivity.this,BalanceActivity.class));
                break;
            case Constants.TO_BINDBANKCARD://绑定银行卡
                startActivity(new Intent(PersonalActivity.this,BankCardActivity.class));
                break;
            case Constants.TO_CREDITCARD://办理信用卡
                SystemUtil.getInstance().makeToastInfo(this);
                break;
            case Constants.TO_MYCOUPON://我的优惠券
                startActivity(new Intent(PersonalActivity.this, PersonCouponActivity.class));
                break;
            case Constants.TO_CEIVEADDRESS://收货地址管理
                startActivity(new Intent(PersonalActivity.this, GoodsReceiptAddressManagerActivity.class));
                break;
/*            case Constants.TO_SAVE://账户与安全
                SystemUtil.getInstance().makeToastInfo(this);
                break;*/
            case Constants.TO_MEMBERCENTER://会员中心
//                SystemUtil.getInstance().makeToastInfo(this);
                startActivity(new Intent(PersonalActivity.this, MemberCenterActivity.class));
                break;
            case Constants.TO_MYEVALUATE://调到我的评价
                startActivity(new Intent(PersonalActivity.this, MyEvaluateActivity.class));
                break;
            case Constants.TO_SCAN://扫一扫
                startActivity(new Intent(PersonalActivity.this, RichScanActivity.class));
                break;
            case Constants.TO_CHECKVERSION://版本更新
                SystemUtil.getInstance().makeToastInfo(this);
                break;
            default:
                break;
        }
    }

    private void getUserInfo(){
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PERSON_GETUSERINFO;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
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

                    final UserInfo userInfo = JSONObject.parseObject(resInfo.getData().toString(), UserInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        SharedPreferencesUtils.saveToShared(MyApplication.getContext(), "userInfo", userInfo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData(userInfo);
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
    protected void onStart() {
        super.onStart();

        if (null != myBroadcastReceiver){
            registerReceiver(myBroadcastReceiver,intentfilter);
        }

        //下载图片，设置头像
//        ImageDownLoad.getDownLoadSmallImg("http://img3.imgtn.bdimg.com/it/u=3483375212,1635986224&fm=214&gp=0.jpg", person_item_userimg_iv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Person----------------------------------------------------"+getTaskId());

        if (SystemUtil.getInstance().getIsLogin()) {
            getUserInfo();
            /*UserInfo userInfo = (UserInfo)SharedPreferencesUtils.queryForSharedToObject(MyApplication.getContext(), "userInfo");
            if (null == userInfo) {
                getUserInfo();
            }else{
                initData(userInfo);
            }*/
        }else{
            //清空数据
            initData(userInfo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBroadcastReceiver){
            unregisterReceiver(myBroadcastReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        String toActivity = Constants.TO_PERSONCENTER;
        switch (view.getId()) {
            case R.id.person_item_headportrait_iv://头像
                toActivity = Constants.TO_PERSONCENTER;
                break;
            case R.id.person_item_ye_rl://账户余额
                toActivity = Constants.TO_BALANCE;
                break;
            case R.id.person_item_bindbankcard_rl://绑定银行卡
                toActivity = Constants.TO_BINDBANKCARD;
                break;
            case R.id.person_item_creditcard_rl://免费办理信用卡
                toActivity = Constants.TO_CREDITCARD;
                break;
            case R.id.person_item_mycoupon_rl://我的优惠券
                toActivity = Constants.TO_MYCOUPON;
                break;
            case R.id.person_item_ceiveaddress_rl://用膳地址管理
                toActivity = Constants.TO_CEIVEADDRESS;
                break;
            case R.id.person_item_safe_rl://账户与安全
                toActivity = Constants.TO_PERSONCENTER;
                break;
            case R.id.person_item_jifen_rl:
            case R.id.person_item_membercenter_rl://会员中心
                //TODO:暂时关闭积分模块
//                toActivity = Constants.TO_MEMBERCENTER;
                toActivity = Constants.DO_NOTHING;

                SystemUtil.getInstance().makeToastInfo(PersonalActivity.this);
                break;
            case R.id.person_item_myevaluate_rl://我的评价
                toActivity = Constants.TO_MYEVALUATE;
                break;
            case R.id.person_item_scan_rl://扫一扫
                toActivity = Constants.TO_SCAN;
                break;
            case R.id.person_item_checkversion_rl://版本更新
                toActivity = Constants.TO_CHECKVERSION;
                break;
            default:
                break;
        }
        if (!SystemUtil.getInstance().getIsLogin()){
            SystemUtil.getInstance().intentToLoginActivity(PersonalActivity.this,toActivity);
        }else{
            intentTo(toActivity);
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            if (System.currentTimeMillis() - firstTime > 2000){
                Toast.makeText(PersonalActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }else{
                MyApplication.getAppContext().clearStatck();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
