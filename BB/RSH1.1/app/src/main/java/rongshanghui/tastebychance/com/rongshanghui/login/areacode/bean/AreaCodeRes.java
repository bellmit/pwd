package rongshanghui.tastebychance.com.rongshanghui.login.areacode.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 地址列表接口
 *
 * @author shenbh
 *         <p>
 *         2017年8月14日
 */
public class AreaCodeRes implements Serializable {

    private Map<String, List<AreaCode>> map;

    public Map<String, List<AreaCode>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<AreaCode>> map) {
        this.map = map;
    }

    public static class AreaCode implements Serializable {
        private String name;
        private String code;

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
    }

}
