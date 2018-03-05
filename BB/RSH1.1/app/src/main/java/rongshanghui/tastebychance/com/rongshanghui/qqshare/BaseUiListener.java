package rongshanghui.tastebychance.com.rongshanghui.qqshare;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;

/**
 * Created by shenbinghong on 2018/1/5.
 */

public class BaseUiListener implements IUiListener {

    /*@Override
    public void onComplete(JSONObject response) {
//        mBaseMessageText.setText("onComplete:");
//        mMessageText.setText(response.toString());
        doComplete(response);
    }*/

    protected void doComplete(JSONObject values) {
    }

    @Override
    public void onComplete(Object o) {

    }

    @Override
    public void onError(UiError e) {
//        showResult("onError:", "code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
        CrashHandler.getInstance().handlerError("qqshare-onError:" + "code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
    }

    @Override
    public void onCancel() {
//        showResult("onCancel", "");
        CrashHandler.getInstance().handlerError("qqshare-onCancel:");
    }
}
