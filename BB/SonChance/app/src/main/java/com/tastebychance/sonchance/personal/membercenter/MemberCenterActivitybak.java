package com.tastebychance.sonchance.personal.membercenter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.personal.membercenter.bean.MemberCenterInfo;
import com.tastebychance.sonchance.personal.membercenter.bean.MemberCenterIntegralDetailInfo;
import com.tastebychance.sonchance.personal.membercenter.bean.MemberCenterUserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.TimeOrDateUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.pulltorefresh.MyListener;
import com.tastebychance.sonchance.view.pulltorefresh.PullToRefreshLayout;

import java.io.IOException;
import java.lang.ref.WeakReference;
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
 * 类描述：MemberCenterActivity 我的-会员中心 
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/14 9:39
 * 修改人：
 * 修改时间：2017/9/14 9:39
 * 修改备注：
 * @version 1.0
 */
public class MemberCenterActivitybak extends MyBaseActivity {

/*    private PullToRefreshScrollView mPullScrollView;
    private ScrollView mScrollView;
    private View view;//自定义布局中的layout*/

    private com.tastebychance.sonchance.view.RoundImageView person_item_headportrait_iv;
    private TextView person_item_grade_tv;
    private TextView person_membercenter_username_tv;
    private com.tastebychance.sonchance.view.MyListView person_membercneter_mlv;

    private MemberCenterInfo memberCenterInfo;//获取到的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_member_center);

        bindViews();

        PullToRefreshLayout pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
    }

    private void bindViews() {
        /*mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        mPullScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (Constants.IS_LOCALDATA){
                    constructByLocal(pageIndex);
                }else{
                    initObject();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        mScrollView = mPullScrollView.getRefreshableView();
        view = LayoutInflater.from(this).inflate(R.layout.person_member_center_content,null);
        mScrollView.addView(view);*/

/*
        person_item_headportrait_iv = (com.tastebychance.sonchance.view.RoundImageView) view.findViewById(R.id.person_item_headportrait_iv);
        person_item_grade_tv = (TextView) view.findViewById(R.id.person_item_grade_tv);
        person_membercenter_username_tv = (TextView) view.findViewById(R.id.person_membercenter_username_tv);
        person_membercneter_mlv = (com.tastebychance.sonchance.view.MyListView) view.findViewById(R.id.person_membercneter_mlv);
*/

        person_item_headportrait_iv = (com.tastebychance.sonchance.view.RoundImageView) findViewById(R.id.person_item_headportrait_iv);
        person_item_grade_tv = (TextView) findViewById(R.id.person_item_grade_tv);
        person_membercenter_username_tv = (TextView) findViewById(R.id.person_membercenter_username_tv);
        person_membercneter_mlv = (com.tastebychance.sonchance.view.MyListView) findViewById(R.id.person_membercneter_mlv);

        /*setLastUpdateTime();*/
    }

   /* private void setLastUpdateTime() {
        String text = TimeOrDateUtil.long2FormatTime(System.currentTimeMillis(),TimeOrDateUtil.MINUTE_TIME);
        mPullScrollView.setLastUpdatedLabel(text);
    }*/

    /**
     * 请求数据并填充到控件上
     */
    private int pageIndex = 1;//请求积分明细的页码，默认为1
    private List<MemberCenterIntegralDetailInfo> lists;
    private void initObject(){
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url =   UrlManager.URL_PERSON_UCENTER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).add("p", pageIndex + "").build();
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
                        msg.obj = GETDATA_SUCCESS;
                        myHandler.sendMessage(msg);

                        memberCenterInfo = JSONObject.parseObject(resInfo.getData().toString(), MemberCenterInfo.class);
                        //                    goodsReceiptInfos = resInfo.getDataList(GoodsReceiptInfo.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*mPullScrollView.onPullDownRefreshComplete();
                                setLastUpdateTime();*/

                                MemberCenterUserInfo memberCenterUserInfo = memberCenterInfo.getInfo();
                                initUser(memberCenterUserInfo);
                                if (null == memberCenterInfo.getLists() || memberCenterInfo.getLists().size() <= 0) {
                                    toastNoMore();
                                    return;
                                }

                                if (null == lists || lists.size() <= 0) {
                                    lists = memberCenterInfo.getLists();
                                } else {
                                    lists.addAll(memberCenterInfo.getLists());
                                }
                                initIntegralDetail(lists);
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
     * 本地构造数据
     * @param pageIndex
     */
    private void constructByLocal(int pageIndex) {
        /*mPullScrollView.onPullDownRefreshComplete();
        setLastUpdateTime();*/

        MemberCenterInfo memberCenterInfo = null;//获取到的数据
        WeakReference<MemberCenterInfo> wf = new WeakReference<MemberCenterInfo>(new MemberCenterInfo());
        wf.get().setAll_page(2);
        wf.get().setCur_page(pageIndex);
        WeakReference<MemberCenterUserInfo> wf2 = new WeakReference<MemberCenterUserInfo>(new MemberCenterUserInfo());
        wf2.get().setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=270928896,317050170&fm=27&gp=0.jpg");
        wf2.get().setGrade(0);
        wf2.get().setScore("0");
        wf2.get().setUser_name("迷之微笑");
        wf.get().setInfo(wf2.get());
        List<MemberCenterIntegralDetailInfo> list = new ArrayList<>();
        if (pageIndex == 1) {
            for (int i = 0; i < 4; i++) {
                WeakReference<MemberCenterIntegralDetailInfo> wf3 = new WeakReference<MemberCenterIntegralDetailInfo>(new MemberCenterIntegralDetailInfo());
                wf3.get().setScore(28);
                wf3.get().setType(1);
                wf3.get().setId(51 + i);
                wf3.get().setMsg("对菜品评价，获得28积分");
                wf3.get().setCreate_time(new Date());
                wf3.get().setUid(71);
                list.add(wf3.get());
            }
            wf.get().setLists(list);
            memberCenterInfo = wf.get();
        } else if (pageIndex == 2) {
            for (int i = 0; i < 3; i++) {
                WeakReference<MemberCenterIntegralDetailInfo> wf3 = new WeakReference<MemberCenterIntegralDetailInfo>(new MemberCenterIntegralDetailInfo());
                wf3.get().setScore(280);
                wf3.get().setType(0);
                wf3.get().setId(51 + i);
                wf3.get().setMsg("对菜品评价，失去280积分");
                wf3.get().setCreate_time(new Date());
                wf3.get().setUid(71);
                list.add(wf3.get());
            }
            wf.get().setLists(list);
            memberCenterInfo = wf.get();
        }

        if (memberCenterInfo == null || memberCenterInfo.getLists() == null || memberCenterInfo.getLists().size() <= 0) {
            toastNoMore();
            return;
        }

        MemberCenterUserInfo memberCenterUserInfo = memberCenterInfo.getInfo();
        initUser(memberCenterUserInfo);

        if (null == lists || lists.size() <= 0) {
            lists = memberCenterInfo.getLists();
        } else {
            if (pageIndex == 1){
                lists.clear();
            }
            lists.addAll(memberCenterInfo.getLists());
        }
        initIntegralDetail(lists);
    }

    private void toastNoMore() {
        Toast.makeText(MemberCenterActivitybak.this,"没有更多了",Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新积分明细列表
     * @param lists
     */
    private CommonAdapter<MemberCenterIntegralDetailInfo> adapter;
    private void initIntegralDetail(List<MemberCenterIntegralDetailInfo> lists) {
        person_membercneter_mlv.setAdapter(adapter = new CommonAdapter<MemberCenterIntegralDetailInfo>(
                getApplicationContext(),lists,R.layout.person_member_center_item
        ) {
            @Override
            protected void convert(ViewHolder viewHolder, MemberCenterIntegralDetailInfo item) {
                viewHolder.setText(R.id.person_membercenter_jifenname_tv,item.getMsg());
                viewHolder.setText(R.id.person_membercenter_jifentime_tv, TimeOrDateUtil.formatDate(item.getCreate_time(),TimeOrDateUtil.YYYY_MM_DD));

                TextView view = viewHolder.getView(R.id.person_membercenter_jifenvalue_tv);
                    if (item.getType() == 1 ){
                        String score = "+"+item.getScore();
                        view.setText(StringUtil.setTextSizeColor(score, Color.RED,0,score.length(),27));
                    }else{
                        String score = "-"+item.getScore();
                        view.setText(StringUtil.setTextSizeColor(score, Color.GRAY,0,score.length(),27));
                    }

            }
        });
    }

    /**
     * 初始化头像、昵称、积分等级
     * @param memberCenterUserInfo
     */
    private void initUser(MemberCenterUserInfo memberCenterUserInfo) {
        ImageDownLoad.getDownLoadSmallImg(memberCenterUserInfo.getAvatar(),person_item_headportrait_iv);
        person_item_grade_tv.setText("V"+memberCenterUserInfo.getGrade());
        person_membercenter_username_tv.setText(memberCenterUserInfo.getUser_name());
    }

    /**
     * 获取更多积分明细信息
     * @param view
     */
    public void getMoreClick(View view){
        UiHelper.ShowOneToast(getApplicationContext(),"获取更多");
        ++pageIndex;

        if (Constants.IS_LOCALDATA){
            constructByLocal(pageIndex);
        }else{
            initObject();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.IS_LOCALDATA){
            constructByLocal(pageIndex);
        }else{
            initObject();
        }
    }
}
