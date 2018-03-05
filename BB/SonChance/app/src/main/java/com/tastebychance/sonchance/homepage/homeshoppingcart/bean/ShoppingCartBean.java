package com.tastebychance.sonchance.homepage.homeshoppingcart.bean;

import com.tastebychance.sonchance.homepage.bean.HomePageListRes;

import java.io.Serializable;

/**封装首页列表类（增加了数量属性）
 * Created by shenbh on 2017/8/18.
 */

public class ShoppingCartBean  implements Serializable {
    private HomePageListRes homePageListRes;
    private int num;//这道菜的数量

    public HomePageListRes getHomePageListRes() {
        return homePageListRes;
    }

    public void setHomePageListRes(HomePageListRes homePageListRes) {
        this.homePageListRes = homePageListRes;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
