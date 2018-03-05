package rongshanghui.tastebychance.com.rongshanghui.home.rzy.bean;

import java.io.Serializable;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/12 9:38
 * 修改人：Administrator
 * 修改时间：2017/12/12 9:38
 * 修改备注：
 */

public class RZYHBean implements Serializable {

    /**
     * url : http://www.baidu.com
     * name : 工商银行
     */

    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
