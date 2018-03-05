package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.player;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.player.utils.CNTrace;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;

public class VideoViewActivity extends AppCompatActivity {
    VideoView mVideoView;
    ViewGroup.LayoutParams mVideoViewLayoutParams;
    RelativeLayout mVideoLayout;

    private ImageButton mediacontrollerPlayPause;
    private TextView mediacontrollerTimeCurrent, mediacontrollerTimeTotal;
    private SeekBar mediacontrollerSeekbar;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mVideoView = (VideoView) this.findViewById(R.id.video_view);
        //mVideoView.setMediaController(new MediaController(this));

        mVideoLayout = (RelativeLayout) super.findViewById(R.id.voide_layout);

        CNTrace.d("onCreate");

        if (getIntent() != null) {
            filePath = getIntent().getStringExtra("filepath");
        }

        initView();
    }

    private void initView() {
        mediacontrollerPlayPause = (ImageButton) findViewById(R.id.mediacontroller_play_pause);
        mediacontrollerTimeCurrent = (TextView) findViewById(R.id.mediacontroller_time_current);
        mediacontrollerTimeTotal = (TextView) findViewById(R.id.mediacontroller_time_total);
        mediacontrollerSeekbar = (SeekBar) findViewById(R.id.mediacontroller_seekbar);

        mediacontrollerPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse(filePath);
                Uri uri = Uri.parse("/storage/emulated/0/DCIM/Camera/VID_20180103_201709.mp4");
                mVideoView.setVideoURI(uri);
                new MediaController(VideoViewActivity.this).setMediaPlayer(mVideoView);
                mVideoView.requestFocus();
                mVideoView.start();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rot = getWindowManager().getDefaultDisplay().getRotation();
        CNTrace.d("onConfigurationChanged : " + newConfig + ", rot : " + rot);
        if (rot == Surface.ROTATION_90 || rot == Surface.ROTATION_270) {
            mVideoViewLayoutParams = mVideoLayout.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mVideoLayout.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else if (rot == Surface.ROTATION_0) {
//            RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(320,240);
//            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoLayout.setLayoutParams(mVideoViewLayoutParams);
        }
    }

    /*public void onClick(View view) {
        if (StringUtil.isEmpty(filePath)){
            return;
        }
        switch (view.getId()) {
            case R.id.play_start:
//                Uri uri = Uri.parse("/storage/emulated/0/DCIM/Camera/VID_20180103_201709.mp4");
                Uri uri = Uri.parse(filePath);
                mVideoView.setVideoURI(uri);
                new MediaController(this).setMediaPlayer(mVideoView);
                mVideoView.requestFocus();
                mVideoView.start();
//                }
                break;

            case R.id.play_pause:
                if(mVideoView.isPlaying()){
                    mVideoView.pause();
                }else{
                    mVideoView.start();
                }
                break;

            case R.id.play_stop:
                if(mVideoView.isPlaying()){
                    mVideoView.suspend();
                }
                break;

            case R.id.test:
                //startActivity(new Intent(this, TestActivity.class));
            {
                if(true){//设置RelativeLayout的全屏模式


                    View decorView = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);

                    ActionBar actionBar = getActionBar();
                    if(actionBar != null)
                        actionBar.hide();
                }else{

                }
            }
            break;
        }
    }*/
}
