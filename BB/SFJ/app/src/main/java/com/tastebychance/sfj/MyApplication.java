package com.tastebychance.sfj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.tastebychance.sfj.common.Constants;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
/**
 *
 *
 * @author shenbinghong
 * @time 2018/1/30 20:14
 * @path com.tastebychance.sfj.MyApplication.java
 */
public class MyApplication extends Application {
    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        context = getApplicationContext();

        //bugly---begin
        String packageName = context.getPackageName();//获取当前包名
        String processName = getProcessName(android.os.Process.myPid());//获取当前进程名
        //设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppReportDelay(20000);   //设置Bugly初始化延迟
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);//设置开发设备
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "311fec86cc", Constants.IS_DEVELOPING);//第二个参数为注册时申请的appid
                                                                                            // 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
                                                                                            //输出详细的Bugly SDK的Log；
                                                                                            //每一条Crash都会被立即上报；
                                                                                            //自定义日志将会在Logcat中输出。
                                                                                            //建议在测试阶段建议设置成true，发布时设置为false。
        //bugly---end

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);

        initNetRequest();
    }

    //bugly(如果使用了MultiDex，建议通过Gradle的“multiDexKeepFile”配置等方式把Bugly的类放到主Dex，另外建议在Application类的"attachBaseContext"方法中主动加载非主dex)
    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    /**
     * 获取进程号对应的进程名
     * @param pid 进程号
     * @return 进程名
     */
    private String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)){
                processName = processName.trim();
            }
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void initNetRequest() {

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//		headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//		headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
//		params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//		params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要debug，缓存相关，cookie相关的就可以了
            OkGo.getInstance()
                    //打开该调试开关，打印级别INFO，并不是异常，是为了显眼，不需要就不要加入该行
                    //最后true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)//是否打开调试

                    //如果使用默认的 60秒，以下三行页不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)                 //全局的写入超时时间

                    //可以全局统一设置缓存模式，默认是不适用缓存，可以不穿，具体其他模式看github介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3);

            //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
            //      .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
//					.setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

            //可以设置https的证书,以下几种方案根据需要自己设置
//					.setCertificates()                                  //方法一：信任所有证书,不安全有风险
            //      .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
            //      .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
            //              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            //      .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
            //      .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
            //      .addInterceptor(new Interceptor() {
            //            @Override
            //            public Response intercept(Chain chain) throws IOException {
            //                 return chain.proceed(chain.request());
            //            }
            //       })

            //这两行同上，不需要就不要加入
//					.addCommonHeaders(headers)  //设置全局公共头
//					.addCommonParams(params);   //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

    private static MyApplication appContext;

    public static MyApplication getAppContext() {
        return appContext;
    }

    /**
     * 清空堆栈
     */
    public void clearStatck() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
