package com.newland.wstdd.common.fileupload;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.newland.wstdd.common.common.UrlManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * UrlEncodedFormEntity上传
 * @author Jason
 *
 */
public class HttpUrlEncodedFormEntityPost {
	/**
	 * 上传
	 * @param pathList
	 */	
	public static void upload(List<String> pathList) {
		String files = "FileUpload:";
		for (int i = 0; i < pathList.size(); i++) {			
			System.out.println("filePath:"+pathList.get(i));
			//根据路径生成一个Bitmap
	        Bitmap  tBitmap = convertToBitmap(pathList.get(i),400,400);
	        //把Bitmap写进流里面
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        tBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	        //把流转化为数组
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			String file = new String(Base64Coder.encodeLines(b));
			//设置一条分割线
			files+="---------------------------7da2137580612";
			//累加每一个文件转化成的String数据
			files+=file;
		}
		
		   
		HttpClient client = new DefaultHttpClient();
		// 设置上传参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("files", files));
		HttpPost post = new HttpPost(UrlManager.headURL+"?tag=UEFE");
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			post.addHeader("Accept",
					"text/javascript, text/html, application/xml, text/xml");
			post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Cache-Control", "no-cache");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(entity);			
			HttpResponse response = client.execute(post);
			System.out.println("--------------"+response.getStatusLine().getStatusCode());
			HttpEntity e = response.getEntity();
			System.out.println("============="+EntityUtils.toString(e));
			if (200 == response.getStatusLine().getStatusCode()) {
				System.out.println("上传完成");
			} else {
				System.out.println("上传失败");
			}
			client.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 根据路径生成一个Bitmap
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
    // 设置为ture只获取图片大小
      opts.inJustDecodeBounds = true;
       // 返回为空
      BitmapFactory.decodeFile(path, opts);
      int width = opts.outWidth;
      int height = opts.outHeight;
      int inSampleSize = 1; 
      if (height > w || width > h) {  
          if (width > height) {  
              inSampleSize = Math.round((float)height / (float)h);  
          } else {  
              inSampleSize = Math.round((float)width / (float)w);  
          } 
      }
      opts.inSampleSize = inSampleSize;
      opts.inJustDecodeBounds = false;
      
      return BitmapFactory.decodeFile(path, opts);
 }
}

