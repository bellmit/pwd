package rongshanghui.tastebychance.com.rongshanghui.zwdt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.ScreenUtils;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.fwdt.FWDTActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.HDLYActivity;
import rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq.SJFQActivity;

/**
 * 政务大厅
 * Created by shenbinghong on 2018/1/15.
 */

public class ZWDTFragment extends MyBaseFragment {

    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.zwdt_fwdt_iv)
    ImageView zwdtFwdtIv;
    @BindView(R.id.zwdt_hdly_iv)
    ImageView zwdtHdlyIv;
    @BindView(R.id.zwdt_sjfq_iv)
    ImageView zwdtSjfqIv;
    Unbinder unbinder;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends android.support.v4.app.Fragment> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
//            ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:

                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);


    public static ZWDTFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        ZWDTFragment homeFragment = new ZWDTFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    private ZWDTFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_zwdt, null);
        fragment = this;
        unbinder = ButterKnife.bind(this, contentView);

        setTitle();

        isPrepared = true;
        return contentView;
    }

    private boolean isPrepared = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepared) {
            getData();
        }
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.INVISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("政务大厅");
        }
        if (null != headRightIv) {
            headRightIv.setVisibility(View.GONE);
        }
    }

    private void getData() {

        Message msg = new Message();
        msg.what = Constants.WHAT_GETDATA;
        msg.obj = "";
        handler.sendMessage(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isPrepared = false;
    }

    @OnClick({R.id.zwdt_fwdt_iv, R.id.zwdt_hdly_iv, R.id.zwdt_sjfq_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zwdt_fwdt_iv:
                getActivity().startActivity(new Intent(getActivity(), FWDTActivity.class));
                break;
            case R.id.zwdt_hdly_iv:
                getActivity().startActivity(new Intent(getActivity(), HDLYActivity.class));
                break;
            case R.id.zwdt_sjfq_iv:
                getActivity().startActivity(new Intent(getActivity(), SJFQActivity.class));
                break;
        }
    }
}
