package com.newland.wstdd.common.saveLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.newland.wstdd.common.log.transaction.utils.Utils;
import com.newland.wstdd.common.mail.MultiMailsender;
import com.newland.wstdd.common.mail.MultiMailsender.MultiMailSenderInfo;

import android.content.Context;
import android.os.Environment;

public class LogcatHelper {

	private static LogcatHelper INSTANCE = null;
	private static String PATH_LOGCAT;
	private LogDumper mLogDumper = null;
	private int mPId;

	/**
	 * 
	 * 初始化目录
	 * 
	 * */
	public void init(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "WSTDDLog";
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "WSTDDLog";
		}
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static LogcatHelper getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LogcatHelper(context);
		}
		return INSTANCE;
	}

	private LogcatHelper(Context context) {
		init(context);
		mPId = android.os.Process.myPid();
	}

	public void start() {
		if (mLogDumper == null){
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
		}
		mLogDumper.start();
	}

	public void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			try {
				out = new FileOutputStream(new File(dir, "WSTDD.log"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/**
			 * 
			 * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
			 * 
			 * 显示当前mPID程序的 E和W等级的日志.
			 * 
			 * */

			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
			// cmds = "logcat -s way";//打印标签过滤信息
			cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

		}

		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((MyDate.getDateEN() + "  " + line + "\n").getBytes());
					}
				}
				File file = new File(PATH_LOGCAT,"WSTDD.Log");
				log2Mail(file);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}
	}

	
	private void log2Mail(File file){
		if (Utils.sdAvailible()) {
			try {
				// 这个类主要是设置邮件
				MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
				mailInfo.setMailServerHost("smtp.163.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("bkpddwenzi@163.com");
				mailInfo.setPassword("cj1234");// 您的邮箱密码
				mailInfo.setFromAddress("bkpddwenzi@163.com");
				mailInfo.setToAddress("ptwenzi@163.com");
				mailInfo.setSubject("WSTDD-Bug");
				String encoding="GBK";
				
				
				
				
				
				
				
				
				
				
               
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    StringBuffer tempString = new StringBuffer();
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);
                        tempString.append(lineTxt+"\n");
                    }
                    mailInfo.setContent(tempString.toString());
                    //抄送
                    String[] receivers = new String[] { "ptwenzi@163.com", "992183370@qq.com" };
                    String[] ccs = receivers;
                    mailInfo.setReceivers(receivers);
                    mailInfo.setCcs(ccs);
                    
                    // 这个类主要来发送邮件
                    MultiMailsender sms = new MultiMailsender();
                    sms.sendTextMail(mailInfo);// 发送文体格式
//					MultiMailsender.sendHtmlMail(mailInfo);// 发送html格式
                    MultiMailsender.sendMailtoMultiCC(mailInfo);// 发送抄送
                    
                    
                    read.close();
		        }else{
		            System.out.println("找不到指定的文件");
		        }
				
			} catch (Exception e) {
			}
		}
	}
}