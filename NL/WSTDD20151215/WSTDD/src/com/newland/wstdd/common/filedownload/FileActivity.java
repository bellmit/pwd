package com.newland.wstdd.common.filedownload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.newland.wstdd.R;
import com.newland.wstdd.common.tools.UiHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
	}

	// 点击进入ListView展示界面  
    public void onImageListClick(View view) {  
       Intent intent = new Intent(this, FileListActivity.class);  
       intent.putExtra(Constants.IMAGES, Constants.images);  
       startActivity(intent);  
    }  
    
    public void onImageGridClick(View view) {
    	Intent intent = new Intent(this, FileGridActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}
    
    
    /*public void onImagePagerClick(View view) {
    	Intent intent = new Intent(this, ImageListActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}
    
    
    public void onImageGalleryClick(View view) {
    	Intent intent = new Intent(this, ImageListActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}*/
    
    public void onClearMemoryClick(View view) {
    	UiHelper.ShowOneToast(this, "清除内存缓存成功");
    	ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
	}
    
    public void onClearDiskClick(View view) {
    	UiHelper.ShowOneToast(this, "清除本地缓存成功");
    	ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
    } 
    
}