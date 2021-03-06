package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.IYWP2PPushListener;
import com.alibaba.mobileim.IYWTribePushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWConstants;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.LoginParam;
import com.alibaba.mobileim.channel.constant.B2BConstant;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWContactOperateNotifyListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.fundamental.model.YWIMSmiley;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeMember;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.login.YWPwdType;
import com.alibaba.mobileim.ui.chat.widget.YWSmilyMgr;
import com.alibaba.mobileim.utility.IMAutoLoginInfoStoreUtil;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.alibaba.tcms.env.EnvManager;
import com.alibaba.tcms.env.TcmsEnvType;
import com.alibaba.tcms.env.YWEnvManager;
import com.alibaba.tcms.env.YWEnvType;
import com.alibaba.wxlib.util.SysUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact.ContactCacheUpdateListenerImpl;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact.ContactOperateNotifyListenerImpl;
import rongshanghui.tastebychance.com.rongshanghui.login.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;

/**
 * SDK ??????????????????
 *
 * @author jing.huai
 */
public class LoginSampleHelper {

    private static LoginSampleHelper sInstance = new LoginSampleHelper();

    public static LoginSampleHelper getInstance() {
        return sInstance;
    }

    // ??????APPKEY?????????APPKEY???????????????????????????
    /*public static String APP_KEY = "23015524";*/

    //????????????????????????????????????????????????????????????
//    public static final String APP_KEY_TEST = "4272";  //60026702

    public static final String APP_KEY_TEST = B2BConstant.APPKEY.APPKEY_B2B;  //60026702    60028148


    public static YWEnvType sEnvType = YWEnvType.TEST;

    // openIM UI???????????????????????????API????????????????????????????????????????????????
    private YWIMKit mIMKit;

    private Application mApp;

    private List<Map<YWTribe, YWTribeMember>> mTribeInviteMessages = new ArrayList<Map<YWTribe, YWTribeMember>>();

    public YWIMKit getIMKit() {
        return mIMKit;
    }

    public void setIMKit(YWIMKit imkit) {
        mIMKit = imkit;
    }

    public void initIMKit(String userId, String appKey) {
        mIMKit = YWAPI.getIMKitInstance(userId, appKey);
        addPushMessageListener();
        //???????????????????????????????????? todo ?????????????????????????????????????????????????????????????????????????????????????????????
        addContactListeners();

    }

    private YWLoginState mAutoLoginState = YWLoginState.idle;

    public YWLoginState getAutoLoginState() {
        return mAutoLoginState;
    }

    public void setAutoLoginState(YWLoginState state) {
        this.mAutoLoginState = state;
    }

    /**
     * ?????????SDK
     *
     * @param context
     */
    public void initSDK_Sample(Application context) {
        mApp = context;
        sEnvType = YWEnvManager.getEnv(context);
        //?????????IMKit
        final String userId = StringUtil.isNotEmpty(IMAutoLoginInfoStoreUtil.getLoginUserId()) ? IMAutoLoginInfoStoreUtil.getLoginUserId()
                : (StringUtil.isNotEmpty(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID)) ? IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID) : Constants.USER_ID);
        final String appkey = StringUtil.isNotEmpty(IMAutoLoginInfoStoreUtil.getAppkey()) ? IMAutoLoginInfoStoreUtil.getAppkey() : Constants.APP_KEY;
        TcmsEnvType type = EnvManager.getInstance().getCurrentEnvType(mApp);
        if (type == TcmsEnvType.ONLINE || type == TcmsEnvType.PRE) {
            if (TextUtils.isEmpty(appkey)) {
                YWAPI.init(mApp, Constants.APP_KEY);
            } else {
                YWAPI.init(mApp, appkey);
            }
        } else if (type == TcmsEnvType.TEST) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String appKey = preferences.getString("app_key", "");
            WxLog.i("APPKEY", "appKey = " + appKey);
            if (!TextUtils.isEmpty(appKey)) {
                YWAPI.aliInit(mApp, appKey, AccountUtils.SITE_CNALICNH);
            } else {
                YWAPI.aliInit(mApp, B2BConstant.APPKEY.APPKEY_B2B, AccountUtils.SITE_CNALICNH);
            }
//            YWAPI.init(mApp, APP_KEY_TEST);
        }

        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(appkey)) {
            LoginSampleHelper.getInstance().initIMKit(userId, appkey);
        }

        //???????????????????????????
        NotificationInitSampleHelper.init();
        initAutoLoginStateCallback();


        //?????????????????????????????????
        YWSmilyMgr.setSmilyInitNotify(new YWSmilyMgr.SmilyInitNotify() {
            @Override
            public void onDefaultSmilyInitOk() {
                //?????????????????????????????????????????????
//                SmilyCustomSample.hideDefaultSmiley();
//                SmilyCustomSample.addDefaultSmiley();
                //????????????????????????????????????sdk?????????????????????????????????????????????????????????hide???????????????????????????????????????
                SmilyCustomSample.addNewEmojiSmiley();
                YWIMSmiley smiley = SmilyCustomSample.addNewImageSmiley();
                smiley.setIndicatorIconResId(R.drawable.aliwx_s012);
                SmilyCustomSample.setSmileySize(YWIMSmiley.SMILEY_TYPE_IMAGE, 60);
                //??????????????????????????????memory leak
                YWSmilyMgr.setSmilyInitNotify(null);
            }
        });
    }

    //????????????????????????????????????
    private void sendAutoLoginState(YWLoginState loginState) {
        Intent intent = new Intent(LoginActivity.AUTO_LOGIN_STATE_ACTION);
        intent.putExtra("state", loginState.getValue());
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).sendBroadcast(intent);
    }

    /**
     * ????????????
     *
     * @param userId   ??????id
     * @param password ??????
     * @param callback ???????????????????????????
     */
    //------------------??????????????????OpenIMSDK?????????????????????????????????ID??????????????????-------------------
    //?????????????????????????????????????????????????????????
    public void login_Sample(String userId, String password, String appKey,
                             IWxCallback callback) {

        if (mIMKit == null) {
            return;
        }

        SysUtil.setCnTaobaoInit(true);
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userId,
                password);
        if (TextUtils.isEmpty(appKey) || appKey.equals(YWConstants.YWSDKAppKey)
                || appKey.equals(YWConstants.YWSDKAppKeyCnHupan)) {
            loginParam.setServerType(LoginParam.ACCOUNTTYPE_WANGXIN);
            loginParam.setPwdType(YWPwdType.pwd);
        }
        // openIM SDK?????????????????????
        IYWLoginService mLoginService = mIMKit.getLoginService();

        mLoginService.login(loginParam, callback);
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void addPushMessageListener() {
        if (mIMKit == null) {
            return;
        }

        IYWConversationService conversationService = mIMKit.getConversationService();
        //???????????????????????????????????????????????????????????????????????????
        conversationService.removeP2PPushListener(mP2PListener);
        conversationService.addP2PPushListener(mP2PListener);

        //???????????????????????????????????????????????????????????????????????????
        conversationService.removeTribePushListener(mTribeListener);
        conversationService.addTribePushListener(mTribeListener);
    }

    private IYWContactOperateNotifyListener mContactOperateNotifyListener = new ContactOperateNotifyListenerImpl();

    private IYWContactCacheUpdateListener mContactCacheUpdateListener = new ContactCacheUpdateListenerImpl();

    /**
     * ????????????????????????????????????SDK?????????????????????????????????????????????????????????????????????
     * ??????????????????UI????????????
     * SDK?????????????????????????????????????????????????????????????????????????????????
     *
     * @author shuheng
     */
    private void addContactListeners() {
        //???????????????????????????????????????????????????????????????????????????????????????
        removeContactListeners();
        if (mIMKit != null) {
            if (mContactOperateNotifyListener != null)
                mIMKit.getContactService().addContactOperateNotifyListener(mContactOperateNotifyListener);
            if (mContactCacheUpdateListener != null)
                mIMKit.getContactService().addContactCacheUpdateListener(mContactCacheUpdateListener);

        }
    }

    private void removeContactListeners() {
        if (mIMKit != null) {
            if (mContactOperateNotifyListener != null)
                mIMKit.getContactService().removeContactOperateNotifyListener(mContactOperateNotifyListener);
            if (mContactCacheUpdateListener != null)
                mIMKit.getContactService().removeContactCacheUpdateListener(mContactCacheUpdateListener);

        }
    }

    private IYWP2PPushListener mP2PListener = new IYWP2PPushListener() {
        @Override
        public void onPushMessage(IYWContact contact, List<YWMessage> messages) {
            for (YWMessage message : messages) {
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS) {
                    if (message.getMessageBody() instanceof YWCustomMessageBody) {
                        YWCustomMessageBody messageBody = (YWCustomMessageBody) message.getMessageBody();
                        if (messageBody.getTransparentFlag() == 1) {
                            String content = messageBody.getContent();
                            try {
                                JSONObject object = new JSONObject(content);
                                if (object.has("text")) {
                                    String text = object.getString("text");
                                    IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), "???????????????content = " + text);
                                } else if (object.has("customizeMessageType")) {
                                    String customType = object.getString("customizeMessageType");
                                    if (!TextUtils.isEmpty(customType) && customType.equals(ChattingOperationCustomSample.CustomMessageType.READ_STATUS)) {
                                        YWConversation conversation = mIMKit.getConversationService().getConversationByConversationId(message.getConversationId());
                                        long msgId = Long.parseLong(object.getString("PrivateImageRecvReadMessageId"));
                                        conversation.updateMessageReadStatus(conversation, msgId);
                                    }
                                }
                            } catch (JSONException e) {
                            }
                        }
                    }
                }
            }
        }
    };

    private IYWTribePushListener mTribeListener = new IYWTribePushListener() {
        @Override
        public void onPushMessage(YWTribe tribe, List<YWMessage> messages) {
            //TODO ???????????????
        }

    };

    /**
     * ??????
     */
    public void loginOut_Sample() {
        if (mIMKit == null) {
            return;
        }


        // openIM SDK?????????????????????
        IYWLoginService mLoginService = mIMKit.getLoginService();
        mLoginService.logout(new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {

            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {

            }
        });
    }

    /**
     * ?????????????????????????????????????????????DEMO?????????????????????
     */
    private void initAutoLoginStateCallback() {
        YWChannel.setAutoLoginCallBack(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                mAutoLoginState = YWLoginState.success;
                sendAutoLoginState(mAutoLoginState);
            }

            @Override
            public void onError(int code, String info) {
                mAutoLoginState = YWLoginState.fail;
                sendAutoLoginState(mAutoLoginState);
            }

            @Override
            public void onProgress(int progress) {
                mAutoLoginState = YWLoginState.logining;
                sendAutoLoginState(mAutoLoginState);
            }
        });
    }

    public List<Map<YWTribe, YWTribeMember>> getTribeInviteMessages() {
        return mTribeInviteMessages;
    }
}
