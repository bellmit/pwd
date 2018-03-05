package com.tastebychance.sfj.home.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/2/2.
 */

public class NoticeBean {

    /**
     * code : 0
     * data : {"total":3,"per_page":10,"current_page":1,"data":[{"id":22,"title":"34234234","add_time":"2018-01-31","image":"","detail":"http://sfj.com/index.php/api/html/detail/id/22.html"},{"id":23,"title":"3423423423","add_time":"2018-01-31","image":"","detail":"http://sfj.com/index.php/api/html/detail/id/23.html"},{"id":24,"title":"43234让24","add_time":"2018-01-31","image":"http://sfj.comsrc=\"http://sfj.com/static/layui/images/face/27.gif\"","detail":"http://sfj.com/index.php/api/html/detail/id/24.html"}]}
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
         * total : 3
         * per_page : 10
         * current_page : 1
         * data : [{"id":22,"title":"34234234","add_time":"2018-01-31","image":"","detail":"http://sfj.com/index.php/api/html/detail/id/22.html"},{"id":23,"title":"3423423423","add_time":"2018-01-31","image":"","detail":"http://sfj.com/index.php/api/html/detail/id/23.html"},{"id":24,"title":"43234让24","add_time":"2018-01-31","image":"http://sfj.comsrc=\"http://sfj.com/static/layui/images/face/27.gif\"","detail":"http://sfj.com/index.php/api/html/detail/id/24.html"}]
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
             * id : 22
             * title : 34234234
             * add_time : 2018-01-31
             * image :
             * detail : http://sfj.com/index.php/api/html/detail/id/22.html
             */

            private int id;
            private String title;
            private String add_time;
            private String image;
            private String detail;

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

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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
