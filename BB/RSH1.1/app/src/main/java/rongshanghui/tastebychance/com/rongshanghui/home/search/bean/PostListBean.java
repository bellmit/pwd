package rongshanghui.tastebychance.com.rongshanghui.home.search.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/1 11:54
 * 修改人：Administrator
 * 修改时间：2017/12/1 11:54
 * 修改备注：
 */

public class PostListBean implements Serializable {


    /**
     * total : 10709
     * per_page : 10
     * current_page : 1
     * last_page : 1071
     * data : [{"unit_name":"单位名称327","post_title":"文章标题52","m_post_image1":"http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120","post_hits":"33.1 万","id":1,"detail":"http://rsh.com/index.php?s=/api/html/detail/id/1","is_collect":0,"is_like":0},{"unit_name":"单位名称327","post_title":"文章标题39","m_post_image1":"http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120","post_hits":"94.26 万","id":2,"detail":"http://rsh.com/index.php?s=/api/html/detail/id/2","is_collect":0,"is_like":0},{"unit_name":"单位名称327","post_title":"文章标题3","m_post_image1":"http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120","post_hits":"90.31 万","id":3,"detail":"http://rsh.com/index.php?s=/api/html/detail/id/3","is_collect":0,"is_like":0}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;
    private int is_collect;// 	1 已收藏 0 未收藏
    private int is_like;// 	1 已点赞 0 未点赞

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
         * unit_name : 单位名称327
         * post_title : 文章标题52
         * m_post_image1 : http://ozhx45ipd.bkt.clouddn.com/20171121/a1f000614625c63706cae4a210df2154.png!thumbnail120x120
         * post_hits : 33.1 万
         * id : 1
         * detail : http://rsh.com/index.php?s=/api/html/detail/id/1
         * is_collect : 0
         * is_like : 0
         */

        private String unit_name;
        private String post_title;
        private String m_post_image1;
        private String post_hits;
        private int id;
        private String detail;
        private int is_collect;
        private int is_like;


        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
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

        public String getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(String post_hits) {
            this.post_hits = post_hits;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
