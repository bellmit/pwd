package com.tastebychance.sonchance.homepage.toevaluate.bean;

import java.io.Serializable;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/20 20:08
 * 修改人：Administrator
 * 修改时间：2017/9/20 20:08
 * 修改备注：
 */

public class ButtonInfo  implements Serializable {
    private String text;//按钮文字
    private int action_id;//按钮对应操作 0 : 去支付 1：联系商家 2：确认收货 3：再来一单 4：去评价 5：订单取消

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }
}
