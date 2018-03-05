package rongshanghui.tastebychance.com.rongshanghui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.lang.ref.WeakReference;

import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/27 10:15
 * 修改人：Administrator
 * 修改时间：2017/11/27 10:15
 * 修改备注：
 */

public class MyBaseFragment extends Fragment {

    public LoadingBar loadingBar;

    public static final int ERROR_WHAT = 0;//错误提示
    public static final int INFO_WHAT = 1;//消息提示
    public static final int LOADING_CANCEL = 2;//取消Loading
    public String UPDATE_SUCCESS = "更新成功";
    public String GETDATA_SUCCESS = "请求数据成功";

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    public static class MyHandler<T extends android.support.v4.app.Fragment> extends Handler {
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
                        case ERROR_WHAT:
                            ToastUtils.showOneToast(t.getActivity().getApplicationContext(), (String) msg.obj);
                            break;
                        case INFO_WHAT:
                            if (Constants.IS_DEVELOPING) {
                                ToastUtils.showOneToast(t.getActivity().getApplicationContext(), (String) msg.obj);
                            }
                            break;
                        case LOADING_CANCEL:
                            if (null != ((MyBaseFragment) t).loadingBar) {
                                ((MyBaseFragment) t).loadingBar.cancel();
                                ((MyBaseFragment) t).loadingBar = null;
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


    public void dialogCancel() {
        Message msg = new Message();
        msg.what = LOADING_CANCEL;
        myHandler.sendMessage(msg);
    }
}
