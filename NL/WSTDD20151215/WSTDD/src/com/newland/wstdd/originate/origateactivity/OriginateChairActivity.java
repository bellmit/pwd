/**
 * 
 */
package com.newland.wstdd.originate.origateactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.fileupload.FormFile;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.selectphoto.AlbumsActivity;
import com.newland.wstdd.common.selectphoto.PhotoUpImageItem;
import com.newland.wstdd.common.tools.DateTimePickDialogUtil;
import com.newland.wstdd.common.tools.DateUtil;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.beanresponse.HeadImgRes;
import com.newland.wstdd.mine.beanresponse.TddUserCertificate;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.netutils.MessageUtil;
import com.newland.wstdd.netutils.WBResponse;
import com.newland.wstdd.originate.beanrequest.SelectMustItemInfo;
import com.newland.wstdd.originate.handle.SingleActivityPublishHandle;
import com.newland.wstdd.originate.origateactivity.beanrequest.SingleActivityPublishReq;
import com.newland.wstdd.originate.origateactivity.beanresponse.SingleActivityPublishRes;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 发起--讲座
 * 
 * @author H81 2015-11-5
 * 
 */
public class OriginateChairActivity extends BaseFragmentActivity implements OnPostListenerInterface, IWXAPIEventHandler {
	TextView addPhotoIcon;//没有图片的时候添加图片的标志
	private TddActivity editTddActivity;// 这里表示的是从服务器返回的一个活动的id，不管是编辑还是发布都会有这个id
	private AppContext appContext;
	private Intent intent;// 接收首页传递过来的值
	// 活动的类型id
	private int activiyType;// 活动的类型
	// 活动的动作
	private String activityAction;// 是发起还是编辑
	// 图片上传的相关信息
	private List<String> filePathList;// 本地图片的列表
	private List<String> imgUrl = new ArrayList<String>();// 服务器返回的图片列表
	private List<FormFile> formFiles;
	private HttpMultipartPostOriginate post;
	// 活动图片选择的相关操作
	private GridView gridView;// 活动图片的数据
	private ArrayList<PhotoUpImageItem> arrayList = new ArrayList<PhotoUpImageItem>();// 活动图片的选择
	private SelectedImagesAdapter adapter;// 活动图片的适配器
	// 必选项
	private ScrollView mustScrollView;// 必选项容器
	private GridView selectGridView;// 必选项的网格
	private ArrayList<SelectMustItemInfo> selectItemList = new ArrayList<SelectMustItemInfo>();// 必选项的数据
	private SelectedMustAdapter selectItemAdapter;// 必选项的适配器
	private ImageView mustImageView;// 必选项的图表指向
	// 必填项的选择
	private EditText mOriginate_originateactivity_title_edt;// 开讲主题
	private TextView mOriginate_originateactivity_starttime_edt;// 开始时间
	private EditText mOriginate_originateactivity_address_edt;// 地址
	private EditText mOriginate_originateactivity_limitperson_edt;// 人数限制
	private TextView mOriginate_originateactivity_endtime_edt;// 报名截止
	private TextView mOriginate_originateactivity_mustselect_edt;// 必填项
	private EditText mOriginate_originateactivity_describe_edt;// 填写描述，让更多的人参与
	private PengTextView mOriginate_chair_remain_tv;// 输入*/2500 字

	private IntentFilter filter;// 定一个广播接收过滤器
	private LinearLayout mOriginate_chair_theme_ll;// 讲座主题ll
	private LinearLayout mOriginate_chair_time_ll;// 开讲时间ll
	private LinearLayout mOriginate_chair_location_ll;// 开讲地点ll
	private LinearLayout mOriginate_chair_endtime_ll;// 报名截止ll
	private LinearLayout mOriginate_chair_numnolimit_ll;// 人数不限ll 设置一个监听事件
	private LinearLayout mOriginate_chair_mustselect_ll;// 必选项
	private RelativeLayout mOriginate_chair_nonempty_rl;// 高级内容

	private String initStartDateTime = ""; // 日期时间控件初始化开始时间
	private int inputNum = 2500;// 限制输入的字数
	
	// 服务器相关的
	private SingleActivityPublishRes singleActivityPublishRes;// 服务器返回的信息
	private SingleActivityPublishHandle handler = new SingleActivityPublishHandle(this);
	/**
	 * 分享相关的
	 */
	private PopupWindow popupWindow;// 分享窗口
	// 微信
	private static final String appid = "wx1b84c30d9f380c89";// 微信的appid
	private IWXAPI wxApi;// 微信的API
	// QQ
	private Tencent mTencent;
	private static final String APP_ID = "1104957952";

	// private TddActivity editTddActivity;//从活动编辑界面发送过来

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_originate_chair);
		initStartDateTime = DateUtil.getDateNowString2();// 获取当前时间格式为 2013年9月3日
		appContext = AppContext.getAppContext(); // 14:44
		// 获取到activity_type
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		activiyType = bundle.getInt("activity_type");// 默认为0
		activityAction = bundle.getString("activity_action");
		editTddActivity = (TddActivity) bundle.getSerializable("tddActivity");

		// 注册广播
		filter = new IntentFilter("ORIGUNATE_CHAIR_PHOTO");
		registerReceiver(broadcastReceiver, filter);// 在对象broadcastReceiver中进行接收的相应处理
		// 分享
		// QQ
		final Context ctxContext = this.getApplicationContext();
		mTencent = Tencent.createInstance(APP_ID, ctxContext);
		mHandler = new Handler();
		// weixin
		wxApi = WXAPIFactory.createWXAPI(this, appid);
		wxApi.registerApp(appid);

		setTitle();// 设置标题
		initView();// 控件的绑定 包括初始化必选项 图片
		if (editTddActivity == null) {
			initMustSelect();// 初始化必选的五项
		}
		initMust();// 添加必选项中的“+”这个选项
		init();// 图片选择初始化
		setclickListener();

	}

	// 初始化必选的五项
	private void initMustSelect() {
		// TODO Auto-generated method stub
		// 初始化必选的两个
		if (selectItemList != null && !selectItemList.isEmpty()) {
			selectItemList.clear();
		} else {
			SelectMustItemInfo mustItem1 = new SelectMustItemInfo();
			mustItem1.setSelectItem("姓名");
			mustItem1.setSelect(true);
			selectItemList.add(mustItem1);
			SelectMustItemInfo mustItem2 = new SelectMustItemInfo();
			mustItem2.setSelectItem("手机");
			mustItem2.setSelect(true);
			selectItemList.add(mustItem2);
			SelectMustItemInfo mustItem3 = new SelectMustItemInfo();
			mustItem3.setSelectItem("身份证");
			mustItem3.setSelect(false);
			selectItemList.add(mustItem3);
			SelectMustItemInfo mustItem4 = new SelectMustItemInfo();
			mustItem4.setSelectItem("邮箱");
			mustItem4.setSelect(false);
			selectItemList.add(mustItem4);
			SelectMustItemInfo mustItem5 = new SelectMustItemInfo();
			mustItem5.setSelectItem("性别");
			mustItem5.setSelect(false);
			selectItemList.add(mustItem5);

			if (editTddActivity != null) {
				mOriginate_originateactivity_mustselect_edt.setText(editTddActivity.getSignAttr().replace(",", "/"));
			} else {
				mOriginate_originateactivity_mustselect_edt.setText("姓名/手机");
			}

		}
	}

	private void setTitle() {
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);// 发布的操作
		leftBtn.setVisibility(View.VISIBLE);
		centerTitle.setText("发起讲座");
		if ("publish".equals(activityAction)) {
			rightTv.setText("发布");
		}
		if ("edit".equals(activityAction)) {
			rightTv.setText("编辑");
		}
		rightTv.setTextColor(getResources().getColor(R.color.red));
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setOnClickListener(this);
		leftBtn.setOnClickListener(this);

	}

	public void initView() {
		addPhotoIcon = (TextView) findViewById(R.id.originate_chair_addphoto_icon);
		addPhotoIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPhotoIcon.setVisibility(View.GONE);
				Intent intent = new Intent(OriginateChairActivity.this, AlbumsActivity.class);
				arrayList.remove(arrayList.size() - 1);
				intent.putExtra("selectImageList", arrayList);
				intent.putExtra("activityType", "OriginateChairActivity");
				startActivity(intent);
			}
		});
		mOriginate_originateactivity_title_edt = (EditText) findViewById(R.id.originate_originateactivity_title_edt);// 主题
		mOriginate_originateactivity_starttime_edt = (TextView) findViewById(R.id.originate_chair_starttime_tv);// 开始时间
		mOriginate_originateactivity_address_edt = (EditText) findViewById(R.id.originate_originateactivity_address_edt);// 地址
		mOriginate_originateactivity_endtime_edt = (TextView) findViewById(R.id.originate_chair_endtime_tv);// 报名截止
		mOriginate_originateactivity_limitperson_edt = (EditText) findViewById(R.id.originate_originateactivity_limitperson_edt);// 人数限制
		mOriginate_originateactivity_mustselect_edt = (TextView) findViewById(R.id.originate_originateactivity_signattr_tv);// 必填项
		mOriginate_originateactivity_describe_edt = (EditText) findViewById(R.id.originate_chair_describe_edt);// 描述

		mustScrollView = (ScrollView) findViewById(R.id.activity_originate_chair_mustscrollview);// 必选项的容器
		mustImageView = (ImageView) findViewById(R.id.activity_originate_chair_mustselect_icon);
		gridView = (GridView) findViewById(R.id.selected_images_gridv);
		gridView.setVisibility(View.GONE);
		selectGridView = (GridView) findViewById(R.id.selected_must_gridv);
		mOriginate_chair_theme_ll = (LinearLayout) findViewById(R.id.originate_chair_theme_ll);
		mOriginate_chair_time_ll = (LinearLayout) findViewById(R.id.originate_chair_starttime_ll);
		mOriginate_chair_location_ll = (LinearLayout) findViewById(R.id.originate_chair_location_ll);
		mOriginate_chair_endtime_ll = (LinearLayout) findViewById(R.id.originate_chair_endtime_ll);
		mOriginate_chair_numnolimit_ll = (LinearLayout) findViewById(R.id.originate_chair_numnolimit_ll);
		mOriginate_chair_mustselect_ll = (LinearLayout) findViewById(R.id.originate_chair_mustselect_ll);
		mOriginate_chair_mustselect_ll.setOnClickListener(this);// 设置监听事件
																// 显示隐藏必选项
		mOriginate_chair_remain_tv = (PengTextView) findViewById(R.id.originate_chair_remain_tv);
		mOriginate_originateactivity_starttime_edt = (TextView) findViewById(R.id.originate_chair_starttime_tv);

		mOriginate_chair_time_ll.setOnClickListener(this);
		mOriginate_originateactivity_endtime_edt = (TextView) findViewById(R.id.originate_chair_endtime_tv);
		mOriginate_chair_endtime_ll.setOnClickListener(this);
		// 限制输入的个数
		mOriginate_originateactivity_describe_edt.addTextChangedListener(new TextWatcher() {
			private boolean isOutOfBounds = false;
			int end;

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > inputNum) {
					isOutOfBounds = true;
				} else {
					mOriginate_chair_remain_tv.setText(s.length() + "/2500");
					isOutOfBounds = false;
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
				if (isOutOfBounds) {
					UiHelper.ShowOneToast(OriginateChairActivity.this, "字符超过了");
					if (s.length() > inputNum) {
						s.delete(inputNum, s.length());
						end = inputNum;
					} else if (s.length() > 20 && s.length() <= inputNum) {
						s.delete(20, s.length());
						end = 20;
					}
					end = s.length();
					mOriginate_originateactivity_describe_edt.setSelection(end);// 设置光标在最后
					mOriginate_chair_remain_tv.setText(s.length() + "/2500");
				}
			}
		});
		/**
		 * 若果是编辑活动的话，就将传过来的值，填写到界面上去
		 */
		if (editTddActivity != null) {
			mOriginate_originateactivity_title_edt.setText(editTddActivity.getActivityTitle());
			mOriginate_originateactivity_starttime_edt.setText(editTddActivity.getActivityTime().substring(0, editTddActivity.getActivityTime().lastIndexOf(":")));
			mOriginate_originateactivity_address_edt.setText(editTddActivity.getActivityAddress());
			mOriginate_originateactivity_endtime_edt.setText(editTddActivity.getOffTime().substring(0, editTddActivity.getOffTime().lastIndexOf(":")));
			mOriginate_originateactivity_limitperson_edt.setText(editTddActivity.getLimitPerson() + "");
			mOriginate_originateactivity_mustselect_edt.setText(editTddActivity.getSignAttr().replace(",", "/"));
			// 显示必选项
			String[] strs = editTddActivity.getSignAttr().split(",");
			for (String substr : strs) {
				SelectMustItemInfo selectMustItemInfo = new SelectMustItemInfo();
				selectMustItemInfo.setSelect(true);
				selectMustItemInfo.setSelectItem(substr);
				selectItemList.add(selectMustItemInfo);
			}

			mOriginate_originateactivity_describe_edt.setText(editTddActivity.getActivityDescription());// 描述

			// 显示图片 首先根据
			PhotoUpImageItem photoUpImageItem1 = new PhotoUpImageItem();
			photoUpImageItem1.setCover(true);
			photoUpImageItem1.setImageId(editTddActivity.getImage1());
			photoUpImageItem1.setImagePath(editTddActivity.getImage1());
			photoUpImageItem1.setSelected(true);
			arrayList.add(photoUpImageItem1);
			// 这里需要注意 11，22 11
			if (editTddActivity.getImage() != null && !"".equals(editTddActivity.getImage())) {
				String[] imgstr = editTddActivity.getImage().split(",");
				for (String substr : imgstr) {
					PhotoUpImageItem photoUpImageItem = new PhotoUpImageItem();
					photoUpImageItem.setCover(false);
					photoUpImageItem.setImageId(substr);
					photoUpImageItem.setImagePath(substr);
					photoUpImageItem.setSelected(true);
					arrayList.add(photoUpImageItem);
				}
			}
		}

	}

	private void getPopWindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();
			return;
		} else {
			initPopupWindow();
		}
	}

	/**
	 * 分享窗口初始化
	 */
	private void initPopupWindow() {
		View popupWindow_view = this.getLayoutInflater().inflate(R.layout.activity_originate_chair_popwindow, null, false);
		popupWindow = new PopupWindow(popupWindow_view, AppContext.getAppContext().getScreenWidth() * 4 / 5, LayoutParams.WRAP_CONTENT, true);
		ImageView cancelImageView = (ImageView) popupWindow_view.findViewById(R.id.originate_chair_popwindow_delete);
		cancelImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				finish();
			}
		});
		popupWindow.setOutsideTouchable(false);
		// popupWindow.setAnimationStyle(R.style.pop_anim);
		// popupWindow_view.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if (popupWindow != null && popupWindow.isShowing()) {
		// popupWindow.dismiss();
		// popupWindow = null;
		// }
		// return false;
		// }
		// });

		PengTextView weixinFriend, weixinZone, qqFriend;// 弹出窗口三个控件
		weixinFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin);
		weixinZone = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin_zone);
		qqFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_qq);
		// 微信好友分享
		weixinFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friend(v);
			}
		});

		// 微信朋友圈分享
		weixinZone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friendline(v);
			}
		});

		// qq好友分享
		qqFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickShareToQQ();
			}
		});

		// 设置透明度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		// 显示窗口 位置
		popupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.CENTER, 0, 0);//
		popupWindow.setOutsideTouchable(false);

	}

	// 发布活动
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 必填项
					StringBuffer signAttr = new StringBuffer();
					for (int i = 0, size = selectItemList.size(); i < size; i++) {
						if (selectItemList.get(i).isSelect()) {
							signAttr.append(selectItemList.get(i).getSelectItem());
							signAttr.append(",");
						}
					}
					if (signAttr != null && !"".equals(signAttr)) {
						signAttr.deleteCharAt(signAttr.lastIndexOf(","));

					}
					// 需要发送一个request的对象进行请求
					SingleActivityPublishReq reqInfo = new SingleActivityPublishReq();
					if (editTddActivity == null) {
						editTddActivity = new TddActivity();
					}
					editTddActivity.setActivityTitle(mOriginate_originateactivity_title_edt.getText().toString());// 活动主题
					editTddActivity.setActivityTime(mOriginate_originateactivity_starttime_edt.getText().toString());// 活动时间
					editTddActivity.setActivityAddress(mOriginate_originateactivity_address_edt.getText().toString());// 活动地址
					editTddActivity.setOffTime(mOriginate_originateactivity_endtime_edt.getText().toString());// 截止时间
					editTddActivity.setLimitPerson(Integer.valueOf(mOriginate_originateactivity_limitperson_edt.getText().toString()));// 限制人数
					editTddActivity.setSignAttr(signAttr.toString());// 报名属性（用户必填项）
					// tddActivity.setSignAttr("姓名,手机");// 报名属性（用户必填项）
					editTddActivity.setActivityDescription(mOriginate_originateactivity_describe_edt.getText().toString());// 活动内容
					editTddActivity.setActivityType(1);
					for (int i = 0; i < arrayList.size(); i++) {
						if (arrayList.get(i).isCover()) {
							editTddActivity.setImage1(arrayList.get(i).getImagePath());
							break;// 设置完封面就可以退出循环了
						} else {
							editTddActivity.setImage1(arrayList.get(0).getImagePath());
						}
					}

					// 必须的地点 省份 跟 城市
					editTddActivity.setProvince("全国");
					editTddActivity.setCity("全国");
					// 封装图片
					StringBuffer imgAttr = new StringBuffer();
					for (int i = 0; i < arrayList.size(); i++) {
						if (!"".equals(arrayList.get(i).getImageId())) {
							imgAttr.append(arrayList.get(i).getImagePath());
							if (i != arrayList.size() - 1) {
								imgAttr.append(",");
							}

						}
					}

					// for (int j = 0; j < imgUrl.size; j++) {
					//
					// }
					editTddActivity.setImage(imgAttr.toString());
					editTddActivity.setActivityType(activiyType);
					TddUserCertificate tddUserCertificate = new TddUserCertificate();
					tddUserCertificate.setUserId(appContext.getUserId());
					tddUserCertificate.setNickName(appContext.getNickName());
					tddUserCertificate.setUserName("sas");
					tddUserCertificate.setCerStatus(1);
					tddUserCertificate.setSex(1);
					tddUserCertificate.setHeadimgurl("");
					tddUserCertificate.setMobilePhone("");
					tddUserCertificate.setEmail("");
					tddUserCertificate.setIdentity("350321199200008888");
					reqInfo.setTddActivity(editTddActivity);
					reqInfo.setTddUserCertificate(tddUserCertificate);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityPublishRes> ret = null;
					if ("publish".equals(activityAction)) {
						ret = mgr.getSingleActivityPublishInfo(reqInfo, activityAction, "");// 泛型类，
					} else if ("edit".equals(activityAction)) {
						ret = mgr.getSingleActivityPublishInfo(reqInfo, activityAction, editTddActivity.getActivityId());// 泛型类，
					}

					Message message = new Message();
					message.what = SingleActivityPublishHandle.SINGLE_ACTIVITY_PUBLISH;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						singleActivityPublishRes = (SingleActivityPublishRes) ret.getObj();
						message.obj = singleActivityPublishRes;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 必选项的的尾部添加操作
	private void initMust() {

		// 选择必选项的操作
		if (selectItemList != null) {
			// if(selectItemList.size()<7){
			SelectMustItemInfo mustItem = new SelectMustItemInfo();
			mustItem.setSelectItem("+");
			mustItem.setSelect(false);
			selectItemList.add(mustItem);
			// }

		} else {
			selectItemList = new ArrayList<SelectMustItemInfo>();
			SelectMustItemInfo mustItem = new SelectMustItemInfo();
			mustItem.setSelectItem("+");
			mustItem.setSelect(false);
			selectItemList.add(mustItem);
		}

		selectItemAdapter = new SelectedMustAdapter(OriginateChairActivity.this, selectItemList);
		selectGridView.setAdapter(selectItemAdapter);
		selectItemAdapter.notifyDataSetChanged();
	}

	// 选择图片的相关操作
	@SuppressWarnings("unchecked")
	private void init() {
		// arrayList = (ArrayList<PhotoUpImageItem>)
		// getIntent().getSerializableExtra("selectIma");
		if (arrayList != null) {
			if (arrayList.size() < 7) {// 如果
				PhotoUpImageItem lastItem = new PhotoUpImageItem();
				lastItem.setImageId("");
				lastItem.setImagePath("");
				lastItem.setSelected(true);
				arrayList.add(lastItem);
			}

		} else {
			arrayList = new ArrayList<PhotoUpImageItem>();
			PhotoUpImageItem lastItem = new PhotoUpImageItem();
			lastItem.setImageId("");
			lastItem.setImagePath("");
			lastItem.setSelected(true);
			arrayList.add(lastItem);
		}

		adapter = new SelectedImagesAdapter(OriginateChairActivity.this, arrayList);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	/**
	 * 监听事件
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 开始时间
		case R.id.originate_chair_starttime_ll:
			DateTimePickDialogUtil startTimePicKDialog = new DateTimePickDialogUtil(OriginateChairActivity.this, initStartDateTime);
			startTimePicKDialog.dateTimePicKDialog(mOriginate_originateactivity_starttime_edt);
			break;
		// 结束时间
		case R.id.originate_chair_endtime_ll:
			DateTimePickDialogUtil endTimePicKDialog = new DateTimePickDialogUtil(OriginateChairActivity.this, initStartDateTime);
			endTimePicKDialog.dateTimePicKDialog(mOriginate_originateactivity_endtime_edt);

			break;
		case R.id.head_left_iv:// 返回
			finish();
			break;
		// 必须选项 显示出必选项的gridview
		case R.id.originate_chair_mustselect_ll:
			if (mustScrollView.getVisibility() == View.VISIBLE) {
				mustImageView.setImageDrawable(getResources().getDrawable(R.drawable.right_expansion));
				mustScrollView.setVisibility(View.GONE);
			} else {
				mustScrollView.setVisibility(View.VISIBLE);
				mustImageView.setImageDrawable(getResources().getDrawable(R.drawable.down_expansion));
			}
			selectItemAdapter.setArrayList(selectItemList);
			selectItemAdapter.notifyDataSetChanged();
			break;

		// 发布活动
		case R.id.head_right_tv:
			// 发布的时候需要进行判断日期的开始日期是否小于结束日期
			if("".equals(appContext.getIsLogin())||"false".equals(appContext.getIsLogin())){
				UiHelper.ShowOneToast(OriginateChairActivity.this, "该操作需要登录后进行！");
			}else if("true".equals(appContext.getIsLogin())){
			String startTimeString = mOriginate_originateactivity_starttime_edt.getText().toString();
			String endTimeString = mOriginate_originateactivity_endtime_edt.getText().toString();
			if (StringUtil.isEmpty(mOriginate_originateactivity_title_edt.getText().toString())) {
				UiHelper.ShowOneToast(OriginateChairActivity.this, "活动主题不能为空");
				return;
			} else if (StringUtil.isEmpty(mOriginate_originateactivity_starttime_edt.getText().toString())) {
				UiHelper.ShowOneToast(OriginateChairActivity.this, "开始时间不能为空");
				return;
			} else if (StringUtil.isEmpty(mOriginate_originateactivity_address_edt.getText().toString())) {
				UiHelper.ShowOneToast(OriginateChairActivity.this, "活动地点不能为空");
				return;
			} else if (StringUtil.isEmpty(mOriginate_originateactivity_endtime_edt.getText().toString())) {
				UiHelper.ShowOneToast(OriginateChairActivity.this, "截止时间不能为空");
				return;
			} else if (StringUtil.isEmpty(mOriginate_originateactivity_limitperson_edt.getText().toString())) {
				UiHelper.ShowOneToast(OriginateChairActivity.this, "人数限制不能为空");
				return;
			} else if (StringUtil.isNotEmpty(startTimeString) && StringUtil.isNotEmpty(endTimeString) && !DateUtil.judgeTimeLarge(startTimeString, endTimeString)) {

				UiHelper.ShowOneToast(OriginateChairActivity.this, "截止时间不能迟于活动时间");

			}

			else {

				upload();
			}
		}
			break;
		default:
			break;
		}
	}

	private void setclickListener() {
		// 必须选项
		selectGridView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				// 这里的arraylist是包含空图片的// 点击的是最后一个且为空
				if (position == selectItemList.size() - 1 && "+".equals(selectItemList.get(position).getSelectItem())) {
					// 判断是否为添加标志 弹出对话框添加
					final EditText et = new EditText(OriginateChairActivity.this);

					et.setBackground(null);
					AlertDialog dialog = new AlertDialog.Builder(OriginateChairActivity.this).setTitle("请输入新增选项").setView(et)
							.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									String input = et.getText().toString();
									// TODO 比对内容是否已有
									boolean isSame = false;
									if (input.trim().equals("")) {
										Toast.makeText(getApplicationContext(), "内容不能为空！", Toast.LENGTH_LONG).show();
									} else {
										for (int i = 0; i < selectItemList.size(); i++) {
											isSame = input.equals(selectItemList.get(i).getSelectItem());
											if (isSame) {
												Toast.makeText(getApplicationContext(), "内容不能相同！" + input, Toast.LENGTH_LONG).show();
												return;
											}
										}
										if (!isSame) {
											// 添加进了选项
											SelectMustItemInfo tempInfo = new SelectMustItemInfo();
											tempInfo.setSelect(true);
											tempInfo.setSelectItem(input);
											selectItemList.set(selectItemList.size() - 1, tempInfo);
											StringBuffer signAttr = new StringBuffer();
											for (int i = 0, size = selectItemList.size(); i < size; i++) {
												if (selectItemList.get(i).isSelect()) {
													signAttr.append(selectItemList.get(i).getSelectItem());
													signAttr.append("/");
												}
											}
											signAttr.deleteCharAt(signAttr.lastIndexOf("/"));
											mOriginate_originateactivity_mustselect_edt.setText(signAttr.toString());
											initMust();// 添加“+”项
										}
									}
								}
							}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int which) {

								}
							}).show();
					dialog.setCanceledOnTouchOutside(false);

				} else {
					// 点击其它的
					if (position != 0 && position != 1) {
						if (selectItemList.get(position).isSelect()) {
							selectItemList.get(position).setSelect(false);
						} else {
							selectItemList.get(position).setSelect(true);
						}
						selectItemAdapter.setArrayList(selectItemList);
						selectItemAdapter.notifyDataSetChanged();
						StringBuffer signAttr = new StringBuffer();
						for (int i = 0, size = selectItemList.size(); i < size; i++) {
							if (selectItemList.get(i).isSelect()) {
								signAttr.append(selectItemList.get(i).getSelectItem());
								signAttr.append("/");
							}
						}
						signAttr.deleteCharAt(signAttr.lastIndexOf("/"));
						mOriginate_originateactivity_mustselect_edt.setText(signAttr.toString());
					}
				}
				selectItemAdapter.setArrayList(selectItemList);
				selectItemAdapter.notifyDataSetChanged();
			}

		});

		// 相册选项
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 这里的arraylist是包含空图片的
				if (position == arrayList.size() - 1 && "".equals(arrayList.get(position).getImageId())) {// 点击的是最后一个且为空
					// 判断是否为添加标志
					Intent intent = new Intent(OriginateChairActivity.this, AlbumsActivity.class);
					arrayList.remove(arrayList.size() - 1);
					intent.putExtra("selectImageList", arrayList);
					intent.putExtra("activityType", "OriginateChairActivity");
					startActivity(intent);

				} else {
					Intent intent = new Intent(OriginateChairActivity.this, SelectedCoverActivity.class);
					if ("".equals(arrayList.get(arrayList.size() - 1).getImageId())) {
						arrayList.remove(arrayList.size() - 1);
					}

					intent.putExtra("selectImageList", arrayList);
					intent.putExtra("position", position);// 点击哪一个就跳转到哪一个，然后在设置封面界面上显示出当前的界面
					startActivity(intent);

					// 删除
					// PhotoUpImageItem photoUpImageItem = (PhotoUpImageItem)
					// parent
					// .getItemAtPosition(position);
					// showIsDeleteDialog(photoUpImageItem, position);
				}
			}

		});
	}

	@SuppressWarnings("unused")
	private void showIsDeleteDialog(final PhotoUpImageItem photoUpImageItem, final int position) {
		AlertDialog dialog = new AlertDialog.Builder(this).setMessage("是否删除所选图片").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// 如果是删除最后一个item的话，需要添加 添加图片
				arrayList.remove(photoUpImageItem);
				adapter.setArrayList(arrayList);
				adapter.notifyDataSetChanged();
				/**
				 * 如果全部为充满的话 arraylist是为8张 且没有空图片 如果没有全部充满的话 arraylist是比如6张
				 * 实际是5张的，包括一张空白的
				 */
				// 添加空图片
				if (arrayList.size() == 6 && !"".equals(arrayList.get(arrayList.size() - 1).getImageId())) {// 删除最后一张
					PhotoUpImageItem lastItem = new PhotoUpImageItem();
					lastItem.setImageId("");
					lastItem.setImagePath(null);
					lastItem.setSelected(true);
					arrayList.add(lastItem);
				}

			}
		}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
		dialog.setCanceledOnTouchOutside(false);
	}

	/**
	 * 接收到广播之后 在这里要做三件事情 1.更新数据到界面上去 2.更新pluinfoList的值 3.上传到服务器
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ArrayList<PhotoUpImageItem> tempArrayList = new ArrayList<PhotoUpImageItem>();

			// 这里注意需要区分已有的网络下载下来的跟相册选择的
			/**
			 * 第一种：发布活动的时候，所选的图片都是来自相册的 arrayList=(ArrayList<PhotoUpImageItem>)
			 * intent.getSerializableExtra("selectIma"); 第二种：有网络的也有本地的
			 * 
			 * 
			 * 另一种是设置封面界面的时候传过来的，需要用接
			 */
			// 对选择图片的那张 标签图片进行隐藏跟显示处理
			
			if ("cover".equals(intent.getStringExtra("type"))) {
				arrayList = (ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("selectIma");
			} else {
				if (editTddActivity != null) {
					tempArrayList = (ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("selectIma");

					for (int i = 0; i < tempArrayList.size(); i++) {
						File file = new File(tempArrayList.get(i).getImagePath());
						if (!file.exists()) {
							tempArrayList.remove(tempArrayList.get(i));
							file.delete();
							i -= 1;
						}
					}
					// 去除已经存在的
					for (int j = 0; j < arrayList.size(); j++) {
						File file = new File(arrayList.get(j).getImagePath());
						if (file.exists()) {
							arrayList.remove(arrayList.get(j));
							j -= 1;
						} else {
							file.delete();
						}
					}
					for (int i = 0; i < tempArrayList.size(); i++) {
						PhotoUpImageItem photoUpImageItem = new PhotoUpImageItem();
						photoUpImageItem.setCover(false);
						photoUpImageItem.setImageId(tempArrayList.get(i).getImageId());
						photoUpImageItem.setImagePath(tempArrayList.get(i).getImagePath());
						photoUpImageItem.setSelected(tempArrayList.get(i).isSelected());
						arrayList.add(photoUpImageItem);
					}
					tempArrayList.clear();

				} else {
					arrayList = (ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("selectIma");
				}
			}
			if(arrayList.size()>0){
				addPhotoIcon.setVisibility(View.GONE);
				gridView.setVisibility(View.VISIBLE);
			}else {
				addPhotoIcon.setVisibility(View.VISIBLE);
				gridView.setVisibility(View.GONE);
			}
			// arrayList=(ArrayList<PhotoUpImageItem>)
			// intent.getSerializableExtra("selectIma");
			init();

		}
	};

	@Override
	public void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	// 服务器返回结果
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			switch (responseId) {
			case SingleActivityPublishHandle.SINGLE_ACTIVITY_PUBLISH:
				if (dialog != null) {
					dialog.dismiss();
				}
				singleActivityPublishRes = (SingleActivityPublishRes) obj;
				if (singleActivityPublishRes != null) {
					editTddActivity = singleActivityPublishRes.getTddActivity();
					if ("publish".equals(activityAction)) {
						getPopWindow();
					} else {

						Intent intent0 = new Intent();// 切记
														// 这里的Action参数与IntentFilter添加的Action要一样才可以
						intent0.setAction("RECEIVE_TDDACTIVITY");
						Bundle bundle = new Bundle();
						bundle.putSerializable("tddactivity", editTddActivity);
						intent0.putExtras(bundle);
						sendBroadcast(intent0);// 发送广播了
						finish();
						UiHelper.ShowOneToast(OriginateChairActivity.this, "编辑成功！");
					}

				}
				break;

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 服务器失败结果
	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(OriginateChairActivity.this, mess);

	}

	/**
	 * 分享的相关操作
	 */
	public void friend(View v) {
		share(0);
	}

	public void friendline(View v) {
		share(1);
	}

	private void share(int flag) {
		downloadWeiXinImg(flag);

	}

	// 微信分享需要 先去下载封面的图片，然后才会分享出去
	private void downloadWeiXinImg(final int flag) {
		// TODO Auto-generated method stub
		if (editTddActivity.getShareContent() != null && editTddActivity.getShareImg() != null && editTddActivity.getShareUrl() != null) {

			ImageLoader.getInstance().loadImage(editTddActivity.getShareImg(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
					// 下载失败
					WXWebpageObject webpage = new WXWebpageObject();
					webpage.webpageUrl = editTddActivity.getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = editTddActivity.getActivityTitle();
					msg.description = editTddActivity.getActivityDescription();
					// 根据ImgUrl下载下来一张图片，弄出bitmap格式
					// 这里替换一张自己工程里的图片资源
					Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
					msg.setThumbImage(thumb);
					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("webpage");
					req.message = msg;
					req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
					boolean fla = wxApi.sendReq(req);
					System.out.println("fla=" + fla);
				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
					// TODO Auto-generated method stub
					// 表示下载成功了
					WXWebpageObject webpage = new WXWebpageObject();
					webpage.webpageUrl = editTddActivity.getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = editTddActivity.getActivityTitle();
					msg.description = editTddActivity.getActivityDescription();
					// 根据ImgUrl下载下来一张图片，弄出bitmap格式
					// 这里替换一张自己工程里的图片资源
					Bitmap thumb = bitmap;
					msg.setThumbImage(thumb);
					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("webpage");
					req.message = msg;
					req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
					boolean fla = wxApi.sendReq(req);
					System.out.println("fla=" + fla);
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}
			});
		} else {
			UiHelper.ShowOneToast(OriginateChairActivity.this, "第三方分享的内容不能为空！！！");
			finish();
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub
		finish();
	}

	private void onClickShareToQQ() {
		Bundle b = getShareBundle();
		if (b != null) {
			shareParams = b;
			Thread thread = new Thread(shareThread);
			thread.start();
		}
	}

	private Bundle getShareBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("title", editTddActivity.getActivityTitle());
		bundle.putString("imageUrl", editTddActivity.getShareImg());
		bundle.putString("targetUrl", editTddActivity.getShareUrl());
		bundle.putString("summary", editTddActivity.getActivityDescription());
		bundle.putString("site", "1104957952");
		bundle.putString("appName", "我是TDD");
		return bundle;
	}

	Bundle shareParams = null;

	Handler shareHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	// 线程类，该类使用匿名内部类的方式进行声明
	Runnable shareThread = new Runnable() {

		public void run() {
			doShareToQQ(shareParams);
			Message msg = shareHandler.obtainMessage();

			// 将Message对象加入到消息队列当中
			shareHandler.sendMessage(msg);

		}
	};

	private void doShareToQQ(Bundle params) {
		mTencent.shareToQQ(OriginateChairActivity.this, params, new BaseUiListener() {
			protected void doComplete(JSONObject values) {
				showResult("shareToQQ:", "onComplete");
			}

			@Override
			public void onError(UiError e) {
				showResult("shareToQQ:", "分享失败");
			}

			@Override
			public void onCancel() {
				showResult("shareToQQ", "onCancel");
			}
		});
	}

	private class BaseUiListener implements IUiListener {

		// @Override
		// public void onComplete(JSONObject response) {
		// // mBaseMessageText.setText("onComplete:");
		// // mMessageText.setText(response.toString());
		// doComplete(response);
		// }

		protected void doComplete(Object values) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			doComplete(arg0);
		}
	}

	private Handler mHandler;

	// qq分享的结果处理
	private void showResult(final String base, final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				UiHelper.ShowOneToast(OriginateChairActivity.this, msg);
				popupWindow.dismiss();
				finish();
			}
		});
	}

	/*
	 * 上传图片
	 */
	public void upload() {

		// 构造方法1、2的参数
		filePathList = new ArrayList<String>();
		// 就只是将本地存在的传上传
		for (int i = 0; i < arrayList.size(); i++) {
			if (!"".equals(arrayList.get(i).getImageId())) {
				File file = new File(arrayList.get(i).getImagePath());
				if (file.exists()) {
					filePathList.add(arrayList.get(i).getImagePath());// 将本地存在的抽出来
																		// 这部分不用上传的
				} else {
					file.delete();
				}

			}
		}

		post = new HttpMultipartPostOriginate(OriginateChairActivity.this, filePathList);
		post.execute();
	}

	// 根据Uri获取到图片的路径
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	// 上传头像完之后的操作
	@SuppressWarnings("unchecked")
	public void handleHeadImg(String imgMess) {
		if (imgMess != null) {
			WBResponse response = MessageUtil.JsonStrToWBResponse(imgMess, HeadImgRes.class);
			String msgString = response.getMsg();
			imgUrl = ((HeadImgRes) response.getRespBody()).getFileUrls();
			if (arrayList.size() > 0 && imgUrl != null) {
				List<PhotoUpImageItem> tempImageItems = new ArrayList<PhotoUpImageItem>();
				// 首先先抽搐本地存在的
				for (int i = 0; i < arrayList.size() - 1; i++) {
					File file = new File(arrayList.get(i).getImagePath());
					if (file.exists()) {
						tempImageItems.add(arrayList.get(i));

					} else {
						file.delete();

					}

				}
				for (int i = 0; i < arrayList.size() - 1; i++) {
					File file = new File(arrayList.get(i).getImagePath());

					if (file.exists()) {
						arrayList.remove(arrayList.get(i));
						i -= 1;
					} else {
						file.delete();
					}
				}
				arrayList.remove(arrayList.size() - 1);
				for (int i = 0; i < tempImageItems.size(); i++) {
					File file = new File(tempImageItems.get(i).getImagePath());
					if (file.exists()) {
						tempImageItems.get(i).setImagePath(imgUrl.get(i));
						arrayList.add(tempImageItems.get(i));
					} else {
						file.delete();
					}

				}

				tempImageItems.clear();

			}
		}
		refresh();// 刷新

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent0 = new Intent();//切记
			// 这里的Action参数与IntentFilter添加的Action要一样才可以
			// intent0.setAction("RECEIVE_TDDACTIVITY");
			// Bundle bundle=new Bundle();//tddactivity
			// bundle.putSerializable("tddactivity",editTddActivity);
			// intent0.putExtras(bundle);
			// sendBroadcast(intent0);//发送广播了
			finish();
			return true;
		}
		return false;
	}

}