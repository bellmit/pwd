package rongshanghui.tastebychance.com.rongshanghui.home.sht.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.util.Constants;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/21 15:26
 * 修改人：Administrator
 * 修改时间：2017/11/21 15:26
 * 修改备注：
 */

public class SHTMineRes {

    /**
     * code : 0
     * data : [{"id":"1","unit_name":"单位名称","avatar":"单位头像"},{"id":"1","unit_name":"单位名称","avatar":"单位头像"}]
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

    public static class DataBean {
        /**
         * id : 1
         * unit_name : 单位名称
         * avatar : 单位头像
         */

        private String id;
        private String unit_name;
        private String avatar;
        private int subject_type;//0:个人,1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }
    }

    public static void main(String[] args) {
        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "          \"id\": \"1\",\n" +
                "          \"unit_name\": \"单位名称\",\n" +
                "          \"avatar\": \"单位头像\",\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"1\",\n" +
                "          \"unit_name\": \"单位名称\",\n" +
                "          \"avatar\": \"单位头像\",\n" +
                "        },\n" +
                "    ]\n" +
                "  }";


        SHTMineRes shtMineRes = JSONObject.parseObject(str, SHTMineRes.class);
        if (shtMineRes.getCode() == Constants.RESULT_SUCCESS) {
            List<SHTMineRes.DataBean> dataBean = shtMineRes.getData();
            System.out.println("listBeans = " + dataBean);
        }
    }
}
