package rongshanghui.tastebychance.com.rongshanghui.home.bean;

/**
 * 项目名称：RongShangHui2
 * 类描述：热门推荐
 * 创建人：Administrator
 * 创建时间： 2017/11/29 13:41
 * 修改人：Administrator
 * 修改时间：2017/11/29 13:41
 * 修改备注：
 */

public class RMTJBean {

    /**
     * id : 37
     * unit_name : 单位名称67
     * avatar : http://testrsh.bouncebank.com/upload/20171121/a1f000614625c63706cae4a210df2154.png
     */

    private int id;
    private String unit_name;
    private String avatar;
    private int is_cared;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIs_cared() {
        return is_cared;
    }

    public void setIs_cared(int is_cared) {
        this.is_cared = is_cared;
    }
}
