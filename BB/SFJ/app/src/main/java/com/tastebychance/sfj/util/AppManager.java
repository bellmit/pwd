package com.tastebychance.sfj.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**应用程序Activity管理类：用于Activity管理和应用程序退出
 * Created by shenbinghong on 2018/2/12.
 */

public class AppManager {
    private static Stack<Activity> activityStack;//使用栈的方式来管理Activity
    private static AppManager instance;

    private AppManager(){}

    /**
     * 单一实例
     */
    public static AppManager getInstance(){
        return AppManagerHolder.INSTANCE;
    }

    private static class AppManagerHolder{
        private static final AppManager INSTANCE = new AppManager();
    }

    /**
     * 添加activity到堆栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if (null == activityStack){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     * @return
     */
    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        finishActivity(currentActivity());
    }

    /**结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity : activityStack){
            if (activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)){
                finishActivity(activityStack.get(i));
                -- i;
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     * @param context
     */
    public void appExit(Context context){
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
