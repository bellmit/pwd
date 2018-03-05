package com.tastebychance.sonchance.personal.locate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.locate.bean.AddressSearchInfo;
import com.tastebychance.sonchance.personal.locate.overlay.PoiOverlay;
import com.tastebychance.sonchance.personal.locate.util.AMapUtil;
import com.tastebychance.sonchance.personal.locate.util.ToastUtil;
import com.tastebychance.sonchance.util.Constants;

import java.util.List;

/**
 * AMapV2地图中简单介绍一些Marker的用法.
 * Marker动画功能介绍
 */
public class MarkerAnimationActivity extends MyBaseActivity implements
		LocationSource,AMapLocationListener,
		AMap.OnCameraChangeListener,GeocodeSearch.OnGeocodeSearchListener ,PoiSearch.OnPoiSearchListener {
	private MarkerOptions markerOption;
	private AMap aMap;
	private MapView mapView;
	private LatLng latlng = new LatLng(39.761, 116.434);

	//定位
	private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
	private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	Marker screenMarker = null;
	GeocodeSearch geocodeSearch;

	private ImageView mPerson_address_locate_iv;
	private TextView mPerson_address_content_tv;
	private ImageView mPerson_address_turnright_iv;
	private Button mPerson_address_confirm_btn;

	//获取到的地址和经纬度
	private String simpleAddress;
	private String longitude;
	private String latitude;


	private ProgressDialog progDialog = null;// 搜索时进度条

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationsource_activity);
		/*
		 * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
		// Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
		// MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState); // 此方法必须重写
		init();

		setTitle();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}

		//当地图重新加载的时候给屏幕中心添加一个marker
		aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
			@Override
			public void onMapLoaded() {
				addMarkersToMap();
			}
		});

		aMap.setOnCameraChangeListener(this);
		geocodeSearch = new GeocodeSearch(this);
		geocodeSearch.setOnGeocodeSearchListener(this);

		mPerson_address_locate_iv = (ImageView) findViewById(R.id.person_address_locate_iv);
		mPerson_address_content_tv = (TextView) findViewById(R.id.person_address_content_tv);
		mPerson_address_turnright_iv = (ImageView) findViewById(R.id.person_address_turnright_iv);
		mPerson_address_confirm_btn = (Button) findViewById(R.id.person_address_confirm_btn);

		//绑定信息串口点击事件
//		aMap.setOnInfoWindowClickListener(onInfoWindowClickListener);

		mPerson_address_confirm_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击确定进行返回获取的地址
				Intent intent = new Intent();
				intent.putExtra("address",simpleAddress);
				intent.putExtra("longitude",longitude);
				intent.putExtra("latitude",latitude);
				MarkerAnimationActivity.this.setResult(Constants.GETADDRESS_RETURNCODE,intent);
				MarkerAnimationActivity.this.finish();
			}
		});
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		//动态设置状态栏下方的控件（view）的高度
		View view = (View) findViewById(R.id.view1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.height = statusHeight;
		view.setLayoutParams(lp);

		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
		TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
		if (center_tv != null) {
			center_tv.setText("确认收货地址");
		}
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
			left_btn.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					setResult(Constants.GETADDRESS_RETURNCODE);
					MarkerAnimationActivity.this.finish();
				}
			});
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		if(null != mlocationClient){
			mlocationClient.onDestroy();
		}
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {
		addMarkerInScreenCenter();
	}


	/**
	 * 在屏幕中心添加一个Marker
	 */
	private void addMarkerInScreenCenter() {
		LatLng latLng = aMap.getCameraPosition().target;
		Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
		screenMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f,0.5f)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
		//设置Marker在屏幕上,不跟随地图移动
	 	screenMarker.setPositionByPixels(screenPosition.x,screenPosition.y);
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//		aMap.getUiSettings().setCompassEnabled(true);//设置指南针是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

		//初始化时设置缩放比例
		aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

		setupLocationStyle();
	}

	private void setupLocationStyle(){
		// 自定义系统定位蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
		// 自定义定位蓝点图标
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
				fromResource(R.drawable.gps_point));
		// 自定义精度范围的圆形边框颜色
		myLocationStyle.strokeColor(STROKE_COLOR);
		//自定义精度范围的圆形边框宽度
		myLocationStyle.strokeWidth(5);
		// 设置圆形的填充颜色
		myLocationStyle.radiusFillColor(FILL_COLOR);
		// 将自定义的 myLocationStyle 对象添加到地图上
		myLocationStyle.interval(10 * 60 * 1000);//设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

		aMap.setMyLocationStyle(myLocationStyle);
	}

	/**
	 * 地址搜索
	 * @param view
	 */
	public void  searchClick(View view){
		Intent intent = new Intent(MarkerAnimationActivity.this,PoiKeywordSearchActivity.class);
		startActivityForResult(intent,Constants.GETSEARCHADDRESS_RETURNCODE);
	}

	/**
	 * 点击搜索按钮
	 */
	private String keyWord;
	public void searchButton(String city) {
//		keyWord = AMapUtil.checkEditText(searchText);
		if ("".equals(keyWord)) {
			ToastUtil.show(MarkerAnimationActivity.this, "请输入搜索关键字");
			return;
		} else {
			doSearchQuery(city);
		}
	}

	/**
	 * 开始进行poi搜索
	 */
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索
	protected void doSearchQuery(String city) {
		showProgressDialog();// 显示进度框
		currentPage = 0;
		query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页

		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}

	private AddressSearchInfo addressSearchInfo;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Constants.GETSEARCHADDRESS_RETURNCODE && null != data){
			addressSearchInfo = (AddressSearchInfo) data.getSerializableExtra("addressSearchInfo");
			//搜索
			keyWord = addressSearchInfo.getLocationName();
			searchButton(addressSearchInfo.getCity());
		}
	}

	@Override
	public void onCameraChange(CameraPosition cameraPosition) {
		LatLng target = cameraPosition.target;
//		System.out.println(target.latitude+"---"+target.longitude);
		getAddressByLatlng(target);
	}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {

	}


	@Override
	public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

		if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
			RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
			String formatAddress = regeocodeAddress.getFormatAddress();
			simpleAddress = formatAddress.substring(9);
			System.out.println("查询经纬度对应详细地址：\n" + simpleAddress);
			mPerson_address_content_tv.setText(simpleAddress);
		} else {
//				NToast.shortToast(AMAPLocationActivity.this, "没有搜索到结果");
			mPerson_address_content_tv.setText("没有搜索到结果");
			System.out.println();
		}

		/*if (i == 0) {
			if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
				RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
				String formatAddress = regeocodeAddress.getFormatAddress();
				String simpleAddress = formatAddress.substring(9);
				System.out.println("查询经纬度对应详细地址：\n" + simpleAddress);
			} else {
//				NToast.shortToast(AMAPLocationActivity.this, "没有搜索到结果");
				System.out.println();
			}
		} else {
//			NToast.shortToast(AMAPLocationActivity.this, "搜索失败,请检查网络");
			System.out.println();
		}*/
	}

	@Override
	public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

	}

	private void getAddressByLatlng(LatLng latLng) {

		latitude = latLng.latitude +"";
		longitude = latLng.longitude +"";

		//逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
		LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
		//异步查询
		geocodeSearch.getFromLocationAsyn(query);
	}


	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索:\n" + keyWord);
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (mListener != null && aMapLocation != null) {
			if (aMapLocation != null
					&& aMapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
				aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//设置定位成功后地图缩放比例
			} else {
				String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
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
		ToastUtil.show(MarkerAnimationActivity.this, infomation);
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener onLocationChangedListener) {
		mListener = onLocationChangedListener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//获取一次定位结果：
			//该方法默认为false。
			mLocationOption.setOnceLocation(true);
			//获取最近3s内精度最高的一次定位结果：
			//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
			mLocationOption.setOnceLocationLatest(true);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	private PoiResult poiResult; // poi返回的结果
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		dissmissProgressDialog();// 隐藏对话框

		if (rCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

					if (poiItems != null && poiItems.size() > 0) {
						aMap.clear();// 清理之前的图标
		/*				PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();*/


						//将地图移动到指定经纬度
						aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(addressSearchInfo.getLocation_latitude(), addressSearchInfo.getLocation_longitude())));
						//设置缩放级别
						aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
						//在屏幕中心添加一个marker
						addMarkerInScreenCenter();

					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						ToastUtil.show(MarkerAnimationActivity.this, R.string.no_result);
					}
				}
			} else {
				ToastUtil.show(MarkerAnimationActivity.this, R.string.no_result);
			}
		} else {
			ToastUtil.showerror(this, rCode);
		}
	}

	@Override
	public void onPoiItemSearched(PoiItem poiItem, int i) {

	}
}
