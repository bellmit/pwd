package rongshanghui.tastebychance.com.rongshanghui.login.areacode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean.AreaCodeInfo;

/**
 * 区号
 *
 * @author shenbh
 * @date 2017/9/5
 */
public class AreaCodeAdapter extends BaseAdapter {

    private Context context;
    private List<AreaCodeInfo> list;

    public AreaCodeAdapter(Context context, List<AreaCodeInfo> list) {
        super();
        this.context = context;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<AreaCodeInfo> list) {
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
            convertView = View.inflate(context, R.layout.item_area, null);
        }

        ViewHolder holder = ViewHolder.getHolder(convertView);
        // 设置数据
        AreaCodeInfo areaCodeInfo = list.get(position);
        holder.area_name_tv.setText(areaCodeInfo.getName());
        holder.area_code_tv.setText(areaCodeInfo.getCode());
        String currentWord = areaCodeInfo.getFirstWord();

        if (position > 0) {
            //获取上一个item的首字母
            String lastWord = list.get(position - 1).getFirstWord();
            //拿当前的首字母和上一个首字母比较
            if (currentWord.equals(lastWord)) {
                //说明首字母相同，需要隐藏当前item的first_word
                holder.first_word.setVisibility(View.GONE);
                holder.first_word_border.setVisibility(View.GONE);
            } else {
                //不一样，需要显示当前的首字母
                //由于布局是复用的，所以在需要显示的时候，再次将first_word设置为可见
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word_border.setVisibility(View.VISIBLE);
                holder.first_word.setText(currentWord);
            }
        } else {
            holder.first_word.setVisibility(View.VISIBLE);
            holder.first_word_border.setVisibility(View.VISIBLE);
            holder.first_word.setText(currentWord);
        }

        if (position == selectItem) {
            holder.item_area_child_rl.setBackgroundColor(context.getResources().getColor(R.color.bg_gray));
        } else {
            holder.item_area_child_rl.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    public AreaCodeInfo getSelectedData() {
        if (selectItem != -1 && selectItem < getCount()) {
            return (AreaCodeInfo) getItem(selectItem);
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
        public TextView first_word, area_name_tv, area_code_tv;
        public View first_word_border;
        private RelativeLayout item_area_child_rl;

        public ViewHolder(View convertView) {
            first_word = (TextView) convertView.findViewById(R.id.area_code_firstword_tv);
            first_word_border = convertView.findViewById(R.id.first_word_border);
            area_name_tv = (TextView) convertView.findViewById(R.id.area_name_tv);
            area_code_tv = (TextView) convertView.findViewById(R.id.area_code_tv);
            item_area_child_rl = (RelativeLayout) convertView.findViewById(R.id.item_area_child_rl);
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
