package com.newland.wstdd.common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.widget.ImageView;

public class BitmapImageUtil {

	// 根据Uri获取到图片的路径
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	@SuppressWarnings("unused")
	public static void SetBitmap(String path, ImageView miageView) {
		// 1.加载位图

		try {
			InputStream is = new FileInputStream(path);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inTempStorage = new byte[100 * 1024];
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			opts.inPurgeable = true;
			opts.inSampleSize = 4;
			// 6.设置解码位图的尺寸信息
			opts.inInputShareable = true;
			// 7.解码位图
			Bitmap btp = BitmapFactory.decodeStream(is, null, opts);
			// 8.显示位图
			miageView.setImageBitmap(btp);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 将path变成一个BitMap对象
	 */
	public static Bitmap getBitmapFromLocal(String path)   {
	    Log.i("showImage","loading:"+path);
	    Bitmap bitmap = null;
	    BitmapFactory.Options bfOptions=new BitmapFactory.Options();
	    bfOptions.inDither=false;                     
	    bfOptions.inPurgeable=true;                 
	    bfOptions.inInputShareable=true;             
	    bfOptions.inTempStorage=new byte[32 * 1024]; 


	    File file=new File(path);
	    FileInputStream fs=null;
	    try {
	        fs = new FileInputStream(file);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    try {
	        if(fs!=null) bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{ 
	        if(fs!=null) {
	            try {
	                fs.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		return bitmap;

	   
	}
}
