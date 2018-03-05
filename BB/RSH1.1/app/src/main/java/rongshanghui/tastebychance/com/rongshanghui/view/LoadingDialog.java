package rongshanghui.tastebychance.com.rongshanghui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import rongshanghui.tastebychance.com.rongshanghui.R;


public class LoadingDialog {
    private Dialog mDialog;
    private Context mContext;
    private TextView tvMessage;

    public LoadingDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {

        mDialog = new Dialog(mContext, R.style.custom_dialog_style);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_content);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * @param isCancelable 是否可以点击取消
     */
    public void show(boolean isCancelable) {
        mDialog.setCancelable(isCancelable);
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void isVisible() {
        tvMessage.setVisibility(View.VISIBLE);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void setTvMessage(String message) {
        this.tvMessage.setText(message);
        this.tvMessage.setVisibility(View.VISIBLE);
    }


}
