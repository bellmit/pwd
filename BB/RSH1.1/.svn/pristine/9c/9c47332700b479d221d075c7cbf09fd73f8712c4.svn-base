package rongshanghui.tastebychance.com.rongshanghui.login.areacode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.adapter.AreaCodeAdapter;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeRes;
import rongshanghui.tastebychance.com.rongshanghui.util.CharacterParser;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.QuickIndexBar;
import rongshanghui.tastebychance.com.rongshanghui.view.SearchIconClearEditText;

/**
 * 区号选择
 *
 * @author shenbh
 * @time 2017/11/23 13:52
 */
public class AreaCodeActivity extends MyBaseActivity {


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
    @BindView(R.id.filter_edit)
    SearchIconClearEditText filterEdit;
    @BindView(R.id.search_nocity_tv)
    TextView searchNocityTv;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.quickIndexBar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.currentWord)
    TextView currentWord;
    @BindView(R.id.activity_areacode_rootlayout)
    LinearLayout activityAreacodeRootlayout;
    private List<AreaCodeInfo> resultList = new ArrayList<AreaCodeInfo>();
    private AreaCodeAdapter adapter;

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
                            Map<String, Object> map = resInfo.strToJSONObject(resInfo.getData().toString());

                            Map<String, List<AreaCodeRes.AreaCode>> resultMap = new HashMap<String, List<AreaCodeRes.AreaCode>>();
                            Set<Map.Entry<String, Object>> set = map.entrySet();
                            for (Map.Entry<String, Object> entry : set) {
                                List<AreaCodeRes.AreaCode> value = resInfo.strToList(entry.getValue().toString(), AreaCodeRes.AreaCode.class);
                                if (value != null) {
                                    resultMap.put(entry.getKey(), value);
                                }
                            }

                            for (Map.Entry<String, List<AreaCodeRes.AreaCode>> entry : resultMap.entrySet()) {
                                String key = entry.getKey();
                                List<AreaCodeRes.AreaCode> list = entry.getValue();
                                for (AreaCodeRes.AreaCode areaCode : list) {
                                    WeakReference<AreaCodeInfo> wf = new WeakReference<AreaCodeInfo>(new AreaCodeInfo());
                                    wf.get().setFirstWord(key);
                                    wf.get().setName(areaCode.getName());
                                    wf.get().setCode(areaCode.getCode());
                                    ((AreaCodeActivity) t).resultList.add(wf.get());
                                }
                            }

                            Collections.sort(((AreaCodeActivity) t).resultList);
                            System.out.println("resultList = " + ((AreaCodeActivity) t).resultList);
                            ((AreaCodeActivity) t).listview.setAdapter(((AreaCodeActivity) t).adapter = new AreaCodeAdapter(t, ((AreaCodeActivity) t).resultList));

                            //4、点击右侧快速导航字母
                            ((AreaCodeActivity) t).touchQuickIndexBar(((AreaCodeActivity) t).resultList);
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

    private MyHandler mHandler = new MyHandler(this);

    private void initTitle() {
        //动态设置状态栏下方的控件（view）的高度

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageView right_btn = (ImageView) findViewById(R.id.head_right_iv);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("区号选择");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AreaCodeActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("确定");
            right_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //确认
                    if (null != adapter) {
                        if (adapter.getSelectItem() == -1) {
                            ToastUtils.showOneToast(AreaCodeActivity.this, "请选择一个区号");
                            return;
                        }

                        AreaCodeInfo selectedAreaData = adapter.getSelectedData();
                        Intent intent = getIntent();
                        intent.putExtra("selectedAreaData", selectedAreaData);
                        setResult(Constants.GETAREACODE_RETURNCODE, intent);
                        AreaCodeActivity.this.finish();
                    }
                }
            });
        }
    }

    private void setListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 网络请求数据
     * <p>
     * /api/UserApp/cityList
     */
    //1、获取数据：城市列表
//    private List<AreaCodeRes.DataBean> dataBeans = new ArrayList<AreaCodeRes.DataBean>();
    private String resStr;

    private void getDatas() {

        if (Constants.IS_LOCALDATA) {
            resStr = "    {\n" +
                    "      \"code\": \"0\",\n" +
                    "      \"data\": {\n" +
                    "        \"A\": [\n" +
                    "          {\n" +
                    "            \"name\": \"安提瓜\",\n" +
                    "            \"code\": \"1268\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"name\": \"埃塞俄比亚\",\n" +
                    "            \"code\": \"251\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"name\": \"安哥拉\",\n" +
                    "            \"code\": \"244\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"Z\": [\n" +
                    "          {\n" +
                    "            \"name\": \"中国\",\n" +
                    "            \"code\": \"86\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"name\": \"直布罗陀\",\n" +
                    "            \"code\": \"350\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    }";

        } else {
            getDataFromServer();
        }

    }

    private void getDataFromServer() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_areacode_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_GETAREACODE;
        OkHttpClient mOkHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder().build();
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
                                mHandler.sendMessage(msg);
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
     * 解析数据
     *
     * @param resStr
     * @return
     */
    private AreaCodeRes analysisBean(String resStr) {
        if (StringUtil.isEmpty(resStr)) {
            if (Constants.IS_DEVELOPING) {
//                throw new IllegalArgumentException(this.getClass().getSimpleName()+"获取到的str为空");
            }
            return null;
        }
        return JSONObject.parseObject(resStr, AreaCodeRes.class);
    }

    private void touchQuickIndexBar(final List<AreaCodeInfo> list) {
        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                // 根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
                for (int i = 0; i < list.size(); i++) {
                    String firstWord = list.get(i).getFirstWord();
                    if (letter.equals(firstWord.toUpperCase())) {
                        // 说明找到了，那么应该讲当前的item放到屏幕顶端
                        listview.setSelection(i);
                        break;//只需要找到第一个就行
                    }
                }

                if (letter.equals("☆")) {
                    listview.setSelection(0);
                }
                // 显示当前触摸的字母
                showCurrentWord(letter);
            }
        });

        // 通过缩小currentWord来隐藏
        ViewHelper.setScaleX(currentWord, 0);
        ViewHelper.setScaleY(currentWord, 0);
    }

    private boolean isScale = false;
    private Handler handler = new Handler();

    protected void showCurrentWord(String letter) {
        currentWord.setText(letter);
        if (!isScale) {
            isScale = true;
            ViewPropertyAnimator.animate(currentWord).scaleX(1f).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
            ViewPropertyAnimator.animate(currentWord).scaleY(1f).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
        }

        // 先移除之前的任务
        handler.removeCallbacksAndMessages(null);

        // 延时隐藏currentWord
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // currentWord.setVisibility(View.INVISIBLE);
                ViewPropertyAnimator.animate(currentWord).scaleX(0f).setDuration(450).start();
                ViewPropertyAnimator.animate(currentWord).scaleY(0f).setDuration(450).start();
                isScale = false;
            }
        }, 1500);
    }

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private void initObject() {
        if (null != searchNocityTv) {
            searchNocityTv.setVisibility(View.GONE);
        }
        filterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*filterData(s.toString());*/
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        characterParser = CharacterParser.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_code);
        ButterKnife.bind(this);

        initTitle();

        setListener();
        initObject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatas();
    }
}
