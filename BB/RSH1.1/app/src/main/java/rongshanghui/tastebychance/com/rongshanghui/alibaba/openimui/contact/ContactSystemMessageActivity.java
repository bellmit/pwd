package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.conversation.IYWMessageListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.gingko.presenter.contact.IContactProfileUpdateListener;
import com.alibaba.mobileim.lib.model.message.YWSystemMessage;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.demo.FragmentTabs;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;


import java.util.ArrayList;
import java.util.List;

public class ContactSystemMessageActivity extends Activity {

    private ContactSystemMessageAdapter mAdapter;
    private ListView mListView;
    private List<YWMessage> mList = new ArrayList<YWMessage>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private YWIMKit imKit;
    private YWConversation mConversation;
    private IContactProfileUpdateListener iContactProfileUpdateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_contact_activity_system_message);
        init();
    }

    private void init() {
        initTitle();
        imKit = LoginSampleHelper.getInstance().getIMKit();
        mListView = (ListView) findViewById(R.id.message_list);
        mConversation = imKit.getConversationService().getCustomConversationByConversationId(FragmentTabs.SYSTEM_FRIEND_REQ_CONVERSATION);
        mList = mConversation.getMessageLoader().loadMessage(20, null);
        mAdapter = new ContactSystemMessageAdapter(imKit.getUserContext(), this, mList);
        mListView.setAdapter(mAdapter);
        imKit.getConversationService().markReaded(mConversation);

        //???????????????????????????,????????????????????????????????????????????????????????????????????????????????????adapter
        mConversation.getMessageLoader().addMessageListener(mMessageListener);

        iContactProfileUpdateListener = new IContactProfileUpdateListener() {
            @Override
            public void onProfileUpdate() {

            }

            @Override
            public void onProfileUpdate(String userid, String appkey) {
                refreshAdapter();
            }
        };
        LoginSampleHelper.getInstance().getIMKit().getContactService().addProfileUpdateListener(iContactProfileUpdateListener);

    }


    IYWMessageListener mMessageListener = new IYWMessageListener() {
        @Override
        public void onItemUpdated() {  //?????????????????????????????????????????????????????????????????????????????????????????????
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChangedWithAsyncLoad();
                }
            });
        }

        @Override
        public void onItemComing() { //???????????????
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChangedWithAsyncLoad();
                    if (imKit != null && imKit.getConversationService() != null)
                        imKit.getConversationService().markReaded(mConversation);

                }
            });
        }

        @Override
        public void onInputStatus(byte status) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConversation.getMessageLoader().removeMessageListener(mMessageListener);

    }


    private void initTitle() {
        RelativeLayout titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#25498F"));
        titleBar.setVisibility(View.VISIBLE);

        TextView titleView = (TextView) findViewById(R.id.title_self_title);
        TextView leftButton = (TextView) findViewById(R.id.left_button);
        leftButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.demo_common_back_btn_white, 0, 0, 0);
        leftButton.setTextColor(Color.WHITE);
        leftButton.setText("??????");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleView.setTextColor(Color.WHITE);
        titleView.setText("?????????????????????");


        TextView rightButton = (TextView) findViewById(R.id.right_button);
        rightButton.setText("??????");
        rightButton.setTextColor(Color.WHITE);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConversation.getMessageLoader().deleteAllMessage();
            }
        });
    }

    private void refreshAdapter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.refreshData(mList);
            }
        });
    }

    private IYWContactService getContactService() {
        return LoginSampleHelper.getInstance().getIMKit().getContactService();
    }

    public void acceptToBecomeFriend(final YWMessage message) {
        final YWSystemMessage msg = (YWSystemMessage) message;
        if (getContactService() != null) {
            getContactService().ackAddContact(message.getAuthorUserId(), message.getAuthorAppkey(), true, "", new IWxCallback() {
                @Override
                public void onSuccess(Object... result) {
                    msg.setSubType(YWSystemMessage.SYSMSG_TYPE_AGREE);
                    refreshAdapter();
                    getContactService().updateContactSystemMessage(msg);
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

    public void refuseToBecomeFriend(YWMessage message) {
        final YWSystemMessage msg = (YWSystemMessage) message;

        if (getContactService() != null) {
            getContactService().ackAddContact(message.getAuthorUserId(), message.getAuthorAppkey(), false, "", new IWxCallback() {
                @Override
                public void onSuccess(Object... result) {
                    msg.setSubType(YWSystemMessage.SYSMSG_TYPE_IGNORE);
                    refreshAdapter();
                    getContactService().updateContactSystemMessage(msg);
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
}