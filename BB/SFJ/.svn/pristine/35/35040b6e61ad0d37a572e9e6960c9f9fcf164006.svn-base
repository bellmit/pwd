package com.tastebychance.sfj.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sfj.R;
import com.tastebychance.sfj.home.bean.NoticeBean;
import com.tastebychance.sfj.util.PicassoUtils;

import java.util.List;

/**
 * Created by shenbinghong on 2018/2/2.
 */

public class HomeNoticeAdapter extends BaseAdapter {
    private Context context;
    private List<NoticeBean.DataBeanX.DataBean> list;

    public HomeNoticeAdapter(Context context, List<NoticeBean.DataBeanX.DataBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<NoticeBean.DataBeanX.DataBean> list) {
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
        ViewHolderHasPic viewHolderHasPic;
        ViewHolderNoPic viewHolderNoPic;
        View hasPicView = null;
        View noPicView = null;

        if (TextUtils.isEmpty(list.get(position).getImage())){
            if (noPicView == null) {
                noPicView = View.inflate(context, R.layout.item_home_mlv_nopic, null);
                viewHolderNoPic = new ViewHolderNoPic();
                viewHolderNoPic.nameTv = (TextView) noPicView.findViewById(R.id.item_home_mlv_nopic_name_tv);
                viewHolderNoPic.dateTv = (TextView) noPicView.findViewById(R.id.item_home_mlv_nopic_date_tv);
                noPicView.setTag(viewHolderNoPic);
            }else {
                viewHolderNoPic = (ViewHolderNoPic) noPicView.getTag();
            }

            convertView = noPicView;
            viewHolderNoPic.nameTv.setText(list.get(position).getTitle());
            viewHolderNoPic.dateTv.setText(list.get(position).getAdd_time());
        }else {
            if (hasPicView == null) {
                hasPicView = View.inflate(context, R.layout.item_home_mlv_haspic, null);
                viewHolderHasPic = new ViewHolderHasPic();
                viewHolderHasPic.nameTv = (TextView) hasPicView.findViewById(R.id.item_home_mlv_haspic_name_tv);
                viewHolderHasPic.dateTv = (TextView) hasPicView.findViewById(R.id.item_home_mlv_haspic_date_tv);
                viewHolderHasPic.picIv = (ImageView) hasPicView.findViewById(R.id.imageView);
                hasPicView.setTag(viewHolderHasPic);
            }else {
                viewHolderHasPic = (ViewHolderHasPic) hasPicView.getTag();
            }

            convertView = hasPicView;
            viewHolderHasPic.nameTv.setText(list.get(position).getTitle());
            viewHolderHasPic.dateTv.setText(list.get(position).getAdd_time());
            PicassoUtils.getinstance().loadImageByPath(context, list.get(position).getImage(), viewHolderHasPic.picIv, PicassoUtils.PICASSO_BITMAP_SHOW_NORMAL_TYPE, -1);
        }

        return convertView;
    }

    public NoticeBean.DataBeanX.DataBean getSelectedData() {
        if (selectItem != -1 && selectItem < getCount()) {
            return (NoticeBean.DataBeanX.DataBean) getItem(selectItem);
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

    static class ViewHolderHasPic {
        // 所有控件对象引用
        private TextView nameTv, dateTv;
        private ImageView picIv;
    }

    static class ViewHolderNoPic {
        // 所有控件对象引用
        private TextView nameTv, dateTv;
    }
}
