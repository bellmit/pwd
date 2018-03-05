package rongshanghui.tastebychance.com.rongshanghui.login.bean;

import java.io.Serializable;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/25 14:18
 * 修改人：Administrator
 * 修改时间：2017/11/25 14:18
 * 修改备注：
 */

public class LoginRes implements Serializable {

    /**
     * user_info : {"id":16,"user_nickname":"","user_email":"","card_image":"","avatar":"","area_code":"86","mobile":"15112333634","unit_name":"","sex":0,"industry":null,"city":null,"subject_type":0}
     * token : eyJ1aWQiOjE2LCJleHAiOjE1MTQxODIzNDQsInNpZ25hdHVyZSI6Ijk5YzVlZWI4YTMxNGUwMmM5ZmMwN2E0ODgwM2ZmOGEwIn0
     */

    private UserInfoBean user_info;
    private String token;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserInfoBean implements Serializable {
        /**
         * id : 16
         * user_nickname :
         * user_email :
         * card_image :
         * avatar :
         * area_code : 86
         * mobile : 15112333634
         * unit_name :
         * sex : 0
         * industry : null
         * city : null
         * subject_type : 0 //主体类型;0:个人,1:商会,2:企业，3：部门，4：机构，5:镇街，6：学校，7：医院,8:其他
         * regi_status:0,
         * user_login: "95545123"
         */

        private int id;
        private String user_nickname;
        private String user_email;
        private String card_image;
        private String avatar;
        private String area_code;
        private String mobile;
        private String unit_name;
        private int sex;
        private Object industry;
        private Object city;
        private int subject_type;
        private int regi_status;//注册审核状态;0:未审核,1:通过,2:不通过
        private String user_login;//用户账号用于阿里百川

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getCard_image() {
            return card_image;
        }

        public void setCard_image(String card_image) {
            this.card_image = card_image;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public int getRegi_status() {
            return regi_status;
        }

        public void setRegi_status(int regi_status) {
            this.regi_status = regi_status;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }
    }
}
