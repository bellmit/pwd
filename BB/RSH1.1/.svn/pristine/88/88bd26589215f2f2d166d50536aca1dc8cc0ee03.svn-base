package rongshanghui.tastebychance.com.rongshanghui.common.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 项目名称：SonChance 通用adapter,点击改变背景色
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/12 11:11
 * 修改人：Administrator
 * 修改时间：2017/9/12 11:11
 * 修改备注：
 */

public abstract class CommonChoseAbleAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    protected final int mItemRootLayoutId;

    public CommonChoseAbleAdapter(Context context, List<T> mDatas, int mItemLayoutId, int mItemRootLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
        this.mItemRootLayoutId = mItemRootLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));

        TextView tv = viewHolder.getView(mItemRootLayoutId);
        if (position == selectItem) {
            tv.setTextColor(Color.BLACK);
        } else {
            tv.setTextColor(Color.GRAY);
        }

        return viewHolder.getConvertView();
    }

    private int selectItem = -1;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    protected abstract void convert(ViewHolder viewHolder, T item);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }
}
