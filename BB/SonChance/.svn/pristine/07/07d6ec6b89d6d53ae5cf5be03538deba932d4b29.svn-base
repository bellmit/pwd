package com.tastebychance.sonchance.homepage.order.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**服务器返回的评论内容
 * Created by shenbh on 2017/8/22.
 */

public class EvaluateRes  implements Serializable {
    private Object list	;//评论列表
    private int all_page		;//评论总页数
    private int cur_page		;//当前页码

    /*public List<EvaluateInfo> getList() {
        return list;
    }

    public void setList(List<EvaluateInfo> list) {
        this.list = list;
    }*/

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
    }

    public int getCur_page() {
        return cur_page;
    }

    public void setCur_page(int cur_page) {
        this.cur_page = cur_page;
    }

    public <T> List<T> getList(Class<T> cla){
        String str = JSON.toJSONString(getList());
        List<T> list = JSON.parseArray(str, cla);
        if(list == null){
            list = new ArrayList<T>();
        }
        return list;
    }
}
