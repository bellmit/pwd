package rongshanghui.tastebychance.com.rongshanghui.zwdt.hdly.ckhf.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class CKHFBean {

    /**
     * code : 0
     * data : [{"id":6,"user_id":217,"content":"hasihdoashohjov","add_time":"2018-01-22","back_content":"","back_time":"1970-01-01"},{"id":5,"user_id":217,"content":"","add_time":"2018-01-22","back_content":"","back_time":"1970-01-01"},{"id":4,"user_id":217,"content":"","add_time":"2018-01-22","back_content":"","back_time":"1970-01-01"}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * user_id : 217
         * content : hasihdoashohjov
         * add_time : 2018-01-22
         * back_content :
         * back_time : 1970-01-01
         */

        private int id;
        private int user_id;
        private String content;
        private String add_time;
        private String back_content;
        private String back_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getBack_content() {
            return back_content;
        }

        public void setBack_content(String back_content) {
            this.back_content = back_content;
        }

        public String getBack_time() {
            return back_time;
        }

        public void setBack_time(String back_time) {
            this.back_time = back_time;
        }
    }
}
