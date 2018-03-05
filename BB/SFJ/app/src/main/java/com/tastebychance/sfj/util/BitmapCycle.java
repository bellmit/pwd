package com.tastebychance.sfj.util;

import android.graphics.Bitmap;

/**
 * Created by shenbinghong on 2018/2/28.
 */

public class BitmapCycle {

    public static void cycle(Bitmap bitmap){
        if (null != bitmap && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
    }
}
