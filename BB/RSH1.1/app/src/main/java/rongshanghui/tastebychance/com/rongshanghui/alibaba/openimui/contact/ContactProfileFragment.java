package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWConstants;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.cloud.contact.YWProfileInfo;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.contact.IYWDBContact;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;
import com.alibaba.mobileim.utility.IMNotificationUtils;


import java.lang.ref.WeakReference;
import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;


public class ContactProfileFragment extends Fragment implements OnClickListener {


    private View view;

    private YWContactHeadLoadHelper mHelper;
    private YWProfileInfo mYWProfileInfo;
    private LinearLayout mBottomLayout;
    private String APPKEY;
    private String mUserId;
    private String TAG = ContactProfileFragment.class.getSimpleName();
    private IYWContactCacheUpdateListener mContactCacheUpdateListener;
    private List<IYWDBContact> contactsFromCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = LoginSampleHelper.getInstance().getIMKit().getIMCore().getLoginUserId();
        if (TextUtils.isEmpty(mUserId)) {
            YWLog.i(TAG, "user not login");
        }
        APPKEY = getIMkit().getIMCore().getAppKey();


    }

    private void addContactCacheUpdateListener() {
        if (mContactCacheUpdateListener != null && LoginSampleHelper.getInstance().getIMKit() != null)
            LoginSampleHelper.getInstance().getIMKit().getContactService().addContactCacheUpdateListener(mContactCacheUpdateListener);
    }

    private void removeContactCacheUpdateListener() {
        if (mContactCacheUpdateListener != null && LoginSampleHelper.getInstance().getIMKit() != null)
            LoginSampleHelper.getInstance().getIMKit().getContactService().removeContactCacheUpdateListener(mContactCacheUpdateListener);
    }

    private void initContactCacheUpdateListener() {
        mContactCacheUpdateListener = new IYWContactCacheUpdateListener() {

            /**
             * ????????????????????????(???????????????????????????????????????????????????)???????????????????????????????????????UI
             * todo ????????????UI???????????? ???????????????????????????
             *
             * @param currentUserid                 ??????????????????
             * @param currentAppkey                 ??????Appkey
             */
            @Override
            public void onFriendCacheUpdate(String currentUserid, String currentAppkey) {
                IParent superParent = getSuperParent();
                if (superParent != null) {
                    checkIfHasContact(superParent.getYWProfileInfo());
                    initData();
                }


            }
        };
    }

    private void checkIfHasContact(YWProfileInfo mYWProfileInfo) {
        //??????hasContactAlready???contactsFromCache???Fragment??????????????????
        IParent superParent = getSuperParent();
        if (superParent != null) {
            contactsFromCache = getContactService().getContactsFromCache();
            for (IYWDBContact contact : contactsFromCache) {
                if (contact.getUserId().equals(mYWProfileInfo.userId)) {
                    superParent.setHasContactAlready(true);
                    return;
                }

            }
            superParent.setHasContactAlready(false);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            initContactCacheUpdateListener();
        }
        addContactCacheUpdateListener();

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.demo_fragment_contact_profile, null);
        init();
        return view;
    }

    private void initTitle() {

        RelativeLayout titleBar = (RelativeLayout) view.findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setVisibility(View.VISIBLE);
//		titleBar.setBackgroundColor(Color.parseColor("#25498F"));
        TextView leftButton = (TextView) view.findViewById(R.id.left_button);
        leftButton.setText("");
        leftButton.setTextColor(Color.WHITE);
        leftButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.aliwx_common_back_btn_bg_white, 0, 0, 0);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView title = (TextView) view.findViewById(R.id.title_self_title);
        title.setTextColor(Color.WHITE);
        title.setText("????????????");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeContactCacheUpdateListener();
    }

    private void init() {
        initTitle();
        initSearchResultView();
        initHelper();
        initData();

    }

    private IParent getSuperParent() {
        IParent superParent = (IParent) getActivity();
        return superParent;
    }

    private IYWContactService getContactService() {
        IYWContactService contactService = LoginSampleHelper.getInstance().getIMKit().getContactService();
        return contactService;
    }

    private YWIMKit getIMkit() {
        YWIMKit imkit = LoginSampleHelper.getInstance().getIMKit();
        return imkit;
    }

    private void initData() {
        IParent superParent = getSuperParent();
        mYWProfileInfo = superParent.getYWProfileInfo();
        hasContactAlready = superParent.isHasContactAlready();
        clearProfileResult();
        showProfileResult(mYWProfileInfo);
    }

    private void initHelper() {
        mHelper = new YWContactHeadLoadHelper(this.getActivity(), null, getIMkit().getUserContext());
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.aliwx_bottom_btn) {
            getSuperParent().addFragment(new AddContactFragment(), true);
        } else if (i == R.id.aliwx_btn_send_message) {
            sendMessage(mYWProfileInfo);
        } else if (i == R.id.aliwx_persondinfo_btn) {

            //????????????
            if (StringUtil.isEmpty(SystemUtil.getInstance().getStrFromSP("alibaba_userid"))) {
                return;
            }
            WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
            wf.get().setUrl(UrlManager.URL_WEB_ALIBABAUSERCARD + SystemUtil.getInstance().getStrFromSP("alibaba_userid"));
            wf.get().setTitle("??????");
            SystemUtil.getInstance().intentToWeb2Activity(getActivity(), wf.get());
        }
    }


    public boolean onBackPressed() {
        getSuperParent().finish(false);
        return true;
    }

    //--------------------------[??????????????????????????????]????????????

    private View parallaxView;
    private Button mBottomButton;
    private Button mSendMessageBtn;
    private Button mPersonInfo;
    private ImageView mHeadBgView;
    private ImageView mHeadView;
    private TextView mSelfDescview;
    private boolean hasContactAlready;

    private void initSearchResultView() {
        parallaxView = view.findViewById(R.id.aliwx_parallax_view);
        parallaxView.setVisibility(View.GONE);
        mHeadBgView = (ImageView) view.findViewById(R.id.aliwx_block_bg);
        mHeadBgView.setImageResource(R.drawable.aliwx_head_bg_0);
        mHeadView = (ImageView) view.findViewById(R.id.aliwx_people_head);
        mSelfDescview = (TextView) view.findViewById(R.id.aliwx_people_desc);
        mSelfDescview.setMaxLines(2); // ?????????????????????????????????

        mBottomLayout = (LinearLayout) view.findViewById(R.id.aliwx_bottom_layout);
        mBottomButton = (Button) view.findViewById(R.id.aliwx_bottom_btn);
        mBottomButton.setOnClickListener(this);
        mSendMessageBtn = (Button) view.findViewById(R.id.aliwx_btn_send_message);
        mSendMessageBtn.setOnClickListener(this);

        mPersonInfo = (Button) view.findViewById(R.id.aliwx_persondinfo_btn);
        mPersonInfo.setOnClickListener(this);
    }

    public void clearProfileResult() {
        parallaxView.setVisibility(View.GONE);
    }

    public void showProfileResult(final YWProfileInfo lmYWProfileInfo) {


        if (lmYWProfileInfo == null || TextUtils.isEmpty(lmYWProfileInfo.userId)) {
            IMNotificationUtils.getInstance().showToast(ContactProfileFragment.this.getActivity(), "???????????????????????????????????????");
            return;
        }

        mYWProfileInfo = lmYWProfileInfo;
        setProfileResult(lmYWProfileInfo);
        setBottomView(lmYWProfileInfo);
        parallaxView.setVisibility(View.VISIBLE);

    }

    public void setProfileResult(YWProfileInfo profileInfo) {
        if (profileInfo != null) {
            RelativeLayout useridLayout = (RelativeLayout) view.findViewById(R.id.aliwx_userid_layout);
            if (TextUtils.isEmpty(profileInfo.userId)) {
                useridLayout.setVisibility(View.GONE);
            } else {
                useridLayout.setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(R.id.aliwx_userid_text);
                textView.setText(new StringBuilder("  ").append(profileInfo.userId));

                //????????????
                SystemUtil.getInstance().saveStrToSP("alibaba_userid", mYWProfileInfo.userId);
            }
            RelativeLayout settingRemarkNameLayout = (RelativeLayout) view.findViewById(R.id.aliwx_remark_name_layout);
            if (TextUtils.isEmpty(profileInfo.nick)) {
                settingRemarkNameLayout.setVisibility(View.GONE);
            } else {
                settingRemarkNameLayout.setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(R.id.aliwx_remark_name_text);
                textView.setText(new StringBuilder("  ").append(profileInfo.nick));
            }
//			if (TextUtils.isEmpty(profileInfo.icon)) {
//				mHeadView.setImageResource(R.drawable.aliwx_head_default);
//			} else if(!TextUtils.isEmpty(profileInfo.userId)&&!TextUtils.isEmpty(profileInfo.icon)) {
            mHelper.setHeadView(mHeadView, profileInfo.userId, APPKEY, true);
//			}
        }
    }

    private void sendMessage(YWProfileInfo mYWProfileInfo) {
        if (mYWProfileInfo.userId.equals(LoginSampleHelper.getInstance().getIMKit().getIMCore().getLoginUserId())) {
            IMNotificationUtils.getInstance().showToast(this.getActivity(), "????????????????????????????????????");
            return;
        }
        if (APPKEY.equals(YWConstants.YWSDKAppKey)) {
            //TB????????????????????????
            EServiceContact eServiceContact = new EServiceContact(mYWProfileInfo.userId, 0);//
            Intent intent = getIMkit().getChattingActivityIntent(eServiceContact);
            startActivity(intent);
        } else {
            Intent intent = getIMkit().getChattingActivityIntent(mYWProfileInfo.userId, APPKEY);
            startActivity(intent);
        }
    }


    private void setBottomView(YWProfileInfo lmYWProfileInfo) {
        /* ??????????????????????????????????????????????????????*/


        if (mYWProfileInfo.userId.equals(LoginSampleHelper.getInstance().getIMKit().getIMCore().getLoginUserId())) {
            mBottomLayout.setVisibility(View.GONE);
            IMNotificationUtils.getInstance().showToast(this.getActivity(), "???????????????");
            return;
        } else if (hasContactAlready) {
            mBottomLayout.setVisibility(View.VISIBLE);
            mBottomButton.setVisibility(View.GONE);
//			mSendMessageBtn.setVisibility(View.VISIBLE);
            //TODO:?????????????????????????????????
            mSendMessageBtn.setVisibility(View.GONE);
            mPersonInfo.setVisibility(View.VISIBLE);
//			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSendMessageBtn
//					.getLayoutParams();
//			layoutParams.width = getResources().getDimensionPixelSize(
//					R.dimen.aliwx_friend_info_btn_width);
//			layoutParams.weight = 0;
//			mSendMessageBtn.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPersonInfo
                    .getLayoutParams();
            layoutParams.width = getResources().getDimensionPixelSize(
                    R.dimen.aliwx_friend_info_btn_width);
            layoutParams.weight = 1;
            mPersonInfo.setLayoutParams(layoutParams);
//			ToastHelper.showToastMsg(this.getActivity(), "??????????????????");
        } else {
            mBottomLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams bLayoutParams = (LinearLayout.LayoutParams) mBottomButton
                    .getLayoutParams();
            bLayoutParams.width = 0;
            bLayoutParams.weight = 1;
            mBottomButton.setLayoutParams(bLayoutParams);
            mBottomButton.setVisibility(View.VISIBLE);

//			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSendMessageBtn
//					.getLayoutParams();
//			layoutParams.width = 0;
//			layoutParams.weight = 1;
//			mSendMessageBtn.setLayoutParams(layoutParams);
//			mSendMessageBtn.setVisibility(View.VISIBLE);
            //TODO:?????????????????????????????????
            mSendMessageBtn.setVisibility(View.GONE);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPersonInfo
                    .getLayoutParams();
            layoutParams.width = 0;
            layoutParams.weight = 1;
            mPersonInfo.setLayoutParams(layoutParams);
            mPersonInfo.setVisibility(View.VISIBLE);
        }

    }

}
