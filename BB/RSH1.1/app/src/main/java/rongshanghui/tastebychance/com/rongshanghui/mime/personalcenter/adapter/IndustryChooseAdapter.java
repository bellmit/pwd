package rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;
import rongshanghui.tastebychance.com.rongshanghui.mime.personalcenter.bean.IndustryInfo;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/28 22:43
 * 修改人：Administrator
 * 修改时间：2017/11/28 22:43
 * 修改备注：
 */

public class IndustryChooseAdapter extends BaseAdapter {
    private Context context;
    private List<IndustryInfo> list;

    public IndustryChooseAdapter(Context context, List<IndustryInfo> industryInfos) {
        this.context = context;
        this.list = industryInfos;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_mine_industrychoose, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        IndustryInfo industryInfo = list.get(position);
        holder.textView.setText(industryInfo.getName());

        if (position == selectItem) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.font_blue));
        } else {
            holder.textView.setTextColor(context.getResources().getColor(R.color.font_gray));
        }

        return convertView;
    }

    private int selectItem = -1;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public IndustryInfo getSelectedData() {
        if (selectItem != -1 && selectItem < getCount()) {
            return (IndustryInfo) getItem(selectItem);
        }
        return null;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<IndustryInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView textView;
        private LinearLayout ll;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.item_mine_industry_tv);
            ll = (LinearLayout) convertView.findViewById(R.id.item_mine_industry_ll);
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
