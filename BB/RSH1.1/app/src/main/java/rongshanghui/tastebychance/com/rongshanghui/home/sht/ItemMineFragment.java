package rongshanghui.tastebychance.com.rongshanghui.home.sht;

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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.SHTMineRes;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
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
 * 类描述：ItemMineFragment我的
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/21 16:39
 * 修改人：
 * 修改时间：2017/11/21 16:39
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemMineFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private CommonAdapter<SHTMineRes.DataBean> adapter;
    private PullRefreshPushAddListView pullrefreshlistview;

    private List<SHTMineRes.DataBean> dataBeen = new ArrayList<>();
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
                            ((ItemMineFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ItemMineFragment) t).adapter.notifyDataSetChanged();
                            ((ItemMineFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ItemMineFragment) t).pageIndex;
                            ((ItemMineFragment) t).adapter.notifyDataSetChanged();
                            ((ItemMineFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:

                            ResInfo resInfo = (ResInfo) msg.obj;

                            if (((ItemMineFragment) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getDataList(SHTMineRes.DataBean.class).size() == 0) {
                                ToastUtils.showOneToast(t.getActivity().getApplicationContext(), "没有更多了");
                                emptyLayoutShowOrHide(((ItemMineFragment) t).emptyLayout, false);
                                return;
                            }

                            if (((ItemMineFragment) t).pageIndex > Constants.PAGE_START_INDEX) {
                                ((ItemMineFragment) t).scrollMyListViewToBottom();
                            }

                            if (((ItemMineFragment) t).emptyLayout != null) {
                                if (((ItemMineFragment) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getDataList(SHTMineRes.DataBean.class) == null || resInfo.getDataList(SHTMineRes.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((ItemMineFragment) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((ItemMineFragment) t).emptyLayout, false);
                                }
                            }

                            ((ItemMineFragment) t).dataBeen.addAll(resInfo.getDataList(SHTMineRes.DataBean.class));

                            ((ItemMineFragment) t).pullrefreshlistview = (PullRefreshPushAddListView) ((ItemMineFragment) t).rootView.findViewById(R.id.fragment_rsnews_item_lv);
                            ((ItemMineFragment) t).adapter = new CommonAdapter<SHTMineRes.DataBean>(
                                    t.getActivity().getApplicationContext(), ((ItemMineFragment) t).dataBeen, R.layout.item_sht_mine
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final SHTMineRes.DataBean item) {
                                    RoundImageView rtv = viewHolder.getView(R.id.item_head_rtv);
                                    PicassoUtils.getinstance().loadCircleImage(t.getActivity().getApplicationContext(), item.getAvatar(), rtv, 31);
                                    viewHolder.setText(R.id.item_name_tv, item.getUnit_name());
                                    TextView followTv = viewHolder.getView(R.id.item_adopt_tv);

                                    followTv.setText("[取消关注]");
                                    followTv.setTextColor(t.getActivity().getResources().getColor(R.color.light_blue));

                                    followTv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //取消关注的网络请求（请求成功后刷新界面）
                                            DialogUtils.getInstance().AlertMessage(t.getActivity(), "", "确定取消关注", "容我三思", "心意已决", null, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ((ItemMineFragment) t).userCare(item.getId());
                                                }
                                            });
                                        }
                                    });
                                }
                            };
                            ((ItemMineFragment) t).pullrefreshlistview.setAdapter(((ItemMineFragment) t).adapter);
                            ((ItemMineFragment) t).pullrefreshlistview.setPullLoadEnable(false);
                            ((ItemMineFragment) t).pullrefreshlistview.setPullRefreshEnable(false);
                            ((ItemMineFragment) t).pullrefreshlistview.setXListViewListener(((ItemMineFragment) t).fragment);

                            ((ItemMineFragment) t).pullrefreshlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                    wf.get().setId(Integer.valueOf(((ItemMineFragment) t).dataBeen.get(position - 1).getId()));
                                    wf.get().setTitle(((ItemMineFragment) t).dataBeen.get(position - 1).getUnit_name());
                                    wf.get().setIsCared(1);//是否已关注 0 ： 未关注 1 ：已关注
                                    wf.get().setToType(((ItemMineFragment) t).dataBeen.get(position - 1).getSubject_type());
                                    SystemUtil.getInstance().intentToDetail(t.getActivity(), wf.get());
                                }
                            });
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
    private ItemMineFragment fragment;
    private EmptyLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        rootView = contextView;
        fragment = this;

        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);
        pullrefreshlistview = (PullRefreshPushAddListView) contextView.findViewById(R.id.fragment_rsnews_item_lv);
        if (null != pullrefreshlistview) {
            pullrefreshlistview.setPullLoadEnable(false);
            pullrefreshlistview.setPullRefreshEnable(false);
            pullrefreshlistview.setXListViewListener(this);
        }

        isPrepared = true;
        return contextView;
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != dataBeen) {
            dataBeen.clear();
        }
        getData();
    }

    private boolean isPrepared = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepared) {
            if (null != dataBeen) {
                dataBeen.clear();
            }
            getData();
        } else {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepared = false;
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

    /**
     * token 	是 	string 	token值
     * user_id 	是 	int 	被关注的用户id
     * type 	是 	int 	1 关注 2 取消关注
     */
    private void userCare(String userId) {
        if (null == loadingBar) {
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
        final String url = UrlManager.URL_USERCARER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("user_id", userId)
                .add("type", "2")
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

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != dataBeen) {
                                    dataBeen.clear();
                                }
                                getDataFromServer();
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


    private String resStr;

    private void getData() {
        if (Constants.IS_LOCALDATA) {
            resStr = "    {\n" +
                    "    \"code\": 0,\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "          \"id\": \"1\",\n" +
                    "          \"unit_name\": \"单位名称\",\n" +
                    "          \"avatar\": \"单位头像\",\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": \"1\",\n" +
                    "          \"unit_name\": \"单位名称\",\n" +
                    "          \"avatar\": \"单位头像\",\n" +
                    "        },\n" +
                    "    ]\n" +
                    "  }";
        } else {
            getDataFromServer();
        }
    }

    /**
     * URL_HOME_SHT_CARE
     */
    private void getDataFromServer() {
        if (null == loadingBar) {
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
        final String url = UrlManager.URL_HOME_SHT_CARE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
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