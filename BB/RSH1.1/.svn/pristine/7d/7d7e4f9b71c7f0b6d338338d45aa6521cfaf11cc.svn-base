package rongshanghui.tastebychance.com.rongshanghui.home.rzy;

import android.content.Context;
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
import rongshanghui.tastebychance.com.rongshanghui.common.MyBaseHandler;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.rzy.bean.ZSBRZYBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.SHTMineRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：ItemRZYFragment 融资易
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/1 15:22
 * 修改人：
 * 修改时间：2017/12/1 15:22
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemRZYFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private List<ZSBRZYBean.DataBean> dataBean = new ArrayList<>();
    private CommonAdapter<ZSBRZYBean.DataBean> adapter;
    private PullRefreshPushAddListView pullrefreshlistview;

    private String type;
    private int pageIndex = Constants.PAGE_START_INDEX;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends android.support.v4.app.Fragment> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    switch (msg.what) {
                        case Constants.WHAT_REFRESH:
                            ((ItemRZYFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ItemRZYFragment) t).adapter.notifyDataSetChanged();
                            ((ItemRZYFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ItemRZYFragment) t).pageIndex;
                            ((ItemRZYFragment) t).adapter.notifyDataSetChanged();
                            ((ItemRZYFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:


                            try {
                                ResInfo resInfo = (ResInfo) msg.obj;

                                if (null == ((ItemRZYFragment) t).dataBean) {
                                    ((ItemRZYFragment) t).dataBean = new ArrayList<>();
                                }

                                if (((ItemRZYFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(ZSBRZYBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getActivity().getApplicationContext(), "没有更多了");
                                    emptyLayoutShowOrHide(((ItemRZYFragment) t).emptyLayout, false);
                                    return;
                                }

                                if (((ItemRZYFragment) t).emptyLayout != null) {
                                    if (((ItemRZYFragment) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClass(ZSBRZYBean.class).getData() == null || resInfo.getClass(ZSBRZYBean.class).getData().size() == 0)) {
                                        emptyLayoutShowOrHide(((ItemRZYFragment) t).emptyLayout, true);
                                    } else {
                                        emptyLayoutShowOrHide(((ItemRZYFragment) t).emptyLayout, false);
                                    }
                                }
                                ((ItemRZYFragment) t).dataBean.addAll(resInfo.getClass(ZSBRZYBean.class).getData());
                                ((ItemRZYFragment) t).pullrefreshlistview = (PullRefreshPushAddListView) ((ItemRZYFragment) t).rootView.findViewById(R.id.fragment_rsnews_item_lv);
                                ((ItemRZYFragment) t).adapter = new CommonAdapter<ZSBRZYBean.DataBean>(
                                        t.getActivity().getApplicationContext(), ((ItemRZYFragment) t).dataBean, R.layout.item_rsnewslist
                                ) {
                                    @Override
                                    protected void convert(ViewHolder viewHolder, final ZSBRZYBean.DataBean item) {
                                        PicassoUtils.getinstance().loadNormalByPath(t.getActivity(), item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                                        viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                                        viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                                        TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                                        tv.setText(item.getUnit_name());
                                        tv.setTextColor(t.getResources().getColor(R.color.font_blue));
                                    }
                                };
                                ((ItemRZYFragment) t).pullrefreshlistview.setAdapter(((ItemRZYFragment) t).adapter);
                                ((ItemRZYFragment) t).pullrefreshlistview.setPullLoadEnable(true);
                                ((ItemRZYFragment) t).pullrefreshlistview.setXListViewListener(((ItemRZYFragment) t).fragment);

                                ((ItemRZYFragment) t).pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                        wf.get().setId(((ItemRZYFragment) t).dataBean.get(position - 1).getId());
                                        wf.get().setTitle("信息");
                                        wf.get().setUrl(((ItemRZYFragment) t).dataBean.get(position - 1).getDetail());
                                        wf.get().setIsCollect(((ItemRZYFragment) t).dataBean.get(position - 1).getIs_collect());
                                        wf.get().setIsLike(((ItemRZYFragment) t).dataBean.get(position - 1).getIs_like());

                                        wf.get().setShareTitle(((ItemRZYFragment) t).dataBean.get(position - 1).getPost_title());
                                        wf.get().setShareImgUrl(((ItemRZYFragment) t).dataBean.get(position - 1).getM_post_image1());
                                        wf.get().setShareUrl(((ItemRZYFragment) t).dataBean.get(position - 1).getDetail());
                                        SystemUtil.getInstance().intentToWebActivity(t.getActivity(), wf.get());
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private View rootView;
    private ItemRZYFragment fragment;
    private EmptyLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);

        rootView = contextView;
        fragment = this;

        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullLoadEnable(true);
            pullrefreshlistview.setXListViewListener(this);
        }

        return contextView;
    }

    private void getData() {
        if (Constants.IS_LOCALDATA) {
        } else {
            getDataFromServer();
        }
    }

    /**
     * publish_location 	是 	int 	1:融商新闻-头条;2:融商新闻-融商风采;3:融商新闻-商会活动;4:融商新闻-文化寻根;5:招商宝-优惠政策;6:招商宝-资产交易;7:招商宝-创新创业,8:招商宝-招商秀（国际商机）
     * 9:融资易-金融资讯,10:融资易-融资融券，11：融资易-放贷信息，12：融资易-企业管理
     * page 	是 	int 	当前页页码
     * user_id 	否 	int 	查对应用户对应类别的文章
     * token 	否 	string 	有token 时要传
     */
    private void getDataFromServer() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_rzy_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

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
                                msg.what = Constants.WHAT_GETDATA;
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

    private void onLoad() {
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    private SHTMineRes analysisBean(String resStr) {
        SHTMineRes shtMineRes = JSONObject.parseObject(resStr, SHTMineRes.class);
        if (StringUtil.isEmpty(resStr)) {
            if (Constants.IS_DEVELOPING) {
                throw new IllegalArgumentException(this.getClass().getSimpleName() + "获取到的str为空");
            }
            return null;
        }
        return JSONObject.parseObject(resStr, SHTMineRes.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBean) {
            dataBean.clear();
        }
        getData();
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBean) {
            dataBean.clear();
        }
        getData();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        getData();
        onLoad();
    }
}