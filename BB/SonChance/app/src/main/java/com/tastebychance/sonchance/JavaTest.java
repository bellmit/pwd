package com.tastebychance.sonchance;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.homepage.afterpay.bean.AfterPayInfo;
import com.tastebychance.sonchance.util.CharacterParser;
import com.tastebychance.sonchance.util.Constants;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/18 18:48
 * 修改人：Administrator
 * 修改时间：2017/9/18 18:48
 * 修改备注：
 */

public class JavaTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        CharacterParser characterParser = CharacterParser.getInstance();
        char str1 = characterParser.getSelling("美国").charAt(0);
        System.out.println("str1 = " + str1);
    }

    private static void test2(){
        String str = "    {\n" +
                "      \"result\": \"0\",\n" +
                "      \"data\": {\n" +
                "        \"result_code\": \"0\",\n" +
                "      }\n" +
                "    }";
        try{
            final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
            if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                JSONObject jsonObject = JSONObject.parseObject(resInfo.getData().toString());
                System.out.println("jsonObject.get(\"result_code\") = " + jsonObject.get("result_code"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}