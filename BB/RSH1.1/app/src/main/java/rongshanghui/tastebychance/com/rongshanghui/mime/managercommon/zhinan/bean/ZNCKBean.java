package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean;

import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/7 14:23
 * 修改人：Administrator
 * 修改时间：2017/12/7 14:23
 * 修改备注：
 */

public class ZNCKBean {

    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"post_title":"文章标题861","m_post_image1":"http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png","id":490,"post_hits":"49.58 万","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/490.html"},{"post_title":"文章标题668","m_post_image1":"http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png","id":494,"post_hits":"30.22 万","detail":"http://testrsh.bouncebank.com/index.php/api/html/detail/id/494.html"}]
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
         * post_title : 文章标题861
         * m_post_image1 : http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png
         * id : 490
         * post_hits : 49.58 万
         * detail : http://testrsh.bouncebank.com/index.php/api/html/detail/id/490.html
         */

        private String post_title;
        private String m_post_image1;
        private int id;
        private String post_hits;
        private String detail;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
