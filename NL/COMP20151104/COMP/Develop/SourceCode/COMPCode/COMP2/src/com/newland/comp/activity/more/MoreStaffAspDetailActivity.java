package com.newland.comp.activity.more;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSON;
import com.newland.comp.activity.BaseActivity;
import com.newland.comp.activity.ShowPhotoActivity;
import com.newland.comp.adapter.more.MoreStaffAspAdapter;
import com.newland.comp.adapter.more.MoreStaffAspDetailAccessoryAdapter;
import com.newland.comp.adapter.more.MoreStaffAspDetailAdapter1;
import com.newland.comp.adapter.more.MoreStaffAspDetailAdapter2;
import com.newland.comp.bean.JsonInfoV2;
import com.newland.comp.bean.more.ProbleDetailAttachmentData;
import com.newland.comp.bean.more.PrombleData;
import com.newland.comp.bean.more.PrombleDetailData;
import com.newland.comp.bean.more.PrombleDetailData2;
import com.newland.comp.bean.more.PrombleDetailExp;
import com.newland.comp.bean.process.ProcessKeyValue;
import com.newland.comp.utils.ActivityUtil;
import com.newland.comp.utils.HttpUtils;
import com.newland.comp.utils.JsonInfo;
import com.newland.comp.utils.JsonInfoV3;
import com.newland.comp.utils.MD5Utils;
import com.newland.comp.utils.Options;
import com.newland.comp.utils.SharedPreferencesUtils;
import com.newland.comp.utils.StringUtil;
import com.newland.comp.view.LoadingDialog;
import com.newland.comp2.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ??????-???????????????????????????(????????????)
 * 
 * @author H81
 * 
 *         2015???5???6??? ??????11:53:05
 * @version 1.0.0
 */
public class MoreStaffAspDetailActivity extends BaseActivity implements OnClickListener {
	private TextView mTv_name;// ????????????
	private TextView mTv_id;// ????????????
	private TextView mTv_commu;// ????????????
	private TextView mTv_ks;// ??????
	private TextView mTv_type;// ????????????
	private TextView mTv_key;// ?????????
	private TextView mTv_content;// ??????
	private TextView mTv_reply;// ????????????
	// private com.newland.comp.view.MyListView mMylistview;// ????????????
	private com.newland.comp.view.MyListView mListview;// ????????????

	private List<PrombleDetailData> listData;// ????????????Data??????????????????
	private List<PrombleDetailData2> listData2;// ????????????Data2??????????????????

	private MoreStaffAspDetailAdapter1 adapter1;// ????????????
	private MoreStaffAspDetailAdapter2 adapter2;// ????????????
	private MoreStaffAspDetailAccessoryAdapter adapter3;// ????????????

	private LinearLayout mId_gallery;
	private final int MAX_INDEX = 9;
	ImageView[] mIv_accessory;

	private LoadingDialog dialog;
	private List<String> urlList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_staffasp_detail);
		setTitle();
		bindViews();
		getData();
	}

	/**
	 * ???????????????
	 */
	private void setTitle() {
		ImageButton left_btn = (ImageButton) findViewById(R.id.head_left_btn);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("????????????");
		if (left_btn != null) {// ??????
			left_btn.setVisibility(View.VISIBLE);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {// ??????
			right_tv.setVisibility(View.GONE);
		}
	}

	private void bindViews() {

		mTv_name = (TextView) findViewById(R.id.tv_name);
		mTv_id = (TextView) findViewById(R.id.tv_id);
		mTv_commu = (TextView) findViewById(R.id.tv_commu);
		mTv_ks = (TextView) findViewById(R.id.tv_ks);
		mTv_type = (TextView) findViewById(R.id.tv_type);
		mTv_key = (TextView) findViewById(R.id.tv_key);
		mListview = (com.newland.comp.view.MyListView) findViewById(R.id.listview);
		mTv_content = (TextView) findViewById(R.id.tv_content);
		mTv_reply = (TextView) findViewById(R.id.tv_reply);
		bindImgViews();
	}

	private void bindImgViews() {
		mId_gallery = (LinearLayout) findViewById(R.id.id_gallery);
		mIv_accessory = new ImageView[MAX_INDEX];
		mIv_accessory[0] = (ImageView) findViewById(R.id.iv_accessory2);
		mIv_accessory[1] = (ImageView) findViewById(R.id.iv_accessory3);
		mIv_accessory[2] = (ImageView) findViewById(R.id.iv_accessory4);
		mIv_accessory[3] = (ImageView) findViewById(R.id.iv_accessory5);
		mIv_accessory[4] = (ImageView) findViewById(R.id.iv_accessory6);
		mIv_accessory[5] = (ImageView) findViewById(R.id.iv_accessory7);
		mIv_accessory[6] = (ImageView) findViewById(R.id.iv_accessory8);
		mIv_accessory[7] = (ImageView) findViewById(R.id.iv_accessory9);
		mIv_accessory[8] = (ImageView) findViewById(R.id.iv_accessory10);
		mIv_accessory[0].setOnClickListener(this);
		mIv_accessory[1].setOnClickListener(this);
		mIv_accessory[2].setOnClickListener(this);
		mIv_accessory[3].setOnClickListener(this);
		mIv_accessory[4].setOnClickListener(this);
		mIv_accessory[5].setOnClickListener(this);
		mIv_accessory[6].setOnClickListener(this);
		mIv_accessory[7].setOnClickListener(this);
		mIv_accessory[8].setOnClickListener(this);

	}

	@SuppressWarnings("unchecked")
	private void getData() {
		Intent intent = getIntent();

		dialog = new LoadingDialog(MoreStaffAspDetailActivity.this);
		dialog.setTvMessage("????????????...");
		if (!isFinishing()) {
			dialog.show(true);
		}
		System.out.println("getdata dialog exist");
		String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("userid", userid);
		ajaxParams.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
		ajaxParams.put("method", "promble_detail");
		ajaxParams.put("pro_id", intent.getStringExtra("pid"));
		System.out.println(HttpUtils.URL + "?" + ajaxParams.toString());
		FinalHttp fh = new FinalHttp();
		fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
		fh.post(HttpUtils.URL, ajaxParams, new AjaxCallBack() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if (StringUtil.isNotEmpty(strMsg))
					strMsg = replaceErroStr(strMsg);
				super.onFailure(t, errorNo, strMsg);
				strMsg = "????????????";
				Toast.makeText(getApplicationContext(), strMsg, 1000).show();
				if (MoreStaffAspDetailActivity.this == null) {
					return;
				}
				dialog.dismiss();
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);

			}

			@SuppressWarnings("unused")
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				if (MoreStaffAspDetailActivity.this == null) {
					return;
				}
				System.out.println(t.toString());
				JsonInfoV3 jsonInfo = null;
				try {
					jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
				}
				// JsonInfo jsonInfo = jsonInfov2.getResultData();
				dialog.dismiss();
				if (jsonInfo != null) {
					if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
						PrombleDetailExp mExp = jsonInfo.getResultDataToClass(PrombleDetailExp.class);
						List<PrombleDetailData> mlistData = mExp.assessData;// ????????????
						// List<PrombleDetailData2> mlistData2 =
						// mExp.handleData;// ????????????
						// TODO ????????????
						List<ProbleDetailAttachmentData> mlistData3 = mExp.attachmentData;
						// listKeyValue=jsonInfo.getDataList2(ProcessKeyValue.class);//
						// ????????????
						if (mExp != null) {
							mTv_name.setText(StringUtil.noNull(mExp.name));// ????????????
							mTv_id.setText(StringUtil.noNull(mExp.userid));// ????????????
							mTv_commu.setText(StringUtil.noNull(mExp.phone));// ????????????
							mTv_ks.setText(StringUtil.noNull(mExp.dep));// ??????
							mTv_type.setText(StringUtil.noNull(mExp.pro_type));// ????????????
							mTv_key.setText(StringUtil.noNull(mExp.keywords));// ?????????
							mTv_content.setText(StringUtil.noNull(mExp.pro_content));// ??????
							mTv_reply.setText(StringUtil.noNull(mExp.pro_reply));// ????????????
						} else {
							mTv_name.setText("0");
							mTv_id.setText("0");
							mTv_commu.setText("0");
							mTv_ks.setText("0");
							mTv_type.setText("0");
							mTv_key.setText("0");
							mTv_content.setText("0");
							mTv_reply.setText("0");// ????????????
							Toast.makeText(getApplicationContext(), "???????????????", 1000).show();// ???????????????????????????
						}
						if (mlistData.size() > 0) {
							adapter1 = new MoreStaffAspDetailAdapter1(MoreStaffAspDetailActivity.this, mlistData);// ????????????
							mListview.setAdapter(adapter1);
						} else {
							Toast.makeText(MoreStaffAspDetailActivity.this, "?????????????????????", 1000).show();
						}
						if (mlistData3.size() > 0) {
							// TODO ?????????
							// TODO ????????????
							for (int i = 0; i < mlistData3.size(); i++) {
								ImageLoader imageLoader = ImageLoader.getInstance();
								DisplayImageOptions options = Options.getListOptions();
								String strurl = HttpUtils.URL_PIC_ROOT + StringUtil.noNull(mlistData3.get(i).attachment_img, "");
								imageLoader.displayImage(StringUtil.noNull(strurl, ""), mIv_accessory[i], options);
								mIv_accessory[i].setVisibility(View.VISIBLE);
								urlList.add(i, strurl);
							}
						} else {
							Toast.makeText(getApplicationContext(), "?????????????????????", 1000).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), jsonInfo.getResultDesc(), 1000).show();// ??????????????????
					}
				}
			}
		});
	}

	public void onClick(View view) {

		Intent intent = new Intent(this, ShowPhotoActivity.class);
		switch (view.getId()) {
		case R.id.iv_accessory2:
			intent.putExtra("strurl", urlList.get(0).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory3:
			intent.putExtra("strurl", urlList.get(1).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory4:
			intent.putExtra("strurl", urlList.get(2).toString());
			startActivity(intent);
			break;

		case R.id.iv_accessory5:
			intent.putExtra("strurl", urlList.get(3).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory6:
			intent.putExtra("strurl", urlList.get(4).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory7:
			intent.putExtra("strurl", urlList.get(5).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory8:
			intent.putExtra("strurl", urlList.get(6).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory9:
			intent.putExtra("strurl", urlList.get(7).toString());
			startActivity(intent);
			break;
		case R.id.iv_accessory10:
			intent.putExtra("strurl", urlList.get(8).toString());
			startActivity(intent);
			break;

		default:
			break;

		}

		int id = view.getId();
		if (id == R.id.head_left_btn) {// ??????
			MoreStaffAspDetailActivity.this.finish();
		}
	}

	private void showPhoto(String url) {
		LayoutInflater inflater = LayoutInflater.from(MoreStaffAspDetailActivity.this);

		View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // ??????????????????????????????

		final AlertDialog dialog = new AlertDialog.Builder(MoreStaffAspDetailActivity.this, R.style.dialog_transparent).create();

		ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);

		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = Options.getListOptions();
		imageLoader.displayImage(StringUtil.noNull(url, ""), img, options);// ?????????????????????????????????????????????????????????????????????

		dialog.setView(imgEntryView, 0, 0, 0, 0); // ?????????dialog

		dialog.show();

		// ???????????????????????????????????????????????????????????????dialog????????????dialog???????????????

		imgEntryView.setOnClickListener(new OnClickListener() {

			public void onClick(View paramView) {

				dialog.cancel();

			}

		});
	}
}
