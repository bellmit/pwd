package rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/28 19:34
 * 修改人：Administrator
 * 修改时间：2017/11/28 19:34
 * 修改备注：
 */

public class AreaCodeInfo implements Serializable, Comparable {
    private String firstWord;
    private String name;
    private String code;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int compareTo(Object another) {
        return firstWord.compareTo(((AreaCodeInfo) another).getFirstWord());
    }
}
