package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.alibaba.mobileim.conversation.IYWMessageListener;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.IYWDebugToolService;
import com.alibaba.mobileim.utils.IYWCacheService;

import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.alibaba.openIMUIDemo.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.SimpleWebViewActivity;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.DemoSimpleKVStore;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.NotificationInitSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.test.MultiAccountTestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MoreFragment.class
            .getSimpleName();
    private static final String PAGE1 = "http://chuye.cloud7.com.cn/7086991";
    private static final String PAGE2 = "http://im.baichuan.taobao.com";
    private Activity mContext;
    private String mUserId;
    private View logout, more1, more2, more3;
    private View mView;
    private YWIMKit mIMKit;
    private IYWCacheService mCacheService;
    private CheckBox soundCheckBox;
    private CheckBox vibrationCheckBox;
    private CheckBox quietCheckBox;
    private TextView cacheSizeView;
    private NotificationInitSampleHelper mNotificationSettings;
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        if (mIMKit == null) {
            return;
        }
        mCacheService = mIMKit.getCacheService();
        mUserId = mIMKit.getIMCore().getLoginUserId();


        try {
            JSONObject testJson = new JSONObject("{\"device_manufacturer\":\"HTC\",\"os\":\"Android\",\"send_id\":\"iwangxinopenimtest16\",\"access_subtype\":\"Unknown\",\"msg_type\":0,\"package\":\"com.alibaba.openIMUIDemo\",\"app_version\":\"1.0\",\"sdk_version\":\"1.0\",\"resolution\":\"1920x1080\",\"device_brand\":\"htc\",\"access\":\"Wi-Fi\",\"os_version\":\"4.4.4\",\"message\":\"ufjcjmv\",\"device_model\":\"One\",\"send_time\":1452064725,\"device_uuid\":\"b3e94f6c-336f-e4fd-f40d-080938100f2a\",\"msg_id\":6269105843813482506,\"carrier\":\"\",\"device_board\":\"m7\",\"feedback_message_attri\":\"{\\\"key\\\":\\\"value\\\"}\",\"parent_id\":\"6269105843803324425\"}");
            String jsondevice_manufacturer = testJson.optString("device_manufacturer");


            JSONObject jsonObject1 = new JSONObject("{\n" +
                    "   \"msg\" : {\n" +
                    "      \"appkey\" : \"4272\",\n" +
                    " \n" +
                    "      \"content\" : \"{\\\"device_manufacturer\\\":\\\"HTC\\\",\\\"os\\\":\\\"Android\\\",\\\"send_id\\\":\\\"iwangxinopenimtest16\\\",\\\"access_subtype\\\":\\\"Unknown\\\",\\\"msg_type\\\":0,\\\"package\\\":\\\"com.alibaba.openIMUIDemo\\\",\\\"app_version\\\":\\\"1.0\\\",\\\"sdk_version\\\":\\\"1.0\\\",\\\"resolution\\\":\\\"1920x1080\\\",\\\"device_brand\\\":\\\"htc\\\",\\\"access\\\":\\\"Wi-Fi\\\",\\\"os_version\\\":\\\"4.4.4\\\",\\\"message\\\":\\\"ufjcjmv\\\",\\\"device_model\\\":\\\"One\\\",\\\"send_time\\\":1452064725,\\\"device_uuid\\\":\\\"b3e94f6c-336f-e4fd-f40d-080938100f2a\\\",\\\"msg_id\\\":626910584381348 \n" +
                    "2506,\\\"carrier\\\":\\\"\\\",\\\"device_board\\\":\\\"m7\\\",\\\"feedback_message_attri\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"}\\\",\\\"parent_id\\\":\\\"6269105843803324425\\\"}\",\n" +
                    "      \"fromId\" : \"iwangxinopenimtest16\",\n" +
                    "      \"msgId\" : \"6269105843813482506\",\n" +
                    "      \"sendTime\" : \"1452064726\",\n" +
                    "      \"toId\" : \"iwangxin_feedback_service_account_\"\n" +
                    "   },\n" +
                    "   \"type\" : \"feedback\"\n" +
                    "} ");

            JSONObject msg = jsonObject1.optJSONObject("msg");
            String content = msg.optString("content");

            Log.d("test", "content:" + content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.demo_fragment_more, container, false);
        init();
        return mView;
    }

    protected void init() {
        mContext.getWindow().setWindowAnimations(0);
        initTitle();
        initNotificationSettings();
        logout = mView.findViewById(R.id.more_logout);
        logout.setOnClickListener(this);
        more1 = mView.findViewById(R.id.more1);
        more1.setOnClickListener(this);
        more2 = mView.findViewById(R.id.more2);
        more2.setOnClickListener(this);
        more3 = mView.findViewById(R.id.more3);
        more3.setOnClickListener(this);

        View more4 = mView.findViewById(R.id.more4);
        more4.setOnClickListener(this);

        View clearCacheView = mView.findViewById(R.id.clear_cache_layout);
        clearCacheView.setOnClickListener(this);

        mView.findViewById(R.id.more_upload_log).setOnClickListener(this);
        cacheSizeView = (TextView) mView.findViewById(R.id.cache_size);
        updateCacheSize();

        TextView getBlackList = (TextView) mView.findViewById(R.id.sync_black_list);
        getBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????
                mIMKit.getIMCore().getContactManager().syncBlackContacts(new IWxCallback() {
                    @Override
                    public void onSuccess(Object... obj) {
                        List<IYWContact> blackList = (List<IYWContact>) obj[0];
                        StringBuilder builder = new StringBuilder();
                        if (blackList != null && blackList.size() > 0) {
                            for (IYWContact user : blackList) {
                                builder.append(user.getAppKey()).append(user.getUserId()).append("\n");
                            }
                        }
                        if (TextUtils.isEmpty(builder.toString())) {
                            IMNotificationUtils.getInstance().showToast(mContext, "?????????????????????~");
                        } else {
                            IMNotificationUtils.getInstance().showToast(mContext, builder.toString());
                            WxLog.i(TAG, builder.toString());
                        }
                    }

                    @Override
                    public void onError(int code, String info) {
                        IMNotificationUtils.getInstance().showToast(mContext, "getBlackList error, code = " + code + ", info = " + info);
                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
            }
        });
        initTest();
    }

    private void initTitle() {
        RelativeLayout titleBar = (RelativeLayout) mView.findViewById(R.id.title_bar);
        TextView titleView = (TextView) mView.findViewById(R.id.title_self_title);
        TextView leftButton = (TextView) mView.findViewById(R.id.left_button);

        titleBar.setBackgroundColor(Color.parseColor("#25498F"));
        titleView.setTextColor(Color.WHITE);
        titleView.setText("??????");
        titleBar.setVisibility(View.VISIBLE);
        leftButton.setVisibility(View.GONE);
    }

    /**
     * ???????????????????????????
     */
    private void initNotificationSettings() {
        mNotificationSettings = new NotificationInitSampleHelper(null);

        View soundRelativeLayout = mView.findViewById(R.id.setting_sound_layout);
        soundRelativeLayout.setOnClickListener(this);
        View vibrationRelativeLayout = mView.findViewById(R.id.setting_vibration_layout);
        vibrationRelativeLayout.setOnClickListener(this);
        View quietRelativeLayout = mView.findViewById(R.id.setting_quiet_layout);
        quietRelativeLayout.setOnClickListener(this);

        soundCheckBox = (CheckBox) mView.findViewById(R.id.setting_sound_check);
        vibrationCheckBox = (CheckBox) mView.findViewById(R.id.setting_vibration_check);
        quietCheckBox = (CheckBox) mView.findViewById(R.id.setting_quiet_check);
    }

    private void updateNotificationSettings() {
        soundCheckBox.setChecked(DemoSimpleKVStore.getNeedSound() == 1);
        vibrationCheckBox.setChecked(DemoSimpleKVStore.getNeedVibration() == 1);
    }

    private void updateCacheSize() {
        mCacheService.getCacheSize(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                if (result != null && result.length > 0) {
                    final double size = (double) result[0];
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            cacheSizeView.setText((int) size / 1024 / 1024 + "MB");
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String info) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    private List<YWMessage> mMsgList;

    private IYWMessageListener msgListener = new IYWMessageListener() {
        @Override
        public void onItemUpdated() {
            WxLog.d(TAG, "onItemUpdated");
        }

        @Override
        public void onItemComing() {
            WxLog.d(TAG, "onItemComing");
        }

        @Override
        public void onInputStatus(byte status) {
            WxLog.d(TAG, "onInputStatus");
        }
    };

    @Override
    public void onClick(View v) {
        boolean oldCheck;
        switch (v.getId()) {
            case R.id.more_logout:
                showAlertDialog();
                break;
            case R.id.more1:
                viewUrlInSimpleWebView(PAGE1, "????????????");
                break;
            case R.id.more2:
                viewUrlInSimpleWebView(PAGE2, "????????????");
                break;
            case R.id.more3:
                EServiceContact contact = new EServiceContact("openim????????????", 0);//
                YWIMKit imKit = LoginSampleHelper.getInstance().getIMKit();
                Intent intent = imKit.getChattingActivityIntent(contact);
                startActivity(intent);
                break;
            case R.id.more4:
//                imKit = LoginSampleHelper.getInstance().getIMKit();
                intent = FeedbackAPI.getFeedbackActivityIntent();
                if (intent != null) {
                    startActivity(intent);
                }
                break;
            case R.id.more_upload_log:
                //??????????????????APP??????????????????????????????????????????????????????????????????????????????LOG???????????????
                IYWDebugToolService debugToolService = LoginSampleHelper.getInstance().getIMKit().getDebugToolService();
                debugToolService.uploadIMLog("myTestLog", new IWxCallback() {
                    @Override
                    public void onSuccess(Object... result) {
                        //????????????
                        IMNotificationUtils.getInstance().showToast(getContext(), "??????????????????!");
                    }

                    @Override
                    public void onError(int code, String info) {
                        //????????????
                        IMNotificationUtils.getInstance().showToast(getContext(), "??????????????????, code = " + code + ", info = " + info);
                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
                break;
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ?????????????????????????????????????????????????????????????????????????????????SharedPreferences?????????????????????
            case R.id.setting_sound_layout:
                oldCheck = soundCheckBox.isChecked();
                mNotificationSettings.setNeedSound(!oldCheck);
                soundCheckBox.setChecked(!oldCheck);
                DemoSimpleKVStore.setNeedSound(!oldCheck ? 1 : 0);

                break;
            case R.id.setting_vibration_layout:
                oldCheck = vibrationCheckBox.isChecked();
                mNotificationSettings.setNeedVibrator(!oldCheck);
                vibrationCheckBox.setChecked(!oldCheck);
                DemoSimpleKVStore.setNeedVibration(!oldCheck ? 1 : 0);
                break;
            case R.id.clear_cache_layout:
                mCacheService.clearCache(new IWxCallback() {
                    @Override
                    public void onSuccess(Object... result) {
                        updateCacheSize();
                    }

                    @Override
                    public void onError(int code, String info) {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
            case R.id.setting_quiet_layout:
                oldCheck = quietCheckBox.isChecked();
                mNotificationSettings.setNeedQuiet(!oldCheck);
                quietCheckBox.setChecked(!oldCheck);
                break;
            default:
                break;
        }
    }


    private void showAlertDialog() {
        AlertDialog.Builder builder = new YWAlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.quit_confirm))
                .setCancelable(false)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                logout();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialog = builder.create();
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void logout() {
        // openIM SDK?????????????????????
        IYWLoginService mLoginService = mIMKit.getLoginService();
        mLoginService.logout(new IWxCallback() {
            //??????logout?????????????????????IMBaseActivity???OpenIM??????Actiivity???s
            @Override
            public void onSuccess(Object... arg0) {
                Toast.makeText(MyApplication.getContext(), "????????????", Toast.LENGTH_SHORT).show();
                YWLog.i(TAG, "????????????");
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
                getActivity().finish();
                Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {
                Toast.makeText(MyApplication.getContext(), "????????????,???????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ??????WebView????????????????????????????????????????????????????????????
     *
     * @param url
     * @param title
     */
    private void viewUrlInSimpleWebView(String url, String title) {
        Intent intent = new Intent(getActivity(), SimpleWebViewActivity.class);
        intent.putExtra(SimpleWebViewActivity.URL, url);
        intent.putExtra(SimpleWebViewActivity.TITLE, title);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNotificationSettings();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        YWLog.e(TAG, "onDestroy");
    }

    private void initTest() {
        TextView test = (TextView) mView.findViewById(R.id.more_test_multi_account);
        test.setVisibility(View.VISIBLE);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MultiAccountTestActivity.class);
                startActivity(intent);
            }
        });
    }

}
