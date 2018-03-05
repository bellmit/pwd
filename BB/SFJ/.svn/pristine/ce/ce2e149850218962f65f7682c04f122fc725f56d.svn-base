package com.tastebychance.sfj.mine.contacts.bean;

import java.io.Serializable;

/**
 * Created by shenbinghong on 2018/2/2.
 */

public class ContactShowBean implements Serializable,Comparable{

    private int id;
    private String name;
    private String firstWord;
    private String detail;
    private boolean isChoosed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public int compareTo(Object another) {
        return firstWord.compareTo(((ContactShowBean)another).getFirstWord());
    }

}
