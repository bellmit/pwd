/**
 * 
 */
package com.newland.wstdd.mine.managerpage;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.view.CustomListViews.IXListViewListener;
import com.newland.wstdd.find.categorylist.ShowFindListViewActivity;
import com.newland.wstdd.find.categorylist.detail.FindChairDetailActivity;
import com.newland.wstdd.find.categorylist.detail.handle.SingleActivityDetailHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.managerpage.beanrequest.MyActivityListReq;
import com.newland.wstdd.mine.managerpage.beanresponse.MyActivityListRes;
import com.newland.wstdd.mine.managerpage.handle.MyActivityListHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;

/**我的活动列表
 * @author H81 2015-11-12
 *
 */
public class MyActivitiesListAcitivity extends BaseFragmentActivity implements OnItemClickListener,IXListViewListener,OnPostListenerInterface{
	AppContext appContext = AppContext.getAppContext();
	
	MyActivitiesListAdapter myActivitiesListAdapter;//列表的适配器
	com.newland.wstdd.common.view.CustomListViews listView;//列表listview
	List<MyActivityListInfo> activityList = new ArrayList<MyActivityListInfo>();;//列表的数据
	Intent getIntent;//意图
	String activitybtn;//活动类型   是发起，参与，收藏
	//服务器返回的相关信息
	private MyActivityListRes myActivityListRes;
	MyActivityListHandle handler=new MyActivityListHandle(this);
	
	private List<TddActivity> acList = new ArrayList<TddActivity>();// 返回的列表对象
	
	private TddActivity tddActivity;//活动对象
	private int page_index=0;//当前页码
	@Override
	protected void processMessage(Message msg) {
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myactivity_list);
		getIntent=getIntent();
		activitybtn=getIntent.getStringExtra("activitybtn");
		bindView();
		setTitle();
		refresh();
	}

	private void bindView(){


		listView = (com.newland.wstdd.common.view.CustomListViews) findViewById(R.id.fragment_myactivities_originate_list);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		myActivitiesListAdapter = new MyActivitiesListAdapter(this, activityList);
		listView.setAdapter(myActivitiesListAdapter);
		listView.setOnItemClickListener(this);
	}
	
	/**
	 * 设置标题栏
	 */
	private TextView center_tv;
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null){
			if (activitybtn.equals("originate")) {
				center_tv.setText("我发起的");
			}else if (activitybtn.equals("participation")) {
				center_tv.setText("我参与的");
			}else if (activitybtn.equals("collect")) {
				center_tv.setText("我收藏的");
			}
		}
		if (left_btn != null) {// 返回
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					MyActivitiesListAcitivity.this.finish();
				}
			});
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
//			right_btn.setImageDrawable(getResources().getDrawable(R.drawable.test_myactivities_more));
//			// 更多
//			right_btn.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					UiHelper.ShowOneToast(MyActivitiesListAcitivity.this, "更多");
//				}
//			});
		}
		if (right_tv != null) {// 日期
			right_tv.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (arg0.getId() == R.id.fragment_myactivities_originate_list) {
			if (myActivityListRes != null && myActivityListRes.getAcList().size() > 0) {
				tddActivity = acList.get(position-1);
				if (tddActivity.getUserId().equals(appContext.getUserId())) {//团长（发起者）跳转到管理界面，否则跳到具体活动界面
					
					singleActivitySearch(tddActivity.getActivityId());
					//TODO
					/*Intent intent = new Intent(this, ManagerPageActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("tddActivity", tddActivity);
					intent.putExtras(bundle);
					startActivity(intent);*/
				}else {//参与者，跳转到具体活动界面
					//TODO 传递arg2活动对象
					/*Intent intent = new Intent(MyActivitiesListAcitivity.this, FindChairDetailActivity.class);// 跳转到详情
					Bundle bundle = new Bundle();
					bundle.putSerializable("tddActivity", tddActivity);
					intent.putExtras(bundle);
					startActivity(intent);*/
					
					singleActivitySearch(tddActivity.getActivityId());
					
				}
			}
		}
	}
	/**
	 * 单个活动查询   用来判断   活动报名人数   是否已经报名 等等
	 */
	SingleActivityRes singleActivityRes;
	private void singleActivitySearch(final String activityString) {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					SingleActivityReq reqInfo = new SingleActivityReq();
					reqInfo.setActivityId(activityString);
				
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityRes> ret = mgr.getSingleActivityInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = MyActivityListHandle.SINGLE_ACTIVITY;// 设置死
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
	@Override
	public void onListViewRefresh() {
		
		page_index = 0;
		refresh();// 网络刷新
		onLoad();
		
	}
	@Override
	public void onListViewLoadMore() {
		page_index++;
		refresh();
		onLoad();
	}
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
//		String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new Date());
//		listView.setRefreshTime(date);
	}
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					MyActivityListReq reqInfo = new MyActivityListReq();
					reqInfo.setPage(page_index+"");
					reqInfo.setRow(appContext.pageRow);
					reqInfo.setFilterVaule("all");
					if (activitybtn.equals("originate")) {
						reqInfo.setType("1");			
						
					}else if (activitybtn.equals("participation")) {

						reqInfo.setType("2");
						
					}else if (activitybtn.equals("collect")) {
						reqInfo.setType("3");

						
					}
					
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<MyActivityListRes> ret = mgr.getMyActivityListInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = MyActivityListHandle.MYACTIVITY_LIST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						if (page_index == 0) {
							acList.clear();
							myActivityListRes = (MyActivityListRes) ret.getObj();
							acList = myActivityListRes.getAcList();
						} else {
							myActivityListRes = (MyActivityListRes) ret.getObj();
							if (myActivityListRes != null && myActivityListRes.getAcList().size() > 0) {
								for (int i = 0,size=myActivityListRes.getAcList().size(); i < size; i++) {
									TddActivity tddActivity = myActivityListRes.getAcList().get(i);
									acList.add(tddActivity);
								}
							}
						}
						message.obj = myActivityListRes;
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
	//服务器返回复制黏贴
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			switch (responseId) {
			case MyActivityListHandle.MYACTIVITY_LIST:
				if (dialog != null) {
					dialog.dismiss();
				}
				myActivityListRes = (MyActivityListRes) obj;
				List<MyActivityListInfo> list = new ArrayList<MyActivityListInfo>();;//列表的数据
				if(myActivityListRes!=null){
					for (int i = 0; i < myActivityListRes.getAcList().size(); i++) {
						MyActivityListInfo findCategoryListViewInfo = new MyActivityListInfo();
						findCategoryListViewInfo.setActivityType(myActivityListRes.getAcList().get(i).getActivityType());
						findCategoryListViewInfo.setImage1(myActivityListRes.getAcList().get(i).getImage1());
						findCategoryListViewInfo.setActivityTitle(myActivityListRes.getAcList().get(i).getActivityTitle());
						findCategoryListViewInfo.setFriendActivityTime(myActivityListRes.getAcList().get(i).getFriendActivityTime());
						findCategoryListViewInfo.setActivityAddress(myActivityListRes.getAcList().get(i).getActivityAddress());
						findCategoryListViewInfo.setSignCount(myActivityListRes.getAcList().get(i).getSignCount());
						findCategoryListViewInfo.setNeedPay(myActivityListRes.getAcList().get(i).getNeedPay());
						list.add(findCategoryListViewInfo);
						
					}
					
					if (page_index == 0) {
						activityList.clear();
						activityList.addAll(list);
						myActivitiesListAdapter.notifyDataSetChanged();
					}else {
						activityList.addAll(list);
						myActivitiesListAdapter.notifyDataSetChanged();
					}
					if (list.size() < Integer.valueOf(AppContext.getAppContext().pageRow)) {// 数据没必要分页时，隐藏“加载更多”按钮
						listView.setPullLoadEnable(false);
					} else {
						listView.setPullLoadEnable(true);
					}
				}
				
				
				break;

			case MyActivityListHandle.SINGLE_ACTIVITY:
				if (dialog != null) {
					dialog.dismiss();
				}
				singleActivityRes=(SingleActivityRes) obj;
				if(singleActivityRes!=null){
					if (singleActivityRes.getIsLeader().equals("true")) {//团大
						Intent intent = new Intent(this, ManagerPageActivity.class);// 跳转到管理
						Bundle bundle = new Bundle();
						singleActivityRes.getTddActivity().setSignRole(1);
						bundle.putSerializable("singleActivityRes", singleActivityRes);
						intent.putExtras(bundle);
						startActivity(intent);
					}else if (singleActivityRes.getSignRole() == 2) {//团秘
						Intent intent = new Intent(this, ManagerPageActivity.class);// 跳转到管理
						Bundle bundle = new Bundle();
						singleActivityRes.getTddActivity().setSignRole(2);
						bundle.putSerializable("singleActivityRes", singleActivityRes);
						intent.putExtras(bundle);
						startActivity(intent);
					}else if (singleActivityRes.getSignRole() == 9) {//团员
						Intent intent = new Intent(this, FindChairDetailActivity.class);// 跳转到详情
						Bundle bundle = new Bundle();
						singleActivityRes.getTddActivity().setSignRole(9);
						bundle.putSerializable("singleActivityRes", singleActivityRes);
						intent.putExtras(bundle);
						startActivity(intent);
					}else if (singleActivityRes.getSignRole() == 0) {//没参加
						Intent intent = new Intent(this, FindChairDetailActivity.class);// 跳转到详情
						Bundle bundle = new Bundle();
						singleActivityRes.getTddActivity().setSignRole(0);
						bundle.putSerializable("singleActivityRes", singleActivityRes);
						intent.putExtras(bundle);
						startActivity(intent);
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
	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(this, mess);
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if (center_tv != null) {
			String centerString = center_tv.getText().toString();
			if (centerString.equals("我发起的")) {
				activitybtn = "originate" ;
			}else if (centerString.equals("我参与的")) {
				activitybtn = "participation" ;
			}else if (centerString.equals("我收藏的")) {
				activitybtn = "collect" ;
			}
			if (StringUtil.isNotEmpty(activitybtn)) {
				refresh();
			}
		}
	}
}
