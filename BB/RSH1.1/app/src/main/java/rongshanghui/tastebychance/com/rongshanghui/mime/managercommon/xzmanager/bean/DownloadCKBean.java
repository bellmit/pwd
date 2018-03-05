package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.xzmanager.bean;

import java.util.List;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/18 11:21
 * 修改人：Administrator
 * 修改时间：2017/12/18 11:21
 * 修改备注：
 */

public class DownloadCKBean {

    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"post_title":"heiheheieieeh","id":568,"create_time":"2017-12-04 14:03:44","accessory":"3249238490394798"},{"post_title":"哦哦哦哦哦哦","id":567,"create_time":"2017-12-04 14:03:36","accessory":"3249238490394798"}]
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
         * post_title : heiheheieieeh
         * id : 568
         * create_time : 2017-12-04 14:03:44
         * accessory : 3249238490394798
         */

        private String post_title;
        private int id;
        private String create_time;
        private String accessory;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAccessory() {
            return accessory;
        }

        public void setAccessory(String accessory) {
            this.accessory = accessory;
        }
    }
}
