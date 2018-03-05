/**
 * 
 */
package com.newland.wstdd.mine.receiptaddress;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.mine.receiptaddress.beanrequest.TddDeliverAddr;

/**我的-设置收货地址列表 适配器
 * @author H81 2015-11-8
 *
 */
public class MineReceiptAddressListAdapter extends BaseAdapter{
	List<TddDeliverAddr> mineReceiptAddressInfos;
	MineReceiptAddressListActivity mineReceiptAddressActivity;
	private Context context;
	LayoutInflater layoutInflater;
	private HashMap<Integer,Boolean> isDefaultAddressMap ;
	
	public HashMap<Integer, Boolean> getIsDefaultAddressMap() {
		return isDefaultAddressMap;
	}
	public void setIsDefaultAddressMap(HashMap<Integer, Boolean> isDefaultAddressMap) {
		this.isDefaultAddressMap = isDefaultAddressMap;
	}
	public MineReceiptAddressListAdapter(Context context , List<TddDeliverAddr> list) {
		this.mineReceiptAddressActivity=(MineReceiptAddressListActivity) context;
		this.mineReceiptAddressInfos=list;
		this.context=context;
		layoutInflater = LayoutInflater.from(mineReceiptAddressActivity);
		isDefaultAddressMap = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}
	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < mineReceiptAddressInfos.size(); i++) {
			getIsDefaultAddressMap().put(i, false);
		}
	}
	@Override
	public int getCount() {
		return mineReceiptAddressInfos.size() == 0 ?0 :mineReceiptAddressInfos.size();
	}

	@Override
	public Object getItem(int position) {
		if (mineReceiptAddressInfos!=null&&mineReceiptAddressInfos.size()>0) {
			
			return mineReceiptAddressInfos.get(position);
		}else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private String oldIdString = null;//默认地址的ActivityId
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.activity_mine_receiptaddress_item, null);
			viewHolder.mActivity_mine_receiptaddress_name_tv = (TextView)convertView. findViewById(R.id.activity_mine_receiptaddress_name_tv);
			viewHolder.mActivity_mine_receiptaddress_telephone_tv = (TextView)convertView. findViewById(R.id.activity_mine_receiptaddress_telephone_tv);
			viewHolder.mActivity_mine_receiptaddress_defaultaddress_tv = (TextView)convertView. findViewById(R.id.activity_mine_receiptaddress_defaultaddress_tv);
			viewHolder.mActivity_mine_receiptaddress_addresscontent_tv = (TextView) convertView.findViewById(R.id.activity_mine_receiptaddress_addresscontent_tv);
			viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv = (PengTextView) convertView.findViewById(R.id.activity_mine_receiptaddress_setdefaultaddress_tv);
			viewHolder.mActivity_mine_receiptaddress_delete_tv = (PengTextView)convertView. findViewById(R.id.activity_mine_receiptaddress_delete_tv);
			viewHolder.mActivity_mine_receiptaddress_edit_tv = (PengTextView) convertView.findViewById(R.id.activity_mine_receiptaddress_edit_tv);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mActivity_mine_receiptaddress_defaultaddress_tv = (TextView)convertView. findViewById(R.id.activity_mine_receiptaddress_defaultaddress_tv);
		
		viewHolder.mActivity_mine_receiptaddress_name_tv.setText(mineReceiptAddressInfos.get(position).getConnectUserName());
		viewHolder.mActivity_mine_receiptaddress_telephone_tv .setText(mineReceiptAddressInfos.get(position).getConnectPhone());
		if(mineReceiptAddressInfos.get(position).getIsDefault()==(short)1){
			viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.setDrawableLeft(context.getResources().getDrawable(R.drawable.defaultaddress_ticked));
			viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.setCompoundDrawablesWithIntrinsicBounds( viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.getDrawableLeft(),null, null, null);
			
			viewHolder.mActivity_mine_receiptaddress_defaultaddress_tv .setText("默认地址");
			viewHolder.mActivity_mine_receiptaddress_defaultaddress_tv .setVisibility(View.VISIBLE);
			oldIdString = mineReceiptAddressInfos.get(position).getAddressId();
			
			isDefaultAddressMap.put(position, true);
			setIsDefaultAddressMap(isDefaultAddressMap);
			
		}else if(mineReceiptAddressInfos.get(position).getIsDefault()==(short)2){
			viewHolder.mActivity_mine_receiptaddress_defaultaddress_tv .setVisibility(View.GONE);
			viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.setDrawableLeft(context.getResources().getDrawable(R.drawable.defaultaddress_tick));
			viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.setCompoundDrawablesWithIntrinsicBounds( viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.getDrawableLeft(),null, null, null);
			
			
			isDefaultAddressMap.put(position, false);
			setIsDefaultAddressMap(isDefaultAddressMap);
		}
		viewHolder.mActivity_mine_receiptaddress_addresscontent_tv.setText(mineReceiptAddressInfos.get(position).getAddress());
		viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv .setText("设为默认地址");
		viewHolder.mActivity_mine_receiptaddress_setdefaultaddress_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isDefaultAddressMap.get(position)) {
					mineReceiptAddressActivity.setDefaultAddress(oldIdString,mineReceiptAddressInfos.get(position).getAddressId());//上传默认地址
				}
				/*else {
					UiHelper.ShowOneToast(context, "已经是默认地址了,请不要再摁了");
				}*/
			}
		});
		viewHolder.mActivity_mine_receiptaddress_delete_tv .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {//删除
				AlertDialog dialog = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("是否删除？").setPositiveButton("是", new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						mineReceiptAddressActivity.setDeleteAddress(mineReceiptAddressInfos.get(position).getAddressId());
					}
				}).setNegativeButton("否", new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
				dialog.setCanceledOnTouchOutside(false);
			}
		});
		//编辑收货地址
		viewHolder.mActivity_mine_receiptaddress_edit_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mineReceiptAddressActivity.editAddress(mineReceiptAddressInfos.get(position).getAddressId());
			}
		});
		return convertView;
	}

	class ViewHolder {
		private TextView mActivity_mine_receiptaddress_name_tv;//名字
	    private TextView mActivity_mine_receiptaddress_telephone_tv;//电话
	    private TextView mActivity_mine_receiptaddress_defaultaddress_tv;//默认地址
	    private TextView mActivity_mine_receiptaddress_addresscontent_tv;//详细地址
	    private PengTextView mActivity_mine_receiptaddress_setdefaultaddress_tv;//设为默认地址
	    private PengTextView mActivity_mine_receiptaddress_delete_tv;//删除
	    private PengTextView mActivity_mine_receiptaddress_edit_tv;//编辑

	}
}
