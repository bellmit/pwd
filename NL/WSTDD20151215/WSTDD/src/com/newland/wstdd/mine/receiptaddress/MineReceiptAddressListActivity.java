/**
 * 
 */
package com.newland.wstdd.mine.receiptaddress;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.receiptaddress.bean.MineReceiptAddressInfo;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineAddAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineDefaultAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineDeleteAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.TddDeliverAddr;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDefaultAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDeleteAddressRes;
import com.newland.wstdd.mine.receiptaddress.handle.MineReceiptAddressHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

/**
 * 我的-设置收货地址列表
 * 
 * @author H81 2015-11-8
 * 
 */
public class MineReceiptAddressListActivity extends BaseFragmentActivity implements OnPostListenerInterface,
		OnClickListener {
	ListView mMine_address_listview;
	MineReceiptAddressListAdapter mineReceiptAddressAdapter;
	List<MineReceiptAddressInfo> mineReceiptAddressInfos;
	// 服务器返回的信息
	MineAddAddressRes mineAddAddressRes;// 地址列表
	List<TddDeliverAddr> tddDeliverAddrs;// 地址对象
	MineDefaultAddressRes mineDefaultAddressRes;// 默认地址
	MineDeleteAddressRes mineDeleteAddressRes;// 删除地址
	MineReceiptAddressHandle handler = new MineReceiptAddressHandle(this);
	private String oldId;// 默认地址
	private int deletPosition;// 删除项

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_receiptaddress);
		// bindView();
		setTitle();
		refresh();// 获取总的列表
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		refresh();// 获取总的列表
		if (mineReceiptAddressAdapter != null) {
			mineReceiptAddressAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 设置标题
	 */
	@SuppressLint("ResourceAsColor")
	private void setTitle() {
		ImageView leftIv = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		leftIv.setVisibility(View.VISIBLE);
		centerTitle.setText("设置收货地址");
		rightTv.setText("新增");
		rightTv.setTextColor(getResources().getColor(R.color.red));
		rightTv.setVisibility(View.VISIBLE);
		leftIv.setOnClickListener(this);
		rightTv.setOnClickListener(this);
	}

	/**设置地址列表数据
	 * @param list
	 */
	private void bindView(List<TddDeliverAddr> list) {
		mMine_address_listview = (ListView) findViewById(R.id.mine_address_listview);
		mineReceiptAddressAdapter = new MineReceiptAddressListAdapter(this, list);
		mMine_address_listview.setAdapter(mineReceiptAddressAdapter);
	}

	/**
	 * 编辑收货地址
	 * 
	 */
	public void editAddress(String addressIdString) {
		for (int i = 0; i < tddDeliverAddrs.size(); i++) {
			if (tddDeliverAddrs.get(i).getAddressId().equals(addressIdString)) {
				Intent intent = new Intent(this, MineEditReceiptAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("tddDeliverAddr", tddDeliverAddrs.get(i));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}

	// 请求获取地址列表 (13. 收货地址新增/列表)
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineAddAddressReq addAddressReq = new MineAddAddressReq();
					RetMsg<MineAddAddressRes> ret = mgr.getMineReceiptAddressInfo(addAddressReq);// 泛型类，
					Message message = new Message();
					message.what = MineReceiptAddressHandle.GET_ADDRESS_LIST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						mineAddAddressRes = (MineAddAddressRes) ret.getObj();
						message.obj = mineAddAddressRes;
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

	// 上传默认的地址列表
	public void setDefaultAddress(final String oldId,final String newId) {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineDefaultAddressReq defaultAddressReq = new MineDefaultAddressReq();
					defaultAddressReq.setOldId(oldId);
					defaultAddressReq.setNewId(newId);

					RetMsg<MineDefaultAddressRes> ret = mgr.getMineDefaultAddressInfo(defaultAddressReq);// 泛型类，
					Message message = new Message();
					message.what = MineReceiptAddressHandle.SET_ADDRESS_DEFAULT;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						mineDefaultAddressRes = (MineDefaultAddressRes) ret.getObj();
						message.obj = mineDefaultAddressRes;
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

	/**
	 * 删除地址的操作
	 */
	public void setDeleteAddress(final String addressId) {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					for (int i = 0, size = tddDeliverAddrs.size(); i < size; i++) {
						if (tddDeliverAddrs.get(i).getAddressId().equals(addressId)) {
							deletPosition = i;
						}
					}
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineDeleteAddressReq mineDeleteAddressReq = new MineDeleteAddressReq();
					mineDeleteAddressReq.setAddrId(addressId);// 选中删除的那个地址的id
																// 从删除监听事件中获取
					RetMsg<MineDeleteAddressRes> ret = mgr.getMineDeleteAddressInfo(mineDeleteAddressReq, addressId);// 泛型类，
					Message message = new Message();
					message.what = MineReceiptAddressHandle.SET_DELETE_ADDRESS;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						mineDeleteAddressRes = (MineDeleteAddressRes) ret.getObj();
						message.obj = mineDeleteAddressRes;
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
		// 取得设置的列表
		case MineReceiptAddressHandle.GET_ADDRESS_LIST:
			if (dialog != null) {
				dialog.dismiss();
			}
			mineAddAddressRes = (MineAddAddressRes) obj;
			if (mineAddAddressRes != null) {
//				UiHelper.ShowOneToast(this, "新增/列表 请求成功");
				if (mineAddAddressRes.getAddresses().size() > 0) {
					UiHelper.ShowOneToast(this, "获取地址成功！！！" + mineAddAddressRes.getAddresses().get(0).getAddress());
					for (int i = 0, size = mineAddAddressRes.getAddresses().size(); i < size; i++) {
						TddDeliverAddr tddDeliverAddr = mineAddAddressRes.getAddresses().get(i);
						if (tddDeliverAddr.getIsDefault().equals("1")) {// 是否默认
																		// 1默认
																		// 2非默认
							oldId = tddDeliverAddr.getAddressId();
						}
						bindView(mineAddAddressRes.getAddresses());
						tddDeliverAddrs = mineAddAddressRes.getAddresses();
					}
				}
			}
			break;
		// 设置默认地址
		case MineReceiptAddressHandle.SET_ADDRESS_DEFAULT:
			if (dialog != null) {
				dialog.dismiss();
			}
			mineDefaultAddressRes = (MineDefaultAddressRes) obj;
			if (mineAddAddressRes != null) {
//				UiHelper.ShowOneToast(this, "默认地址 请求成功");

				if (mineReceiptAddressAdapter != null && mMine_address_listview != null) {
					
					mineReceiptAddressAdapter = new MineReceiptAddressListAdapter(this, mineDefaultAddressRes.getAddresses());
					mMine_address_listview.setAdapter(mineReceiptAddressAdapter);
					mineReceiptAddressAdapter.notifyDataSetChanged();
				}
			}
			break;
		// 设置删除地址更新
		case MineReceiptAddressHandle.SET_DELETE_ADDRESS:
			if (dialog != null) {
				dialog.dismiss();
			}
			mineDeleteAddressRes = (MineDeleteAddressRes) obj;
			if (mineAddAddressRes != null) {
//				UiHelper.ShowOneToast(this, "删除地址 请求成功");

				if (mineReceiptAddressAdapter != null && mMine_address_listview != null) {
					mineAddAddressRes.getAddresses().remove(deletPosition);
					mineReceiptAddressAdapter = new MineReceiptAddressListAdapter(this, mineAddAddressRes.getAddresses());
					mMine_address_listview.setAdapter(mineReceiptAddressAdapter);
					mineReceiptAddressAdapter.notifyDataSetChanged();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(this, mess);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv://返回
			finish();
			break;
		case R.id.head_right_tv://新增
			Intent intent = new Intent(MineReceiptAddressListActivity.this, MineAddReceiveAddressActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}


	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	

}
