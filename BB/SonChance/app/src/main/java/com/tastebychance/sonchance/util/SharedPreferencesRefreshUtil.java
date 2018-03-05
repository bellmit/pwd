/**
 * 
 */
package com.tastebychance.sonchance.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * @author shenbh
 *
 * 2017年8月10日
 */
public class SharedPreferencesRefreshUtil {
	//记录保存的内容（键）
	private String chairUpdateTime;//讲座
	private String groupbuyingUpdateTime;//团购
	private String inviteUpdateTime;//招聘
	private String meetingUpdateTime;//聚会
	private String travelUpdateTime;//旅行
	private String crowdfundingUpdateTime;//众筹
	private String voteUpdateTime;//投票
	private String allUpdateTime;//全部

	private String searchhistory;//搜索历史记录
	private String historyindex;//搜索历史脚标
	
	private Context context;
	private final String SEARCH_INFO = "search_info";
	private final String TEMP_INFO ="temp_info";

	public SharedPreferencesRefreshUtil(Context context) {
		this.context = context;
	}

	/**
	 * 保存参数
	 * 
	 * @param name
	 *            账户名
	 * @param age
	 *            密码
	 */
	/*public void savesAppcntextInfo(String user_name, String user_nick_name, String user_phone, String user_mail, String user_gender, String user_id, String shop_id, String shop_name,
			String shop_area, String shop_addr, String shop_comment, String shop_phone, String fruit_version, String dateOfMonthStart, String dateOfMonthEnd, String phoneDeviceId) {
		if (user_name != null && !"null".equals(user_name)) {

			SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.remove("user_name");
			editor.remove("user_nick_name");
			editor.remove("user_phone");
			editor.remove("user_mail");
			editor.remove("user_gender");
			editor.remove("user_id");
			editor.remove("shop_id");
			editor.remove("shop_name");
			editor.remove("shop_area");
			editor.remove("shop_addr");
			editor.remove("shop_comment");
			editor.remove("shop_phone");
			editor.remove("fruit_version");
			editor.remove("dateOfMonthStart");
			editor.remove("dateOfMonthEnd");
			editor.remove("phoneDeviceId");
			editor.putString("user_name", user_name);
			editor.putString("user_nick_name", user_nick_name);
			editor.putString("user_phone", user_phone);
			editor.putString("user_mail", user_mail);
			editor.putString("user_gender", user_gender);
			editor.putString("user_id", user_id);
			editor.putString("shop_id", shop_id);
			editor.putString("shop_name", shop_name);
			editor.putString("shop_area", shop_area);
			editor.putString("shop_addr", shop_addr);
			editor.putString("shop_comment", shop_comment);
			editor.putString("shop_phone", shop_phone);
			editor.putString("fruit_version", fruit_version);
			editor.putString("dateOfMonthStart", dateOfMonthStart);
			editor.putString("dateOfMonthEnd", dateOfMonthEnd);
			editor.putString("phoneDeviceId", phoneDeviceId);
			editor.commit();
		}
	}*/

	/**保存参数
	 * @param string 
	 */
	public void saveComment(String key,String value) {
		SharedPreferences preferences = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
	}
	
	/**保存参数
	 * @param string 
	 */
	public void saveSearchComment(String key,String value) {
		SharedPreferences preferences = context.getSharedPreferences(SEARCH_INFO, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
	}
	SharedPreferences sp;
    /**保存参数
     * @param context
     * @param key 键
     * @param datas  List<Map<String, String>>型数据
     */
    public void saveInfo(Context context, String key, List<HashMap<String, String>> datas) {  
        JSONArray mJsonArray = new JSONArray();  
        for (int i = 0; i < datas.size(); i++) {  
          HashMap<String, String> itemMap = datas.get(i);  
          Iterator<Entry<String, String>> iterator = itemMap.entrySet().iterator();  
      
          JSONObject object = new JSONObject();  
      
          while (iterator.hasNext()) {  
            Entry<String, String> entry = iterator.next();  
            try {  
              object.put(entry.getKey(), entry.getValue());  
            } catch (Exception e) {
              e.printStackTrace();  
            }  
          }  
          mJsonArray.put(object);  
        }  
      
        sp = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);  
        Editor editor = sp.edit();  
        editor.putString(key, mJsonArray.toString());  
        editor.commit();  
      }  
    
    /**保存参数
     * @param context
     * @param key 键
     * @param datas  List<Map<String, String>>型数据
     */
    public void saveSearchInfo(Context context, String key, List<HashMap<String, String>> datas) {  
        JSONArray mJsonArray = new JSONArray();  
        for (int i = 0; i < datas.size(); i++) {  
          HashMap<String, String> itemMap = datas.get(i);  
          Iterator<Entry<String, String>> iterator = itemMap.entrySet().iterator();  
      
          JSONObject object = new JSONObject();  
      
          while (iterator.hasNext()) {  
            Entry<String, String> entry = iterator.next();  
            try {  
              object.put(entry.getKey(), entry.getValue());  
            } catch (Exception e) {
              e.printStackTrace();  
            }  
          }  
          mJsonArray.put(object);  
        }  
      
        sp = context.getSharedPreferences(SEARCH_INFO, Context.MODE_PRIVATE);  
        Editor editor = sp.edit();  
        editor.putString(key, mJsonArray.toString());  
        editor.commit();  
      }  
    
    /**取出数据
     * @param context
     * @param key 键
     * @return List<HashMap<String, String>>型数据
     */
    public List<HashMap<String, String>> getInfo(Context context, String key) {  
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();  
        sp = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);  
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
        } catch (Exception e) {
          e.printStackTrace();  
        }  
      
        return datas;  
      }  
    
    /**取出数据
     * @param context
     * @param key 键
     * @return List<HashMap<String, String>>型数据
     */
    public List<HashMap<String, String>> getSearchInfo(Context context, String key) {  
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();  
        sp = context.getSharedPreferences(SEARCH_INFO, Context.MODE_PRIVATE);  
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
        } catch (Exception e) {
          e.printStackTrace();  
        }  
      
        return datas;  
      }  
    
    public  void  cleanInfo(Context context ) {
		sp = context.getSharedPreferences(TEMP_INFO,Context.MODE_PRIVATE);
		sp.edit().clear().commit();
		
		sp = context.getSharedPreferences(SEARCH_INFO,Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
    
	public Set<String > getPreferenceSet(){
		Set<String> params= new HashSet<String>();
		SharedPreferences preferences = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
		params=preferences.getStringSet("searchhistory", null);
		return params;
	}
	/**
	 * 获取各项配置参数,相当于读取文件中的数据 集合都是键-值对的形式，可以通过key找到相应的值
	 * 
	 * @return
	 */
	public Map<String, String> getPreferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
		/**
		 * 理解一下这里的put方法只是为了封装该类，以便在另外一个类中可以去调用
		 * 
		 * preferences.getString("name",
		 * "null");如果没有参数name的话，这时候呢默认为null，否则就是按name对应的值
		 */
		params.put("chairUpdateTime", preferences.getString("chairUpdateTime", "null"));
		params.put("groupbuyingUpdateTime", preferences.getString("groupbuyingUpdateTime", "null"));
		params.put("inviteUpdateTime", preferences.getString("inviteUpdateTime", "null"));
		params.put("meetingUpdateTime", preferences.getString("meetingUpdateTime", "null"));
		params.put("travelUpdateTime", preferences.getString("travelUpdateTime", "null"));
		params.put("crowdfundingUpdateTime", preferences.getString("crowdfundingUpdateTime", "null"));
		params.put("voteUpdateTime", preferences.getString("voteUpdateTime", "null"));
		params.put("allUpdateTime", preferences.getString("allUpdateTime", "null"));
		return params;
	}

	/**
	 * 清空记录
	 */
	public void clear() {
		SharedPreferences sp = context.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}
