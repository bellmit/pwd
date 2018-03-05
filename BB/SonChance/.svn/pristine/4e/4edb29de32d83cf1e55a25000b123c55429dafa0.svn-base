package com.tastebychance.sonchance.homepage.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tastebychance.sonchance.BaseFragmentActivity;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.homepage.locate.SearchEditText;
import com.tastebychance.sonchance.homepage.order.OrderActivity;
import com.tastebychance.sonchance.homepage.search.bean.DishesSearchInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DishesSearchActivity extends MyBaseActivity{
    private ImageView head_left_iv;
    private SearchEditText filter_edit;
    private ListView category_listview;
    private RelativeLayout root_layout;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dishes_search);
        bindViews();
        setListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindViews() {
        head_left_iv = (ImageView) findViewById(R.id.head_left_iv);
        filter_edit = (SearchEditText) findViewById(R.id.filter_edit);
        category_listview = (ListView) findViewById(R.id.category_listview);
        root_layout = (RelativeLayout) findViewById(R.id.root_layout);

        head_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DishesSearchActivity.this.finish();
            }
        });
    }

    private void setListener() {
        filter_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString())) {
//                    listview_empty_ll.setVisibility(View.GONE);
                    category_listview.setVisibility(View.GONE);
                } else {
                    refreshData(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        category_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mDatas && mDatas.size() > position) {
                    Intent intent = new Intent(DishesSearchActivity.this, OrderActivity.class);
                    intent.putExtra("dishes_id", mDatas.get(position).getId() + "");
                    startActivity(intent);
                    DishesSearchActivity.this.finish();
                }
            }
        });

        root_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int offset = root_layout.getRootView().getHeight() - root_layout.getHeight();
                if (offset > 100){

                }
            }
        });
    }

    /**
     * 获取搜索菜品数据
     */
    private List<DishesSearchInfo> mDatas;

    private void refreshData(String keyWord) {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_HOME_SEARCH;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token)
                .add("keyword", keyWord).build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
                             e.printStackTrace();
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
                             try {
                                 String str = response.body().string();
                                 Log.i(Constants.TAG, str);

                                 ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                                 if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                                     Message msg = new Message();
                                     msg.what = INFO_WHAT;
                                     msg.obj = GETDATA_SUCCESS;
                                     myHandler.sendMessage(msg);

                                     mDatas = resInfo.getDataList(DishesSearchInfo.class);

                                     runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             updateListData(mDatas);
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
                     }
        );
    }

    /**
     * 更新列表数据
     *
     * @param mDatas
     */
    private CommonAdapter<DishesSearchInfo> adapter;

    private void updateListData(List<DishesSearchInfo> mDatas) {
        if (null == mDatas || mDatas.size() <= 0) {
//            listview_empty_ll.setVisibility(View.VISIBLE);
            category_listview.setVisibility(View.GONE);
        } else {
//            listview_empty_ll.setVisibility(View.GONE);
            category_listview.setVisibility(View.VISIBLE);
            category_listview.setAdapter(adapter = new CommonAdapter<DishesSearchInfo>(
                    getApplicationContext(), mDatas, R.layout.home_dishes_search_listitem
            ) {
                @Override
                protected void convert(ViewHolder viewHolder, DishesSearchInfo item) {
                    viewHolder.setText(R.id.home_dishes_search_listitem_tv, item.getName());
                }
            });
        }
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DishesSearch Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
