package com.tastebychance.sonchance.homepage.locate;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.homepage.locate.QuickIndexBar.OnTouchLetterListener;
import com.tastebychance.sonchance.homepage.locate.bean.CityInfo;
import com.tastebychance.sonchance.util.CharacterParser;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

/**
 * 地点选择
 * 
 * @author shenbh
 *
 *         2017年8月14日
 */
public class PlaceChooseActivity extends MyBaseActivity implements AMapLocationListener {

	private String defaultPlace;

	private QuickIndexBar quickIndexBar;
	private ListView listview;
	private TextView currentWord , locateName_tv;
//	private EditText searchcityname_edt;//搜索
	private EditText filter_edit;
	private TextView search_nocity_tv;//搜索结果没有城市
	private PlaceListViewAdapter adapter;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		defaultPlace = intent.getStringExtra("defaultPlace");

		setContentView(R.layout.home_placechoose);

		bindViews();
		setListener();
		
		setTitle();
		getPlacesDatas();

		initObject();
	}

	private void initObject() {
		if (null != search_nocity_tv) {
			search_nocity_tv.setVisibility(View.GONE);
		}
		filter_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		characterParser = CharacterParser.getInstance();

		if (null != locateName_tv){

			if (null != getLocateCityFromServer) {
				locateName_tv.setText(getLocateCityFromServer);
			} else if (defaultPlace != null) {
				locateName_tv.setText(defaultPlace);
			}
		}
	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
     */
	private void filterData(String filterStr) {
		List<CityInfo> filterDataList = new ArrayList<CityInfo>();
		if (TextUtils.isEmpty(filterStr)){
			filterDataList = cityInfos;
			search_nocity_tv.setVisibility(View.GONE);
		}else{
			filterDataList.clear();
			for (CityInfo cityInfo:cityInfos){
				String name = cityInfo.getCityName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDataList.add(cityInfo);
				}
			}
		}

		//根据a-z进行排序
		Collections.sort(filterDataList);
		if (null != adapter){
			adapter.updateListView(filterDataList);
		}
		if (filterDataList.size() == 0){
			search_nocity_tv.setVisibility(View.VISIBLE);
		}
	}

	private void bindViews(){
		locateName_tv = (TextView) findViewById(R.id.locateName_tv);
		quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
		listview = (ListView) findViewById(R.id.listview);
		currentWord = (TextView) findViewById(R.id.currentWord);
//		searchcityname_edt = (EditText) findViewById(R.id.searchcityname_edt);
		filter_edit = (EditText) findViewById(R.id.filter_edit);
		search_nocity_tv = (TextView) findViewById(R.id.search_nocity_tv);
	}

	private void setListener() {
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("parent = " + parent);
				CityInfo cityInfo = cityInfos.get(position);
				if (cityInfo != null) {
					String chosedCity = cityInfo.getCityName();
					SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"chosedCity",chosedCity);
				}
				PlaceChooseActivity.this.finish();
			}
		});
	}
	
	/**网络请求数据
	 * 
	 * /api/UserApp/cityList
	 * 
	 */
	//1、获取数据：城市列表
	private List<CityInfo> cityInfos = new ArrayList<CityInfo>();
	private String getLocateCityFromServer ;
	private void getPlacesDatas() {
		String token = SystemUtil.getInstance().getToken();
		Log.i(Constants.TAG, token);

		String url =  UrlManager.URL_HOME_CITYLIST;
		OkHttpClient mOkHttpClient = new OkHttpClient();

		RequestBody formBody = new FormBody.Builder().add("city", defaultPlace).add("token", token).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					String str = response.body().string();
					Log.i(Constants.TAG, str);

					ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
					if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

    //data:{"D":["大理"],"E":[],"F":[],"G":["广州"],"A":[],"B":["北京市"],"C":[],"L":["伦敦","龙岩"],"M":[],"N":["纽约"],"O":[],"H":[],"I":[],"J":["江苏"],"K":["开封"],"U":[],"T":[],"W":["武汉"],"V":[],"Q":[],"P":["莆田"],"S":["三明","上海","石家庄"],"R":[],"Y":[],"X":["厦门"],"Z":[]}
                        Map<String, Object> map = (Map<String, Object>) JSONObject.parseObject(resInfo.getData().toString());

                        Map<String, Object> resultMap = new HashMap<String, Object>();

                        Set<Entry<String, Object>> set = map.entrySet();
                        for (Entry<String, Object> entry : set) {
                            System.out.println(entry.getKey() + "---" + entry.getValue());
                            String value = entry.getValue().toString().replace("\",\"", ",").replace("[\"", "").replace("\"]", "").replace("[", "").replace("]", "");
                            if (StringUtil.isNotEmpty(value)) {
                                resultMap.put(entry.getKey(), value);
                            }
                        }

                        for (Entry<String, Object> entry : resultMap.entrySet()) {
                            String[] strs = entry.getValue().toString().split(",");
                            for (String string : strs) {
                                if (entry.getKey().equals("tag")) {
                                    getLocateCityFromServer = string;
                                    continue;
                                }
                                WeakReference<CityInfo> wf = new WeakReference<CityInfo>(new CityInfo());
                                wf.get().setCityFirstWord(entry.getKey());
                                wf.get().setCityName(string);
                                cityInfos.add(wf.get());
                            }
                        }

                        System.out.println(cityInfos);

                        //2、排序
                        Collections.sort(cityInfos);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //3、设置adapter
                                adapter = new PlaceListViewAdapter(PlaceChooseActivity.this, cityInfos);
                                listview.setAdapter(adapter);
                                //4、点击右侧快速导航字母
                                touchQuickIndexBar(cityInfos);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});}
	
	private void touchQuickIndexBar(final List<CityInfo> list){
		quickIndexBar.setOnTouchLetterListener(new OnTouchLetterListener() {
			@Override
			public void onTouchLetter(String letter) {
				// 根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
				for (int i = 0; i < list.size(); i++) {
					String firstWord = list.get(i).getCityFirstWord();
					if(letter.equals(firstWord)){
						// 说明找到了，那么应该讲当前的item放到屏幕顶端
						listview.setSelection(i);
						break;//只需要找到第一个就行
					}
				}
				
				if (letter.equals("☆")) {
					listview.setSelection(0);
				}
				// 显示当前触摸的字母
				showCurrentWord(letter);
			}
		});

		// 通过缩小currentWord来隐藏
		ViewHelper.setScaleX(currentWord, 0);
		ViewHelper.setScaleY(currentWord, 0);
	}
	
	private boolean isScale = false;
	private Handler handler = new Handler();

	protected void showCurrentWord(String letter) {
		currentWord.setText(letter);
		if (!isScale) {
			isScale = true;
			ViewPropertyAnimator.animate(currentWord).scaleX(1f).setInterpolator(new OvershootInterpolator())
					.setDuration(450).start();
			ViewPropertyAnimator.animate(currentWord).scaleY(1f).setInterpolator(new OvershootInterpolator())
					.setDuration(450).start();
		}

		// 先移除之前的任务
		handler.removeCallbacksAndMessages(null);

		// 延时隐藏currentWord
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// currentWord.setVisibility(View.INVISIBLE);
				ViewPropertyAnimator.animate(currentWord).scaleX(0f).setDuration(450).start();
				ViewPropertyAnimator.animate(currentWord).scaleY(0f).setDuration(450).start();
				isScale = false;
			}
		}, 1500);
	}

	
	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
		if (center_tv != null)
			center_tv.setText("地点选择");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
			left_btn.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					PlaceChooseActivity.this.finish();
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
	 * 重新定位
	 * @param view
	 */
	public void reLocate(View view){
		location();
		String city = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city");
		if (StringUtil.isNotEmpty(city)){
			if(defaultPlace != null){
				locateName_tv.setText(defaultPlace);
			}
		}
	}

	//Amap高德定位
	private void location() {
		detective();

		//初始化定位
		mlocationClient = new AMapLocationClient((getApplicationContext()));
		//设置定位回调监听
		mlocationClient.setLocationListener(this);
		mLocationOption = new AMapLocationClientOption();
		//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
		//设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		//设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(false);
		//设置是否强制刷新WIFI，默认为强制刷新
		mLocationOption.setWifiActiveScan(true);
		//设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(false);
		//设置定位间隔 单位毫秒
		mLocationOption.setInterval(10 * 1 * 1000);
		//给定位客户端对象设置定位参数
		mlocationClient.setLocationOption(mLocationOption);
		//启动定位
		mlocationClient.startLocation();

	}

	private void detective(){
		//停止定位
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	protected void onStart() {
		super.onStart();
		detective();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		if(null != mlocationClient){
			mlocationClient.onDestroy();
		}
	}

	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				String city = amapLocation.getCity();
				System.out.println("高德定位到city = " + city);

				if (null != locateName_tv && StringUtil.isNotEmpty(city)){
					locateName_tv.setText(city);
					SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"city",city);
				}

			} else {
				String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);
			}
		}
	}
}
