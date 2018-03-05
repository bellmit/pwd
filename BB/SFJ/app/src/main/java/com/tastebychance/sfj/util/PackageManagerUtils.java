package com.tastebychance.sfj.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：获得当前应用包名、当前activity的名字、推断是否是系统桌面
 * 创建人：Administrator
 * 创建时间： 2017/11/24 10:40
 * 修改人：Administrator
 * 修改时间：2017/11/24 10:40
 * 修改备注：
 */

public class PackageManagerUtils {
    /**
     * 1、获得当前activity的名字
     */

    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//完整类名
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        String contextActivity = runningActivity.substring(runningActivity.lastIndexOf(".") + 1);
        return contextActivity;
    }

    /**
     * 2、获得当前应用包名
     */

    public static String getAppPackageName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.d("lixx", "当前应用:" + componentInfo.getPackageName());
        return componentInfo.getPackageName();
    }

    /**
     *
     3、推断是否是系统桌面
     */

    /**
     * 推断当前界面是否是桌面
     */
    public static boolean isHome(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes(context).contains(rti.get(0).topActivity.getPackageName());
    }


    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包括全部包名的字符串列表
     */
    public static List<String> getHomes(Context context) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
}
