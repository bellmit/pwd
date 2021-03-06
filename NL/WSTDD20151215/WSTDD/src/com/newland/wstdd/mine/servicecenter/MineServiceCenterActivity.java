package com.newland.wstdd.mine.servicecenter;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;

/**
 * 服务中心界面
 * 
 * @author Administrator 2015-12-9
 */
public class MineServiceCenterActivity extends BaseFragmentActivity implements OnClickListener {
	AppContext appContext = AppContext.getAppContext();
	
	private ImageView mService_headimg_iv;//头像
	private TextView mService_kefu_tv;//客服 我是团太太
	private TextView mService_time_tv;//当班时间
	private com.newland.wstdd.common.view.RoundAngleImageView mService_twocode_iv;//二维码图片
	private TextView mService_weixin_tv;//微信号
	private TextView mService_weixintwocode_tv;//微信扫描二维码
	private TextView mService_qq_tv;//qq
	private com.newland.wstdd.common.widget.PengTextView mService_tel_ptv;//tel
	private com.newland.wstdd.common.widget.PengTextView mService_phone_ptv;//phone
	private TextView mService_email_tv;//email

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_servicecenter);
		setTitle();
		initView();
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		ImageButton rightBtn = (ImageButton) findViewById(R.id.head_right_btn);
		leftBtn.setVisibility(View.VISIBLE);
		centerTitle.setText("服务中心");
		// rightTv.setText("发布");
		rightTv.setVisibility(View.GONE);
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setImageDrawable(getResources().getDrawable(R.drawable.feedback_red));
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}
	@Override
	protected void processMessage(Message msg) {
	}

	@Override
	public void initView() {
		mService_headimg_iv = (ImageView) findViewById(R.id.service_headimg_iv);
		mService_kefu_tv = (TextView) findViewById(R.id.service_kefu_tv);
		mService_time_tv = (TextView) findViewById(R.id.service_time_tv);
		mService_twocode_iv = (com.newland.wstdd.common.view.RoundAngleImageView) findViewById(R.id.service_twocode_iv);
		mService_weixin_tv = (TextView) findViewById(R.id.service_weixin_tv);
		mService_weixintwocode_tv = (TextView) findViewById(R.id.service_weixintwocode_tv);
		mService_qq_tv = (TextView) findViewById(R.id.service_qq_tv);
		mService_tel_ptv = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.service_tel_ptv);
		mService_phone_ptv = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.service_phone_ptv);
		mService_email_tv = (TextView) findViewById(R.id.service_email_tv);

		mService_headimg_iv.getLayoutParams().width = appContext.getScreenWidth()/4 + 10;
		mService_headimg_iv.getLayoutParams().height = appContext.getScreenWidth()/4 +10;
		
		mService_twocode_iv.getLayoutParams().height = appContext.getScreenWidth()/3 +20;
		mService_twocode_iv.getLayoutParams().width = appContext.getScreenWidth()/3 +20;
		mService_twocode_iv.setImageDrawable(getResources().getDrawable(R.drawable.test_tuantaitai_weixincode));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv:
			finish();
			break;
		case R.id.head_right_btn:
			
			break;
		default:
			break;
		}
	}
}
