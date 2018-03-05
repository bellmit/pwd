package rongshanghui.tastebychance.com.rongshanghui.mime.yijianzhengqiumanger.bean;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeZixunBean;

/**
 * Created by shenbinghong on 2018/1/24.
 */

public class YJZQManagerBean {
    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":559,"publish_type":4,"post_title":"文章标题329","m_post_image1":"http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png","unit_name":"有意义","post_hits":"13.6 万","detail":"http://testrsh.bouncebank.com/index.php?s=/api/html/detail/id/559","is_collect":1,"is_like":0,"create_time":"xxxxxx"},{"id":560,"publish_type":4,"post_title":"文章标题627","m_post_image1":"http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png","unit_name":"有意义","post_hits":"66.59 万","detail":"http://testrsh.bouncebank.com/index.php?s=/api/html/detail/id/560","is_collect":1,"is_like":0},{"id":561,"publish_type":4,"post_title":"文章标题662","m_post_image1":"http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png","unit_name":"有意义","post_hits":"8.03 万","detail":"http://testrsh.bouncebank.com/index.php?s=/api/html/detail/id/561","is_collect":0,"is_like":0,"create_time":"xxxxxx"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<YJZQManagerBean.DataBean> data;

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

    public List<YJZQManagerBean.DataBean> getData() {
        return data;
    }

    public void setData(List<YJZQManagerBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 559
         * publish_type : 4
         * post_title : 文章标题329
         * m_post_image1 : http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png
         * unit_name : 有意义
         * post_hits : 13.6 万
         * detail : http://testrsh.bouncebank.com/index.php?s=/api/html/detail/id/559
         * is_collect : 1
         * is_like : 0
         * create_time : xxxxxx
         */
        private boolean isChoosed;

        private int id;
        private int publish_type;
        private String post_title;
        private String m_post_image1;
        private String unit_name;
        private String post_hits;
        private String detail;
        private int is_collect;
        private int is_like;
        private String create_time;

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean choosed) {
            isChoosed = choosed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPublish_type() {
            return publish_type;
        }

        public void setPublish_type(int publish_type) {
            this.publish_type = publish_type;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getM_post_image1() {
            return m_post_image1;
        }

        public void setM_post_image1(String m_post_image1) {
            this.m_post_image1 = m_post_image1;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(String post_hits) {
            this.post_hits = post_hits;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

}
