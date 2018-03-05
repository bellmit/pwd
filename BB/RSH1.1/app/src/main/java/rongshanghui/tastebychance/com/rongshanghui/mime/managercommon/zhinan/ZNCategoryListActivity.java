package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonChoseAbleAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.SubjectCount;
import rongshanghui.tastebychance.com.rongshanghui.mime.jgmanager.JGManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCategoryBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;

/**
 * 类描述：ZNCategoryListActivity 我的-管理-指南管理-类别列表页
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 16:16
 * 修改人：
 * 修改时间：2017/12/7 16:16
 * 修改备注：
 *
 * @version 1.0
 */
public class ZNCategoryListActivity extends MyAppCompatActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_zncategory_add_mlv)
    MyListView activityZncategoryAddMLv;
    @BindView(R.id.activity_zncategory_add_tv)
    TextView activityZncategoryAddTv;
    @BindView(R.id.content_zncategory)
    RelativeLayout contentZncategory;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_zncategorylist_rootlayout)
    CoordinatorLayout activityZncategoryListRootlayout;

//    activityZncategoryAddMLv

    private CommonChoseAbleAdapter<ZNCategoryBean> adapter;
    private List<ZNCategoryBean> list = new ArrayList<>();
    private ZNCategoryBean chosedZNCategoryBean;

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
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            ((ZNCategoryListActivity) t).list = resInfo.getDataList(ZNCategoryBean.class);
                            ((ZNCategoryListActivity) t).activityZncategoryAddMLv.setAdapter(((ZNCategoryListActivity) t).adapter = new CommonChoseAbleAdapter<ZNCategoryBean>(
                                    t.getApplicationContext(), ((ZNCategoryListActivity) t).list, R.layout.item_zncategory, R.id.activity_zncategory_content_tv
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, ZNCategoryBean item) {
                                    viewHolder.setText(R.id.activity_zncategory_content_tv, item.getName());
                                }
                            });

                            ((ZNCategoryListActivity) t).activityZncategoryAddMLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ((ZNCategoryListActivity) t).adapter.setSelectItem(position);
                                    ((ZNCategoryListActivity) t).adapter.notifyDataSetChanged();
                                    ((ZNCategoryListActivity) t).chosedZNCategoryBean = ((ZNCategoryListActivity) t).list.get(position);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zncategory);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setTitle();

        getData();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("指南管理");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }

    /**
     * 获取类别初始列表数据
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_zncategorylist_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_COLUMNLIST;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("token", token).build();
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

    /**
     * 部门/机构/镇街-指南上传-添加分类
     * token 	是 	string 	token值
     * column_name 	是 	string 	类名
     *
     * @param categoryName
     */
    private void addCategory(String categoryName) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_zncategorylist_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_ADDCOLUMN;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("column_name", categoryName)
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getData();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Constants.ADDCATEGORY_RETURNCODE) {
            if (null != data) {
                String categoryName = data.getStringExtra("categoryName");
                if (StringUtil.isEmpty(categoryName)) {
                    return;
                }
                addCategory(categoryName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_zncategory_add_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                ZNCategoryListActivity.this.finish();
                break;
            case R.id.head_right_tv:
                //确认
                if (null != chosedZNCategoryBean) {
                    intent = getIntent();
                    intent.putExtra("znCategoryBean", chosedZNCategoryBean);
                    setResult(Constants.GETCATEGORY_RETURNCODE, intent);
                    ZNCategoryListActivity.this.finish();
                }
                break;
            case R.id.activity_zncategory_add_tv:
                //添加类别
                intent = new Intent(ZNCategoryListActivity.this, ZNCategoryAddActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
