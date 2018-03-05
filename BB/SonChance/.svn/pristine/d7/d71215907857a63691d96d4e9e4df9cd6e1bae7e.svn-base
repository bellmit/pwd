package com.tastebychance.sonchance.personal.locate;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.common.CommonAdapter;
import com.tastebychance.sonchance.common.ViewHolder;
import com.tastebychance.sonchance.personal.locate.bean.AddressSearchInfo;
import com.tastebychance.sonchance.personal.locate.util.AMapUtil;
import com.tastebychance.sonchance.personal.locate.util.ToastUtil;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * AMapV1地图中简单介绍poisearch搜索
 */
public class PoiKeywordSearchActivity extends FragmentActivity implements
		OnMarkerClickListener, InfoWindowAdapter, TextWatcher,
		OnClickListener, InputtipsListener {
	private AutoCompleteTextView searchText;// 输入搜索关键字
	private ListView person_goodsreceiptaddress_lv;
	private TextView city_tv;//城市
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_goodsreceiptaddress_search);
		bindViews();
		setTitle();
		setStatusBar();
	}

	private void bindViews() {
		person_goodsreceiptaddress_lv = (ListView) findViewById(R.id.person_goodsreceiptaddress_lv);
	}

	/**
	 * 设置状态栏颜色为透明色，这样能保证状态栏为沉浸式状态
	 * 根据SDK >= 21时，标题栏高度设为statusBarHeight(25dp)+titlBarHeight(48dp)
	 * 若SDK < 21,标题栏高度直接为titlBarHeight,关于不同的高度设置可以用两个values，values-19两个分别设置不同的dimens
	 */
	protected void setStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
		searchText.addTextChangedListener(this);// 添加文本输入框监听事件

		//动态设置状态栏下方的控件（view）的高度
		View view = (View) findViewById(R.id.view1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.height = SystemUtil.getInstance().getStatusBarHeight();;
		view.setLayoutParams(lp);

		city_tv = (TextView) findViewById(R.id.head_location_tv);
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);

		//展示城市
		left_btn.setOnClickListener(this);
		city_tv.setOnClickListener(this);
		//取消
		right_tv.setOnClickListener(this);

		person_goodsreceiptaddress_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (null == addressList || addressList.size() <= 0){
					UiHelper.ShowOneToast(PoiKeywordSearchActivity.this,"无查询结果,请重新关键字!");
					return;
				}
				if (addressList.size() > position){
					addressList.get(position);

					Intent intent = new Intent();
					intent.putExtra("addressSearchInfo" , addressList.get(position));
					PoiKeywordSearchActivity.this.setResult(Constants.GETSEARCHADDRESS_RETURNCODE,intent);
					PoiKeywordSearchActivity.this.finish();
				}
			}
		});
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return false;
	}

	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	@Override
	public View getInfoWindow(final Marker marker) {
		View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
				null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(marker.getTitle());

		TextView snippet = (TextView) view.findViewById(R.id.snippet);
		snippet.setText(marker.getSnippet());
		ImageButton button = (ImageButton) view
				.findViewById(R.id.start_amap_app);
		// 调起高德地图app
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAMapNavi(marker);
			}
		});
		return view;
	}

	/**
	 * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
	 */
	public void startAMapNavi(Marker marker) {
		// 构造导航参数
		NaviPara naviPara = new NaviPara();
		// 设置终点位置
		naviPara.setTargetPoint(marker.getPosition());
		// 设置导航策略，这里是避免拥堵
		naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

		// 调起高德地图导航
		try {
			AMapUtils.openAMapNavi(naviPara, getApplicationContext());
		} catch (com.amap.api.maps.AMapException e) {

			// 如果没安装会进入异常，调起下载页面
			AMapUtils.getLatestAMapApp(getApplicationContext());
		}
	}

	/**
	 * 判断高德地图app是否已经安装
	 */
	public boolean getAppIn() {
		PackageInfo packageInfo = null;
		try {
			packageInfo = this.getPackageManager().getPackageInfo(
					"com.autonavi.minimap", 0);
		} catch (Exception e) {
			packageInfo = null;
			e.printStackTrace();
		}
		// 本手机没有安装高德地图app
		if (packageInfo != null) {
			return true;
		}
		// 本手机成功安装有高德地图app
		else {
			return false;
		}
	}

	/**
	 * 获取当前app的应用名字
	 */
	public String getApplicationName() {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = getApplicationContext().getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					getPackageName(), 0);
		} catch (Exception e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		ToastUtil.show(PoiKeywordSearchActivity.this, infomation);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String newText = s.toString().trim();
		if (!AMapUtil.IsEmptyOrNullString(newText)) {
		    InputtipsQuery inputquery = new InputtipsQuery(newText, city_tv.getText().toString());
		    Inputtips inputTips = new Inputtips(PoiKeywordSearchActivity.this, inputquery);
		    inputTips.setInputtipsListener(this);
		    inputTips.requestInputtipsAsyn();
		}
	}

	/**
	 * Button点击事件回调方法
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			/**
			 * 选择城市
			 */
			case R.id.head_location_tv:
			case R.id.head_left_iv:
				UiHelper.ShowOneToast(PoiKeywordSearchActivity.this, "暂未");
				break;
			/**
			 * 取消
			 */
			case R.id.head_right_tv:
				PoiKeywordSearchActivity.this.finish();
				break;
			default:
				break;
		}
	}


	private List<AddressSearchInfo> addressList;
	@Override
	public void onGetInputtips(List<Tip> tipList, int rCode) {
		addressList = new ArrayList<AddressSearchInfo>();
		if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回

			addressList.clear();
			for (int i = 0; i < tipList.size(); i++) {
				WeakReference<AddressSearchInfo> wf = new WeakReference<AddressSearchInfo>(new AddressSearchInfo());
				wf.get().setCity(city_tv.getText().toString());
				wf.get().setLocation_latitude(tipList.get(i).getPoint() != null ? tipList.get(i).getPoint().getLatitude() : 0);
				wf.get().setLocation_longitude(tipList.get(i).getPoint() != null ? tipList.get(i).getPoint().getLongitude() :0);
				wf.get().setKeyword(searchText.getText().toString());
				wf.get().setLocationName(tipList.get(i).getName());
				wf.get().setDetailLocationName(tipList.get(i).getAddress());
				addressList.add(wf.get());
			}

			CommonAdapter<AddressSearchInfo> adapter;
			person_goodsreceiptaddress_lv.setAdapter(adapter = new CommonAdapter<AddressSearchInfo>(
					getApplicationContext(),addressList,R.layout.person_goodsreceiptaddress_search_item
			) {
				@Override
				protected void convert(ViewHolder viewHolder, AddressSearchInfo item) {
					viewHolder.setText(R.id.person_goodsreceiptaddress_search_item_locationname_tv,item.getLocationName());
					viewHolder.setText(R.id.person_goodsreceiptaddress_search_item_locationdetailname_tv,item.getDetailLocationName());
				}
			});
		} else {
			ToastUtil.showerror(this, rCode);
		}
	}
}
