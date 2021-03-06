package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.IYWConversationUnreadChangeListener;
import com.alibaba.mobileim.conversation.IYWMessageLifeCycleListener;
import com.alibaba.mobileim.conversation.IYWSendMessageToContactInBlackListListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.conversation.YWMessageType;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeMember;
import com.alibaba.mobileim.gingko.presenter.tribe.IYWTribeChangeListener;
import com.alibaba.mobileim.login.IYWConnectionListener;
import com.alibaba.mobileim.login.YWLoginCode;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.UserContext;

import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.alibaba.openIMUIDemo.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.CustomConversationHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.tribe.TribeConstants;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayongge on 15-9-22.
 */
public class FragmentTabs extends FragmentActivity {

    public static final String TAG = "FragmentTabs";
    public static final String LOGIN_SUCCESS = "loginSuccess";

    public static final String TAB_MESSAGE = "message";
    public static final String TAB_CONTACT = "contact";
    public static final String TAB_TRIBE = "tribe";
    public static final String TAB_MORE = "more";

    private TextView mMessageTab;
    private TextView mContactTab;
    private TextView mTribeTab;
    private TextView mMoreTab;
    private TextView mUnread;

    private Drawable mMessagePressed;
    private Drawable mMessageNormal;
    private Drawable mFriendPressed;
    private Drawable mFriendNormal;
    private Drawable mTribePressed;
    private Drawable mTribeNormal;
    private Drawable mMorePressed;
    private Drawable mMoreNormal;

    private FragmentTabHost mTabHost;
    private YWIMKit mIMKit;
    private IYWConversationService mConversationService;
    private IYWConversationUnreadChangeListener mConversationUnreadChangeListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private IYWTribeChangeListener mTribeChangedListener;

    private IYWMessageLifeCycleListener mMessageLifeCycleListener;
    private IYWSendMessageToContactInBlackListListener mSendMessageToContactInBlackListListener;
    private IYWConnectionListener mConnectionListener;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        if (mIMKit == null) {
            return;
        }
        mConversationService = mIMKit.getConversationService();
        initListeners();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.demo_fragment_tabs);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View indicator = getIndicatorView(TAB_MESSAGE);

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putSerializable(UserContext.EXTRA_USER_CONTEXT_KEY, mIMKit.getUserContext());
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), mIMKit.getConversationFragmentClass(), fragmentBundle);

        indicator = getIndicatorView(TAB_CONTACT);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CONTACT).setIndicator(indicator), mIMKit.getContactsFragmentClass(), fragmentBundle);

        indicator = getIndicatorView(TAB_TRIBE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TRIBE).setIndicator(indicator), TribeFragment.class, fragmentBundle);

        indicator = getIndicatorView(TAB_MORE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator), MoreFragment.class, fragmentBundle);

        mUnread = (TextView) findViewById(R.id.unread);

        mTabHost.setOnTabChangedListener(listener);
        listener.onTabChanged(TAB_MESSAGE);


    }

    public static final String SYSTEM_TRIBE_CONVERSATION = "sysTribe";
    public static final String SYSTEM_FRIEND_REQ_CONVERSATION = "sysfrdreq";
    public static final String RELATED_ACCOUNT = "relatedAccount";

    /**
     * ????????????????????????????????????????????????
     */
    private void initCustomConversation() {
        CustomConversationHelper.addCustomConversation(SYSTEM_FRIEND_REQ_CONVERSATION, null);
    }

    /**
     * ?????????????????????
     */
    private void initListeners() {
        //????????????????????????????????????
        initConversationServiceAndListener();
        //?????????????????????????????????
        addTribeChangeListener();
        //????????????????????????
        initCustomConversation();
        //????????????????????????????????????
        setMessageLifeCycleListener();
        //???????????????????????????????????????????????????
        setSendMessageToContactInBlackListListener();
        //??????IM??????????????????
        addConnectionListener();
    }

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            if (TAB_MESSAGE.equals(tabId)) {
                setMessageText(true);
                setContactText(false);
                setTribeText(false);
                setMoreText(false);
                return;
            }
            if (TAB_CONTACT.equals(tabId)) {
                setMessageText(false);
                setContactText(true);
                setTribeText(false);
                setMoreText(false);
                return;
            }
            if (TAB_TRIBE.equals(tabId)) {
                setMessageText(false);
                setContactText(false);
                setTribeText(true);
                setMoreText(false);
                return;
            }
            if (TAB_MORE.equals(tabId)) {
                setMessageText(false);
                setContactText(false);
                setTribeText(false);
                setMoreText(true);
                return;
            }
        }
    };

    private View getIndicatorView(String tab) {
        View tabView = View.inflate(this, R.layout.demo_tab_item, null);
        TextView indicator = (TextView) tabView.findViewById(R.id.tab_text);
        Drawable drawable;

        if (tab.equals(TAB_MESSAGE)) {
            indicator.setText("??????");
            drawable = getResources().getDrawable(R.drawable.demo_tab_icon_message_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMessageTab = indicator;
        } else if (tab.equals(TAB_CONTACT)) {
            indicator.setText("?????????");
            drawable = getResources().getDrawable(R.drawable.demo_tab_icon_contact_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mContactTab = indicator;
        } else if (tab.equals(TAB_TRIBE)) {
            indicator.setText("??????");
            drawable = getResources().getDrawable(R.drawable.demo_tab_icon_tribe_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mTribeTab = indicator;
        } else if (tab.equals(TAB_MORE)) {
            indicator.setText("??????");
            drawable = getResources().getDrawable(R.drawable.demo_tab_icon_setting_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMoreTab = indicator;
        }
        return tabView;
    }

    private void initConversationServiceAndListener() {
        mConversationUnreadChangeListener = new IYWConversationUnreadChangeListener() {

            //?????????????????????????????????????????????????????????????????????????????????????????????
            @Override
            public void onUnreadChange() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoginSampleHelper loginHelper = LoginSampleHelper.getInstance();
                        final YWIMKit imKit = loginHelper.getIMKit();
                        mConversationService = imKit.getConversationService();
                        //??????????????????????????????????????????
                        int unReadCount = mConversationService.getAllUnreadCount();
                        //??????????????????????????????
                        mIMKit.setShortcutBadger(unReadCount);
                        if (unReadCount > 0) {
                            mUnread.setVisibility(View.VISIBLE);
                            if (unReadCount < 100) {
                                mUnread.setText(unReadCount + "");
                            } else {
                                mUnread.setText("99+");
                            }
                        } else {
                            mUnread.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        };
        mConversationService.addTotalUnreadChangeListener(mConversationUnreadChangeListener);
    }

    private void addTribeChangeListener() {
        mTribeChangedListener = new IYWTribeChangeListener() {
            @Override
            public void onInvite(YWTribe tribe, YWTribeMember user) {
                Map<YWTribe, YWTribeMember> map = new HashMap<YWTribe, YWTribeMember>();
                map.put(tribe, user);
                LoginSampleHelper.getInstance().getTribeInviteMessages().add(map);
                String userName = user.getShowName();
                if (TextUtils.isEmpty(userName)) {
                    userName = user.getUserId();
                }
                WxLog.i(TAG, "onInvite");
            }

            @Override
            public void onUserJoin(YWTribe tribe, YWTribeMember user) {
                //??????user?????????tribe
            }

            @Override
            public void onUserQuit(YWTribe tribe, YWTribeMember user) {
                //??????user?????????tribe
            }

            @Override
            public void onUserRemoved(YWTribe tribe, YWTribeMember user) {
                //??????user????????????tribe
            }

            @Override
            public void onTribeDestroyed(YWTribe tribe) {
                //??????tribe????????????
            }

            @Override
            public void onTribeInfoUpdated(YWTribe tribe) {
                //??????tribe????????????????????????????????????????????????????????????????????????
            }

            @Override
            public void onTribeManagerChanged(YWTribe tribe, YWTribeMember user) {
                //??????user????????????????????????????????????????????????
            }

            @Override
            public void onTribeRoleChanged(YWTribe tribe, YWTribeMember user) {
                //??????user???????????????????????????
            }
        };
        mIMKit.getTribeService().addTribeListener(mTribeChangedListener);
    }

    private void setMessageLifeCycleListener() {
        mMessageLifeCycleListener = new IYWMessageLifeCycleListener() {
            /**
             * ?????????????????????
             * @param conversation ????????????????????????
             * @param message      ???????????????????????????
             * @return ??????????????????????????????null???????????????????????????
             */
            @Override
            public YWMessage onMessageLifeBeforeSend(YWConversation conversation, YWMessage message) {
                //todo ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                String cvsType = "??????";
                if (conversation.getConversationType() == YWConversationType.Tribe) {
                    cvsType = "?????????";
                }
                String msgType = "????????????";
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_IMAGE) {
                    msgType = "????????????";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GEO) {
                    msgType = "??????????????????";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_AUDIO) {
                    msgType = "????????????";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
                    msgType = "???????????????";
                }

                //TODO ??????APNS Push???????????????????????????APNS Push????????????????????????message.setPushInfo(YWPushInfo)????????????????????????????????????APNS Push????????????????????????message.setPushInfo(YWPushInfo)??????
                //TODO Demo???????????????????????????APNS Push??????,?????????????????????????????????
//                YWPushInfo pushInfo = new YWPushInfo(1, cvsType + msgType, "dingdong", "?????????????????????");
//                message.setPushInfo(pushInfo);

                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT) {
                    String content = message.getContent();
                    if (content.equals("55")) {
                        message.setContent("????????????????????????, ???????????????55");
                        return message;
                    } else if (content.equals("66")) {
                        YWMessage newMsg = YWMessageChannel.createTextMessage("???????????????????????????, ?????????????????????66");
                        return newMsg;
                    } else if (content.equals("77")) {
                        IMNotificationUtils.getInstance().showToast(FragmentTabs.this, "???????????????????????????????????????77");
                        return null;
                    }
                }
                return message;
            }

            /**
             * ???????????????????????????
             * @param message   ?????????????????????
             * @param sendState ??????????????????????????????????????????{@link com.alibaba.mobileim.conversation.YWMessageType.SendState}
             */
            @Override
            public void onMessageLifeFinishSend(YWMessage message, YWMessageType.SendState sendState) {
//                IMNotificationUtils.getInstance().showToast(FragmentTabs.this, sendState.toString());
            }
        };
        mConversationService.setMessageLifeCycleListener(mMessageLifeCycleListener);
    }

    //???????????????????????????
    private void addConnectionListener() {
        mConnectionListener = new IYWConnectionListener() {
            @Override
            public void onDisconnect(int code, String info) {
                if (code == YWLoginCode.LOGON_FAIL_KICKOFF) {
                    //????????????????????????????????????????????????
                    Toast.makeText(MyApplication.getContext(), "????????????", Toast.LENGTH_LONG).show();
                    YWLog.i("LoginSampleHelper", "????????????");
                    LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
                    finish();
                    Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);
                }
            }

            @Override
            public void onReConnecting() {

            }

            @Override
            public void onReConnected() {

            }
        };
        mIMKit.getIMCore().addConnectionListener(mConnectionListener);
    }

    private void setSendMessageToContactInBlackListListener() {
        mSendMessageToContactInBlackListListener = new IYWSendMessageToContactInBlackListListener() {
            /**
             * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
             * @param conversation ???????????????????????????
             * @param message      ??????????????????
             * @return true?????????  false????????????
             */
            @Override
            public boolean sendMessageToContactInBlackList(YWConversation conversation, YWMessage message) {
                //TODO ?????????????????????????????????????????????????????????????????????SDK???????????????
                return true;
            }
        };
        mConversationService.setSendMessageToContactInBlackListListener(mSendMessageToContactInBlackListListener);
    }

    private void setMessageText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMessageTab.setTextColor(getResources().getColor(
                    R.color.tab_pressed_color));
            if (mMessagePressed == null) {
                mMessagePressed = getResources().getDrawable(
                        R.drawable.demo_tab_icon_message_pressed);
            }
            drawable = mMessagePressed;
        } else {
            mMessageTab.setTextColor(getResources().getColor(
                    R.color.tab_normal_color));
            if (mMessageNormal == null) {
                mMessageNormal = getResources().getDrawable(
                        R.drawable.demo_tab_icon_message_normal);
            }
            drawable = mMessageNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            mMessageTab.setCompoundDrawables(null, drawable, null, null);
        }
    }


    private void setContactText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mContactTab.setTextColor(getResources().getColor(
                    R.color.tab_pressed_color));
            if (mFriendPressed == null) {
                mFriendPressed = getResources().getDrawable(
                        R.drawable.demo_tab_icon_contact_pressed);
            }
            drawable = mFriendPressed;
        } else {
            mContactTab.setTextColor(getResources().getColor(
                    R.color.tab_normal_color));
            if (mFriendNormal == null) {
                mFriendNormal = getResources().getDrawable(
                        R.drawable.demo_tab_icon_contact_normal);
            }
            drawable = mFriendNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            mContactTab.setCompoundDrawables(null, drawable, null, null);
        }

    }

    private void setTribeText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mTribeTab.setTextColor(getResources().getColor(
                    R.color.tab_pressed_color));
            if (mTribePressed == null) {
                mTribePressed = getResources().getDrawable(
                        R.drawable.demo_tab_icon_tribe_pressed);
            }
            drawable = mTribePressed;
        } else {
            mTribeTab.setTextColor(getResources().getColor(
                    R.color.tab_normal_color));
            if (mTribeNormal == null) {
                mTribeNormal = getResources().getDrawable(
                        R.drawable.demo_tab_icon_tribe_normal);
            }
            drawable = mTribeNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            mTribeTab.setCompoundDrawables(null, drawable, null, null);
        }

    }

    private void setMoreText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMoreTab.setTextColor(getResources().getColor(
                    R.color.tab_pressed_color));
            if (mMorePressed == null) {
                mMorePressed = getResources().getDrawable(
                        R.drawable.demo_tab_icon_setting_pressed);
            }
            drawable = mMorePressed;
        } else {
            mMoreTab.setTextColor(getResources().getColor(
                    R.color.tab_normal_color));
            if (mMoreNormal == null) {
                mMoreNormal = getResources().getDrawable(
                        R.drawable.demo_tab_icon_setting_normal);
            }
            drawable = mMoreNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            mMoreTab.setCompoundDrawables(null, drawable, null, null);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        YWLog.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //resume?????????????????????????????????????????????????????????????????????????????????????????????????????????
        mConversationUnreadChangeListener.onUnreadChange();

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(LOGIN_SUCCESS) != null) {
            listener.onTabChanged(TAB_MESSAGE);
            getIntent().removeExtra(LOGIN_SUCCESS);
        }
        if (YWAPI.getLoginAccountList().size() >= 2) {
            CustomConversationHelper.addCustomConversation(RELATED_ACCOUNT, null);
        }
        YWLog.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        YWLog.i(TAG, "onDestroy");
        super.onDestroy();

        if (mMessageNormal != null) {
            mMessageNormal.setCallback(null);
        }
        if (mMessagePressed != null) {
            mMessagePressed.setCallback(null);
        }
        if (mFriendNormal != null) {
            mFriendNormal.setCallback(null);
        }
        if (mFriendPressed != null) {
            mFriendPressed.setCallback(null);
        }

        if (mTribeNormal != null) {
            mTribeNormal.setCallback(null);
        }
        if (mTribePressed != null) {
            mTribePressed.setCallback(null);
        }
        if (mMoreNormal != null) {
            mMoreNormal.setCallback(null);
        }
        if (mMorePressed != null) {
            mMorePressed.setCallback(null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long tribeId = intent.getLongExtra(TribeConstants.TRIBE_ID, 0);
        if (tribeId != 0) {
            setIntent(intent);
            mTabHost.setCurrentTab(1);
        }

        String tribeOp = intent.getStringExtra(TribeConstants.TRIBE_OP);
        if (!TextUtils.isEmpty(tribeOp)) {
            setIntent(intent);
            mTabHost.setCurrentTab(2);
        }
        YWLog.i(TAG, "onNewIntent");
    }
}
