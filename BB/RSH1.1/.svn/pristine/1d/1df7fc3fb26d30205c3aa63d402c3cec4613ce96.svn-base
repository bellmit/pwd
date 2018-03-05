package rongshanghui.tastebychance.com.rongshanghui.home.sht;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.bean.AscriptionChildRes;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.register.adapter.AscriptionGroupAdapter;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：ItemSHFragment商会
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/21 16:37
 * 修改人：
 * 修改时间：2017/11/21 16:37
 * 修改备注：
 *
 * @version 1.0
 */
public class ItemSHFragment extends MyBaseFragment {
    private String type;
    private TextView sht_domestic_tv;
    private TextView sht_international_tv;
    private ListView shtGroupLv;
    private ListView shtChildLv;

    private boolean isPrepared;
    private AscriptionGroupAdapter groupAdapter;
    private CommonAdapter<AscriptionChildRes.DataBean> childAdapter;
    private List<RegionRes.DataBean> groupDatas = new ArrayList<>();
    private List<AscriptionChildRes.DataBean> childDatas = new ArrayList<>();
    private boolean isInternational = false;//国内，国际

    @Override
    public void onAttach(Context context) {
        getGroupData();
        super.onAttach(context);
    }

    private String selectedGroupItemId;
    private static final int GROUP_WHAT = 1000;
    private static final int CHILD_WHAT = 1001;

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
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case GROUP_WHAT:
                            ((ItemSHFragment) t).groupDatas = resInfo.getDataList(RegionRes.DataBean.class);

                            ((ItemSHFragment) t).groupAdapter = new AscriptionGroupAdapter(t.getActivity(), ((ItemSHFragment) t).groupDatas);
                            ((ItemSHFragment) t).shtGroupLv.setAdapter(((ItemSHFragment) t).groupAdapter);

                            if (((ItemSHFragment) t).groupDatas != null && ((ItemSHFragment) t).groupDatas.size() > 0) {
                                ((ItemSHFragment) t).getChildData(((ItemSHFragment) t).groupDatas.get(0).getRegion_id());
                                ((ItemSHFragment) t).selectedGroupItemId = ((ItemSHFragment) t).groupDatas.get(0).getRegion_id();
                                //点击变颜色
                                if (null != ((ItemSHFragment) t).groupAdapter) {
                                    ((ItemSHFragment) t).groupAdapter.setSelectItem(0);
                                    ((ItemSHFragment) t).groupAdapter.notifyDataSetChanged();
                                }
                            }

                            ((ItemSHFragment) t).shtGroupLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //点击变颜色
                                    if (null != ((ItemSHFragment) t).groupAdapter) {
                                        ((ItemSHFragment) t).groupAdapter.setSelectItem(position);
                                        ((ItemSHFragment) t).groupAdapter.notifyDataSetChanged();
                                    }
                                    //点击左侧某一项，获取右侧明细数据
                                    if (((ItemSHFragment) t).groupDatas != null && position < ((ItemSHFragment) t).groupDatas.size()) {
                                        ((ItemSHFragment) t).getChildData(((ItemSHFragment) t).groupDatas.get(position).getRegion_id());
                                        ((ItemSHFragment) t).selectedGroupItemId = ((ItemSHFragment) t).groupDatas.get(position).getRegion_id();
                                    }
                                }
                            });

                            break;
                        case CHILD_WHAT:
                            ((ItemSHFragment) t).childDatas = resInfo.getDataList(AscriptionChildRes.DataBean.class);

                            ((ItemSHFragment) t).shtChildLv.setAdapter(((ItemSHFragment) t).childAdapter = new CommonAdapter<AscriptionChildRes.DataBean>(t.getActivity().getApplicationContext(),
                                    ((ItemSHFragment) t).childDatas, R.layout.item_sht_sh_ascription_child) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final AscriptionChildRes.DataBean item) {
                                    viewHolder.setText(R.id.sht_sh_ascription_child_name_tv, item.getUnit_name());
                                    TextView sht_sh_ascription_child_follow_tv = viewHolder.getView(R.id.sht_sh_ascription_child_follow_tv);
                                    if (item.getIs_cared() == 0) {//是否已关注 0 ： 未关注 1 ：已关注
                                        sht_sh_ascription_child_follow_tv.setText("[加关注]");
                                        sht_sh_ascription_child_follow_tv.setTextColor(t.getActivity().getResources().getColor(R.color.font_blue));
                                        sht_sh_ascription_child_follow_tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //加关注
                                                ((ItemSHFragment) t).userCare(item.getId() + "", true);
                                            }
                                        });
                                    } else {
                                        sht_sh_ascription_child_follow_tv.setText("[已关注]");
                                        sht_sh_ascription_child_follow_tv.setTextColor(t.getActivity().getResources().getColor(R.color.font_gray));

                                        sht_sh_ascription_child_follow_tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //取消关注
                                                ((ItemSHFragment) t).userCare(item.getId() + "", false);
                                            }
                                        });
                                    }
                                }
                            });

                            ((ItemSHFragment) t).shtChildLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    WeakReference<ToDetailBean> wf = new WeakReference<ToDetailBean>(new ToDetailBean());
                                    wf.get().setTitle(((ItemSHFragment) t).childDatas.get(position).getUnit_name());
                                    wf.get().setId(((ItemSHFragment) t).childDatas.get(position).getId());
                                    wf.get().setIsCared(((ItemSHFragment) t).childDatas.get(position).getIs_cared());
                                    wf.get().setToType(SystemUtil.getInstance().castTypeToValue("商会"));
                                    SystemUtil.getInstance().intentToDetail(t.getActivity(), wf.get());
                                }
                            });
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    EditText shtSearchEdt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        type = mBundle.getString("type");

        groupDatas = new ArrayList<>();
        childDatas = new ArrayList<>();

        View contextView = inflater.inflate(R.layout.fragment_sht_sh, container, false);
        sht_domestic_tv = (TextView) contextView.findViewById(R.id.sht_domestic_tv);
        sht_international_tv = (TextView) contextView.findViewById(R.id.sht_international_tv);

        shtSearchEdt = (EditText) contextView.findViewById(R.id.sht_search_edt);
        shtGroupLv = (ListView) contextView.findViewById(R.id.sht_group_lv);
        shtChildLv = (ListView) contextView.findViewById(R.id.sht_child_lv);

        if (null != shtSearchEdt) {
            shtSearchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //先隐藏软键盘
                        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        search(shtSearchEdt.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
        }

        return contextView;
    }


    @Override
    public void onResume() {
        super.onResume();
        isInternational = false;
        getGroupData();

        if (null != sht_domestic_tv) {
            sht_domestic_tv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));
            sht_domestic_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sht_domestic_tv.setTextColor(getActivity().getResources().getColor(R.color.font_blue));
                    if (null != sht_international_tv) {
                        sht_international_tv.setTextColor(getActivity().getResources().getColor(R.color.font_gray));
                    }

                    isInternational = false;
                    getGroupData();
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

                    isInternational = true;
                    getGroupData();
                }
            });
        }
    }

    /**
     * 对关键字进行搜索
     *
     * @param s
     */
    private void search(String s) {
        if (StringUtil.isEmpty(s)) {
            return;
        }

        getChildData(null);
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
     * 获取左侧列表数据
     */
    private void getGroupData() {
        if (Constants.IS_LOCALDATA) {
        } else {
            getGroupDataFromServer("");
        }
    }

    /**
     * is_international 	是 	int 	0 国内 1 国际
     * parent_id 	否 	int 	根据父地址查子地址列表
     */
    private void getGroupDataFromServer(String region_id) {
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
        final String url = UrlManager.URL_REGION;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("is_international", isInternational ? "1" : "0");
        if (StringUtil.isNotEmpty(region_id)) {
            builder.add("parent_id", region_id);
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
                                msg.what = GROUP_WHAT;
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

    /**
     * 获取右侧明细数据
     */
    private String ascriptionChildStr;

    private void getChildData(String regionId) {
        //TODO:具体的商会（带是否关注）
        if (Constants.IS_LOCALDATA) {
            ascriptionChildStr = "    {\n" +
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
            getChildDataFromServer(regionId);
        }
    }

    /**
     * 获取关键字
     *
     * @return
     */
    private String getKeyWord() {
        String keyWord = null;
        if (null != shtSearchEdt) {
            keyWord = shtSearchEdt.getText().toString();
        }

        return keyWord;
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
                                if (StringUtil.isNotEmpty(selectedGroupItemId)) {
                                    getChildData(selectedGroupItemId + "");
                                }
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

    /**
     * token 	是 	string 	token值
     * region_id 	否 	int 	当需要根据地区查询列表时传
     * subject_id 	是 	int 	1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
     * keyword 	否 	string 	需要关键词搜索时传
     */
    private void getChildDataFromServer(String regionId) {
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
        if (StringUtil.isNotEmpty(regionId)) {
            builder.add("region_id", regionId);
        }
        Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        builder.add("subject_id", SystemUtil.getInstance().castTypeToValue(type) + "");
        if (StringUtil.isNotEmpty(getKeyWord())) {
            builder.add("keyword", getKeyWord());
        }
        builder.add("is_international", isInternational ? "1" : "0");

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
                                msg.what = CHILD_WHAT;
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
}