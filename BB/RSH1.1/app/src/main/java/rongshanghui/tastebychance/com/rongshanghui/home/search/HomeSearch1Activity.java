package rongshanghui.tastebychance.com.rongshanghui.home.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.search.bean.PostListBean;
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
 * 类描述：RSNewsSearchActivity 融尚新闻搜索
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/20 15:09
 * 修改人：
 * 修改时间：2017/11/20 15:09
 * 修改备注：
 *
 * @version 1.0
 */
public class HomeSearch1Activity extends MyBaseActivity implements PullRefreshPushAddListView.IXListViewListener {

    private EditText searchEdt;
    private TextView searchCancelTv;
    private ImageView searchIconIv;
    private PullRefreshPushAddListView searchLv;


    private CommonAdapter<PostListBean.DataBean> adapter;
    private List<PostListBean.DataBean> list = new ArrayList<>();
    private int pageIndex = Constants.PAGE_START_INDEX;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
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
                            ((HomeSearch1Activity) t).pageIndex = Constants.PAGE_START_INDEX;
                            ((HomeSearch1Activity) t).adapter.notifyDataSetChanged();
                            ((HomeSearch1Activity) t).onLoad();
                            break;
                        case Constants.WHAT_LOADMORE:
                            ++((HomeSearch1Activity) t).pageIndex;
                            ((HomeSearch1Activity) t).adapter.notifyDataSetChanged();
                            ((HomeSearch1Activity) t).onLoad();
                            break;
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            ((HomeSearch1Activity) t).updateData(resInfo);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search1);

        initView();

        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    getDataFromServer();

                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        searchEdt = (EditText) findViewById(R.id.search_edt);
        searchCancelTv = (TextView) findViewById(R.id.search_cancel_tv);
        searchIconIv = (ImageView) findViewById(R.id.search_icon_iv);
        searchLv = (PullRefreshPushAddListView) findViewById(R.id.search_lv);

        searchCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSearch1Activity.this.finish();
            }
        });
    }

    public void updateData(ResInfo resInfo) {
        if (null != resInfo) {
            list.addAll(resInfo.getClassByKey("post_list", PostListBean.class).getData());
        }
        if (null == searchLv) {
            searchLv.setPullLoadEnable(false);
            searchLv.setXListViewListener(this);
        }
        if (null != searchLv) {
            searchLv.setAdapter(adapter = new CommonAdapter<PostListBean.DataBean>(
                    getApplicationContext(), list, R.layout.item_rsnewslist
            ) {
                @Override
                protected void convert(ViewHolder viewHolder, PostListBean.DataBean item) {
                    PicassoUtils.getinstance().loadNormalByPath(HomeSearch1Activity.this, item.getM_post_image1(), (ImageView) viewHolder.getView(R.id.item_yjzq_hf_img_iv));
                    viewHolder.setText(R.id.twolevellist_child_title_tv, item.getPost_title());
                    viewHolder.setText(R.id.twolevellist_child_pagerview_tv, item.getPost_hits());
                    TextView tv = viewHolder.getView(R.id.twolevellist_child_source_tv);
                    tv.setText(item.getUnit_name());
                    tv.setTextColor(getResources().getColor(R.color.font_blue));
                }
            });

            searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                    wf.get().setId(list.get(position - 1).getId());
                    wf.get().setTitle("信息");
                    wf.get().setUrl(list.get(position - 1).getDetail());
                    wf.get().setIsCollect(list.get(position - 1).getIs_collect());
                    wf.get().setIsLike(list.get(position - 1).getIs_like());

                    wf.get().setShareTitle(list.get(position - 1).getPost_title());
                    wf.get().setShareImgUrl(list.get(position - 1).getM_post_image1());
                    wf.get().setShareUrl(list.get(position - 1).getDetail());
                    SystemUtil.getInstance().intentToWebActivity(HomeSearch1Activity.this, wf.get());
                }
            });
        }
    }

    /**
     * keyword 	是 	string 	关键词搜索
     * token 	否 	string 	token
     * URL_HOME_INDEXSEARCH
     */
    private void getDataFromServer() {
        if (StringUtil.isEmpty(searchEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入要搜索的关键字");
            return;
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.home_search_rootlayout), new CustomLoadingFactory());
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
        builder.add("keyword", searchEdt.getText().toString());
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

                        runOnUiThread(new Runnable() {
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

    private void onLoad() {
        searchLv.stopRefresh();
        searchLv.stopLoadMore();
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
        searchLv.setRefreshTime(date);
    }

    @Override
    public void onListViewRefresh() {
        pageIndex = Constants.PAGE_START_INDEX;
        if (null != list) {
            list.clear();
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
