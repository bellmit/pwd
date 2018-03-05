package com.tastebychance.sonchance.homepage.homeshoppingcart.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.HomeActivity;
import com.tastebychance.sonchance.homepage.adapter.HomeListViewAdapter;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.util.StringUtil;


/***
 * 底部购物车
 */
public class ShoppingcartAdapter extends BaseAdapter {
    HomeListViewAdapter homeListViewAdapter;
    private HomeActivity activity;
    private SparseArray<ShoppingcartBean> dataList;

//    private HashMap<Integer,ShoppingCartBean> dataList;

    public ShoppingcartAdapter(HomeActivity activity
            , HomeListViewAdapter homeListViewAdapter
            , SparseArray<ShoppingcartBean> dataList) {
        this.homeListViewAdapter = homeListViewAdapter;
        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Viewholder viewholder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.product_item, null);
            viewholder = new Viewholder();
            viewholder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewholder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            viewholder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
            viewholder.iv_remove = (ImageView) view.findViewById(R.id.iv_remove);
            viewholder.tv_count = (TextView) view.findViewById(R.id.home_shoppingcart_payment_tv);

            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }


        StringUtil.filtNull(viewholder.tv_name, dataList.valueAt(position).getDishname());//商品名称
        StringUtil.filtNull(viewholder.tv_price, "￥" + dataList.valueAt(position).getPrice());//商品价格
        viewholder.tv_count.setText(String.valueOf(dataList.valueAt(position).getNum()));//商品数量

        viewholder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.handlerCarNum(1, dataList.valueAt(position), true);
                homeListViewAdapter.notifyDataSetChanged();

            }
        });
        viewholder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.handlerCarNum(0, dataList.valueAt(position), true);
                homeListViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    class Viewholder {
        TextView tv_price;
        TextView tv_name;
        ImageView iv_add, iv_remove;
        TextView tv_count;
    }

}