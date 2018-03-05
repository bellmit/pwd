package com.newland.wstdd.common.filedownload;

import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;

public abstract class FileBaseActivity extends Activity {
	private static final String TAG = "FileBaseActivity";//收集异常日志tag
	protected ImageLoader imageLoader;

    /**
     * 初始化布局资源文件
     */
    public abstract int initResource();

    /**
     * 初始化组件
     */
    public abstract void initComponent();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 添加监听
     */
    public abstract void addListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initResource());
        
        /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
        imageLoader = ImageLoader.getInstance();
        initComponent();
        initData();
        addListener();
    }
    @Override
    protected void onDestroy() {
    	 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
    	super.onDestroy();
    }

}
