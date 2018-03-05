package rongshanghui.tastebychance.com.rongshanghui.news.caremsg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/29 9:55
 * 修改人：Administrator
 * 修改时间：2017/11/29 9:55
 * 修改备注：
 */

public class CareMsgBean implements Serializable {

    /**
     * total : 4
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"post_title":"45345","id":5,"post_hits":0,"m_post_image1":"http://rsh.com/upload/","unit_name":"福州商会","detail":"http://rsh.com/index.php/api/html/detail/id/5.html"},{"post_title":"21321312","id":4,"post_hits":0,"m_post_image1":"http://rsh.com/upload/","unit_name":"福州商会","detail":"http://rsh.com/index.php/api/html/detail/id/4.html"}]
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
         * post_title : 45345
         * id : 5
         * post_hits : 0
         * m_post_image1 : http://rsh.com/upload/
         * unit_name : 福州商会
         * detail : http://rsh.com/index.php/api/html/detail/id/5.html
         */

        private String post_title;
        private int id;
        private String post_hits;
        private String m_post_image1;
        private String unit_name;
        private String detail;
        private int is_collect;// 	1 已收藏 0 未收藏
        private int is_like;// 	1 已点赞 0 未点赞

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
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
