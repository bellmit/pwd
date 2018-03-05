package com.tastebychance.sonchance.homepage.orderform;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.homepage.ShoppCartInstance;
import com.tastebychance.sonchance.homepage.orderform.adapter.OrderInfoAdapter;
import com.tastebychance.sonchance.homepage.orderform.adapter.ReceiptTimeGridAdapter;
import com.tastebychance.sonchance.homepage.orderform.bean.Address;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderDetailInfo;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderInfo;
import com.tastebychance.sonchance.homepage.orderform.bean.ReceiptTimeInfo;
import com.tastebychance.sonchance.homepage.pay.PayActivity;
import com.tastebychance.sonchance.personal.locate.GoodsReceiptAddressManagerActivity;
import com.tastebychance.sonchance.personal.locate.bean.GoodsReceiptInfo;
import com.tastebychance.sonchance.personal.ordercoupon.PersonCouponActivity;
import com.tastebychance.sonchance.util.ArithUtil;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名称：SonChance 
 * 类描述：订单配送至（界面）
 * 创建人：Administrator
 * 创建时间： 2017/10/17 13:38
 * 修改人：Administrator
 * 修改时间：2017/10/17 13:38
 * 修改备注：
 */

public class OrderFormActivity extends MyBaseActivity implements View.OnClickListener{
    private TextView afterpay_choocereceivetime_tv;//选择收货时间

    private RelativeLayout content_order_form;
    private RelativeLayout head_rl;
    private ImageView head_left_iv;
    private TextView head_center_title_tv;
    private TextView head_center_address_tv;
    private ImageView head_center_address_iv;
    private TextView head_username_tv;
    private TextView head_tel_tv;
    private com.tastebychance.sonchance.view.MyListView order_form_dishes_mlv;
    private RelativeLayout order_form_coupon_rl;
    private TextView order_form_coupon_num_tv;
    private ImageView order_form_coupon_turnright_iv;
    private View line;
    private TextView order_form_subtotal_tv;

    //账户余额
    private TextView order_form_balance_tv;
    private CheckBox order_form_balance_cb;

    //选择支付方式
    private RelativeLayout order_form_paymenttype_rl;
    private ImageView order_form_paymenttype_turnright_iv;
    private TextView order_form_paymenttype_tv;
    private ImageView order_form_paymenttype_icon_iv;

    private LinearLayout shoppingcart_ll;
    private TextView shoppingcart_totle_money_tv;
    private View veriticalline;
    private TextView shoppingcart_discount_tv;
    private TextView shoppingcart_immediatlypay_tv;

    //使用优惠券后
    private LinearLayout order_form_discount_ll;
    private TextView order_form_discountname_tv;
    private TextView order_form_discountnum_tv;

    private OrderInfoAdapter adapter;
    private List<OrderDetailInfo> mDatas;

    //选择支付方式
    private PopupWindow paymentPopupWindow;
    //微信支付
    private CheckBox order_form_paymenttype_wx_cb;
    //支付宝支付
    private CheckBox order_form_paymenttype_zfb_cb;

    //选择收货时间
    private PopupWindow receiveTimePopupWindow;
    private String dateString = "";//收货时间的日期
    private String minString = "";//收货时间的具体时间点

    private Intent intent;
    private OrderInfo orderInfo;

    //购物车信息
    private StringBuffer cart = null;

    //选择地址
    private IntentFilter intentFilter;

    private HashMap<Integer,Boolean> paymentCBHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_orderform);

        intent = getIntent();
        if (intent != null) {
            orderInfo = (OrderInfo) intent.getSerializableExtra("orderInfo");
        }
        paymentCBHashMap = new HashMap<>();

        bindViews();
        setLiener();

        setPaymentCb();

        if (orderInfo == null) {

        }
//        getOrderInfo();
        initObject();

        intentFilter = new IntentFilter(Constants.CHOSEADDRESS_ACTION);

    }

    private void bindViews() {
        afterpay_choocereceivetime_tv = (TextView) findViewById(R.id.afterpay_choocereceivetime_tv);

        content_order_form = (RelativeLayout) findViewById(R.id.content_order_form);
        head_rl = (RelativeLayout) findViewById(R.id.head_rl);
        head_left_iv = (ImageView) findViewById(R.id.head_left_iv);
        head_center_title_tv = (TextView) findViewById(R.id.head_center_title_tv);
        head_center_address_tv = (TextView) findViewById(R.id.head_center_address_tv);
        head_center_address_iv = (ImageView) findViewById(R.id.head_center_address_iv);
        head_username_tv = (TextView) findViewById(R.id.head_username_tv);
        head_tel_tv = (TextView) findViewById(R.id.head_tel_tv);
        order_form_dishes_mlv = (com.tastebychance.sonchance.view.MyListView) findViewById(R.id.afterpay_dishes_mlv);
        order_form_coupon_rl = (RelativeLayout) findViewById(R.id.order_form_coupon_rl);
        order_form_coupon_num_tv = (TextView) findViewById(R.id.afterpay_couponnum_tv);
        order_form_coupon_turnright_iv = (ImageView) findViewById(R.id.order_form_coupon_turnright_iv);
        line = (View) findViewById(R.id.line);
        order_form_subtotal_tv = (TextView) findViewById(R.id.afterpay_subtotal_tv);
        order_form_paymenttype_rl = (RelativeLayout) findViewById(R.id.order_form_paymenttype_rl);
        order_form_paymenttype_turnright_iv = (ImageView) findViewById(R.id.order_form_paymenttype_turnright_iv);
        order_form_paymenttype_tv = (TextView) findViewById(R.id.order_form_paymenttype_tv);
        order_form_paymenttype_icon_iv = (ImageView) findViewById(R.id.paymenttype_icon_iv);
        shoppingcart_ll = (LinearLayout) findViewById(R.id.shoppingcart_ll);
        shoppingcart_totle_money_tv = (TextView) findViewById(R.id.shoppingcart_totle_money_tv);
        veriticalline = (View) findViewById(R.id.veriticalline);
        shoppingcart_discount_tv = (TextView) findViewById(R.id.shoppingcart_discount_tv);
        shoppingcart_immediatlypay_tv = (TextView) findViewById(R.id.shoppingcart_immediatlypay_tv);
        order_form_balance_tv = (TextView) findViewById(R.id.order_form_balance_tv);

        //使用优惠券后
        order_form_discount_ll = (LinearLayout) findViewById(R.id.order_form_discount_ll);
        order_form_discountname_tv = (TextView) findViewById(R.id.order_form_discountname_tv);
        order_form_discountnum_tv = (TextView) findViewById(R.id.order_form_discountnum_tv);

        order_form_balance_cb = (CheckBox) findViewById(R.id.order_form_balance_cb);
        order_form_paymenttype_wx_cb = (CheckBox) findViewById(R.id.order_form_paymenttype_wx_cb);
        order_form_paymenttype_zfb_cb = (CheckBox) findViewById(R.id.order_form_paymenttype_zfb_cb);
        //初始化默认是余额支付
        paymentCBHashMap.put(R.id.order_form_balance_cb,true);
        paymentCBHashMap.put(R.id.order_form_paymenttype_wx_cb,false);
        paymentCBHashMap.put(R.id.order_form_paymenttype_zfb_cb,false);
    }

    private void setPaymentCb(){
        order_form_balance_cb.setChecked(paymentCBHashMap.get(R.id.order_form_balance_cb));
        order_form_paymenttype_wx_cb.setChecked(paymentCBHashMap.get(R.id.order_form_paymenttype_wx_cb));
        order_form_paymenttype_zfb_cb.setChecked(paymentCBHashMap.get(R.id.order_form_paymenttype_zfb_cb));
    }

    private void setLiener() {
        head_left_iv.setOnClickListener(this);
        order_form_coupon_num_tv.setOnClickListener(this);
        order_form_coupon_turnright_iv.setOnClickListener(this);
        order_form_discountname_tv.setOnClickListener(this);
        order_form_discountnum_tv.setOnClickListener(this);

        //选择支付方式
        order_form_paymenttype_rl.setOnClickListener(this);

//        立即支付
        shoppingcart_immediatlypay_tv.setOnClickListener(this);

        order_form_balance_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentCBHashMap.put(R.id.order_form_balance_cb,true);
                    paymentCBHashMap.put(R.id.order_form_paymenttype_wx_cb,false);
                    paymentCBHashMap.put(R.id.order_form_paymenttype_zfb_cb,false);
                }else{
                    paymentCBHashMap.put(R.id.order_form_balance_cb,false);
                }
                setPaymentCb();
            }
        });
        order_form_paymenttype_wx_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentCBHashMap.put(R.id.order_form_paymenttype_wx_cb,true);
                    paymentCBHashMap.put(R.id.order_form_balance_cb,false);
                    paymentCBHashMap.put(R.id.order_form_paymenttype_zfb_cb,false);
                }else{
                    paymentCBHashMap.put(R.id.order_form_paymenttype_wx_cb,false);
                }
                setPaymentCb();
            }
        });
        order_form_paymenttype_zfb_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentCBHashMap.put(R.id.order_form_paymenttype_zfb_cb,true);
                    paymentCBHashMap.put(R.id.order_form_balance_cb,false);
                    paymentCBHashMap.put(R.id.order_form_paymenttype_wx_cb,false);
                }else{
                    paymentCBHashMap.put(R.id.order_form_paymenttype_zfb_cb,false);
                }
                setPaymentCb();
            }
        });

    }

    private void initObject() {

        head_center_address_tv.setText("请选择地址");
        head_username_tv.setText("");
        head_tel_tv.setText("");

        mDatas = new ArrayList<OrderDetailInfo>();

        if (Constants.IS_LOCALDATA){
            //构造订单信息
            getDishInfoByLocal();
            //TODO：构造收货地址

        }else{
            //TODO：从服务器获取收货地址信息，并给相关控件设置值
            getReceiptAddress();

            if (null != orderInfo ){
                if(orderInfo.getList().size() > 0) {
                    mDatas.addAll(orderInfo.getList());
                }

                //收货时间
                if (StringUtil.isNotEmpty(orderInfo.getDatetime()) && null != afterpay_choocereceivetime_tv){
                    dateString = orderInfo.getDatetime();
                    minString = orderInfo.getMins();
                    afterpay_choocereceivetime_tv.setText(orderInfo.getDatetime()+orderInfo.getMins());
                }

                //购物车
                if (null != mDatas && mDatas.size() > 0){
                    cart = new StringBuffer();
                    for (int i = 0; i < mDatas.size(); i++) {
                        cart.append(mDatas.get(i).getDishes_id()+":"+mDatas.get(i).getCount()+",");
                    }
                }

                //小计金额
                if (null != order_form_subtotal_tv){
                    order_form_subtotal_tv.setText(orderInfo.getOrigin_price()+"");
                }
                //购物车总金额
                if (shoppingcart_totle_money_tv != null) {
                    shoppingcart_totle_money_tv.setText("￥"+orderInfo.getOrigin_price()+"");
                    dedPrice = orderInfo.getOrigin_price();
                }
                //购物券数量
                if (order_form_coupon_num_tv != null) {
                    order_form_coupon_num_tv.setText(orderInfo.getCoupon_count()+"个可用");
                }

                //账户余额
                if (null != order_form_balance_tv){
                    String balance = "账户余额：￥"+new java.text.DecimalFormat("#,##0.00").format(orderInfo.getMoney())+"";
                    order_form_balance_tv.setText(StringUtil.setTextSizeColor(balance, Color.RED,"账户余额：".length(),balance.length(),18));
                }

                //支付方式
               /* if (){
                    paymentType = PAYMENTTYPE_WEIXIN;
                    setPaymentType(paymentType);
                }else{
                    paymentType = PAYMENTTYPE_ZHIFUBAO;
                    setPaymentType(paymentType);
                }*/
            }
        }

        adapter = new OrderInfoAdapter(OrderFormActivity.this,mDatas);
        order_form_dishes_mlv.setAdapter(adapter);

//        updateTotalMoney(0);
    }

    private int addressId;
    private void setAddress(Address address) {
        addressId = address.getId();
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(address.getVillage())){
            sb.append(address.getVillage());
        }
        if (StringUtil.isNotEmpty(address.getAddress())){
            sb.append(address.getAddress());
        }
        if (StringUtil.isNotEmpty(sb.toString())){
            head_center_address_tv.setText(sb.toString());
        }
        if (StringUtil.isNotEmpty(address.getUsername())){
            head_username_tv.setText(address.getUsername());
        }
        if (StringUtil.isNotEmpty(address.getTel())){
            head_tel_tv.setText(address.getTel());
        }
    }

    /**
     * 获取收货地址
     */
    private void getReceiptAddress() {
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_ORDERFORM_ADDRESS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("city", StringUtil.isEmpty(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city"))
                        ? "福州":SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city")).build();
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
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        final Address address = JSONObject.parseObject(resInfo.getData().toString(),Address.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != address){
                                    setAddress(address);
                                }
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

    //构造购物车中的菜品
    private void getDishInfoByLocal() {
       /* WeakReference<OrderInfo> wf1 = new WeakReference<OrderInfo>(new OrderInfo());
        wf1.get().setCount(3);
        wf1.get().setCoupon(0);
        wf1.get().setDishes_id(1);
        wf1.get().setGive_num(0);
        wf1.get().setName("秘制香煎龙利鱼套餐");
        wf1.get().setLogo("");
        wf1.get().setPrice(32);
        mDatas.put(wf1.get().getDishes_id(),wf1.get());

        WeakReference<OrderInfo> wf2 = new WeakReference<OrderInfo>(new OrderInfo());
        wf2.get().setCount(1);
        wf2.get().setCoupon(0);
        wf2.get().setDishes_id(2);
        wf2.get().setGive_num(1);//送多少份
        wf2.get().setName("淡香原味鸡胸肉套餐");
        wf2.get().setLogo("");
        wf2.get().setPrice(64);
        mDatas.put(wf2.get().getDishes_id(),wf2.get());*/
    }

    private float totalMoney;
   /* private void updateTotalMoney(float disCount){

        int size = mDatas.size();
        int count = 0;
        for (int i = 0; i < size; i++) {

            OrderInfo item = mDatas.valueAt(i);
            count += item.getCount();
//            totalMoney += item.getCount() * item.getPrice();
            totalMoney += item.getPrice();
            //减去赠送
            if (item.getGive_num() > 0){
                totalMoney -= item.getPrice();
            }
        }

        //减去优惠
        totalMoney -= disCount;

        shoppingcart_totle_money_tv.setText("￥"+ totalMoney);
        order_form_subtotal_tv.setText("￥"+ totalMoney);
        totalMoney = 0;
    }*/

    /**
     * 选择支付方式
     */
    private void initPaymentPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.home_orderform_choosepaymenttype,null,false);
        //        选择支付方式
        paymentPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        paymentPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        paymentPopupWindow.setBackgroundDrawable(new PaintDrawable());
        paymentPopupWindow.setOutsideTouchable(true);
        paymentPopupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        RelativeLayout paymenttype_zhifubao_rf = (RelativeLayout) popupWindow_view.findViewById(R.id.paymenttype_zhifubao_rl);
        RelativeLayout paymenttype_weixin_rl = (RelativeLayout) popupWindow_view.findViewById(R.id.paymenttype_weixin_rl);
        final ImageView paymenttype_zhifubao_choose_iv = (ImageView) popupWindow_view.findViewById(R.id.paymenttype_zhifubao_choose_iv);
        final ImageView paymenttype_wexin_choose_iv = (ImageView) popupWindow_view.findViewById(R.id.paymenttype_wexin_choose_iv);


        if (paymentType.equals(PAYMENTTYPE_ZHIFUBAO)){
            paymenttype_zhifubao_choose_iv.setVisibility(View.VISIBLE);
            paymenttype_wexin_choose_iv.setVisibility(View.GONE);
        }else{
            paymenttype_zhifubao_choose_iv.setVisibility(View.GONE);
            paymenttype_wexin_choose_iv.setVisibility(View.VISIBLE);
        }

        paymenttype_zhifubao_rf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymenttype_zhifubao_choose_iv.setVisibility(View.VISIBLE);
                paymenttype_wexin_choose_iv.setVisibility(View.GONE);

                paymentType = PAYMENTTYPE_ZHIFUBAO ;
                paymentPopupWindow.dismiss();
            }
        });

        paymenttype_weixin_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymenttype_zhifubao_choose_iv.setVisibility(View.GONE);
                paymenttype_wexin_choose_iv.setVisibility(View.VISIBLE);

                paymentType = PAYMENTTYPE_WEIXIN ;
                paymentPopupWindow.dismiss();
            }
        });


        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    paymentPopupWindow.dismiss();
                    paymentPopupWindow = null;
                    return true;
                }
                return false;
            }
        });

        paymentPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);

                paymentPopupWindow = null;

                if (null != paymenttype_wexin_choose_iv && null != paymenttype_zhifubao_choose_iv){
                    if (paymenttype_wexin_choose_iv.getVisibility() == View.VISIBLE){
                        setPaymentType(paymentType);
                    }else{
                        setPaymentType(paymentType);
                    }
                }
            }
        });


        setBackgroundAlpha(0.3f);
        paymentPopupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }

    // 设置透明度
    private void setBackgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(layoutParams);
    }

    /**
     * 设置支付方式
     */
    private final String PAYMENTTYPE_ZHIFUBAO = "zhifubao" ;
    private final String PAYMENTTYPE_WEIXIN = "weixin";
    private String paymentType = PAYMENTTYPE_ZHIFUBAO ;
    private void setPaymentType(String paymentType) {
        if (null == paymentType ){
            return;
        }

        if(paymentType.equals(PAYMENTTYPE_ZHIFUBAO)){
            order_form_paymenttype_tv.setText("支付宝支付");
            order_form_paymenttype_icon_iv.setBackground(getResources().getDrawable(R.drawable.order_form_payment_zfb_small));
        }else{
            order_form_paymenttype_tv.setText("微信支付");
            order_form_paymenttype_icon_iv.setBackground(getResources().getDrawable(R.drawable.order_form_payment_wx_small));
        }
    }

    public void getPaymentPopupWindow(){
        if (null != paymentPopupWindow){
            //如果popupWindow正在显示，接下来隐藏
            if (paymentPopupWindow.isShowing()) {
                paymentPopupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                paymentPopupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            }
        }else{
            initPaymentPopupWindow();
        }
    }

    /**
     * 选择支付宝支付
     * @param view
     */
    public void chooseZFB(View view){

    }

    /**
     * 选择微信支付
     */
    public void chooseWX(View view){

    }


    /* ------------------------------------------------选择收货时间 begin-----------------------------------------------------------*/
    public void getReceiveTimePopupWindow(){
        if (null != receiveTimePopupWindow){
            //如果popupWindow正在显示，接下来隐藏
            if (receiveTimePopupWindow.isShowing()) {
                receiveTimePopupWindow.dismiss();
            } else {
                //产生背景变暗效果
                setBackgroundAlpha(0.3f);
                receiveTimePopupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            }
        }else{
            initReceiveTimePopupWindow();
        }
    }

    //    private CommonAdapter<ReceiptTimeInfo> chooseTimeLvAdapter;
    private ReceiptTimeGridAdapter<ReceiptTimeInfo> receiptTimeListAdapter;
    private ReceiptTimeGridAdapter<String> receiptTimeGridAdapter;
    private final int[] chooseTimeLvSelectedItem = {0};//listview默认选中的项
    private final int[] chooseTimeGvSelectedItem = {0};//gridview默认选中的项
    private void initReceiveTimePopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.home_orderform_choosereceivetime,null,false);
        //        选择支付方式
        receiveTimePopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        receiveTimePopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        receiveTimePopupWindow.setBackgroundDrawable(new PaintDrawable());
        receiveTimePopupWindow.setOutsideTouchable(true);
        receiveTimePopupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        ListView choosereceivetime_lv = (ListView) popupWindow_view.findViewById(R.id.choosereceivetime_lv);
        final GridView choosereceivetime_gv = (GridView) popupWindow_view.findViewById(R.id.choosereceivetime_gv);

        choosereceivetime_lv.setAdapter(receiptTimeListAdapter = new ReceiptTimeGridAdapter<ReceiptTimeInfo>(
                getApplicationContext(),lvList,R.layout.home_orderform_choosereceivetime_lvitem) {
            @Override
            protected void convert(ViewHolder viewHolder, ReceiptTimeInfo item) {
                TextView choosereceivetime_lvitem_tv = viewHolder.getView(R.id.choosereceivetime_lvitem_tv);
                choosereceivetime_lvitem_tv.setText(item.getDatetime());
                choosereceivetime_lvitem_tv.setTextColor(Color.BLACK);

                TextPaint tp = choosereceivetime_lvitem_tv.getPaint();
                if(viewHolder.getPosition() == chooseTimeLvSelectedItem[0]){
                    tp.setFakeBoldText(true);
                    choosereceivetime_lvitem_tv.setBackgroundResource(R.color.white);
                }else{
                    tp.setFakeBoldText(false);
                    choosereceivetime_lvitem_tv.setBackgroundResource(R.color.bg_gray);
                }
            }
        });

        choosereceivetime_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (chooseTimeLvSelectedItem[0] != position){//如果有切换左侧星期的标签才进行重置右侧的选中项
                    chooseTimeGvSelectedItem[0] = 0;
                    if (null != receiptTimeGridAdapter){
                        receiptTimeGridAdapter.setSetSelectedPosition(chooseTimeGvSelectedItem[0]);
                    }
                }

                chooseTimeLvSelectedItem[0] = position;
                gvList.clear();
                gvList.addAll(receiptTimeInfos.get(position).getTimes());
                if (receiptTimeGridAdapter != null){
                    receiptTimeGridAdapter.notifyDataSetChanged();
                }

                if(receiptTimeListAdapter != null){
                    receiptTimeListAdapter.notifyDataSetChanged();
                }
            }
        });

        gvList.addAll(receiptTimeInfos.get(0).getTimes());
        choosereceivetime_gv.setVerticalSpacing(UiHelper.dip2px(OrderFormActivity.this, 8));//设置GridView的行间距
        choosereceivetime_gv.setHorizontalSpacing(UiHelper.dip2px(OrderFormActivity.this, 8));//设置GridView的列间距
        choosereceivetime_gv.setPadding(UiHelper.dip2px(OrderFormActivity.this, 8),UiHelper.dip2px(OrderFormActivity.this, 8),
                UiHelper.dip2px(OrderFormActivity.this, 8),UiHelper.dip2px(OrderFormActivity.this, 8));//设置控件距离旁边的距离
        choosereceivetime_gv.setColumnWidth(choosereceivetime_gv.getColumnWidth() - 16);
        choosereceivetime_gv.setAdapter(receiptTimeGridAdapter = new ReceiptTimeGridAdapter<String>(
                getApplicationContext(), gvList, R.layout.home_orderform_choosereceivetime_gvitem
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, String item) {
                TextView choosereceivetime_gvitem_tv = viewHolder.getView(R.id.choosereceivetime_gvitem_tv);
                choosereceivetime_gvitem_tv.setText(item);
                choosereceivetime_gvitem_tv.setTextColor(Color.BLACK);
            }
        });

        //初始设置gridview的选中项
        if (null != receiptTimeGridAdapter){
            receiptTimeGridAdapter.setSetSelectedPosition(chooseTimeGvSelectedItem[0]);
        }

        choosereceivetime_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseTimeGvSelectedItem[0] = position;

                receiptTimeGridAdapter.setSetSelectedPosition(position);

                if (receiptTimeGridAdapter != null) {
                    receiptTimeGridAdapter.notifyDataSetChanged();
                }

                //popupwindow消失
                receiveTimePopupWindow.dismiss();
                receiveTimePopupWindow = null;
            }
        });

        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    receiveTimePopupWindow.dismiss();
                    receiveTimePopupWindow = null;
                    return true;
                }
                return false;
            }
        });

        receiveTimePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);

                receiveTimePopupWindow = null;
                StringBuffer receiptTime = new StringBuffer();
                dateString = lvList.get(chooseTimeLvSelectedItem[0]).getDatetime();
                minString = gvList.get(chooseTimeGvSelectedItem[0]);
                receiptTime.append("预计"+lvList.get(chooseTimeLvSelectedItem[0]).getDatetime() + (gvList.size() > 0 ? gvList.get(chooseTimeGvSelectedItem[0]) : "") +"送达");

                if (null != afterpay_choocereceivetime_tv){
                    //设置选中的收货时间
                    afterpay_choocereceivetime_tv.setText(receiptTime.toString());
                }
            }
        });

//        receiptTimeGridAdapter = new ReceiptTimeGridAdapter(OrderFormActivity.this,gvMap);

        setBackgroundAlpha(0.3f);
        receiveTimePopupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }

    /**
     * 获取收货地址时间列表
     */
    private HashMap<Integer,ReceiptTimeInfo> receiptTimeInfos = new HashMap<Integer, ReceiptTimeInfo>();
    private List<ReceiptTimeInfo> lvList = new ArrayList<>();
    private List<String> gvList = new ArrayList<>();
    private void getReceiptTimeData(){
        //有数据的时候就不请求了
        if (null != receiptTimeInfos && receiptTimeInfos.size() > 0){
            return;
        }
        if (Constants.IS_LOCALDATA){
            for (int i = 0; i < 7; i++) {
                WeakReference<ReceiptTimeInfo> wf = new WeakReference<ReceiptTimeInfo>(new ReceiptTimeInfo());
                WeakReference<List<String>> wf2 = new WeakReference<List<String>>(new ArrayList<String>());
                if (i == 0){
                    wf.get().setDatetime("今天（周一)");
                    wf2.get().add("19:30");
                    wf.get().setTimes(wf2.get());
                }else{
                    if (i == 1) {
                        wf.get().setDatetime("09//12（周二)");
                    }
                    if (i == 2) {
                        wf.get().setDatetime("09//13（周三)");
                    }
                    if (i == 3) {
                        wf.get().setDatetime("09//14（周四)");
                    }
                    if (i == 4) {
                        wf.get().setDatetime("09//15（周五)");
                    }
                    if (i == 5) {
                        wf.get().setDatetime("09//16（周六)");
                    }
                    if (i == 6) {
                        wf.get().setDatetime("09//17（周日)");
                    }
                    wf2.get().add("11:30");
                    wf2.get().add("12:00");
                    wf2.get().add("12:30");
                    wf2.get().add("13:00");
                    wf2.get().add("13:30");
                    wf2.get().add("14:00");
                    wf2.get().add("14:30");
                    wf2.get().add("15:00");
                    wf2.get().add("15:30");
                    wf2.get().add("16:00");
                    wf2.get().add("16:30");
                    wf2.get().add("17:00");
                    wf2.get().add("17:30");
                    wf2.get().add("18:00");
                    wf2.get().add("18:30");
                    wf2.get().add("19:00");
                    wf2.get().add("19:30");
                    wf.get().setTimes(wf2.get());
                }
                receiptTimeInfos.put(i,wf.get());
            }

            for (int i = 0; i < receiptTimeInfos.size(); i++) {
                lvList.add(receiptTimeInfos.get(i));
            }
        }else {

            if(null == loadingBar){
                loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
            }
            if(null != loadingBar){
                loadingBar.show();
                loadingBar.setOnClickListener(null);
            }

            //取到已经保存的token（登录后的信息）
            String token = SystemUtil.getInstance().getToken();
            Log.i(Constants.TAG, token);

            //采用okhttp3来进行网络请求
            String url =   UrlManager.URL_ORDERFORM_TIMES;
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
                            Message msg = new Message();
                            msg.what = INFO_WHAT;
                            msg.obj = UPDATE_SUCCESS;
                            myHandler.sendMessage(msg);

                            final List<ReceiptTimeInfo> tempList = resInfo.getDataList(ReceiptTimeInfo.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < tempList.size(); i++) {
                                        receiptTimeInfos.put(i,tempList.get(i));
                                    }
                                    for (int i = 0; i < receiptTimeInfos.size(); i++) {
                                        lvList.add(receiptTimeInfos.get(i));
                                    }
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
    }

    /* ------------------------------------------------选择收货时间 end-------------------------------------------------------------*/
    /**选择优惠券后重新计算总价
     *cart	是	string	购物车信息
     coupon_id	是	int	用户选中的优惠券ID
     */
    private double dedPrice;//实际要付的金额
    private void dedPrice(String couponId){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //TODO：后续优化，增加从服务器获取回来的值当购物车内容
        if (cart == null || StringUtil.isEmpty(cart.toString())){
            Message msg = new Message();
            msg.what = ERROR_WHAT;
            msg.obj = "购物车内容为空";
            myHandler.sendMessage(msg);
            return;
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);


        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_ORDERFORM_DEDPRICE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("cart", cart.toString())
                .add("coupon_id",couponId).build();
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
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        final JSONObject jsonObject = JSONObject.parseObject(resInfo.getData().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double originPrice = Double.valueOf(jsonObject.get("origin_price").toString());
                                dedPrice = Double.valueOf(jsonObject.get("ded_price").toString());

                                //设置小计和购物车总金额
                                shoppingcart_totle_money_tv.setText("￥"+dedPrice+"");
                                order_form_subtotal_tv.setText(dedPrice+"");
                                veriticalline.setVisibility(View.VISIBLE);
                                shoppingcart_discount_tv.setVisibility(View.VISIBLE);
                                shoppingcart_discount_tv.setText("已优惠" + ArithUtil.sub(originPrice,dedPrice)+ "元");

                                order_form_coupon_num_tv.setVisibility(View.GONE);
                                order_form_discount_ll.setVisibility(View.VISIBLE);
                                order_form_discountname_tv.setText("VIP礼包");
                                order_form_discountnum_tv.setText("-￥"+ ArithUtil.sub(originPrice,dedPrice));
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

    /**
     * 立即支付 提交支付接口
     *
     * cart	是	string	58:3,59:1 购物车信息
     pay_type	是	int	1 微信支付 2支付宝支付 3账户余额支付
     ded_price	是	float	实付金额提交过来 (用户没有使用优惠券 即传递 app 这边计算出来的购物车总价)
     coupon_id	否	int	优惠券ID
     address_id	是	int	地址信息ID

     * @param couponId
     */
    private void orderPay(String couponId){
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.content_order_form),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //TODO：后续优化，增加从服务器获取回来的值当购物车内容
        if (cart == null || StringUtil.isEmpty(cart.toString())){
            UiHelper.ShowOneToast(getApplicationContext(),"购物车内容为空");
            return;
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);


        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_ORDERFORM_ORDERPAY;
        OkHttpClient mOkHttpClient = new OkHttpClient();

       /* String payType = "3";
        //支付方式 1 微信支付 2支付宝支付 3账户余额支付
        if(paymentType.equals(PAYMENTTYPE_WEIXIN)){
            payType = "1";
        }else if(paymentType.equals(PAYMENTTYPE_ZHIFUBAO)){
            payType = "2";
        }else{
            payType = "3";
        }*/

        //选择支付方式 1 微信支付 2支付宝支付 3账户余额支付
        String payType = "3";
        if(paymentCBHashMap.get(R.id.order_form_balance_cb)){
            payType = "3";
        }
        if (paymentCBHashMap.get(R.id.order_form_paymenttype_wx_cb)){
            payType = "1";
        }
        if (paymentCBHashMap.get(R.id.order_form_paymenttype_zfb_cb)){
            payType = "2";
        }


        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("cart", cart.toString())
                .add("coupon_id",couponId)
                .add("address_id",addressId+"")
//                .add("pay_type",payType)
                .add("day",dateString)
                .add("mins",minString).build();
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
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        final JSONObject jsonObject = JSONObject.parseObject(resInfo.getData().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //清空购物车内容
                                ShoppCartInstance.getInstance().clear();

                                //订单号
                                String  order_sn = (String) jsonObject.get("order_sn");

                                //进行支付
                                intent = new Intent(OrderFormActivity.this, PayActivity.class);
                                intent.putExtra("order_sn",order_sn);

                                DecimalFormat    df   = new DecimalFormat("######0.00");
                                String price = df.format(dedPrice);
                                intent.putExtra("dedPrice",price);
                                startActivity(intent);
                                OrderFormActivity.this.finish();
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

    /**
     * 选择地址
     * @param view
     */
    public void choseAddress(View view){
//        UiHelper.ShowOneToast(getApplication(),"选择地址");
        Intent intent= new Intent(OrderFormActivity.this, GoodsReceiptAddressManagerActivity.class);
        startActivity(intent);
    }

    /**
     * 选择收货时间
     * @param view
     */
    public void chooseReceiveTime(View view){
//        UiHelper.ShowOneToast(getApplicationContext(),"click");
        getReceiveTimePopupWindow();
    }

    BroadcastReceiver choseAddressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                GoodsReceiptInfo goodsReceiptInfo = (GoodsReceiptInfo) intent.getSerializableExtra("goodsReceiptInfo");
                if (goodsReceiptInfo != null) {
                    head_center_address_tv.setText(goodsReceiptInfo.getAddress_detail());
                    head_username_tv.setText(goodsReceiptInfo.getUsername());
                    head_tel_tv.setText(goodsReceiptInfo.getTel());
                    addressId = goodsReceiptInfo.getId();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (null != choseAddressReceiver){
            registerReceiver(choseAddressReceiver,intentFilter);
        }

        getReceiptTimeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != choseAddressReceiver){
            unregisterReceiver(choseAddressReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.head_left_iv:
                OrderFormActivity.this.finish();
                break;
            case R.id.afterpay_couponnum_tv:
            case R.id.order_form_coupon_turnright_iv:
            case R.id.order_form_discountnum_tv:
            case R.id.order_form_discountname_tv:
                //跳转到优惠券界面
                Message msg = new Message();
                msg.what = INFO_WHAT;
                msg.obj = "没有可用的优惠券";
                myHandler.sendMessage(msg);

                intent = new Intent(OrderFormActivity.this, PersonCouponActivity.class);
                intent.putExtra("flag","OrderFormActivity");
                intent.putExtra("origin_price",orderInfo.getOrigin_price()+"");
                startActivityForResult(intent, 1000);
                break;
            case R.id.order_form_paymenttype_rl:
                getPaymentPopupWindow();

                paymentPopupWindow.showAtLocation(this.findViewById(R.id.content_order_form), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.shoppingcart_immediatlypay_tv:
                //立即支付

                //提交地址id
                if (addressId <= 0){
                    UiHelper.ShowOneToast(getApplicationContext(),"请选择一个收货地址");
                    return;
                }

                //提交收货时间
                if (StringUtil.isEmpty(dateString) || StringUtil.isEmpty(minString)){
                    UiHelper.ShowOneToast(getApplicationContext(),"请选择一个收货时间");
                    return;
                }

                //提交购物车信息
                if (StringUtil.isEmpty(cart.toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"购物车信息失效,请重新订餐,给您造成的不便敬请谅解!");
                    return;
                }

                orderPay(couponId);
                break;
        }
    }

    private String couponId = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1000){
            couponId = data.getStringExtra("coupon_id");
            if (StringUtil.isNotEmpty(couponId)){
//                updateTotalMoney(Float.valueOf(disCount));

                dedPrice(couponId);

/*                veriticalline.setVisibility(View.VISIBLE);
                shoppingcart_discount_tv.setVisibility(View.VISIBLE);
                shoppingcart_discount_tv.setText("已优惠" + couponId +"元");

                order_form_coupon_num_tv.setVisibility(View.GONE);
                order_form_discount_ll.setVisibility(View.VISIBLE);
                order_form_discountname_tv.setText("VIP礼包");
                order_form_discountnum_tv.setText("-￥"+ couponId);*/
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("OrderForm----------------------------------------------------"+getTaskId());
    }
}
