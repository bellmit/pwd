package com.tastebychance.sonchance.personal.myevaluate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.bankcard.BankCardActivity;
import com.tastebychance.sonchance.personal.bankcard.bean.BankCardInfo;
import com.tastebychance.sonchance.personal.myevaluate.MyEvaluateActivity;
import com.tastebychance.sonchance.personal.myevaluate.bean.MyEvaluateInfo;

import java.util.List;

/**
 * 优惠券adapter
 * Created by shenbh on 2017/8/29.
 */

public class MyEvaluateAdapter extends BaseAdapter {

    private MyEvaluateActivity activity;
    private List<MyEvaluateInfo> list;

    public MyEvaluateAdapter(Context context, List<MyEvaluateInfo> list) {
        this.activity = (MyEvaluateActivity) context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.person_myevaluate_listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.person_myevaluate_ratingBar = (RatingBar) convertView.findViewById(R.id.person_myevaluate_ratingBar);
            viewHolder.person_myevaluate_value_tv = (TextView) convertView.findViewById(R.id.person_myevaluate_value_tv);
            viewHolder.person_myevaluate_content_tv = (TextView) convertView.findViewById(R.id.person_myevaluate_content_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.person_myevaluate_ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        viewHolder.person_myevaluate_ratingBar.setRating(list.get(position).getGrade()/(2f));
        viewHolder.person_myevaluate_value_tv.setText(list.get(position).getGrade()+"");
        viewHolder.person_myevaluate_content_tv.setText(list.get(position).getContent()+"");
        return convertView;
    }

    class ViewHolder {
        private RatingBar person_myevaluate_ratingBar;
        private TextView person_myevaluate_value_tv;
        private TextView person_myevaluate_content_tv;
    }
}
