package com.newland.wstdd.find.find;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.common.UrlManager;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.selectphoto.AlbumItemActivity;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.common.view.FindGridView;
import com.newland.wstdd.common.view.FindListViews;
import com.newland.wstdd.common.view.LoadingDialog;
import com.newland.wstdd.find.categorylist.detail.FindChairDetailActivity;
import com.newland.wstdd.find.find.bean.FindReq;
import com.newland.wstdd.find.find.bean.FindRes;
import com.newland.wstdd.find.groupongridview.GroupViewFactory;
import com.newland.wstdd.find.groupongridview.GroupViewPager;
import com.newland.wstdd.find.groupongridview.GroupViewPager.GroupCycleListener;
import com.newland.wstdd.find.groupongridview.bean.ADInfo;
import com.newland.wstdd.find.groupongridview.bean.HotGroupListInfo;
import com.newland.wstdd.find.hotgridview.HotViewFactory;
import com.newland.wstdd.find.hotgridview.HotViewPager;
import com.newland.wstdd.find.hotgridview.HotViewPager.HotCycleListener;
import com.newland.wstdd.find.hotlist.bean.HotListInfo;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.managerpage.ManagerPageActivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
import com.newland.wstdd.originate.origateactivity.OriginateChairActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 发现界面
 * 
 * @author Administrator
 * 
 */
public class FindFragment extends BaseFragment implements OnItemClickListener, OnPostListenerInterface, OnClickListener
//,com.newland.wstdd.common.view.RefreshableView.RefreshListener 
{
	private LinearLayout listviewEmptyImageView;//图片信息为空的时候
	private boolean initBoolean = false;// 初始化的boolean 作为fragment可见时候的判断
	boolean isTest = true;
	private View view;
	// gridView 8个效果
	private FindGridView gvHome;
	// 推荐
	private ImageView hotImageView;
	private TextView mFind_hot_title;
	private TextView mFind_hot_content;
	private ImageView mFind_recommend;
	private TextView mFind_recommend_title;
	private TextView mFind_recommend_content;
	private ImageView mFind_recommend1;
	private TextView mFind_recommend1_title;
	private TextView mFind_recommend1_content;
	private LinearLayout find_recommend_ll, find_recommend_ll1, find_recommend_ll2;
	private LinearLayout find_recommend_all_ll;// 推荐布局总的布局

	// 广告1滚动的广告效果 团购
	private List<ImageView> groupImgList = new ArrayList<ImageView>();
	private List<HotGroupListInfo> groupADInfos = new ArrayList<HotGroupListInfo>();// 团购的数据列表
	private GroupViewPager groupViewPager;
	// 第2个广告界面 热门
	private List<ImageView> hotImgList = new ArrayList<ImageView>();
	private List<HotListInfo> hotADInfos = new ArrayList<HotListInfo>();// 热门的数据列表
	private HotViewPager hotViewPager;
	// 第3个界面 ListView界面
	private FindListViews hotListView;
	// private List<FindListViewData> findFragmentListViewDatas = new
	// ArrayList<FindListViewData>();// //gridView上显示的数据
	private FindListAdapter findListAdapter;
	private String[] imageUrls = { "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg", "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg", "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg", "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg" };
	private FindFragmentHandle handler = new FindFragmentHandle(this);

	/** -------服务器返回---------- */
	private FindRes findRes;// 服务器发挥的信息
	// 推荐--三条数据
	String tjImgge1[] = new String[3];
	String tjActivityTitle[] = new String[3];
	String tjActivityDescription[] = new String[3];

	/** -------服务器返回---------- */


	private ScrollView find_scrollview;
	private LinearLayout find_all_ll;
	

	//广播返回
	private String activityIdString;//活动id
	private String statusString;//报名状态
	
	// 请求数据
	@Override
	public void refresh() {
		
		super.refreshDialog();progressDialog.setCancelable(false);
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					FindReq reqInfo = new FindReq();
					reqInfo.setCity("全国");
					reqInfo.setProvince("全国");
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<FindRes> ret = mgr.getFindInfo(reqInfo);// 泛型类，
					Message message = new Message();
					message.what = FindFragmentHandle.FIND;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						findRes = (FindRes) ret.getObj();
						message.obj = findRes;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		System.out.println("ExampleFragment--onCreate");
		
		//用于更新报名状态
		/*IntentFilter intentFilter = new IntentFilter("StatusChange");
		getActivity().registerReceiver(broadcastReceiver, intentFilter);*/
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		if (view != null && isTest) {
			initView(view);// 初始化控件
			// 团购
			groupImageLoader();
			// groupInitialize();
			// 热门
			hotImageLoader();
			// hotInitialize();

			initHotlistData();
			isTest = false;
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_find, container, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return view;
	}

	// 执行完setUserVisibleHint 才会去执行onStart();
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		if (initBoolean) {
			initBoolean = false;
			if(getUserVisibleHint()){
				setUserVisibleHint(true);
			}
			
		
		} else {
			initBoolean = true;
		}
		super.onStart();
		/*updateStatus();*/
	}
	
	/**
	 * 更新报名状态
	 *//*
	private void updateStatus() {
		if (singleActivityRes != null && singleActivityRes.getTddActivity()!=null) {
//			for (int j = 0 , size =hotADInfos.size(); j < size; j++) {
				if (activityIdString!=null&&activityIdString.equals(singleActivityRes.getTddActivity().getActivityId())) {
					if ("关闭报名".equals(statusString)) {
//						hotADInfos.get(j).setStatus(2);//状态  0.未发布 1.报名中 2.关闭报名 4.已撤销
//						break;
					}else if ("开启报名".equals(statusString)) {
//						hotADInfos.get(j).setStatus(1);
//						break;
					}
				}
//			}
			if (findListAdapter!=null) {
				findListAdapter.notifyDataSetChanged();
			}
		}
	}*/

	// 优先于Oncreate()方法执行 但是第一次执行的话为false
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// 首次运行不可见 第二次运行this.isVisible可见
		if (this.isVisible()) {
			if (isVisibleToUser) {
				if (isAdded()) {
//					UiHelper.ShowOneToast(getActivity(), "find");
				groupImgList.clear();
				groupADInfos.clear();
				hotImgList.clear();
				hotADInfos.clear();
				refresh();// 向服务器请求
				initBoolean=true;
				}
				
			}
		}
		super.setUserVisibleHint(isVisibleToUser);

	}

	// 初始化布局
	private void initView(View view) {
		listviewEmptyImageView = (LinearLayout) view.findViewById(R.id.listview_empty_iv);
		// 推荐数据设置
		mFind_hot_title = (TextView) view.findViewById(R.id.find_hot_title);
		mFind_hot_content = (TextView) view.findViewById(R.id.find_hot_content);
		mFind_recommend = (ImageView) view.findViewById(R.id.find_recommend);
		mFind_recommend_title = (TextView) view.findViewById(R.id.find_recommend_title);
		mFind_recommend_content = (TextView) view.findViewById(R.id.find_recommend_content);
		mFind_recommend1 = (ImageView) view.findViewById(R.id.find_recommend1);
		mFind_recommend1_title = (TextView) view.findViewById(R.id.find_recommend1_title);
		mFind_recommend1_content = (TextView) view.findViewById(R.id.find_recommend1_content);
		find_recommend_ll = (LinearLayout) view.findViewById(R.id.find_recommend_ll);
		find_recommend_ll1 = (LinearLayout) view.findViewById(R.id.find_recommend_ll1);
		find_recommend_ll2 = (LinearLayout) view.findViewById(R.id.find_recommend_ll2);
		find_recommend_all_ll = (LinearLayout) view.findViewById(R.id.tj_ll);
		find_recommend_ll.setOnClickListener(this);
		find_recommend_ll1.setOnClickListener(this);
		find_recommend_ll2.setOnClickListener(this);
		// 热门 推荐活动的测试
		hotImageView = (ImageView) view.findViewById(R.id.find_hot);
		gvHome = (FindGridView) view.findViewById(R.id.gv_home);
		gvHome.setSelector(new ColorDrawable(Color.TRANSPARENT));
		SimpleAdapter adapter = new GridViewHomeAdapter(getActivity()).getAdapter();
		gvHome.setAdapter(adapter);
		gvHome.setOnItemClickListener(this);
		
		//下拉刷新
		/*mRefreshableView = (RefreshableView) view.findViewById(R.id.refresh_root);
		mRefreshableView.setRefreshListener(this);*/
		
		
		find_scrollview = (ScrollView) view.findViewById(R.id.find_scrollview);
		find_all_ll = (LinearLayout) view.findViewById(R.id.find_all_ll);
		/*scrollToBottom(find_scrollview,find_all_ll);
		scrollToTop(find_scrollview,find_all_ll);*/
		
		
		find_scrollview.smoothScrollTo(0, 0);
	}

	/**
	 * 设置推荐数据
	 * 
	 */
	private void setHotTjData() {

		if (StringUtil.isNotEmpty(tjImgge1[0])) {
			ImageDownLoad.getDownLoadSmallImg(tjImgge1[0], hotImageView);
		} else {
			hotImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
		}
		mFind_hot_title.setText(StringUtil.noNull(tjActivityTitle[0]));
		mFind_hot_content.setText(StringUtil.noNull(tjActivityDescription[0]));
		if (StringUtil.isNotEmpty(tjImgge1[1])) {
			ImageDownLoad.getDownLoadSmallImg(tjImgge1[1], mFind_recommend);
		} else {
			mFind_recommend.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
		}
		mFind_recommend_title.setText(StringUtil.noNull(tjActivityTitle[1]));
		mFind_recommend_content.setText(StringUtil.noNull(tjActivityDescription[1]));
		if (StringUtil.isNotEmpty(tjImgge1[2])) {
			ImageDownLoad.getDownLoadSmallImg(tjImgge1[2], mFind_recommend1);
		} else {
			mFind_recommend1.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty));
		}
		mFind_recommend1_title.setText(StringUtil.noNull(tjActivityTitle[1]));
		mFind_recommend1_content.setText(StringUtil.noNull(tjActivityDescription[1]));
	}

	/**
	 * 设置热门列表数据
	 * 
	 */
	private void initHotlistData() {
		try {

			if (hotADInfos != null && hotADInfos.size() > 0) {
				findListAdapter = new FindListAdapter(getActivity(), hotADInfos);
				hotListView = (FindListViews) view.findViewById(R.id.hot_listview);
				hotListView.setAdapter(findListAdapter);
				findListAdapter.setHotFragmentListInfo(hotADInfos);
				findListAdapter.notifyDataSetChanged();
				hotListView.invalidate();

				// 跳转
				hotListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						singleActivitySearch(hotADInfos.get(position).getActivityId());

					}
				});
			} else {
				// 如果没有数据
				if (hotADInfos.isEmpty()) {
					listviewEmptyImageView.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 单个活动查询   用来判断   活动报名人数   是否已经报名 等等
	 */
	SingleActivityRes singleActivityRes;
	private void singleActivitySearch(final String activityString) {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					SingleActivityReq reqInfo = new SingleActivityReq();
					reqInfo.setActivityId(activityString);
				
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityRes> ret = mgr.getSingleActivityInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = FindFragmentHandle.SINGLE_ACTIVITY;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						singleActivityRes = (SingleActivityRes) ret.getObj();
						message.obj = singleActivityRes;
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
	
	// 0.高级 1.讲座 2.通知 3.会议 4.运动 5.招聘 6.旅游 7.众筹 8.赛事 9.聚会 10.投票 31.高级快捷活动 32.一句话
	// 33.一张图片 34.一段语音 50.团购 99.微店',全部为""
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		// 讲座
		case 0:
			UiHelper.showFindListViewActivity(getActivity(), "讲座", "1");
			break;
		// 团购
		case 1:
			UiHelper.showFindListViewActivity(getActivity(), "团购", "50");

			break;
		// 招聘
		case 2:
			UiHelper.showFindListViewActivity(getActivity(), "招聘", "5");
			break;
		// 聚会
		case 3:
			UiHelper.showFindListViewActivity(getActivity(), "聚会", "9");
			break;
		// 旅行
		case 4:
			UiHelper.showFindListViewActivity(getActivity(), "旅行", "6");

			break;
		// 众筹
		case 5:
			UiHelper.showFindListViewActivity(getActivity(), "众筹", "7");

			break;
		// 投票
		case 6:
			UiHelper.showFindListViewActivity(getActivity(), "投票", "10");
			break;
		// 全部
		case 7:
			UiHelper.showFindListViewActivity(getActivity(), "全部", "");

			break;

		}
	}

	/**
	 * 热门gridview
	 */
	// 热门图片下载
	private void hotImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	// 热门初始化
	@SuppressLint("NewApi")
	private void hotInitialize() {
		hotViewPager = (HotViewPager) getActivity().getFragmentManager().findFragmentById(R.id.find_hot_fragment);

		if (hotADInfos != null && hotADInfos.size() > 0) {

			// 将最后一个ImageView添加进来
			// hotImgList.add(HotViewFactory.getImageView(getActivity(),
			// hotADInfos.get(hotADInfos.size() - 1).getImgUrl()));
			for (int i = 0; i < hotADInfos.size(); i++) {
				hotImgList.add(HotViewFactory.getImageView(getActivity(), UrlManager.uploadToUrlServer + hotADInfos.get(i).getImgUrl()));
			}
			// 将第一个ImageView添加进来
			// hotImgList.add(HotViewFactory.getImageView(getActivity(),
			// hotADInfos.get(0).getImgUrl()));

		}

		/**
		 * 第一个广告
		 */
		// 设置循环，在调用setData方法前调用
		hotViewPager.setCycle(false);

		// 在加载数据前设置是否循环
		hotViewPager.setData(hotImgList, hotADInfos, hotCycleListener);
		// 设置轮播
		hotViewPager.setWheel(false);

		// 设置轮播时间，默认5000ms
		hotViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		hotViewPager.setIndicatorCenter();

	}

	// 热门滑动监听事件
	private HotCycleListener hotCycleListener = new HotCycleListener() {

		@Override
		public void onImageClick(HotListInfo info, int position, View imageView) {

			if (hotViewPager.isCycle()) {
				position = position - 1;
			}

		}
	};

	/**
	 * 团购的gridview
	 * 
	 */
	// 团购的监听事件
	private GroupCycleListener groupCycleListener = new GroupCycleListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {

			if (groupViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(getActivity(), "position-->" + info.getContent(), Toast.LENGTH_SHORT).show();
			}

		}

	};

	// 团购图片的下载
	private void groupImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	// 团购数据的初始化
	@SuppressLint("NewApi")
	private void groupInitialize() {

		groupViewPager = (GroupViewPager) getActivity().getFragmentManager().findFragmentById(R.id.find_group_fragment);

		// for (int i = 0; i < imageUrls.length; i++) {
		// HotGroupListInfo info = new HotGroupListInfo();
		// info.setImgUrl(imageUrls[i]);
		// info.setImgUrl("图片-->" + i);
		// groupADInfos.add(info);

		// 将最后一个ImageView添加进来
		// groupImgList.add(GroupViewFactory.getImageView(getActivity(),
		// groupADInfos.get(groupADInfos.size() - 1).getImgUrl()));
		for (int i = 0; i < groupADInfos.size(); i++) {
			groupImgList.add(GroupViewFactory.getImageView(getActivity(), groupADInfos.get(i).getImgUrl()));
		}
		// 将第一个ImageView添加进来
		// groupImgList.add(GroupViewFactory.getImageView(getActivity(),
		// groupADInfos.get(0).getImgUrl()));

		// 设置循环，在调用setData方法前调用
		groupViewPager.setCycle(false);

		// 在加载数据前设置是否循环
		groupViewPager.setData(groupImgList, groupADInfos, groupCycleListener);
		// 设置轮播
		groupViewPager.setWheel(false);

		// 设置轮播时间，默认5000ms
		groupViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		groupViewPager.setIndicatorCenter();
	}

	// 服务器返回的内容
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		switch (responseId) {
		// 从服务器获取到了所有相关的信息
		case FindFragmentHandle.FIND:
			find_scrollview.smoothScrollTo(0, 0);
			if (progressDialog != null) {
				progressDialog.setContinueDialog(false);
			}
			
			
			// try {
			findRes = (FindRes) obj;
			if (findRes != null) {
				// 进行界面的显示更新
//				UiHelper.ShowOneToast(getActivity(), "发现界面请求成功");
				// 热门推荐
				if (findRes.getTjList().size() >= 3) {// 暂时处理：至少三条数据才显示
					tjImgge1[0] = findRes.getTjList().get(0).getImage1();
					tjImgge1[1] = findRes.getTjList().get(1).getImage1();
					tjImgge1[2] = findRes.getTjList().get(2).getImage1();
					tjActivityTitle[0] = findRes.getTjList().get(0).getActivityTitle();
					tjActivityTitle[1] = findRes.getTjList().get(1).getActivityTitle();
					tjActivityTitle[2] = findRes.getTjList().get(2).getActivityTitle();
					tjActivityDescription[0] = findRes.getTjList().get(0).getActivityDescription();
					tjActivityDescription[1] = findRes.getTjList().get(1).getActivityDescription();
					tjActivityDescription[2] = findRes.getTjList().get(2).getActivityDescription();
					if(getActivity()==null){
						return;
					}
					setHotTjData();
				} else {
//					find_recommend_all_ll.setVisibility(View.GONE);
				}

				// 团购
				if (findRes.getHotGroupList() != null && !findRes.getHotGroupList().isEmpty()) {
					for (int j = 0; j < findRes.getHotGroupList().size(); j++) {
						HotGroupListInfo groupListInfo = new HotGroupListInfo();
						groupListInfo.setOffTime(findRes.getHotGroupList().get(j).getOffTime());
						groupListInfo.setImgUrl(findRes.getHotGroupList().get(j).getImage1());
						groupListInfo.setActivityTitle(findRes.getHotGroupList().get(j).getActivityTitle());
						groupListInfo.setFeeAdult(findRes.getHotGroupList().get(j).getFeeAdult());
						groupListInfo.setFeeKid(findRes.getHotGroupList().get(j).getFeeKid());
						groupADInfos.add(groupListInfo);

					}

				}
				
				if(!findRes.getHotList().isEmpty()) {
					listviewEmptyImageView.setVisibility(View.GONE);
				}else{
					listviewEmptyImageView.setVisibility(View.VISIBLE);
				}
				// 热门列表
				if (findRes.getHotList().size() > 0) {
					for (int i = 0; i < findRes.getHotList().size(); i++) {
						HotListInfo hotListInfo = new HotListInfo();
						hotListInfo.setActivityType(findRes.getHotList().get(i).getActivityType());
						hotListInfo.setImgUrl(findRes.getHotList().get(i).getImage1());
						hotListInfo.setActivityTitle(findRes.getHotList().get(i).getActivityTitle());
						hotListInfo.setFriendActivityTime(findRes.getHotList().get(i).getFriendActivityTime());
						hotListInfo.setActivityAddress(findRes.getHotList().get(i).getActivityAddress());
						hotListInfo.setSignCount(findRes.getHotList().get(i).getSignCount());
						hotListInfo.setNeedPay(findRes.getHotList().get(i).getNeedPay());
						hotListInfo.setActivityId(findRes.getHotList().get(i).getActivityId());
						hotListInfo.setStatus(findRes.getHotList().get(i).getStatus());
						hotADInfos.add(hotListInfo);
					}
					initHotlistData();
				}
			}
			// 团购
			groupImageLoader();
			groupInitialize();
			// 热门
			hotImageLoader();
			hotInitialize();
			// } catch (Exception e) {
			// // TODO: handle exception
			// }

			break;
		case FindFragmentHandle.SINGLE_ACTIVITY:
			if (progressDialog != null) {
				progressDialog.setContinueDialog(false);
			}
			singleActivityRes=(SingleActivityRes) obj;
			if(singleActivityRes!=null){
				if (singleActivityRes.getIsLeader().equals("true")) {//团大
					Intent intent = new Intent(getActivity(), ManagerPageActivity.class);// 跳转到管理
					Bundle bundle = new Bundle();
					singleActivityRes.getTddActivity().setSignRole(1);
					bundle.putSerializable("singleActivityRes", singleActivityRes);
					intent.putExtras(bundle);
					startActivity(intent);
				}else if (singleActivityRes.getSignRole() == 2) {//团秘
					Intent intent = new Intent(getActivity(), ManagerPageActivity.class);// 跳转到管理
					Bundle bundle = new Bundle();
					singleActivityRes.getTddActivity().setSignRole(2);
					bundle.putSerializable("singleActivityRes", singleActivityRes);
					intent.putExtras(bundle);
					startActivity(intent);
				}else if (singleActivityRes.getSignRole() == 9) {//团员
					Intent intent = new Intent(getActivity(), FindChairDetailActivity.class);// 跳转到详情
					Bundle bundle = new Bundle();
					singleActivityRes.getTddActivity().setSignRole(9);
					bundle.putSerializable("singleActivityRes", singleActivityRes);
					intent.putExtras(bundle);
					startActivity(intent);
				}else if (singleActivityRes.getSignRole() == 0) {//没参加
					Intent intent = new Intent(getActivity(), FindChairDetailActivity.class);// 跳转到详情
					Bundle bundle = new Bundle();
					singleActivityRes.getTddActivity().setSignRole(0);
					bundle.putSerializable("singleActivityRes", singleActivityRes);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			
				
			/*	for (int i = 0; i < findRes.getHotList().size(); i++) {
					if (hotADInfos.get(position).getActivityId().equals(findRes.getHotList().get(i).getActivityId())) {
						Intent intent = new Intent(getActivity(), FindChairDetailActivity.class);// 跳转到详情
						Bundle bundle = new Bundle();
						bundle.putSerializable("tddActivity", findRes.getHotList().get(i));
						intent.putExtras(bundle);
						startActivity(intent);

					}
				}*/
			}
			break;
		default:
			break;
		}

	}

	// 返回失败
	@Override
	public void OnFailResultListener(String mess) {
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
			UiHelper.ShowOneToast(getActivity(), mess);
		}
		if(hotADInfos.isEmpty()) {
			listviewEmptyImageView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		// 热门推荐
		case R.id.find_recommend_ll:
			if (findRes.getTjList().size()>0) {
				
				if (findRes.getTjList().get(0) != null) {
					/*intent = new Intent(getActivity(), FindChairDetailActivity.class);
				bundle.putSerializable("tddActivity", findRes.getTjList().get(0));
				intent.putExtras(bundle);
				startActivity(intent);*/
					singleActivitySearch(findRes.getTjList().get(0).getActivityId());
				} else {
					find_recommend_ll.setVisibility(View.GONE);
				}
			}

			break;
		case R.id.find_recommend_ll1:
			if (findRes.getTjList().size()>1) {
			if (findRes.getTjList().get(1) != null) {
				/*intent = new Intent(getActivity(), FindChairDetailActivity.class);
				bundle.putSerializable("tddActivity", findRes.getTjList().get(1));
				intent.putExtras(bundle);
				startActivity(intent);*/
				singleActivitySearch(findRes.getTjList().get(1).getActivityId());
			} else {
				find_recommend_ll1.setVisibility(View.GONE);
			}
			}
			break;
		case R.id.find_recommend_ll2:
			if (findRes.getTjList().size()>2) {
			if (findRes.getTjList().get(2) != null) {
				/*intent = new Intent(getActivity(), FindChairDetailActivity.class);
				bundle.putSerializable("tddActivity", findRes.getTjList().get(2));
				intent.putExtras(bundle);
				startActivity(intent);*/
				singleActivitySearch(findRes.getTjList().get(2).getActivityId());
			} else {
				find_recommend_ll2.setVisibility(View.GONE);
			}
			}
			break;
		default:
			break;
		}

	}

	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		//用于更新报名状态
		/*getActivity().unregisterReceiver(broadcastReceiver);*/
	}

	
	/**用于更新报名状态
	 * 
	 */
	/*BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			activityIdString = intent.getStringExtra("activityId");
			statusString = intent.getStringExtra("status");
		}
	};*/
	
	/*private RefreshableView mRefreshableView;
	Handler mHandler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			mRefreshableView.finishRefresh();
			Toast.makeText(getActivity(), R.string.toast_text, Toast.LENGTH_SHORT).show();
		};
	};
	// 实现刷新RefreshListener 中方法
	@Override
	public void onRefresh(RefreshableView view) {
		// 伪处理
		mHandler.sendEmptyMessageDelayed(1, 2000);
	}*/
	
	
	
	/*public static void scrollToBottom(final View scroll, final View inner) {
		Handler mHandler = new Handler();

		mHandler.post(new Runnable() {
			public void run() {
				if (scroll == null || inner == null) {
					return;
				}

				int offset = inner.getMeasuredHeight() - scroll.getHeight();
				if (offset < 0) {
					offset = 0;
				}

				scroll.scrollTo(0, offset);// 将滚动条放到顶部
			}
		});
	}
	public static void scrollToTop(final View scroll, final View inner) {
		Handler mHandler = new Handler();

		mHandler.post(new Runnable() {
			public void run() {
				if (scroll == null || inner == null) {
					return;
				}

				int offset = inner.getMeasuredHeight() - scroll.getHeight();
				if (offset < 0) {
					offset = 0;
				}

				scroll.scrollTo(0, -offset);// 将滚动条放到顶部
			}
		});
	}*/
	
}
