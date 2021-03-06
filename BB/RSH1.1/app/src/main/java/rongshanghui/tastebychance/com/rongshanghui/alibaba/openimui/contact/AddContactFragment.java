package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.channel.cloud.contact.YWProfileInfo;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.utility.IMNotificationUtils;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;


public class AddContactFragment extends Fragment implements OnClickListener {

    private static final int TIME_OUT = 10;
    private ProgressDialog mProgressView;
    private EditText mAddFriendMessageEditText;
    private volatile boolean isStop;

    private View view;
    private boolean isFinishing;

    private String TAG = AddContactFragment.class.getSimpleName();
    private String mAppKey;
    private String mUserId;
    private String mNickName;
    private String mMsg;
    private TextView rightButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.demo_fragment_add_contact, null);
        init();

        return view;
    }

    private void initTitle() {

        RelativeLayout titleBar = (RelativeLayout) view.findViewById(R.id.title_bar);
        View customTitleView = null;
        if (customTitleView != null && titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
            titleBar.removeAllViews();
            titleBar.addView(customTitleView, customTitleView.getLayoutParams());
        } else {
            TextView titleView = (TextView) view.findViewById(R.id.title_self_title);
            TextView leftButton = (TextView) view.findViewById(R.id.left_button);
            EditText addFriendMessage = (EditText) view.findViewById(R.id.aliwx_add_friend_message);
            addFriendMessage.setText("??????");
            addFriendMessage.setImeOptions(EditorInfo.IME_ACTION_DONE);
            addFriendMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                        sendAddContactRequest();
                    }
                    return false;
                }
            });

            leftButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            leftButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.aliwx_common_back_btn_bg_white, 0, 0, 0);
            titleBar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            titleView.setTextColor(Color.WHITE);
            titleView.setText("????????????");
            titleBar.setVisibility(View.VISIBLE);
            rightButton = (TextView) view.findViewById(R.id.right_button);
            rightButton.setTextColor(Color.WHITE);
            rightButton.setText("??????");
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAddContactRequest();
                }
            });
        }

    }

    private void sendAddContactRequest() {
        sendAddContactRequest(mUserId, mAppKey, mNickName, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                YWLog.i(TAG, "???????????????????????????,  id = " + mUserId + ", appkey = " + mAppKey + ", mMsg = " + mMsg);
                IMNotificationUtils.getInstance().showToast(getActivity(), "???????????????????????????,  id = " + mUserId + ", appkey = " + mAppKey);
                cancelProgress();
                getSuperParent().finish(false);
            }

            @Override
            public void onError(int code, String info) {
                YWLog.i(TAG, "?????????????????????code = " + code + ", info = " + info);
                IMNotificationUtils.getInstance().showToast(getActivity(), "?????????????????????code = " + code + ", info = " + info);
                cancelProgress();
            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }


    private void sendAddContactRequest(String userId, String appKey, String nickName, IWxCallback callback) {
        mMsg = mAddFriendMessageEditText.getText().toString();
        showProgress();
        getContactService().addContact(userId, appKey, nickName, mMsg, callback);
    }

    private IParent getSuperParent() {
        IParent superParent = (IParent) getActivity();
        return superParent;
    }

    private IYWContactService getContactService() {
        IYWContactService contactService = LoginSampleHelper.getInstance().getIMKit().getContactService();
        return contactService;
    }

    private void init() {
        initTitle();
        initAddContactView();
        initData();

    }

    private void initData() {
        IParent superParent = getSuperParent();
        YWProfileInfo ywProfileInfo = superParent.getYWProfileInfo();
        if (ywProfileInfo != null) {
            mUserId = ywProfileInfo.userId;
            mAppKey = LoginSampleHelper.getInstance().getIMKit().getIMCore().getAppKey();
        }
    }


    private void initAddContactView() {
        mAddFriendMessageEditText = (EditText) view.findViewById(R.id.aliwx_add_friend_message);
        mAddFriendMessageEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mAddFriendMessageEditText.requestFocus();
        showKeyBoard();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

    }


    public boolean onBackPressed() {
        isFinishing = true;
        if (mProgressView != null && mProgressView.isShowing()) {
            mProgressView.dismiss();
            isStop = true;
            return false;
        }
        hideKeyBoard();
        getSuperParent().finish(false);
        return true;
    }

    private void showKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showSoftInput(view, 0);
        }
    }

    protected void hideKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void showProgress() {
        if (mProgressView == null) {
            mProgressView = new ProgressDialog(this.getActivity());
            mProgressView.setMessage(getResources().getString(
                    R.string.aliwx_add_friend_processing));
            mProgressView.setIndeterminate(true);
            mProgressView.setCancelable(true);
            mProgressView.setCanceledOnTouchOutside(false);
        }
        mProgressView.show();
    }

    private void cancelProgress() {
        if (mProgressView != null && mProgressView.isShowing()) {
            mProgressView.dismiss();
        }
    }
}
