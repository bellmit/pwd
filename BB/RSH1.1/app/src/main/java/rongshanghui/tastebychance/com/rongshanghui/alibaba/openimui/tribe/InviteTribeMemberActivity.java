package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.tribe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeType;
import com.alibaba.mobileim.tribe.IYWTribeService;
import com.alibaba.mobileim.ui.contact.ContactsFragment;
import com.alibaba.mobileim.ui.contact.adapter.ContactsAdapter;
import com.alibaba.mobileim.utility.IMNotificationUtils;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;

import java.util.List;

public class InviteTribeMemberActivity extends FragmentActivity {

    private static final String TAG = "InviteTribeMemberActivity";

    private YWIMKit mIMKit;
    private IYWTribeService mTribeService;
    private long mTribeId;

    private ContactsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_invite_tribe_member);

        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        mTribeService = mIMKit.getTribeService();
        mTribeId = getIntent().getLongExtra(TribeConstants.TRIBE_ID, 0);

        initTitle();
        createFragment();
        YWLog.i(TAG, "onCreate");
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
        titleView.setText("???????????????");


        final YWTribeType tribeType = mTribeService.getTribe(mTribeId).getTribeType();
        TextView rightButton = (TextView) findViewById(R.id.right_button);
        rightButton.setText("??????");
        rightButton.setTextColor(Color.WHITE);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsAdapter adapter = mFragment.getContactsAdapter();
                List<IYWContact> list = adapter.getSelectedList();
                if (list != null && list.size() > 0) {
                    mTribeService.inviteMembers(mTribeId, list, new IWxCallback() {
                        @Override
                        public void onSuccess(Object... result) {
                            Integer retCode = (Integer) result[0];
                            if (retCode == 0) {
                                if (tribeType == YWTribeType.CHATTING_GROUP) {
                                    IMNotificationUtils.getInstance().showToast(InviteTribeMemberActivity.this, "????????????????????????");
                                } else {
                                    IMNotificationUtils.getInstance().showToast(InviteTribeMemberActivity.this, "????????????????????????");
                                }
                                finish();
                            }
                        }

                        @Override
                        public void onError(int code, String info) {
                            IMNotificationUtils.getInstance().showToast(InviteTribeMemberActivity.this, "????????????????????????code = " + code + ", info = " + info);
                        }

                        @Override
                        public void onProgress(int progress) {

                        }
                    });
                }
            }
        });
    }

    private void createFragment() {
        mFragment = mIMKit.getContactsFragment();
        Bundle bundle = mFragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(TribeConstants.TRIBE_OP, TribeConstants.TRIBE_INVITE);
        bundle.putLong(TribeConstants.TRIBE_ID, mTribeId);
        mFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contact_list_container, mFragment).commit();
    }
}
