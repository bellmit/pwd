package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMTribeAtPageUI;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeMember;
import com.alibaba.mobileim.kit.chat.ChattingFragment;
import com.alibaba.mobileim.ui.WxChattingActvity;

import rongshanghui.tastebychance.com.rongshanghui.R;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/10/23.
 */
public class SendAtMsgDetailUISample extends IMTribeAtPageUI {

    public SendAtMsgDetailUISample(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomTitle(final Fragment fragment, final Context context, LayoutInflater layoutInflater) {
        RelativeLayout customView = (RelativeLayout) layoutInflater
                .inflate(R.layout.demo_custom_at_msg_titlebar, null);
        customView.setBackgroundColor(context.getResources().getColor(R.color.aliwx_common_bg_blue_color));
        TextView title = (TextView) customView.findViewById(R.id.title_txt);
        title.setText(context.getResources().getString(R.string.aliwx_at_message_detail_title));
        title.setTextColor(Color.WHITE);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context, "click ", Toast.LENGTH_SHORT).show();

            }
        });
        TextView backButton = (TextView) customView.findViewById(R.id.left_button);
        backButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.demo_common_back_btn_white, 0, 0, 0);
        backButton.setText(context.getResources().getString(R.string.aliwx_title_back));
        backButton.setTextColor(Color.WHITE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragment.getActivity().finish();
            }
        });
        backButton.setVisibility(View.VISIBLE);
        return customView;
    }

    /**
     * ????????????@?????????Intent
     *
     * @param context          ????????????????????????
     * @param reAtTribeMembers ??????@??????????????????(???????????????Intent???????????????????????????List??????)
     * @param appKey           ?????????????????????appKey
     * @param userId           ?????????????????????userId
     * @return
     */
    @Override
    public Intent getAtAgainIntent(Context context, ArrayList<YWTribeMember> reAtTribeMembers, String appKey, String userId, long tribeId) {
        Intent intent = new Intent(context, WxChattingActvity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ChattingFragment.SEND_AT_MSG_UNREADLIST, reAtTribeMembers);
        return intent;
    }
}
