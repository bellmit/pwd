package com.newland.wstdd.common.filedownload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class ImageDownLoader {
    private String uriString=null;
	private ExecutorService threadPool;
	public ImageDownLoader(Context context) {
		threadPool = Executors.newFixedThreadPool(3);
	}

	

	

	/**
	 * 异步下载图片，并按指定宽度和高度压缩图片
	 * 
	 * @param url
	 * @param width
	 * @param height
	 * @param listener
	 *            图片下载完成后调用接口
	 */
	public synchronized void loadImage(final String url, final int width, final int height,
			AsyncImageLoaderListener listener) {
		uriString =url;
		
		final ImageHandler handler = new ImageHandler(listener);
		Runnable runnable=	new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = downloadImage(uriString, width, height);
				Message msg = handler.obtainMessage();
				msg.obj = bitmap;
				handler.sendMessage(msg);
				
				
			}
		};
		threadPool.execute(runnable);
	}

	public synchronized void loadWeatherImage(final String url, final int width, final int height,
			AsyncImageLoaderListener listener) {
		uriString =url;
		
		final ImageHandler handler = new ImageHandler(listener);
		Runnable runnable=	new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = downloadImage(uriString, width, height);
				Message msg = handler.obtainMessage();
				msg.obj = bitmap;
				handler.sendMessage(msg);	
			}
		};
		threadPool.execute(runnable);
	}
	

	/**
	 * 下载图片，并按指定高度和宽度压缩
	 * 
	 * @param url
	 * @param width
	 * @param height
	 * @return
	 */
	public synchronized Bitmap downloadImage(String url, int width, int height) {
		Bitmap bitmap = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			httpClient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				byte[] byteIn = EntityUtils.toByteArray(entity);

				BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
				bmpFactoryOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length,
						bmpFactoryOptions);
				int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
						/ height);
				int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
						/ width);
				if (heightRatio > 1 && widthRatio > 1) {
					bmpFactoryOptions.inSampleSize = heightRatio > widthRatio ? heightRatio
							: widthRatio;
				}
				bmpFactoryOptions.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeByteArray(byteIn, 0,
						byteIn.length, bmpFactoryOptions);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null && httpClient.getConnectionManager() != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

		return bitmap;
	}

	

	

	/** 异步加载图片接口 */
	public interface AsyncImageLoaderListener {
		void onImageLoader(Bitmap bitmap);
	}

	
	
	
	
	/** 异步加载完成后，图片处理 */
	static class ImageHandler extends Handler {

		private AsyncImageLoaderListener listener;

		public ImageHandler(AsyncImageLoaderListener listener) {
			this.listener = listener;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listener.onImageLoader((Bitmap) msg.obj);
		}
	}
		
	public synchronized void cancelTasks() {
		if (threadPool != null) {
			threadPool.shutdownNow();
			threadPool = null;
		}
	}

}
