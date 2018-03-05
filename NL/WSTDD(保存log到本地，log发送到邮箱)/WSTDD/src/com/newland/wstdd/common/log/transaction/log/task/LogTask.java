package com.newland.wstdd.common.log.transaction.log.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.newland.wstdd.common.log.transaction.utils.FileUtils;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.log.transaction.utils.Utils;
import com.newland.wstdd.common.mail.MultiMailsender;
import com.newland.wstdd.common.mail.MultiMailsender.MultiMailSenderInfo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class LogTask implements Runnable {
	private Context mContext;
	private String mTag;
	private String mMsg;
	private int mLogType;

	public LogTask(Context context, String tag, String msg, int logType) {
		mContext = context;
		mMsg = msg;
		mTag = tag;
		mLogType = logType;
	}

	@Override
	public void run() {
		if (mContext == null || TextUtils.isEmpty(mMsg) || TextUtils.isEmpty(mTag)) {
			return;
		}

		if ((mLogType & LogUtils.LOG_TYPE_2_LOGCAT) > 0) {
			Log.d(mTag, mMsg);
		}

		if ((mLogType & LogUtils.LOG_TYPE_2_FILE) > 0) {
			log2File("ms:" + System.currentTimeMillis() + " [" + mTag + "]" + mMsg);
		}

		if ((mLogType & LogUtils.LOG_TYPE_2_NETWORK) > 0) {
			log2Network(mTag, mMsg);
		}
	}

	private void log2File(String msg) {
		if (Utils.sdAvailible()) {
			try {
				String systemInfo = "\n";
				File file = new File(LogUtils.getLogFileName(mContext));
				if (!file.exists() || file.isDirectory()) {
					FileUtils.delete(file);
					file.createNewFile();
					systemInfo = Utils.buildSystemInfo(mContext);
				}

				String lineSeparator = System.getProperty("line.separator");
				if (lineSeparator == null) {
					lineSeparator = "\n";
				}

				// Encode and encrypt the message.
				FileOutputStream trace = new FileOutputStream(file, true);
				OutputStreamWriter writer = new OutputStreamWriter(trace, "utf-8");
				writer.write(Utils.encrypt(buildLog(msg, systemInfo)));
				writer.flush();

				trace.flush();
				trace.close();
//				log2Mail(file, mTag, mMsg);
			} catch (Exception e) {
			}
		}
	}

	private String buildLog(String msg, String systemInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append(systemInfo);
		sb.append("\n");
		sb.append(new Date().toString());
		sb.append(msg);
		sb.append("\n");

		return sb.toString();
	}

	private void log2Network(String tag, String msg) {
		// TODO: Server API for upload message.
		// TODO: Encode and encrypt the message.
	}
	
	private void log2Mail(File file,String tag,String msg){
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