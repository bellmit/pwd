package rongshanghui.tastebychance.com.rongshanghui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
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
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.CustomConversationHelper;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.bean.AlibabaUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventRegisterSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventSysNewsNum;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventTaobaoUser;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToContactList;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToConversation;
import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.home.HomeFragment;
import rongshanghui.tastebychance.com.rongshanghui.login.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.MineFragment;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PackageManagerUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.network.MyCallBack;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.ZWDTFragment;

public class HostActivity extends BaseActivity {

    class TabBean {
        private int drawableId;
        private String nameStr;
        private int textColor;

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public String getNameStr() {
            return nameStr;
        }

        public void setNameStr(String nameStr) {
            this.nameStr = nameStr;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private List<TabBean> tabItemBg;

    private Bundle fragmentBundle;
    //alibaichuan
    private YWIMKit mIMKit;
    private IYWConversationUnreadChangeListener mConversationUnreadChangeListener;
    private IYWSendMessageToContactInBlackListListener mSendMessageToContactInBlackListListener;
    private IYWConnectionListener mConnectionListener;
    private IYWMessageLifeCycleListener mMessageLifeCycleListener;
    private IYWTribeChangeListener mTribeChangedListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IYWConversationService mConversationService;
    //alibaichuan


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_host);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
//        tintManager.setTintColor(Color.parseColor("#990000FF"));
        tintManager.setTintColor(getResources().getColor(R.color.tab_pressed_color));

        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);
        mContentVp.setOffscreenPageLimit(4);

        fragmentBundle = new Bundle();

        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        mConversationService = mIMKit.getConversationService();


        initTabBg();
        if (tabItemBg.size() > 0) {
            tabItemBg.get(0).setDrawableId(R.drawable.tab_home_selected);
            tabItemBg.get(0).setTextColor(R.color.tab_pressed_color);
        }
        initContent();
        initTab();

        initListener();
    }

    private void initListener() {
        mTabTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab被选的时候回调

                try {
                    if (null == tabItemBg) {
                        tabItemBg = new ArrayList<>();
                    }
                    tabItemBg.clear();

                    initTabBg();
                    switch (tab.getPosition()) {
                        case 0:
                            tabItemBg.get(0).setDrawableId(R.drawable.tab_home_selected);
                            tabItemBg.get(0).setTextColor(R.color.tab_pressed_color);
                            break;
                        case 1:
                            tabItemBg.get(1).setDrawableId(R.drawable.tab_addresslist_selected);
                            tabItemBg.get(1).setTextColor(R.color.tab_pressed_color);

                            if (!SystemUtil.getInstance().getIsLogin()) {
                                SystemUtil.getInstance().intentToLoginActivity(HostActivity.this, Constants.TO_CARDCLIP);
                            } else {
                                setTabValue();
                            }
                            break;
                        case 2:
                            tabItemBg.get(2).setDrawableId(R.drawable.tab_news_selected);
                            tabItemBg.get(2).setTextColor(R.color.tab_pressed_color);
                            break;
                        case 3:
                            tabItemBg.get(3).setDrawableId(R.drawable.tab_person_selected);
                            tabItemBg.get(3).setTextColor(R.color.tab_pressed_color);
                            break;
                        default: break;
                    }
                    setTabValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未被选择的时候回调
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选择的时候回调
            }
        });

        //初始化并添加会话变更监听
        initConversationServiceAndListener();
        //初始化并添加群变更监听
        addTribeChangeListener();
        //初始化自定义会话
        initCustomConversation();
        //设置发送消息生命周期监听
        setMessageLifeCycleListener();
        //设置发送消息给黑名单中的联系人监听
        setSendMessageToContactInBlackListListener();
        //添加IM连接状态监听
        addConnectionListener();
    }

    private TextView alibabaUnread;

    private void initConversationServiceAndListener() {
        mConversationUnreadChangeListener = new IYWConversationUnreadChangeListener() {

            //当未读数发生变化时会回调该方法，开发者可以在该方法中更新未读数
            @Override
            public void onUnreadChange() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoginSampleHelper loginHelper = LoginSampleHelper.getInstance();
                        final YWIMKit imKit = loginHelper.getIMKit();
                        mConversationService = imKit.getConversationService();
                        //获取当前登录用户的所有未读数
                        int unReadCount = mConversationService.getAllUnreadCount();
                        //设置桌面角标的未读数
                        mIMKit.setShortcutBadger(unReadCount);
                        if (null != alibabaUnread) {
                            if (unReadCount > 0) {
                                alibabaUnread.setVisibility(View.VISIBLE);
                            } else {
                                alibabaUnread.setVisibility(View.INVISIBLE);
                            }
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
                WxLog.i(Constants.TAG, "onInvite");
            }

            @Override
            public void onUserJoin(YWTribe tribe, YWTribeMember user) {
                //用户user加入群tribe
            }

            @Override
            public void onUserQuit(YWTribe tribe, YWTribeMember user) {
                //用户user退出群tribe
            }

            @Override
            public void onUserRemoved(YWTribe tribe, YWTribeMember user) {
                //用户user被提出群tribe
            }

            @Override
            public void onTribeDestroyed(YWTribe tribe) {
                //群组tribe被解散了
            }

            @Override
            public void onTribeInfoUpdated(YWTribe tribe) {
                //群组tribe的信息更新了（群名称、群公告、群校验模式修改了）
            }

            @Override
            public void onTribeManagerChanged(YWTribe tribe, YWTribeMember user) {
                //用户user被设置为群管理员或者被取消管理员
            }

            @Override
            public void onTribeRoleChanged(YWTribe tribe, YWTribeMember user) {
                //用户user的群角色发生改变了
            }
        };
        mIMKit.getTribeService().addTribeListener(mTribeChangedListener);
    }

    private void setMessageLifeCycleListener() {
        mMessageLifeCycleListener = new IYWMessageLifeCycleListener() {
            /**
             * 发送消息前回调
             * @param conversation 当前消息所在会话
             * @param message      当前将要发送的消息
             * @return 需要发送的消息，若为null，则表示不发送消息
             */
            @Override
            public YWMessage onMessageLifeBeforeSend(YWConversation conversation, YWMessage message) {
                //todo 以下代码仅仅是示例，开发者无需按照以下方式设置，应该根据自己的需求对消息进行修改
                String cvsType = "单聊";
                if (conversation.getConversationType() == YWConversationType.Tribe) {
                    cvsType = "群聊：";
                }
                String msgType = "文本消息";
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_IMAGE) {
                    msgType = "图片消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GEO) {
                    msgType = "地理位置消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_AUDIO) {
                    msgType = "语音消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
                    msgType = "自定义消息";
                }

                //TODO 设置APNS Push，如果开发者需要对APNS Push进行定制可以调用message.setPushInfo(YWPushInfo)方法进行设置，如果不需要APNS Push定制则不需要调用message.setPushInfo(YWPushInfo)方法
                //TODO Demo默认发送消息不需要APNS Push定制,所以注释掉以下两行代码
//                YWPushInfo pushInfo = new YWPushInfo(1, cvsType + msgType, "dingdong", "我是自定义数据");
//                message.setPushInfo(pushInfo);

                //根据消息类型对消息进行修改，切记这里只是示例，具体怎样对消息进行修改开发者可以根据自己的需求进行处理
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT) {
                    String content = message.getContent();
                    if (content.equals("55")) {
                        message.setContent("我修改了消息内容, 原始内容：55");
                        return message;
                    } else if (content.equals("66")) {
                        YWMessage newMsg = YWMessageChannel.createTextMessage("我创建了一条新消息, 原始消息内容：66");
                        return newMsg;
                    } else if (content.equals("77")) {
                        IMNotificationUtils.getInstance().showToast(HostActivity.this, "不发送该消息，消息内容为：77");
                        return null;
                    }
                }
                return message;
            }

            /**
             * 发送消息结束后回调
             * @param message   当前发送的消息
             * @param sendState 消息发送状态，具体状态请参考{@link com.alibaba.mobileim.conversation.YWMessageType.SendState}
             */
            @Override
            public void onMessageLifeFinishSend(YWMessage message, YWMessageType.SendState sendState) {
//                IMNotificationUtils.getInstance().showToast(FragmentTabs.this, sendState.toString());
            }
        };
        mConversationService.setMessageLifeCycleListener(mMessageLifeCycleListener);
    }

    //设置连接状态的监听
    private void addConnectionListener() {
        mConnectionListener = new IYWConnectionListener() {
            @Override
            public void onDisconnect(int code, String info) {
                if (code == YWLoginCode.LOGON_FAIL_KICKOFF) {
                    //在其它终端登录，当前用户被踢下线
                    Toast.makeText(MyApplication.getContext(), "被踢下线", Toast.LENGTH_LONG).show();
                    YWLog.i("LoginSampleHelper", "被踢下线");
                    LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
//                    finish();
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
             * 是否发送消息给黑名单中的联系人，当用户发送消息给黑名单中的联系人时我们会回调该接口
             * @param conversation 当前发送消息的会话
             * @param message      要发送的消息
             * @return true：发送  false：不发送
             */
            @Override
            public boolean sendMessageToContactInBlackList(YWConversation conversation, YWMessage message) {
                //TODO 开发者可用根据自己的需求决定是否要发送该消息，SDK默认不发送
                return true;
            }
        };
        mConversationService.setSendMessageToContactInBlackListListener(mSendMessageToContactInBlackListListener);
    }

    public static final String SYSTEM_TRIBE_CONVERSATION = "sysTribe";
    public static final String SYSTEM_FRIEND_REQ_CONVERSATION = "sysfrdreq";
    public static final String RELATED_ACCOUNT = "relatedAccount";

    /**
     * 自定义会话示例展示系统通知的示例
     */
    private void initCustomConversation() {
        CustomConversationHelper.addCustomConversation(SYSTEM_FRIEND_REQ_CONVERSATION, null);
    }

    private void initTabBg() {
        if (null == tabItemBg) {
            tabItemBg = new ArrayList<>();
        }

        WeakReference<TabBean> wf = new WeakReference<TabBean>(new TabBean());
        wf.get().setDrawableId(R.drawable.tab_home_unselected);
        wf.get().setNameStr("首页");
        wf.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf.get());

        WeakReference<TabBean> wf1 = new WeakReference<TabBean>(new TabBean());
        wf1.get().setDrawableId(R.drawable.tab_addresslist_unselected);
        wf1.get().setNameStr("名片夹");
        wf1.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf1.get());

        WeakReference<TabBean> wf2 = new WeakReference<TabBean>(new TabBean());
        wf2.get().setDrawableId(R.drawable.tab_news_unselected);
        wf2.get().setNameStr("政务大厅");
        wf2.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf2.get());

        WeakReference<TabBean> wf3 = new WeakReference<TabBean>(new TabBean());
        wf3.get().setDrawableId(R.drawable.tab_person_unselected);
        wf3.get().setNameStr("我的");
        wf3.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf3.get());
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);

        setTabValue();
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
    }

    private void setTabValue() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            if (mTabTl.getTabAt(i) != null) {
                if (null == mTabTl.getTabAt(i).getCustomView()) {
                    mTabTl.getTabAt(i).setCustomView(R.layout.item_tab_home);
                }
                TextView indicator = (TextView) mTabTl.getTabAt(i).getCustomView().findViewById(R.id.tab_text);

                Drawable drawable = getResources().getDrawable(tabItemBg.get(i).getDrawableId());
                int drawableWidth = drawable.getIntrinsicWidth() + drawable.getIntrinsicWidth() / 4;
                int drawableHeight = drawable.getIntrinsicHeight() + drawable.getIntrinsicHeight() / 4;
                drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                indicator.setCompoundDrawables(null, drawable, null, null);

                if (i == 1) {
                    alibabaUnread = (TextView) mTabTl.getTabAt(i).getCustomView().findViewById(R.id.unread);
                }

                mTabTl.getTabAt(i).setTag(i);
            }
        }

    }

    private HomeFragment homeFragment;
    private Fragment conversationFragment;
    private Fragment contactsFragment;
    private ZWDTFragment zwdtFragment;
    private MineFragment mineFragment;

    private void initContent() {
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabIndicators.add("Tab " + i);
        }
        tabFragments = new ArrayList<>();
        homeFragment = HomeFragment.newInstance(tabIndicators.get(0));
        conversationFragment = mIMKit.getConversationFragment();
        contactsFragment = mIMKit.getContactsFragment();
        zwdtFragment = ZWDTFragment.newInstance(tabIndicators.get(2));
        mineFragment = MineFragment.newInstance(tabIndicators.get(3));

        resetTabFragments();
    }

    private void resetTabFragments() {
        if (null != tabFragments) {
            tabFragments.clear();
        }
        tabFragments.add(homeFragment);
        tabFragments.add(conversationFragment);
        tabFragments.add(zwdtFragment);
        tabFragments.add(mineFragment);

        contentAdapter = new ContentPagerAdapter(this, getSupportFragmentManager(), tabFragments);
        mContentVp.setAdapter(contentAdapter);

        if (mTabTl != null) {
            mTabTl.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToContact(@NonNull EventToContactList eventToContactList) {
        if (null != tabFragments) {
            tabFragments.clear();
        }

        tabFragments.add(homeFragment);
        tabFragments.add(contactsFragment);
        tabFragments.add(zwdtFragment);
        tabFragments.add(mineFragment);

        contentAdapter = new ContentPagerAdapter(this, getSupportFragmentManager(), tabFragments);
        mContentVp.setAdapter(contentAdapter);

        if (null != contentAdapter) {
            contentAdapter.notifyDataSetChanged();
        }

        initTab();
        mTabTl.setSelectedTabIndicatorHeight(1);
        mTabTl.getTabAt(1).getCustomView().setSelected(true);
        mContentVp.setCurrentItem(1);

        mTabTl.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToConversation(@NonNull EventToConversation eventToConversation) {
        reset();

        mTabTl.setSelectedTabIndicatorHeight(1);
        mTabTl.getTabAt(1).getCustomView().setSelected(true);
        mContentVp.setCurrentItem(1);

        mTabTl.setVisibility(View.VISIBLE);
    }

    private void reset() {
        resetTabFragments();
        if (null != contentAdapter) {
            contentAdapter.notifyDataSetChanged();
        }
        initTab();
    }

    private boolean isLoginSuc = false;
    private boolean isRegisterSuc = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        isLoginSuc = true;

        if (StringUtil.isEmpty(eventLoginSuc.getToActivity())) {
            return;
        }
        if (eventLoginSuc.getToActivity().equals(Constants.TO_CARDCLIP)) {
            mTabTl.setSelectedTabIndicatorHeight(1);
            mTabTl.getTabAt(1).getCustomView().setSelected(true);
            mContentVp.setCurrentItem(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThisActivity(@NonNull EventRegisterSuc eventRegisterSuc) {
        isRegisterSuc = true;

        //登录成功，切换到首页
        mTabTl.setSelectedTabIndicatorHeight(0);
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
        mContentVp.setCurrentItem(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sysNewsNum(@NonNull EventSysNewsNum sysNewsNum) {
        setTabValue();
    }

    class ContentPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments = null;
        private Context context;

        public ContentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.context = context;
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Override
    protected void onResume() {
        //resume时需要检查全局未读消息数并做处理，因为离开此界面时删除了全局消息监听器
        mConversationUnreadChangeListener.onUnreadChange();

        if (isLoginSuc) {
            isLoginSuc = false;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        if (null != mTabTl && null != mContentVp && !SystemUtil.getInstance().getIsLogin()) {
            mTabTl.setSelectedTabIndicatorHeight(0);
            mTabTl.getTabAt(0).getCustomView().setSelected(true);
            mContentVp.setCurrentItem(0);
        }

        setTabValue();

        if (isRegisterSuc) {
            registerAndAlibabaLogin();
        }

        super.onResume();
    }

    private void registerAndAlibabaLogin() {
        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response, AlibabaUser result) {
                //注册成功返回
//                SystemUtil.getInstance().alibabaLogin();
                alibabalogin();
            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onError(Response response) {

            }
        });
    }

    private void alibabalogin() {
        LoginSampleHelper.getInstance().login_Sample(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY, new IWxCallback() {
                    @Override
                    public void onSuccess(Object... arg0) {
                        SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                                IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);

                        SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_ISACCOUNTCHANGED, true);

                        isRegisterSuc = false;
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onProgress(int arg0) {

                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        SystemUtil.getInstance().taobaouser(1, new MyCallBack<AlibabaUser>() {
                            @Override
                            public void onLoadingBefore(Request request) {

                            }

                            @Override
                            public void onSuccess(Response response, AlibabaUser result) {
                                SystemUtil.getInstance().saveLoginInfoToLocal(IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_USER_ID),
                                        IMPrefsTools.getStringPrefs(MyApplication.getContext(), Constants.KEY_PASSWORD), Constants.APP_KEY);
                            }

                            @Override
                            public void onFailure(Request request, Exception e) {
                                EventBusUtils.post(new EventTaobaoUser());
                            }

                            @Override
                            public void onError(Response response) {

                            }
                        });
                    }
                });
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
        EventBusUtils.unregister(this);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
