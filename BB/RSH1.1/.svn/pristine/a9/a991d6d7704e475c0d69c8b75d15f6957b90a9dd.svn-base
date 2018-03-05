package rongshanghui.tastebychance.com.rongshanghui.news.systempush;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWeb2Activity;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.news.caremsg.bean.CareMsgBean;
import rongshanghui.tastebychance.com.rongshanghui.news.systempush.bean.SystemMsgBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：CareMsgFragment 关注消息
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/29 9:52
 * 修改人：
 * 修改时间：2017/11/29 9:52
 * 修改备注：
 *
 * @version 1.0
 */
public class SystemPushFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private CommonAdapter<SystemMsgBean.DataBean> adapter;
    private PullRefreshPushAddListView pullrefreshlistview;

    private List<SystemMsgBean.DataBean> dataBeen = new ArrayList<>();
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
                            ((SystemPushFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((SystemPushFragment) t).adapter.notifyDataSetChanged();
                            ((SystemPushFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((SystemPushFragment) t).pageIndex;
                            ((SystemPushFragment) t).adapter.notifyDataSetChanged();
                            ((SystemPushFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            if (((SystemPushFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getListByKey("data", SystemMsgBean.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getActivity().getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((SystemPushFragment) t).emptyLayout, false);
                                return;
                            }

                            if (((SystemPushFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((SystemPushFragment) t).scrollMyListViewToBottom();
                            }

                            if (((SystemPushFragment) t).emptyLayout != null) {
                                if (((SystemPushFragment) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getListByKey("data", SystemMsgBean.DataBean.class) == null || resInfo.getListByKey("data", SystemMsgBean.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((SystemPushFragment) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((SystemPushFragment) t).emptyLayout, false);
                                }
                            }

                            ((SystemPushFragment) t).dataBeen.addAll(resInfo.getListByKey("data", SystemMsgBean.DataBean.class));

                            if (((SystemPushFragment) t).pullrefreshlistview == null) {
                                ((SystemPushFragment) t).pullrefreshlistview = (PullRefreshPushAddListView) ((SystemPushFragment) t).rootView.findViewById(R.id.fragment_rsnews_item_lv);
                            }
                            ((SystemPushFragment) t).adapter = new CommonAdapter<SystemMsgBean.DataBean>(
                                    t.getActivity().getApplicationContext(), ((SystemPushFragment) t).dataBeen, R.layout.item_sysnews
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final SystemMsgBean.DataBean item) {
                                    TextView noreadTv = viewHolder.getView(R.id.item_sysnews_head_noread_tv);
                                    if (item.getIs_read() == 0) {// 	1 已读 0 未读
                                        noreadTv.setVisibility(View.VISIBLE);
                                    } else {
                                        noreadTv.setVisibility(View.GONE);
                                    }
                                    viewHolder.setText(R.id.item_sysnews_head_title_tv, item.getTitle());
                                    TextView contextTv = viewHolder.getView(R.id.item_sysnews_content_tv);
                                    contextTv.setText(item.getContent());
                                    contextTv.setTextColor(t.getActivity().getResources().getColor(R.color.lightgray));
                                    TextView timeTv = viewHolder.getView(R.id.item_sysnews_time_tv);
                                    timeTv.setText(item.getAdd_time());
                                    timeTv.setTextColor(t.getActivity().getResources().getColor(R.color.lightgray));
                                }
                            };
                            ((SystemPushFragment) t).pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setTitle("消息");
                                    wf.get().setId(((SystemPushFragment) t).dataBeen.get(position - 1).getId());
                                    wf.get().setUrl(((SystemPushFragment) t).dataBeen.get(position - 1).getDetail());

                                    Intent intent = new Intent(t.getActivity(), ShowWebSysNewsActivity.class);
                                    intent.putExtra("showWebBean", wf.get());
                                    t.getActivity().startActivity(intent);
                                }
                            });

                            ((SystemPushFragment) t).pullrefreshlistview.setAdapter(((SystemPushFragment) t).adapter);
                            ((SystemPushFragment) t).pullrefreshlistview.setPullLoadEnable(true);
                            ((SystemPushFragment) t).pullrefreshlistview.setPullRefreshEnable(true);
                            ((SystemPushFragment) t).pullrefreshlistview.setXListViewListener(((SystemPushFragment) t).fragment);
                            ((SystemPushFragment) t).adapter.notifyDataSetChanged();
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
    private SystemPushFragment fragment;
    private EmptyLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        rootView = contextView;
        fragment = this;
        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);


        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullRefreshEnable(true);
            pullrefreshlistview.setPullLoadEnable(true);
            pullrefreshlistview.setXListViewListener(this);
        }

        isPrepare = true;
        return contextView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != dataBeen) {
            dataBeen.clear();
        }
        getData();
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            if (null != dataBeen) {
                dataBeen.clear();
            }
            getData();
        } else {
            if (isPrepare) {
                if (null != dataBeen) {
                    dataBeen.clear();
                }
                getData();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepare = false;
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
     * token 	是 	string 	token值
     * page 	是 	string 	当前页码
     */
    private void getDataFromServer() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.news_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_NEWS_SYSTEMPUSH;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("page", pageIndex + "")
                .build();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBeen) {
            dataBeen.clear();
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