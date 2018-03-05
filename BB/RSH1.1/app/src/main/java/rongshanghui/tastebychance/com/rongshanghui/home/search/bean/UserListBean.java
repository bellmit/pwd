package rongshanghui.tastebychance.com.rongshanghui.home.search.bean;

import java.io.Serializable;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/1 11:55
 * 修改人：Administrator
 * 修改时间：2017/12/1 11:55
 * 修改备注：
 */

public class UserListBean implements Serializable {

    /**
     * id : 5
     * unit_name :
     * user_nickname : 32142
     * subject_type : 0
     * is_friend : 1
     */

    private int id;
    private String unit_name;
    private String user_nickname;
    private int subject_type;
    private int is_friend;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
