package rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;
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
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.bmmanager.BMManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger.bean.YJZQManagerBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.HDLYActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.yjzq.YJZQActivity;

/**
 * 部门、镇街-意见征求管理
 *
 * @author shenbh
 * @time 2018/1/24 15:25
 */
public class YiJianZhengQiuManagerActivity extends MyAppCompatActivity implements PullRefreshPushAddListView.IXListViewListener {


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
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.del_tv)
    TextView delTv;
    @BindView(R.id.chooceall_tv)
    TextView chooceallTv;
    @BindView(R.id.edit_cancel_tv)
    TextView editCancelTv;
    @BindView(R.id.plv)
    PullRefreshPushAddListView plv;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.yijianzhengqiumanager_rootlayout)
    CoordinatorLayout yijianzhengqiumanagerRootlayout;

    private List<YJZQManagerBean.DataBean> list;
    private ListViewCanChooseAllAdapter adapter;

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
                            if (null != resInfo) {
                                if (((YiJianZhengQiuManagerActivity) t).pageIndex > Constants.PAGE_START_INDEX && resInfo.getClass(YJZQManagerBean.class).getData().size() == 0) {
                                    ToastUtils.showOneToast(t.getApplicationContext(), "没有更多");
                                    return;
                                }
                                ((YiJianZhengQiuManagerActivity) t).list.addAll(resInfo.getClass(YJZQManagerBean.class).getData());
                            }
                            if (((YiJianZhengQiuManagerActivity) t).pageIndex > Constants.PAGE_START_INDEX && ((YiJianZhengQiuManagerActivity) t).list.size() > 0) {
                                ((YiJianZhengQiuManagerActivity) t).scrollMyListViewToBottom();
                            }

                            ((YiJianZhengQiuManagerActivity) t).plv.setAdapter(((YiJianZhengQiuManagerActivity) t).adapter = new ListViewCanChooseAllAdapter(t, ((YiJianZhengQiuManagerActivity) t).list));
                            ((YiJianZhengQiuManagerActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(t, YJZQActivity.class);
                                    intent.putExtra("post_id", ((YiJianZhengQiuManagerActivity) t).list.get(position - 1).getId());
                                    intent.putExtra("from", Constants.FROMYJZQMANAGERTOYJZQ);
                                    t.startActivity(intent);
                                }
                            });

                            break;
                        case Constants.WHAT_POSTDATA:
                            ((YiJianZhengQiuManagerActivity) t).resetView();
                            ((YiJianZhengQiuManagerActivity) t).initGetData();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    private String type = "部门";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_jian_zheng_qiu_manager);
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
        initView();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        plv.setPullLoadEnable(true);
        plv.setPullRefreshEnable(true);
        plv.setXListViewListener(this);
        plv.switchFooterToShowOrHide(false);
    }

    private void resetView() {
        editCancelTv.setText("编辑");
        hideEditLayot();
    }

    private void initGetData() {
        if (list != null) {
            list.clear();
        }
        pageIndex = Constants.PAGE_START_INDEX;
        getData();
    }

    /**
     * 滚动到底部
     */
    private void scrollMyListViewToBottom() {
        plv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                plv.setSelection(adapter.getCount() - 1);
            }
        });
    }

    private void getData() {
        if (list == null) {
            list = new ArrayList<>();
        }

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.yijianzhengqiumanager_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_POSTLISTBYCATE;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("publish_type", SystemUtil.getInstance().castPostListByCate(Constants.PULISHTYPE_YJZJ) + "");
        builder.add("page", pageIndex + "");
        if (SystemUtil.getInstance().getUserInfo().getId() != 0) {
            builder.add("user_id", SystemUtil.getInstance().getUserInfo().getId() + "");
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
     * token	是	string	token
     * ids	是	string	多个id 逗号隔开 1,2,3
     * publish_type	是	int	要删除的文章类别 ‘文章类型 1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报,11:意见征集’,
     */
    private void batchDel() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.yijianzhengqiumanager_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_BATCHDEl;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        builder.add("ids", btnOperateList());
        builder.add("publish_type", SystemUtil.getInstance().castPostListByCate(Constants.PULISHTYPE_YJZJ) + "");
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
                                msg.what = Constants.WHAT_POSTDATA;
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

    private void initView() {
        hideEditLayot();
    }

    private void setTitle() {
        headCenterTitleTv.setText("意见征求管理");
        headRightIv.setVisibility(View.VISIBLE);
        Bitmap newInto = BitmapFactory.decodeResource(getResources(), R.drawable.new_into);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headRightIv.getLayoutParams();
        lp.width = newInto.getWidth() + newInto.getWidth() / 2;
        lp.height = newInto.getHeight() + newInto.getHeight() / 2;
        headRightIv.setLayoutParams(lp);
        PicassoUtils.getinstance().loadImageById(YiJianZhengQiuManagerActivity.this, R.drawable.new_into, headRightIv, PicassoUtils.PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }

    private void hideEditLayot() {
        editCancelTv.setText("编辑");
        editCancelTv.setTextColor(getResources().getColor(R.color.font_blue));

        linearLayout.setVisibility(View.GONE);
        if (null != adapter) {
            adapter.setFlage(false);
            adapter.notifyDataSetChanged();
        }
    }

    private void showEditLayout() {
        editCancelTv.setText("取消");
        editCancelTv.setTextColor(getResources().getColor(R.color.textgray));

        linearLayout.setVisibility(View.VISIBLE);
        if (null != adapter) {
            adapter.setFlage(true);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 全选
     */
    public void btnSelectAllList() {
        if (null != adapter && adapter.isFlage()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChoosed(true);
            }

            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 全不选
     */
    public void btnNoList() {

        if (null != adapter && adapter.isFlage()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChoosed(false);
            }

            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取选中数据
     */
    public String btnOperateList() {
        String retIds = "";

        if (null != adapter && adapter.isFlage()) {
            StringBuilder ids = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChoosed()) {
                    ids.append(String.valueOf(list.get(i).getId()));
                    ids.append(",");
                }
            }

            if (ids.length() > 0) {
                retIds = ids.substring(0, ids.lastIndexOf(","));
            }
            if (Constants.IS_DEVELOPING) {
                Toast.makeText(YiJianZhengQiuManagerActivity.this, retIds, Toast.LENGTH_SHORT).show();
            }
            Log.e("TAG", retIds);
        }

        return retIds;
    }

    private void onLoad() {

        String str = Thread.currentThread().getName();
        System.out.println("str = " + str);

        if (StringUtil.isNotEmpty(str) && str.equals("main") && null != plv) {
            plv.stopRefresh();
            plv.stopLoadMore();
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
            plv.setRefreshTime(date);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGetData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        resetView();
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_iv, R.id.del_tv, R.id.chooceall_tv, R.id.edit_cancel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                YiJianZhengQiuManagerActivity.this.finish();
                break;
            case R.id.head_right_iv:
                WeakReference<ToDetailBean> wf3 = new WeakReference<ToDetailBean>(new ToDetailBean());
                wf3.get().setTitle(Constants.PULISHTYPE_YJZJ);
                wf3.get().setPublishCate(type);//0:平台;1:商会;2:企业;3:机构;4:部门;5:个人;6:镇街
                wf3.get().setPublishType(Constants.PULISHTYPE_YJZJ);////0:平台;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报
                SystemUtil.getInstance().intentToRichEditActivity(YiJianZhengQiuManagerActivity.this, wf3.get());
                break;
            case R.id.del_tv:
                if (StringUtil.isEmpty(btnOperateList())) {
                    ToastUtils.showOneToast(getApplicationContext(), "请选择要删除的项");
                    return;
                }

                //批量删除
                batchDel();
                break;
            case R.id.chooceall_tv:
                if (chooceallTv.getText().toString().equals("全选")) {
                    btnSelectAllList();
                    chooceallTv.setText("全不选");
                } else {
                    btnNoList();
                    chooceallTv.setText("全选");
                }
                break;
            case R.id.edit_cancel_tv:
                if (editCancelTv.getText().toString().equals("编辑")) {
                    showEditLayout();
                } else {
                    hideEditLayot();
                }
                break;
        }
    }

    private int pageIndex = Constants.PAGE_START_INDEX;

    @Override
    public void onListViewRefresh() {
        initGetData();
        onLoad();
    }

    @Override
    public void onListViewLoadMore() {
        ++pageIndex;
        getData();
        onLoad();
    }

}
