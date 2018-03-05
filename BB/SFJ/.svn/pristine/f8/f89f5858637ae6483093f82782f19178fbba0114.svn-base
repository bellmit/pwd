package com.tastebychance.sfj.apply.mywaitdealwith;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.bean.MyJobBean;
import com.tastebychance.sfj.apply.bean.ToApplyDetailBean;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
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
 * 我的申请、我的待办
 *
 * @author shenbinghong
 * @time 2018/2/5 22:05
 * @path com.tastebychance.sfj.apply.MyJobFragment.java
 */
public class MyJobFragment extends MyBaseFragment {

    private CommonAdapter<MyJobBean.DataBeanX.DataBean> adapter;

    private List<MyJobBean.DataBeanX.DataBean> dataBeen = new ArrayList<MyJobBean.DataBeanX.DataBean>();

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
                        case Constants.WHAT_GETDATA2://我的待办
                            if (((MyJobFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getListByKey("data", MyJobBean.DataBeanX.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getActivity(), "没有更多了");

                                ((MyJobFragment) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                emptyLayoutShowOrHide(((MyJobFragment) t).emptyLayout, false);
                                if (((MyJobFragment) t).adapter != null) {
                                    ((MyJobFragment) t).adapter.notifyDataSetChanged();
                                }
                                return;
                            }

                            //空布局
                            if (((MyJobFragment) t).pageIndex == Constants.PAGE_START_INDEX && resInfo.getListByKey("data", MyJobBean.DataBeanX.DataBean.class).size() == 0) {
                                emptyLayoutShowOrHide(((MyJobFragment) t).emptyLayout, true);
                            } else {
                                emptyLayoutShowOrHide(((MyJobFragment) t).emptyLayout, false);
                            }

                            if (((MyJobFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((MyJobFragment) t).scrollMyListViewToBottom();
                            }

                            if (((MyJobFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                ((MyJobFragment) t).scrollMyListViewToTop();
                            }

                            ((MyJobFragment) t).dataBeen.addAll(resInfo.getListByKey("data", MyJobBean.DataBeanX.DataBean.class));
                            ((MyJobFragment) t).lv.setAdapter(((MyJobFragment) t).adapter = new CommonAdapter<MyJobBean.DataBeanX.DataBean>(
                                    t.getActivity().getApplicationContext(), ((MyJobFragment) t).dataBeen, R.layout.item_fragment_myapply
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, MyJobBean.DataBeanX.DataBean item) {
                                    TextView nameTv = viewHolder.getView(R.id.item_fragment_myapply_name_tv);
                                    nameTv.setText(item.getType());
                                    nameTv.setTextColor(t.getActivity().getResources().getColor(R.color.gray));

                                    TextView dateTv = viewHolder.getView(R.id.item_fragment_myapply_date_tv);
                                    dateTv.setTextColor(t.getActivity().getResources().getColor(R.color.gray));
                                    dateTv.setText(item.getAdd_time());
                                }
                            });

                            ((MyJobFragment) t).lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToApplyDetailBean> wf = new WeakReference<ToApplyDetailBean>(new ToApplyDetailBean());
                                    wf.get().setId(((MyJobFragment) t).dataBeen.get(position).getId());
                                    wf.get().setDetail(((MyJobFragment) t).dataBeen.get(position).getDetail());
                                    Intent intent = new Intent(t.getActivity(), WaitDealWithActivity.class);
                                    intent.putExtra(Constants.KEY_TOMYAPPLYDETAIL, wf.get());
                                    t.getActivity().startActivity(intent);
                                }
                            });

                            if (null != ((MyJobFragment) t).onRefreshLister.getPullToRefreshLayout()) {
                                if (((MyJobFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((MyJobFragment) t).onRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                                } else if (((MyJobFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((MyJobFragment) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                }

                                if (((MyJobFragment) t).adapter != null) {
                                    ((MyJobFragment) t).adapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emptyLayoutShowOrHide(((MyJobFragment) t).emptyLayout, false);
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private String type;
    private View rootView;
    private MyJobFragment fragment;
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
            rootView = inflater.inflate(R.layout.fragment_myjob, container, false);
            fragment = this;
            emptyLayout = (EmptyLayout) rootView.findViewById(R.id.emptyLayout);
            refreshView = (PullToRefreshRelativeLayout) rootView.findViewById(R.id.refresh_view);
            lv = (PullableListView) rootView.findViewById(R.id.fragment_rsnews_item_lv);
            isPrepare = true;

            onRefreshLister = new MyOnRefreshLister();
            refreshView.setOnRefreshListener(onRefreshLister);


            /*if (null != dataBeen) {
                dataBeen.clear();
            }
            getData();*/
        }

        return rootView;
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            pageIndex = Constants.PAGE_START_INDEX;
            if (null != dataBeen) {
                dataBeen.clear();
            }
            getData();
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != dataBeen) {
            dataBeen.clear();
        }
        getData();
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
        Map<String, String> map = new HashMap<String, String>(2);
        url = UrlManager.URL_MYJOB;
        map.put("page", pageIndex + "");
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

                                msg.what = Constants.WHAT_GETDATA2;
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


            if (null == dataBeen) {
                dataBeen = new ArrayList<>();
            }

            if (null != dataBeen) {
                dataBeen.clear();
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