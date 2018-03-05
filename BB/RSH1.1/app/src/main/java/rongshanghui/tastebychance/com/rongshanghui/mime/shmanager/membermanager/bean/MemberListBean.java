package rongshanghui.tastebychance.com.rongshanghui.mime.shmanager.membermanager.bean;

import java.io.Serializable;

/**
 * 项目名称：RongShangHui2
 * 类描述：商会-会员列表接口
 * 创建人：Administrator
 * 创建时间： 2017/12/7 21:26
 * 修改人：Administrator
 * 修改时间：2017/12/7 21:26
 * 修改备注：
 */

public class MemberListBean implements Serializable {

    /**
     * avatar :
     * unit_name : 福州商会
     * id : 4
     */

    private String avatar;
    private String unit_name;
    private int user_id;

    private String toType;//本地加的，用于区分是成员还是待审核

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }
}
