package com.tastebychance.sfj.filedealwith.received.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/2/8.
 */

public class ReceivedBean {

    /**
     * code : 0
     * data : [{"id":3,"title":"4234","content":"24234","add_time":"2018-02-07","detail":"http://debugsfj.bouncebank.com/api/html/filedetail/id/3","has_read":0,"has_file":0,"send_user":"办公室-主任-林四"},{"id":4,"title":"43232","content":"3423","add_time":"2018-02-07","detail":"http://debugsfj.bouncebank.com/api/html/filedetail/id/4","has_read":1,"has_file":0,"send_user":"办公室-主任-林四"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * title : 4234
         * content : 24234
         * add_time : 2018-02-07
         * detail : http://debugsfj.bouncebank.com/api/html/filedetail/id/3
         * has_read : 0
         * has_file : 0
         * send_user : 办公室-主任-林四
         *
         *
         id	int	信件id
         title	string	标题
         content	string	内容
         add_time	string	发送时间
         detail	string	详情页链接
         has_read	int	1:已读 0：未读
         has_file	int	1：有附件 0 没有附件
         send_user	string	发件人
         */

        private int id;
        private String title;
        private String content;
        private String add_time;
        private String detail;
        private int has_read;
        private int has_file;
        private String send_user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getHas_read() {
            return has_read;
        }

        public void setHas_read(int has_read) {
            this.has_read = has_read;
        }

        public int getHas_file() {
            return has_file;
        }

        public void setHas_file(int has_file) {
            this.has_file = has_file;
        }

        public String getSend_user() {
            return send_user;
        }

        public void setSend_user(String send_user) {
            this.send_user = send_user;
        }
    }
}
