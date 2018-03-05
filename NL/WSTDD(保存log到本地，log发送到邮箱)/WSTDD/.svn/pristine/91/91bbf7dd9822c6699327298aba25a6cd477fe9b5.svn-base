package com.newland.wstdd.find.hotgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.newland.wstdd.R;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * ImageView创建工厂
 */
public class HotViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner1, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
