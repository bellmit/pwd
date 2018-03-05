package rongshanghui.tastebychance.com.rongshanghui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：RongShangHui2
 * 类描述：归属地
 * 创建人：Administrator
 * 创建时间： 2017/11/21 17:42
 * 修改人：Administrator
 * 修改时间：2017/11/21 17:42
 * 修改备注：
 */

public class RegionRes implements Serializable {

    /**
     * code : 0
     * data : [{"region_id":"1","region_name":"福建省"},{"region_id":"2","region_name":"江苏省"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * region_id : 1
         * region_name : 福建省
         */

        private String region_id;
        private String region_name;

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }
    }
}
