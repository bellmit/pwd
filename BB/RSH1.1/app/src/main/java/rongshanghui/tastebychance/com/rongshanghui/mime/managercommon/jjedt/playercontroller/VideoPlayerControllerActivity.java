package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.playercontroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import rongshanghui.tastebychance.com.rongshanghui.R;


public class VideoPlayerControllerActivity extends Activity implements MediaController.onClickIsFullScreenListener {

    private MediaController mController;
    private boolean fullscreen = false;
    private VideoView viv;
    private ProgressBar progressBar;
    private RelativeLayout rlDD;
    private RelativeLayout rlTop;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getIntent() != null) {
            filePath = getIntent().getStringExtra("filepath");
        }

        setContentView(R.layout.activity_video_view1);
        viv = (VideoView) findViewById(R.id.videoView);
        rlDD = (RelativeLayout) findViewById(R.id.rl_dd);
//		progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        mController = new MediaController(this);
        mController.setClickIsFullScreenListener(this);

        viv.setMediaController(mController);
//		progressBar.setVisibility(View.VISIBLE);
//		viv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.apple));
        viv.setVideoURI(Uri.parse(filePath));
        viv.requestFocus();
        viv.start();

        ((ImageView) findViewById(R.id.head_left_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPlayerControllerActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickIsFullScreen() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//设置RelativeLayout的全屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            VideoPlayerControllerActivity.this.finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("info", "横屏");
            rlDD.setVisibility(View.GONE);
        } else {
            Log.e("info", "竖屏");
            rlDD.setVisibility(View.VISIBLE);
        }
        super.onConfigurationChanged(newConfig);
        viv.refreshDrawableState();

    }

}