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
 * ????????????
 *
 * @author shenbh
 *         <p>
 *         2017???8???11???
 */
public class OrderActivity extends MyBaseActivity implements OnClickListener{

    //??????????????????id
    private String dishesId;

    //????????????
//    private ImageView order_dish_img_iv;
    private ConvenientBanner mCb;
    //????????????
    private TextView order_dish_title_tv;
    //	??????
    private TextView order_dish_discount_tv;
    //	???????????????
    private TextView order_dish_addshoppingcart_tv;
    private LinearLayout order_dish_addshoppingcart_ll;
    private ImageView order_dish_addshoppingcart_remove_iv;
    private TextView order_dish_addshoppingcart_num_tv;
    private ImageView order_dish_addshoppingcart_add_iv;
    //	??????
    private TextView order_dish_price_tv;
    //	????????????
    private TextView order_dish_soldnum_tv;
    //	?????????
    private TextView order_dish_content_tv;
    //	????????????
    private LinearLayout order_dish_evaluate_ll;
    //????????????-????????????
    private TextView order_dish_goodevaluatenum_tv;
    //	????????????-????????????
    private TextView order_dish_badevaluatenum_tv;

    //??????
    private RadioGroup order_dish_evaluate_radiogroup;
    private RadioButton order_dish_allevaluate_rb;
    private ListViewForScrollView order_evaluate_listview;

    //?????????
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private ProductAdapter productAdapter ;
    private SparseArray<ShoppingcartBean> selectedList;
    private LinearLayout order_bottom_shoppingcart;
    private TextView order_dish_unseleceddish_tv;//???????????????
    private TextView order_dish_shoppingcart_price_tv;
    private TextView order_dish_shoppingcart_showdishes_tv;
    private ImageView order_dish_shoppingcart_payment_iv;
    private static DecimalFormat df;
    private Double totleMoney = 0.00;//?????????

    //??????adapter???????????????
    private List<EvaluateInfo> evaluateInfos;
    //????????????
    private List<EvaluateInfo> allEvaluateInfos;
    //	??????
    private List<EvaluateInfo> goodEvaluateInfos;
    //	??????
    private List<EvaluateInfo> badEvaluateInfos;
    private EvaluateListViewAdapter evaluateListViewAdapter;
    private String radioButtonFlag = "all";//radio????????????????????????????????????????????????????????????

    //????????????
    private LinearLayout home_order_nutrientcomposition_block_ll;

    private DishInfo dishInfo;//????????????????????????????????????

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
            CrashHandler.getInstance().handlerError("?????????????????????dishesId??????");
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
        //????????????????????????
        order_dish_evaluate_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                order_dish_allevaluate_rb = (RadioButton) order_dish_evaluate_radiogroup.findViewById(checkedId);
                String content = order_dish_allevaluate_rb.getText().toString();
                evaluateInfos.clear();

                if(content.equals("??????")){
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
                }else if(content.equals("??????")){
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
                }else if(content.equals("????????????")){
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

        order_dish_shoppingcart_price_tv.setOnClickListener(this);//?????????????????????
        order_dish_shoppingcart_showdishes_tv.setOnClickListener(this);//?????????????????????
        order_dish_addshoppingcart_tv.setOnClickListener(this);//???????????????
        order_dish_addshoppingcart_add_iv.setOnClickListener(this);//????????????
        order_dish_addshoppingcart_remove_iv.setOnClickListener(this);//????????????
        order_dish_shoppingcart_payment_iv.setOnClickListener(this);//????????????????????????
    }

    private void initObject(){
        initSet();
        df = new DecimalFormat("0.00");

        //??????????????????????????????????????????????????????????????????
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

    //??????????????????????????????????????????
    private void updateOrderDishNum(int dishNum) {
        order_dish_addshoppingcart_num_tv.setText(dishNum +"");
    }

    //???????????????????????????
    private void showAddToShoppingCart(){
        //???????????????
        order_dish_addshoppingcart_tv.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_ll.setVisibility(View.GONE);
        order_dish_addshoppingcart_add_iv.setVisibility(View.GONE);
        order_dish_addshoppingcart_remove_iv.setVisibility(View.GONE);
    }

    //???????????????????????????
    private void hideAddToShoppingCart(){
        //???????????????
        order_dish_addshoppingcart_tv.setVisibility(View.GONE);
        order_dish_addshoppingcart_ll.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_add_iv.setVisibility(View.VISIBLE);
        order_dish_addshoppingcart_remove_iv.setVisibility(View.VISIBLE);
    }

    //??????????????????????????????
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

    //?????????????????????
    private void showShoppingCartNothing(){
        order_dish_unseleceddish_tv.setVisibility(View.VISIBLE);
        order_dish_shoppingcart_price_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_showdishes_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_payment_iv.setBackgroundColor(getResources().getColor(R.color.gray2));

        refreshShoppingTotalCartNum(0);
    }

    //??????????????????
    private void showShoppingCartNum(int totalNum){
        order_dish_unseleceddish_tv.setVisibility(View.GONE);
        order_dish_shoppingcart_price_tv.setVisibility(View.VISIBLE);
        order_dish_shoppingcart_showdishes_tv.setVisibility(View.VISIBLE);

        refreshShoppingTotalCartNum(totalNum);
        order_dish_shoppingcart_payment_iv.setBackgroundColor(getResources().getColor(R.color.green));

    }

    //??????????????????
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

    //???????????????????????????
    public void refreshShoppingTotalCartNum(int totalNum){
        order_dish_shoppingcart_showdishes_tv.setText(StringUtil.setStringRedColor("???"+totalNum+"?????????","???".length(),("???"+totalNum+"?????????").length() - "?????????".length()));
    }

    //????????????????????????????????????????????????
    private void updateShoppingCart(ShoppingcartBean shoppingcartBean){

        ShoppingcartBean temp = ShoppCartInstance.getInstance().get(shoppingcartBean.getId());
        if (null != temp){//??????
            ShoppCartInstance.getInstance().get(shoppingcartBean.getId()).setNum(shoppingcartBean.getNum());
            if (ShoppCartInstance.getInstance().get(shoppingcartBean.getId()).getNum() <= 0){
                if (shoppingcartBean.getId() == dishInfo.getId()){
                    showAddToShoppingCart();//???????????????????????????
                }
                ShoppCartInstance.getInstance().remove(shoppingcartBean.getId());
            }
        }else{//??????????????????????????????????????????????????????
            WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
            wf.get().setNum(shoppingcartBean.getNum());
            wf.get().setDishname(shoppingcartBean.getDishname());
            wf.get().setId(shoppingcartBean.getId());
            wf.get().setPrice(shoppingcartBean.getPrice());
            ShoppCartInstance.getInstance().add(wf.get());
        }

        update();
    }



    //??????Constants.ShoppingCart?????????????????????
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
     * ????????????
     */
    private void setTitle() {
        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        ImageView subTitleEdtIv = (ImageView) findViewById(R.id.head_center_address_iv);
        if (center_tv != null)
            center_tv.setText("??????");
        if (subTitle != null) {
            subTitle.setText("?????????c???1??????");
        }
        if (subTitleEdtIv != null){
            subTitleEdtIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //????????????
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
                    //TODO???????????????

                }
            });
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    /*********************************************?????????*************************************/
    //?????????????????????
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

    //???????????????
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

    //???????????????
    public void clearCart() {
        ShoppCartInstance.getInstance().clear();
        update();
    }

    //?????????????????????
    private void update(){
        //??????selected
        initSelectedListData();

        //??????or?????????????????????????????????????????????????????????(????????????????????????dishesId(??????dishInfo.getId()????????????????????????????????????????????????dishInfo???null)?????????????????????????????????????????????????????????????????????)
        if (ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)) != null){
            if (ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)).getNum() > 0){
                hideAddToShoppingCart();//???????????????????????????
                updateOrderDishNum(ShoppCartInstance.getInstance().get(Integer.valueOf(dishesId)).getNum());
            }else{
                showAddToShoppingCart();//???????????????????????????
                ShoppCartInstance.getInstance().remove(Integer.valueOf(dishesId));
            }
        }else{
            showAddToShoppingCart();//???????????????????????????
        }

        //???????????????????????????????????????
        if (ShoppCartInstance.getInstance().getCount() <= 0){
            showShoppingCartNothing();//?????????????????????????????????????????????????????????
        }else{//??????????????????????????????
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
        if (type == 0) {//??????
            ShoppingcartBean temp = selectedList.get(shoppingcartBean.getId());
            if (temp != null) {
                if (temp.getNum() < 2) {
                    shoppingcartBean.setNum(0);
                    ShoppCartInstance.getInstance().remove(shoppingcartBean.getId());
 /*                   if (shoppingcartBean.getId() == dishInfo.getId()){
                        showAddToShoppingCart();//???????????????????????????
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

    /*********************************************?????????*************************************/
    /**?????????????????????????????????????????????????????????
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
                System.out.println("????????????");
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

                        // ??????Data???JSONObject??????
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
     * ??????????????????????????????
     *
     * @param type ?????????0?????????????????????1????????????2?????????
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
                System.out.println("????????????");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if(resInfo.getResult() == Constants.RESULT_SUCCESS){
        //                TODO???????????????
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
                                        if (pageIndex != 1){//???????????????????????????????????????????????????????????????????????????????????????
                                            UiHelper.ShowOneToast(getApplicationContext(),"?????????????????????!");
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
        //????????????
        mCb = (ConvenientBanner) findViewById(R.id.id_cb);
        mImageList = dishInfo.getPics();
        initBannerData();
        setBannerListener();

        //????????????
        order_dish_title_tv.setText(dishInfo.getName());
        //	??????
        if (StringUtil.isEmpty(dishInfo.getPromotion())){
            order_dish_discount_tv.setVisibility(View.GONE);
        }else{
            order_dish_discount_tv.setVisibility(View.VISIBLE);
            order_dish_discount_tv.setText(dishInfo.getPromotion());
        }
        //	?????????????????????????????????
//        order_dish_addshoppingcart_num_tv;
        //	??????
        order_dish_price_tv.setText(StringUtil.setShoppingcartTotalMoney(String.valueOf(df.format(dishInfo.getPrice()))));
        //	????????????
        String soldNum = "??????:"+ dishInfo.getSail_count()+"???";
        order_dish_soldnum_tv.setText(StringUtil.setStringGreenColor(soldNum, "??????:".length(),soldNum.length()));
        //	?????????
        order_dish_content_tv.setText(dishInfo.getDescribe());
        //????????????-????????????
        String goodValuate = "??????:"+dishInfo.getGood_comment()+"???";
        order_dish_goodevaluatenum_tv.setText(StringUtil.setStringGreenColor(goodValuate,"??????:".length(),goodValuate.length()));
        //	????????????-????????????
        String badValuate = "??????:"+dishInfo.getBad_comment()+"???";
        order_dish_badevaluatenum_tv.setText(StringUtil.setStringGreenColor(badValuate,"??????:".length(),badValuate.length()));

        //??????
//        order_evaluate_listview;

        List<NutrientCompositionInfo> nClist = new ArrayList<>();
        nClist.clear();
        nClist  = dishInfo.getTag_list();

        setNutrientcomposition(nClist);
    }

    private List<String> mImageList = new ArrayList<>();//???????????????????????????????????????url
    private void initBannerData() {
        //TODO:?????????????????????????????????????????????
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Util.getScreenWidth(), Util.getScreenWidth());
        mCb.setLayoutParams(lp);
        mCb.setPages(new CBViewHolderCreator<ImageViewHolder>(){

            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
    },mImageList);
//                .setPageIndicator(new int[]{R.drawable.ponit_normal,R.drawable.point_select})//??????????????????????????????
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//????????????????????????????????????
//        mCb.setCanLoop(true);
    }

    private void setBannerListener() {
        /*mCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mLinkList && mLinkList.size() > position){
                    //??????????????????????????????

                    UiHelper.ShowOneToast(getApplicationContext(),"???????????????" + position);
                }
            }
        });*/
    }

    /**
     * ??????????????????????????????
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
                view.setLayoutParams(lp);//??????????????????
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
     * ??????????????????
     * <p>
     * ???????????????3????????????????????????????????????????????????????????????????????????????????????????????????11~20????????????????????????????????????????????????????????????????????????
     * ????????????????????????????????????????????????????????????????????????????????????1~10????????????
     *
     * @param view
     */
    public void getmoreevaluate(View view) {

        //??????????????????????????????
        if (radioButtonFlag.equals("good")){//??????????????????????????????????????????0?????????????????????1????????????2?????????
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
     * ?????????????????????????????????
     */
    private void pay() {
        //???????????????
        StringBuffer cart = new StringBuffer();
        HashMap<Integer,ShoppingcartBean> hashMap = ShoppCartInstance.getInstance().getAll();
        for (Map.Entry<Integer,ShoppingcartBean> entry:hashMap.entrySet()) {
            int id = entry.getKey();
            int num = entry.getValue().getNum();
            cart.append(id +":" + num +",");
        }

        if (StringUtil.isEmpty(cart.toString())){
            //TODO???????????????????????????????????????id?????????????????????????????????????????????
            CrashHandler.getInstance().handlerError("????????????,????????????????????????????????????!");
            UiHelper.ShowOneToast(getApplicationContext(),"????????????,???????????????!");
            return;
        }
        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        String url =  UrlManager.URL_PAY;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("cart",cart.toString()).build();//"10???2???10??? 2???"??????dishesid:count??????
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
                                //?????????????????????
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

        //??????????????????????????????
        if (Constants.IS_LOCALDATA) {
            getDishInfoByLocal();
        } else {
            getDishInfo();
        }
//        getAddress();

        //??????????????????
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
     * ????????????????????????
     * @return
     */
    private List<EvaluateInfo> getEvaluateInfosByLocal() {

        if(radioButtonFlag.equals("good")){
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wr = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wr.get().setMobile("151****5461");
                wr.get().setContent("?????????????????????????????????");
                wr.get().setGrade(8);
                goodEvaluateInfos.add(wr.get());
            }

            return  goodEvaluateInfos;
        }else if(radioButtonFlag.equals("bad")){
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wf.get().setMobile("138****9652");
                wf.get().setContent("???????????????????????????????????????????????????????????????????????????????????????????????????");
                wf.get().setGrade(8);
                badEvaluateInfos.add(wf.get());
            }

            return  badEvaluateInfos;
        }else {
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> wf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                wf.get().setMobile("151****5461");
                wf.get().setContent("?????????????????????????????????");
                wf.get().setGrade(8);
                allEvaluateInfos.add(wf.get());

            }
            for (int i = 0; i < 3; i++) {
                WeakReference<EvaluateInfo> sf = new WeakReference<EvaluateInfo>(new EvaluateInfo());
                sf.get().setMobile("138****9652");
                sf.get().setContent("???????????????????????????????????????????????????????????????????????????????????????????????????");
                sf.get().setGrade(8);
                allEvaluateInfos.add(sf.get());
            }

            return allEvaluateInfos;
        }
    }

    //??????????????????
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
     * ?????????????????????????????????
     */
    private void getDishInfoByLocal() {
        WeakReference<DishInfo> wf = new WeakReference<DishInfo>(new DishInfo());
        wf.get().setId(20);
        wf.get().setName("????????????");
        List<String> pics = new ArrayList<>();
        pics.add("http://img3.imgtn.bdimg.com/it/u=940174915,1631657552&fm=11&gp=0.jpg");
        pics.add("http://rescdn.qqmail.com/dyimg/20141106/7F393E5C9D9D.jpg");
        pics.add("http://img.mp.itc.cn/upload/20160820/7a1471b0cf9246be901c1daa7d4ec98c_th.jpg");
        wf.get().setPics(pics);
        wf.get().setDescribe("?????????????????????????????????????????????");
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
        String[] names = {"??????","??????","?????????","??????","?????????"};
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
//            mCb.startTurning(2000);//????????????????????????????????????
//        }

        if (dishesId != null) {
            System.out.println(dishesId);
        } else {
            dishesId = "49";
            CrashHandler.getInstance().handlerError("?????????????????????dishesId??????");
        }
        System.out.println("Order----------------------------------------------------"+getTaskId());
    }

    @Override
    protected void onPause() {
//        if (null != mCb){
//            mCb.stopTurning();//????????????
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
            //?????????????????????
            case R.id.order_dish_shoppingcart_price_tv:
            case R.id.order_dish_shoppingcart_showdishes_tv:
                showBottomSheet();
                break;

            //???????????????
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

            //????????????
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

            //????????????
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

            //????????????????????????
            case R.id.order_dish_shoppingcart_payment_iv:
                if (selectedList.size() <= 0){
                    UiHelper.ShowOneToast(getApplicationContext(),"???????????????");
                }else{
                    //?????????????????????
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
