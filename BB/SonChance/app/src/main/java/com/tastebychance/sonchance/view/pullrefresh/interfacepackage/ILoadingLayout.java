package com.tastebychance.sonchance.view.pullrefresh.interfacepackage;

/**
 * 项目名称：SonChance 下拉刷新和上拉加载更多的界面接口
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/14 16:41
 * 修改人：Administrator
 * 修改时间：2017/9/14 16:41
 * 修改备注：
 */

public interface ILoadingLayout {
    /**
     * 当前的状态
     */
    public enum State {

        /**
         * Initial state
         */
        NONE,

        /**
         * When the UI is in a state which means that user is not interacting
         * with the Pull-to-Refresh function.
         */
        RESET,

        /**
         * When the UI is being pulled by the user, but has not been pulled far
         * enough so that it refreshes when released.
         */
        PULL_TO_REFRESH,

        /**
         * When the UI is being pulled by the user, and <strong>has</strong>
         * been pulled far enough so that it will refresh when released.
         */
        RELEASE_TO_REFRESH,

        /**
         * When the UI is currently refreshing, caused by a pull gesture.
         */
        REFRESHING,

        /**
         * When the UI is currently refreshing, caused by a pull gesture.
         */
        @Deprecated
        LOADING,

        /**
         * No more data
         */
        NO_MORE_DATA,
    }

    /**
     * 设置当前状态，派生类应该根据这个状态的变化来改变View的变化
     *
     * @param state 状态
     */
    public void setState(State state);

    /**
     * 得到当前的状态
     *
     * @return 状态
     */
    public State getState();

    /**
     * 得到当前Layout的内容大小，它将作为一个刷新的临界点
     *
     * @return 高度
     */
    public int getContentSize();

    /**
     * 在拉动时调用
     *
     * @param scale 拉动的比例
     */
    public void onPull(float scale);
}
