package com.tastebychance.sonchance.homepage.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.HomeActivity;
import com.tastebychance.sonchance.homepage.homeshoppingcart.bean.ShoppingCartBean;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;
import com.tastebychance.sonchance.util.ImageDownLoad;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.UrlManager;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
/**
 *
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class HomeListViewAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private SparseArray<ShoppingCartBean> list;
	private HomeActivity activity;
	private static DecimalFormat df;

	public HomeListViewAdapter(Context context , SparseArray<ShoppingCartBean> list) {
		this.activity = (HomeActivity) context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		df = new DecimalFormat("0.00");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list == null ? null : list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.home_listviewitem, null);
			viewHolder = new ViewHolder();
			viewHolder.mHomelistitem = (RelativeLayout) convertView.findViewById(R.id.homelistitem);
			viewHolder.mHomelist_image = (com.tastebychance.sonchance.view.NumImageView) convertView.findViewById(R.id.homelist_image);
			viewHolder.mHome_listitem_title_tv = (TextView) convertView.findViewById(R.id.home_listitem_title_tv);
			viewHolder.mHome_listitem_discount_tv = (TextView) convertView.findViewById(R.id.home_listitem_discount_tv);
			viewHolder.mHome_listitem_soldnum_tv = (TextView) convertView.findViewById(R.id.home_listitem_soldnum_tv);
			viewHolder.mHome_listitem_addshoppingcart_ll = (LinearLayout) convertView.findViewById(R.id.order_dish__addshoppingcart_ll);
			viewHolder.mHome_listitem_price = (TextView) convertView.findViewById(R.id.order_dish_price_tv);
			viewHolder.home_listitem_goodevaluatenum_tv = (TextView) convertView.findViewById(R.id.home_listitem_goodevaluatenum_tv);
			viewHolder.home_listitem_badevaluatenum_tv = (TextView) convertView.findViewById(R.id.home_listitem_badevaluatenum_tv);
			viewHolder.mHome_listitem_content_tv = (TextView) convertView.findViewById(R.id.home_listitem_content_tv);
//			viewHolder.home_listitem_contentscore_integer_tv = (TextView) convertView.findViewById(R.id.home_listitem_contentscore_integer_tv);
//			viewHolder.home_listitem_contentscore_decimal_tv = (TextView) convertView.findViewById(R.id.home_listitem_contentscore_integer_tv);

			viewHolder.num_ll = (LinearLayout) convertView.findViewById(R.id.num_ll);
//			viewHolder.add_iv = (ImageView) convertView.findViewById(R.id.add_iv);
//			viewHolder.remove_iv = (ImageView) convertView.findViewById(R.id.remove_iv);
 			viewHolder.add_ll = (LinearLayout) convertView.findViewById(R.id.add_ll);
			viewHolder.remove_ll = (LinearLayout) convertView.findViewById(R.id.remove_ll);
			viewHolder.num_tv = (TextView) convertView.findViewById(R.id.num_tv);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final ShoppingCartBean bean = list.valueAt(position);

		ImageDownLoad.getDownLoadSmallImg(bean.getHomePageListRes().getLogo(),viewHolder.mHomelist_image);

		viewHolder.mHome_listitem_title_tv.setText(bean.getHomePageListRes().getName());

		if (StringUtil.isEmpty(bean.getHomePageListRes().getPromotion())){
			viewHolder.mHome_listitem_discount_tv.setVisibility(View.GONE);
		}else{
			viewHolder.mHome_listitem_discount_tv.setVisibility(View.VISIBLE);
			viewHolder.mHome_listitem_discount_tv.setText(bean.getHomePageListRes().getPromotion());
		}
		String soldNum = "已售 "+ bean.getHomePageListRes().getSail_count()+" 单";
		viewHolder.mHome_listitem_soldnum_tv.setText(StringUtil.setStringRedColor(soldNum, "已售 ".length(),soldNum.length()- " 单".length()));
		viewHolder.mHome_listitem_price.setText(StringUtil.setShoppingcartTotalMoney(String.valueOf(df.format(bean.getHomePageListRes().getPrice()))));
		String goodValuate = "好评:"+bean.getHomePageListRes().getGood_comments()+" 条";
		viewHolder.home_listitem_goodevaluatenum_tv.setText(StringUtil.setStringRedColor(goodValuate,"好评:".length(),goodValuate.length() - " 条".length()));
		String badValuate = "差评:"+bean.getHomePageListRes().getBad_comments()+" 条";
		viewHolder.home_listitem_badevaluatenum_tv.setText(StringUtil.setStringGreenColor(badValuate,"差评:".length(),badValuate.length() - " 条".length()));
		//具体介绍内容
//		viewHolder.mHome_listitem_content_tv.setText(bean.getHomePageListRes().);
		//评分
		/*viewHolder.home_listitem_contentscore_integer_tv.setVisibility(View.GONE);
		if(null != bean.getHomePageListRes().getComments() && bean.getHomePageListRes().getComments().size() > 0 ){

			viewHolder.home_listitem_contentscore_integer_tv.setVisibility(View.VISIBLE);
			viewHolder.home_listitem_contentscore_integer_tv.setText(StringUtil.setStringGreenColor(bean.getHomePageListRes().getComments().get(0).getGrade()+""));
			*//*String grade = bean.getHomePageListRes().getComments().get(0).getGrade()+"";
			String[] grades = grade.split(".");
			if(null != grade && grades.length > 0 ){
				viewHolder.home_listitem_contentscore_integer_tv.setText(StringUtil.setStringGreenColor(grades[0]));
			}else if(StringUtil.isNotEmpty(grade)){
				viewHolder.home_listitem_contentscore_decimal_tv.setVisibility(View.GONE);
				viewHolder.home_listitem_contentscore_integer_tv.setText(StringUtil.setStringGreenColor(grade));
			}
			if(null != grade && grades.length > 1 ){
				viewHolder.home_listitem_contentscore_decimal_tv.setVisibility(View.VISIBLE);
				viewHolder.home_listitem_contentscore_decimal_tv.setText(StringUtil.setStringGreenColor("."+grades[1]));
			}else{
				viewHolder.home_listitem_contentscore_decimal_tv.setVisibility(View.GONE);
			}*//*
		}
*/
		if (null != bean.getHomePageListRes().getComments()) {
			if (bean.getHomePageListRes().getComments().size() >= 1) {
				String mobile1 = bean.getHomePageListRes().getComments().get(0).getMobile();
				String grade1 = String.valueOf(bean.getHomePageListRes().getComments().get(0).getGrade());
				String content1 = StringUtil.isNotEmpty(bean.getHomePageListRes().getComments().get(0).getContent())
						? bean.getHomePageListRes().getComments().get(0).getContent()
						: "该客官没有留下评价说明~";
//				String evalute1 = mobile1 + ":" + content1 + grade1;
//				viewHolder.mHome_listitem_content_tv.setText(StringUtil.setStringGreenColor(evalute1, evalute1.length() - grade1.length(),evalute1.length()));
				String evalute1 = mobile1 + ":" + content1 ;
				viewHolder.mHome_listitem_content_tv.setText(evalute1);
			}else{
				viewHolder.mHome_listitem_content_tv.setText(null);
			}
		}

		//TODO:缓存上一次网络请求的数据；图片暂未设置；
		
		final int dishesId = bean.getHomePageListRes().getId();
		//点击某一项
		viewHolder.mHomelistitem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				activity.intentToOrder(dishesId);
			}
		});

		//显示“加入购物车” or 显示数量
		if (bean.getNum() <= 0){
			viewHolder.mHome_listitem_addshoppingcart_ll.setVisibility(View.VISIBLE);
			viewHolder.num_ll.setVisibility(View.GONE);
		}else{
			viewHolder.mHome_listitem_addshoppingcart_ll.setVisibility(View.GONE);
			viewHolder.num_ll.setVisibility(View.VISIBLE);

			setDishNum(viewHolder.num_tv,bean.getNum());
		}

		//点击“加入购物车”
		viewHolder.mHome_listitem_addshoppingcart_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示“加入购物车” 表示购物车中没有这个菜品
				activity.handlerCarNum(1,ShoppingCartToShoppingcart(list.valueAt(position)),true);
			}
		});

		if(viewHolder.mHome_listitem_addshoppingcart_ll.getVisibility() == View.GONE){
			viewHolder.num_ll.setVisibility(View.VISIBLE);
		}else{
			viewHolder.num_ll.setVisibility(View.GONE);
		}


		viewHolder.add_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.handlerCarNum(1,ShoppingCartToShoppingcart(list.valueAt(position)),true);
			}
		});

		viewHolder.remove_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (list.valueAt(position).getNum() <= 0){
					viewHolder.num_ll.setVisibility(View.GONE);
					viewHolder.mHome_listitem_addshoppingcart_ll.setVisibility(View.VISIBLE);
				}
				activity.handlerCarNum(0,ShoppingCartToShoppingcart(list.valueAt(position)),true);
			}
		});

		return convertView;
	}

	private ShoppingcartBean ShoppingCartToShoppingcart(ShoppingCartBean shoppingCartBean){
		WeakReference<ShoppingcartBean > wf = new WeakReference<ShoppingcartBean>(new ShoppingcartBean());
		wf.get().setId(shoppingCartBean.getHomePageListRes().getId());
		wf.get().setNum(shoppingCartBean.getNum());
		wf.get().setDishname(shoppingCartBean.getHomePageListRes().getName());
		wf.get().setPrice(shoppingCartBean.getHomePageListRes().getPrice());
		return wf.get();
	}

	//设置数量
	private void setDishNum(TextView tv,int num){
		tv.setText(num+"");
	}

	class ViewHolder{
		private RelativeLayout mHomelistitem;
		private com.tastebychance.sonchance.view.NumImageView mHomelist_image;
		private TextView mHome_listitem_title_tv;
		private TextView mHome_listitem_discount_tv;
		private TextView mHome_listitem_soldnum_tv;
//		private ImageView mHome_listitem_addshoppingcart_iv;
		private LinearLayout mHome_listitem_addshoppingcart_ll;
		private TextView mHome_listitem_price;
		private TextView home_listitem_goodevaluatenum_tv;
		private TextView home_listitem_badevaluatenum_tv;
		private TextView mHome_listitem_content_tv;
//		private TextView home_listitem_contentscore_integer_tv;
//		private TextView home_listitem_contentscore_decimal_tv;

		private LinearLayout num_ll;
		private TextView num_tv;

//		private ImageView remove_iv,add_iv;
		private LinearLayout remove_ll,add_ll;
	}
}
