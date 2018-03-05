package com.tastebychance.sonchance.homepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jaeger.library.StatusBarUtil;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.homepage.adapter.HomeListViewAdapter;
import com.tastebychance.sonchance.homepage.banner.ImageViewHolder;
import com.tastebychance.sonchance.homepage.bean.BannerInfo;
import com.tastebychance.sonchance.homepage.bean.HomePageListRes;
import com.tastebychance.sonchance.homepage.cateringservice.CateringServiceActivity;
import com.tastebychance.sonchance.homepage.healthylife.HealthyLifeActivity;
import com.tastebychance.sonchance.homepage.homeshoppingcart.adapter.ShoppingcartAdapter;
import com.tastebychance.sonchance.homepage.homeshoppingcart.bean.ShoppingCartBean;
import com.tastebychance.sonchance.homepage.locate.PlaceChooseActivity;
import com.tastebychance.sonchance.homepage.myorder.MyOrderActivity;
import com.tastebychance.sonchance.homepage.opinionfeedback.OpinionFeedbackActivity;
import com.tastebychance.sonchance.homepage.order.OrderActivity;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.homepage.orderform.OrderFormActivity;
import com.tastebychance.sonchance.homepage.orderform.bean.OrderInfo;
import com.tastebychance.sonchance.homepage.search.DishesSearchActivity;
import com.tastebychance.sonchance.personal.locate.util.ToastUtil;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.RealmHelper;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;
import com.tastebychance.sonchance.view.ListViewForScrollView;
import com.tastebychance.sonchance.view.MyListView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 首页
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class HomeActivity extends MyBaseActivity implements/* LocationSource,*/AMapLocationListener/*implements PullRefreshListView.IXListViewListener*/ {
    //购物车数据
    private SparseArray<ShoppingcartBean> selectedList;
    //首页列表数据
    private SparseArray<ShoppingCartBean> mDatas;

    private Handler mHanlder;

    //    private PullRefreshListView homelistView;
    private HomeListViewAdapter homeListViewAdapter;

    private ScrollView home_scrollview;

    private ListViewForScrollView homelist;

    //购物车
    private LinearLayout home_shoppingcart_ll;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
//    private LinearLayout ll_shopcar;
    private TextView home_shoppingcart_totle_money_tv;//购物车：总共多少钱
    private TextView home_shoppingcart_dishnum_tv;//购物车：总共几份餐
    private TextView home_shoppingcart_payment_tv;//结算
    private static DecimalFormat df;
    private Double totleMoney = 0.00;
    private ShoppingcartAdapter productAdapter;//底部购物车的adapter

    private IntentFilter intentFilter;

    private RealmHelper mRealmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.home);
//        StatusBarUtil.setTransparent(MainActivity.this);
        StatusBarUtil.setColor(this, Color.RED);

        bindViews();

//        initBanner();
        setBanner();

        mHanlder = new Handler(getMainLooper());
        initView();
        setTitle();

        addListener();

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.LOGINSUCCESS_ACTION);
        /*intentFilter.addAction(Constants.PAY_SUCCESS_ACTION);
        intentFilter.addAction(Constants.PAY_ERROR_ACTION);
        intentFilter.addAction(Constants.PAY_CANCEL_ACTION);*/

        mRealmHelper = new RealmHelper(this);
    }

    private void bindViews() {
        home_scrollview = (ScrollView) findViewById(R.id.home_scrollview);
        homelist = (ListViewForScrollView) findViewById(R.id.homelist);
        home_shoppingcart_ll = (LinearLayout) findViewById(R.id.home_shoppingcart_ll);
        home_shoppingcart_dishnum_tv = (TextView) findViewById(R.id.home_shoppingcart_dishnum_tv);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);
//        ll_shopcar = (LinearLayout) findViewById(R.id.ll_shopcar);
        home_shoppingcart_totle_money_tv = (TextView) findViewById(R.id.home_shoppingcart_totle_money_tv);
        home_shoppingcart_dishnum_tv = (TextView) findViewById(R.id.home_shoppingcart_dishnum_tv);
        home_shoppingcart_payment_tv = (TextView) findViewById(R.id.home_shoppingcart_payment_tv);
    }

    public void initView() {
        selectedList = new SparseArray<>();
        mDatas = new SparseArray<>();
        df = new DecimalFormat("0.00");

        updateTotalPrice();


//        homelistView = (PullRefreshListView) findViewById(R.id.homelist);
//        homelistView.setPullLoadEnable(true);
//        homelistView.setXListViewListener(this);

//        homeListView = (ListView) findViewById(R.id.homelist);
    }

    //初始化菜单内容
    private List<HomePageListRes> serverDishDatas;

    private void initData() {
        serverDishDatas = new ArrayList<HomePageListRes>();

        //TODO：从本地数据库中取出数据设置到页面
/*        Realm mRealm = Realm.getDefaultInstance();
        serverDishDatas.addAll(mRealm.where(HomePageListRes.class).findAll());
        for (HomePageListRes homePageListRes : serverDishDatas) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setNum(0);
            shoppingCartBean.setHomePageListRes(homePageListRes);
            mDatas.put(homePageListRes.getId(), shoppingCartBean);
        }
        updateHomeDish(mDatas);*/

        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        String url = UrlManager.URL_HOME_DISHES;
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

                    if (null == resInfo) {
                        return;
                    }

                    if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        //服务器请求到的数据
                        serverDishDatas = resInfo.getDataList(HomePageListRes.class);

                        //TODO：清空本地数据库
/*                        Realm mRealm = Realm.getDefaultInstance();
                        final RealmResults<HomePageListRes> dishes = mRealm.where(HomePageListRes.class).findAll();
                        mRealm.executeTransaction(new Realm.Transaction(){

                            @Override
                            public void execute(Realm realm) {
                                dishes.deleteAllFromRealm();//删除所有数据
                            }
                        });*/

                        Log.i(Constants.TAG,"serverDishDatas.size()-----------------------------------------"+serverDishDatas.size()+"");
                        //传入HomeListViewAdapter的数据
                        for (HomePageListRes homePageListRes : serverDishDatas) {
                            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                            shoppingCartBean.setNum(0);
                            shoppingCartBean.setHomePageListRes(homePageListRes);
                            mDatas.put(homePageListRes.getId(), shoppingCartBean);

                            //TODO：添加到本地数据库
//                            mRealmHelper.addDish(homePageListRes);
                        }

                        setHomeListViewSelectedData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateHomeDish(mDatas);
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
     * 更新首页菜品
     */
    private void updateHomeDish(SparseArray<ShoppingCartBean> mDatas){
        homeListViewAdapter = new HomeListViewAdapter(HomeActivity.this, mDatas);
        homelist.setAdapter(homeListViewAdapter);
    }

    private void addListener() {
        //展示购物车
        home_shoppingcart_totle_money_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        //展示购物车
        home_shoppingcart_dishnum_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        //去结算
        home_shoppingcart_payment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SystemUtil.getInstance().getIsLogin()){
                    SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this, Constants.TO_ORDERFORM);
                }else{
                    pay();
                }
            }
        });
    }


    //跳转到点餐界面
    public void intentToOrder(int dishesId) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("dishes_id", dishesId + "");
        startActivity(intent);
    }

    //查看购物车布局
    private void showBottomSheet() {

        bottomSheet = createBottomSheetView();
        if (null == bottomSheetLayout) {
            bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);
        }
//        bottomSheetLayout.setVisibility(View.VISIBLE);

        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
//            bottomSheetLayout.setVisibility(View.GONE);
        } else {
            if (selectedList.size() != 0 && null != bottomSheetLayout && null != bottomSheet) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }


    //创建购物车view
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, bottomSheetLayout, false);
        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
        ImageView clear = (ImageView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });

        updateSelectedListData();

        productAdapter = new ShoppingcartAdapter(HomeActivity.this, homeListViewAdapter, selectedList);
        lv_product.setAdapter(productAdapter);
        return view;
    }

    //设置选中的菜（购物车数据）
    public void updateSelectedListData() {
        //给购物车添加内容

        selectedList.clear();
        if (ShoppCartInstance.getInstance().getCount() > 0) {
            Set<Map.Entry<Integer, ShoppingcartBean>> set = ShoppCartInstance.getInstance().getAll().entrySet();
            Iterator<Map.Entry<Integer, ShoppingcartBean>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, ShoppingcartBean> entry = iterator.next();

                WeakReference<ShoppingcartBean> wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
                wf.get().setNum(entry.getValue().getNum());
                wf.get().setDishname(entry.getValue().getDishname());
                wf.get().setId(entry.getValue().getId());
                wf.get().setPrice(entry.getValue().getPrice());
                selectedList.put(wf.get().getId(),wf.get());
            }
        }
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
//        Constants.shoppingCartHashMap.clear();

        ShoppCartInstance.getInstance().clear();
        updateSelectedListData();
//        setHomeListViewSelectedData();
        clearHomeListViewSelectedData();
        homeListViewAdapter.notifyDataSetChanged();

        update(true);
    }


    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
//        GoodsBean temp = selectedList.get(id);
//        if (temp == null) {
//            return 0;
//        }
//        return temp.getNum();
        return 0;
    }


    public void handlerCarNum(int type, ShoppingcartBean shoppingcartBean, boolean refreshGoodList) {
        if (type == 0) {
            ShoppingcartBean temp = selectedList.get(shoppingcartBean.getId());
            if (temp != null) {
                if (temp.getNum() < 2) {
                    shoppingcartBean.setNum(0);
                    selectedList.remove(shoppingcartBean.getId());

//                    Constants.shoppingCartHashMap.remove(shoppingcartBean.getId());
                    ShoppCartInstance.getInstance().remove(shoppingcartBean.getId());
                } else {
                    int i = shoppingcartBean.getNum();
                    shoppingcartBean.setNum(--i);

                    //TODO
                    updateConstansShoppingCartData(shoppingcartBean.getId(),shoppingcartBean.getNum());
                }

            }


        } else if (type == 1) {
            ShoppingcartBean temp = selectedList.get(shoppingcartBean.getId());
            if (temp == null) {
                shoppingcartBean.setNum(1);
                selectedList.append(shoppingcartBean.getId(), shoppingcartBean);

                //TODO
                updateConstansShoppingCartData(shoppingcartBean.getId(),shoppingcartBean.getNum());
            } else {
                int i = shoppingcartBean.getNum();
                shoppingcartBean.setNum(++i);

                //TODO
                updateConstansShoppingCartData(shoppingcartBean.getId(),shoppingcartBean.getNum());
            }

        }

        updateSelectedListData();
        updateHomeListViewSelectedData(shoppingcartBean.getId(), shoppingcartBean.getNum());

        update(refreshGoodList);

    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {

        if (null != selectedList && selectedList.size() > 0) {
            showOrHideShoppingcart(true);
        } else {
            showOrHideShoppingcart(false);
        }

        updateTotalPrice();

        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }

        if (homeListViewAdapter != null) {
            homeListViewAdapter.notifyDataSetChanged();
        }

        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    //设置显示或隐藏底部购物车
    private void showOrHideShoppingcart(boolean toShow) {
        if (toShow) {
            home_shoppingcart_ll.setVisibility(View.VISIBLE);
            home_shoppingcart_ll.setClickable(true);
        } else {
            home_shoppingcart_ll.setVisibility(View.GONE);
            home_shoppingcart_ll.setClickable(false);
        }
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


        home_shoppingcart_totle_money_tv.setText(StringUtil.setShoppingcartTotalMoney(String.valueOf(df.format(totleMoney))));
        totleMoney = 0.00;
        if (count < 1) {
            home_shoppingcart_dishnum_tv.setVisibility(View.GONE);
        } else {
            home_shoppingcart_dishnum_tv.setVisibility(View.VISIBLE);
        }

        home_shoppingcart_dishnum_tv.setText(StringUtil.setStringRedColor("共"+count+"份套餐","共".length(),("共"+count+"份套餐").length() - "份套餐".length()));
    }

    /**
     * 更新Constants.ShoppingCart购物车中的数据
     */
    private void updateConstansShoppingCartData(int id , int num){
        WeakReference<ShoppingCartBean> wf2 = new WeakReference<ShoppingCartBean>(new ShoppingCartBean());

        WeakReference<ShoppingcartBean> wf4 = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());

        for (int i = 0; i < mDatas.size(); i++) {
            if (id == mDatas.valueAt(i).getHomePageListRes().getId()) {
                /*WeakReference<HomePageListRes> wf3 = new WeakReference<HomePageListRes>(new HomePageListRes());
                wf3.get().setId(mDatas.valueAt(i).getHomePageListRes().getId());
                wf3.get().setName(mDatas.valueAt(i).getHomePageListRes().getName());
                wf3.get().setComment_count(mDatas.valueAt(i).getHomePageListRes().getComment_count());
                wf3.get().setSail_count(mDatas.valueAt(i).getHomePageListRes().getSail_count());
                wf3.get().setPrice(mDatas.valueAt(i).getHomePageListRes().getPrice());
                wf3.get().setGood_comments(mDatas.valueAt(i).getHomePageListRes().getGood_comments());
                wf3.get().setBad_comments(mDatas.valueAt(i).getHomePageListRes().getBad_comments());
                wf2.get().setNum(num);
                wf2.get().setHomePageListRes(wf3.get());
                Constants.shoppingCartHashMap.put(id, wf2.get());*/


                wf4.get().setId(id);
                wf4.get().setNum(num);
                wf4.get().setPrice(mDatas.valueAt(i).getHomePageListRes().getPrice());
                wf4.get().setDishname(mDatas.valueAt(i).getHomePageListRes().getName());
                ShoppCartInstance.getInstance().add(wf4.get());

            }
        }


    }

    private void clearHomeListViewSelectedData() {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.valueAt(i).setNum(0);
        }
    }

    //设置首页列表在购物车中的数量
    private void setHomeListViewSelectedData() {
        /*Set<Map.Entry<Integer, ShoppingCartBean>> set = Constants.shoppingCartHashMap.entrySet();
        Iterator<Map.Entry<Integer, ShoppingCartBean>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ShoppingCartBean> entry = iterator.next();

            //如果使用put，那么不存在的key会添加到mDatas中
//                    ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//                    shoppingCartBean.setNum(Constants.shoppingCartHashMap.get(entry.getKey()).getNum());
//                    shoppingCartBean.setHomePageListRes(Constants.shoppingCartHashMap.get(entry.getKey()).getHomePageListRes());
//                    mDatas.put(Constants.shoppingCartHashMap.get(entry.getKey()).getHomePageListRes().getId(),shoppingCartBean);

            for (int i = 0; i < mDatas.size(); i++) {
                if (Constants.shoppingCartHashMap.get(entry.getKey()).getHomePageListRes().getId() == mDatas.valueAt(i).getHomePageListRes().getId()) {
                    mDatas.valueAt(i).setNum(entry.getValue().getNum());
                }
            }
        }*/


        for (int i = 0; i < mDatas.size(); i++) {
            int id = mDatas.valueAt(i).getHomePageListRes().getId();
            if (ShoppCartInstance.getInstance().get(id) == null) {
                continue;
            }
            mDatas.valueAt(i).setNum(ShoppCartInstance.getInstance().get(id).getNum());
        }
    }

    //移除购物车中的元素后更新首页列表选择的数量
    private void updateHomeListViewSelectedData(int id, int num) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (id == mDatas.valueAt(i).getHomePageListRes().getId()) {
                mDatas.valueAt(i).setNum(num);
            }
        }

        if(null != homeListViewAdapter){
            homeListViewAdapter.notifyDataSetChanged();
        }
    }


    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    //顶部广告和上拉加载、下拉刷新
    private int pageIndex = 0;
    private final int WHAT_VIEWPAGER = 0;
    private final int WHAT_REFRESHL = 1;
    private final int WHAT_LOADMORE = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 设置当前页面
            switch (msg.what) {
                case WHAT_VIEWPAGER:
                    break;
                /*case WHAT_REFRESHL:
                    pageIndex = 0;
//				mDatas.clear();
                    homeListViewAdapter.notifyDataSetChanged();
//				initData();
                    homeListViewAdapter.notifyDataSetChanged();
                    onLoad();
                    break;
                case WHAT_LOADMORE:
//				initData();
                    homeListViewAdapter.notifyDataSetChanged();
                    onLoad();
                    break;*/
                default:
                    break;
            }
        }
    };

/*    private void onLoad() {
        homelistView.stopRefresh();
        homelistView.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        homelistView.setRefreshTime(date);
    }*/


    /**
     * 配餐服务
     * @param view
     */
    public void CateringService(View view) {
//        UiHelper.ShowOneToast(getApplicationContext(),"暂未开放");
        if (!SystemUtil.getInstance().getIsLogin()){
            SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this,Constants.TO_CATERINGSERVICE);
        }else{
            toCateringServiceActivity();
        }
    }

    private void toCateringServiceActivity(){

        Intent intent = new Intent(HomeActivity.this, CateringServiceActivity.class);
        startActivity(intent);
    }

    /**
     * 意见反馈
     * @param view
     */
    public void OpinionFeedback(View view) {
//        Toast.makeText(this, "意见反馈", Toast.LENGTH_SHORT).show();
        if (!SystemUtil.getInstance().getIsLogin()){
            SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this,Constants.TO_OPINIONFEEDBACK);
        }else{
            toOpinionFeedback();
        }
    }

    private void toOpinionFeedback(){
        Intent intent = new Intent(HomeActivity.this, OpinionFeedbackActivity.class);
        startActivity(intent);
    }

    /**
     * 健康生活
     * @param view
     */
    public void HealthyLife(View view) {
//        UiHelper.ShowOneToast(getApplicationContext(),"暂未开放");

        if (!SystemUtil.getInstance().getIsLogin()){
            SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this,Constants.TO_HEALTHYLIFE);
        }else{
            toHealthyLife();
        }
    }

    private void toHealthyLife(){

        Intent intent = new Intent(HomeActivity.this, HealthyLifeActivity.class);
        startActivity(intent);
    }

    /**
     * 我的订单
     * @param view
     */
    public void MyOrder(View view) {
//        Toast.makeText(this, "我的订单", Toast.LENGTH_SHORT).show();
        if (!SystemUtil.getInstance().getIsLogin()){
            SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this,Constants.TO_MYORDER);
        }else{
            toMyOrder();
        }
    }

    private void toMyOrder(){
        Intent intent = new Intent(HomeActivity.this, MyOrderActivity.class);
        startActivity(intent);
    }

    /*-------------------------------------------设置标题------------------------------------------*/
    private TextView home_location;
    private EditText home_search_edt;
    private ImageView home_speechsearch;

    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);

        home_location = (TextView) findViewById(R.id.home_location);
        home_search_edt = (EditText) findViewById(R.id.home_search_edt);
        home_speechsearch = (ImageView) findViewById(R.id.home_speechsearch);

        home_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PlaceChooseActivity.class);
                intent.putExtra("defaultPlace", StringUtil.isNotEmpty(home_location.getText().toString()) ? home_location.getText().toString() :"福州");
                startActivity(intent);
            }
        });
        home_search_edt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!SystemUtil.getInstance().getIsLogin()){
                    SystemUtil.getInstance().intentToLoginActivity(HomeActivity.this,Constants.TO_DISHSEARCH);
                }else{
                    toDishesSearch();
                }
            }
        });
        home_speechsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    private void toDishesSearch() {
        startActivity(new Intent(HomeActivity.this, DishesSearchActivity.class));
    }
    /*-------------------------------------------设置标题------------------------------------------*/

    /*-------------------------------------------顶部轮播广告------------------------------------------*/
    /**
     * 获取顶部广告图片
     */
    private void getDiscount(){
        //采用okhttp3来进行网络请求
        String url = UrlManager.URL_HOME_BANNER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build();
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
                    final String str = response.body().string();
                    Log.i(Constants.TAG, str);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                            if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                                List<BannerInfo> bannerInfos = resInfo.getDataList(BannerInfo.class);
                                for (BannerInfo bannerInfo : bannerInfos) {
                                    mImageList.add(bannerInfo.getImage());
                                    mLinkList.add(bannerInfo.getLink());
                                }
                                if (null != mImageList && mImageList.size() > 0) {
                                    initBannerData();
                                }
                            } else {
                                Message msg = new Message();
                                msg.what = ERROR_WHAT;
                                msg.obj = resInfo.getError_message();
                                myHandler.sendMessage(msg);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private List<String> mImageList = new ArrayList<>();//存放服务器获取回来的图片的url
//    private int[] mImages = {R.drawable.item01,R.drawable.item02,R.drawable.item03};
    private List<String> mLinkList = new ArrayList<>();//存放服务器获取回来的图片对应的跳转链接

    private ConvenientBanner mCb;
    private void setBanner(){
        mCb = (ConvenientBanner) findViewById(R.id.id_cb);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.width = UiHelper.getScreenWidth(HomeActivity.this);
        lp.height = lp.width * 9 / 16;
        mCb.setLayoutParams(lp);

        getDiscount();

//        initBannerData();

        setBannerListener();
    }

    private void initBannerData() {
        //initData
       /* for (int i = 0; i < mImages.length ; i ++){
            mImageList.add(mImages[i]);
        }*/

        mCb.setPages(new CBViewHolderCreator<ImageViewHolder>(){

            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        },mImageList)
                .setPageIndicator(new int[]{R.drawable.ponit_normal,R.drawable.point_select})//设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器位置为水平居中
        //      mCb.setCanLoop(true);

    }

    private void setBannerListener() {
        mCb.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (null != mLinkList && mLinkList.size() > position){
                    //TODO:点击图片进行链接跳转

//                    UiHelper.ShowOneToast(getApplicationContext(),"点击了条目" + position);
                }
            }
        });
    }
    /*-------------------------------------------顶部轮播广告------------------------------------------*/

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constants.LOGINSUCCESS_ACTION)){
                String toActivity = null;
                if (null != intent){
                    toActivity = intent.getStringExtra("toActivity");
                }
                switch (toActivity){
                    case Constants.TO_ORDERFORM:
                        pay();
                        break;
                    case Constants.TO_CATERINGSERVICE:

                        toCateringServiceActivity();
                        break;
                    case Constants.TO_OPINIONFEEDBACK:
                        toOpinionFeedback();
                        break;
                    case Constants.TO_HEALTHYLIFE:
                        toHealthyLife();
                        break;
                    case Constants.TO_MYORDER:
                        toMyOrder();
                        break;
                    case Constants.TO_DISHSEARCH:
                        toDishesSearch();
                        break;
                }
            }
        }
    };

    //提交购物车信息到服务器
    private void pay() {
        //购物车信息
        StringBuffer cart = new StringBuffer();
        HashMap<Integer,ShoppingcartBean> hashMap = ShoppCartInstance.getInstance().getAll();
        for (Map.Entry<Integer,ShoppingcartBean> entry:hashMap.entrySet()) {
            int id = entry.getKey();
            int num = entry.getValue().getNum();
            cart.append(id +":" + num +",");
//            cart.append(58 +":" + num +",");
        }

        if (StringUtil.isEmpty(cart.toString())){
            //TODO：封装更多的信息，比如用户id之类的，方便更快定位到错误原因
            CrashHandler.getInstance().handlerError("程序异常,提交的时候购物车不能为空!");
            if (Constants.IS_DEVELOPING){
                UiHelper.ShowOneToast(getApplicationContext(),"提交失败,请联系产商!");
            }
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
//                .add("cart","58:4").build();//"10：2，10： 2，"表示dishesid:count数量
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
                        msg.obj = UPDATE_SUCCESS;
                        myHandler.sendMessage(msg);

                        final OrderInfo orderInfo = JSONObject.parseObject(resInfo.getData().toString(),OrderInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //跳转到配送界面
                                Intent intent = new Intent(HomeActivity.this,OrderFormActivity.class);
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

    //排序
   /* private void sortedSparseArray(SparseArray<ShoppingCartBean> datas){
        for (int i = 0,size = datas.size();i < size ; i ++){
            datas.valueAt(i).ge
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if (null != myBroadcastReceiver){
            registerReceiver(myBroadcastReceiver,intentFilter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isFromMyOrder = SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(),Constants.TEMP,"isFromMyOrder");
        boolean toMyOrder = SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(),Constants.TEMP,"toMyOrder");
        if (isFromMyOrder || toMyOrder){
            startActivity(new Intent(HomeActivity.this,MyOrderActivity.class));
        }

        System.out.println("Home----------------------------------------------------"+getTaskId());
        initData();

        initSelectedData();

        if (null != mCb){
            mCb.startTurning(2000);//设置开始轮播以及轮播时间
        }

        if (null != home_location && StringUtil.isNotEmpty(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"chosedCity"))){
            home_location.setText(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"chosedCity"));
        }else{
            try {
                String locateCityName = SystemUtil.getInstance().getLocationName();
                System.out.println("locateCityName = " + locateCityName);
                if (StringUtil.isNotEmpty(locateCityName)){
                    if (null != home_location){
                        home_location.setText(locateCityName);
                    }
                    SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"city",locateCityName);
                }else{
                    //采用高德定位
                    location();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (MyApplication.isDevelopmentStage){
                    UiHelper.ShowOneToast(getApplicationContext(),"获取定位失败");
                }
            }
        }
    }

    /**
     * 初始化选中的和首页购物车的内容
     */
    private void initSelectedData() {
        clearHomeListViewSelectedData();
        if (ShoppCartInstance.getInstance().getCount() > 0){
            //初始化列表选中的数据
            for (int i = 0; i < mDatas.size(); i++) {
                int id = mDatas.valueAt(i).getHomePageListRes().getId();
                if (ShoppCartInstance.getInstance().get(id) == null) {
                    continue;
                }
                mDatas.valueAt(i).setNum(ShoppCartInstance.getInstance().get(id).getNum());
            }
        }
        updateSelectedListData();
        update(true);
    }

    //Amap高德定位
    private void location() {
        //初始化定位
        mlocationClient = new AMapLocationClient((getApplicationContext()));
        //设置定位回调监听
        mlocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔 单位毫秒
        mLocationOption.setInterval(30 * 60 * 1000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();

    }

    @Override
    protected void onPause() {
        if (null != mCb){
            mCb.stopTurning();//停止轮播
        }
        super.onPause();

        //停止定位
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBroadcastReceiver) {
            unregisterReceiver(myBroadcastReceiver);
        }

        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }


    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                String city = amapLocation.getCity();
                System.out.println("高德定位到city = " + city);

                if (null != home_location && StringUtil.isNotEmpty(city)){
                    home_location.setText(city);
                    SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"city",city);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            if (System.currentTimeMillis() - firstTime > 2000){
                Toast.makeText(HomeActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }else{
                MyApplication.getAppContext().clearStatck();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

   /* @Override
    public void onListViewRefresh() {
        handler.sendEmptyMessage(WHAT_REFRESHL);
    }

    @Override
    public void onListViewLoadMore() {
        handler.sendEmptyMessage(WHAT_LOADMORE);
    }*/
}
