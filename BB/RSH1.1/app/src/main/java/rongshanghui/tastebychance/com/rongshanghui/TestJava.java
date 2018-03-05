package rongshanghui.tastebychance.com.rongshanghui;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import rongshanghui.tastebychance.com.rongshanghui.home.rsnews.bean.RSNewslvRes;
import rongshanghui.tastebychance.com.rongshanghui.util.CharacterParser;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/15 17:34
 * 修改人：Administrator
 * 修改时间：2017/11/15 17:34
 * 修改备注：
 */

public class TestJava {
    public static void main(String[] args) {
        test3();
    }

    private static void test1() {
        CharacterParser characterParser = CharacterParser.getInstance();
        char str1 = characterParser.getSelling("美国").charAt(0);
        System.out.println("str = " + str1);
    }

    private static void test2() {
        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"data\": {\n" +
                "        \"top\":{\n" +
                "              \"id\": \"1\",\n" +
                "              \"post_title\": \"这是置顶新闻\",\n" +
                "              \"m_post_image1\": \"第一张图片\",\n" +
                "              \"detail\": \"\" ,\n" +
                "        },\n" +
                "        \"list\":[\n" +
                "                {\n" +
                "                  \"id\":\"2\",\n" +
                "                  \"post_title\":\"文章标题\",\n" +
                "                  \"m_post_image1\":\"第一张图\",\n" +
                "                  \"unit_name\":\"单位名称\",\n" +
                "                  \"post_hits\":\"查看数\",\n" +
                "                  \"detail\":\"\"\n" +
                "              },\n" +
                "              {\n" +
                "                   \"id\":\"2\",\n" +
                "                   \"post_title\":\"文章标题\",\n" +
                "                   \"m_post_image1\":\"第一张图\",\n" +
                "                   \"unit_name\":\"单位名称\",\n" +
                "                   \"post_hits\":\"查看数\",\n" +
                "                   \"detail\":\"\"\n" +
                "              }\n" +
                "        ]\n" +
                "    }\n" +
                "  }";
        RSNewslvRes rsNewslvRes = JSONObject.parseObject(str, RSNewslvRes.class);
        if (rsNewslvRes.getCode() == Constants.RESULT_SUCCESS) {
            RSNewslvRes.DataBeanX.TopBean topBean = rsNewslvRes.getData().getTop();
            RSNewslvRes.DataBeanX.ListBean listBeans = rsNewslvRes.getData().getList();
            System.out.println("listBeans = " + listBeans);
        }
    }

    private static void test3() {
     /*   Entiry entiry = new Entiry();
        entiry.setName("aaa");
        new Impl();*/
    }


}
