package com.tastebychance.sonchance.homepage.bean;

import java.io.Serializable;

/**首页banner轮播图接口
 * Created by shenbh on 2017/9/2.
 */

public class BannerInfo implements Serializable{
    private String image	;//	banner图片地址
    private String name	;//	banner图片名称
    private String link	;//	banner图片链接地址

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
