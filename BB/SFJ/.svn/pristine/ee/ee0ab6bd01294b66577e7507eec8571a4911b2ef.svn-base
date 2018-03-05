package com.tastebychance.sfj;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.tastebychance.sfj.util.SystemBarTintManager;

/**
 *
 *
 * @author shenbinghong
 * @time 2018/1/30 11:35
 * @path sfj.tastebychance.com.sfj.BaseActivity.java
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public Toolbar mToolbarTb;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        mContext = this;

        mToolbarTb =  findViewById(R.id.tb_toolbar);
        if (mToolbarTb != null) {
            setSupportActionBar(mToolbarTb);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTb != null) {
            mToolbarTb.setTitle(title);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
    }

    protected void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);
        tintManager.setNavigationBarTintColor(Color.BLACK);//通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            winParams.flags |= bits;
        }else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }
}
