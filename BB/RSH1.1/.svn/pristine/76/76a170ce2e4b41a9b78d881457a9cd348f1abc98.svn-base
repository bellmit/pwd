package rongshanghui.tastebychance.com.rongshanghui.home.search;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.HashMap;
import java.util.List;

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
import rongshanghui.tastebychance.com.rongshanghui.home.search.bean.UserListBean;
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
 * 类描述：ItemUserFragment 首页-搜索-通讯录
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/2 16:58
 * 修改人：
 * 修改时间：2017/12/2 16:58
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemUserFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {
    private List<UserListBean> userList = new ArrayList<>();
    private PullRefreshPushAddListView pullrefreshlistview;
    private View rootView;
    private ItemUserFragment fragment;
    private CommonAdapter<UserListBean> adapter;

    private String type;
    private int pageIndex = Constants.PAGE_START_INDEX;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends android.support.v4.app.Fragment> extends Handler {
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
                            ((ItemUserFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ItemUserFragment) t).adapter.notifyDataSetChanged();
                            ((ItemUserFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ItemUserFragment) t).pageIndex;
                            ((ItemUserFragment) t).adapter.notifyDataSetChanged();
                            ((ItemUserFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            ((ItemUserFragment) t).updateData(resInfo);
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

    public void updateData(ResInfo resInfo) {
        if (null != resInfo) {
            userList.addAll(resInfo.getListByKey("user_list", UserListBean.class));
        }
        if (null == pullrefreshlistview && null != rootView) {
            pullrefreshlistview = (PullRefreshPushAddListView) rootView.findViewById(R.id.fragment_rsnews_item_lv);
            pullrefreshlistview.setPullLoadEnable(false);
            pullrefreshlistview.setXListViewListener(this);
        }
        if (null != pullrefreshlistview) {
            pullrefreshlistview.setAdapter(adapter = new CommonAdapter<UserListBean>(
                    getActivity().getApplicationContext(), userList, R.layout.item_cardclip
            ) {
                @Override
                protected void convert(ViewHolder viewHolder, UserListBean item) {
                    PicassoUtils.getinstance().loadNormalByPath(getActivity(), item.getAvatar(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                    TextView isFriendTv = viewHolder.getView(R.id.twolevellist_child_isfriend_tv);
                    if (item.getIs_friend() == 0) {
                        isFriendTv.setVisibility(View.VISIBLE);
                    } else {
                        isFriendTv.setVisibility(View.GONE);
                    }
                    viewHolder.setText(R.id.twolevellist_child_title_tv, item.getUser_nickname());
                    TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                    tv.setText(item.getUnit_name());
                    tv.setTextColor(getResources().getColor(R.color.font_blue));
                }
            });

            pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO:跳转
                    System.out.println("parent = " + parent);
                }
            });
        }
    }

    private IntentFilter intentFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        intentFilter = new IntentFilter();
        intentFilter.addAction(HomeSearchActivity.SEARCH_USER_ACTION);
        getActivity().registerReceiver(userBroadcastReceiver, intentFilter);

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        Bundle mBundle = getArguments();
        type = mBundle.getString("type");
        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullLoadEnable(false);
            pullrefreshlistview.setXListViewListener(this);
        }
        rootView = contextView;
        fragment = this;

        return contextView;
    }

    private void onLoad() {
        pullrefreshlistview.stopRefresh();
        pullrefreshlistview.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        pullrefreshlistview.setRefreshTime(date);
    }

    BroadcastReceiver userBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("SystemUtil.getInstance().getStrFromSP(\"keyword\") = " + SystemUtil.getInstance().getStrFromSP("keyword"));
            if (null != userList) {
                userList.clear();
            }
            getDataFromServer();
        }
    };

    /**
     * keyword 	是 	string 	关键词搜索
     * token 	否 	string 	token
     * URL_HOME_INDEXSEARCH
     */
    private void getDataFromServer() {
        if (StringUtil.isEmpty(SystemUtil.getInstance().getStrFromSP("keyword"))) {
            ToastUtils.showOneToast(getActivity().getApplicationContext(), "请输入要搜索的关键字");
            return;
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(getActivity().findViewById(R.id.home_search_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_INDEXSEARCH;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("keyword", SystemUtil.getInstance().getStrFromSP("keyword"));
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });

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
    public void onDestroy() {
        super.onDestroy();
        if (null != userBroadcastReceiver) {
            getActivity().unregisterReceiver(userBroadcastReceiver);
        }
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != userList) {
            userList.clear();
        }
        getDataFromServer();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        getDataFromServer();
        onLoad();
    }
}