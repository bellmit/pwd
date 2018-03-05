package rongshanghui.tastebychance.com.rongshanghui.home.sht;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.AscriptionChildRes;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;


public class ItemDepartmentFragment extends MyBaseFragment implements PullRefreshPushAddListView.IXListViewListener {

    private CommonAdapter<AscriptionChildRes.DataBean> adapter;
    private PullRefreshPushAddListView pullrefreshlistview;
    private EditText searchEdt;

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
                            ((ItemDepartmentFragment) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((ItemDepartmentFragment) t).adapter.notifyDataSetChanged();
                            ((ItemDepartmentFragment) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((ItemDepartmentFragment) t).pageIndex;
                            ((ItemDepartmentFragment) t).adapter.notifyDataSetChanged();
                            ((ItemDepartmentFragment) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;

                            //					List<AscriptionChildRes.DataBean> items = new ArrayList<AscriptionChildRes.DataBean>();
                            //					AscriptionChildRes ascriptionChildRes = ascriptionChildRes(resStr);
                            //					if (null != ascriptionChildRes){
                            //						if (ascriptionChildRes.getCode() == Constants.RESULT_SUCCESS){
                            //							items.clear();
                            //							items.addAll(ascriptionChildRes.getData());
                            //						}
                            //					}

                            if (((ItemDepartmentFragment) t).emptyLayout != null) {
                                if (((ItemDepartmentFragment) t).pageIndex == Constants.PAGE_START_INDEX && (resInfo.getDataList(AscriptionChildRes.DataBean.class) == null || resInfo.getDataList(AscriptionChildRes.DataBean.class).size() == 0)) {
                                    emptyLayoutShowOrHide(((ItemDepartmentFragment) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((ItemDepartmentFragment) t).emptyLayout, false);
                                }
                            }
                            final List<AscriptionChildRes.DataBean> items = resInfo.getDataList(AscriptionChildRes.DataBean.class);

                            ((ItemDepartmentFragment) t).pullrefreshlistview = (PullRefreshPushAddListView) ((ItemDepartmentFragment) t).rootView.findViewById(R.id.fragment_rsnews_item_lv);
                            ((ItemDepartmentFragment) t).pullrefreshlistview.setAdapter(((ItemDepartmentFragment) t).adapter = new CommonAdapter<AscriptionChildRes.DataBean>(
                                    ((ItemDepartmentFragment) t).getActivity().getApplicationContext(), items, R.layout.item_sht_department
                            ) {
                                @Override
                                protected void convert(final ViewHolder viewHolder, final AscriptionChildRes.DataBean item) {
                                    viewHolder.setText(R.id.item_name_tv, item.getUnit_name());
                                    TextView sht_sh_ascription_child_follow_tv = viewHolder.getView(R.id.item_sht_department_follow_tv);
                                    if (item.getIs_cared() == 0) {//是否已关注 0 ： 未关注 1 ：已关注
                                        sht_sh_ascription_child_follow_tv.setText("[加关注]");
                                        sht_sh_ascription_child_follow_tv.setTextColor(t.getActivity().getResources().getColor(R.color.font_blue));
                                        sht_sh_ascription_child_follow_tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //加关注
                                                ((ItemDepartmentFragment) t).userCare(item.getId() + "", true);
                                            }
                                        });
                                    } else {
                                        sht_sh_ascription_child_follow_tv.setText("[已关注]");
                                        sht_sh_ascription_child_follow_tv.setTextColor(t.getActivity().getResources().getColor(R.color.font_gray));

                                        sht_sh_ascription_child_follow_tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //										ToastUtils.showOneToast(getActivity().getApplicationContext(),"已关注");
                                                //取消关注
                                                ((ItemDepartmentFragment) t).userCare(item.getId() + "", false);
                                            }
                                        });
                                    }

                                    RelativeLayout rl = viewHolder.getView(R.id.item_sht_mine_rootlayout);
                                    rl.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                            wf.get().setTitle(item.getUnit_name());
                                            wf.get().setId(item.getId());
                                            wf.get().setIsCared(item.getIs_cared());
                                            wf.get().setToType(SystemUtil.getInstance().castTypeToValue(((ItemDepartmentFragment) t).type));//部门、机构、学校、医院、其他
                                            SystemUtil.getInstance().intentToDetail(t.getActivity(), wf.get());
                                        }
                                    });
                                }
                            });
                            ((ItemDepartmentFragment) t).pullrefreshlistview.setPullLoadEnable(false);
                            ((ItemDepartmentFragment) t).pullrefreshlistview.setXListViewListener(((ItemDepartmentFragment) t).fragment);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isPrepared = true;
            getData();
        } else {
            if (isPrepared) {
                isPrepared = false;
            }
        }
    }

    private ItemDepartmentFragment fragment;
    private View rootView;
    private EmptyLayout emptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = this;

        View contextView = inflater.inflate(R.layout.fragment_department, container, false);
        rootView = contextView;

        emptyLayout = (EmptyLayout) contextView.findViewById(R.id.emptyLayout);

        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        //搜索
        searchEdt = (EditText) contextView.findViewById(R.id.sht_search_edt);
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //先隐藏软键盘
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    search(searchEdt.getText().toString());
                    return true;
                }
                return false;
            }
        });


        return contextView;
    }

    //	搜索
    private void search(String s) {
        if (StringUtil.isEmpty(s)) {
            return;
        }
        getDataFromServer(type);
    }

    private String resStr;

    private void getData() {
        if (Constants.IS_LOCALDATA) {
            resStr = "       {\n" +
                    "      \"code\": \"0\",\n" +
                    "      \"data\": [\n" +
                    "        {\n" +
                    "          \"unit_name\": \"工商银行\",\n" +
                    "          \"id\": 3,\n" +
                    "          \"is_cared\": 1\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"unit_name\": \"福州商会\",\n" +
                    "          \"id\": 2,\n" +
                    "          \"is_cared\": 0\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }";
        } else {
            getDataFromServer(type);
        }
    }

    private String getKeyWord() {
        String keyWord = null;
        if (null != searchEdt) {
            keyWord = searchEdt.getText().toString();
        }

        return keyWord;
    }

    /**
     * @param typeValue 1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
     */
    private void getDataFromServer(String typeValue) {
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
        final String url = UrlManager.URL_HOME_SHT_GETSUBJECTLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();


        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("subject_id", SystemUtil.getInstance().castTypeToValue(type) + "");
        if (StringUtil.isNotEmpty(getKeyWord())) {
            builder.add("keyword", getKeyWord());
        }
        builder.add("is_international", "0");

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
                .add("type", isAddCare ? "1" : "2")
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
                                getDataFromServer(type);
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

    private AscriptionChildRes ascriptionChildRes(String resStr) {
        if (StringUtil.isEmpty(resStr)) {
            if (Constants.IS_DEVELOPING) {
//				throw new IllegalArgumentException(this.getClass().getSimpleName()+"获取到的str为空");
            }
            return null;
        }
        return JSONObject.parseObject(resStr, AscriptionChildRes.class);
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
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
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