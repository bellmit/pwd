package com.tastebychance.sonchance.homepage.toevaluate.adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.toevaluate.bean.CommentInfo;

import java.util.List;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/30 10:08
 * 修改人：Administrator
 * 修改时间：2017/9/30 10:08
 * 修改备注：
 */

public class ToEvaluateAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CommentInfo> list;

    public ToEvaluateAdapter(Context context, List<CommentInfo> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list == null ? null : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_toevaluate_listitem, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CommentInfo commentInfo = list.get(position);

        /*--------------------------------------EditText -begin-----------------------------------------*/
        //把CommentInfo与EditText进行绑定
        viewHolder.home_toevaluate_content_edt.setTag(commentInfo);

//        清除焦点
        viewHolder.home_toevaluate_content_edt.clearFocus();
        viewHolder.home_toevaluate_content_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //存储变化
                CommentInfo commentInfo = (CommentInfo) viewHolder.home_toevaluate_content_edt.getTag();
                commentInfo.setContent(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //大部分情况下，getview中有if必须有else
        if (!TextUtils.isEmpty(commentInfo.getContent())) {
            viewHolder.home_toevaluate_content_edt.setText(commentInfo.getContent());
        } else {
            viewHolder.home_toevaluate_content_edt.setText("");//没有数值时，这句不能省略，否则会导致别的item展示当前的数据
            viewHolder.home_toevaluate_content_edt.setHint("说说哪里满意,帮助大家选择");
        }
        /*--------------------------------------EditText -end-----------------------------------------*/

        /*--------------------------------------RatingBar-begin-----------------------------------*/
        //把CommentInfo与RatingBar进行绑定
        viewHolder.ratingBar.setTag(commentInfo);
        // 清除焦点
        viewHolder.ratingBar.clearFocus();
        //滑动星星的时候
        viewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //存储变化
                CommentInfo commentInfo = (CommentInfo) viewHolder.ratingBar.getTag();
                commentInfo.setGrade(rating);//不要在这进行 *2 的操作，否则会导致每次的数值都翻倍，最终使RatingBar充满，只能在展示的时候进行 *2 展示

                if (commentInfo.getGrade() == 0) {
                    viewHolder.home_toevaluate_value_tv.setText("请滑动星星评分");
                    viewHolder.home_toevaluate_value_tv.setTextColor(Color.BLACK);
                } else {
                    viewHolder.home_toevaluate_value_tv.setText(commentInfo.getGrade() * 2 + "");
                    viewHolder.home_toevaluate_value_tv.setTextColor(Color.GREEN);
                }
            }
        });

        if (commentInfo.getGrade() == 0) {
            viewHolder.home_toevaluate_value_tv.setText("请滑动星星评分");
            viewHolder.home_toevaluate_value_tv.setTextColor(Color.BLACK);
        } else {
            viewHolder.home_toevaluate_value_tv.setText(commentInfo.getGrade() * 2 + "");
            viewHolder.home_toevaluate_value_tv.setTextColor(Color.GREEN);
        }

        viewHolder.ratingBar.setRating(commentInfo.getGrade());
        /*--------------------------------------RatingBar-end-----------------------------------*/

        return convertView;
    }

    class ViewHolder {
        RatingBar ratingBar;
        TextView home_toevaluate_value_tv;
        EditText home_toevaluate_content_edt;

        public ViewHolder(View convertView) {
            ratingBar = (RatingBar) convertView.findViewById(R.id.home_toevaluate_ratingBar);
            home_toevaluate_value_tv = (TextView) convertView.findViewById(R.id.home_toevaluate_value_tv);
            home_toevaluate_content_edt = (EditText) convertView.findViewById(R.id.home_toevaluate_content_edt);
        }
    }

    public List<CommentInfo> getList() {
        return list;
    }
}
