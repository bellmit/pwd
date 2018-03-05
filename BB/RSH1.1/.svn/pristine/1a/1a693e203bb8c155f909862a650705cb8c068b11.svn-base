package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.contact;

import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactOperateNotifyListener;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

/**
 * Created by ShuHeng on 16/2/26.
 */
public class ContactOperateNotifyListenerImpl implements IYWContactOperateNotifyListener {
    /**
     * 用户请求加你为好友
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     *
     * @param contact 用户的信息
     * @param message 附带的备注
     */
    @Override
    public void onVerifyAddRequest(IYWContact contact, String message) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), contact.getUserId() + "用户请求加你为好友");

//                 //增加未读数的显示
//                 YWConversation conversation = mIMKit.getConversationService().getCustomConversationByConversationId(SYSTEM_FRIEND_REQ_CONVERSATION);
//                 if ( conversation!= null) {
//                     YWCustomConversationUpdateModel model = new YWCustomConversationUpdateModel();
//                     model.setIdentity(SYSTEM_FRIEND_REQ_CONVERSATION);
//                     model.setLastestTime(new Date().getTime());
//                     model.setUnreadCount(conversation.getUnreadCount() + 1);
//                     if(conversation.getConversationBody() instanceof YWCustomConversationBody){
//                         model.setExtraData(((YWCustomConversationBody)conversation.getConversationBody()).getExtraData());
//                     }
//                     if(mConversationService!=null)
//                     mConversationService.updateOrCreateCustomConversation(model);
//                 }


        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_ADDFRIENDAPPLY)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

    /**
     * 用户接受了你的好友请求
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     *
     * @param contact 用户的信息
     */
    @Override
    public void onAcceptVerifyRequest(IYWContact contact) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), contact.getUserId() + "用户接受了你的好友请求");

        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_ADDFRIEND)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

    /**
     * 用户拒绝了你的好友请求
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     *
     * @param contact 用户的信息
     */
    @Override
    public void onDenyVerifyRequest(IYWContact contact) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), contact.getUserId() + "用户拒绝了你的好友请求");

        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_REFUSEFRIENDAPPLY)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

    /**
     * 云旺服务端（或其它终端）进行了好友添加操作
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     *
     * @param contact 用户的信息
     */
    @Override
    public void onSyncAddOKNotify(IYWContact contact) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), "云旺服务端（或其它终端）进行了好友添加操作对" + contact.getUserId());

        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_ADDFRIEND)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

    /**
     * 用户从好友名单删除了您
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     *
     * @param contact 用户的信息
     */
    @Override
    public void onDeleteOKNotify(IYWContact contact) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), contact.getUserId() + "用户从好友名单删除了您");

        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_DELETEFRIEND)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

    @Override
    public void onNotifyAddOK(IYWContact contact) {
        IMNotificationUtils.getInstance().showToast(MyApplication.getContext(), "用户添加您为好友了" + contact.getUserId());


        /**
         *同步好友关系到融商汇后台
         * token 	是 	string 	token
         user_id 	是 	int 	请求者的用户id (8位数账号)
         */
        OkGo.post(UrlManager.URL_ADDFRIEND)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("token", SystemUtil.getInstance().getToken())
                .params("user_id", contact.getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("成功同步好友关系到融商汇后台");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("同步好友关系到融商汇后台失败");
                    }
                });
    }

}
