package rongshanghui.tastebychance.com.rongshanghui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
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
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import rongshanghui.tastebychance.com.rongshanghui.alibaba.alibaba.openIMUIDemo.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo.MoreFragment;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo.TribeFragment;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.CustomConversationHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.tribe.TribeConstants;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToContactList;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToConversation;
import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.home.HomeFragment;
import rongshanghui.tastebychance.com.rongshanghui.mime.MineFragment;
import rongshanghui.tastebychance.com.rongshanghui.news.NewsFragment;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PackageManagerUtils;

/**
 * Created by mayongge on 15-9-22.
 */
public class MyFragmentTabs extends FragmentActivity {

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
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        if (mIMKit == null) {
            return;
        }
        mConversationService = mIMKit.getConversationService();
        initListeners();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.demo_fragment_tabs);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
//        tintManager.setTintColor(Color.parseColor("#990000FF"));
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDark));

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        resetTab();

        mUnread = (TextView) findViewById(R.id.unread);
        mTabHost.setOnTabChangedListener(listener);
        listener.onTabChanged(TAB_MESSAGE);
    }

    private void resetTab() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putSerializable(UserContext.EXTRA_USER_CONTEXT_KEY, mIMKit.getUserContext());

        View indicator = getIndicatorView(TAB_MESSAGE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), HomeFragment.class, fragmentBundle);

        indicator = getIndicatorView(TAB_CONTACT);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CONTACT).setIndicator(indicator), mIMKit.getConversationFragmentClass(), fragmentBundle);

        indicator = getIndicatorView(TAB_TRIBE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TRIBE).setIndicator(indicator), NewsFragment.class, fragmentBundle);

        indicator = getIndicatorView(TAB_MORE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator), MineFragment.class, fragmentBundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToContact(@NonNull EventToContactList eventToContactList) {
        if (null != mTabHost) {
            mTabHost.clearAllTabs();
        }
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putSerializable(UserContext.EXTRA_USER_CONTEXT_KEY, mIMKit.getUserContext());

        View indicator = getIndicatorView(TAB_MESSAGE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), HomeFragment.class, fragmentBundle);

        indicator = getIndicatorView(TAB_CONTACT);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CONTACT).setIndicator(indicator), mIMKit.getContactsFragmentClass(), fragmentBundle);

        indicator = getIndicatorView(TAB_TRIBE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TRIBE).setIndicator(indicator), NewsFragment.class, fragmentBundle);

        indicator = getIndicatorView(TAB_MORE);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator), MineFragment.class, fragmentBundle);

        mTabHost.setCurrentTab(1);
    }

  /*  @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToConversation(@NonNull EventToConversation eventToConversation) {
        if (null != mTabHost){
            mTabHost.clearAllTabs();
        }
        resetTab();
        mTabHost.setCurrentTab(1);
    }*/

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
            drawable = getResources().getDrawable(R.drawable.tab_home_unselected);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMessageTab = indicator;
        } else if (tab.equals(TAB_CONTACT)) {
            indicator.setText("?????????");
            drawable = getResources().getDrawable(R.drawable.tab_addresslist_unselected);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mContactTab = indicator;
        } else if (tab.equals(TAB_TRIBE)) {
            indicator.setText("??????");
            drawable = getResources().getDrawable(R.drawable.tab_news_unselected);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mTribeTab = indicator;
        } else if (tab.equals(TAB_MORE)) {
            indicator.setText("??????");
            drawable = getResources().getDrawable(R.drawable.tab_person_unselected);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
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
                        IMNotificationUtils.getInstance().showToast(MyFragmentTabs.this, "???????????????????????????????????????77");
                        return null;
                    }
                }
                return message;
            }

            /**
             * ???????????????????????????
             * @param message   ?????????????????????
             * @param sendState ??????????????????????????????????????????{@link YWMessageType.SendState}
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
            mMessageTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            if (mMessagePressed == null) {
                mMessagePressed = getResources().getDrawable(R.drawable.tab_home_selected);
            }
            drawable = mMessagePressed;
        } else {
            mMessageTab.setTextColor(getResources().getColor(R.color.font_blue));
            if (mMessageNormal == null) {
                mMessageNormal = getResources().getDrawable(R.drawable.tab_home_unselected);
            }
            drawable = mMessageNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mMessageTab.setCompoundDrawables(null, drawable, null, null);
        }
    }


    private void setContactText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mContactTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            if (mFriendPressed == null) {
                mFriendPressed = getResources().getDrawable(R.drawable.tab_addresslist_selected);
            }
            drawable = mFriendPressed;
        } else {
            mContactTab.setTextColor(getResources().getColor(R.color.font_blue));
            if (mFriendNormal == null) {
                mFriendNormal = getResources().getDrawable(R.drawable.tab_addresslist_unselected);
            }
            drawable = mFriendNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mContactTab.setCompoundDrawables(null, drawable, null, null);
        }

    }

    private void setTribeText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mTribeTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            if (mTribePressed == null) {
                mTribePressed = getResources().getDrawable(R.drawable.tab_news_selected);
            }
            drawable = mTribePressed;
        } else {
            mTribeTab.setTextColor(getResources().getColor(R.color.font_blue));
            if (mTribeNormal == null) {
                mTribeNormal = getResources().getDrawable(R.drawable.tab_news_unselected);
            }
            drawable = mTribeNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mTribeTab.setCompoundDrawables(null, drawable, null, null);
        }

    }

    private void setMoreText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMoreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            if (mMorePressed == null) {
                mMorePressed = getResources().getDrawable(R.drawable.tab_person_selected);
            }
            drawable = mMorePressed;
        } else {
            mMoreTab.setTextColor(getResources().getColor(R.color.font_blue));
            if (mMoreNormal == null) {
                mMoreNormal = getResources().getDrawable(R.drawable.tab_person_unselected);
            }
            drawable = mMoreNormal;
        }
        if (null != drawable) {// ???????????????NP??????????????????
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
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
    protected void onStart() {
        if (Constants.IS_DEVELOPING) {
            System.out.println(PackageManagerUtils.getRunningActivityName(this) + "----------------------------------------------------" + getTaskId());
        }

        EventBusUtils.register(this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        EventBusUtils.unregister(this);
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
