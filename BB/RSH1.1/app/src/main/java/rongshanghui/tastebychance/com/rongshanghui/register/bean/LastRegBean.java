package rongshanghui.tastebychance.com.rongshanghui.register.bean;

/**
 * Created by shenbinghong on 2018/1/25.
 */

public class LastRegBean {

    /**
     * code : 0
     * data : {"id":256,"email":"12345678@qq.com","avatar":"20171129/8e1597b4cd65883d9a92486426779f4d.png","card_image":"20171129/8e1597b4cd65883d9a92486426779f4d.png","user_nickname":"文件oij98","unit_name":"11111","attribution":"","organization_type":0}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 256
         * email : 12345678@qq.com
         * avatar : 20171129/8e1597b4cd65883d9a92486426779f4d.png
         * card_image : 20171129/8e1597b4cd65883d9a92486426779f4d.png
         * user_nickname : 文件oij98
         * unit_name : 11111
         * attribution :
         * organization_type : 0
         */

        private int id;
        private String email;
        private String avatar;
        private String card_image;
        private String user_nickname;
        private String unit_name;
        private String attribution;
        private int organization_type;
        private String attribution_str;
        private String organization_type_str;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCard_image() {
            return card_image;
        }

        public void setCard_image(String card_image) {
            this.card_image = card_image;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getAttribution() {
            return attribution;
        }

        public void setAttribution(String attribution) {
            this.attribution = attribution;
        }

        public int getOrganization_type() {
            return organization_type;
        }

        public void setOrganization_type(int organization_type) {
            this.organization_type = organization_type;
        }

        public String getAttribution_str() {
            return attribution_str;
        }

        public void setAttribution_str(String attribution_str) {
            this.attribution_str = attribution_str;
        }

        public String getOrganization_type_str() {
            return organization_type_str;
        }

        public void setOrganization_type_str(String organization_type_str) {
            this.organization_type_str = organization_type_str;
        }
    }
}
