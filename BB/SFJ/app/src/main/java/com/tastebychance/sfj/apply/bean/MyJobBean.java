package com.tastebychance.sfj.apply.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/2/5.
 */

public class MyJobBean {

    /**
     * code : 0
     * data : {"total":5,"per_page":10,"current_page":1,"data":[{"id":169,"type":"请假单","add_time":"2018-01-18"},{"id":168,"type":"请假单","add_time":"2018-01-18"},{"id":150,"type":"请假单","add_time":"2018-01-18"},{"id":148,"type":"请假单","add_time":"2018-01-18"},{"id":141,"type":"请假单","add_time":"2018-01-18"}]}
     */

    private String code;
    private DataBeanX data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 5
         * per_page : 10
         * current_page : 1
         * data : [{"id":169,"type":"请假单","add_time":"2018-01-18"},{"id":168,"type":"请假单","add_time":"2018-01-18"},{"id":150,"type":"请假单","add_time":"2018-01-18"},{"id":148,"type":"请假单","add_time":"2018-01-18"},{"id":141,"type":"请假单","add_time":"2018-01-18"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 169
             * type : 请假单
             * add_time : 2018-01-18
             */

            private int id;
            private String type;
            private String add_time;
            private String detail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
        }
    }
}
