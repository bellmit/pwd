package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.HostActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.bean.HDLYBean;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ckhf.CKHFListActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ly.LYActivity;

/**
 * Created by shenbinghong on 2018/1/16.
 */

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<HDLYBean> groups;

    public ExpandListViewAdapter(Context context, List<HDLYBean> groups) {
        this.context = context;
        this.groups = groups;
    }

    public List<HDLYBean> getGroups() {
        return groups;
    }

    public void addList(List<HDLYBean> groups) {
        this.groups.addAll(groups);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return groups.get(groupPosition).getItems().size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
//        return groups.get(groupPosition).getItems().get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        View groupView = convertView;
        if (groupView == null) {
            groupView = LayoutInflater.from(context).inflate(R.layout.item_hdly_group, null);
            groupHolder = new GroupHolder();
            groupHolder.nameTv = (TextView) groupView.findViewById(R.id.item_hdly_group_tv);
            groupView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) groupView.getTag();
        }

//        groupHolder.nameTv.setText(groups.get(groupPosition).getName());
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (groupPosition == 0) {
            View childView = null;
            if (childView == null) {
                childView = LayoutInflater.from(context).inflate(R.layout.item_hdly_child1, null);
                childHolder = new ChildHolder();
                childHolder.imgIv = (ImageView) childView.findViewById(R.id.hdly_child1__bg_iv);
                childHolder.ckhfTv = (TextView) childView.findViewById(R.id.hdly_child1_ckhf_tv);
                childHolder.lyTv = (TextView) childView.findViewById(R.id.hdly_child1_ly_tv);
                childHolder.unreadTv = (TextView) childView.findViewById(R.id.unread);
                childView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) childView.getTag();
            }

            if (childPosition == 0) {
                /*PicassoUtils.getinstance().loadNormalByPath(context, groups.get(groupPosition).getItems().get(childPosition).getAvator(), childHolder.imgIv);
                if (groups.get(groupPosition).getItems().get(childPosition).getHuifuNum() > 0) {
                    childHolder.unreadTv.setVisibility(View.VISIBLE);
                } else {
                    childHolder.unreadTv.setVisibility(View.INVISIBLE);
                }
                childHolder.ckhfTv.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if(!SystemUtil.getInstance().getIsLogin()){
                            SystemUtil.getInstance().intentToLoginActivity(context, Constants.TO_HDLY_CKHF);
                        }else {
                            context.startActivity(new Intent(context, CKHFListActivity.class));
                        }
                    }
                });
                childHolder.lyTv.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if(!SystemUtil.getInstance().getIsLogin()){
                            SystemUtil.getInstance().intentToLoginActivity(context, Constants.TO_HDLY_LY);
                        }else {
                            context.startActivity(new Intent(context, LYActivity.class));
                        }
                    }
                });*/
            }

            convertView = childView;
        } else {
            View childView1 = null;
            if (childView1 == null) {
                childHolder = new ChildHolder();
                childView1 = LayoutInflater.from(context).inflate(R.layout.item_hdly_child2, null);
                childHolder.nameTv = (TextView) childView1.findViewById(R.id.hdly_child2_name_tv);
                childHolder.timeTv = (TextView) childView1.findViewById(R.id.hdly_child2_time_tv);
                childView1.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) childView1.getTag();
            }

            /*if (childHolder.nameTv != null) {
                childHolder.timeTv.setText(groups.get(groupPosition).getItems().get(childPosition).getTime());
            }
            if (childHolder.timeTv != null) {
                childHolder.nameTv.setText(groups.get(groupPosition).getItems().get(childPosition).getName());
            }*/

            convertView = childView1;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 根据方法名，此处应该表示二级条目是否可以被点击   先返回true
        return true;
    }

    class GroupHolder {
        private TextView nameTv;
    }

    class ChildHolder {
        private ImageView imgIv;
        private TextView ckhfTv, unreadTv, lyTv;

        private TextView nameTv;
        private TextView timeTv;
    }
}
