package rongshanghui.tastebychance.com.rongshanghui.alibaba.alibaba.openIMUIDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWConstants;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.HttpChannel;
import com.alibaba.mobileim.channel.IMChannel;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.channel.util.DeviceInfoHelper;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.alibaba.mobileim.login.YWLoginCode;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.login.YWPwdType;
import com.alibaba.mobileim.utility.AccountInfoTools;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.alibaba.tcms.client.ServiceChooseHelper;
import com.alibaba.tcms.env.EnvManager;
import com.alibaba.tcms.env.TcmsEnvType;
import com.alibaba.tcms.env.YWEnvManager;
import com.alibaba.tcms.env.YWEnvType;

import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo.FragmentTabs;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.NotificationInitSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.UserProfileSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSucToLoginAlibaba;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends Activity {

    private static final int GUEST = 1;
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String APPKEY = "appkey";
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginSampleHelper loginHelper;
    private EditText userIdView;
    private EditText passwordView;
    private EditText appKeyView;
    private ProgressBar progressBar;
    private Button loginButton;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ImageView lg;
    private Button annoyloginButton;
    private int mClickCount = 0;


    public static final String AUTO_LOGIN_STATE_ACTION = "com.openim.autoLoginStateActionn";

    private BroadcastReceiver mAutoLoginStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int state = intent.getIntExtra("state", -1);
            YWLog.i(TAG, "mAutoLoginStateReceiver, loginState = " + state);
            if (state == -1) {
                return;
            }
            handleAutoLoginState(state);
        }
    };

    private void startChattingActivity(String targetId) {
        startActivity(loginHelper.getIMKit().getChattingActivityIntent(targetId));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.demo_login);
        loginHelper = LoginSampleHelper.getInstance();
        userIdView = (EditText) findViewById(R.id.account);
        passwordView = (EditText) findViewById(R.id.password);
        appKeyView = (EditText) findViewById(R.id.appkey);
        appKeyView.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        initLoginInfo();

        init(userIdView.getText().toString(), appKeyView.getText().toString());

        myRegisterReceiver();

        //????????????????????????
        //??????????????????????????????(????????????????????????????????????????????????)
//				CustomMessageSampleHelper.initHandler();

        loginButton = (Button) findViewById(R.id.login);
        annoyloginButton = (Button) findViewById(R.id.annoylogin);


        Bitmap logo = BitmapFactory.decodeResource(getResources(),
                R.drawable.login_logo);

        lg = (ImageView) findViewById(R.id.logo);
        lg.setImageBitmap(logo);
        userIdView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    passwordView.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                normalLogin();
            }
        });

        //???????????????????????????????????????
        findViewById(R.id.copy_right).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switchAndSaveEnv();
                return true;
            }
        });
        View copyRight = findViewById(R.id.copy_right);
        copyRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mClickCount;
                if (mClickCount % 5 == 0) {
                    String account = userIdView.getText().toString();
                    if (TextUtils.isEmpty(account)) {
                        final SharedPreferences defalultSprefs = getSharedPreferences(
                                "ywPrefsTools", Context.MODE_PRIVATE);
                        String annoyUid = defalultSprefs.getString("annoy_uid", "");
                        account = annoyUid;
                    }
                    FeedbackAPI.getFeedbackUnreadCount(account, new IWxCallback() {
                        @Override
                        public void onSuccess(final Object... result) {
                            if (result != null && result.length == 1 && result[0] instanceof Integer) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int count = (Integer) result[0];
                                        Toast toast = Toast.makeText(LoginActivity.this, "????????????" + count, Toast.LENGTH_SHORT);
                                        toast.show();
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
            }
        });


        annoyloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHelper.loginOut_Sample();

                annoyLogin();
            }
        });

    }

    private void normalLogin() {
        //?????????????????????????????????????????????????????????????????????
        if (YWChannel.getInstance().getNetWorkState().isNetWorkNull()) {
            Toast.makeText(LoginActivity.this, "????????????????????????????????????~", Toast.LENGTH_SHORT).show();
            return;
        }
        loginButton.setClickable(false);
        final Editable userId = userIdView.getText();
        final Editable password = passwordView.getText();
        final Editable appKey = appKeyView.getText();
//                if (userId == null || userId.toString().length() == 0) {
//                    Toast.makeText(LoginActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
//                    loginButton.setClickable(true);
//                    return;
//                }
//                if (password == null || password.toString().length() == 0) {
//                    Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
//                    loginButton.setClickable(true);
//                    return;
//                }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userIdView.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
        if (TextUtils.isEmpty(appKey)) {
            Constants.APP_KEY = YWConstants.YWSDKAppKey;
        }
        init(userId.toString(), appKeyView.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        loginHelper.login_Sample(userId.toString(), password.toString(), appKey.toString(), new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                saveLoginInfoToLocal(userId.toString(), password.toString(), appKey.toString());

                loginButton.setClickable(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "????????????",
                        Toast.LENGTH_SHORT).show();
                YWLog.i(TAG, "login success!");
                Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
                intent.putExtra(FragmentTabs.LOGIN_SUCCESS, "loginSuccess");
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
//						YWIMKit mKit = LoginSampleHelper.getInstance().getIMKit();
//						EServiceContact contact = new EServiceContact("qn??????????????????001:??????");
//						LoginActivity.this.startActivity(mKit.getChattingActivityIntent(contact));
//                        mockConversationForDemo();
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                progressBar.setVisibility(View.GONE);
                if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER) { //??????????????????????????????????????????????????????
                    showDialog(GUEST);
                } else {
                    loginButton.setClickable(true);
                    YWLog.w(TAG, "???????????????????????????" + errorCode + "  ???????????????" + errorMessage);
                    IMNotificationUtils.getInstance().showToast(LoginActivity.this, errorMessage);
                }
            }
        });

    }


    private void init(String userId, String appKey) {
        //?????????imkit
        LoginSampleHelper.getInstance().initIMKit(userId, appKey);
        //???????????????????????????????????????(?????????????????????????????????????????????????????????)
        UserProfileSampleHelper.initProfileCallback();
        //???????????????????????????
        NotificationInitSampleHelper.init();

    }

    /**
     * ???????????????????????????????????????
     *
     * @param userId
     * @param password
     */
    private void saveLoginInfoToLocal(String userId, String password, String appkey) {
        IMPrefsTools.setStringPrefs(LoginActivity.this, USER_ID, userId);
        IMPrefsTools.setStringPrefs(LoginActivity.this, PASSWORD, password);
        IMPrefsTools.setStringPrefs(LoginActivity.this, APPKEY, appkey);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case GUEST: {
                AlertDialog.Builder builder = new YWAlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.guest_login))
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.dismiss();
                                        guest_login();

                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.dismiss();
                                        loginButton.setClickable(true);
                                    }
                                });
                AlertDialog dialog = builder.create();
                return dialog;

            }
        }
        return super.onCreateDialog(id);

    }

    public void guest_login() {

        loginHelper.loginOut_Sample();
        progressBar.setVisibility(View.VISIBLE);
        //TODO ??????visitor1-visitor1000????????????
        final String guestId = new StringBuilder("visitor").append((new Random().nextInt(1000) + 1)).toString();
//      String userid = new StringBuffer(("uid")).append((new Random().nextInt(10) + 1)).toString();
        YWLog.d(TAG, guestId);
        LoginSampleHelper.getInstance().initIMKit(guestId, "23015524");
        loginHelper.login_Sample(guestId, "taobao1234", "23015524", new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                saveLoginInfoToLocal(guestId, "taobao1234", "23015524");
                loginButton.setClickable(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "????????????",
                        Toast.LENGTH_SHORT).show();
//                mockConversationForDemo();
                Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }

            @Override
            public void onProgress(int arg0) {
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                progressBar.setVisibility(View.GONE);
                if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER || errorCode == YWLoginCode.LOGON_FAIL_INVALIDPWD
                        || errorCode == YWLoginCode.LOGON_FAIL_EMPTY_ACCOUNT || errorCode == YWLoginCode.LOGON_FAIL_EMPTY_PWD
                        || errorCode == YWLoginCode.LOGON_FAIL_INVALIDSERVER || TextUtils.isEmpty(userIdView.getText().toString())
                        || TextUtils.isEmpty(passwordView.getText().toString())) {
                    showDialog(GUEST);
                } else {
                    loginButton.setClickable(true);
                    YWLog.w(TAG, "???????????? ????????????" + errorCode + "  ???????????????" + errorMessage);
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * ??????????????????????????????????????????
     */
    private void mockConversationForDemo() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                YWIMKit imKit = loginHelper.getIMKit();
                String targetId1 = "testpro81";
                String targetId2 = "testpro82";
                if (LoginSampleHelper.sEnvType == YWEnvType.TEST) {
                    targetId1 = "openimtest20";
                    targetId2 = "openimtest21";
                }
                IYWConversationService conversationService = imKit.getConversationService();
                YWConversation conversation = conversationService.getConversationCreater().createConversationIfNotExist(targetId1);
                YWMessage msg = YWMessageChannel.createTextMessage("hello");
                if (conversation.getLastestMessage() == null) {
                    conversation.getMessageSender().sendMessage(msg, 120, null);
                }

                YWConversation conversation2 = conversationService.getConversationCreater().createConversationIfNotExist(targetId2);
                YWMessage msg2 = YWMessageChannel.createTextMessage("hi");
                if (conversation2.getLastestMessage() == null) {
                    conversation2.getMessageSender().sendMessage(msg2, 120, null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int loginState = LoginSampleHelper.getInstance().getAutoLoginState().getValue();
        handleAutoLoginState(loginState);
//        YWLog.i(TAG, "onResume, loginState = " + LoginSampleHelper.getInstance().getAutoLoginState().getValue());
        YWLog.i(TAG, "onResume, loginState = " + loginState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myUnregisterReceiver();
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventToLogin(@NonNull EventLoginSucToLoginAlibaba eventLoginSucToLoginAlibaba) {
        saveLoginInfoToLocal(eventLoginSucToLoginAlibaba.getUserid(), eventLoginSucToLoginAlibaba.getPassword(), eventLoginSucToLoginAlibaba.getAppkey());
        if (null != userIdView) {
            userIdView.setText(eventLoginSucToLoginAlibaba.getUserid());
        }
        if (null != passwordView) {
            passwordView.setText(eventLoginSucToLoginAlibaba.getPassword());
        }
        if (null != appKeyView) {
            appKeyView.setText(eventLoginSucToLoginAlibaba.getAppkey());
        }
        normalLogin();
    }

    private void myRegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AUTO_LOGIN_STATE_ACTION);
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).registerReceiver(mAutoLoginStateReceiver, filter);
    }

    private void myUnregisterReceiver() {
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).unregisterReceiver(mAutoLoginStateReceiver);
    }

    private void handleAutoLoginState(int loginState) {
        if (loginState == YWLoginState.logining.getValue()) {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            loginButton.setClickable(false);
        } else if (loginState == YWLoginState.success.getValue()) {
            loginButton.setClickable(true);
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        } else {
            //??????????????????
            progressBar.setVisibility(View.GONE);
            loginButton.setClickable(true);
        }
    }

    /**
     * ??????????????????
     *
     * @return
     */
    private String getRandAccount() {
        final int max = 90;
        final int min = 10;
        Random random = new Random();

        return "testpro" + (random.nextInt(max) % (max - min + 1) + min);
    }

    private YWEnvType envType = YWEnvType.ONLINE;
    private AlertDialog dialog;

    private void switchAndSaveEnv() {
        final String[] items = {"??????", "??????", "??????"};
        if (dialog == null) {
            dialog = new YWAlertDialog.Builder(this)
                    .setTitle("????????????")
                    .setItems(items,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {

                                    TcmsEnvType tcmsEnvType = TcmsEnvType.ONLINE;
                                    if (which == 0) {
                                        envType = YWEnvType.ONLINE;
                                    } else if (which == 1) {
                                        envType = YWEnvType.PRE;
                                        tcmsEnvType = TcmsEnvType.PRE;
                                    } else if (which == 2) {
                                        envType = YWEnvType.TEST;
                                        tcmsEnvType = TcmsEnvType.TEST;
                                    }

                                    EnvManager.getInstance().resetEnvType(MyApplication.getContext(), tcmsEnvType);
                                    YWEnvManager.prepare(MyApplication.getContext(), envType);
                                    IMNotificationUtils.getInstance().showToast("?????????????????????????????????????????????", LoginActivity.this);
                                    ServiceChooseHelper.exitService(LoginActivity.this);//xianzhen: service must restart too.

                                    AccountInfoTools.saveAnnoyAccount("", "");


                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.exit(0);
                                        }
                                    }, 1000);
                                }
                            }).setNegativeButton(
                            getResources().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            }).create();
        }
        dialog.show();
    }


    private void annoyLogin() {
        String[] annoyAccountPw = AccountInfoTools.getAnnoyAccount();
        if (annoyAccountPw != null && annoyAccountPw.length == 2 && !TextUtils.isEmpty(annoyAccountPw[0]) && !TextUtils.isEmpty(annoyAccountPw[1])) {
            String uid = annoyAccountPw[0];
            String pw = annoyAccountPw[1];
            WxLog.d("test", "annoy account:" + uid + "  " + pw);

            doAnnoyLogin(uid, pw);
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("prefix", AccountInfoTools.getPrefix(YWAPI.getAppKey()));
            Map<String, String> loginDeviceInfomap = DeviceInfoHelper.getLoginDeviceInfo(IMChannel.getApplication());
            Iterator<Map.Entry<String, String>> iterator = loginDeviceInfomap.entrySet().iterator();
            JSONObject deviceInfoJsonObj = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    deviceInfoJsonObj.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            params.put("deviceinfo", deviceInfoJsonObj.toString());
            IWxCallback callback = new IWxCallback() {
                @Override
                public void onSuccess(Object... result) {
                    if (result != null && result.length > 0 && result[0] instanceof String) {
                        String resultStr = (String) result[0];
                        try {
                            JSONObject resultJson = new JSONObject(resultStr);
                            int retcode = resultJson.optInt("retcode");
                            if (retcode == 0) {
                                String userid = resultJson.optString("userid");
                                String password = resultJson.optString("password");

                                AccountInfoTools.saveAnnoyAccount(userid, password);

                                doAnnoyLogin(userid, password);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        onError(IWxCallback.ERROR, "");
                    }
                }

                @Override
                public void onError(int code, String info) {

                }

                @Override
                public void onProgress(int progress) {

                }
            };
            HttpChannel.getInstance().asyncPostRequest(HttpChannel.getAnnoyDomain() + "openim/getanonymous", params, callback);
        }
    }


    private void doAnnoyLogin(String mUid, String mPw) {
        YWIMKit mIMKit = YWAPI.getIMKitInstance(AccountUtils.getShortUserID(mUid), FeedbackAPI.getFeedbackAppkey());
        IYWLoginService mLoginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(AccountUtils.getShortUserID(mUid),
                mPw);
        Map<String, String> deviceInfo = DeviceInfoHelper.getLoginDeviceInfo(IMChannel.getApplication());
        Iterator<Map.Entry<String, String>> iterator = deviceInfo.entrySet().iterator();
        JSONObject attrJson = new JSONObject();
        try {
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();

                attrJson.put(entry.getKey(), entry.getValue());

            }
            Map<String, String> attrsMap = new HashMap<String, String>();
            attrsMap.put("deviceinfo", attrJson.toString());
            loginParam.setAttrs(attrsMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginParam.setPwdType(YWPwdType.annoy);
        mLoginService.login(loginParam, mloginCallback);
    }

    private IWxCallback mloginCallback = new IWxCallback() {
        @Override
        public void onSuccess(Object... result) {
            WxLog.d("test", "Feedback login success");

//            Intent intent = new Intent(YWChannel.getApplication(),
//                    WxChattingActvity.class);
//            intent.putExtra(ChattingDetailPresenter.EXTRA_USERID, mActIntent.getStringExtra(ChattingDetailPresenter.EXTRA_USERID));
//            intent.putExtra(ChattingDetailPresenter.EXTRA_APPKEY, mActIntent.getStringExtra(ChattingDetailPresenter.EXTRA_APPKEY));
//            intent.putExtra(ChattingDetailPresenter.EXTRA_CVS_TYPE,
//                    mActIntent.getIntExtra(ChattingDetailPresenter.EXTRA_CVS_TYPE, YWConversationType.P2P.getValue()));
//            intent.putExtra(YWAccountType.class.getSimpleName(),
//                    mActIntent.getIntExtra(YWAccountType.class.getSimpleName(), YWAccountType.open.getValue()));
//            startActivity(intent);
//            finish();
//            YWIMKit imKit = LoginSampleHelper.getInstance().getIMKit();
            Intent intent = FeedbackAPI.getFeedbackActivityIntent();
            if (intent != null) {
                startActivity(intent);
                LoginActivity.this.finish();
            }
        }

        @Override
        public void onError(int code, String info) {
            WxLog.d("test", "Feedback  login fail");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT);
                    finish();
                }
            });
        }

        @Override
        public void onProgress(int progress) {

        }
    };

    private void initLoginInfo() {
        //???????????????????????????????????????????????????appkey
        String localId = IMPrefsTools.getStringPrefs(LoginActivity.this, USER_ID, "");
        if (!TextUtils.isEmpty(localId)) {
            userIdView.setText(localId);
            String localPassword = IMPrefsTools.getStringPrefs(LoginActivity.this, PASSWORD, "");
            if (!TextUtils.isEmpty(localPassword)) {
                passwordView.setText(localPassword);
            }
            String localAppKey = IMPrefsTools.getStringPrefs(LoginActivity.this, APPKEY, "");
            if (!TextUtils.isEmpty(localAppKey)) {
                appKeyView.setText(localAppKey);
            }
        }

        if (LoginSampleHelper.sEnvType == YWEnvType.ONLINE) {
            if (TextUtils.isEmpty(userIdView.getText())) {
                userIdView.setText(getRandAccount());
            }
            if (TextUtils.isEmpty(passwordView.getText())) {
                passwordView.setText("taobao1234");
            }
            if (TextUtils.isEmpty(appKeyView.getText())) {
                appKeyView.setText(Constants.APP_KEY);
            }
        } else if (LoginSampleHelper.sEnvType == YWEnvType.TEST) {
            if (TextUtils.isEmpty(userIdView.getText())) {
                Random r = new Random();
                int suffix = Math.abs(r.nextInt() % 100);
                userIdView.setText("fk" + suffix);
            }
            if (TextUtils.isEmpty(passwordView.getText())) {
                passwordView.setText("taobao1234");
            }
            if (TextUtils.isEmpty(appKeyView.getText())) {
                appKeyView.setText(LoginSampleHelper.APP_KEY_TEST);
            }

        } else if (LoginSampleHelper.sEnvType == YWEnvType.PRE) {
            if (TextUtils.isEmpty(userIdView.getText())) {
                userIdView.setText("testpro74");
            }
            if (TextUtils.isEmpty(passwordView.getText())) {
                passwordView.setText("taobao1234");
            }
            if (TextUtils.isEmpty(appKeyView.getText())) {
                appKeyView.setText(Constants.APP_KEY);
            }
        }
    }
}
