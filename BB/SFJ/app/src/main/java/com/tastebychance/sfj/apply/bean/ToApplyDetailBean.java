package com.tastebychance.sfj.apply.bean;

import java.io.Serializable;

/**
 * Created by shenbinghong on 2018/2/6.
 */

public class ToApplyDetailBean implements Serializable{
    private int id;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
