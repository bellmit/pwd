package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ListView;
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
import rongshanghui.tastebychance.com.rongshanghui.common.MyBaseHandler;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberListBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean.MemberNumBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.RoundImageView;
import rongshanghui.tastebychance.com.rongshanghui.view.pullrefreshpushadd.PullRefreshPushAddListView;

/**
 * 类描述：MemberListActivity 我的-商会管理-会员-列表(会员列表、待审核人员列表)
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/7 21:05
 * 修改人：
 * 修改时间：2017/12/7 21:05
 * 修改备注：
 *
 * @version 1.0
 */
public class MemberListActivity extends MyAppCompatActivity {

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
    @BindView(R.id.plv)
    ListView plv;
    @BindView(R.id.content_member_list)
    RelativeLayout contentMemberList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_memberlist_rootlayout)
    CoordinatorLayout activityMemberlistRootlayout;


    private CommonAdapter<MemberListBean> adapter;
    private List<MemberListBean> list = new ArrayList<>();
    private String toType;
    private MemberNumBean memberNumBean;
    private static final int GET_MEMBER_DATA_WHAT = 20;//获取成功列表
    private static final int GET_WAITMEMBER_DATA_WHAT = 21;//获取待审核列表
    private static final int POST_REMOVE_WHAT = 22;//移除会员
    private static final int POST_ADOPT_WHAT = 23;//通过审核
    private static final int POST_REJECT_WHAT = 24;//拒绝通过

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends MyBaseHandler {
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
                        case GET_MEMBER_DATA_WHAT:
                            if (null != ((MemberListActivity) t).list) {
                                ((MemberListActivity) t).list.clear();
                            }

                            if (((MemberListActivity) t).emptyLayout != null) {
                                if (resInfo.getDataList(MemberListBean.class) == null || resInfo.getDataList(MemberListBean.class).size() == 0) {
                                    emptyLayoutShowOrHide(((MemberListActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((MemberListActivity) t).emptyLayout, false);
                                }
                            }
                            ((MemberListActivity) t).list = resInfo.getDataList(MemberListBean.class);
                            ((MemberListActivity) t).plv.setAdapter(((MemberListActivity) t).adapter = new CommonAdapter<MemberListBean>(
                                    t.getApplicationContext(), ((MemberListActivity) t).list, R.layout.item_shtmanager_memberlist
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final MemberListBean item) {
                                    RoundImageView rtv = viewHolder.getView(R.id.item_head_rtv);
                                    PicassoUtils.getinstance().loadCircleImage(t, item.getAvatar(), rtv, 19);
                                    viewHolder.setText(R.id.item_name_tv, item.getUnit_name());

                                    ImageView imageView = viewHolder.getView(R.id.item_adopt_iv);
                                    imageView.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //取消关注的网络请求（请求成功后刷新界面）
                                            DialogUtils.getInstance().AlertMessage(t, "", "确定移除该会员？", "确定", "取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //移除会员
                                                    ((MemberListActivity) t).operateMember(item.getUser_id());
                                                }
                                            }, null);
                                        }
                                    });
                                }
                            });

                            ((MemberListActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(t, MemberInfoActivity.class);
                                    ((MemberListActivity) t).list.get(position).setToType(Constants.JG_TO_SHCY);
                                    intent.putExtra("memberBean", ((MemberListActivity) t).list.get(position));
                                    t.startActivity(intent);
                                }
                            });
                            break;
                        case GET_WAITMEMBER_DATA_WHAT:
                            if (null != ((MemberListActivity) t).list) {
                                ((MemberListActivity) t).list.clear();
                            }

                            if (((MemberListActivity) t).emptyLayout != null) {
                                if (resInfo.getDataList(MemberListBean.class) == null || resInfo.getDataList(MemberListBean.class).size() == 0) {
                                    emptyLayoutShowOrHide(((MemberListActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((MemberListActivity) t).emptyLayout, false);
                                }
                            }
                            ((MemberListActivity) t).list = resInfo.getDataList(MemberListBean.class);

                            ((MemberListActivity) t).plv.setAdapter(((MemberListActivity) t).adapter = new CommonAdapter<MemberListBean>(
                                    t.getApplicationContext(), ((MemberListActivity) t).list, R.layout.item_shtmanager_waitmemberlist
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final MemberListBean item) {
                                    RoundImageView rtv = viewHolder.getView(R.id.item_head_rtv);
                                    PicassoUtils.getinstance().loadCircleImage(t, item.getAvatar(), rtv, 19);
                                    viewHolder.setText(R.id.item_name_tv, item.getUnit_name());

                                    TextView adoptTv = viewHolder.getView(R.id.item_adopt_tv);
                                    TextView rejectTv = viewHolder.getView(R.id.item_reject_tv);

                                    adoptTv.setText("通过");
                                    adoptTv.setTextColor(t.getResources().getColor(R.color.gray));
                                    adoptTv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //通过
                                            ((MemberListActivity) t).operateMember(item.getUser_id());
                                        }
                                    });

                                    rejectTv.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            //拒绝，跳转页面
                                            Intent intent = new Intent(t, MemberRejectActivity.class);
                                            intent.putExtra("id", item.getUser_id());
                                            ((MemberListActivity) t).startActivity(intent);
                                        }
                                    });
                                }
                            });

                            ((MemberListActivity) t).plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(t, MemberInfoActivity.class);
                                    ((MemberListActivity) t).list.get(position).setToType(Constants.JG_TO_DSH);
                                    intent.putExtra("memberBean", ((MemberListActivity) t).list.get(position));
                                    ((MemberListActivity) t).startActivity(intent);
                                }
                            });
                            break;
                        case POST_REMOVE_WHAT:
                            ((MemberListActivity) t).getData();
                            break;
                        case POST_ADOPT_WHAT:
                            ((MemberListActivity) t).getData();
                            break;
                        case POST_REJECT_WHAT:

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

    private IntentFilter intentFilter;
    private EmptyLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
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

        emptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);


        if (null != getIntent()) {
            toType = getIntent().getStringExtra("toType");
        }

        if (null != toType) {
            setTitle();
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.CLOSE_MEMBERLIST_ACTION);

    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);

            if (toType.equals(Constants.JG_TO_SHCY)) {
                headCenterTitleTv.setText("商会成员");
            } else if (toType.equals(Constants.JG_TO_DSH)) {
                headCenterTitleTv.setText("添加成员");
            }
        }
    }

    /**
     * 商会-会员列表接口
     * token 	是 	string 	token值
     * status 	是 	int 	状态;0:待审核,1:入会,2:解除，3.拒绝入会,4.退出
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_memberlist_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_SUBJECTMEMBER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        if (Constants.JG_TO_SHCY.equals(toType)) {
            builder.add("status", "1");
        } else if (Constants.JG_TO_DSH.equals(toType)) {
            builder.add("status", "0");
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
                                if (Constants.JG_TO_SHCY.equals(toType)) {
                                    msg.what = GET_MEMBER_DATA_WHAT;
                                } else if (Constants.JG_TO_DSH.equals(toType)) {
                                    msg.what = GET_WAITMEMBER_DATA_WHAT;
                                }
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
     * 操作会员(此页面只有移除、通过)
     * token 	是 	string 	token值
     * id 	是 	int 	成员id号
     * status 	是 	int 	状态;0:待审核,1:入会,2:解除，3.拒绝入会,4.退出
     * reason 	否 	string 	拒绝入会是需填写拒绝理由
     */
    private void operateMember(int id) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_memberlist_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SHMANAGER_ACTIONMEMBER;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        builder.add("id", id + "");
        if (Constants.JG_TO_SHCY.equals(toType)) {
            builder.add("status", "2");
        } else if (Constants.JG_TO_DSH.equals(toType)) {
            builder.add("status", "1");
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
                                if (Constants.JG_TO_SHCY.equals(toType)) {
                                    msg.what = POST_REMOVE_WHAT;
                                } else if (Constants.JG_TO_DSH.equals(toType)) {
                                    msg.what = POST_ADOPT_WHAT;
                                }
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MemberListActivity.this.finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (null != broadcastReceiver) {
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != toType) {
            getData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != broadcastReceiver) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @OnClick(R.id.head_left_iv)
    public void onViewClicked() {
        MemberListActivity.this.finish();
    }
}
