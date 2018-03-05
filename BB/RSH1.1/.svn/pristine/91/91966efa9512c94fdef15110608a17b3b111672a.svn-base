package rongshanghui.tastebychance.com.rongshanghui.register.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.RegionRes;
//import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCode2Info;

/**
 * Created by shenbinghong on 2017/11/24.
 */

public class AscriptionGroupAdapter extends BaseAdapter {
    private Context context;
    private List<RegionRes.DataBean> list;

    public AscriptionGroupAdapter(Context context, List<RegionRes.DataBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<RegionRes.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_sht_sh_ascription_group, null);
        }

        ViewHolder holder = ViewHolder.getHolder(convertView);
        // 设置数据
        RegionRes.DataBean region = list.get(position);
        holder.sht_sh_ascription_tv.setText(region.getRegion_name());
        holder.sht_sh_ascription_tv.setBackgroundColor(Color.WHITE);

        if (position == selectItem) {
            holder.sht_sh_ascription_tv.setTextColor(context.getResources().getColor(R.color.font_blue));
        } else {
            holder.sht_sh_ascription_tv.setTextColor(context.getResources().getColor(R.color.textgray));
        }

        return convertView;
    }

    public RegionRes.DataBean getSelectedData() {
        if (selectItem != -1 && selectItem < getCount()) {
            return (RegionRes.DataBean) getItem(selectItem);
        }
        return null;
    }

    private int selectItem = -1;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    static class ViewHolder {
        // 所有控件对象引用
        public TextView sht_sh_ascription_tv;

        public ViewHolder(View convertView) {
            sht_sh_ascription_tv = (TextView) convertView.findViewById(R.id.item_mine_industry_tv);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
