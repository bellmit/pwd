package com.tastebychance.sonchance.homepage.toevaluate.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：SonChance 去评价接口
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/19 18:24
 * 修改人：Administrator
 * 修改时间：2017/9/19 18:24
 * 修改备注：
 */

public class CommentRes  implements Serializable {
    private List<CommentInfo> list;
    private int score;

    public List<CommentInfo> getList() {
        return list;
    }

    public void setList(List<CommentInfo> list) {
        this.list = list;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
