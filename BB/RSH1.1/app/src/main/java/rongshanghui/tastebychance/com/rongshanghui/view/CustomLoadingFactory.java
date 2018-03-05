package rongshanghui.tastebychance.com.rongshanghui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.widget.loading.factory.LoadingFactory;

import rongshanghui.tastebychance.com.rongshanghui.R;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/14 16:35
 * 修改人：Administrator
 * 修改时间：2017/11/14 16:35
 * 修改备注：
 */

public class CustomLoadingFactory implements LoadingFactory {
    @Override
    public View onCreateView(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_process_dialog_color, viewGroup, false);
        return view;
    }
}
