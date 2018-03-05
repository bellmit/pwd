package com.tastebychance.sonchance.homepage.order;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.homepage.ShoppCartInstance;
import com.tastebychance.sonchance.homepage.banner.ImageViewHolder;
import com.tastebychance.sonchance.homepage.order.adapter.EvaluateListViewAdapter;
import com.tastebychance.sonchance.homepage.order.adapter.ProductAdapter;
import com.tastebychance.sonchance.homepage.order.bean.DishInfo;
import com.tastebychance.sonchance.homepage.order.bean.EvaluateInfo;
import com.tastebychance.sonchance.homepage.order.bean.EvaluateRes;
import com.tastebychance.sonchance.homepage.order.bean.NutrientCompositionInfo;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.homepage.orderform.OrderFormActivity;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.DensityUtils;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.util.Util;
import com.tastebychance.sonchance.view.ListViewForScrollView;
import com.tastebychance.sonchance.view.MyListView;

/**
 * 点餐界面
 *
 * @author shenbh
 *         <p>
 *         2017年8月11日
 */
public class OrderActivity extends MyBaseActivity implements OnClickListener{

    //传进来的菜品id
    private String dishesId;

    //菜的图片
//    private ImageView order_dish_img_iv;
    private ConvenientBanner mCb;
    //菜的名称
    private TextView order_dish_title_tv;
    //	折扣
    private TextView order_dish_discount_tv;
    //	加入购物车
    private TextView order_dish_addshoppingcart_tv;
    private LinearLayout order_dish_addshoppingcart_ll;
    private ImageView order_dish_addshoppingcart_remove_iv;
    private TextView order_dish_addshoppingcart_num_tv;
    private ImageView order_dish_addshoppingcart_add_iv;
    //	价钱
    private TextView order_dish_price_tv;
    //	已售数量
    private TextView order_dish_soldnum_tv;
    //	菜介绍
    private TextView order_dish_content_tv;
    //	客户评价
    private LinearLayout order_dish_evaluate_ll;
    //客户评价-好评数量
    private TextView order_dish_goodevaluatenum_tv;
    //	客户评价-差评数量
    private TextView order_dish_badevaluatenum_tv;

    //评论
    private RadioGroup order_dish_evaluate_radiogroup;
    private RadioButton order_dish_allevaluate_rb;
    private ListViewForScrollView order_evaluate_listview;

    //购物车
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private ProductAdapter productAdapter ;
    private SparseArray<ShoppingcartBean> selectedList;
    private LinearLayout order_bottom_shoppingcart;
    private TextView order_dish_unseleceddish_tv;//未选购商品
    private TextView order_dish_shoppingcart_price_tv;
    private TextView order_dish_shoppingcart_showdishes_tv;
    private ImageView order_dish_shoppingcart_payment_iv;
    private static DecimalFormat df;
    private Double totleMoney = 0.00;//总价钱

    //传入adapter的评论数据
    private List<EvaluateInfo> evaluateInfos;
    //全部评论
    private List<EvaluateInfo> allEvaluateInfos;
    //	好评
    private List<EvaluateInfo> goodEvaluateInfos;
    //	差评
    private List<EvaluateInfo> badEvaluateInfos;
    private EvaluateListViewAdapter evaluateListViewAdapter;
    private String radioButtonFlag = "all";//radio切换到什么评论渠道：全部评论、好评、差评

    //营养成分
    private LinearLayout home_order_nutrientcomposition_block_ll;

    private DishInfo dishInfo;//从服务器获取到的菜品信息

    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        dishesId = intent.getStringExtra("dishes_id");
        if (dishesId != null) {
            System.out.println(dishesId);
        } else {
//            dishesId = "49";
            CrashHandler.getInstance().handlerError("传到点餐页面的dishesId为空");
        }

        setContentView(R.layout.home_order);
        bindViews();

        addListener();
        setTitle();

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.LOGINSUCCESS_ACTION);

    }
    private void bindViews() {
//        order_dish_img_iv = (ImageView) findViewById(R.id.order_dish_img_iv);
        order_dish_title_tv = (TextView) findViewById(R.id.order_dish_title_tv);
        order_dish_discount_tv = (TextView) findViewById(R.id.order_dish_discount_tv);

        order_dish_addshoppingcart_tv = (TextView) findViewById(R.id.order_dish_addshoppingcart_iv);
        order_dish_addshoppingcart_ll = (LinearLayout) findViewById(R.id.order_dish_addshoppingcart_ll);
        order_dish_addshoppingcart_remove_iv = (ImageView) findViewById(R.id.order_dish_addshoppingcart_remove_iv);
        order_dish_addshoppingcart_num_tv = (TextView) findViewById(R.id.order_dish_addshoppingcart_num_tv);
        order_dish_addshoppingcart_add_iv = (ImageView) findViewById(R.id.order_dish_addshoppingcart_add_iv);

        order_dish_price_tv = (TextView) findViewById(R.id.order_dish_price_tv);
        order_dish_soldnum_tv = (TextView) findViewById(R.id.order_dish_soldnum_tv);
        order_dish_content_tv = (TextView) findViewById(R.id.order_dish_content_tv);
        order_dish_evaluate_ll = (LinearLayout) findViewById(R.id.order_dish_evaluate_ll);
        order_dish_goodevaluatenum_tv = (TextView) findViewById(R.id.order_dish_goodevaluatenum_tv);
        order_dish_badevaluatenum_tv = (TextView) findViewById(R.id.order_dish_badevaluatenum_tv);

        order_dish_evaluate_radiogroup = (RadioGroup) findViewById(R.id.order_dish_evaluate_radiogroup);
        order_dish_allevaluate_rb = (RadioButton) order_dish_evaluate_radiogroup.findViewById(order_dish_evaluate_radiogroup.getCheckedRadioButtonId());
        order_evaluate_listview = (ListViewForScrollView) findViewById(R.id.order_evaluate_listview);


        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);;
        order_bottom_shoppingcart = (LinearLayout) findViewById(R.id.order_bottom_shoppingcart);
        order_dish_unseleceddish_tv = (TextView) findViewById(R.id.order_dish_unseleceddish_tv);
        order_dish_shoppingcart_price_tv = (TextView) findViewById(R.id.order_dish_shoppingcart_price_tv);
        order_dish_shoppingcart_showdishes_tv = (TextView) findViewById(R.id.order_dish_shoppingcart_showdishes_tv);
        order_dish_shoppingcart_payment_iv = (ImageView) findViewById(R.id.order_dish_shoppingcart_payment_iv);

        home_order_nutrientcomposition_block_ll = (LinearLayout) findViewById(R.id.home_order_nutrientcomposition_block_ll);
    }

    private void addListener() {
        //点击切换评论类型
        order_dish_evaluate_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                order_dish_allevaluate_rb = (RadioButton) order_dish_evaluate_radiogroup.findViewById(checkedId);
                String content = order_dish_allevaluate_rb.getText().toString();
                evaluateInfos.clear();

                if(content.equals("好评")){
                    order_dish_evaluate_radiogroup.check(R.id.order_dish_evaluate_good_rb);

                    radioButtonFlag = "good";
                    if(null == goodEvaluateInfos){
                        goodEvaluateInfos = new ArrayList<EvaluateInfo>();
                    }
                    if(goodEvaluateInfos.size() <= 0){
                        if (Constants.IS_LOCALDATA){
                            goodEvaluateInfos = getEvaluateInfosByLocal();
                        }else{
                            getEvaluateInfos(1);
                        }
                    }

                    evaluateInfos.addAll(goodEvaluateInfos);
                }else if(content.equals("差评")){
                    order_dish_evaluate_radiogroup.check(R.id.order_dish_evaluate_bad_rb);

                    radioButtonFlag = "bad";
                    if(null == badEvaluateInfos){
                        badEvaluateInfos = new ArrayList<EvaluateInfo>();
                    }
                    if(badEvaluateInfos.size() <= 0){
                        if (Constants.IS_LOCALDATA){
                            badEvaluateInfos = getEvaluateInfosByLocal();
                        }else{
                            getEvaluateInfos(2);
                        }
                    }
                    evaluateInfos.addAll(badEvaluateInfos);
                }else if(content.equals("全部评价")){
                    order_dish_evaluate_radiogroup.check(R.id.order_dish_evaluate_all_rb);

                    radioButtonFlag = "all";
                    if(null == allEvaluateInfos){
                        allEvaluateInfos = new ArrayList<EvaluateInfo>();
                    }
                    if(allEvaluateInfos.size() <= 0){
                        if (Constants.IS_LOCALDATA){
                            allEvaluateInfos = getEvaluateInfosByLocal();
                        }else{
                            getEvaluateInfos(0);
                        }
                    }
                    evaluateInfos.addAll(allEvaluateInfos);
                }
                evaluateListViewAdapter.notifyDataSetChanged();
            }
        });

        order_dish_shoppingcart_price_tv.setOnClickListener(this);//展示购物车内容
        order_dish_shoppingcart_showdishes_tv.setOnClickListener(this);//展示购物车内容
        order_dish_addshoppingcart_tv.setOnClickListener(this);//加入购物车
        order_dish_addshoppingcart_add_iv.setOnClickListener(this);//增加数量
        order_dish_addshoppingcart_remove_iv.setOnClickListener(this);//减少数量
        order_dish_shoppingcart_payment_iv.setOnClickListener(this);//点击“下单支付”
    }

    private void initObject(){
        initSet();
        df = new DecimalFormat("0.00");

        //刚进入页面如果购物车为空，那么显示的控件变化
        update();
    }

    private void initSet(){
        if (null == evaluateInfos) {
            evaluateInfos = new ArrayList<EvaluateInfo>();
        }
        if (null == allEvaluateInfos) {
            allEvaluateInfos = new ArrayList<EvaluateInfo>();
        }
        if (goodEvaluateInfos == null) {
            goodEvaluateInfos = new ArrayList<EvaluateInfo>();
        }
        if (badEvaluateInfos == null) {
            badEvaluateInfos = new ArrayList<EvaluateInfo>();
        }
        if (selectedList == null) {
            selectedList = new SparseArray<>();
        }
    }

    //隐藏“加入购物车”，更新数量
    private void updateOrderDishNum(int dishNum) {
        order_dish_addshoppingcart_num_tv.setText(dishNum +"");
    }

    //显示“加入购物车”
    private void showAddToShoppingCart(){
        //加入购物车
        order_dish_addshoppingcart_tv.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_ll.setVisibility(View.GONE);
        order_dish_addshoppingcart_add_iv.setVisibility(View.GONE);
        order_dish_addshoppingcart_remove_iv.setVisibility(View.GONE);
    }

    //隐藏“加入购物车”
    private void hideAddToShoppingCart(){
        //加入购物车
        order_dish_addshoppingcart_tv.setVisibility(View.GONE);
        order_dish_addshoppingcart_ll.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_add_iv.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_remove_iv.setVisibility(View.VISIBLE);
    }

    //初始化购物车内容数量
    private void initSelectedListData() {
       /* WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
        wf.get().setNum(Constants.shoppingCartHashMap.get(dishesId).getNum());
        wf.get().setPrice(Constants.shoppingCartHashMap.get(dishesId).getHomePageListRes().getPrice());
        wf.get().setId(Constants.shoppingCartHashMap.get(dishesId).getHomePageListRes().getId());
        wf.get().setDishname(Constants.shoppingCartHashMap.get(dishesId).getHomePageListRes().getName());
        selectedList.put(wf.get().getId(),wf.get());*/

        selectedList.clear();
        if (ShoppCartInstance.getInstance().getCount() > 0) {

            HashMap<Integer,ShoppingcartBean> hashMap = ShoppCartInstance.getInstance().getAll();
            for (Map.Entry<Integer,ShoppingcartBean> entry: hashMap.entrySet()) {
                WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                wf.get().setNum(entry.getValue().getNum());
                wf.get().setDishname(entry.getValue().getDishname());
                wf.get().setId(entry.getKey());
                wf.get().setPrice(entry.getValue().getPrice());
                selectedList.put(wf.get().getId(),wf.get());
            }

            showShoppingCartNum(ShoppCartInstance.getInstance().getAllDishNum());
        }
    }

    //购物车没有东西
    private void showShoppingCartNothing(){
        order_dish_unseleceddish_tv.setVisibility(View.VISIBLE);
        order_dish_shoppingcart_price_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_showdishes_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_payment_iv.setBackgroundColor(getResources().getColor(R.color.gray2));

        refreshShoppingTotalCartNum(0);
    }

    //购物车有东西
    private void showShoppingCartNum(int totalNum){
        order_dish_unseleceddish_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_price_tv.setVisibility(View.VISIBLE);
        order_dish_shoppingcart_showdishes_tv.setVisibility(View.VISIBLE);

        refreshShoppingTotalCartNum(totalNum);
        order_dish_shoppingcart_payment_iv.setBackgroundColor(getResources().getColor(R.color.green));

    }

    //更新总的价钱
    public void updateTotalPrice() {
        int size = selectedList.size();
        int count = 0;
        for (int i = 0; i < size; i++) {

            ShoppingcartBean item = selectedList.valueAt(i);
            count += item.getNum();
            totleMoney += item.getNum() * item.getPrice();
        }
        order_dish_shoppingcart_price_tv.setText(StringUtil.setShoppingcartTotalMoney(String.valueOf(df.format(totleMoney))));
        totleMoney = 0.00;
    }

    //更新购物车总的数量
    public void refreshShoppingTotalCartNum(int totalNum){
        order_dish_shoppingcart_showdishes_tv.setText(StringUtil.setStringRedColor("共"+totalNum+"份套餐","共".length(),("共"+totalNum+"份套餐").length() - "份套餐".length()));
    }

    //更新服务器请求到的数据到购物车内
    private void updateShoppingCart(ShoppingcartBean shoppingcartBean){

        ShoppingcartBean temp = ShoppCartInstance.getInstance().get(shoppingcartBean.getId());
        if (null != temp){//删减
            ShoppCartInstance.getInstance().get(shoppingcartBean.getId()).setNum(shoppingcartBean.getNum());
            if (ShoppCartInstance.getInstance().get(shoppingcartBean.getId()).getNum() <= 0){
                if (shoppingcartBean.getId() == dishInfo.getId()){
                    showAddToShoppingCart();//显示“加入购物车”
                }
                ShoppCartInstance.getInstance().remove(shoppingcartBean.getId());
            }
        }else{//只有增加或添加到购物车的时候才有用到
            WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
            wf.get().setNum(shoppingcartBean.getNum());
            wf.get().setDishname(shoppingcartBean.getDishname());
            wf.get().setId(shoppingcartBean.getId());
            wf.get().setPrice(shoppingcartBean.getPrice());
            ShoppCartInstance.getInstance().add(wf.get());
        }

        update();
    }



    //更新Constants.ShoppingCart购物车中的数据
   /* private void updateConstansShoppingCartData(int num){
        WeakReference<ShoppingCartBean> wf2 = new WeakReference<ShoppingCartBean>(new ShoppingCartBean());
        WeakReference<HomePageListRes> wf3 = new WeakReference<HomePageListRes>(new HomePageListRes());
        wf3.get().setId(dishInfo.getId());
        wf3.get().setName(dishInfo.getName());
        wf3.get().setComment_count(dishInfo.getComments_count());
        wf3.get().setSail_count(dishInfo.getSail_count());
        wf3.get().setPrice(dishInfo.getPrice());
        wf3.get().setGood_comments(dishInfo.getGood_comment());
        wf3.get().setBad_comments(dishInfo.getBad_comment());

        wf2.get().setNum(num);
        wf2.get().setHomePageListRes(wf3.get());
//        Constants.shoppingCartHashMap.put(dishInfo.getId(), wf2.get());

        WeakReference<ShoppingcartBean> wf4 = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
        wf4.get().setDishname(dishInfo.getName());
        wf4.get().setNum(num);
        wf4.get().setPrice(dishInfo.getPrice());
        wf4.get().setId(dishInfo.getId());
        ShoppCartInstance.getInstance().add(wf4.get());
    }*/

    /**
     * 设置标题
     */
    private void setTitle() {
        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        ImageView subTitleEdtIv = (ImageView) findViewById(R.id.head_center_address_iv);
        if (center_tv != null)
            center_tv.setText("点餐");
        if (subTitle != null) {
            subTitle.setText("软件园c区1号楼");
        }
        if (subTitleEdtIv != null){
            subTitleEdtIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编辑地址
                }
            });
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    OrderActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.VISIBLE);
            right_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_share));
            right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO：点击分享

                }
            });
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    /*********************************************购物车*************************************/
    //查看购物车布局
    private void showBottomSheet(){
        bottomSheet = createBottomSheetView();
        if(null == bottomSheetLayout){
            bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);
        }
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else{
            if (selectedList.size() != 0){
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }

    }

    //创建购物车
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getWindow().getDecorView(),false);
        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
        ImageView clear = (ImageView) view.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });
//        setSelectedListData();

        productAdapter = new ProductAdapter(this,selectedList);
        lv_product.setAdapter(productAdapter);
        return view;
    }

    //清空购物车
    public void clearCart() {
        ShoppCartInstance.getInstance().clear();
        update();
    }

    //更新购物车内容
    private void update(){
        //更新selected
        initSelectedListData();

        //显示or隐藏“加入购物车”。隐藏时显示餐品数量(注意此处使用的是dishesId(不用dishInfo.getId()是因为刚进入页面要刷新一下，此时dishInfo为null)，即只针对页面的“加入购物车”显示与否进行判断)
        if (ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)) != null){
            if (ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)).getNum() > 0){
                hideAddToShoppingCart();//隐藏“加入购物车”
                updateOrderDishNum(ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)).getNum());
            }else{
                showAddToShoppingCart();//显示“加入购物车”
                ShoppCartInstance.getInstance().remove(Integer.valueOf(dishesId));
            }
        }else{
            showAddToShoppingCart();//显示“加入购物车”
        }

        //底部购物车，是否展示数量，
        if (ShoppCartInstance.getInstance().getCount() <= 0){
            showShoppingCartNothing();//隐藏底部购物车数量，展示“未选购”布局
        }else{//展示总金额与餐品数量
            showShoppingCartNum(ShoppCartInstance.getInstance().getAllDishNum());
            updateTotalPrice();
        }

        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    public void handlerCarNum(int type, ShoppingcartBean shoppingcartBean, boolean refreshGoodList) {
        if (type == 0) {//减少
            ShoppingcartBean temp = selectedList.get(shoppingcartBean.getId());
            if (temp != null) {
                if (temp.getNum() < 2) {
                    shoppingcartBean.setNum(0);
                    ShoppCartInstance.getInstance().remove(shoppingcartBean.getId());
 /*                   if (shoppingcartBean.getId() == dishInfo.getId()){
                        showAddToShoppingCart();//显示“加入购物车”
                    }*/
                    update();
                } else {
                    int i = shoppingcartBean.getNum();
                    shoppingcartBean.setNum(--i);
                    updateShoppingCart(shoppingcartBean);
                }
            }
        } else if (type == 1) {
            ShoppingcartBean temp = selectedList.get(shoppingcartBean.getId());
            if (temp == null) {
                shoppingcartBean.setNum(1);
                selectedList.append(shoppingcartBean.getId(), shoppingcartBean);

                updateShoppingCart(shoppingcartBean);
            } else {
                int i = shoppingcartBean.getNum();
                shoppingcartBean.setNum(++i);

                updateShoppingCart(shoppingcartBean);
            }
        }
    }

    /*********************************************购物车*************************************/
    /**从服务器获取菜品信息（除评论外的数据）
     *
     */
    public void getDishInfo() {
        String token = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), Constants.TEMP, "token");
        String url =  UrlManager.URL_ORDER_ORDER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("dishes_id", dishesId).add("token",token).build();
//        RequestBody formBody = new FormBody.Builder().add("dishes_id", "55").add("token",token).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        // 获取Data的JSONObject对象
                        dishInfo = JSONObject.parseObject(resInfo.getData().toString(), DishInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dishInfo != null) {
                                    initData(dishInfo);
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

    /**
     * 从服务器获取评论数据
     *
     * @param type 默认为0代表全部评论，1为好评，2为差评
     */
    private int allEvaluatePageIndex = 1;
    private int goodEvaluatePageIndex = 1;
    private int badEvaluatePageIndex = 1;
    private int pageIndex = 1;
    public void getEvaluateInfos(final int type) {
        String token = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), Constants.TEMP, "token");
        String url =  UrlManager.URL_ORDER_COMMENTS;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        if (type == 1){
            pageIndex = goodEvaluatePageIndex;
        }else if (type == 2){
            pageIndex = badEvaluatePageIndex;
        }else{
            pageIndex = allEvaluatePageIndex;
        }

        RequestBody formBody = new FormBody.Builder().add("dishes_id", dishesId).add("p", pageIndex+"").add("type",type+"").add("token",token).build();
//        RequestBody formBody = new FormBody.Builder().add("dishes_id", "58").add("p", pageIndex+"").add("type",type+"").add("token",token).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
        //                TODO：获取评论
                        EvaluateRes evaluateRes = JSONObject.parseObject(resInfo.getData().toString(),EvaluateRes.class);
                        final List<EvaluateInfo> list = evaluateRes.getList(EvaluateInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = ERROR_WHAT;
                                msg.obj = GETDATA_SUCCESS;
                                myHandler.sendMessage(msg);

                                if (list != null) {
                                    if (list.size() <= 0){
                                        if (pageIndex != 1){//确保刚进页面的时候如果没有评论信息不提示“没有更多评论了”
                                            UiHelper.ShowOneToast(getApplicationContext(),"没有更多评论了!");
                                        }

                                        if (type == 1){
                                            setEvaluateData(goodEvaluateInfos);
                                        }else if(type == 2){
                                            setEvaluateData(badEvaluateInfos);
                                        }else{
                                            setEvaluateData(allEvaluateInfos);
                                        }
                                    }else{
                                        if (type == 1){
                                            goodEvaluateInfos.addAll(list);
                                            setEvaluateData(goodEvaluateInfos);
                                        }else if (type == 2){
                                            badEvaluateInfos .addAll(list);
                                            setEvaluateData(badEvaluateInfos);
                                        }else{
                                            allEvaluateInfos .addAll(list);
                                            setEvaluateData(allEvaluateInfos);
                                        }
                                    }
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

    private void initData(DishInfo dishInfo){
        //菜的图片
        mCb = (ConvenientBanner) findViewById(R.id.id_cb);
        mImageList = dishInfo.getPics();
        initBannerData();
        setBannerListener();

        //菜的名称
        order_dish_title_tv.setText(dishInfo.getName());
        //	折扣
        if (StringUtil.isEmpty(dishInfo.getPromotion())){
            order_dish_discount_tv.setVisibility(View.GONE);
        }else{
            order_dish_discount_tv.setVisibility(View.VISIBLE);
            order_dish_discount_tv.setText(dishInfo.getPromotion());
        }
        //	前面购物车该菜品的数量
//        order_dish_addshoppingcart_num_tv;
        //	价钱
        order_dish_price_tv.setText(StringUtil.setShoppingcartTotalMoney(String.valueOf(df.format(dishInfo.getPrice()))));
        //	已售数量
        String soldNum = "已售:"+ dishInfo.getSail_count()+"套";
        order_dish_soldnum_tv.setText(StringUtil.setStringGreenColor(soldNum, "已售:".length(),soldNum.length()));
        //	菜介绍
        order_dish_content_tv.setText(dishInfo.getDescribe());
        //客户评价-好评数量
        String goodValuate = "好评:"+dishInfo.getGood_comment()+"条";
        order_dish_goodevaluatenum_tv.setText(StringUtil.setStringGreenColor(goodValuate,"好评:".length(),goodValuate.length()));
        //	客户评价-差评数量
        String badValuate = "差评:"+dishInfo.getBad_comment()+"条";
        order_dish_badevaluatenum_tv.setText(StringUtil.setStringGreenColor(badValuate,"差评:".length(),badValuate.length()));

        //评论
//        order_evaluate_listview;

        List<NutrientCompositionInfo> nClist = new ArrayList<>();
        nClist.clear();
        nClist  = dishInfo.getTag_list();

        setNutrientcomposition(nClist);
    }

    private List<String> mImageList = new ArrayList<>();//存放服务器获取回来的图片的url
    private void initBannerData() {
        //TODO:写死了控件的长宽一样，为正方形
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Util.getScreenWidth(), Util.getScreenWidth());
        mCb.setLayoutParams(lp);
        mCb.setPages(new CBViewHolderCreator<ImageViewHolder>(){

            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
    },mImageList);
//                .setPageIndicator(new int[]{R.drawable.ponit_normal,R.drawable.point_select})//设置两个点作为指示器
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器位置为水平居中
//        mCb.setCanLoop(true);
    }

    private void setBannerListener() {
        /*mCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mLinkList && mLinkList.size() > position){
                    //点击图片进行链接跳转

                    UiHelper.ShowOneToast(getApplicationContext(),"点击了条目" + position);
                }
            }
        });*/
    }

    /**
     * 动态添加营养成分布局
     */
    private void setNutrientcomposition(List<NutrientCompositionInfo> nClist) {

        if (null != nClist && nClist.size() > 0){

            LinearLayout.LayoutParams tv_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams iv_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_lp.gravity = Gravity.CENTER;
            iv_lp.height = DensityUtils.dp2px(this,30);
            iv_lp.width = DensityUtils.dp2px(this,30);

            for (int i = 0; i < nClist.size() ; i ++) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                if (i != nClist.size() -1){
                    lp.rightMargin = DensityUtils.dp2px(this,34);
                }
                tv_lp.setMargins(0, DensityUtils.dp2px(this,4), 0, 0);
                tv_lp.gravity = Gravity.CENTER;

                LinearLayout view = new LinearLayout(this);
                view.setLayoutParams(lp);//设置布局参数
                view.setOrientation(LinearLayout.VERTICAL);

                ImageView img = new ImageView(this);
                ImageDownLoad.getDownLoadSmallImg(nClist.get(i).getTag_image(),img);
                img.setLayoutParams(iv_lp);

                TextView name = new TextView(this);
                name.setLayoutParams(tv_lp);
                name.setText(nClist.get(i).getTag_name());

                TextView value = new TextView(this);
                value.setLayoutParams(tv_lp);
                value.setText(nClist.get(i).getTag_num());

                view.addView(img);
                view.addView(name);
                view.addView(value);

                home_order_nutrientcomposition_block_ll.addView(view);
            }
        }
    }

    /**
     * 获取更多评论
     * <p>
     * 思路：利用3个集合分别存储全部评论、好评、差评；点击获取更多评论的时候获取从11~20条，加到对应的集合中，同时展示这个集合的数据。（
     * 切换好评的时候是否需要把另外两个集合的数据进行清理只保留1~10个数据）
     *
     * @param view
     */
    public void getmoreevaluate(View view) {

        //从服务器请求评论数据
        if (radioButtonFlag.equals("good")){//从服务器请求好评数据。默认为0代表全部评论，1为好评，2为差评
            goodEvaluatePageIndex ++;

            if (Constants.IS_LOCALDATA){
                allEvaluateInfos = getEvaluateInfosByLocal();
            }else{
                getEvaluateInfos(1);
            }
        }else if(radioButtonFlag.equals("bad")){
            badEvaluatePageIndex ++;

            if (Constants.IS_LOCALDATA){
                allEvaluateInfos = getEvaluateInfosByLocal();
            }else{
                getEvaluateInfos(2);
            }
        }else{
            allEvaluatePageIndex ++;

            if (Constants.IS_LOCALDATA){
                allEvaluateInfos = getEvaluateInfosByLocal();
            }else{
                getEvaluateInfos(0);
            }
        }
        evaluateListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 提交购物车信息到服务器
     */
    private void pay() {
        //购物车信息
        StringBuffer cart = new StringBuffer();
        HashMap<Integer,ShoppingcartBean> hashMap = ShoppCartInstance.getInstance().getAll();
        for (Map.Entry<Integer,ShoppingcartBean> entry:hashMap.entrySet()) {
            int id = entry.getKey();
            int num = entry.getValue().getNum();
            cart.append(id +":" + num +",");
        }

        if (StringUtil.isEmpty(cart.toString())){
            //TODO：封装更多的信息，比如用户id之类的，方便更快定位到错误原因
            CrashHandler.getInstance().handlerError("程序异常,提交的时候购物车不能为空!");
            UiHelper.ShowOneToast(getApplicationContext(),"提交失败,请联系产商!");
            return;
        }
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =  UrlManager.URL_PAY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("cart",cart.toString()).build();//"10：2，10： 2，"表示dishesid:count数量
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
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
                        Message msg = new Message();
                        msg.what = INFO_WHAT;
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        final OrderInfo orderInfo = JSONObject.parseObject(resInfo.getData().toString(),OrderInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //跳转到配送界面
                                Intent intent = new Intent(OrderActivity.this,OrderFormActivity.class);
                                intent.putExtra("orderInfo", orderInfo);
                                startActivity(intent);
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

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent){
                if (null != intent.getStringExtra("toActivity") && intent.getStringExtra("toActivity").equals(Constants.TO_ORDERFORM)){
                    pay();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        initObject();

        //获取除了评论外的数据
        if (Constants.IS_LOCALDATA) {
            getDishInfoByLocal();
        } else {
            getDishInfo();
        }
//        getAddress();

        //获取评论数据
        List<EvaluateInfo> tempEvaluateInfos = null;
        if (Constants.IS_LOCALDATA) {
            tempEvaluateInfos = getEvaluateInfosByLocal();
            if (null != tempEvaluateInfos) {
                setEvaluateData(tempEvaluateInfos);
            }
        } else {
            getEvaluateInfos(0);
        }

        if (null != myBroadcastReceiver){
            registerReceiver(myBroadcastReceiver,intentFilter);
        }
    }

    /**
     * 本地模拟评论数据
     * @return
     */
    private List<EvaluateInfo> getEvaluateInfosByLocal() {

        if(radioButtonFlag.equals("good")){
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wr = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wr.get().setMobile("151****5461");
                wr.get().setContent("味道不错，商家态度好。");
                wr.get().setGrade(8);
                goodEvaluateInfos.add(wr.get());
            }

            return  goodEvaluateInfos;
        }else if(radioButtonFlag.equals("bad")){
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wf.get().setMobile("138****9652");
                wf.get().setContent("还有很多地方需要改进。还有很多地方需要改进。还有很多地方需要改进。");
                wf.get().setGrade(8);
                badEvaluateInfos.add(wf.get());
            }

            return  badEvaluateInfos;
        }else {
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wf.get().setMobile("151****5461");
                wf.get().setContent("味道不错，商家态度好。");
                wf.get().setGrade(8);
                allEvaluateInfos.add(wf.get());

            }
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> sf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                sf.get().setMobile("138****9652");
                sf.get().setContent("还有很多地方需要改进。还有很多地方需要改进。还有很多地方需要改进。");
                sf.get().setGrade(8);
                allEvaluateInfos.add(sf.get());
            }

            return allEvaluateInfos;
        }
    }

    //设置评论数据
    private void setEvaluateData(List<EvaluateInfo> tempEvaluateInfos) {
        if (evaluateInfos == null) {
            evaluateInfos = new ArrayList<EvaluateInfo>();
        }
        evaluateInfos.clear();
        evaluateInfos.addAll(tempEvaluateInfos);

        evaluateListViewAdapter = new EvaluateListViewAdapter(this, evaluateInfos);
        order_evaluate_listview.setAdapter(evaluateListViewAdapter);
    }

    /**
     * 本地模拟除评论外的数据
     */
    private void getDishInfoByLocal() {
        WeakReference<DishInfo> wf = new WeakReference<DishInfo>(new DishInfo());
        wf.get().setId(20);
        wf.get().setName("烤鸭套餐");
        List<String> pics = new ArrayList<>();
        pics.add("http://img3.imgtn.bdimg.com/it/u=940174915,1631657552&fm=11&gp=0.jpg");
        pics.add("http://rescdn.qqmail.com/dyimg/20141106/7F393E5C9D9D.jpg");
        pics.add("http://img.mp.itc.cn/upload/20160820/7a1471b0cf9246be901c1daa7d4ec98c_th.jpg");
        wf.get().setPics(pics);
        wf.get().setDescribe("反反复复反反复复反反复复凤飞飞");
        wf.get().setPrice(22.0f);
        wf.get().setSail_count(0);
        wf.get().setComments_count(0);
        wf.get().setGood_comment(0);
        wf.get().setBad_comment(0);

        String[] drawableIds = {
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504693287373&di=229ff6bfc209f09683858215ed22a8b3&imgtype=0&src=http%3A%2F%2Fimg3.gomein.net.cn%2Fimage%2Fbbcimg%2Fproduction_image%2Fnimg%2F20130716%2F11%2F8001952722%2F103301098_360.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504693287373&di=229ff6bfc209f09683858215ed22a8b3&imgtype=0&src=http%3A%2F%2Fimg3.gomein.net.cn%2Fimage%2Fbbcimg%2Fproduction_image%2Fnimg%2F20130716%2F11%2F8001952722%2F103301098_360.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504693287373&di=229ff6bfc209f09683858215ed22a8b3&imgtype=0&src=http%3A%2F%2Fimg3.gomein.net.cn%2Fimage%2Fbbcimg%2Fproduction_image%2Fnimg%2F20130716%2F11%2F8001952722%2F103301098_360.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504693287373&di=229ff6bfc209f09683858215ed22a8b3&imgtype=0&src=http%3A%2F%2Fimg3.gomein.net.cn%2Fimage%2Fbbcimg%2Fproduction_image%2Fnimg%2F20130716%2F11%2F8001952722%2F103301098_360.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504693287373&di=229ff6bfc209f09683858215ed22a8b3&imgtype=0&src=http%3A%2F%2Fimg3.gomein.net.cn%2Fimage%2Fbbcimg%2Fproduction_image%2Fnimg%2F20130716%2F11%2F8001952722%2F103301098_360.jpg"
        };
        String[] names = {"热量","脂肪","蛋白质","纤维","胆固醇"};
        List<NutrientCompositionInfo> nClist = new ArrayList<>();
        for (int i =0;i <5;i ++) {
            WeakReference<NutrientCompositionInfo> wf2 = new WeakReference<NutrientCompositionInfo>(new NutrientCompositionInfo());
            wf2.get().setTag_image(drawableIds[i]);
            wf2.get().setTag_name(names[i]);
            wf2.get().setTag_num("32g");
            nClist.add(wf2.get());
        }

        wf.get().setTag_list(nClist);
        wf.get().setRaw_material("");

        dishInfo = wf.get();
        initData(dishInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (null != mCb){
//            mCb.startTurning(2000);//设置开始轮播以及轮播时间
//        }

        if (dishesId != null) {
            System.out.println(dishesId);
        } else {
            dishesId = "49";
            CrashHandler.getInstance().handlerError("传到点餐页面的dishesId为空");
        }
        System.out.println("Order----------------------------------------------------"+getTaskId());
    }

    @Override
    protected void onPause() {
//        if (null != mCb){
//            mCb.stopTurning();//停止轮播
//        }
        super.onPause();
        Constants.orderedDishes.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        evaluateInfos = null;
        allEvaluateInfos = null;
        goodEvaluateInfos = null;
        badEvaluateInfos = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBroadcastReceiver) {
            unregisterReceiver(myBroadcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //展示购物车内容
            case R.id.order_dish_shoppingcart_price_tv:
            case R.id.order_dish_shoppingcart_showdishes_tv:
                showBottomSheet();
                break;

            //加入购物车
            case R.id.order_dish_addshoppingcart_iv:

//                hideAddToShoppingCart();
                if (null != dishInfo) {
                    WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                    wf.get().setNum(1);
                    wf.get().setId(dishInfo.getId());
                    wf.get().setPrice(dishInfo.getPrice());
                    wf.get().setDishname(dishInfo.getName());
                    updateShoppingCart(wf.get());
                }
                break;

            //增加数量
            case R.id.order_dish_addshoppingcart_add_iv:

                if (null != dishInfo ){
                    WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                    wf.get().setNum(ShoppCartInstance.getInstance().get(dishInfo.getId()).getNum() + 1);
                    wf.get().setId(dishInfo.getId());
                    wf.get().setPrice(dishInfo.getPrice());
                    wf.get().setDishname(dishInfo.getName());
                    updateShoppingCart(wf.get());
                }

                break;

            //减少数量
            case R.id.order_dish_addshoppingcart_remove_iv:
                if (null != dishInfo ){
                    WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                    wf.get().setNum(ShoppCartInstance.getInstance().get(dishInfo.getId()).getNum() - 1);
                    wf.get().setId(dishInfo.getId());
                    wf.get().setPrice(dishInfo.getPrice());
                    wf.get().setDishname(dishInfo.getName());
                    updateShoppingCart(wf.get());
                }

                break;

            //点击“下单支付”
            case R.id.order_dish_shoppingcart_payment_iv:
                if (selectedList.size() <= 0){
                    UiHelper.ShowOneToast(getApplicationContext(),"请选择商品");
                }else{
                    //跳转到订单界面
                    if(!SystemUtil.getInstance().getIsLogin()){
                        SystemUtil.getInstance().intentToLoginActivity(OrderActivity.this,Constants.TO_ORDERFORM);
                    }else{
                        pay();
                    }
                }
                break;
            default:
                break;
        }
    }

}
