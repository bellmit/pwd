package rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingBizService;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.aop.model.YWChattingPlugin;
import com.alibaba.mobileim.aop.model.YWInputViewPlugin;
import com.alibaba.mobileim.channel.IMChannel;
import com.alibaba.mobileim.channel.constant.YWProfileSettingsConstants;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.alibaba.mobileim.fundamental.widget.RecordButton;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.kit.chat.presenter.ChattingDetailPresenter;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.alibaba.mobileim.utility.YWIMImageUtils;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.tribe.TribeConstants;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.tribe.TribeInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ?????????????????????????????????????????????????????????????????????????????????????????????{@link CustomSampleHelper?????? ?????? AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustomSample.class);
 * ?????????????????????????????????????????????????????????????????????????????????
 * <p>
 * todo ??????????????????????????????1????????????????????????????????????
 * Created by mayongge on 15-9-23.
 */
public class ChattingUICustomSample extends IMChattingPageUI {


    public ChattingUICustomSample(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * ????????????????????????????????????.9???
     *
     * @param conversation ????????????????????????
     * @param message      ???????????????????????????
     * @param self         ?????????????????????????????????true??????????????????????????? false????????????????????????
     * @return 0: ???????????? ???1:??????????????????????????? >0:??????????????????????????????
     */
    @Override
    public int getMsgBackgroundResId(YWConversation conversation, YWMessage message, boolean self) {
        if (true)
            return super.getMsgBackgroundResId(conversation, message, self);
        int msgType = message.getSubType();
        if (msgType == YWMessage.SUB_MSG_TYPE.IM_TEXT || msgType == YWMessage.SUB_MSG_TYPE.IM_AUDIO) {
            if (self) {
                return R.drawable.demo_talk_pop_r_bg;
            } else {
                return R.drawable.demo_talk_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_IMAGE) {
            if (self) {
                return R.drawable.demo_talk_pic_pop_r_bg;
            } else {
                return R.drawable.demo_talk_pic_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_VIDEO) {
            if (self) {
                return R.drawable.demo_talk_pic_pop_r_bg;
            } else {
                return R.drawable.demo_talk_pic_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_GEO) {
            if (self) {
                return R.drawable.aliwx_comment_r_bg;
            } else {
                return R.drawable.aliwx_comment_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || msgType == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
            if (self) {
                return -1;
            } else {
                return -1;
            }
        }
        return super.getMsgBackgroundResId(conversation, message, self);
    }

    /**
     * ????????????{@link #processBitmapOfLeftImageMsg??????{@link #processBitmapOfRightImageMsg???????????????Bitmap??????????????????????????????????????????,?????????????????????,????????????return false
     * ?????????????????????????????????????????????????????????
     *
     * @return false: ??????????????????
     * <br>
     * true????????????????????????????????????true????????????{@link #processBitmapOfLeftImageMsg??????{@link #processBitmapOfRightImageMsg??????????????????????????????????????????
     */

    @Override
    public boolean needRoundChattingImage() {
        return true;
    }

    /**
     * ??????????????????????????????????????????????????????(?????????dp)
     *
     * @return
     */
    @Override
    public float getRoundRadiusDps() {
        return 12.6f;
    }

    /**
     * ????????????????????????
     *
     * @return ????????????????????????????????????
     */
    @Override
    public int getChattingBackgroundResId() {
        //????????????????????????????????????
        return 0;
        // return R.drawable.demo_chatting_bg;
    }

    /**
     * ???????????????????????????????????????????????????Bitmap???????????????SDK????????????????????????????????????????????????Bitmap????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????
     * 1.?????? {@link #needRoundChattingImage}??????return false(???????????????)????????????????????????
     * 2.?????????{@link #getLeftImageMsgBackgroundResId}??????return???1??????????????????
     *
     * @param input ???????????????????????????
     * @return ????????????Bitmap
     */
    public Bitmap processBitmapOfLeftImageMsg(Bitmap input) {
        Bitmap output = Bitmap.createBitmap(input.getWidth(),
                input.getHeight(), Bitmap.Config.ARGB_8888);
        //??????????????????????????????resource???????????????
        Bitmap distBitmap = YWIMImageUtils.getFromCacheOrDecodeResource(R.drawable.left_bubble);
        NinePatch np = new NinePatch(distBitmap, distBitmap.getNinePatchChunk(), null);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rectSrc = new Rect(0, 0, input.getWidth(), input.getHeight());
        final RectF rectDist = new RectF(0, 0, input.getWidth(), input.getHeight());
        np.draw(canvas, rectDist);
        canvas.drawARGB(0, 0, 0, 0);
        //??????Xfermode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(input, rectSrc, rectSrc, paint);
        return output;
    }

    /**
     * ???????????????????????????????????????????????????Bitmap???????????????SDK????????????????????????????????????????????????Bitmap????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????
     * 1.?????? {@link #needRoundChattingImage}??????return false(???????????????)????????????????????????
     * 2.?????????{@link #getRightImageMsgBackgroundResId}??????return???1??????????????????
     *
     * @param input ???????????????????????????
     * @return ????????????Bitmap
     */
    public Bitmap processBitmapOfRightImageMsg(Bitmap input) {
        Bitmap output = Bitmap.createBitmap(input.getWidth(),
                input.getHeight(), Bitmap.Config.ARGB_8888);
        //??????????????????????????????resource???????????????
        Bitmap distBitmap = YWIMImageUtils.getFromCacheOrDecodeResource(R.drawable.right_bubble);
        NinePatch np = new NinePatch(distBitmap, distBitmap.getNinePatchChunk(), null);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rectSrc = new Rect(0, 0, input.getWidth(), input.getHeight());
        final RectF rectDist = new RectF(0, 0, input.getWidth(), input.getHeight());
        np.draw(canvas, rectDist);
        canvas.drawARGB(0, 0, 0, 0);
        //??????Xfermode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(input, rectSrc, rectSrc, paint);
        return output;
    }

    /**
     * ?????????????????????
     *
     * @param fragment
     * @param conversation
     * @return true: ???????????????  false?????????????????????
     */
    @Override
    public boolean needHideTitleView(Fragment fragment, YWConversation conversation) {
//        if (conversation.getConversationType() == YWConversationType.Tribe){
//            return true;
//        }
        //@????????????????????????????????????????????????
        return false;
    }

    /**
     * isv????????????????????????view. openIMSDK?????????????????????????????????????????????view. Fragment ???????????????fragment
     */
    @Override
    public View getCustomTitleView(final Fragment fragment,
                                   final Context context, LayoutInflater inflater,
                                   final YWConversation conversation) {
        // ???????????????????????????????????????????????????????????????????????????
        // ???demo?????????????????????????????????????????????????????????????????????????????????

        //TODO ????????????????????????????????????view---???inflate(R.layout.**, new RelativeLayout(context),false)???------?????????inflater???????????????????????????????????????**???????????????????????????????????????wrap_content
        View view = inflater.inflate(R.layout.demo_custom_chatting_title, new RelativeLayout(context), false);
        view.setBackgroundColor(Color.parseColor("#25498F"));
        TextView textView = (TextView) view.findViewById(R.id.title);
        String title = null;
        if (conversation.getConversationType() == YWConversationType.P2P) {
            YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation
                    .getConversationBody();
            if (!TextUtils.isEmpty(conversationBody.getContact().getShowName())) {
                title = conversationBody.getContact().getShowName();
            } else {

                YWIMKit imKit = LoginSampleHelper.getInstance().getIMKit();
                IYWContact contact = imKit.getContactService().getContactProfileInfo(conversationBody.getContact().getUserId(), conversationBody.getContact().getAppKey());
                //??????showName???According to id???
                if (contact != null && !TextUtils.isEmpty(contact.getShowName())) {
                    title = contact.getShowName();
                }
            }
            //???????????????????????????????????????Id
            if (TextUtils.isEmpty(title)) {
                title = conversationBody.getContact().getUserId();
            }
        } else {
            if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                title = ((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeName();
                if (TextUtils.isEmpty(title)) {
                    title = "?????????????????????";
                }
            } else {
                if (conversation.getConversationType() == YWConversationType.SHOP) { //???OpenIM????????????????????????????????????
                    title = AccountUtils.getShortUserID(conversation.getConversationId());
                }
            }
        }
        textView.setText(title);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(15);
        TextView backView = (TextView) view.findViewById(R.id.back);
        backView.setTextColor(Color.parseColor("#FFFFFF"));
        backView.setTextSize(15);
        backView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.demo_common_back_btn_white, 0, 0, 0);
        backView.setGravity(Gravity.CENTER_VERTICAL);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragment.getActivity().finish();
            }
        });

        ImageView btn = (ImageView) view.findViewById(R.id.title_button);
        if (conversation.getConversationType() == YWConversationType.Tribe) {
            btn.setImageResource(R.drawable.aliwx_tribe_info_icon);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String conversationId = conversation.getConversationId();
                    long tribeId = Long.parseLong(conversationId.substring(5));
                    Intent intent = new Intent(fragment.getActivity(), TribeInfoActivity.class);
                    intent.putExtra(TribeConstants.TRIBE_ID, tribeId);
                    fragment.getActivity().startActivity(intent);

                }
            });
            btn.setVisibility(View.VISIBLE);
        } else if (conversation.getConversationType() == YWConversationType.P2P) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YWP2PConversationBody pConversationBody = (YWP2PConversationBody) conversation.getConversationBody();
                    String appKey = pConversationBody.getContact().getAppKey();
                    String userId = pConversationBody.getContact().getUserId();
                    Intent intent = ContactSettingActivity.getContactSettingActivityIntent(context, appKey, userId);
                    context.startActivity(intent);
                }
            });
            btn.setVisibility(View.VISIBLE);

            String feedbackAccount = IMPrefsTools.getStringPrefs(IMChannel.getApplication(), IMPrefsTools.FEEDBACK_ACCOUNT, "");
            if (!TextUtils.isEmpty(feedbackAccount) && feedbackAccount.equals(AccountUtils.getShortUserID(conversation.getConversationId()))) {
                btn.setVisibility(View.GONE);
            }
        }

        //??????????????????@??????
        if (YWSDKGlobalConfigSample.getInstance().enableTheTribeAtRelatedCharacteristic()) {
            if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                View atView = view.findViewById(R.id.aliwx_at_content);
                atView.setVisibility(View.VISIBLE);
                atView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = chattingBizService.getIMKit().getAtMsgListActivityIntent(context, conversation);
                        context.startActivity(intent);
                    }
                });
            }
        }
        return view;
    }


    /**
     * ????????????????????????titlebar????????????????????????
     *
     * @param fragment
     * @param message  ??????????????????????????????ywmessage??????
     * @return true??????????????????????????????????????? false??????????????????????????????(?????????????????????????????????????????????)
     */
    @Override
    public boolean onImagePreviewTitleButtonClick(Fragment fragment, YWMessage message) {
//        Context context = fragment.getActivity();
//        Toast.makeText(context, "?????????????????????~", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * ???????????????????????????
     *
     * @param fragment
     * @param message
     * @return ?????????null, ??????SDK???????????????, ?????????????????????????????????
     */
    @Override
    public String getImageSavePath(Fragment fragment, YWMessage message) {
//        return Environment
//                .getExternalStorageDirectory().getAbsolutePath()
//                + "/alibaba/WXOPENI/????????????";
        return null;
    }

    /**
     * ??????????????????????????????Id
     *
     * @return 0:??????SDK???????????????
     */
    @Override
    public int getDefaultHeadImageResId() {
        return 0;
    }

    /**
     * ?????????????????????????????????
     *
     * @return true:??????????????????
     * <br>
     * false:???????????????????????????????????????
     * <br>
     * ??????????????????true??????????????????{@link #getRoundRectRadius()}???????????????????????????????????????????????????
     */
    @Override
    public boolean isNeedRoundRectHead() {
        return true;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @return 0:??????{@link #isNeedRoundRectHead()}??????true???????????????0???????????????????????????????????????
     */
    @Override
    public int getRoundRectRadius() {
        return 10;
    }

    /**
     * ????????????????????????????????????View,???????????????????????????????????????????????????????????????
     *
     * @param fragment ???????????????Fragment
     * @param intent   ??????????????????Activity???Intent
     * @return ??????????????????View
     */
    @Override
    public View getChattingFragmentCustomViewAdvice(Fragment fragment, Intent intent) {

        if (intent != null && intent.hasExtra("extraTribeId") && intent.hasExtra("conversationType")) {
            final long tribeId = intent.getLongExtra("extraTribeId", 0);
            int conversationType = intent.getIntExtra("conversationType", -1);
            if (tribeId > 0 && conversationType == YWConversationType.Tribe.getValue()) {
                YWIMKit mIMKit = LoginSampleHelper.getInstance().getIMKit();
                if (mIMKit == null) {
                    return null;
                }
                final YWTribe tribe = mIMKit.getTribeService().getTribe(tribeId);

                if (tribe != null && tribe.getMsgRecType() == YWProfileSettingsConstants.TRIBE_MSG_REJ) { //?????????????????????????????????
                    final Activity context = fragment.getActivity();
                    final TextView view = new TextView(context);
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) context.getResources().getDimension(R.dimen.hint_text_view_height));
                    lp.setMargins(0, (int) context.getResources().getDimension(R.dimen.title_bar_height), 0, 0);
                    view.setLayoutParams(lp);
                    view.setGravity(Gravity.CENTER);
                    view.setBackgroundResource(R.color.third_text_color);
                    view.setText("???????????????????????????????????????????????????");
                    view.setTextColor(context.getResources().getColor(R.color.aliwx_common_bg_white_color));
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog alertDialog = new WxAlertDialog.Builder(context)
                                    .setTitle("??????")
                                    .setMessage("????????????????????????????????????????????????????????????????????????")
                                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            receiveTribeMsg(tribe, view);
                                            dialog.dismiss();
                                        }
                                    }).create();
                            alertDialog.show();
                        }
                    });

                    return view;
                }
            }
        }
        return null;
    }

    /**
     * ????????????????????????????????????????????????View
     *
     * @param fragment ???????????????Fragment
     * @param intent   ??????????????????Activity???Intent
     * @return
     */
    @Override
    public boolean isUseChattingCustomViewAdvice(Fragment fragment, Intent intent) {
        if (intent != null && intent.hasExtra("extraTribeId") && intent.hasExtra("conversationType")) {
            long tribeId = intent.getLongExtra("extraTribeId", 0);
            int conversationType = intent.getIntExtra("conversationType", -1);
            if (tribeId > 0 && conversationType == YWConversationType.Tribe.getValue()) {
                return true;
            }
        }
        return false;
    }

    private void receiveTribeMsg(YWTribe tribe, final View view) {
        LoginSampleHelper.getInstance().getIMKit().getIMCore().getTribeService().unblockTribe(tribe, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(int code, String info) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    /**
     * getView??????????????????View??????????????????????????????????????????item???View??????????????????,?????????View???Padding???
     *
     * @param msg
     * @param rightItemParentView
     * @param fragment
     * @param conversation
     */
    @Override
    public void modifyRightItemParentViewAfterSetValue(YWMessage msg, RelativeLayout rightItemParentView, Fragment fragment, YWConversation conversation) {
//        if(msg!=null&&rightItemParentView!=null&&msg.getSubType()==YWMessage.SUB_MSG_TYPE.IM_IMAGE||msg.getSubType()==YWMessage.SUB_MSG_TYPE.IM_GIF){
//            rightItemParentView.setPadding(rightItemParentView.getPaddingLeft(), rightItemParentView.getPaddingTop(), 0, rightItemParentView.getPaddingBottom());
//        }
    }

    /**
     * getView??????????????????View??????????????????????????????????????????item???View??????????????????,?????????View???Padding???
     *
     * @param msg
     * @param leftItemParentView
     * @param fragment
     * @param conversation
     */
    @Override
    public void modifyLeftItemParentViewAfterSetValue(YWMessage msg, RelativeLayout leftItemParentView, Fragment fragment, YWConversation conversation) {

//        if(msg!=null&&leftItemParentView!=null&&msg.getSubType()==YWMessage.SUB_MSG_TYPE.IM_IMAGE||msg.getSubType()==YWMessage.SUB_MSG_TYPE.IM_GIF) {
//            leftItemParentView.setPadding(0, leftItemParentView.getPaddingTop(), leftItemParentView.getPaddingRight(), leftItemParentView.getPaddingBottom());
//        }
    }

    /**
     * ??????????????????ChattingReplyBar
     *
     * @return
     */
    @Override
    public boolean needHideChattingReplyBar() {
        return false;
    }

    /**
     * ??????????????????ChattingReplyBar
     *
     * @return
     */
    @Override
    public boolean needHideChattingReplyBar(YWConversation conversation) {
        return false;
    }

    /**
     * ??????????????????????????????
     *
     * @return true:??????????????????
     * false:??????????????????
     */
    @Override
    public boolean needHideFaceView() {
        return false;
    }

    /**
     * ??????????????????????????????
     *
     * @return true:????????????????????????
     * false:????????????????????????
     */
    @Override
    public boolean needHideVoiceView() {
        return false;
    }

    /**
     * ???????????????ChattingReplyBar??????(?????????dp)
     *
     * @return ???????????????????????????0, ??????????????????
     */
    @Override
    public int getCustomChattingReplyBarHeight() {
        return 0;
    }

    /**
     * ????????????????????????????????????(?????????dp)
     *
     * @return ???????????????????????????0, ??????????????????
     */
    @Override
    public int getCustomChattingInputEditTextHeight() {
        return 0;
    }

    /**
     * ????????????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getFaceViewBgResId() {
        return 0;
    }

    /**
     * ??????"+???"??????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getExpandViewCheckedBgResId() {
        return 0;
    }

    /**
     * ??????"+???"????????????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getExpandViewUnCheckedBgResId() {
        return 0;
    }

    /**
     * ??????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getSendButtonBgId() {
        return 0;
    }

    /**
     * ????????????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getVoiceViewBgResId() {
        return 0;
    }

    /**
     * ??????????????????????????????Id
     *
     * @return
     */
    @Override
    public int getKeyboardViewBgResId() {
        return 0;
    }

    /**
     * UI?????????????????????
     *
     * @return
     */
    @Override
    public boolean onlySupportAudio() {
        return false;
    }

    public void onCustomDrawRecordButton(Canvas canvas, RecordButton button) {
        //??????????????????????????????????????????
//        if (button.isLongPress()){
//            button.setText("??????");
//        }else{
//            button.setText("??????");
//        }
    }

    /**
     * ??????????????????ImageView
     *
     * @param view
     * @param index     0,1,2,3??????????????????????????????????????????????????????,??????????????????
     * @param direction 0????????????1?????????
     */
    @Override
    public void onSetAudioContentImage(ImageView view, int index, int direction) {

    }

    /**
     * ??????ChattingReplyBar??????item????????????????????????????????????????????????????????????????????????plugin????????????????????????pluginItems???
     * ????????????????????????????????????pluginItems?????????????????????pluginItems?????????????????????removeAll??????addAll
     * <p>??????????????????????????????
     * <p>??????</p>
     * <p>????????????({@link YWInputViewPlugin#setNeedHide(boolean)}),</p>
     * <p>Item?????????View???????????????,</p>
     * </p>
     * <p>
     * SDK???????????????id??????
     * <p>{@link YWChattingPlugin.ReplyBarPlugin#VOICE_VIEW}
     * <p>{@link YWChattingPlugin.ReplyBarPlugin#INPUT_EDIT_TEXT}
     * <p>{@link YWChattingPlugin.ReplyBarPlugin#FACE_VIEW}
     * <p>{@link YWChattingPlugin.ReplyBarPlugin#EXPAND_VIEW}
     * <p>
     * <p/>
     * ???????????????????????????{@link YWInputViewPlugin}????????????replyBarItems???
     *
     * @param conversation ChattingReplyBar????????????,????????????????????????????????????????????????ChattingReplyBar???item????????????
     * @param pluginItems  item????????????????????????sdk???????????????4???item????????????????????????????????????????????????+??????????????????????????????0,1,2,3
     * @return ??????sdk?????????????????????????????????
     */
    @Override
    public List<YWInputViewPlugin> adjustCustomInputViewPlugins(final Fragment fragment, YWConversation conversation, List<YWInputViewPlugin> pluginItems) {
//        if (pluginItems != null && pluginItems.size() > 0) {
//            //???????????????????????????????????????id????????????????????????
//            for (YWInputViewPlugin plugin : pluginItems) {
//                if (plugin.getId() == YWChattingPlugin.ReplyBarPlugin.VOICE_VIEW) {
//                    plugin.setNeedHide(true);//????????????????????????
//                }
//            }
//            //??????????????????plugin???????????????????????????marginLeft??????
//            final View plugin = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.demo_extra_item_layout, null);
//            //TODO ?????????id?????????4??????
//            final YWInputViewPlugin pluginToAdd = new YWInputViewPlugin(plugin, 4);
//            plugin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(fragment.getActivity(), "????????????Id???:" + pluginToAdd.getId() + "?????????item", Toast.LENGTH_LONG).show();
//                }
//            });
//            pluginToAdd.setIndex(0);//?????????index??????????????????????????????pluginItem???????????????????????????????????????
//            pluginItems.add(pluginToAdd);
//        }
        return pluginItems;
    }

    private IMChattingBizService chattingBizService;

    @Override
    public void onInitFinished(final IMChattingBizService bizService) {
        chattingBizService = bizService;
//        final Context context = chattingBizService.getFragment().getContext();
//        IYWChattingReplyBar replyBar = chattingBizService.getChattingReplyBar();
//        replyBar.setInputEditTextRightDrawable(context.getResources().getDrawable(R.drawable.ww_chat_voice), new OnEditTextDrawableClickListener() {
//            @Override
//            public void onClick() {
//                IMNotificationUtils.getInstance().showToast(context, "????????????????????????");
//            }
//        });
    }


    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param conversation ????????????
     * @param isSelf       ?????????????????????????????????true???????????????????????????false????????????????????????
     * @param type         ???????????????1??????????????????2????????????3?????????
     * @return ????????????Id
     */
    @Override
    public int getCustomTextColor(YWConversation conversation, boolean isSelf, int type) {
        if (type == 1) {
            //??????????????????
        } else if (type == 2) {
            //???????????????
        } else if (type == 3) {
            //????????????
        }
        return super.getCustomTextColor(conversation, isSelf, type);
    }

    /**
     * ??????????????????item??????????????????????????????????????????????????????
     *
     * @param conversation ??????????????????????????????
     * @param self         ??????????????????????????????
     * @return true???????????????  false??????????????????
     */
    @Override
    public boolean needShowName(YWConversation conversation, boolean self) {
        if (self) {  //??????????????????????????????????????????
            return false;
        } else {
            YWConversationType type = conversation.getConversationType();
            if (type == YWConversationType.SHOP || type == YWConversationType.Tribe) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * ????????????????????????????????????
     */
    @Override
    public boolean needAlignReplyBar() {
        return true;
    }

    //???????????? ???chatfragment??????
    private boolean mIsMyComputerConv;

    @Override
    public void onStart(Fragment f, Intent intent, ChattingDetailPresenter presenter) {
        super.onStart(f, intent, presenter);
        mIsMyComputerConv = intent.getBooleanExtra(ChattingDetailPresenter.EXTRA_MYCOMPUTER, false);
    }

    public boolean isMyComputerConv() {
        return mIsMyComputerConv;
    }

    @Override
    public List<String> getMenuList(IYWContact loginAccount, YWMessage message) {
        List<String> list = new ArrayList<String>();
        list.add("??????????????????");
        return list;
    }

    @Override
    public void onItemClick(IYWContact loginAccount, YWMessage message, Bitmap bitmap, String item) {
//        IMNotificationUtils.getInstance().showToast(item, YWChannel.getApplication());
    }
}
