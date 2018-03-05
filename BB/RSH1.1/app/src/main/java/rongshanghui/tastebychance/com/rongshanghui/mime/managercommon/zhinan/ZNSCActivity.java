package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNFJFileBeans;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.CommonAdapter;
import rongshanghui.tastebychance.com.rongshanghui.common.adapter.ViewHolder;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean.ZNCategoryBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.FileUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SoftInputUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.util.network.OkHttpUtils;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import rongshanghui.tastebychance.com.rongshanghui.view.MyListView;

/**
 * 类描述：ZNSCActivity 我的-管理-指南管理（指南上传）
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/6 20:03
 * 修改人：
 * 修改时间：2017/12/6 20:03
 * 修改备注：
 *
 * @version 1.0
 */
public class ZNSCActivity extends MyAppCompatActivity {


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
    @BindView(R.id.znsc_title_edt)
    EditText znscTitleEdt;
    @BindView(R.id.znsc_add_num_tv)
    TextView znscAddNumTv;
    @BindView(R.id.znsc_add_iv)
    ImageView znscAddIv;
    @BindView(R.id.znsc_add_rl)
    RelativeLayout znscAddRl;
    @BindView(R.id.activity_zncategory_addfujian_mlv)
    MyListView activityZncategoryAddfujianMlv;
    @BindView(R.id.znsc_add_ll)
    LinearLayout znscAddLl;
    @BindView(R.id.znsc_content_tv)
    EditText znscContentTv;
    @BindView(R.id.znsccontent_count_tv)
    TextView znsccontentCountTv;
    @BindView(R.id.znsc_chosetype_name_tv)
    TextView znscChosetypeNameTv;
    @BindView(R.id.znsc_chosetype_iv)
    ImageView znscChosetypeIv;
    @BindView(R.id.znsc_chosetype_rl)
    RelativeLayout znscChosetypeRl;
    @BindView(R.id.znsc_commit_tv)
    TextView znscCommitTv;
    @BindView(R.id.content_znsc)
    RelativeLayout contentZnsc;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_znsc_rootlayout)
    CoordinatorLayout activityZnscRootlayout;

    //接收上传附件完毕后的处理
    private CommonAdapter<ZNFJFileBeans.FileBean> adapter;


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
                            ((ZNSCActivity) t).activityZncategoryAddfujianMlv.setAdapter(((ZNSCActivity) t).adapter = new CommonAdapter<ZNFJFileBeans.FileBean>(
                                    t.getApplicationContext(), ZNFJFileBeans.getInstance().getFiles(), R.layout.item_zncategory_fujian
                            ) {
                                @Override
                                protected void convert(ViewHolder viewHolder, final ZNFJFileBeans.FileBean item) {
                                    viewHolder.setText(R.id.znsc_fujian_name_tv, item.getFileName());

                                    ImageView del = viewHolder.getView(R.id.znsc_fujian_del_iv);
                                    del.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            for (int i = 0; i < ZNFJFileBeans.getInstance().getFiles().size(); i++) {
                                                if (ZNFJFileBeans.getInstance().getFiles().get(i).getFileName().equals(item.getFileName())) {
                                                    ZNFJFileBeans.getInstance().removeItem(item.getFileName());
                                                    i--;

                                                    Message msg = new Message();
                                                    msg.what = Constants.WHAT_LOADMORE;
                                                    ((ZNSCActivity) t).handler.sendMessage(msg);
                                                }
                                            }
                                        }
                                    });
                                }
                            });

                            ((ZNSCActivity) t).activityZncategoryAddfujianMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            });
                            break;
                        case Constants.WHAT_LOADMORE:
                            if (null != ((ZNSCActivity) t).adapter) {
                                ((ZNSCActivity) t).adapter.notifyDataSetChanged();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znsc);
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

        setTitle();
        initView();
    }

    private void initView() {
        znscChosetypeNameTv.setText(StringUtil.setTextSizeColor("选择分类（未选择）", getResources().getColor(R.color.colorPrimaryDark), 5, 9, 12));
//        znscContentTv.addTextChangedListener(mTextWatcher);
    }

    private void setTitle() {
        if (null != headCenterTitleTv) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("指南管理");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("查看");
        }
    }

    /**
     * 提交
     * token 	是 	string 	token
     * post_title 	是 	string 	指南标题
     * m_post_text1 	否 	string 	内容
     * accessory 	否 	string 	附件地址
     * column_id 	是 	int 	分类id
     */
    private void commit() {
        if (null == loadingBar) {
            loadingBar = LoadingBar.make(findViewById(R.id.activity_znsc_rootlayout), new CustomLoadingFactory());
        }
        if (null != loadingBar) {
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }

        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_GUIDEUPLOAD;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", SystemUtil.getInstance().getToken());
        builder.add("post_title", znscTitleEdt.getText().toString());
        if (StringUtil.isNotEmpty(znscContentTv.getText().toString())) {
            builder.add("m_post_text1", znscContentTv.getText().toString());
        }
        //附件地址
        if (null != ZNFJFileBeans.getInstance().getFiles() && ZNFJFileBeans.getInstance().getFiles().size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (ZNFJFileBeans.FileBean fileBean : ZNFJFileBeans.getInstance().getFiles()) {
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

        builder.add("column_id", znCategoryBean.getId() + "");//分类id
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

                Message msg = new Message();
                msg.what = INFO_WHAT;
                msg.obj = Constants.UPLOAD_FAIL;
                myHandler.sendMessage(msg);
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
                                ZNSCActivity.this.finish();
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
        if (StringUtil.isEmpty(znscTitleEdt.getText().toString())) {
            ToastUtils.showOneToast(getApplicationContext(), "请输入标题");
            return false;
        }

        //请选择分类
        if (null == znCategoryBean) {
            ToastUtils.showOneToast(getApplicationContext(), "请选择分类");
            return false;
        }

        return true;
    }

    /*--------------------------------------- 监听输入字数 ------------------------------------------*/
    private static final int MAX_COUNT = 240;
    private TextWatcher mTextWatcher = new TextWatcher() {

        private int editStart;

        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = znscContentTv.getSelectionStart();
            editEnd = znscContentTv.getSelectionEnd();

            znscContentTv.removeTextChangedListener(mTextWatcher);
            while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            znscContentTv.setText(s);
            znscContentTv.setSelection(editStart);

            //  znscContentTv.addTextChangedListener(mTextWatcher);

            setLeftCount();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    };

    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private void setLeftCount() {
        znsccontentCountTv.setText("(" + getInputCount() + "/" + MAX_COUNT + ")");
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(znscContentTv.getText().toString());
    }
    /*--------------------------------------- 监听输入字数 ------------------------------------------*/


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
        try {
            OkHttpUtils.okGoUploadFile(this, map, file, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        System.out.println("str = " + s);
                        final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                        if (resInfo.getCode() == Constants.RESULT_SUCCESS) {
                            WeakReference<ZNFJFileBeans.FileBean> wf = new WeakReference<ZNFJFileBeans.FileBean>(new ZNFJFileBeans.FileBean());
                            wf.get().setFileName(file.getName());
                            wf.get().setReturnName(resInfo.getDataToStr());
                            ZNFJFileBeans.getInstance().addItem(wf.get());
                        } else {
                            ZNFJFileBeans.getInstance().removeItem(file.getName());

                            Message msg = new Message();
                            msg.what = ERROR_WHAT;
                            msg.obj = resInfo.getError_message();
                            myHandler.sendMessage(msg);

                            Message msg1 = new Message();
                            msg1.what = Constants.WHAT_LOADMORE;
                            handler.sendMessage(msg1);
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
                    System.out.println("currentSize = " + currentSize + "---totalSize:" + totalSize);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    ToastUtils.showOneToast(MyApplication.getContext(), Constants.UPLOAD_FILETYPE_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /******************************************上传文件****************************************************/


    private ZNCategoryBean znCategoryBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Constants.GETCATEGORY_RETURNCODE) {
            if (null != data) {
                znCategoryBean = (ZNCategoryBean) data.getSerializableExtra("znCategoryBean");
                znscChosetypeNameTv.setText(znCategoryBean.getName());
            }
        }

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

                WeakReference<ZNFJFileBeans.FileBean> wf = new WeakReference<ZNFJFileBeans.FileBean>(new ZNFJFileBeans.FileBean());
                wf.get().setFileName(filename);
                ZNFJFileBeans.getInstance().addItem(wf.get());

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
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.znsc_add_rl, R.id.znsc_chosetype_rl, R.id.znsc_commit_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                ZNSCActivity.this.finish();
                break;
            case R.id.head_right_tv:
                //查看
                intent = new Intent(ZNSCActivity.this, ZNCKActivity.class);
                startActivity(intent);
                break;
            case R.id.znsc_add_rl:
                //上传附件
                showFileChooser();
                break;
            case R.id.znsc_chosetype_rl:
                //选择分类
                intent = new Intent(ZNSCActivity.this, ZNCategoryListActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.znsc_commit_tv:
                SoftInputUtil.hideSoftInput(ZNSCActivity.this, znscCommitTv);
                //提交
                if (canCommit()) {
                    commit();
                }
                break;
        }
    }
}
