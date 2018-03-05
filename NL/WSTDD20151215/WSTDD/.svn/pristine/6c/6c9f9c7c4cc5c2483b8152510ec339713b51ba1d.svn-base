/**
 * 
 */
package com.newland.wstdd.mine.personalcenter;

import java.util.Hashtable;

import android.R.integer;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;

/**我的-我的二维码
 * @author H81 2015-11-10
 *
 */
public class MineMyQRCodeActivity extends BaseFragmentActivity implements OnClickListener{
	private AppContext appContext;
//	private FragmentManager fragmentmanager;
	private String myQucodeString ;//二维码url
	private String nikeNameString;//昵称
	private String headImgUrl;//头像url
	private ImageView mMycode_iv;
	private ImageView mPersonal_nickname_iv;
	private TextView mPersonal_nickname_tv;

	private int QR_WIDTH , QR_HEIGHT;
	@Override
	protected void processMessage(Message msg) {
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myQucodeString = getIntent().getStringExtra("myQucodeString");
		headImgUrl = getIntent().getStringExtra("headImgUrl");
		nikeNameString = getIntent().getStringExtra("nikeNameString");
		setContentView(R.layout.activity_mine_myqrcode);
		appContext = AppContext.getAppContext();
		setTitle();
		bindView();
		createQRImage(myQucodeString);
	}
	private void setTitle(){
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		leftBtn.setVisibility(View.VISIBLE);
		centerTitle.setText("我的二维码");
		rightTv.setVisibility(View.GONE);
		leftBtn.setOnClickListener(this);
	}
	private void bindView(){
		mMycode_iv = (ImageView) findViewById(R.id.mycode_iv);
		mPersonal_nickname_iv =(ImageView) findViewById(R.id.personal_nickname_iv);
		mPersonal_nickname_tv =(TextView) findViewById(R.id.personal_nickname_tv);
		QR_WIDTH = AppContext.getAppContext().getScreenWidth() - 40;
		QR_HEIGHT = AppContext.getAppContext().getScreenWidth() - 40;
		ImageDownLoad.getDownLoadImg(headImgUrl, mPersonal_nickname_iv);
		mPersonal_nickname_tv.setText(nikeNameString);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv:
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	
	//要转换的地址或字符串,可以是中文
    public void createQRImage(String url)
    {
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            mMycode_iv.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
