package com.tastebychance.sfj.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResInfo {

    private int code;
    private String error_message;//错误返回描述
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将data对象转成str
     *
     * @return
     */
    public String convertDataToStr() {
        return JSON.toJSONString(data);
    }

    /**
     * JSONArray转成str
     *
     * @param jsonArray
     * @return
     */
    public String jSONArrayToStr(JSONArray jsonArray) {
        return JSON.toJSONString(jsonArray);
    }

    /**
     * JSONObje转成str
     *
     * @param jsonObject
     * @return
     */
    public String jSONObjectToStr(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    /**
     * str 转对象
     *
     * @param str
     * @param cla
     * @param <T>
     * @return
     */
    public <T> T strToClass(String str, Class<T> cla) {
        return JSON.parseObject(str, cla);
    }

    /**
     * 将data转换成对应的对象
     *
     * @param cla
     * @param <T>
     * @return
     */
    public <T> T getClass(Class<T> cla) {
        if (!TextUtils.isEmpty(data.toString())) {
            return strToClass(convertDataToStr(), cla);
        } else {
            return null;
        }
    }

    /**
     * 从data中根据key获取相应对象
     * @param key
     * @param cla
     * @param <T>
     * @return
     */
    public <T> T getClassByKey(String key, Class<T> cla) {
        JSONObject jsonObject = strToJSONObject(convertDataToStr());
        JSONObject jsonObject1 = jSONObjectGetJSONObjectByKey(jsonObject, key);
        String str2 = jSONObjectToStr(jsonObject1);
        return strToClass(str2, cla);
    }

    /**
     * str转JSONObject
     *
     * @param str
     * @return
     */
    public JSONObject strToJSONObject(String str) {
        return JSON.parseObject(str);
    }

    /**
     * JSONObject从key中获取子JSONObject
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public JSONObject jSONObjectGetJSONObjectByKey(JSONObject jsonObject, String key) {
        return jsonObject.getJSONObject(key);
    }

    /**
     * JSONObject从key中获取子JSONArray
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public JSONArray jSONObjectGetJSONArrayByKey(JSONObject jsonObject, String key) {
        return jsonObject.getJSONArray(key);
    }

    /**
     * JSONArray转对象的集合
     * @param jsonArray
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> jsonArrayToList(JSONArray jsonArray, Class<T> cla) {
        String str = jSONArrayToStr(jsonArray);
        return strToList(str, cla);
    }

    /**
     * str转 对象的集合
     *
     * @param str
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> strToList(String str, Class<T> cla) {
        return JSON.parseArray(str, cla);
    }

    /**
     * 将data转换成对应的对象的集合
     *
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> getDataList(Class<T> cla) {
        List<T> list = strToList(convertDataToStr(), cla);
        if (list == null) {
            list = new ArrayList<T>();
        }
        return list;
    }

    /**
     * 把data对象中某个key获取array数组
     *
     * @param key
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> getListByKey(String key, Class<T> cla) {
        JSONObject jsonObject = strToJSONObject(convertDataToStr());
        JSONArray jsonArray = jSONObjectGetJSONArrayByKey(jsonObject, key);
        return jsonArrayToList(jsonArray, cla);
    }

    /**
     * 把data对象中某个key获取array数组
     *
     * @param str
     * @param key
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> getListFromStrByKey(String str, String key, Class<T> cla) {
        JSONObject jsonObject = strToJSONObject(str);
        JSONArray jsonArray = jSONObjectGetJSONArrayByKey(jsonObject, key);
        String str2 = jSONArrayToStr(jsonArray);
        return strToList(str2, cla);
    }

    /**
     * 将data，双层结构的数据提取并转换成array数组
     *
     * @param key
     * @param key1
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> getDataExpToClass(String key, String key1, Class<T> cla) {
        JSONObject jsonObject = strToJSONObject(convertDataToStr());
        JSONObject jsonObject1 = jSONObjectGetJSONObjectByKey(jsonObject, key);
        JSONArray jsonArray = jSONObjectGetJSONArrayByKey(jsonObject1, key1);
        String str2 = jSONArrayToStr(jsonArray);
        return strToList(str2, cla);
    }
}
