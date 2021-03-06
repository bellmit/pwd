/**
 * 
 */
package com.newland.wstdd.mine.minesetting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.clearPersonInfo.ClearPersonInfo;
import com.newland.wstdd.mine.minesetting.about.AboutTddActivity;
import com.newland.wstdd.mine.minesetting.about.SafeActivity;
import com.newland.wstdd.mine.minesetting.feedbackandhelp.FeedBackAndHelpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的--设置界面
 * 
 * @author H81 2015-11-2
 * 
 */
public class MineSettingActivity extends BaseFragmentActivity implements OnClickListener {

	private LinearLayout mMine_setting_safe_ll;// 账户与安全
	private LinearLayout mMine_setting_notification_ll;// 消息推送通知
	private LinearLayout mMine_setting_clean_ll;// 清除缓存
	private LinearLayout mMine_setting_help_ll;// 反馈与帮助
	private LinearLayout mMine_setting_share_ll;// 分享有礼
	private LinearLayout mMine_setting_about_ll;// 关于我是团大大
	private TextView mSetting_about_version_tv;//关于我是团大大版本
	private LinearLayout mMine_setting_scoring_ll;// 为我们评分
	private Button mMine_setting_exit_btn;// 退出当前账户

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_setting);
		setTitle();
		initView();
	}

	public void initView() {

		mMine_setting_safe_ll = (LinearLayout) findViewById(R.id.mine_setting_safe_ll);
		mMine_setting_notification_ll = (LinearLayout) findViewById(R.id.mine_setting_notification_ll);
		mMine_setting_clean_ll = (LinearLayout) findViewById(R.id.mine_setting_clean_ll);
		mMine_setting_help_ll = (LinearLayout) findViewById(R.id.mine_setting_help_ll);
		mMine_setting_share_ll = (LinearLayout) findViewById(R.id.mine_setting_share_ll);
		mMine_setting_about_ll = (LinearLayout) findViewById(R.id.mine_setting_about_ll);
		mSetting_about_version_tv = (TextView) findViewById(R.id.setting_about_version_tv);
		mMine_setting_scoring_ll = (LinearLayout) findViewById(R.id.mine_setting_scoring_ll);
		mMine_setting_exit_btn = (Button) findViewById(R.id.mine_setting_exit_btn);

		mMine_setting_safe_ll.setOnClickListener(this);
		mMine_setting_notification_ll.setOnClickListener(this);
		mMine_setting_clean_ll.setOnClickListener(this);
		mMine_setting_help_ll.setOnClickListener(this);
		mMine_setting_share_ll.setOnClickListener(this);
		mMine_setting_about_ll.setOnClickListener(this);
		mMine_setting_scoring_ll.setOnClickListener(this);
		mMine_setting_exit_btn.setOnClickListener(this);
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("设置");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.left_expandsion));
			left_btn.setOnClickListener(this);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.head_left_iv) {// 返回
			finish();
		} else if (v.getId() == R.id.mine_setting_safe_ll) {// 账户与安全
			
			Intent intent2= new Intent(MineSettingActivity.this, SafeActivity.class);
			startActivity(intent2);
		} else if (v.getId() == R.id.mine_setting_notification_ll) {// 消息推送通知
		} else if (v.getId() == R.id.mine_setting_clean_ll) {// 清除缓存
	    	ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
	    	ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
//	    	UiHelper.ShowOneToast(MineSettingActivity.this, "缓存清楚成功!");
		} else if (v.getId() == R.id.mine_setting_help_ll) {// 反馈与帮助
			Intent intent3 = new Intent(MineSettingActivity.this, FeedBackAndHelpActivity.class);
			startActivity(intent3);
		} else if (v.getId() == R.id.mine_setting_share_ll) {// 分享有礼
		} else if (v.getId() == R.id.mine_setting_about_ll) {// 关于我是团大大
			Intent intent =  new Intent(MineSettingActivity.this, AboutTddActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.mine_setting_scoring_ll) {// 为我们评分
		} else if (v.getId() == R.id.mine_setting_exit_btn) {// 退出当前账户
			showLogoutDialog(MineSettingActivity.this);
			
		}
	}

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}
	public static void showLogoutDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage("确定要注销该账户吗？");
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ClearPersonInfo clearPersonInfo=new ClearPersonInfo();
						
						clearPersonInfo.clearPersonInfo();
						clearPersonInfo.clearAppcontextInfo();
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
}
