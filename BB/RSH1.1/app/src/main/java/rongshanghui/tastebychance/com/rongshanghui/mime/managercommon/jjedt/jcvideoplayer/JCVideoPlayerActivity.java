package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.jcvideoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.ScreenUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;

public class JCVideoPlayerActivity extends AppCompatActivity {

    private JCVideoPlayerStandard mJcVideoPlayerStandard;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcvideo_player);
        if (getIntent() != null) {
            filePath = getIntent().getStringExtra("filepath").replaceAll("\"", "");
        }

        mJcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jc_video);

        if (StringUtil.isEmpty(filePath)) {
            ToastUtils.showOneToast(this, "播放资源错误，请重传");
            JCVideoPlayerActivity.this.finish();
        }

//        mJcVideoPlayerStandard.setUp("http://video.jiecao.fm/11/23/xin/%E5%81%87%E4%BA%BA.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");
        mJcVideoPlayerStandard.setUp(filePath, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Picasso.with(this)
//                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
                .load(R.drawable.login_logo)
                .into(mJcVideoPlayerStandard.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());

        setTitle();


        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        // tintManager.setTintColor(Color.parseColor("#990000FF"));
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDark));

        // set a custom navigation bar resource
        // tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
        // tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    private void setTitle() {
        TextView centerTitle = (TextView) findViewById(R.id.head_center_title_tv);
        centerTitle.setVisibility(View.GONE);

        ImageView heafLeft = (ImageView) findViewById(R.id.head_left_iv);
        heafLeft.setBackground(getResources().getDrawable(R.drawable.head_left));
        heafLeft.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                JCVideoPlayerActivity.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
