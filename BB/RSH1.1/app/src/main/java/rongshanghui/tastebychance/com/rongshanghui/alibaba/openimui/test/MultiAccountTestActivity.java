package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.ui.thridapp.ParamConstant;
import com.alibaba.mobileim.utility.YWTrackUtil;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.alibaba.openIMUIDemo.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;

import java.util.List;

/**
 * Created by mayongge on 15-9-10.
 */
public class MultiAccountTestActivity extends Activity {

    private final static String TAG = "MultiAccountTestActivity";
    private YWIMKit mIMKit;
    private Spinner mSpinner;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMKit = LoginSampleHelper.getInstance().getIMKit();
        if (mIMKit == null) {
            return;
        }
        setContentView(R.layout.demo_multi_account_test);
        TextView openChattingActivity = (TextView) findViewById(R.id.open_chatting_activity);
        openChattingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mIMKit.getChattingActivityIntent("??????");
                intent.putExtra(ParamConstant.ITEMID, "45107173274");
                startActivity(intent);
            }
        });

        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(R.id.logout);
            }
        });
        initMultiAccount();
        initTest();
        YWLog.e(TAG, "onCreate");
    }

    protected void initMultiAccount() {
        TextView addNewAccount = (TextView) findViewById(R.id.add_new_account);
        addNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
                Intent intent1 = new Intent(MultiAccountTestActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });


        mSpinner = (Spinner) findViewById(R.id.spinner);
        List<String> accountList = mIMKit.getLoginAccountList();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountList);
        //????????????????????????
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //?????????????????????spinner??????
        mSpinner.setAdapter(mAdapter);
        mSpinner.setVisibility(View.VISIBLE);//??????????????????
        mSpinner.setOnItemSelectedListener(listener);
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            String userId = parent.getItemAtPosition(position).toString();
//                mIMKit.switchAccount(userId);
//                mIMKit = YWAPI.getIMKitInstance(userId);
//                LoginSampleHelper.getInstance().setIMKit(mIMKit);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case R.id.logout: {
                AlertDialog.Builder builder = new YWAlertDialog.Builder(this);
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
                return dialog;

            }
        }
        return super.onCreateDialog(id);

    }


    public void logout() {
        // openIM SDK?????????????????????
        IYWLoginService mLoginService = mIMKit.getLoginService();
        mLoginService.logout(new IWxCallback() {
            //??????logout?????????????????????IMBaseActivity???OpenIM??????Actiivity???s
            @Override
            public void onSuccess(Object... arg0) {
                Toast.makeText(MyApplication.getContext(), "????????????", Toast.LENGTH_SHORT).show();
                String account = YWAPI.getCurrentUser();
                mIMKit = YWAPI.getIMKitInstance(account);
                LoginSampleHelper.getInstance().setIMKit(mIMKit);
//                setSpinnerItemSelected();
                //??????????????????????????????????????????????????????
                if (YWAPI.getLoginAccountList() == null || YWAPI.getLoginAccountList().size() == 0) {
                    finish();
                    LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
                    Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                setSpinnerItemSelected();
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int arg0, String arg1) {
                Toast.makeText(MyApplication.getContext(), "????????????,???????????????", Toast.LENGTH_SHORT).show();
                LoginSampleHelper.getInstance().loginOut_Sample();
            }
        });

        //???????????????????????????onSuccess?????????????????????????????????????????????IMBaseActivity????????????Finish??????????????????MainTabActivity???Finish??????
        //??????????????????????????????????????????????????????????????????????????????intent??????????????????????????????
//        Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
//        startActivity(intent);

    }

    private void setSpinnerItemSelected() {
        String account = YWAPI.getCurrentUser();
        int count = mAdapter.getCount();
        mSpinner.setAdapter(mAdapter);
//        for (int i = 0; i < count; ++i) {
//            if (account.equals(mAdapter.getItem(i).toString())) {
//                YWLog.e("MultiAccountTestActivity", "itemCount = " + mSpinner.getCount());
//                mSpinner.setSelection(i);
//                mIMKit.switchAccount(account);
//                mIMKit = YWAPI.getIMKitInstance(account);
//                LoginSampleHelper.getInstance().setIMKit(mIMKit);
//                break;
//            }
//        }
    }

    private void initTest() {

        TextView testInit = (TextView) findViewById(R.id.init_track);
        testInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YWTrackUtil.init(LoginSampleHelper.getInstance().getIMKit().getIMCore().getLoginUserId(), Constants.APP_KEY, null);
            }
        });

        TextView update = (TextView) findViewById(R.id.update_track_info);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YWTrackUtil.updateExtraInfo("", "", null);
            }
        });

        TextView test1 = (TextView) findViewById(R.id.open_test_activity1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiAccountTestActivity.this, TestActivity1.class);
                startActivity(intent);
            }
        });

        TextView test2 = (TextView) findViewById(R.id.open_test_activity2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiAccountTestActivity.this, TestActivity2.class);
                startActivity(intent);
            }
        });

        TextView test3 = (TextView) findViewById(R.id.open_conversation_activity);
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EServiceContact contact = new EServiceContact("openim????????????:android", 0);
                contact.changeToMainAccount = false;
                Intent intent = LoginSampleHelper.getInstance().getIMKit().getChattingActivityIntent(contact);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSpinnerItemSelected();
        YWLog.e(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YWLog.e(TAG, "onDestroy");
    }
}
