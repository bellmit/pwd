package com.tastebychance.sfj.filedealwith.received;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
import com.tastebychance.sfj.common.web.bean.ShowWebBean;
import com.tastebychance.sfj.filedealwith.received.bean.ReceivedBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.pullableview.PullToRefreshRelativeLayout;
import com.tastebychance.sfj.view.pullableview.PullableListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 收件箱
 *
 * @author shenbinghong
 * @time 2018/1/31 22:13
 * @path com.tastebychance.sfj.apply.CareMsgFragment.java
 */
public class ReceivedFragment extends MyBaseFragment {

    private List<ReceivedBean.DataBean> dataBeans = new ArrayList<ReceivedBean.DataBean>();
    private CommonAdapter<ReceivedBean.DataBean> adapter;
    private int pageIndex = Constants.PAGE_START_INDEX;

    private static class MyHandler<T extends Fragment> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA://我的申请
                            if (((ReceivedFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(ReceivedBean.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getActivity(), "没有更多了");

                                ((ReceivedFragment) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                emptyLayoutShowOrHide(((ReceivedFragment) t).emptyLayout, false);
                                if (((ReceivedFragment) t).adapter != null) {
                                    ((ReceivedFragment) t).adapter.notifyDataSetChanged();
                                }
                                return;
                            }

                            //空布局
                            if (((ReceivedFragment) t).pageIndex == Constants.PAGE_START_INDEX && resInfo.getDataList(ReceivedBean.DataBean.class).size() == 0) {
                                emptyLayoutShowOrHide(((ReceivedFragment) t).emptyLayout, true);
                            } else {
                                emptyLayoutShowOrHide(((ReceivedFragment) t).emptyLayout, false);
                            }

                            ((ReceivedFragment) t).dataBeans.addAll(resInfo.getDataList(ReceivedBean.DataBean.class));
                            if (((ReceivedFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((ReceivedFragment) t).scrollMyListViewToBottom();
                            }

                            if (((ReceivedFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                ((ReceivedFragment) t).scrollMyListViewToTop();
                            }

                            ((ReceivedFragment) t).lv.setAdapter(((ReceivedFragment) t).adapter = new CommonAdapter<ReceivedBean.DataBean>(
                                    t.getActivity().getApplicationContext(), ((ReceivedFragment) t).dataBeans, R.layout.item_fragment_filedealwith_received
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, ReceivedBean.DataBean item) {
                                    ImageView focusStateIv = viewHolder.getView(R.id.item_fragment_filedealwith_state_iv);
                                    TextView jobTv = viewHolder.getView(R.id.item_fragment_filedealwith_job_tv);
                                    TextView dateTv = viewHolder.getView(R.id.item_fragment_filedealwith_date_tv);
                                    TextView detailJobTv = viewHolder.getView(R.id.item_fragment_filedealwith_detailjob_tv);
                                    TextView contentTv = viewHolder.getView(R.id.item_fragment_filedealwith_content_tv);
                                    ImageView hasFileIv = viewHolder.getView(R.id.item_fragment_filedealwith_hasfile_iv);

                                    jobTv.setText(item.getSend_user());
                                    dateTv.setText(item.getAdd_time());
                                    detailJobTv.setText(item.getTitle());
                                    contentTv.setText(item.getContent());

                                    if (item.getHas_read() == 0){//1:已读 0：未读
                                        focusStateIv.setImageResource(R.drawable.pitchon);
                                        jobTv.setTextColor(t.getResources().getColor(R.color.gray));
                                        dateTv.setTextColor(t.getResources().getColor(R.color.gray));
                                        detailJobTv.setTextColor(t.getResources().getColor(R.color.gray));
                                        contentTv.setTextColor(t.getResources().getColor(R.color.gray));
                                    } else {
                                        focusStateIv.setImageResource(R.drawable.pitch);
                                        jobTv.setTextColor(t.getResources().getColor(R.color.lightgray));
                                        dateTv.setTextColor(t.getResources().getColor(R.color.lightgray));
                                        detailJobTv.setTextColor(t.getResources().getColor(R.color.lightgray));
                                        contentTv.setTextColor(t.getResources().getColor(R.color.lightgray));
                                    }

                                    if (item.getHas_file() == 0){//1：有附件 0 没有附件
                                        hasFileIv.setVisibility(View.INVISIBLE);
                                    } else {
                                        hasFileIv.setVisibility( View.VISIBLE);
                                    }
                                }
                            });

                            ((ReceivedFragment) t).lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                                    wf.get().setId(((ReceivedFragment) t).dataBeans.get(position).getId());
                                    wf.get().setUrl(((ReceivedFragment) t).dataBeans.get(position).getDetail());
                                    wf.get().setTitle(((ReceivedFragment) t).dataBeans.get(position).getTitle());
                                    wf.get().setToRead(true);
                                    SystemUtil.getInstance().intentToWebInfoActivity(t.getActivity(), wf.get());
                                }
                            });

                            if (null != ((ReceivedFragment) t).onRefreshLister.getPullToRefreshLayout()) {
                                if (((ReceivedFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((ReceivedFragment) t).onRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                                } else if (((ReceivedFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((ReceivedFragment) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                }

                                if (((ReceivedFragment) t).adapter != null) {
                                    ((ReceivedFragment) t).adapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emptyLayoutShowOrHide(((ReceivedFragment) t).emptyLayout, false);
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private String type;
    private View rootView;
    private ReceivedFragment fragment;
    private EmptyLayout emptyLayout;
    private PullableListView lv;
    private PullToRefreshRelativeLayout refreshView;
    private MyOnRefreshLister onRefreshLister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_filedealwith_received, container, false);
            fragment = this;
            emptyLayout =  rootView.findViewById(R.id.emptyLayout);
            refreshView =  rootView.findViewById(R.id.refresh_view);
            lv =  rootView.findViewById(R.id.fragment_rsnews_item_lv);
            isPrepare = true;

            onRefreshLister = new MyOnRefreshLister();
            refreshView.setOnRefreshListener(onRefreshLister);
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != dataBeans) {
            dataBeans.clear();
        }
        getData();
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            if (null != dataBeans) {
                dataBeans.clear();
            }
            getData();
        } else {

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
        lv.post(new Runnable() {

            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv.setSelection(adapter.getCount() - 1);
            }
        });
    }
     
    /**
     * 滚动到顶部
     */
    private void scrollMyListViewToTop() {
        lv.post(new Runnable() {
            @Override
            public void run() {
                lv.setSelection(0);
            }
        });

    }

    /**
     * token 	是 	string 	token值
     * page 	是 	string 	当前页码
     */
    private String url;

    private void getData() {
        if (null != emptyLayout) {
            emptyLayout.showLoading(R.drawable.iv_loading, Constants.LOADING);
        }
        if (!TextUtils.isEmpty(type) && type.equals(Constants.FILEDEAL_RECEIVED)) {
            url = UrlManager.URL_RECEIVED;
        } else {
            url = UrlManager.URL_HASBEENSENT;
        }
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                if (type.equals(Constants.FILEDEAL_RECEIVED)) {
                                    msg.what = Constants.WHAT_GETDATA;
                                } else {
                                    msg.what = Constants.WHAT_GETDATA2;
                                }
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);

                        if (null != emptyLayout) {
                            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");

                    if (emptyLayout != null) {
                        emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        emptyLayout.showError();
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                if (emptyLayout != null) {
                    emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                    emptyLayout.showError();
                }
                super.onError(call, response, e);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    class MyOnRefreshLister implements PullToRefreshRelativeLayout.OnRefreshListener {
        private PullToRefreshRelativeLayout pullToRefreshLayout;

        public PullToRefreshRelativeLayout getPullToRefreshLayout() {
            return pullToRefreshLayout;
        }

        @Override
        public void onRefresh(PullToRefreshRelativeLayout pullToRefreshLayout) {
            //下拉刷新操作
            pageIndex = Constants.PAGE_START_INDEX;
            this.pullToRefreshLayout = pullToRefreshLayout;

            if (null == dataBeans) {
                dataBeans = new ArrayList<>();
            }
            if (null != dataBeans){
                dataBeans.clear();
            }

            getData();
        }

        @Override
        public void onLoadMore(final PullToRefreshRelativeLayout pullToRefreshLayout) {
            //加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            pageIndex++;
            getData();
        }
    }
}