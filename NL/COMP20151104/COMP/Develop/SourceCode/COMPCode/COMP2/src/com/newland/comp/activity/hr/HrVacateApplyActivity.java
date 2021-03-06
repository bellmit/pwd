package com.newland.comp.activity.hr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.comp.activity.BaseActivity;
import com.newland.comp.activity.my.MyPersonListNoSearchActivity;
import com.newland.comp.bean.hr.LeaveData;
import com.newland.comp.bean.my.EffictData;
import com.newland.comp.bean.my.EffictData2;
import com.newland.comp.bean.my.EffictDataExp;
import com.newland.comp.bean.my.PersonList;
import com.newland.comp.bean.process.SubmitLeaveBean;
import com.newland.comp.utils.ActivityUtil;
import com.newland.comp.utils.DateTimePickDialogUtil;
import com.newland.comp.utils.HttpUtils;
import com.newland.comp.utils.JsonInfo;
import com.newland.comp.utils.JsonInfoV3;
import com.newland.comp.utils.MD5Utils;
import com.newland.comp.utils.SharedPreferencesUtils;
import com.newland.comp.utils.StringUtil;
import com.newland.comp.view.DatePickDialog;
import com.newland.comp.view.LoadingDialog;
import com.newland.comp2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * ??????-??????????????????
 * 
 * @author H81
 *
 *         2015???4???24??? ??????10:09:54
 * @version 1.0.0
 */
public class HrVacateApplyActivity extends BaseActivity {
	// ???
	private TextView mTv_total;// ????????????
	private TextView mTv_apply;// ???????????????
	private TextView mTv_usable;// ????????????

	private Spinner mSpinner_daiban;// ????????????------------???*
	private Spinner mSpinner_type;// ????????????
	private Spinner mSpinner_leave;// ?????????????????????
	private EditText mTv_userno;// ????????????
	private EditText mTv_username;// ????????????
	private EditText mEt_usersection;// ????????????------------???*
	private EditText mDurTime;// ????????????
	private RadioGroup mRadioGroup;// ????????????
	private RadioButton mRadiobtn_day;// ????????????--???
	private RadioButton mRadiobtn_hours;// ????????????--??????
	private TextView mSpinner_starttime;// ????????????
	private TextView mSpinner_endtime;// ????????????
	private Spinner mSpinner_leavereason;// ????????????
	private EditText mEt_otherreason;// ????????????-------------???*

	private String radiounit = "day";// ?????????????????????
	private String str_durTime;// ????????????

	List<LeaveData> list;// LeaveData
	// ???????????????
	private String[] commission_id;
	private String[] commission_val;
	private String[] leave_type_id;
	private String[] leave_type_val;
	private String[] outside_id;
	private String[] outside_val;

	private List<String> reason_id_list;
	private List<String> reason_val_list;
	LeaveData dataExp;

	LoadingDialog dialog;
	private String next_userid = ""; // ???????????????
	private String flow_code = ""; // ????????????
	private String mis = "";// ???????????????id?????????????????????????????????id

	private int inputNum = 500;
	boolean isDurTimeRight=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vacate);
		setTitle();
		bindViews();
		reflush();
		CURRENTACTIVITY="HrVacateApplyActivity";
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
		if (left_btn != null) // ??????
		{
			left_btn.setVisibility(View.VISIBLE);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null)// ??????
		{
			right_tv.setVisibility(View.GONE);
		}
	}

	/**
	 * ???????????? ?????????????????????
	 */
	@Override
	public void reflush() {
		// updateDurTime();

		dialog = new LoadingDialog(HrVacateApplyActivity.this);
		dialog.setTvMessage("????????????...");
		if (!isFinishing()) {dialog.show(true);}
		System.out.println("reflush dialog exist");
		String userid = SharedPreferencesUtils.getConfigStr(this, BaseActivity.CASH_NAME, "userid");
		AjaxParams params = new AjaxParams();
		params.put("userid", userid);// ??????
		params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
		params.put("method", "getleaveData");
		System.out.println(HttpUtils.URL + "?" + params.toString());
		FinalHttp fh = new FinalHttp();
		fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
		fh.post(HttpUtils.URL, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {if(StringUtil.isNotEmpty(strMsg))strMsg=replaceErroStr(strMsg);
				super.onFailure(t, errorNo, strMsg);
				if (HrVacateApplyActivity.this==null) {
					return;
				}
				dialog.dismiss();
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				if (HrVacateApplyActivity.this==null) {
					return;
				}
				System.out.println(t.toString());
				dialog.dismiss();
				JsonInfoV3 jsonInfo = null;
				try {
					jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
				}

				if (jsonInfo != null) {
					if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) // ??????????????????
					{
						dataExp = jsonInfo.getResultDataToClass(LeaveData.class);
						if (dataExp != null) {
							commission_id = StringUtil.noNull(dataExp.commission_id).split(",");
							commission_val = StringUtil.noNull(dataExp.commission_val).split(",");
							leave_type_id = StringUtil.noNull(dataExp.leave_type_id).split(",");
							leave_type_val = StringUtil.noNull(dataExp.leave_type_val).split(",");
							outside_id = StringUtil.noNull(dataExp.outside_id).split(",");
							outside_val = StringUtil.noNull(dataExp.outside_val).split(",");
							String[] reason_id = StringUtil.noNull(dataExp.reason_id).split(",");
							String[] reason_val = StringUtil.noNull(dataExp.reason_val).split(",");

							reason_id_list = new ArrayList<String>();
							reason_val_list = new ArrayList<String>();
							Collections.addAll(reason_id_list, reason_id);
							Collections.addAll(reason_val_list, reason_val);
							reason_id_list.add("????????????(????????????)");
							reason_val_list.add("????????????(????????????)");

							ActivityUtil.showDropDown(HrVacateApplyActivity.this, mSpinner_daiban, commission_val, R.layout.simple_spinner_item);
							ActivityUtil.showDropDown(HrVacateApplyActivity.this, mSpinner_type, leave_type_val, R.layout.simple_spinner_item);
							ActivityUtil.showDropDown(HrVacateApplyActivity.this, mSpinner_leave, outside_val, R.layout.simple_spinner_item);
							ActivityUtil.showDropDown(HrVacateApplyActivity.this, mSpinner_leavereason, reason_val_list, R.layout.simple_spinner_item);

							mTv_total.setText(StringUtil.noNull(dataExp.year_hol));// ????????????
							mTv_apply.setText(StringUtil.noNull(dataExp.used_hol));// ???????????????
							mTv_usable.setText(StringUtil.noNull(dataExp.ava_hol));// ????????????

							mTv_userno.setText(StringUtil.noNull(dataExp.userno));// mis
							mTv_username.setText(StringUtil.noNull(dataExp.username));// ?????????
							mEt_usersection.setText(StringUtil.noNull(dataExp.dep));// ????????????

						} else {
						}

					} else {
						Toast.makeText(HrVacateApplyActivity.this, "????????????????????????" + jsonInfo.getResultDesc(), 1000).show();// ????????????????????????
					}
				}
			}
		});
	}

	/**
	 * ???????????????
	 */
	private void bindViews() {
		mTv_total = (TextView) findViewById(R.id.tv_total);
		mTv_apply = (TextView) findViewById(R.id.tv_apply);
		mTv_usable = (TextView) findViewById(R.id.tv_usable);
		mDurTime = (EditText) findViewById(R.id.durtime);// ????????????
		mSpinner_daiban = (Spinner) findViewById(R.id.spinner_daiban);
		mSpinner_type = (Spinner) findViewById(R.id.spinner_type);
		mSpinner_leave = (Spinner) findViewById(R.id.spinner_leave);
		mTv_userno = (EditText) findViewById(R.id.tv_userno);
		mTv_username = (EditText) findViewById(R.id.tv_username);
		mEt_usersection = (EditText) findViewById(R.id.et_usersection);
		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		mRadiobtn_day = (RadioButton) findViewById(R.id.radiobtn_day);
		mRadiobtn_hours = (RadioButton) findViewById(R.id.radiobtn_hours);
		mSpinner_starttime = (TextView) findViewById(R.id.spinner_starttime);
		mSpinner_starttime.setText(StringUtil.getNowTime(StringUtil.DAY_TIME));
		mSpinner_starttime.setOnClickListener(new OnClickListener() {

		
			public void onClick(View v) {
				timeDialog(mSpinner_starttime);
			}
		});
		mSpinner_endtime = (TextView) findViewById(R.id.spinner_endtime);
		mSpinner_endtime.setText(StringUtil.getNowTime(StringUtil.DAY_TIME));
		mSpinner_endtime.setOnClickListener(new OnClickListener() {

		
			public void onClick(View v) {
				timeDialog(mSpinner_endtime);
			}
		});


		mSpinner_leavereason = (Spinner) findViewById(R.id.spinner_leavereason);
		mEt_otherreason = (EditText) findViewById(R.id.et_otherreason);
		addLinster();
	}

	/**
	 * ?????????????????????
	 * 
	 * @param t
	 *            textView??????
	 */
	private void timeDialog(TextView t) {
		if (mRadioGroup.getCheckedRadioButtonId() == R.id.radiobtn_day)// ???
		{
			if ((new DatePickDialog(HrVacateApplyActivity.this, DatePickDialog.YEAR_MONTH_DAY).datePicKDialog(t, HrVacateApplyActivity.this).toString()).equals(null))// ???????????????
			{
				t.setText(StringUtil.getNowTime(StringUtil.DAY_TIME));
			}
		} else if ((new DateTimePickDialogUtil(HrVacateApplyActivity.this, null).dateTimePicKDialog(t, HrVacateApplyActivity.this).toString()).equals(null)) // ?????????????????????????????????
		{
			t.setText(StringUtil.getNowTime(StringUtil.MINUTE_TIME));

		}
	}

	private void addLinster() {
		// ????????????????????????
		if (mRadioGroup != null) {
			mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					if (checkedId == mRadiobtn_day.getId()) {
						radiounit = "day";
						mSpinner_starttime.setText("");
						mSpinner_endtime.setText("");
					}

					if (checkedId == mRadiobtn_hours.getId()) {
						radiounit = "hour";
						mSpinner_starttime.setText("");
						mSpinner_endtime.setText("");
					}
				}
			});
		}
		// ?????????????????????
		mSpinner_daiban.setOnItemSelectedListener(new OnItemSelectedListener() {

		
			public void onItemSelected(AdapterView<?> arg0, View view, int poistion, long arg3) {

				if (commission_val[poistion].equals("???")) // ??????????????????
				{
					mTv_userno.setEnabled(true);// mis
					mTv_username.setText("");// ?????????
					mEt_usersection.setText("");// ????????????
				} else// ???????????????
				{
					mTv_userno.setEnabled(false);// mis
					if (dataExp != null) {
						mTv_userno.setText(StringUtil.noNull(dataExp.userno)); // ????????????
						mTv_username.setText(StringUtil.noNull(dataExp.username));// ?????????
						mEt_usersection.setText(StringUtil.noNull(dataExp.dep));// ????????????
						String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
						mis = userid;
					}

				}
			}

		
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// ????????????????????????????????????????????????????????????????????????????????????
		mSpinner_leavereason.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			public void onItemSelected(AdapterView<?> arg0, View arg1, int poistion, long arg3) {
				if (poistion == (reason_id_list.size() - 1)) {
					mEt_otherreason.setEnabled(true);
				} else {
					mEt_otherreason.setEnabled(false);
					mEt_otherreason.setText("");
				}
			}

		
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// ?????????????????????????????????????????????
		mTv_userno.setOnFocusChangeListener(new OnFocusChangeListener() {

		
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1) // ????????????
				{
					String userid = SharedPreferencesUtils.getConfigStr(getApplicationContext(), BaseActivity.CASH_NAME, "userid");
					AjaxParams params = new AjaxParams();
					params.put("userid", mTv_userno.getText().toString());// ??????
					params.put("signid", MD5Utils.getInstance().getUserIdSignid(mTv_userno.getText().toString()));
					params.put("method", "getdepName");
					System.out.println(HttpUtils.URL + "?" + params.toString());
					FinalHttp fh = new FinalHttp();
					fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
					fh.post(HttpUtils.URL, params, new AjaxCallBack<Object>() {

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {if(StringUtil.isNotEmpty(strMsg))strMsg=replaceErroStr(strMsg);
							super.onFailure(t, errorNo, strMsg);
							if (HrVacateApplyActivity.this==null) {
								return;
							}
						}

						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
						}

						@Override
						public void onSuccess(Object t) {
							if (HrVacateApplyActivity.this==null) {
								return;
							}
							System.out.println(t.toString());
							super.onSuccess(t);

							JsonInfoV3 jsonInfo = null;
							try {
								jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
							} catch (Exception e) {
								Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
							}
							if (jsonInfo != null) {
								if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
									JSONObject object = jsonInfo.getResultDataToJsonObject();
									if (object != null) {
										mTv_username.setText(StringUtil.noNull(object.getString("username")));// ?????????
										mEt_usersection.setText(StringUtil.noNull(object.getString("user_dep")));// ????????????
										mis = mTv_userno.getText().toString(); // ????????????id
									}
								} else {
									Toast.makeText(getApplicationContext(), "???????????????????????????:" + jsonInfo.getResultDesc(), 1000).show();// ????????????????????????
								}
							}
						}
					});
				}
			}
		});

		// ?????????????????????
		mEt_otherreason.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

		
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				temp = s;
				System.out.println("s=" + s);
			}

			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

		
			public void afterTextChanged(Editable s) {
				int number = inputNum - s.length();
				selectionStart = mEt_otherreason.getSelectionStart();
				selectionEnd = mEt_otherreason.getSelectionEnd();
				if (temp.length() > inputNum) {
					Toast.makeText(getApplicationContext(), "??????????????????" + inputNum + "??????", Toast.LENGTH_SHORT).show();
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionStart;
					mEt_otherreason.setText(s);
					mEt_otherreason.setSelection(tempSelection);// ?????????????????????
				}
			}
		});

	}


	/**
	 * ????????????????????????
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void postData() throws UnsupportedEncodingException {
		dialog = new LoadingDialog(HrVacateApplyActivity.this);
		dialog.setTvMessage("????????????...");
		if (!isFinishing()) {dialog.show(true);}
		System.out.println("postdata dialog exist");
		String userid = SharedPreferencesUtils.getConfigStr(this, BaseActivity.CASH_NAME, "userid");
		String s = mSpinner_starttime.getText().toString();

		String str_starttime = mSpinner_starttime.getText().toString();
		String str_endtime = mSpinner_endtime.getText().toString();
		AjaxParams params = new AjaxParams();
		params.put("userid", userid);// ??????
		params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
		params.put("method", "submit_leave");
		params.put("commission_id", commission_id[mSpinner_daiban.getSelectedItemPosition()]);// ?????????????????????id
		params.put("leave_type_id", leave_type_id[mSpinner_type.getSelectedItemPosition()]);// ????????????
		params.put("outside_id", outside_id[mSpinner_leave.getSelectedItemPosition()]);// ???????????????????????????id
		params.put("leave_time", mDurTime.getText().toString() + "," + radiounit);// ????????????
																					// ???????????????????????????
																					// 2,hour
																					// (2??????)
																					// 2,day(2???)
		params.put("start_time", str_starttime);// ????????????
		params.put("end_time", str_endtime);// ????????????
		params.put("reason_id", reason_id_list.get(mSpinner_leavereason.getSelectedItemPosition()));// ????????????id
		params.put("others_reason", mEt_otherreason.getText().toString());// ????????????
		params.put("next_userid", next_userid);// ???????????????
		params.put("flow_code", StringUtil.noNull(flow_code));//
		params.put("mis", mis);// ???????????????id?????????????????????????????????id

		FinalHttp fh = new FinalHttp();
		fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);
		fh.configCharset("UTF-8");
		System.out.println(HttpUtils.URL + "?" + params.toString());
		fh.post(HttpUtils.URL, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {if(StringUtil.isNotEmpty(strMsg))strMsg=replaceErroStr(strMsg);
				super.onFailure(t, errorNo, strMsg);
				if (HrVacateApplyActivity.this==null) {
					return;
				}
				dialog.dismiss();
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(Object t) {
				if (HrVacateApplyActivity.this==null) {
					return;
				}
				dialog.dismiss();
				System.out.println(t.toString());
				super.onSuccess(t);
				JsonInfoV3 jsonInfo = null;
				try {
					jsonInfo = JSON.parseObject(t.toString(), JsonInfoV3.class);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "??????????????????????????????????????????????????????", 1000).show();
				}
				if (jsonInfo != null) {
					if (JsonInfo.SUCCESS_CODE.equals(jsonInfo.getResultCode())) {// ??????????????????
						if (StringUtil.isEmpty(next_userid))// ???????????????????????????????????????????????????
						{

							SubmitLeaveBean submitLeaveBean = jsonInfo.getResultDataToClass(SubmitLeaveBean.class);
							flow_code = submitLeaveBean.flow_code;
							ArrayList<PersonList> listPerson = (ArrayList<PersonList>) submitLeaveBean.userList;
							if (listPerson.size() > 0) {

								String startTime = mSpinner_starttime.getText().toString();
								String endTime = mSpinner_endtime.getText().toString();
								long durTime = 0;// ????????????

								if (radiounit == "day") {
									try {
										long m1 = (new SimpleDateFormat(StringUtil.DAY_TIME).parse(startTime).getTime());// ??????????????????????????????
										long m2 = (new SimpleDateFormat(StringUtil.DAY_TIME).parse(endTime).getTime());// ??????????????????????????????
										durTime = (m2 + 1000 * 60 * 60 * 24 - m1) / (1000 * 60 * 60 * 24);// ????????????????????????
										if (durTime != Long.parseLong(mDurTime.getText().toString())) {
											isDurTimeRight = false;
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
								} else if (radiounit == "hour") {
									try {
										long m1 = (new SimpleDateFormat(StringUtil.MINUTE_TIME).parse(startTime).getTime());// ??????????????????????????????
										long m2 = (new SimpleDateFormat(StringUtil.MINUTE_TIME).parse(endTime).getTime());// ??????????????????????????????
										durTime = (m2 - m1) / (1000 * 60 * 60);
										if (durTime != Long.parseLong(mDurTime.getText().toString())) {
											isDurTimeRight = false;
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}

								Intent intent = new Intent(HrVacateApplyActivity.this, MyPersonListNoSearchActivity.class);
								intent.putExtra("list", listPerson);
								startActivityForResult(intent, 1);
							}
						} else {
							Toast.makeText(HrVacateApplyActivity.this, "????????????", Toast.LENGTH_SHORT).show();
							finish();
							CURRENTACTIVITY="";
						}

					} else {
						Toast.makeText(getApplicationContext(), "????????????:" + jsonInfo.getResultDesc(), 1000).show();// ????????????????????????
					}
				}
			}
		});
	}

	public void onClick(View view) throws UnsupportedEncodingException {
		int id = view.getId();
		if (id == R.id.head_left_btn) {// ??????
			finish();
		}
		if (id == R.id.commit) {// ????????????
			commit();
		}
	}

	/**
	 * ??????
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void commit() throws UnsupportedEncodingException {
		String str_no = mTv_userno.getText().toString();
		String str_name = mTv_username.getText().toString();
		boolean flag = false;
		str_durTime = mDurTime.getText().toString();// ????????????
		if (StringUtil.isEmpty(str_no)) {// ???????????????
			Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (StringUtil.isEmpty(str_name)) {
			Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (StringUtil.isEmpty(str_durTime)) {
			Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (StringUtil.isEmpty(mSpinner_starttime.getText().toString())) {
			Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (StringUtil.isEmpty(mSpinner_endtime.getText().toString())) {
			Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (radiounit.equals("day") && (Float.valueOf(mTv_usable.getText().toString())) < (Float.valueOf(str_durTime))) {// ????????????
			Toast.makeText(getApplicationContext(), "????????????????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (radiounit.equals("hour") && (Float.valueOf(mTv_usable.getText().toString()) * 24) < (Float.valueOf(str_durTime))) {// ???????????????
			Toast.makeText(getApplicationContext(), "????????????????????????????????????", Toast.LENGTH_SHORT).show();
		} else if (radiounit.equals("day")) {
			flag = compareTime(StringUtil.DAY_TIME);
			if (flag) {// ?????????????????????????????????
				postData();
			} else {
				Toast.makeText(getApplicationContext(), "????????????????????????????????????", Toast.LENGTH_SHORT).show();
				return;
			}
		} else if (radiounit.equals("hour")) {
			flag = compareTime(StringUtil.MINUTE_TIME);
			if (flag) {// ?????????????????????????????????
				postData();
			} else {
				Toast.makeText(getApplicationContext(), "????????????????????????????????????", Toast.LENGTH_SHORT).show();
				return;
			}
		}
	}

	/**
	 * ??????????????????????????????????????????
	 */
	private boolean compareTime(String pattern) {
		boolean flag = false;
		try {
			if ((new SimpleDateFormat(pattern).parse(mSpinner_starttime.getText().toString())).before(new SimpleDateFormat(pattern).parse(mSpinner_endtime.getText().toString()))||(mSpinner_starttime.getText().toString().equals(mSpinner_endtime.getText().toString()))) {// ??????????????????>=????????????
				flag = true;
			} else {
				flag = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (Activity.RESULT_OK == resultCode) {

			if (requestCode == 1) {// ?????????
				System.out.println("data:" + data.getStringExtra("userid"));
				next_userid = data.getStringExtra("userid");
				try {
					if (isDurTimeRight==false) {
						Toast.makeText(getApplication(), "????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
					}else{
						postData();
						
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}
		} else if (Activity.RESULT_CANCELED == resultCode) {// ????????????????????????????????????????????????
			flow_code = "";
		}
	}
	public void clickDaiban(View v){
		mSpinner_daiban.performClick();
	}
	public void clickType(View v){
		mSpinner_type.performClick();
	}
	public void clickLeave(View v){
		mSpinner_leave.performClick();
	}
	public void clickLeavereason(View v){
		mSpinner_leavereason.performClick();
	}
}
