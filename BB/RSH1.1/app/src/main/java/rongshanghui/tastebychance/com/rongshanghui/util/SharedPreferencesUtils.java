/**
 * 系统项目名称
 * com.lee.utils
 * SharedPreferencesUtils.java
 * <p>
 * 2013-5-30-上午10:49:03
 */
package rongshanghui.tastebychance.com.rongshanghui.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * SharedPreferencesUtils
 * <p>
 * Tony Lee
 * 2013-5-30 上午10:49:03
 *
 * @version 1.0.0
 */
public class SharedPreferencesUtils {
    public static String Name = "RSH";

    /**
     * setConfig设置布尔变量
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param context
     * @param key
     * @param bl      void
     * @throws
     * @since 1.0.0
     */
    public static void setConfigBoolean(Context context, String key, Boolean bl) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    /**
     * getConfig 获取本地布尔变量
     * 没有这个变量 默认false
     *
     * @param context
     * @param key
     * @return boolean
     * @throws
     * @since 1.0.0
     */
    public static boolean getConfigBoolean(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        return share.getBoolean(key, false);

    }


    public static void setConfigStr(Context context, String key, String str) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString(key, str);
        edit.commit();
    }


    public static String getConfigStr(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        return share.getString(key, "");

    }

    public static void setConfigInt(Context context, String key, int intValue) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putInt(key, intValue);
        edit.commit();
    }


    public static int getConfigInt(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(Name, Activity.MODE_PRIVATE);
        return share.getInt(key, 0);

    }

    /**
     * 保存参数
     *
     * @param context
     * @param key     键
     * @param datas   List<Map<String, String>>型数据
     */
    public void saveInfo(Context context, String key, List<HashMap<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            HashMap<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }

        SharedPreferences sp = context.getSharedPreferences(Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }

    /**
     * 取出数据
     *
     * @param context
     * @param key     键
     * @return List<HashMap<String, String>>型数据
     */
    public List<HashMap<String, String>> getInfo(Context context, String key) {
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        SharedPreferences sp = context.getSharedPreferences(Name, Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                HashMap<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;
    }

    /**
     * 对象存储
     * <p>
     * 向SharedPreference 中保存信息<br>
     *
     * @param key 类型String Key
     * @param obj 类型object
     */
    public static void saveToShared(Context context, String key, Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(obj);
            String value = new String(Base64.encode(out.toByteArray(), 0));
            SharedPreferences.Editor editor = context.getSharedPreferences(Name, Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象读取
     * <p>
     * 从SharedPreference 中读取保存的信息<br>
     *
     * @param key 读取保存信息的Key
     * @return 返回读取的信息<br>
     * 类型为 T <br>
     * Value 为读取内容，类型为String,如果Key未找到对应的数据，则返回null
     */
    public static Object queryForSharedToObject(Context context, String key) {

        String value = context.getSharedPreferences(Name, Context.MODE_PRIVATE).getString(key, null);
        if (value != null) {
            byte[] valueBytes = Base64.decode(value, 0);
            ByteArrayInputStream bin = new ByteArrayInputStream(valueBytes);
            try {
                ObjectInputStream oin = new ObjectInputStream(bin);

                return oin.readObject();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
