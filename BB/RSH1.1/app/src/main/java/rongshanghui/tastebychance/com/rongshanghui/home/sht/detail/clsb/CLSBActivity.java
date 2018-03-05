package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.alibaba.openimui.common.Constant;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.HomeDetailBSZNListActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.detailcommon.bean.HomeDetailListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.RHSQActivity;
import rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb.bean.FileBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.jgmanager.JGManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.sbmanager.SBManagerActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.DialogUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.FileSizeUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.FileUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UriUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;

/**
 * 类描述：CLSBActivity 首页-部门、机构、镇街-材料上报
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/18 13:43
 * 修改人：
 * 修改时间：2017/12/18 13:43
 * 修改备注：
 *
 * @version 1.0
 */
public class CLSBActivity extends MyAppCompatActivity {

    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_edt)
    EditText titleEdt;
    @BindView(R.id.add_num_tv)
    TextView addNumTv;
    @BindView(R.id.addimg_iv)
    ImageView addimgIv;
    @BindView(R.id.addfile_iv)
    ImageView addfileIv;
    @BindView(R.id.add_rl)
    RelativeLayout addRl;
    @BindView(R.id.activity_zncategory_addfujian_mlv)
    MyListView activityZncategoryAddfujianMlv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.content_tv)
    EditText contentTv;
    @BindView(R.id.znsccontent_count_tv)
    TextView znsccontentCountTv;
    @BindView(R.id.commit_tv)
    TextView commitTv;
    @BindView(R.id.content_clsb)
    RelativeLayout contentClsb;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_clsb_rootlayout)
    CoordinatorLayout activityClsbRootlayout;
    @BindView(R.id.webview)
    WebView webView;


    //接收上传附件完毕后的处理
    private CommonAdapter<FileBean> adapter;
    private List<FileBean> files = new ArrayList<FileBean>();

    private void removeFile(FileBean file) {
        removeFileByFileName(file.getFileName());
    }

    private void removeFileByFileName(String fileName) {
        if (null == files) {
            files = new ArrayList<>();
        }

        for (FileBean fileBean : files) {
            if (fileBean.getFileName().equals(fileName)) {
                files.remove(fileBean);
            }
        }
    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_CHOSEDFILE:
                            ((CLSBActivity) t).activityZncategoryAddfujianMlv.setAdapter(((CLSBActivity) t).adapter = new CommonAdapter<FileBean>(
                                    ((CLSBActivity) t).getApplicationContext(), ((CLSBActivity) t).files, R.layout.item_clsb_fujianfile
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final FileBean item) {
                                    ProgressBar progressBar = viewHolder.getView(R.id.fujianfile_pb);
                                    progressBar.setProgress((int) (100 * item.getProgress()));

                                    viewHolder.setText(R.id.fujianfile_filename_tv, item.getFileName());

                                    ImageView del = viewHolder.getView(R.id.fujianfile_del_iv);
                                    del.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            for (int i = 0; i < ((CLSBActivity) t).files.size(); i++) {
                                                if (((CLSBActivity) t).files.get(i).getFileName().equals(item.getFileName())) {
                                                    ((CLSBActivity) t).removeFileByFileName(item.getFileName());
                                                    i--;

                                                    Message msg = new Message();
                                                    msg.what = Constants.WHAT_LOADMORE;
                                                    ((CLSBActivity) t).handler.sendMessage(msg);
                                                }
                                            }
                                        }
                                    });
                                    if (item.getStatus() == -1) {
                                        del.setVisibility(View.VISIBLE);
                                        viewHolder.setText(R.id.fujianfile_status_tv, "上传失败");
                                    } else if (item.getStatus() == 1) {
                                        viewHolder.setText(R.id.fujianfile_status_tv, "上传成功");
                                        del.setVisibility(View.GONE);
                                    } else {
                                        viewHolder.setText(R.id.fujianfile_status_tv, "正在上传");
                                        del.setVisibility(View.GONE);
                                    }
                                }
                            });

                            ((CLSBActivity) t).webView.setVisibility(View.GONE);
                            ((CLSBActivity) t).activityZncategoryAddfujianMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //TODO:点击文件支持预览
//                                webView.setVisibility(View.VISIBLE);
//                                webView.loadUrl(files.get(position).getFilePath());
                                }
                            });
                            break;
                        case Constants.WHAT_LOADMORE:
                            if (null != ((CLSBActivity) t).adapter) {
                                ((CLSBActivity) t).adapter.notifyDataSetChanged();
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

    private MyHandler handler = new MyHandler(this);

    private HomeDetailListBean homeDetailListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clsb);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (null != getIntent()) {
            homeDetailListBean = (HomeDetailListBean) getIntent().getSerializableExtra("homeDetailListBean");
        }

        setTitle();
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText(Constants.PULISHTYPE_SB);
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("查看");
        }
    }


    /**
     * 提交
     * token 	是 	string 	token值
     * post_title 	是 	string 	文章标题
     * accessory 	否 	string 	附件地址 [{“title”:”文件名”,”url”:”xxxxxxxx”},{“title”:”文件名”,”url”:”xxxxxxxx”}]
     * m_post_text1 	否 	string 	内容
     * received_id 	是 	string 	上报对象 用户id
     */
    private void commit() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_clsb_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_POSTSUBMIT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        builder.add("post_title", titleEdt.getText().toString());
        if (StringUtil.isNotEmpty(contentTv.getText().toString())) {
            builder.add("m_post_text1", contentTv.getText().toString());
        }
        //附件地址
        if (null != files && files.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (FileBean fileBean : files) {
                sb.append("{\"title\":");
                sb.append("\"" + fileBean.getFileName() + "\"");
                sb.append(",");
                sb.append("\"url\":");
                sb.append(fileBean.getReturnName());
                sb.append("},");
            }
            String finalStr = sb.substring(0, sb.lastIndexOf(",")) + "]";
            builder.add("accessory", finalStr);
        }

        builder.add("received_id", homeDetailListBean.getUserId() + "");//分类id
        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
        }
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = INFO_WHAT;
                                msg.obj = "提交成功";
                                myHandler.sendMessage(msg);
                                CLSBActivity.this.finish();
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private boolean canCommit() {
        if (StringUtil.isEmpty(titleEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入标题");
            return false;
        }

        return true;
    }

    /******************************************上传文件****************************************************/
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"), Constants.FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            ToastUtils.showOneToast(this, "请安装文件管理器");
        }
    }

    /**
     * file    是  obj    文件对象（通过二进制数据流传输）
     * filetype  是  string     文件类型 video,audio,file
     */
    private void uploadFile(final File file, String filetype) {
        Map<String, String> map = new HashMap<>();
        map.put("filetype", filetype);
        if (isImg) {
            map = null;
        }

        try {
            OkHttpUtils.okGoUploadFile(this, map, file, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        System.out.println("str = " + s);
                        final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                        if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                            for (int i = 0; i < files.size(); i++) {
                                if (files.get(i).getFileName().equals(file.getName())) {
                                    files.get(i).setStatus(1);
                                    files.get(i).setReturnName(resInfo.getDataToStr());
                                }
                            }

                            Message msg = new Message();
                            msg.what = Constants.WHAT_CHOSEDFILE;
                            handler.sendMessage(msg);
                        } else {
                           /* removeFileByFileName(file.getName());

                            Message msg1 = new Message();
                            msg1.what = Constants.WHAT_LOADMORE;
                            handler.sendMessage(msg1);*/

                            uploadErrorUpdate(file);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                    //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    //                        mProgressBar.setProgress((int) (100 * progress));
                    //                        mTextView2.setText("已上传" + currentSize/1024/1024 + "MB, 共" + totalSize/1024/1024 + "MB;");
                    for (int i = 0; i < files.size(); i++) {
                        if (files.get(i).getFileName().equals(file.getName())) {
                            if (null != files.get(i)) {
                                files.get(i).setProgress(progress);
                                files.get(i).setStatus(0);
                                System.out.println("currentSize = " + currentSize + "---totalSize:" + totalSize);
                            }
                        }
                    }

                    Message msg = new Message();
                    msg.what = Constants.WHAT_CHOSEDFILE;
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    ToastUtils.showOneToast(MyApplication.getContext(), Constants.UPLOAD_FILETYPE_ERROR);
                    uploadErrorUpdate(file);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadErrorUpdate(File file) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFileName().equals(file.getName())) {
                files.get(i).setStatus(-1);
            }
        }
        Message msg = new Message();
        msg.what = Constants.WHAT_CHOSEDFILE;
        handler.sendMessage(msg);
    }


    private static final int PHOTO_REQUEST_GALLERY = 2000;// 从相册中选择

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.FILE_SELECT_CODE && resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(Constants.TAG, "File Uri: " + uri.toString());

            // Get the path
            String path = null;
            try {
                path = FileUtils.getPath(this, uri);

                if (StringUtil.isNotEmpty(SystemUtil.getInstance().checkFileType(path, Constants.FILE_TYPE))) {
                    ToastUtils.showOneToast(getApplicationContext(), SystemUtil.getInstance().checkFileType(path, Constants.FILE_TYPE));
                    return;
                }

                String filename = path.substring(path.lastIndexOf("/") + 1);
//                        textView1.setText(path);

                WeakReference<FileBean> wf = new WeakReference<FileBean>(new FileBean());
                wf.get().setFileName(filename);
                wf.get().setFilePath(uri.toString());
                files.add(wf.get());

                Message msg = new Message();
                msg.what = Constants.WHAT_CHOSEDFILE;
//                msg.obj = wf.get();
                handler.sendMessage(msg);
            } catch (URISyntaxException e) {
                Log.e("TAG", e.toString());
                //e.printStackTrace();
                path = "";
            }
            String filepath = path;
            Log.d(Constants.TAG, "File Path: " + path);
            // Get the file instance
            // File file = new File(path);
            // Initiate the upload

            uploadFile(new File(filepath), "file");
        } else if (requestCode == PHOTO_REQUEST_GALLERY) {

            String path = null;
            if (data == null) {
                return;
            }
            // 得到图片的全路径
            Uri uri = data.getData();
            path = UriUtils.getPath(this, uri);

            if (StringUtil.isEmpty(path)) {
                ToastUtils.showOneToast(getApplicationContext(), "请选择要上传的文件");
                return;
            }
            if (StringUtil.isNotEmpty(SystemUtil.getInstance().checkFileType(path, Constants.IMG_TYPE))) {
                ToastUtils.showOneToast(getApplicationContext(), SystemUtil.getInstance().checkFileType(path, Constants.IMG_TYPE));
                return;
            }

            //TODO:1期先不做
           /* if (StringUtil.isNotEmpty(uri + "")) {
                WeakReference<FileBean> wf = new WeakReference<FileBean>(new FileBean());
                wf.get().setFileName(filename);
                wf.get().setFilePath(uri.toString());
                files.add(wf.get());
                uploadFile(new File(path), "file");
            }*/
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /******************************************上传文件****************************************************/


    private boolean isImg = false;

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.addimg_iv, R.id.addfile_iv, R.id.commit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                CLSBActivity.this.finish();
                break;
            case R.id.head_right_tv:
                WeakReference<HomeDetailListBean> wf = new WeakReference<HomeDetailListBean>(new HomeDetailListBean());
                wf.get().setType(Constants.PULISHTYPE_ZN);
                wf.get().setTitle(homeDetailListBean.getTitle());
                wf.get().setUserId(homeDetailListBean.getUserId());
                Intent intent = new Intent(CLSBActivity.this, SBCKListActivity.class);
                intent.putExtra("homeDetailListBean", wf.get());
                startActivity(intent);
                break;
            case R.id.addimg_iv:
                isImg = true;
//                choseHeadImageFromGallery();
                showFileChooser();
                break;
            case R.id.addfile_iv:
                isImg = false;
                showFileChooser();
                break;
            case R.id.commit_tv:
                SoftInputUtil.hideSoftInput(CLSBActivity.this, commitTv);
                //提交
                DialogUtils.getInstance().AlertMessage(CLSBActivity.this, null, "确认提交？", "取消", "确定", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (canCommit()) {
                            commit();
                        }
                    }
                });
                break;
        }
    }

}
