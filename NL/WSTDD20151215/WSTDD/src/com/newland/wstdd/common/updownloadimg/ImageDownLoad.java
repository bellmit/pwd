package com.newland.wstdd.common.updownloadimg;


import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ImageView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.UrlManager;
import com.newland.wstdd.common.tools.UiHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片下载获取并且设置到控件上
 * 
 * @author Administrator
 * 
 */
public class ImageDownLoad {
	private static DisplayImageOptions options; // 设置图片显示相关参数

	// 获取网络图片
	public static void getDownLoadImg(String headImgUrlString, final ImageView headImageView) {

		if (headImgUrlString != null && !"".equals(headImgUrlString)) {
			// 说明有图片了~从服务器上下载图片
			// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
			options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
					.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
					.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
					.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
					
					.build(); // 构建完成
			/**
			 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
			 * DisplayImageOptions配置文件
			 */
			// String
			// urlImageString="http://mario.picp.net/tdd/resources/upload/";
			try {
				String a[] = headImgUrlString.split("/upload/");
				if(headImgUrlString.contains("http:")){
					ImageLoader.getInstance().displayImage(headImgUrlString, headImageView, options, new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
							// TODO Auto-generated method stub
							
						}
						
						
						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
							// TODO Auto-generated method stub
							
							
							int widths =getScreenWidth();
							double temp = (double)bitmap.getHeight()/bitmap.getWidth();
							int heights = (int)(temp*widths);
							headImageView.getLayoutParams().width=widths;
							headImageView.getLayoutParams().height=heights;
							headImageView.setImageBitmap(bitmap);
						}
						
						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							// TODO Auto-generated method stub
							
						}
					});
				}else{
					ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+headImgUrlString, headImageView, options,new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
							// TODO Auto-generated method stub

							int widths =getScreenWidth();
							double temp = (double)bitmap.getHeight()/bitmap.getWidth();
							int heights = (int)(temp*widths);
							headImageView.getLayoutParams().width=widths;
							headImageView.getLayoutParams().height=heights;
							headImageView.setImageBitmap(bitmap);
						}
						
						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			
			} catch (Exception e) {
				// TODO: handle exception
				
			}
		}
	}
	
	
	// 获取网络图片  变成圆形
	public static void getDownLoadCircleImg(String headImgUrlString, final ImageView headImageView) {

		if (headImgUrlString != null && !"".equals(headImgUrlString)) {
			// 说明有图片了~从服务器上下载图片
			// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
			options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
					.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
					.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
					.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
					
					.build(); // 构建完成
			/**
			 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
			 * DisplayImageOptions配置文件
			 */
			// String
			// urlImageString="http://mario.picp.net/tdd/resources/upload/";
			try {
				String a[] = headImgUrlString.split("/upload/");
				if(headImgUrlString.contains("http:")){
					ImageLoader.getInstance().displayImage(headImgUrlString, headImageView, options, new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String arg0, View arg1) {
						}
						
						@Override
						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						}
						
						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {

							headImageView.setImageBitmap(UiHelper.CircleImageView(bitmap, 2));
						}
						
						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
						}
					});	
				}else {
					ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+headImgUrlString, headImageView, options, new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String arg0, View arg1) {
						}
						
						@Override
						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						}
						
						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
							headImageView.setImageBitmap(UiHelper.CircleImageView(bitmap, 2));
						}
						
						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
						}
					});
				}
				
//				ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+a[1], headImageView, options);
			} catch (Exception e) {
				// TODO: handle exception
//				ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+headImgUrlString, headImageView, options);
				
			}
		}
	}
	
	
	
	// 获取网络图片  变成圆形
		public static void getDownLoadSmallImg(String headImgUrlString, final ImageView headImageView) {

			if (headImgUrlString != null && !"".equals(headImgUrlString)) {
				// 说明有图片了~从服务器上下载图片
				// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
				options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
						.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
						.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
						.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
						.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
						
						.build(); // 构建完成
				/**
				 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
				 * DisplayImageOptions配置文件
				 */
				// String
				// urlImageString="http://mario.picp.net/tdd/resources/upload/";
				try {
					String a[] = headImgUrlString.split("/upload/");
					if(headImgUrlString.contains("http:")){
						ImageLoader.getInstance().displayImage(headImgUrlString, headImageView, options);
					}else {
						ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+headImgUrlString, headImageView, options);
						
					}
					
					
//					ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+a[1], headImageView, options);
				} catch (Exception e) {
					// TODO: handle exception
//					ImageLoader.getInstance().displayImage(UrlManager.uploadToUrlServer+headImgUrlString, headImageView, options);
					
				}
			}
		}

		public static int getScreenWidth() {
			WindowManager windowManager = (WindowManager) AppContext.getAppContext().getContext()
					.getSystemService(Context.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int width = windowManager.getDefaultDisplay().getWidth();
			return width;
		}

		/**
		 * 获取屏幕高度
		 * 
		 * @return
		 */
		public static int getScreenHeight() {
			WindowManager windowManager = (WindowManager) AppContext.getAppContext().getContext()
					.getSystemService(Context.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int height = windowManager.getDefaultDisplay().getHeight();
			return height;
		}
}
