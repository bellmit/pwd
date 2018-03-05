package company.webdemo.agile.com.technologycompany;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;

import company.webdemo.agile.com.technologycompany.util.CrashHandler;


public class MyApplication extends Application {
	public static final boolean IS_SAVE_ERRORLOG = true;

	public static boolean isDevelopmentStage = true;//是否是开发阶段
	
	public static ArrayList<Activity> activities = new ArrayList<Activity>();
	
	private static Context context;


	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;

		context = getApplicationContext();

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);

		//imageload初始化
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(imageLoaderConfiguration);

	}
	
	public static Context getContext(){
		return context;
	}

	private static MyApplication appContext;
	public static MyApplication getAppContext(){
		return appContext;
	}

	/**
	 * 清空堆栈
	 */
	public void clearStatck(){
		for (Activity activity : activities) {
			activity.finish();
		}
	}

}
