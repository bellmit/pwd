package com.tastebychance.sfj.filedealwith.writefile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.adapter.CommonAdapter;
import com.tastebychance.sfj.common.adapter.ViewHolder;
import com.tastebychance.sfj.filedealwith.writefile.obtainpics.PhotoAdapter;
import com.tastebychance.sfj.filedealwith.writefile.obtainpics.RecyclerItemClickListener;
import com.tastebychance.sfj.mine.contacts.ContactsActivity;
import com.tastebychance.sfj.mine.contacts.bean.ContactShowBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.MyListView;
import com.tastebychance.sfj.view.MyRecyclerView;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 写文件
 *
 * @author shenbinghong
 * @time 2018/2/3 14:26
 * @path com.tastebychance.sfj.filedealwith.writefile.WriteFileActivity.java
 */
public class WriteFileActivity extends MyAppCompatActivity {

    @BindView(R.id.mine_topblank_iv)
    View mineTopblankIv;
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.mlv)
    MyListView mlv;
    @BindView(R.id.activity_writefile_addreceiver_tv)
    TextView activityWritefileAddreceiverTv;
    @BindView(R.id.activity_writefile_goon_addreceiver_tv)
    TextView activityWritefileGoonAddreceiverTv;
    @BindView(R.id.activity_writefile_title_edt)
    EditText activityWritefileTitleEdt;
    @BindView(R.id.activity_writefile_content_edt)
    EditText activityWritefileContentEdt;

    private static class MyHandler<T extends Activity> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t){
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_POSTDATA:
                            ToastUtils.showOneToast(t.getApplicationContext(), Constants.UPLOAD_SUCCES);
                            t.finish();
                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private MyHandler handler = new MyHandler(this);

    //图片选择
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_file);
        ButterKnife.bind(this);

        activityWritefileAddreceiverTv.setVisibility(View.VISIBLE);
        activityWritefileGoonAddreceiverTv.setVisibility(View.GONE);
        setTitle();


        /**-----------------------图片选择-------------------------*/
        MyRecyclerView recyclerView = findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD){
                    PhotoPicker.builder()
                            .setPhotoCount(PhotoAdapter.MAX)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .setSelected(selectedPhotos)
                            .start(WriteFileActivity.this);
                } else {
                    PhotoPreview.builder()
                            .setPhotos(selectedPhotos)
                            .setCurrentItem(position)
                            .start(WriteFileActivity.this);
                }
            }
        }));
        /**-----------------------图片选择-------------------------*/
    }

    private List<ContactShowBean> contactBeans = new ArrayList<>();
    private CommonAdapter<ContactShowBean> contactAdapter;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Constants.RET_CHOOCECONTACT){
            List<ContactShowBean> showBeans = (List<ContactShowBean>) data.getSerializableExtra(Constants.KEY_CHOOCECONTACT);
            if (showBeans != null && showBeans.size() > 0){
                if (null == contactBeans){
                    contactBeans = new ArrayList<>();
                }
                if (null != contactBeans){
                    contactBeans.clear();
                }

                contactBeans.addAll(showBeans);


                if (contactBeans.size() > 0){
                    activityWritefileAddreceiverTv.setVisibility(View.INVISIBLE);
                    activityWritefileGoonAddreceiverTv.setVisibility(View.VISIBLE);
                }

                mlv.setAdapter(contactAdapter = new CommonAdapter<ContactShowBean>(
                        getApplicationContext(), contactBeans, R.layout.item_writefile_contact
                ) {
                    @Override
                    protected void convert(ViewHolder viewHolder, final ContactShowBean item) {
                        ImageView delIv = viewHolder.getView(R.id.item_writefile_contact_delete_iv);
                        viewHolder.setText(R.id.item_writefile_contact_name_tv, item.getName());
                        delIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                contactBeans.remove(item);
                                if (contactBeans.size() == 0){
                                    activityWritefileAddreceiverTv.setVisibility(View.VISIBLE);
                                    activityWritefileGoonAddreceiverTv.setVisibility(View.GONE);
                                }
                                contactAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

                if (null !=  contactAdapter) {
                    contactAdapter.notifyDataSetChanged();
                }
            }
        }


        /**-----------------------图片选择-------------------------*/
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)){
            List<String> photos = null;
            if (data != null){
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null){
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
        /**-----------------------图片选择-------------------------*/
    }

    private void setTitle() {
        headCenterTitleTv.setText("写文件");
        headRightTv.setVisibility(View.VISIBLE);
        headRightTv.setText("发送");
    }

    private boolean canSend(){
        if (contactBeans == null || contactBeans.size() <= 0){
            ToastUtils.showOneToast(getApplicationContext(), Constants.NULL_RECEIVER);
            return false;
        }
        if (TextUtils.isEmpty(activityWritefileTitleEdt.getText().toString())){
            ToastUtils.showOneToast(getApplicationContext(), Constants.NULL_TITLE);
            return false;
        }

        //收件人
        if (null == contactBeans || contactBeans.size() <= 0){
            ToastUtils.showOneToast(getApplicationContext(), Constants.CONTACTS_NULL);
            return false;
        }
        return true;
    }

    /**
     token	是	string	token值
     title	是	string	标题
     content	否	string	内容
     image	否	string	图片 src:src:src
     user_ids	是	string	收件人id串 例 23,24,25
     */
    private void send(){
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.acitivity_writefile_rootlayout));

        final String url = UrlManager.URL_WRITEFILE;
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("title", activityWritefileTitleEdt.getText().toString());
        if (!TextUtils.isEmpty(activityWritefileContentEdt.getText().toString())) {
            map.put("content", activityWritefileContentEdt.getText().toString());
        }
        map.put("user_ids", getContacts());

        if (null != selectedPhotos && selectedPhotos.size() <= 0) {
            CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        CommonOkGo.getInstance().dialogCancel();

                        final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                        if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {
                            System.out.println(Thread.currentThread().getId() + Thread.currentThread().getName());
                            Message msg = new Message();
                            msg.what = Constants.WHAT_POSTDATA;
                            msg.obj = resInfo;
                            handler.sendMessage(msg);
                        }else {
                            Message msg = new Message();
                            msg.what = CommonOkGo.ERROR_WHAT;
                            msg.obj = resInfo.getError_message();
                            CommonOkGo.getInstance().myHandler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    CommonOkGo.getInstance().dialogCancel();
                    super.onError(call, response, e);
                }
            });
        } else {
            CommonOkGo.getInstance().uploadFiles(url, map, "image[]", getPhotos(), new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        CommonOkGo.getInstance().dialogCancel();
                        final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                        if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    msg.what = Constants.WHAT_POSTDATA;
                                    msg.obj = resInfo;
                                    handler.sendMessage(msg);
                                }
                            });
                        } else {
                            Message msg = new Message();
                            msg.what = CommonOkGo.ERROR_WHAT;
                            msg.obj = resInfo.getError_message();
                            CommonOkGo.getInstance().myHandler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    CommonOkGo.getInstance().dialogCancel();
                    super.onError(call, response, e);
                }
            });
        }
    }

    private String getContacts(){
        StringBuffer sb = new StringBuffer();
        for (ContactShowBean contactShowBean : contactBeans){
            sb.append(contactShowBean.getId() + ",");
        }
        String finalStr = sb.substring(0, sb.lastIndexOf(","));
        return finalStr;
    }


    private List<File> getPhotos(){
        if (null == selectedPhotos){
            return null;
        }
        List<File> list = new ArrayList<>();
        for (String str: selectedPhotos) {
            list.add(new File(str));
        }
        return list;
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv, R.id.activity_writefile_addreceiver_tv, R.id.activity_writefile_goon_addreceiver_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.head_left_iv:
                WriteFileActivity.this.finish();
                break;
            case R.id.head_right_tv:
                if (canSend()){
                    send();
                }
                break;
            case R.id.activity_writefile_addreceiver_tv:
            case R.id.activity_writefile_goon_addreceiver_tv:
                intent = new Intent(WriteFileActivity.this, ContactsActivity.class);
                intent.putExtra(Constants.KEY_FROM, Constants.FROM_WRITEFILE);
                intent.putExtra(Constants.KEY_CHOOCECONTACT, (Serializable) contactBeans);
                startActivityForResult(intent, 1);
                break;
            default:break;
        }
    }
}
