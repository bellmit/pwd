package com.tastebychance.sfj.util.permissonchecker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 项目名称：RongShangHui2
 * 类描述：检查权限的工具类
 * 创建人：Administrator
 * 创建时间： 2017/11/29 21:53
 * 修改人：Administrator
 * 修改时间：2017/11/29 21:53
 * 修改备注：
 */

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }
}
