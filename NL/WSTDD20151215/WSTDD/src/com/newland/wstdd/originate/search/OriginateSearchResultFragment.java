/**
 * 
 */
package com.newland.wstdd.originate.search;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.find.categorylist.bean.FindCategoryListViewInfo;
import com.newland.wstdd.find.categorylist.detail.FindChairDetailActivity;
import com.newland.wstdd.find.categorylist.detail.handle.SingleActivityDetailHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.managerpage.ManagerPageActivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.OriginateSearchReq;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.OriginateSearchRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
import com.newland.wstdd.originate.handle.OriginateSearchHandle;

/**发起-搜索结果界面
 * @author H81 2015-11-24
 *
 */
@SuppressLint("ValidFragment")
public class OriginateSearchResultFragment extends BaseFragment implements OnPostListenerInterface{
	private Context context;
	private AppContext appContext;
	
//	private com.newland.wstdd.common.view.CustomListViews findCategoryListViews;
	private ListView findCategoryListViews;
	OriginateSearchResultAdapter originateSearchResultAdapter;
	private List<FindCategoryListViewInfo> findCategoryListViewInfos = new ArrayList<FindCategoryListViewInfo>();

	// 服务器返回的信息
	OriginateSearchRes originateSearchRes;
	OriginateSearchHandle handler = new OriginateSearchHandle(this);
	
	String searchContentString;//搜索框内容
	@SuppressLint("ValidFragment")
	public OriginateSearchResultFragment(Context context, String searchContentString) {
		this.context = context;
		this.searchContentString = searchContentString;
	}

	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					OriginateSearchReq reqInfo = new OriginateSearchReq();
					reqInfo.setCity("全国");
//					reqInfo.setActivityType("");
					reqInfo.setPage("0");
					reqInfo.setProvince("全国");
					reqInfo.setRow("10000");
//					reqInfo.setSearchText(((EditText)getActivity().findViewById(R.id.originate_search_edt)).getText().toString());
					reqInfo.setSearchText(searchContentString);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<OriginateSearchRes> ret = mgr.getOriginateSearchInfo(reqInfo);// 泛型类，
					Message message = new Message();
					message.what = OriginateSearchHandle.ORIGINATE_SEARCH;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						originateSearchRes = (OriginateSearchRes) ret.getObj();
						message.obj = originateSearchRes;
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
	public void OnHandleResultListener(Object obj, int responseId) {
		switch (responseId) {
		case OriginateSearchHandle.ORIGINATE_SEARCH:
			if (dialog != null) {
				dialog.dismiss();
			}
			try {
				originateSearchRes = (OriginateSearchRes) obj;
				if (originateSearchRes != null) {
					/**
					 * 得到从服务器返回的数据，可以进行相应的处理
					 * 
					 */
//					UiHelper.ShowOneToast(getActivity(), "搜索请求成功");
					if (originateSearchRes.getAcList().size() > 0) {
						for (int i = 0; i < originateSearchRes.getAcList().size(); i++) {
							FindCategoryListViewInfo findCategoryListViewInfo = new FindCategoryListViewInfo();
							findCategoryListViewInfo.setActivityType(originateSearchRes.getAcList().get(i).getActivityType());
							findCategoryListViewInfo.setImage1(originateSearchRes.getAcList().get(i).getImage1());
							findCategoryListViewInfo.setActivityTitle(originateSearchRes.getAcList().get(i).getActivityTitle());
							findCategoryListViewInfo.setFriendActivityTime(originateSearchRes.getAcList().get(i).getFriendActivityTime());
							findCategoryListViewInfo.setActivityAddress(originateSearchRes.getAcList().get(i).getActivityAddress());
							findCategoryListViewInfo.setSignCount(originateSearchRes.getAcList().get(i).getSignCount());
							findCategoryListViewInfo.setNeedPay(originateSearchRes.getAcList().get(i).getNeedPay());
							findCategoryListViewInfo.setActivityId(originateSearchRes.getAcList().get(i).getActivityId());
							findCategoryListViewInfos.add(findCategoryListViewInfo);
						}
						originateSearchResultAdapter = new OriginateSearchResultAdapter(context , findCategoryListViewInfos);
						findCategoryListViews.setAdapter(originateSearchResultAdapter);
						originateSearchResultAdapter.notifyDataSetChanged();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case OriginateSearchHandle.SINGLE_ACTIVITY:
			if (dialog != null) {
				dialog.dismiss();
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
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(context, mess);
	}
	@Override
	public void onClick(View v) {
		
	}
	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_originate_searchresult,container, false);
		appContext = AppContext.getAppContext();
		initView(view);
		refresh();
		return view;
	}

	private void initView(View view) {
//		findCategoryListViews = (CustomListViews) view.findViewById(R.id.category_listview);
		findCategoryListViews = (ListView) view.findViewById(R.id.category_listview);
		findCategoryListViews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				// TODO Auto-generated method stub
				//跳转到详细界面去
				for (int i = 0; i < originateSearchRes.getAcList().size(); i++) {
					if(findCategoryListViewInfos.get(position).getActivityId().equals(originateSearchRes.getAcList().get(i).getActivityId())){
						//TODO 
						singleActivitySearch(findCategoryListViewInfos.get(position).getActivityId());
						/*Intent intent = new Intent(getActivity(), FindChairDetailActivity.class);// 跳转到详情
						Bundle bundle = new Bundle();
						bundle.putSerializable("tddActivity", originateSearchRes.getAcList().get(i));
						intent.putExtras(bundle);
						startActivity(intent);*/
					}
					
				}
			}
		});
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
					message.what = OriginateSearchHandle.SINGLE_ACTIVITY;// 设置死
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
}
