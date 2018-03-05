package com.newland.wstdd.mine.managerpage.ilike;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.bean.TddLikeVo;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.handle.RegistFragmentHandle;
import com.newland.wstdd.mine.managerpage.ilike.beanrequest.LikeListReq;
import com.newland.wstdd.mine.managerpage.ilike.beanresponse.LikeListRes;
import com.newland.wstdd.mine.managerpage.ilike.likelisthandle.LikeListHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
/***
 * 点赞的列表
 * @author Administrator
 *
 */
public class LikeListActivity extends BaseFragmentActivity implements OnPostListenerInterface {
	private static final String TAG = "LikeListActivity";//收集异常日志tag
	private Intent intent;
	private TddActivity tddActivity;
	private LikeListViews likeListViews;//点赞列表的listview
	private LikeListAdapter likeListAdapter;//点赞列表的适配器
	private List<TddLikeVo> tddLikeVos = new ArrayList<TddLikeVo>();//列表的数据
	//服务器返回相关信息
	private LikeListRes likeListRes;
	private LikeListHandle handler=new LikeListHandle(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		AppManager.getAppManager().addActivity(this);//添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_like_list);
		
		 /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		initTitle();//初始化  标题栏
		initView();//初始化控件
		likeListAdapter = new LikeListAdapter(this, tddLikeVos);
		likeListViews.setAdapter(likeListAdapter);
		intent=getIntent();
		tddActivity = (TddActivity) intent.getExtras().getSerializable("tddactivity");
		getDataFromServer();//从服务器获取数据
		
		
	}

	@Override
	protected void onDestroy() {
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** 收集异常日志 */
		super.onDestroy();
	}	

	//初始化标题栏
	private void initTitle() {
		// TODO Auto-generated method stub
		TextView head_center_title;
		TextView head_right_tv;
		ImageView head_left_iv;
		head_center_title = (TextView) findViewById(R.id.head_center_title);
		head_center_title.setText("点赞列表");
		head_right_tv = (TextView) findViewById(R.id.head_right_btns);
		head_right_tv.setVisibility(View.GONE);
		head_left_iv = (ImageView) findViewById(R.id.head_left_iv);
		head_left_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}

	@Override
	protected void processMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		likeListViews = (LikeListViews) findViewById(R.id.likelist_listview);
	}

	//从服务器获取数据
	private void getDataFromServer() {
		// TODO Auto-generated method stub
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					LikeListReq reqInfo = new LikeListReq();
					reqInfo.setActivityId(tddActivity.getActivityId());
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<LikeListRes> ret = mgr.getLikeListInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = RegistFragmentHandle.REGIST_FIRST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						likeListRes = (LikeListRes) ret.getObj();
						message.obj = likeListRes;
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
		// TODO Auto-generated method stub
		try {
			switch (responseId) {
			case LikeListHandle.LIKE_LIST:
				likeListRes = (LikeListRes) obj;
				if(likeListRes!=null){
					tddLikeVos = likeListRes.getTddLikeVos();
					likeListAdapter.setRegistrationData(tddLikeVos);
					likeListAdapter.notifyDataSetChanged();
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
	public void OnFailResultListener(String mess) {
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
			UiHelper.ShowOneToast(this, mess);
		}
	}

	

}
