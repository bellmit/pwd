package rongshanghui.tastebychance.com.rongshanghui.zwdt.sjfq.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/1/16.
 */

public class SJFQBean {

    /**
     * code : 0
     * data : {"news":[{"id":20,"post_title":"这是测试的","create_time":"2018-01-08","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/20.html","is_collect":0,"is_like":0},{"id":19,"post_title":"测试一下上报","create_time":"2018-01-08","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/19.html","is_collect":0,"is_like":0}],"unscramble":[{"id":25,"post_title":"哈哈哈地","create_time":"2018-01-08","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/25.html","is_collect":0,"is_like":0},{"id":24,"post_title":"测定一下","create_time":"2018-01-08","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/24.html","is_collect":0,"is_like":0}]}
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
        private List<NewsBean> news;
        private List<UnscrambleBean> unscramble;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<UnscrambleBean> getUnscramble() {
            return unscramble;
        }

        public void setUnscramble(List<UnscrambleBean> unscramble) {
            this.unscramble = unscramble;
        }

        public static class NewsBean {
            /**
             * id : 20
             * post_title : 这是测试的
             * create_time : 2018-01-08
             * detail : http://testrsh.bouncebank.com/index.php/api/html/detail/id/20.html
             * is_collect : 0
             * is_like : 0
             */

            private int id;
            private String post_title;
            private String create_time;
            private String detail;
            private int is_collect;
            private int is_like;

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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public int getIs_like() {
                return is_like;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }
        }

        public static class UnscrambleBean {
            /**
             * id : 25
             * post_title : 哈哈哈地
             * create_time : 2018-01-08
             * detail : http://testrsh.bouncebank.com/index.php/api/html/detail/id/25.html
             * is_collect : 0
             * is_like : 0
             */

            private int id;
            private String post_title;
            private String create_time;
            private String detail;
            private int is_collect;
            private int is_like;

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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public int getIs_like() {
                return is_like;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }
        }
    }
}
