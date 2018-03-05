package com.tastebychance.sfj.mine.contacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tastebychance.sfj.R;
import com.tastebychance.sfj.mine.contacts.bean.ContactShowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址列表
 *
 * @author shenbh
 * @date 2017/9/5
 */
public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private List<ContactShowBean> list;
    private boolean toShowCb = false;

    public boolean isToShowCb() {
        return toShowCb;
    }

    public void setToShowCb(boolean toShowCb) {
        this.toShowCb = toShowCb;
    }

    public ContactsAdapter(Context context, List<ContactShowBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<ContactShowBean> list) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_mine_contact, null);
        }

        ViewHolder holder = ViewHolder.getHolder(convertView);
        // 设置数据
        ContactShowBean contactsBean = list.get(position);
        holder.name.setText(contactsBean.getName());

        String currentWord = contactsBean.getFirstWord();

        if (position > 0) {
            //获取上一个item的首字母
            String lastWord = list.get(position - 1).getFirstWord();
            //拿当前的首字母和上一个首字母比较
            if (currentWord.equals(lastWord)) {
                //说明首字母相同，需要隐藏当前item的first_word
                holder.first_word.setVisibility(View.GONE);
            } else {
                //不一样，需要显示当前的首字母
                //由于布局是复用的，所以在需要显示的时候，再次将first_word设置为可见
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word.setText(currentWord);
            }
        } else {
            holder.first_word.setVisibility(View.VISIBLE);
            holder.first_word.setText(currentWord);
        }

        // 根据isSelected来设置checkbox的显示状况
        if (toShowCb) {
            holder.cb.setVisibility(View.VISIBLE);
        } else {
            holder.cb.setVisibility(View.GONE);
        }

        if (position == selectItem) {
            list.get(selectItem).setChoosed(!list.get(selectItem).isChoosed());
        }

        holder.cb.setChecked(list.get(position).isChoosed());

        //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isChoosed()) {
                    list.get(position).setChoosed(false);
                } else {
                    list.get(position).setChoosed(true);
                }

                notifyDataSetChanged();
            }
        });



        /*holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isChoosed()) {
                    list.get(position).setChoosed(false);
                } else {
                    list.get(position).setChoosed(true);
                }

                notifyDataSetChanged();
            }
        });*/

        return convertView;
    }

    private int selectItem = -1;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }


    public List<ContactShowBean> getSelectedData() {

        List<ContactShowBean> retList = new ArrayList<>();
        if (isToShowCb()){
            for (ContactShowBean contactShowBean: list) {
                if (contactShowBean.isChoosed()){
                    retList.add(contactShowBean);
                }
            }
        }
        return retList;
    }

    static class ViewHolder {
        // 所有控件对象引用
        public TextView first_word, name;
        public CheckBox cb;

        public ViewHolder(View convertView) {
            first_word =  convertView.findViewById(R.id.qqservice_first_word_tv);
            name =  convertView.findViewById(R.id.qqservice_item_name_tv);
            cb =  convertView.findViewById(R.id.qqservice_item_name_cb);
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
