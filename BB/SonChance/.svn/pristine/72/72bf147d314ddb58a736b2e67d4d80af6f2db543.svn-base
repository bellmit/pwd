package com.tastebychance.sonchance.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tastebychance.sonchance.util.ImageDownLoad;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/12 11:15
 * 修改人：Administrator
 * 修改时间：2017/9/12 11:15
 * 修改备注：
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    private ViewHolder(Context context, ViewGroup parent,int layoutId,int position){
        this.mViews = new SparseArray<View>();
        mPosition = position;
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        //setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param postion
     * @return
     */
    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int postion){
        if (convertView == null){
            return new ViewHolder(context,parent,layoutId,postion);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入views
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 为TextView 设置字符串
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text){
        TextView view = getView(viewId);
        view.setText(text);
        view.setTextColor(Color.BLACK);
        return this;
    }

    /**
     * 为ImageView 设置图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId,int drawableId){
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView 设置图片
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId,Bitmap bm){
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView 设置图片
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByUrl(int viewId,String url){
        ImageDownLoad.getDownLoadSmallImg(url, (ImageView) getView(viewId));
        return this;
    }

    public int getPosition(){
        return mPosition;
    }
}
