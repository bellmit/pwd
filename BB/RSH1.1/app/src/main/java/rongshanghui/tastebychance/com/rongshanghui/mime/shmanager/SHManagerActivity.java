package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.lang.ref.WeakReference;

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
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.SubjectCount;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean.ToDetailBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.MemberNumActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;

/**
 * 类描述：SHManagerActivity 我的-商会管理界面
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/1 9:44
 * 修改人：
 * 修改时间：2017/12/1 9:44
 * 修改备注：
 *
 * @version 1.0
 */
public class SHManagerActivity extends MyAppCompatActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightBtn;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_mine_manager_head_iv)
    ImageView activityMineManagerHeadIv;
    @BindView(R.id.activity_mine_manager_huiyuannum_tv)
    TextView activityMineManagerHuiyuannumTv;
    @BindView(R.id.activity_mine_manager_huiyuan_tv)
    TextView activityMineManagerHuiyuanTv;
    @BindView(R.id.activity_mine_manager_huiyuannum_ll)
    LinearLayout activityMineManagerHuiyuannumLl;
    @BindView(R.id.activity_mine_manager_fsnum_tv)
    TextView activityMineManagerFsnumTv;
    @BindView(R.id.activity_mine_manager_fs_tv)
    TextView activityMineManagerFsTv;
    @BindView(R.id.activity_mine_manager_fsnum_ll)
    LinearLayout activityMineManagerFsnumLl;
    @BindView(R.id.activity_mine_manager_shanghuixiunum_tv)
    TextView activityMineManagerShanghuixiunumTv;
    @BindView(R.id.activity_mine_manager_shanghuixiu_tv)
    TextView activityMineManagerShanghuixiuTv;
    @BindView(R.id.activity_mine_manager_shanghuixiu_ll)
    LinearLayout activityMineManagerShanghuixiuLl;
    @BindView(R.id.activity_mine_manager_llshownum_tv)
    TextView activityMineManagerLlshownumTv;
    @BindView(R.id.activity_mine_manager_llnum_tv)
    TextView activityMineManagerLlnumTv;
    @BindView(R.id.activity_mine_manager_llnum_ll)
    LinearLayout activityMineManagerLlnumLl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.activity_mine_manager_jianjieguanli_iv)
    ImageView activityMineManagerJianjieguanliIv;
    @BindView(R.id.activity_mine_manager_zixunfabu_iv)
    ImageView activityMineManagerZixunfabuIv;
    @BindView(R.id.activity_mine_manager_shanghuixiufabu_iv)
    ImageView activityMineManagerShanghuixiufabuIv;
    @BindView(R.id.activity_mine_manager_huiyuanguanli_iv)
    ImageView activityMineManagerHuiyuanguanliIv;
    @BindView(R.id.relativelalyout1)
    RelativeLayout relativelalyout1;
    @BindView(R.id.content_shmanager)
    RelativeLayout contentShmanager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_mine_shmanager_rootlayout)
    CoordinatorLayout activityMineShmanagerRootlayout;


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
                        case Constants.WHAT_GETDATA:
                            ResInfo resInfo = (ResInfo) msg.obj;
                            SubjectCount subjectCount = resInfo.getClass(SubjectCount.class);
                            PicassoUtils.getinstance().loadNormalByPath(t, subjectCount.getImage(), ((SHManagerActivity) t).activityMineManagerHeadIv);
                            ((SHManagerActivity) t).activityMineManagerHuiyuannumTv.setText(subjectCount.getMember_count() + "");
                            ((SHManagerActivity) t).activityMineManagerFsnumTv.setText(subjectCount.getFans_count() + "");
                            ((SHManagerActivity) t).activityMineManagerShanghuixiunumTv.setText(subjectCount.getShx() + "");
                            ((SHManagerActivity) t).activityMineManagerLlshownumTv.setText(subjectCount.getHits_count() + "");
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
        setContentView(R.layout.activity_mine_shmanager);
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
    }

    private void initView() {
        activityMineManagerJianjieguanliIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityMineManagerJianjieguanliIv, 82, 120));
        activityMineManagerZixunfabuIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityMineManagerZixunfabuIv, 81, 120));
        activityMineManagerShanghuixiufabuIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityMineManagerShanghuixiufabuIv, 82, 120));
        activityMineManagerHuiyuanguanliIv.setLayoutParams(SystemUtil.getInstance().getRLLparams(activityMineManagerHuiyuanguanliIv, 82, 120));
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("商会管理");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
    }

    /**
     * token 	是 	string 	token值
     * id 	是 	int 	查看主体所对应的用户id
     */
    private void getData() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_mine_shmanager_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_SUBJECTCOUNT;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (StringUtil.isNotEmpty(SystemUtil.getInstance().getToken())) {
            builder.add("token", SystemUtil.getInstance().getToken());
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        builder.add("id", SystemUtil.getInstance().getUserInfo().getId() + "");
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
                        /**Looper.prepare();
                         UiHelper.ShowOneToast(getApplicationContext(),"数据请求成功");
                         Looper.loop();*/

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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick({R.id.head_left_iv, R.id.activity_mine_manager_jianjieguanli_iv, R.id.activity_mine_manager_zixunfabu_iv, R.id.activity_mine_manager_shanghuixiufabu_iv, R.id.activity_mine_manager_huiyuanguanli_iv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                SHManagerActivity.this.finish();
                break;
            case R.id.activity_mine_manager_jianjieguanli_iv:
                intent = new Intent(SHManagerActivity.this, ManagerJJActivity.class);
                intent.putExtra("type", Constants.PUBLISHCATE_SH);
                startActivity(intent);
                break;
            case R.id.activity_mine_manager_zixunfabu_iv:
                WeakReference<ToDetailBean> wf2 = new WeakReference<ToDetailBean>(new ToDetailBean());
                wf2.get().setTitle("我的资讯");
                wf2.get().setPublishCate(Constants.PUBLISHCATE_SH);//0:平台;1:商会;2:企业;3:机构;4:部门;5:个人;6:镇街
                wf2.get().setPublishType(Constants.PULISHTYPE_ZX);////0:平台;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报
                SystemUtil.getInstance().intentToFabuDeleteableActivity(SHManagerActivity.this, wf2.get());
                break;
            case R.id.activity_mine_manager_shanghuixiufabu_iv:
                WeakReference<ToDetailBean> wf3 = new WeakReference<ToDetailBean>(new ToDetailBean());
                wf3.get().setTitle("商会秀发布");
                wf3.get().setPublishCate(Constants.PUBLISHCATE_SH);//0:平台;1:商会;2:企业;3:机构;4:部门;5:个人;6:镇街
                wf3.get().setPublishType(Constants.PULISHTYPE_SHX);////0:平台;1:融资项目;2:放贷信息;3:政策;4:资讯;5:指南;6:招商秀;7:商会秀;8:下载材料;9:企业风采,10:上报
                SystemUtil.getInstance().intentToFabuDeleteableActivity(SHManagerActivity.this, wf3.get());
                break;
            case R.id.activity_mine_manager_huiyuanguanli_iv:
                if (StringUtil.isEmpty(SystemUtil.getInstance().getToken())) {
                    ToastUtils.showOneToast(getApplicationContext(), Constants.LOGIN_INVALID);
                    return;
                }
                intent = new Intent(SHManagerActivity.this, MemberNumActivity.class);
                startActivity(intent);
                break;
        }
    }
}
