package com.zbar.lib;

/**
 * 调用JNI该类只能放在ZbarManager下面  作用是？
 */
public class ZbarManager {

	static {
		System.loadLibrary("zbar");
	}

	public native String decode(byte[] data, int width, int height, boolean isCrop, int x, int y, int cwidth, int cheight);
}
