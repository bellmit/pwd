package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.kit.common.IMUtility;
import com.alibaba.mobileim.kit.common.YWAsyncBaseAdapter;
import com.alibaba.mobileim.kit.contact.ContactHeadLoadHelper;
import com.alibaba.mobileim.lib.model.message.YWSystemMessage;
import com.alibaba.mobileim.utility.UserContext;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import okhttp3.Call;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample.LoginSampleHelper;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageDownLoad;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactSystemMessageAdapter extends YWAsyncBaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<YWMessage> mMessageList;
    private ContactHeadLoadHelper mContactHeadLoadHelper;
    private String mAppKey;
    private UserContext userContext;

    public ContactSystemMessageAdapter(UserContext userContext, Context context, List<YWMessage> messages) {
        this.userContext = userContext;
        mContext = context;
        mMessageList = messages;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final YWIMKit ywimKit = YWAPI.getIMKitInstance(userContext.getShortUserId(), userContext.getAppkey());
        mContactHeadLoadHelper = new ContactHeadLoadHelper((Activity) context, null, ywimKit.getUserContext());
        mAppKey = LoginSampleHelper.getInstance().getIMKit().getIMCore().getAppKey();
    }

    private class ViewHolder {
        TextView showName;
        TextView message;
        TextView agreeButton;
        TextView result;
        ImageView head;
    }

    public void refreshData(List<YWMessage> list) {
        mMessageList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private IYWContactService getContactService() {
        return LoginSampleHelper.getInstance().getIMKit().getContactService();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.demo_contact_system_message_item, parent, false);
            holder = new ViewHolder();
            holder.showName = (TextView) convertView.findViewById(R.id.contact_title);
            holder.head = (ImageView) convertView.findViewById(R.id.head);
            holder.message = (TextView) convertView.findViewById(R.id.invite_message);
            holder.agreeButton = (TextView) convertView
                    .findViewById(R.id.agree);
            holder.result = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mMessageList != null) {
            final YWMessage msg = mMessageList.get(position);
            final YWSystemMessage message = (YWSystemMessage) msg;
            final String authorUserId = message.getAuthorUserId();
            final IYWContact contact = IMUtility.getContactProfileInfo(userContext, message.getAuthorUserId(), message.getAuthorAppkey());
            if (contact != null) {
                holder.showName.setText(contact.getShowName() + " 申请加你为好友");
            } else {
                holder.showName.setText(authorUserId + " 申请加你为好友");
            }
            holder.message.setText("备注: " + message.getMessageBody().getContent());
            holder.agreeButton.setText("接受");
            holder.agreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ContactSystemMessageActivity) mContext).acceptToBecomeFriend(msg);
                }
            });

            if (message.isAccepted()) {
                holder.agreeButton.setVisibility(View.GONE);
                holder.result.setVisibility(View.VISIBLE);
                holder.result.setText("已添加");
            } else if (message.isIgnored()) {
                holder.agreeButton.setVisibility(View.GONE);
                holder.result.setVisibility(View.VISIBLE);
                holder.result.setText("已忽略");
            } else {
                holder.agreeButton.setVisibility(View.VISIBLE);
                holder.result.setVisibility(View.GONE);
            }

            mContactHeadLoadHelper.setHeadView(holder.head, authorUserId, mAppKey, true);

            if (null != mContext && StringUtil.isNotEmpty(contact.getAvatarPath()) && null != holder.head) {
                PicassoUtils.getinstance().LoadImageWithWidtAndHeight(mContext, contact.getAvatarPath(), holder.head, -1, -1, PicassoUtils.PICASSO_BITMAP_SHOW_ROUND_TYPE, 10);
                System.out.println("申请人的头像链接 = " + contact.getAvatarPath());
            }

            //点击跳转查看申请人的信息
            holder.head.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (null != contact && StringUtil.isEmpty(contact.getUserId())) {
                        Toast.makeText(mContext, "无该用户信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                    wf.get().setTitle("详情");
                    wf.get().setUrl(UrlManager.URL_WEB_ALIBABAUSERCARD + contact.getUserId());
                    SystemUtil.getInstance().intentToWeb2Activity(mContext, wf.get());
                }
            });
        }
        return convertView;
    }

    @Override
    public void loadAsyncTask() {

    }

}
