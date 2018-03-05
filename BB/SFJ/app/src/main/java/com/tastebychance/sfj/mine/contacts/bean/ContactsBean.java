package com.tastebychance.sfj.mine.contacts.bean;

import android.support.annotation.NonNull;


import java.io.Serializable;
import java.util.List;

/**
 * Created by shenbinghong on 2018/2/2.
 */

public class ContactsBean implements Serializable,Comparable {
    private String firstWord;//城市首字母

    private List<ContactBean> contacts;

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public static class ContactBean{
        private int id;
        private String name;
        private String detail;

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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    @Override
    public int compareTo(Object another) {
        return firstWord.compareTo(((ContactsBean)another).getFirstWord());
    }
}
