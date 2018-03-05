package rongshanghui.tastebychance.com.rongshanghui.util.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/12 17:18
 * 修改人：Administrator
 * 修改时间：2017/12/12 17:18
 * 修改备注：
 */

public class OkHttpUtils {
    /**
     * 网络访问要求singleton
     */
    private static OkHttpUtils instance;

    // 必须要用的okhttpclient实例,在构造器中实例化保证单一实例
    private OkHttpClient mOkHttpClient;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mHandler;
    private Gson mGson;

    private OkHttpUtils() {
        /**
         * okHttp3中超时方法移植到Builder中
         */
        mOkHttpClient = (new OkHttpClient()).newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 对外提供的Get方法访问
     *
     * @param url
     * @param callBack
     */
    public void Get(String url, MyCallBack callBack) {
        /**
         * 通过url和GET方式构建Request
         */
        Request request = bulidRequestForGet(url);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param parms:   提交内容为表单数据
     * @param callBack
     */
    public void PostWithFormData(String url, Map<String, String> parms, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByForm(url, parms);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param json:    提交内容为json数据
     * @param callBack
     */
    public void PostWithJson(String url, String json, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByJson(url, json);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * POST方式构建Request {json}
     *
     * @param url
     * @param json
     * @return
     */
    private Request bulidRequestForPostByJson(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * POST方式构建Request {Form}
     *
     * @param url
     * @param parms
     * @return
     */
    private Request bulidRequestForPostByForm(String url, Map<String, String> parms) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parms != null) {
            for (Map.Entry<String, String> entry : parms.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * GET方式构建Request
     *
     * @param url
     * @return
     */
    private Request bulidRequestForGet(String url) {
        return new Request.Builder().url(url).get().build();
    }

    private void requestNetWork(final Request request, final MyCallBack<Object> callBack) {

        /**
         * 处理连网逻辑，此处只处理异步操作enqueue
         */
        callBack.onLoadingBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
//                mHandler.post(() -> callBack.onFailure(request, e));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(request, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String resultStr = response.body().string();
                    if (callBack.mType == String.class) {
                        // 如果想要返回字符串 直接返回就行
//                        mHandler.post(() -> callBack.onSuccess(response, resultStr));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(response, resultStr);
                            }
                        });
                    } else {
                        // 需要返回解析好的javaBean集合
                        try {
                            // 此处暂时写成object，使用时返回具体的带泛型的集合
                            final Object obj = mGson.fromJson(resultStr, callBack.mType);
//                            mHandler.post(() -> callBack.onSuccess(response, obj));
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(response, obj);
                                }
                            });
                        } catch (Exception e) {
                            // 解析错误时
//                            mHandler.post(() -> callBack.onError(response));
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(response);
                                }
                            });
                        }
                    }
                } else {
//                    mHandler.post(() -> callBack.onError(response));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(response);
                        }
                    });
                }
            }
        });
    }

    /**
     * okhttp上传文件，同时带参数
     *
     * @param url
     * @param params
     * @param key      文件对应的key
     * @param file
     * @param callback 调用例子
     *                 private void uploadFile2(){
     *                 Map<String, String> map = new HashMap<>();
     *                 map.put("filetype", "file");
     *                 OkHttpUtils.uploadFile(UrlManager.URL_FILEUPLOAD, map, "file", new File(filepath), new FileCallBack() {
     * @Override public void onFailure(Call call, IOException e) {
     * Log.i(Constants.TAG, e.getMessage());
     * }
     * @Override public void onResponse(Call call, Response response) throws IOException {
     * Log.i(Constants.TAG, response.body().string());
     * }
     * });
     * }
     */
    public static void uploadFile(String url, Map<String, String> params, String key, File file, Callback callback) {
        if (!file.exists()) {
            System.out.println("======文件不存在========");
            return;
        }
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/xml"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addPart(Headers.of("Content-Disposition", "multipart/form-data; name=" + key + "; filename=\"" + file.getName() + "\""), fileBody);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = builder.build();
        Request requestPostFile = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(requestPostFile).enqueue(callback);
    }

    /**
     * 本app专属的，如果通用那么需要把其中的url、“file”当参数来传
     *
     * @param context
     * @param params
     * @param file
     * @param absCallback
     */
    public static void okGoUploadFile(Context context, Map<String, String> params, File file, AbsCallback absCallback) {
        PostRequest postRequest = OkGo.post(UrlManager.URL_FILEUPLOAD);
        postRequest.tag(context);
        postRequest.connTimeOut(60 * 60 * 1000L);
        postRequest.isMultipart(true);// 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
        postRequest.params("file", file);  // 可以添加文件上传

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postRequest.params(entry.getKey(), entry.getValue()); // 这里可以上传参数
            }
        }
        //postRequest..params("file2", new File("filepath2"))     // 支持多文件同时添加上传
        //postRequest..addFileParams(keyName, files)    // 这里支持一个key传多个文件
        postRequest.execute(absCallback);
    }

    /**
     * 多文件上传
     *
     * @param url
     * @param keyName
     * @param files   文件集合
     */
    private void uploadFiles(String url, String keyName, List<File> files, final ProgressBar mProgressBar, final TextView mTextView2) {
        OkGo.post(url)//
                .tag(this)//
                //.isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                //.params("param1", "paramValue1")        // 这里可以上传参数
                //.params("file1", new File("filepath1"))   // 可以添加文件上传
                //.params("file2", new File("filepath2"))     // 支持多文件同时添加上传
                .addFileParams(keyName, files)    // 这里支持一个key传多个文件
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Toast.makeText(MyApplication.getAppContext(), "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        mProgressBar.setProgress((int) (100 * progress));
                        mTextView2.setText("已上传" + currentSize / 1024 / 1024 + "MB, 共" + totalSize / 1024 / 1024 + "MB;");
                    }
                });
    }

    /**
     * 请求网络图片
     *
     * @param url
     */
    private void getBitmap(String url, final ImageView mImageView) {
        OkGo.get(url)//
                .tag(this)//
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        // bitmap 即为返回的图片数据
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }


    public void cancleAll(Object tag) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
