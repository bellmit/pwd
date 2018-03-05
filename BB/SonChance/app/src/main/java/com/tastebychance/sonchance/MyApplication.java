package com.tastebychance.sonchance;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.RealmHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application{
	public static final boolean IS_SAVE_ERRORLOG = true;

	public static boolean isDevelopmentStage = true;//是否是开发阶段
	
	public ArrayList<Activity> activities = new ArrayList<Activity>();
	
	private static Context context;


	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;

		// The Realm file will be located in Context.getFilesDir() with name "default.realm"
		Realm.init(this);
		//使用默认配置
//		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
		//使用自定义配置
		RealmConfiguration realmConfiguration = new  RealmConfiguration.Builder()
				.name(RealmHelper.DB_NAME)
				.deleteRealmIfMigrationNeeded()
				.build();
		Realm.setDefaultConfiguration(realmConfiguration);


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

	/**replaceFragment 改变当前的Fragment
	 *
	 * @param fragmentmanager
	 * @param containerViewId
	 * @param fragment
	 */
	private FragmentTransaction transaction;// FragmentTransaction对fragment进行添加,移除,替换,以及执行其他动作。
	public void replaceFragment(FragmentManager fragmentmanager,int containerViewId,BaseFragment fragment){
		transaction = fragmentmanager.beginTransaction();// 对fragment进行添加,移除,替换,以及执行其他动作。
		// 设置切换Fragment时的动画效果
		transaction.setCustomAnimations(R.anim.move_x_in, R.anim.move_x_out);// 利用xml弄出这个动画的效果																			// 先暗后明的效果
		transaction.replace(containerViewId, fragment);// 使用的是FrameLayout当中容器，然后将fragment替换进来
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);// 只是设置了切换效果而已																		// 没用效果
		transaction.commit();// 记得提交 这样才有效果
		fragmentmanager.executePendingTransactions();// 立即执行事务
	}

	/**
	 * 清空堆栈
	 */
	public void clearStatck(){
		for (Activity activity : activities) {
			activity.finish();
		}
		
		Constants.orderedDishes = null;
	}

}
