package rongshanghui.tastebychance.com.rongshanghui.mime.feedback.yjfkckhf.bean;

import java.util.List;

/**
 * Created by shenbinghong on 2018/1/19.
 */

public class YJFKCKHFBean {

    /**
     * content	string	意见反馈内容
     * add_time	string	意见反馈时间
     * back_content	string	回复内容
     * back_time	string	回复时间
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
         * content : 哈哈哈哈哈
         * add_time : 1512040831
         * back_content : 4534324234234234234
         * back_time : 1512040868
         */

        private String content;
        private String add_time;
        private String back_content;
        private String back_time;

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
