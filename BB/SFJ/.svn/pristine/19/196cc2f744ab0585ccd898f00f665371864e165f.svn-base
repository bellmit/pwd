package com.tastebychance.sfj.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.EventLogout;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.login.LoginActivity;
import com.tastebychance.sfj.login.bean.LoginBean;
import com.tastebychance.sfj.mine.contacts.ContactsActivity;
import com.tastebychance.sfj.mine.personalcenter.PersonalCenterActivity;
import com.tastebychance.sfj.util.EventBusUtils;
import com.tastebychance.sfj.util.PicassoUtils;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.view.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by shenbinghong on 2018/1/31.
 */

public class MineFragment extends MyBaseFragment {

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
    @BindView(R.id.fragment_mine_headbg_iv)
    ImageView fragmentMineHeadbgIv;
    @BindView(R.id.fragment_mine_headpic_riv)
    RoundImageView fragmentMineHeadpicRIv;
    @BindView(R.id.fragment_mine_nickname_tv)
    TextView fragmentMineNicknameTv;
    @BindView(R.id.fragment_mine_grxx_icon_iv)
    ImageView fragmentMineGrxxIconIv;
    @BindView(R.id.fragment_mine_grxx_name_tv)
    TextView fragmentMineGrxxNameTv;
    @BindView(R.id.fragment_mine_grxx_rl)
    RelativeLayout fragmentMineGrxxRl;
    @BindView(R.id.fragment_mine_contacts_icon_iv)
    ImageView fragmentMineTxlIconIv;
    @BindView(R.id.fragment_mine_contacts_name_tv)
    TextView fragmentMineTxlNameTv;
    @BindView(R.id.fragment_mine_contacts_rl)
    RelativeLayout fragmentMineTxlRl;
    Unbinder unbinder;

    public static MineFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.EXTRA_CONTENT, content);
        MineFragment homeFragment = new MineFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    private MineFragment fragment;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        }else {
            rootView = inflater.inflate(R.layout.fragment_mine, null);
            unbinder = ButterKnife.bind(this, rootView);

            setTitle();
            initObject();
        }

        return rootView;
    }

    private void initObject() {
        try {
            LoginBean.DataBean.InfoBean userInfo = (LoginBean.DataBean.InfoBean) SystemUtil.getInstance().getObjectFromSP(Constants.KEY_USERINFO);
            if (null == userInfo){
                return;
            }
            PicassoUtils.getinstance().loadCircleHead(getActivity(), userInfo.getAvatar(), fragmentMineHeadpicRIv, 0);
            fragmentMineNicknameTv.setText(userInfo.getUser_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTitle() {
        headLeftIv.setVisibility(View.INVISIBLE);
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("退出账号");
        headCenterTitleTv.setText("我的");
        headCenterTitleTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        headRightTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder){
            unbinder.unbind();
        }
    }

    @OnClick({R.id.head_right_tv, R.id.fragment_mine_headpic_riv, R.id.fragment_mine_nickname_tv, R.id.fragment_mine_grxx_rl, R.id.fragment_mine_contacts_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_right_tv:
                SystemUtil.getInstance().clearData();
                SystemUtil.getInstance().saveBooleanToSP(Constants.KEY_LOGOUT, true);
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.fragment_mine_headpic_riv:
                break;
            case R.id.fragment_mine_nickname_tv:
                break;
            case R.id.fragment_mine_grxx_rl:
                //TODO:判断是否已经登入
                getActivity().startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                break;
            case R.id.fragment_mine_contacts_rl:
                //TODO:判断是否已经登入
                getActivity().startActivity(new Intent(getActivity(), ContactsActivity.class));
                break;
            default:break;
        }
    }
}
