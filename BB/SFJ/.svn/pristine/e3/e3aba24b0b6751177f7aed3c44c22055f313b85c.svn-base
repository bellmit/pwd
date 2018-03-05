package com.tastebychance.sfj.login.bean;

import java.io.Serializable;

/**
 * Created by shenbinghong on 2018/2/1.
 */

public class LoginBean{

    /**
     * code : 0
     * data : {"info":{"id":1,"avatar":"http://sfj.com/upload/2018-01-30/1517299308703.png"},"token":"eyJ1aWQiOjEsImV4cCI6MTUxOTk3MjQ4MCwic2lnbmF0dXJlIjoiZjUyMzI3Mzk1ZDM5NWNlYTllNzRjNTRkYzhlOTlmODQifQ"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        /**
         * info : {"id":1,"avatar":"http://sfj.com/upload/2018-01-30/1517299308703.png"}
         * token : eyJ1aWQiOjEsImV4cCI6MTUxOTk3MjQ4MCwic2lnbmF0dXJlIjoiZjUyMzI3Mzk1ZDM5NWNlYTllNzRjNTRkYzhlOTlmODQifQ
         */

        private InfoBean info;
        private String token;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class InfoBean implements Serializable{

            private int id;
            private String avatar;
            private String user_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }
}
