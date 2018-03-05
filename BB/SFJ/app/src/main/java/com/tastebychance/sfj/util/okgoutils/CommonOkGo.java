package com.tastebychance.sfj.util.okgoutils;

import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.tastebychance.sfj.MyApplication;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.view.CustomLoadingFactory;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shenbinghong on 2018/1/30.
 */

public class CommonOkGo {
    public static CommonOkGo getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final CommonOkGo INSTANCE = new CommonOkGo();
    }

    private CommonOkGo(){}

    public LoadingBar loadingBar;

    /**
     * get请求获取数据
     *
     * @param url
     */
    public void getByOkGo(String url, StringCallback callback) {
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(callback);
    }

    /**
     * post请求
     *
     * @param url
     */
    public <T> void postByOkGo(String url, Map<String, String> params, AbsCallback<T> callback) {

        PostRequest postRequest = OkGo.post(url)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postRequest.params(entry.getKey(), entry.getValue()); // 这里可以上传参数
            }
        }

        postRequest.execute(callback);
    }

    /**
     * 下载文件
     * @param url 下载地址
     * @param destFileDir 保存文件路径
     * @param destFileName 保存文件名
     */
    public void downLoad(String url, String destFileDir, String destFileName){
        OkGo.get(url)//
                .tag(this)//
                .execute(new FileCallback(destFileDir, destFileName) {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        //currentSize totalSize以字节byte为单位
                    }
                });
    }

    /**
     * 多文件上传
     * @param url
     * @param keyName
     * @param files 文件集合
     */
    public <T> void uploadFiles(String url, Map<String, String> params, String keyName, List<File> files, AbsCallback<T> callback){
        PostRequest postRequest = OkGo.post(url)//
                .tag(this)//
                .isMultipart(true);    // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                //.params("param1", "paramValue1")        // 这里可以上传参数
                //.params("file1", new File("filepath1"))   // 可以添加文件上传
                //.params("file2", new File("filepath2"))     // 支持多文件同时添加上传

        postRequest.connTimeOut(60 * 60 * 1000L);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postRequest.params(entry.getKey(), entry.getValue()); // 这里可以上传参数
            }
        }

        postRequest.addFileParams(keyName, files)    // 这里支持一个key传多个文件
                .execute(callback);
    }

    /**
     * 多文件上传
     * @param url
     * @param keyName
     * @param files 文件集合
     */
    public void uploadFiles1(String url, Map<String, String> params, String keyName, List<File> files){
        PostRequest postRequest = OkGo.post(url)//
                .tag(this)//
                .isMultipart(true);    // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                //.params("param1", "paramValue1")        // 这里可以上传参数
                //.params("file1", new File("filepath1"))   // 可以添加文件上传
                //.params("file2", new File("filepath2"))     // 支持多文件同时添加上传

        postRequest.connTimeOut(60 * 60 * 1000L);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postRequest.params(entry.getKey(), entry.getValue()); // 这里可以上传参数
            }
        }

        postRequest.addFileParams(keyName, files)    // 这里支持一个key传多个文件
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Toast.makeText(MyApplication.getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
//                        mProgressBar.setProgress((int) (100 * progress));
//                        mTextView2.setText("已上传" + currentSize/1024/1024 + "MB, 共" + totalSize/1024/1024 + "MB;");
                    }
                });
    }

    /**
     * 请求网络图片
     * @param url
     */
    public void getBitmap(String url, BitmapCallback callback) {
        OkGo.get(url)//
                .tag(this)//
                .execute(callback);
    }

    //错误提示
    public static final int ERROR_WHAT = 0;
    //消息提示
    private static final int INFO_WHAT = 1;
    //取消Loading
    private static final int LOADING_CANCEL = 2;

    public static class MyHandler extends MyBaseHandler{
        private WeakReference<CommonOkGo> mT;
        public MyHandler(CommonOkGo t){
            mT = new WeakReference<CommonOkGo>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            CommonOkGo t = mT.get();
            if (null != t) {
                try {
                    switch (msg.what) {
                        case ERROR_WHAT:
                            ToastUtils.showOneToast(MyApplication.getContext(), (String) msg.obj);
                            break;
                        case INFO_WHAT:
                            if (Constants.IS_DEVELOPING) {
                                ToastUtils.showOneToast(MyApplication.getContext(), (String) msg.obj);
                            }
                            break;
                        case LOADING_CANCEL:
                            if (null != t.loadingBar) {
                                t.loadingBar.cancel();
                                t.loadingBar = null;
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public MyHandler myHandler = new MyHandler(this);

    public void generateLoading(View view){
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(view, new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            System.out.println(Thread.currentThread().getId() + "-----" + Thread.currentThread().getName());
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }
    }

    public void dialogCancel() {
        Message msg = new Message();
        msg.what = LOADING_CANCEL;
        myHandler.sendMessage(msg);
    }
}
