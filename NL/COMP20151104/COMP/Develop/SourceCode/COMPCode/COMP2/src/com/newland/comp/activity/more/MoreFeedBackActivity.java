package com.newland.comp.activity.more;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.bither.util.NativeUtil;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.newland.comp.activity.BaseActivity;
import com.newland.comp.activity.indicator.IntegraExchangeDetail_;
import com.newland.comp.bean.JsonInfoV2;
import com.newland.comp.bean.PrombleType;
import com.newland.comp.bean.indicator.IndicatorData;
import com.newland.comp.bean.more.FeedBackData;
import com.newland.comp.bean.more.TreeBean;
import com.newland.comp.utils.ActivityUtil;
import com.newland.comp.utils.Base64Test;
import com.newland.comp.utils.FileHelper;
import com.newland.comp.utils.HttpUtils;
import com.newland.comp.utils.JsonInfo;
import com.newland.comp.utils.JsonInfoV3;
import com.newland.comp.utils.MD5Utils;
import com.newland.comp.utils.SharedPreferencesUtils;
import com.newland.comp.utils.StringUtil;
import com.newland.comp.view.LoadingDialog;
import com.newland.comp2.R;

/**
 * ??????????????????????????????????????????????????????????????????????????????????????????
 * 
 * @author H81
 *
 *         2015???5???5??? ??????3:55:49
 * @version 1.0.0
 */
public class MoreFeedBackActivity extends BaseActivity {
	private static final int PICTURE = 1; // ????????????
	private int picture_index = 0;
	private int a = 0;
	private int successCount = 0;
	private int pictureCount = 0;
	private static final int MAX_LENGTH = 9;
	// ??????
	private ImageButton left_btn;// ??????
	private ImageButton right_btn;
	private TextView right_tv;
	private TextView third_spinner_pulldown;

	private EditText mInput;
	private Spinner first_spinner;
	private Spinner second_spinner;
	private Spinner third_spinner;
	private EditText mFeedback_content;
	private Button mFeedback_btn;

	private ImageView[] pic = new ImageView[MAX_LENGTH];// ????????????
	private ImageView mIv;

	private List<PrombleType> list;// ????????????
	private List<PrombleType> firestLevelList = new ArrayList<PrombleType>(); // ??????????????????
	private List<PrombleType> seconLevelList = new ArrayList<PrombleType>();;
	private List<PrombleType> thridLevelList = new ArrayList<PrombleType>();;

	private String[] mpicturepath = new String[MAX_LENGTH];
	private String[] backId = new String[MAX_LENGTH];
	private String BackID;

	private String top_id;// ????????????id top_id ???????????????
	private String leafType;// ?????????????????? ????????????id
	private boolean isSelected = false;// ?????????????????????????????????
	LoadingDialog dialog;

	private String phtotFilename;// ????????????
	private String photoname;

	private String[] pn = new String[MAX_LENGTH];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_feedback);
		bindViews();
		setTitle();
		reflush();
	}

	private void bindViews() {
		mInput = (EditText) findViewById(R.id.input);
		first_spinner = (Spinner) findViewById(R.id.first_spinner);
		second_spinner = (Spinner) findViewById(R.id.second_spinner);
		third_spinner = (Spinner) findViewById(R.id.third_spinner);
		mFeedback_content = (EditText) findViewById(R.id.feedback_content);
		mFeedback_btn = (Button) findViewById(R.id.feedback_btn);
		third_spinner_pulldown = (TextView) findViewById(R.id.third_spinner_pulldown);

		mIv = (ImageView) findViewById(R.id.iv_accessory1);

		pic[0] = (ImageView) findViewById(R.id.iv_accessory2);
		pic[1] = (ImageView) findViewById(R.id.iv_accessory3);
		pic[2] = (ImageView) findViewById(R.id.iv_accessory4);
		pic[3] = (ImageView) findViewById(R.id.iv_accessory5);
		pic[4] = (ImageView) findViewById(R.id.iv_accessory6);
		pic[5] = (ImageView) findViewById(R.id.iv_accessory7);
		pic[6] = (ImageView) findViewById(R.id.iv_accessory8);
		pic[7] = (ImageView) findViewById(R.id.iv_accessory9);
		pic[8] = (ImageView) findViewById(R.id.iv_accessory10);

		for (a = 0; a < MAX_LENGTH; a++) {
			pic[a].setOnClickListener(new ClickEvent());
		}

		dialog = new LoadingDialog(this);

		mIv.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (picture_index < MAX_LENGTH) {
					showImgFileChooser();
				} else {
					Toast.makeText(getApplicationContext(), "????????????????????????9???", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	class ClickEvent implements View.OnClickListener {

		public void onClick(View v) {
			for (a = 0; a < MAX_LENGTH; a++) {
				if (v == pic[a])
					picture_index = a;
			}
			showImgFileChooser();
		}

	}

	/**
	 * ???????????????
	 */
	private void setTitle() {
		left_btn = (ImageButton) findViewById(R.id.head_left_btn);
		right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
		}
		if (center_tv != null)
			center_tv.setText("????????????");
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}

	/**
	 * ????????????
	 */
	private void addListener() {
		showDropDown(MoreFeedBackActivity.this, first_spinner, firestLevelList, R.layout.simple_spinner_item);

		first_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int poistion, long arg3) {

				// ?????????????????????????????????
				PrombleType prombleType = firestLevelList.get(poistion);
				if ("1".equals(prombleType.isLeaf)) { // ??????????????????
					top_id = prombleType.topId;
					leafType = prombleType.typeId;
					isSelected = true;
					// second_spinner.setVisibility(View.INVISIBLE);
					ActivityUtil.showDropDown(MoreFeedBackActivity.this, second_spinner, new String[0], R.layout.simple_spinner_item);
					ActivityUtil.showDropDown(MoreFeedBackActivity.this, third_spinner, new String[0], R.layout.simple_spinner_item);
					// third_spinner_pulldown.setVisibility(View.GONE);
					return;
				}
				isSelected = false;
				seconLevelList.clear();
				for (PrombleType bean : list) {
					if (prombleType.typeId.equals(bean.parentType) && prombleType.topId.equals(bean.topId)) {// ??????????????????????????????
						seconLevelList.add(bean);
					}
				}
				showDropDown(MoreFeedBackActivity.this, second_spinner, seconLevelList, R.layout.simple_spinner_item);
				// ??????????????????
				thridLevelList.clear();
				ActivityUtil.showDropDown(MoreFeedBackActivity.this, third_spinner, new String[0], R.layout.simple_spinner_item);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		second_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int poistion, long arg3) {
				// ?????????????????????????????????
				PrombleType prombleType = seconLevelList.get(poistion);
				if ("1".equals(prombleType.isLeaf)) { // ??????????????????
					top_id = prombleType.topId;
					leafType = prombleType.typeId;
					isSelected = true;
					// ??????????????????
					ActivityUtil.showDropDown(MoreFeedBackActivity.this, third_spinner, new String[0], R.layout.simple_spinner_item);
					return;
				}

				isSelected = false; // ?????????????????????
				thridLevelList.clear();
				for (PrombleType bean : list) {
					if (prombleType.typeId.equals(bean.parentType) && prombleType.topId.equals(bean.topId)) {// ??????????????????????????????
						thridLevelList.add(bean);
					}
				}
				showDropDown(MoreFeedBackActivity.this, third_spinner, thridLevelList, R.layout.simple_spinner_item);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		third_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int poistion, long arg3) {

				PrombleType prombleType = thridLevelList.get(poistion);
				top_id = prombleType.topId;
				leafType = prombleType.typeId;
				isSelected = true;
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		mFeedback_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (StringUtil.isEmpty(mInput.getText().toString())) {
					Toast.makeText(MoreFeedBackActivity.this, "????????????", Toast.LENGTH_SHORT).show();
					return;
				} else if (StringUtil.isEmpty(mFeedback_content.getText().toString())) {
					Toast.makeText(MoreFeedBackActivity.this, "????????????", Toast.LENGTH_SHORT).show();
					return;
				} else {
					postPicture();

				}
			}
		});
	}

	/**
	 * ????????????????????????
	 * 
	 * userid;//??????
	 * 
	 * signid: md5(userid+'comp')
	 * 
	 * method???class_search
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void reflush() {
		dialog.setTvMessage("????????????...");
		if (!isFinishing()) {dialog.show(true);}
		String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
		AjaxParams params = new AjaxParams();
		params.put("userid", userid);
		params.put("method", "promble_getType");
		params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));

		System.out.println(HttpUtils.URL + "?" + params.toString());
		FinalHttp fh = new FinalHttp();
		fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
		fh.post(HttpUtils.URL, params, new AjaxCallBack() {
			@Override
			public void onLoading(long count, long current) {
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if (StringUtil.isNotEmpty(strMsg))
					strMsg = replaceErroStr(strMsg);
				if (MoreFeedBackActivity.this==null) {
					return;
				}
				dialog.dismiss();
				strMsg = "????????????";
				Toast.makeText(getApplicationContext(), strMsg, 1000).show();
			}

			@Override
			public void onSuccess(Object t) {
				if (MoreFeedBackActivity.this==null) {
					return;
				}
				dialog.dismiss();
				System.out.println("promble_getType   " + t.toString());
				JsonInfoV3 jsonInfo = null;
				try {
					jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
				}

				if (jsonInfo != null) {
					if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
						try {
							list = jsonInfo.getResultDataKeyToClass("data", PrombleType.class);

						} catch (Exception e) {

						}
						if (list == null) {
							System.out.println("list  ====  null");
							return;
						}
						firestLevelList.clear();
						for (PrombleType prombleType : list) {
							if ("0".equals(prombleType.parentType)) {// ????????????
								firestLevelList.add(prombleType);
							}
						}
						addListener();

					} else {
						Toast.makeText(getApplicationContext(), jsonInfo.getResultDesc(), 1000).show();// ??????????????????
					}
				}
			}
		});

	}

	/**
	 * ????????????
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void postData() {
		try {
			String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
			AjaxParams params = new AjaxParams();
			params.put("userid", userid);
			params.put("method", "promble_submit");
			params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
			params.put("title", StringUtil.noNull(mInput.getText().toString()));
			params.put("type", StringUtil.noNull(top_id));
			params.put("leafType", StringUtil.noNull(leafType));
			params.put("content", StringUtil.noNull(mFeedback_content.getText().toString()));
			for (a = 0; a < MAX_LENGTH; a++) {
				if (!StringUtil.isEmpty(pn[a]) || !StringUtil.isEmpty(mpicturepath[a])) {
					if (a == 0) {
						BackID = backId[a];
					} else {
						BackID = BackID + "," + backId[a];
					}
				}
			}

			if (!StringUtil.isEmpty(BackID)) {
				params.put("bizIds", BackID);
			} else {
				params.put("bizIds", "");
			}

			System.out.println(HttpUtils.URL + "?" + params.toString());
			FinalHttp fh = new FinalHttp();
			fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
			fh.post(HttpUtils.URL, params, new AjaxCallBack() {
				@Override
				public void onLoading(long count, long current) {
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					if (StringUtil.isNotEmpty(strMsg))
						strMsg = replaceErroStr(strMsg);
					if (MoreFeedBackActivity.this==null) {
						return;
					}
					dialog.dismiss();
					strMsg = "????????????";
					Toast.makeText(getApplicationContext(), strMsg, 1000).show();
				}

				@Override
				public void onSuccess(Object t) {
					if (MoreFeedBackActivity.this==null) {
						return;
					}
					dialog.dismiss();
					System.out.println("promble_submit  " + t.toString());
					JsonInfoV3 jsonInfo = null;
					try {
						jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
					}

					if (jsonInfo != null) {
						if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
							Toast.makeText(getApplicationContext(), jsonInfo.getResultDesc(), 1000).show();
							MoreFeedBackActivity.this.finish();

						} else {
							Toast.makeText(getApplicationContext(), jsonInfo.getResultDesc(), 1000).show();// ??????????????????
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ????????????
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void postPicture() {
		if (!isSelected) {
			Toast.makeText(getApplicationContext(), "??????????????????!", 1000).show();
			return;
		}
		successCount = 0;
		dialog.setTvMessage("????????????...");
		if (!isFinishing()) {dialog.show(true);}
		String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
		AjaxParams params = new AjaxParams();
		params.put("userid", userid);
		params.put("method", "attachment_upload");
		params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
		params.put("module", "01");

		try {

			if (pictureCount == 0) {
				postData();
			} else {
				for (a = 0; a < MAX_LENGTH; a++) {
					if (!StringUtil.isEmpty(pn[a]) || !StringUtil.isEmpty(mpicturepath[a])) {
						photoname = pn[a];
						phtotFilename = Base64Test.GetImageStr(mpicturepath[a]);
						System.out.println("==============================="+phtotFilename);
						params.put("fileName", StringUtil.noNull(photoname));
						if (!StringUtil.isEmpty(phtotFilename)) {
							params.put("accessory", phtotFilename);
						} else {
							params.put("accessory", "");
						}

						System.out.println(HttpUtils.URL + "?" + params.toString());

						FinalHttp fh = new FinalHttp();
						fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
						fh.post(HttpUtils.URL, params, new AjaxCallBack() {
							@Override
							public void onLoading(long count, long current) {
							}

							@Override
							public void onFailure(Throwable t, int errorNo, String strMsg) {
								if (MoreFeedBackActivity.this==null) {
									return;
								}
								dialog.dismiss();
								strMsg = "????????????";
								Toast.makeText(getApplicationContext(), strMsg, 1000).show();
							}

							@Override
							public void onSuccess(Object t) {
								if (MoreFeedBackActivity.this==null) {
									return;
								}
								Log.e("System.out", t.toString());
								JsonInfoV3 jsonInfo = null;
								try {
									jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
								} catch (Exception e) {
									dialog.dismiss();
									Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
								}

								if (jsonInfo != null) {
									if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
										FeedBackData fbd = jsonInfo.getResultDataToClass(FeedBackData.class);
										backId[successCount] = fbd.getBizId();
										successCount++;
										if (successCount == pictureCount) {
											postData();
										}

									} else {
										Toast.makeText(getApplicationContext(), jsonInfo.getResultDesc(), 1000).show();// ??????????????????
									}
								}
							}
						});
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.head_left_btn) {// ??????
			finish();
		}
	}

	/** ??????????????????????????????????????? **/
	private void showImgFileChooser() {
		Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, PICTURE);
	}

	/** ??????????????????????????????????????????????????? **/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PICTURE) {
				Uri selectedImage = data.getData();
				String[] filePathColumns = { MediaStore.Images.Media.DATA };
				Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				String picturePath = c.getString(columnIndex);
				c.close();

				// //????????????
				try {

					BitmapFactory.Options options2 = new BitmapFactory.Options();
					options2.inSampleSize = 2;
					InputStream inputStream = new FileInputStream(new File(picturePath));
					phtotFilename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/comp/compressphoto";
					photoname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
					phtotFilename = phtotFilename + File.separator + photoname;
					FileHelper.createFile(new File(phtotFilename));

					Bitmap bitmapOri = BitmapFactory.decodeFile(picturePath, options2);
					NativeUtil.compressBitmap(bitmapOri, 100, phtotFilename, true);

					// ?????????????????????
					Bitmap bitmap = BitmapFactory.decodeFile(phtotFilename);
					// ????????????

					if (StringUtil.isEmpty(pn[picture_index])) {
						pictureCount++;
					}
					pn[picture_index] = photoname;
					mpicturepath[picture_index] = picturePath;
					pic[picture_index].setImageBitmap(bitmap);
					pic[picture_index + 1].setVisibility(View.VISIBLE);

					picture_index++;

					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ????????????
	 * 
	 * @throws IOException
	 */
	public void takePhoto() throws IOException {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {

			phtotFilename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/comp/photo";
			phtotFilename = phtotFilename + File.separator + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
			FileHelper.createFile(new File(phtotFilename));
			Uri uri = Uri.fromFile(new File(phtotFilename));

			// ????????????
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(intent, 1);

		} else {
			Toast.makeText(MoreFeedBackActivity.this, "sd????????????", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * ??????????????? ???????????????
	 * 
	 * @param context
	 * @param spinner
	 * @param adp
	 * @param adpres
	 */
	public static void showDropDown(Context context, Spinner spinner, List<PrombleType> list, int simpleSpinnerItem) {
		List<String> firNames = new ArrayList<String>();
		for (PrombleType bean : list) {
			firNames.add(StringUtil.noNull(bean.typeName));
		}
		ArrayAdapter<String> adp = new ArrayAdapter<String>(context, simpleSpinnerItem, firNames);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adp);
		spinner.setVisibility(View.VISIBLE);

	}

	public void clickThird_spinner(View v) {
		third_spinner.performClick();
	}

	public void clickfirstspinner(View v) {
		first_spinner.performClick();
	}

	public void clicksecondspinner(View v) {
		second_spinner.performClick();
	}
}
