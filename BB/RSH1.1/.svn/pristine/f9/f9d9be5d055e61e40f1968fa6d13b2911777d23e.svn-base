package rongshanghui.tastebychance.com.rongshanghui.common.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.util.Constants;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/22 14:55
 * 修改人：Administrator
 * 修改时间：2017/11/22 14:55
 * 修改备注：
 */

public abstract class ExpandAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected ArrayList<HashMap<String, Object>> mDatas;
    protected final int mGroupLayoutId;
    protected final int mItemLayoutId;

    public ExpandAdapter(Context mContext, ArrayList<HashMap<String, Object>> mDatas, int mGroupLayoutId, int mItemLayoutId) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mGroupLayoutId = mGroupLayoutId;
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder groupViewHolder = getGroupViewHolder(position, convertView, parent);
        final ViewHolder childViewHolder = getChildViewHolder(position, convertView, parent);

        View view = convertView;
        switch (getItemViewType(position)) {
            case Constants.GROUP:
                convertGroup(groupViewHolder, (HashMap<String, Object>) getItem(position));
                view = groupViewHolder.getConvertView();
                break;
            case Constants.ITEM:
                convertChild(childViewHolder, (HashMap<String, Object>) getItem(position));
                view = childViewHolder.getConvertView();
                break;
        }

        return view;
    }

    protected abstract void convertGroup(ViewHolder viewHolder, HashMap<String, Object> item);

    protected abstract void convertChild(ViewHolder viewHolder, HashMap<String, Object> item);

    private ViewHolder getGroupViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mGroupLayoutId, position);
    }

    @Override
    public int getItemViewType(int position) {
        HashMap<String, Object> map = mDatas.get(position);
        return (int) map.get(Constants.TYPE);
    }

    private ViewHolder getChildViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }
}
