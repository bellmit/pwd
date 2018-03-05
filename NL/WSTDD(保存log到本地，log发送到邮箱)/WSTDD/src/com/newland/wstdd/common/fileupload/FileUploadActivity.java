package com.newland.wstdd.common.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.UrlManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
/**
 * 三种方式上传文件，既可以任选一种放项目直接使用，更是学习文件上传非常好的例子！
 * 用在项目的话推荐使用第一和第三种方法，第一种带进度条，第二和第三种不带
 * @author Jason
 *
 */
public class FileUploadActivity extends Activity implements OnClickListener {
	private static final String TAG = "FileUploadActivity";//收集异常日志tag
	private Button btn_upload;
	private Button btn_upload2;
	private Button btn_upload3;
	private Button btn_cancle;
	private List<String> filePathList;
	private List<FormFile> formFiles;
	private HttpMultipartPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.activity_file_upload); 
        
        /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload2 = (Button) findViewById(R.id.btn_upload2);
        btn_upload3 = (Button) findViewById(R.id.btn_upload3);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
     
        btn_upload.setOnClickListener(this);
        btn_upload2.setOnClickListener(this);
        btn_upload3.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 创建一个文件夹对象，赋值为外部存储器的目录
             File sdcardDir =Environment.getExternalStorageDirectory();
           //得到一个路径，内容是sdcard的文件夹路径和名字
             String path=sdcardDir.getPath()+"/cardImages";
             File path1 = new File(path);
            if (!path1.exists()) {
             //若不存在，创建目录，可以在应用启动的时候创建
             path1.mkdirs();
             setTitle("paht ok,path:"+path);
           }
        }
        //构造方法1、2的参数
        filePathList = new ArrayList<String>();
		filePathList.add(Environment.getExternalStorageDirectory().getAbsolutePath() 
				+ File.separator +"cardImages"+File.separator+"a.jpg");
		filePathList.add(Environment.getExternalStorageDirectory().getAbsolutePath() 
				+ File.separator +"cardImages"+File.separator+"b.jpg");
//		
//		
//		 //构造方法3的参数
//		formFiles = new ArrayList<FormFile>();
//		File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
//									+ File.separator +"cardImages"+File.separator+"01.png");
//		FormFile formFile1= new FormFile("01.png",file1,"tag",null);
//		formFiles.add(formFile1);
//		File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
//				+ File.separator +"cardImages"+File.separator+"02.png");
//		FormFile formFile2= new FormFile("02.png",file2,"tag",null);
//		formFiles.add(formFile2);	
    }

    @Override
    protected void onDestroy() {
    	 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
    	super.onDestroy();
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_upload:			
				post = new HttpMultipartPost(FileUploadActivity.this, filePathList);
				post.execute();
			break;	
		case R.id.btn_upload2:
			new Thread(new Runnable() {
				public void run() {			
					HttpUrlEncodedFormEntityPost.upload(filePathList);	
				}
			}).start();
			break;
		case R.id.btn_upload3:
			new Thread(new Runnable() {
				public void run() {			
						try {
							//这里是要上传的文本内容
							Map<String,String> map = new HashMap<String, String>();
							map.put("key", "文件上传");
							HttpSocketPost.post(UrlManager.headURL, map, formFiles);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}).start();
			break;	
		case R.id.btn_cancle:
			if (post != null) {
				if (!post.isCancelled()) {
					post.cancel(true);
				}
			}
			break;
		}
		
	}
    
}
