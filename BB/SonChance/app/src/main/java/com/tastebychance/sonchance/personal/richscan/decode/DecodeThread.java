package com.tastebychance.sonchance.personal.richscan.decode;

import java.util.concurrent.CountDownLatch;

import android.os.Handler;
import android.os.Looper;

import com.tastebychance.sonchance.personal.richscan.RichScanActivity;


/**
 *
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

	RichScanActivity activity;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(RichScanActivity activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (Exception ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
