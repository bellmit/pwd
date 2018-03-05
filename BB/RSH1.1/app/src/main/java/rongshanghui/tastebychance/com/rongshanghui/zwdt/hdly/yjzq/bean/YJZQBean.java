package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.yjzq.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/1/18.
 */

public class YJZQBean {


    /**
     * code : 0
     * data : {"info":{"id":37,"post_title":"基督教","user_id":232,"post_content":"基督教","avatar":"http://testrsh.bouncebank.com/default.png","unit_name":"5测试商会","create_time":"2018-01-10","is_cared":0},"replies":{"total":3,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":3,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":2,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":1,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"}]}}
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
         * info : {"id":37,"post_title":"基督教","user_id":232,"post_content":"基督教","avatar":"http://testrsh.bouncebank.com/default.png","unit_name":"5测试商会","create_time":"2018-01-10","is_cared":0}
         * replies : {"total":3,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":3,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":2,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":1,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"}]}
         */

        private InfoBean info;
        private RepliesBean replies;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public RepliesBean getReplies() {
            return replies;
        }

        public void setReplies(RepliesBean replies) {
            this.replies = replies;
        }

        public static class InfoBean {
            /**
             * id : 37
             * post_title : 基督教
             * user_id : 232
             * post_content : 基督教
             * avatar : http://testrsh.bouncebank.com/default.png
             * unit_name : 5测试商会
             * create_time : 2018-01-10
             * is_cared : 0
             */

            private int id;
            private String post_title;
            private int user_id;
            private String post_content;
            private String avatar;
            private String unit_name;
            private String create_time;
            private int is_cared;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPost_title() {
                return post_title;
            }

            public void setPost_title(String post_title) {
                this.post_title = post_title;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getPost_content() {
                return post_content;
            }

            public void setPost_content(String post_content) {
                this.post_content = post_content;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUnit_name() {
                return unit_name;
            }

            public void setUnit_name(String unit_name) {
                this.unit_name = unit_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getIs_cared() {
                return is_cared;
            }

            public void setIs_cared(int is_cared) {
                this.is_cared = is_cared;
            }
        }

        public static class RepliesBean {
            /**
             * total : 3
             * per_page : 10
             * current_page : 1
             * last_page : 1
             * data : [{"id":3,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":2,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"},{"id":1,"reply":"呵呵呵呵呵呵呵","add_time":"2018-01-22","user_id":215,"avatar":"http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg","user_name":"回归线"}]
             */

            private int total;
            private int per_page;
            private int current_page;
            private int last_page;
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

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
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
                 * reply : 呵呵呵呵呵呵呵
                 * add_time : 2018-01-22
                 * user_id : 215
                 * avatar : http://testrshimg.bouncebank.com/20180102/78fcc34d58bc3c5448bff636a9183e40.jpg
                 * user_name : 回归线
                 */

                private int id;
                private String reply;
                private String add_time;
                private int user_id;
                private String avatar;
                private String user_name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getReply() {
                    return reply;
                }

                public void setReply(String reply) {
                    this.reply = reply;
                }

                public String getAdd_time() {
                    return add_time;
                }

                public void setAdd_time(String add_time) {
                    this.add_time = add_time;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
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
}
