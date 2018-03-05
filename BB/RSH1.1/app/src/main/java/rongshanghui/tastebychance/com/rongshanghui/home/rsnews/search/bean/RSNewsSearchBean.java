package rongshanghui.tastebychance.com.rongshanghui.home.rsnews.search.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/1 13:57
 * 修改人：Administrator
 * 修改时间：2017/12/1 13:57
 * 修改备注：
 */

public class RSNewsSearchBean implements Serializable {


    /**
     * total : 3590
     * per_page : 10
     * current_page : 1
     * last_page : 359
     * data : [{"id":34,"post_title":"文章标题56","publish_location":"头条","m_post_image1":"http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120","post_hits":"57.27 万","detail":"http://rsh.com/index.php?s=/api/html/detail/id/34","is_collect":0,"is_like":0},{"id":36,"post_title":"文章标题506","publish_location":"头条","m_post_image1":"http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120","post_hits":"72.54 万","detail":"http://rsh.com/index.php?s=/api/html/detail/id/36","is_collect":0,"is_like":0}]
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
         * id : 34
         * post_title : 文章标题56
         * publish_location : 头条
         * m_post_image1 : http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120
         * post_hits : 57.27 万
         * detail : http://rsh.com/index.php?s=/api/html/detail/id/34
         * is_collect : 0
         * is_like : 0
         */

        private int id;
        private String post_title;
        private String publish_location;
        private String m_post_image1;
        private String post_hits;
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

        public String getPublish_location() {
            return publish_location;
        }

        public void setPublish_location(String publish_location) {
            this.publish_location = publish_location;
        }

        public String getM_post_image1() {
            return m_post_image1;
        }

        public void setM_post_image1(String m_post_image1) {
            this.m_post_image1 = m_post_image1;
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
    }
}
