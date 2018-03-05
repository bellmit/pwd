package com.newland.wstdd.mine.managerpage.activitycode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;

/**
 * 活动二维码
 * 
 * @author Administrator
 * 
 */
public class ActivityCodeActivity extends BaseFragmentActivity implements OnClickListener {

	private RelativeLayout rr;
	private RelativeLayout rLayout;
	private TextView activityType_tv;// 活动类型
	private TextView personal_nickname_tv;// 活动标题
	private FrameLayout login_regist_details;
	private ImageView mycode_iv;// 二维码图片
	private ImageView headimg_iv;// 头像
	private Button save_btn;// 保存

	private TddActivity tddActivity;
	private int QR_WIDTH, QR_HEIGHT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tddActivity = (TddActivity) getIntent().getSerializableExtra("tddActivity");
		setContentView(R.layout.activity_manageractivitycode);
		QR_WIDTH = AppContext.getAppContext().getScreenWidth() * 4 / 7;
		QR_HEIGHT = AppContext.getAppContext().getScreenWidth() * 4 / 7;
		initView();
	}

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void initView() {
		setTitle();

		rr = (RelativeLayout) findViewById(R.id.rr);
		rLayout = (RelativeLayout) findViewById(R.id.rl);
		activityType_tv = (TextView) findViewById(R.id.activityType_tv);
		personal_nickname_tv = (TextView) findViewById(R.id.personal_nickname_tv);
		login_regist_details = (FrameLayout) findViewById(R.id.login_regist_details);
		mycode_iv = (ImageView) findViewById(R.id.mycode_iv);
		headimg_iv = (ImageView) findViewById(R.id.headimg_iv);
		save_btn = (Button) findViewById(R.id.save_btn);

		activityType_tv.setText(StringUtil.intType2Str(tddActivity.getActivityType()));
		personal_nickname_tv.setText(tddActivity.getActivityTitle());// 活动标题
		headimg_iv.getLayoutParams().width = QR_WIDTH / 4;
		headimg_iv.getLayoutParams().height = headimg_iv.getLayoutParams().width;
		ImageDownLoad.getDownLoadImg(tddActivity.getHeadimgurl(), headimg_iv);
		save_btn.setOnClickListener(this);
		// 活动二维码url
		createQRImage(tddActivity.getShareUrl());
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView head_left_iv = (ImageView) findViewById(R.id.head_left_iv);
		TextView head_center_title = (TextView) findViewById(R.id.head_center_title);
		ImageButton head_right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView head_right_tv = (TextView) findViewById(R.id.head_right_tv);
		head_center_title.setText("活动二维码");
		head_left_iv.setOnClickListener(this);
		head_right_tv.setVisibility(View.GONE);
		head_right_btn.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.head_left_iv:// 返回
			finish();
			break;
		case R.id.save_btn:// 保存
			// 进行截图的功能

			GetandSaveCurrentImage();
			break;
		default:
			break;
		}
	}

	// 要转换的地址或字符串,可以是中文
	public void createQRImage(String url) {
		try {
			// 判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// 显示到一个ImageView上面
			mycode_iv.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 截屏并保存图片
	 */
	private void GetandSaveCurrentImage() {
		// 1.构建Bitmap
		int w = AppContext.getAppContext().getScreenWidth();
		int h = AppContext.getAppContext().getScreenHeight();

		Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 2.获取屏幕
		View decorview = this.getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		Bmp = decorview.getDrawingCache();

		String SavePath = getSDCardPath() + "/AndyDemo/ScreenImage";

		// 3.保存Bitmap
		try {
			File path = new File(SavePath);
			// 文件
			String filepath = SavePath + "/Screen_1.png";
			File file = new File(filepath);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
				UiHelper.ShowOneToast(this, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**获取sd卡路径
	 * @return
	 */
	private String getSDCardPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

}
