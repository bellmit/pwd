package com.tastebychance.sfj.apply.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/2/6.
 */

public class MyApplyDetailBean {

    /**
     * code : 0
     * data : {"status":"处理中","h_type":"事假","h_holiday_days":"4天","add_time":"2018-02-05","begin":"01.14","end":"01.16","textarea":"覅hi分手复活甲搜集和v欧尼","process":[{"status":0,"user_id":33,"add_time":"2018-02-05","check_name":"办公室-主任-林四","reason":""}]}
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

    public static class DataBean {
        /**
         * status : 处理中
         * h_type : 事假
         * h_holiday_days : 4天
         * add_time : 2018-02-05
         * begin : 01.14
         * end : 01.16
         * textarea : 覅hi分手复活甲搜集和v欧尼
         * process : [{"status":0,"user_id":33,"add_time":"2018-02-05","check_name":"办公室-主任-林四","reason":""}]
         */

        private String status;
        private String h_type;
        private String h_holiday_days;
        private String add_time;
        private String begin;
        private String end;
        private String textarea;
        private List<ProcessBean> process;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getH_type() {
            return h_type;
        }

        public void setH_type(String h_type) {
            this.h_type = h_type;
        }

        public String getH_holiday_days() {
            return h_holiday_days;
        }

        public void setH_holiday_days(String h_holiday_days) {
            this.h_holiday_days = h_holiday_days;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getTextarea() {
            return textarea;
        }

        public void setTextarea(String textarea) {
            this.textarea = textarea;
        }

        public List<ProcessBean> getProcess() {
            return process;
        }

        public void setProcess(List<ProcessBean> process) {
            this.process = process;
        }

        public static class ProcessBean {
            /**
             * status : 0
             * user_id : 33
             * add_time : 2018-02-05
             * check_name : 办公室-主任-林四
             * reason :
             */

            private int status;
            private int user_id;
            private String add_time;
            private String check_name;
            private String reason;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getCheck_name() {
                return check_name;
            }

            public void setCheck_name(String check_name) {
                this.check_name = check_name;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }
        }
    }
}
