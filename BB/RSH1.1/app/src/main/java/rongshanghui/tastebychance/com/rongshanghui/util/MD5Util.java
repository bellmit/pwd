package rongshanghui.tastebychance.com.rongshanghui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/22 9:57
 * 修改人：Administrator
 * 修改时间：2017/12/22 9:57
 * 修改备注：
 */

public class MD5Util {
    /**
     * @param psd MD5要加密的对象
     * @returnMD5加密后市返回一个32位数的字符串，返回“”，代表加密异常
     */
    public static String md5Code(String psd) {
        try {
            // 加盐
            psd = psd + "rsh";
            // 1，获取加密算法对象，单利设计模式
            MessageDigest instance = MessageDigest.getInstance("MD5");
            // 2，通过加密算法操作，对psd进行哈希加密操作
            byte[] digest = instance.digest(psd.getBytes());
            StringBuffer sb = new StringBuffer();
            // 循环16次
            for (byte b : digest) {
                // 获取b的后8位
                int i = b & 0xff;
                // 将10进制数，转化为16进制
                String hexString = Integer.toHexString(i);
                // 容错处理，长度小于2的，自动补0
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                // 把生成的32位字符串添加到stringBuffer中
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
