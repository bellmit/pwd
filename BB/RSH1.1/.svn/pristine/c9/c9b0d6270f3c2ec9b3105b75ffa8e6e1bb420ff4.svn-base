package rongshanghui.tastebychance.com.rongshanghui.home.rsnews;

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

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.TwoLevelListAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.rsnews.bean.RSNewslvRes;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：ItemTTFragment 首页-融商新闻-头条
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/2 18:03
 * 修改人：
 * 修改时间：2017/12/2 18:03
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private TwoLevelListAdapter adapter;
    private PullRefreshPushAddListView pullrefreshlistview;
    private EmptyLayout emptyLayout;
    private ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
    private ItemFragment fragment;

    private RSNewslvRes.DataBeanX.TopBean topBean;
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
                            ((ItemFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ItemFragment) t).adapter.notifyDataSetChanged();
                            ((ItemFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ItemFragment) t).pageIndex;
                            ((ItemFragment) t).adapter.notifyDataSetChanged();
                            ((ItemFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:

                            try {
                                ResInfo resInfo = (ResInfo) msg.obj;

                                if (((ItemFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((ItemFragment) t).topBean = resInfo.getClassByKey("top", RSNewslvRes.DataBeanX.TopBean.class);
                                    WeakReference<HashMap<String, Object>> wf2 = new WeakReference<HashMap<String, Object>>(new HashMap<String, Object>());
                                    wf2.get().put(Constants.TYPE, Constants.GROUP);
                                    wf2.get().put(Constants.DATA, ((ItemFragment) t).topBean);
                                    ((ItemFragment) t).items.add(wf2.get());
                                }

                                if (((ItemFragment) t).emptyLayout != null) {
                                    if (((ItemFragment) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getClassByKey("list", RSNewslvRes.DataBeanX.ListBean.class) == null || resInfo.getClassByKey("list", RSNewslvRes.DataBeanX.ListBean.class).getData().size() == 0)) {
                                        emptyLayoutShowOrHide(((ItemFragment) t).emptyLayout, true);
                                    } else {
                                        emptyLayoutShowOrHide(((ItemFragment) t).emptyLayout, false);
                                    }
                                }

                                final RSNewslvRes.DataBeanX.ListBean listBeans = resInfo.getClassByKey("list", RSNewslvRes.DataBeanX.ListBean.class);
                                System.out.println("listBeans = " + listBeans);

                                if (((ItemFragment) t).pageIndex > Constants.PAGE_START_INDEX && listBeans.getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getActivity().getApplicationContext(), "没有更多了");
                                    emptyLayoutShowOrHide(((ItemFragment) t).emptyLayout, false);
                                    return;
                                }

                                if (((ItemFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((ItemFragment) t).scrollMyListViewToBottom();
                                }

                                for (RSNewslvRes.DataBeanX.ListBean.DataBean dataBean : listBeans.getData()) {
                                    WeakReference<HashMap<String, Object>> wf3 = new WeakReference<HashMap<String, Object>>(new HashMap<String, Object>());
                                    wf3.get().put(Constants.TYPE, Constants.ITEM);
                                    wf3.get().put(Constants.DATA, dataBean);
                                    ((ItemFragment) t).items.add(wf3.get());
                                }

                                ((ItemFragment) t).pullrefreshlistview = (PullRefreshPushAddListView) ((ItemFragment) t).rootView.findViewById(R.id.fragment_rsnews_item_lv);
                                ((ItemFragment) t).adapter = new TwoLevelListAdapter(t.getActivity(), ((ItemFragment) t).items);
                                ((ItemFragment) t).pullrefreshlistview.setAdapter(((ItemFragment) t).adapter);

                                ((ItemFragment) t).pullrefreshlistview.setPullLoadEnable(true);
                                if (null != ((ItemFragment) t).fragment) {
                                    ((ItemFragment) t).pullrefreshlistview.setXListViewListener(((ItemFragment) t).fragment);
                                }

                                ((ItemFragment) t).pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        System.out.println("position = " + position);

                                        WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                        if (position == 1) {
                                            wf.get().setId(((ItemFragment) t).topBean.getId());
                                            wf.get().setIsCollect(((ItemFragment) t).topBean.getIs_collect());
                                            wf.get().setIsLike(((ItemFragment) t).topBean.getIs_like());
                                            wf.get().setUrl(((ItemFragment) t).topBean.getDetail());

                                            wf.get().setShareTitle(((ItemFragment) t).topBean.getPost_title());
                                            wf.get().setShareImgUrl(((ItemFragment) t).topBean.getM_post_image1());
                                            wf.get().setShareUrl(((ItemFragment) t).topBean.getDetail());
                                        } else {
                                            wf.get().setIsCollect(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getIs_collect());
                                            wf.get().setIsLike(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getIs_like());
                                            wf.get().setId(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getId());
                                            wf.get().setUrl(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getDetail());

                                            wf.get().setShareTitle(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getPost_title());
                                            wf.get().setShareImgUrl(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getM_post_image1());
                                            wf.get().setShareUrl(((RSNewslvRes.DataBeanX.ListBean.DataBean) ((ItemFragment) t).items.get(position - 1).get(Constants.DATA)).getDetail());
                                        }
                                        wf.get().setTitle("信息");
                                        if (StringUtil.isEmpty(wf.get().getUrl())) {
                                            ToastUtils.showOneToast(t.getActivity(), Constants.NODETAIL);
                                            return;
                                        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        rootView = contextView;

        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);
        pullrefreshlistview = (PullRefreshPushAddListView) contextView.findViewById(R.id.fragment_rsnews_item_lv);
        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullLoadEnable(true);
            pullrefreshlistview.setXListViewListener(this);
        }

        fragment = this;
        return contextView;
    }

    /**
     * 滚动到底部
     */
    private void scrollMyListViewToBottom() {
        pullrefreshlistview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                pullrefreshlistview.setSelection(adapter.getCount() - 1);
            }
        });
    }

    private void getData() {
        if (Constants.IS_LOCALDATA) {
        } else {
            getDataFromServer();
        }
    }

    /**
     * 融商新闻列表接口
     * publish_location 	true 	int 	1:融商新闻-头条;2:融商新闻-融商风采;3:融商新闻-融商活动;4:融商新闻-文化寻根
     * page 	是 	int 	当前页页码
     */
    private void getDataFromServer() {
        if (null == loadingBar && null != rootView) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_rsnews_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_NEWSES;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("publish_location", SystemUtil.getInstance().castType2(type));
        builder.add("page", pageIndex + "");
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
                onLoad();
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

        String str = Thread.currentThread().getName();
        System.out.println("str = " + str);

        if (StringUtil.isNotEmpty(str) && str.equals("main") && null != pullrefreshlistview) {
            pullrefreshlistview.stopRefresh();
            pullrefreshlistview.stopLoadMore();
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
            pullrefreshlistview.setRefreshTime(date);
        }
    }

    private RSNewslvRes analysisBean(String resStr) {
        if (StringUtil.isEmpty(resStr)) {
            if (Constants.IS_DEVELOPING) {
//				throw new IllegalArgumentException(this.getClass().getSimpleName()+"获取到的str为空");
            }
            return null;
        }
        return JSONObject.parseObject(resStr, RSNewslvRes.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != items) {
            items.clear();
        }
        getData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != items) {
            items.clear();
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