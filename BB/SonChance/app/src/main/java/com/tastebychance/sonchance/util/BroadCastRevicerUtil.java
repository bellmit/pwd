package com.tastebychance.sonchance.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tastebychance.sonchance.homepage.HomeActivity;

/**
 * Created by shenbh on 2017/8/31.
 */

public class BroadCastRevicerUtil extends BroadcastReceiver {

    private Context mContext;
    public BroadCastRevicerUtil(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (mContext instanceof HomeActivity){
            HomeActivity mainActivity = (HomeActivity) mContext;
        }
    }
}
