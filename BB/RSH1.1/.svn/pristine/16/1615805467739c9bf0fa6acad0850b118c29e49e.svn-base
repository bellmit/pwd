package rongshanghui.tastebychance.com.rongshanghui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
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
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
import rongshanghui.tastebychance.com.rongshanghui.register.adapter.AscriptionChildAdapter;
import rongshanghui.tastebychance.com.rongshanghui.register.adapter.AscriptionGroupAdapter;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.SearchIconClearEditText;

/**
 * 所属地界面
 *
 * @author shenbh
 * @time 2017/11/24 16:38
 */
public class AscriptionActivity extends MyBaseActivity {


    @BindView(R.id.mine_myfollow_underline)
    View mineMyfollowUnderline;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.sht_domestic_tv)
    TextView shtDomesticTv;
    @BindView(R.id.sht_international_tv)
    TextView shtInternationalTv;
    @BindView(R.id.sht_search_edt)
    SearchIconClearEditText shtSearchEdt;
    @BindView(R.id.sht_group_lv)
    ListView shtGroupLv;
    @BindView(R.id.sht_child_lv)
    ListView shtChildLv;
    @BindView(R.id.activity_register_xxyyqt)
    LinearLayout activityRegisterXxyyqt;
    private List<RegionRes.DataBean> groupDatas;
    private List<RegionRes.DataBean> childDatas;
    private boolean isInternational = false;//国内，国际

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascription);
        ButterKnife.bind(this);
        setTitle();

        init();
    }

    private void init() {
        resetDomesticInternationalView();
        shtDomesticTv.setTextColor(AscriptionActivity.this.getResources().getColor(R.color.font_blue));
//        quickIndexBar.setVisibility(View.GONE);
//        currentWord.setVisibility(View.GONE);

        groupDatas = new ArrayList<>();
        childDatas = new ArrayList<>();

        shtGroupLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击变颜色
                if (null != groupAdapter) {
                    groupAdapter.setSelectItem(position);
                    groupAdapter.notifyDataSetChanged();
                }
                //点击左侧某一项，获取右侧明细数据
                if (groupDatas != null && position < groupDatas.size()) {
                    getChildData(groupDatas.get(position).getRegion_id());
                }
            }
        });

        shtChildLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != childAdapter) {
                    childAdapter.setSelectItem(i);
                    childAdapter.notifyDataSetChanged();

                    chosedCity = childAdapter.getSelectedData();
                }
            }
        });

        if (null != shtSearchEdt) {
            shtSearchEdt.setVisibility(View.INVISIBLE);
        }
    }

    private RegionRes.DataBean chosedCity = null;

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("籍贯/所在地选择");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("确认");
        }
    }

    /**
     * is_international	是	int	0 国内 1 国际
     * parent_id	否	int	根据父地址查子地址列表
     */
    private AscriptionGroupAdapter groupAdapter;

    private void getGroupListData() {

        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_xxyyqt), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_REGION;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("is_international", isInternational ? "1" : "0")
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

                    RegionRes resInfo = JSONObject.parseObject(str, RegionRes.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        groupDatas.clear();
                        groupDatas.addAll(resInfo.getData());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                groupAdapter = new AscriptionGroupAdapter(AscriptionActivity.this, groupDatas);
                                shtGroupLv.setAdapter(groupAdapter);

                                getChildData(groupDatas.get(0).getRegion_id());
                                //点击变颜色
                                if (null != groupAdapter) {
                                    groupAdapter.setSelectItem(0);
                                    groupAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        /*Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    /**
     * is_international	是	int	0 国内 1 国际
     * parent_id	否	int	根据父地址查子地址列表
     */
    private AscriptionChildAdapter childAdapter;

    private void getChildData(String region_id) {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_register_xxyyqt), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_REGION;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("is_international", isInternational ? "1" : "0")
                .add("parent_id", region_id)
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

                    RegionRes resInfo = JSONObject.parseObject(str, RegionRes.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        childDatas.clear();
                        childDatas.addAll(resInfo.getData());
                        runOnUiThread(new Runnable() {
                            @Override

                            public void run() {
                                childAdapter = new AscriptionChildAdapter(AscriptionActivity.this, childDatas);
                                shtChildLv.setAdapter(childAdapter);
                            }
                        });
                    } else {
                        /*Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getGroupListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void resetDomesticInternationalView() {
        shtDomesticTv.setTextColor(AscriptionActivity.this.getResources().getColor(R.color.textgray));
        shtInternationalTv.setTextColor(AscriptionActivity.this.getResources().getColor(R.color.textgray));
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.sht_domestic_tv, R.id.sht_international_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                AscriptionActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (chosedCity == null) {
                    ToastUtils.showOneToast(AscriptionActivity.this, "请选择一个省份");
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("ascription", chosedCity);
                setResult(Constants.GETASCRIPTION_RETURNCODE, intent);
                AscriptionActivity.this.finish();
                break;
            case R.id.sht_domestic_tv:
                resetDomesticInternationalView();
                isInternational = false;
                shtDomesticTv.setTextColor(AscriptionActivity.this.getResources().getColor(R.color.font_blue));
                getGroupListData();
                break;
            case R.id.sht_international_tv:
                resetDomesticInternationalView();
                isInternational = true;
                shtInternationalTv.setTextColor(AscriptionActivity.this.getResources().getColor(R.color.font_blue));
                getGroupListData();
                break;
        }
    }
}
