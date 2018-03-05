package rongshanghui.tastebychance.com.rongshanghui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;

import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.common.PermissionsActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.PackageManagerUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PermissionsChecker;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/17 17:33
 * 修改人：Administrator
 * 修改时间：2017/11/17 17:33
 * 修改备注：
 */

public class MyBaseFragmentActivity extends FragmentActivity {
    public LoadingBar loadingBar;

    public int statusHeight;

    public static final int ERROR_WHAT = 0;//错误提示
    public static final int INFO_WHAT = 1;//消息提示
    public static final int LOADING_CANCEL = 2;//取消Loading
    public String UPDATE_SUCCESS = "更新成功";
    public String GETDATA_SUCCESS = "请求数据成功";

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    public static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    switch (msg.what) {
                        case MyBaseFragmentActivity.ERROR_WHAT:
                            ToastUtils.showOneToast(((MyBaseFragmentActivity) t).getApplicationContext(), (String) msg.obj);
                            break;
                        case INFO_WHAT:
//                        if (Constants.IS_DEVELOPING) {
                            ToastUtils.showOneToast(((MyBaseFragmentActivity) t).getApplicationContext(), (String) msg.obj);
//                        }
                            break;
                        case MyBaseFragmentActivity.LOADING_CANCEL:
                            if (null != ((MyBaseFragmentActivity) t).loadingBar) {
                                ((MyBaseFragmentActivity) t).loadingBar.cancel();
                                ((MyBaseFragmentActivity) t).loadingBar = null;
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MyHandler myHandler = new MyHandler(this);

    public PermissionsChecker permissionsChecker; // 权限检测器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        permissionsChecker = new PermissionsChecker(this);

        statusHeight = SystemUtil.getInstance().getStatusBarHeight();
        setStatusBar();

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
//        tintManager.setTintColor(Color.parseColor("#990000FF"));
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDark));

// set a custom navigation bar resource
//        tintManager.setNavigationBarTintResource(R.drawable.my_tint);
// set a custom status bar drawable
//        tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    /**
     * 设置状态栏颜色为透明色，这样能保证状态栏为沉浸式状态
     * 根据SDK >= 21时，标题栏高度设为statusBarHeight(25dp)+titlBarHeight(48dp)
     * 若SDK < 21,标题栏高度直接为titlBarHeight,关于不同的高度设置可以用两个values，values-19两个分别设置不同的dimens
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            /*Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);*/


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= 23 && permissionsChecker.lacksPermissions(Constants.PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    public void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, Constants.REQUEST_CODE, Constants.PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == Constants.REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    protected void onStart() {
        if (Constants.IS_DEVELOPING) {
            System.out.println(PackageManagerUtils.getRunningActivityName(this) + "----------------------------------------------------" + getTaskId());
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    public void dialogCancel() {
        Message msg = new Message();
        msg.what = LOADING_CANCEL;
        myHandler.sendMessage(msg);
    }

}
