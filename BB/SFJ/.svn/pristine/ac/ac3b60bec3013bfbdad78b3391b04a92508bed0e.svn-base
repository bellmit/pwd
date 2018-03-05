package com.tastebychance.sfj.apply.myapply;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.bean.ApplyBean;
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
public class MyApplyFragment extends MyBaseFragment {

    private List<ApplyBean.DataBean> myApplys = new ArrayList<ApplyBean.DataBean>();
    private CommonAdapter<ApplyBean.DataBean> myApplyAdapter;


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
                            if (((MyApplyFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(ApplyBean.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getActivity(), "没有更多了");

                                ((MyApplyFragment) t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                emptyLayoutShowOrHide(((MyApplyFragment) t).emptyLayout, false);
                                if (((MyApplyFragment) t).myApplyAdapter != null) {
                                    ((MyApplyFragment) t).myApplyAdapter.notifyDataSetChanged();
                                }
                                return;
                            }

                            //空布局
                            if (null != ((MyApplyFragment) t).emptyLayout){
                                if (((MyApplyFragment) t).pageIndex == Constants.PAGE_START_INDEX && resInfo.getDataList(ApplyBean.DataBean.class).size() == 0) {
                                    emptyLayoutShowOrHide(((MyApplyFragment) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((MyApplyFragment) t).emptyLayout, false);
                                }
                            }

                            //
                            if (((MyApplyFragment) t).myApplys != null){
                                ((MyApplyFragment) t).myApplys.clear();
                            }

                            ((MyApplyFragment) t).myApplys.addAll(resInfo.getDataList(ApplyBean.DataBean.class));
                            if (((MyApplyFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((MyApplyFragment) t).scrollMyListViewToBottom();
                            }

                            if (((MyApplyFragment) t).pageIndex == Constants.PAGE_START_INDEX) {
                                ((MyApplyFragment) t).scrollMyListViewToTop();
                            }

                            ((MyApplyFragment) t).lv.setAdapter(((MyApplyFragment) t).myApplyAdapter = new CommonAdapter<ApplyBean.DataBean>(
                                    t.getActivity().getApplicationContext(), ((MyApplyFragment) t).myApplys, R.layout.item_fragment_myapply
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, ApplyBean.DataBean item) {
                                    TextView stateTv = viewHolder.getView(R.id.item_fragment_myapply_state_tv);
                                    if (item.getStatus() == 3) {//0:处理中,1:处理完成（通过）,3:被驳回
                                        stateTv.setBackgroundColor(t.getActivity().getResources().getColor(R.color.red));
                                    } else if (item.getStatus() == 1) {
                                        stateTv.setBackgroundColor(t.getActivity().getResources().getColor(R.color.green));
                                    } else {
                                        stateTv.setBackgroundColor(t.getActivity().getResources().getColor(R.color.gray));
                                    }
                                    TextView nameTv = viewHolder.getView(R.id.item_fragment_myapply_name_tv);
                                    nameTv.setText(item.getName());
                                    nameTv.setTextColor(t.getActivity().getResources().getColor(R.color.gray));

                                    TextView dateTv = viewHolder.getView(R.id.item_fragment_myapply_date_tv);
                                    dateTv.setTextColor(t.getActivity().getResources().getColor(R.color.gray));
                                    dateTv.setText(item.getAdd_time());
                                }
                            });

                            ((MyApplyFragment) t).lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToApplyDetailBean> wf = new WeakReference<ToApplyDetailBean>(new ToApplyDetailBean());
                                    wf.get().setId(((MyApplyFragment) t).myApplys.get(position).getId());
                                    Intent intent = new Intent(t.getActivity(), ExamineStatusActivity.class);
                                    intent.putExtra(Constants.KEY_TOMYAPPLYDETAIL, wf.get());
                                    t.getActivity().startActivity(intent);
                                }
                            });

                            if (null != ((MyApplyFragment)t).onRefreshLister.getPullToRefreshLayout()) {
                                if (((MyApplyFragment)t).pageIndex == Constants.PAGE_START_INDEX) {
                                    ((MyApplyFragment)t).onRefreshLister.getPullToRefreshLayout().refreshFinish(PullToRefreshRelativeLayout.SUCCEED);
                                } else if (((MyApplyFragment)t).pageIndex > Constants.PAGE_START_INDEX) {
                                    ((MyApplyFragment)t).onRefreshLister.getPullToRefreshLayout().loadmoreFinish(PullToRefreshRelativeLayout.SUCCEED);
                                }

                                if (((MyApplyFragment)t).myApplyAdapter != null) {
                                    ((MyApplyFragment)t).myApplyAdapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emptyLayoutShowOrHide(((MyApplyFragment) t).emptyLayout, false);
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private String type;
    private View rootView;
    private MyApplyFragment fragment;
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
            emptyLayout =  rootView.findViewById(R.id.emptyLayout);
            refreshView =  rootView.findViewById(R.id.refresh_view);
            lv =  rootView.findViewById(R.id.fragment_rsnews_item_lv);
            isPrepare = true;

            onRefreshLister = new MyOnRefreshLister();
            refreshView.setOnRefreshListener(onRefreshLister);


            if (!TextUtils.isEmpty(type) && type.equals(Constants.APPLY_MYAPPLY)) {
                if (null != myApplys) {
                    myApplys.clear();
                }
            }
            getData();
        }


        return rootView;
    }

    private boolean isPrepare = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {

            pageIndex = Constants.PAGE_START_INDEX;
            if (!TextUtils.isEmpty(type) && type.equals(Constants.APPLY_MYAPPLY)) {
                if (null != myApplys) {
                    myApplys.clear();
                }
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
                if (!TextUtils.isEmpty(type) && type.equals(Constants.APPLY_MYAPPLY)) {
                    lv.setSelection(myApplyAdapter.getCount() - 1);
                }
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
        if (!TextUtils.isEmpty(type) && type.equals(Constants.APPLY_MYAPPLY)) {
            url = UrlManager.URL_MYAPPLY;
        } else {
            url = UrlManager.URL_MYJOB;
            map.put("page", pageIndex + "");
        }
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
                                if (type.equals(Constants.APPLY_MYAPPLY)) {
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

            if (null == myApplys) {
                myApplys = new ArrayList<>();
            }

            if (null == myApplys){
                myApplys = new ArrayList<>();
            }

            if (!TextUtils.isEmpty(type) && type.equals(Constants.APPLY_MYAPPLY)) {
                if (null != myApplys) {
                    myApplys.clear();
                }
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