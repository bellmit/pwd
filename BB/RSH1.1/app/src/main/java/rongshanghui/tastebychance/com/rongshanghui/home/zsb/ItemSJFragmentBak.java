package rongshanghui.tastebychance.com.rongshanghui.home.zsb;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.rzy.bean.ZSBRZYBean;
import rongshanghui.tastebychance.com.rongshanghui.home.zsb.bean.DomesticSJBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.RoundImageView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：ItemSJFragment 首页-招商宝-商机
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/1 19:02
 * 修改人：
 * 修改时间：2017/12/1 19:02
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemSJFragmentBak extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private String type;

    private TextView sht_domestic_tv;
    private TextView sht_international_tv;

    private boolean isPrepared;

    private CommonAdapter<DomesticSJBean> domesticAdapter;
    private CommonAdapter<ZSBRZYBean.DataBean> internationalAdapter;
    private PullRefreshPushAddListView pullrefreshlistview;

    private List<DomesticSJBean> domesticList = new ArrayList<>();
    private List<ZSBRZYBean.DataBean> internalList = new ArrayList<>();
    private boolean isInternational = false;//国内，国际

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private int pageIndex = Constants.PAGE_START_INDEX;
    private String selectedGroupItemId;
    private final int DOMESTIC_WHAT = 1000;
    private final int INTERNATION_WHAT = 1001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                ResInfo resInfo = (ResInfo) msg.obj;
                switch (msg.what) {
                    case Constants.WHAT_REFRESH:
                        pageIndex = Constants.PAGE_START_INDEX;
                        if (!isInternational) {
                            domesticAdapter.notifyDataSetChanged();
                        } else {
                            internationalAdapter.notifyDataSetChanged();
                        }
                        onLoad();
                        break;
                    case Constants.WHAT_LOADMORE:
                        ++pageIndex;
                        if (!isInternational) {
                            domesticAdapter.notifyDataSetChanged();
                        } else {
                            internationalAdapter.notifyDataSetChanged();
                        }
                        onLoad();
                        break;
                    case DOMESTIC_WHAT:
                        try {
                            if (null == domesticList) {
                                domesticList = new ArrayList<>();
                            }

                            if (pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(DomesticSJBean.class).size() == 0) {
                                ToastUtils.showOneToast(getActivity().getApplicationContext(), "没有更多了");
                                return;
                            }

                            if (emptyLayout != null) {
                                if (pageIndex == Constants.PAGE_START_INDEX && (resInfo.getDataList(DomesticSJBean.class) == null || resInfo.getDataList(DomesticSJBean.class).size() == 0)) {
                                    emptyLayout.showEmpty(R.drawable.nothing, "");
                                } else {
                                    emptyLayout.hide();
                                }
                            }

                            domesticList.addAll(resInfo.getDataList(DomesticSJBean.class));


                            pullrefreshlistview = (PullRefreshPushAddListView) rootView.findViewById(R.id.zsb_sj_plv);
                            domesticAdapter = new CommonAdapter<DomesticSJBean>(
                                    getActivity().getApplicationContext(), domesticList, R.layout.item_sht_mine
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final DomesticSJBean item) {
                                    RoundImageView rtv = viewHolder.getView(R.id.item_head_rtv);
                                    PicassoUtils.getinstance().loadCircleImage(getActivity().getApplicationContext(), item.getAvatar(), rtv, 31);

                                    TextView nameTv = viewHolder.getView(R.id.item_name_tv);
                                    if (item.getUser_id() == 0) {//未入驻
                                        nameTv.setText(item.getName() + "（未入驻）");
                                        nameTv.setTextColor(getActivity().getResources().getColor(R.color.lightgray));
                                    } else {
                                        nameTv.setText(item.getName());
                                        nameTv.setTextColor(getActivity().getResources().getColor(R.color.gray));
                                    }

                                    final TextView followTv = viewHolder.getView(R.id.item_adopt_tv);
                                    //初始化关注的状态
                                    if (item.getIs_cared() == 0) {
                                        followTv.setText("[加关注]");
                                        followTv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));
                                    } else {
                                        followTv.setText("[已关注]");
                                        followTv.setTextColor(getActivity().getResources().getColor(R.color.font_gray));
                                    }

                                    followTv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {

                                            if (item.getUser_id() == 0) {
                                                ToastUtils.showOneToast(getActivity().getApplicationContext(), "未入驻单位无法加取关");
                                                return;
                                            }

                                            if (item.getIs_cared() == 0) {//1 已关注 0 未关注
                                                userCare(item.getUser_id() + "", true);
                                            } else {
                                                //取消关注的网络请求（请求成功后刷新界面）
                                                DialogUtils.getInstance().AlertMessage(getActivity(), "", "确定取消关注", "容我三思", "心意已决", null, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        userCare(item.getUser_id() + "", false);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            };
                            pullrefreshlistview.setAdapter(domesticAdapter);
                            pullrefreshlistview.setPullRefreshEnable(false);
                            pullrefreshlistview.setPullLoadEnable(false);
                            pullrefreshlistview.setXListViewListener(fragment);

                            pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (domesticList.get(position - 1).getUser_id() == 0) {
                                        ToastUtils.showOneToast(getActivity().getApplicationContext(), "未入驻单位无法查看其信息");
                                        return;
                                    }

                                    //跳转到镇街信息
                                   /* WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                    wf.get().setId(domesticList.get(position - 1).getUser_id());
                                    wf.get().setIsCared(domesticList.get(position - 1).getIs_cared());
                                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("镇街"));
                                    SystemUtil.getInstance().intentToDetail(getActivity(), wf.get());*/

                                    //跳转到详情
                                   /* WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(domesticList.get(position - 1).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(domesticList.get(position - 1).getDetail());
                                    wf.get().setIsCollect(domesticList.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(domesticList.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(domesticList.get(position - 1).getPost_title());
                                    wf.get().setShareImgUrl(domesticList.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(domesticList.get(position - 1).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(getActivity(), wf.get());*/
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case INTERNATION_WHAT:
                        try {
                            if (null == internalList) {
                                internalList = new ArrayList<>();
                            }

                            if (pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZSBRZYBean.class).getData().size() == 0) {
                                ToastUtils.showOneToast(getActivity().getApplicationContext(), "没有更多了");
                                return;
                            }
                            if (emptyLayout != null) {
                                if (pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(ZSBRZYBean.class).getData() == null || resInfo.getClass(ZSBRZYBean.class).getData().size() == 0)) {
                                    emptyLayout.showEmpty(R.drawable.nothing, "");
                                } else {
                                    emptyLayout.hide();
                                }
                            }
                            internalList.addAll(resInfo.getClass(ZSBRZYBean.class).getData());

                            pullrefreshlistview = (PullRefreshPushAddListView) rootView.findViewById(R.id.zsb_sj_plv);
                            internationalAdapter = new CommonAdapter<ZSBRZYBean.DataBean>(
                                    getActivity().getApplicationContext(), internalList, R.layout.item_rsnewslist
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final ZSBRZYBean.DataBean item) {
                                    PicassoUtils.getinstance().loadNormalByPath(getActivity(), item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                                    viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                                    viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                                    TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                                    tv.setText(item.getUnit_name());
                                    tv.setTextColor(getResources().getColor(R.color.font_blue));
                                }
                            };
                            pullrefreshlistview.setAdapter(internationalAdapter);
                            pullrefreshlistview.setPullRefreshEnable(true);
                            pullrefreshlistview.setPullLoadEnable(true);
                            pullrefreshlistview.setXListViewListener(fragment);

                            pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(internalList.get(position - 1).getId());
                                    wf.get().setTitle("信息");
                                    wf.get().setUrl(internalList.get(position - 1).getDetail());
                                    wf.get().setIsCollect(internalList.get(position - 1).getIs_collect());
                                    wf.get().setIsLike(internalList.get(position - 1).getIs_like());

                                    wf.get().setShareTitle(internalList.get(position - 1).getPost_title());
                                    wf.get().setShareImgUrl(internalList.get(position - 1).getM_post_image1());
                                    wf.get().setShareUrl(internalList.get(position - 1).getDetail());
                                    SystemUtil.getInstance().intentToWebActivity(getActivity(), wf.get());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View rootView;
    private ItemSJFragmentBak fragment;
    private EmptyLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        domesticList = new ArrayList<>();
        internalList = new ArrayList<>();

        View contextView = inflater.inflate(R.layout.fragment_zsb_sj, container, false);
        sht_domestic_tv = (TextView) contextView.findViewById(R.id.sht_domestic_tv);
        sht_international_tv = (TextView) contextView.findViewById(R.id.sht_international_tv);
        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);

        rootView = contextView;
        fragment = this;

        isInternational = false;

        //国内商机不需要分页加载
        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullRefreshEnable(false);
            pullrefreshlistview.setPullLoadEnable(false);
            pullrefreshlistview.setXListViewListener(this);
        }

        return contextView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != domesticList) {
            domesticList.clear();
        }
        getDomesticData();

        if (null != sht_domestic_tv) {
            sht_domestic_tv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));
            sht_domestic_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sht_domestic_tv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));
                    if (null != sht_international_tv) {
                        sht_international_tv.setTextColor(getActivity().getResources().getColor(R.color.font_gray));
                    }
                    if (isInternational) {
                        getDomesticData();
                        if (null != domesticList) {
                            domesticList.clear();
                        }
                    }
                    isInternational = false;
                }
            });
        }

        if (null != sht_international_tv) {
            sht_international_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != sht_domestic_tv) {
                        sht_domestic_tv.setTextColor(getActivity().getResources().getColor(R.color.font_gray));
                    }
                    sht_international_tv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));

                    if (!isInternational) {//防止多次点击“国际”标签时加载重复的数据
                        getInternationalData();
                        if (null != internalList) {
                            internalList.clear();
                        }
                    }
                    isInternational = true;
                }
            });
        }
    }

    /**
     * 获取国内数据
     */
    private void getDomesticData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_rzy_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_ZSBDEPARTMENT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
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

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = DOMESTIC_WHAT;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                        if (emptyLayout != null) {
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * 获取国际数据
     */
    private void getInternationalData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_rzy_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_POSTLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("publish_location", SystemUtil.getInstance().castType2(type));
        builder.add("page", pageIndex + "");
        /*if (SystemUtil.getInstance().getIsLogin() && null != SystemUtil.getInstance().getUserInfo()){
            if (StringUtil.isNotEmpty(SystemUtil.getInstance().getUserInfo().getId()+"")){
				builder.add("user_id",SystemUtil.getInstance().getUserInfo().getId()+"");
			}
		}*/

        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
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

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = INTERNATION_WHAT;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                        if (emptyLayout != null) {
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isPrepared = true;

        } else {
            if (isPrepared) {
                isPrepared = false;

            }
        }
    }

    /**
     * 加关注、取消关注
     * <p>
     * token 	是 	string 	token值
     * user_id 	是 	int 	被关注的用户id
     * type 	是 	int 	1 关注 2 取消关注
     *
     * @param userId
     */
    private void userCare(String userId, boolean isAddCare) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_rzy_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_USERCARER;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("user_id", userId);
        builder.add("type", isAddCare ? "1" : "2");
        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
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
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != domesticList) {
                                    domesticList.clear();
                                }
                                getDomesticData();
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

    private void onLoad() {
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }


    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (!isInternational) {
            if (null != domesticList) {
                domesticList.clear();
            }
            getDomesticData();
        } else {
            getInternationalData();
        }
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        if (!isInternational) {
            getDomesticData();
        } else {
            getInternationalData();
        }
        onLoad();
    }

}