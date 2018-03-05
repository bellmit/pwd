package com.tastebychance.sfj.mine.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.web.bean.ShowWebBean;
import com.tastebychance.sfj.mine.contacts.bean.ContactShowBean;
import com.tastebychance.sfj.mine.contacts.bean.ContactsBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.ToastUtils;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tastebychance.sfj.view.QuickIndexBar;
import com.tastebychance.sfj.view.SearchIconClearEditText;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 通讯录
 *
 * @author shenbinghong
 * @time 2018/1/31 16:50
 * @path com.tastebychance.sfj.mine.contacts.ContactsActivity.java
 */
public class ContactsActivity extends MyAppCompatActivity {

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
    @BindView(R.id.filter_edit)
    SearchIconClearEditText filterEdit;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.quickIndexBar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.currentWord)
    TextView currentWord;

    private ContactsAdapter adapter;
    private List<ContactShowBean> showList = new ArrayList<ContactShowBean>();

    private static class MyHandler<T extends Activity> extends MyBaseHandler {
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
                        case Constants.WHAT_GETDATA:
                            Map<String, Object> map = (Map<String, Object>) JSONObject.parseObject(resInfo.convertDataToStr());

                            Set<Map.Entry<String, Object>> set = map.entrySet();
                            List<ContactsBean> list = new ArrayList<ContactsBean>();

                            for (Map.Entry<String, Object> entry : set) {
                                WeakReference<ContactsBean> wf = new WeakReference<ContactsBean>(new ContactsBean());
                                wf.get().setFirstWord(entry.getKey());
                                wf.get().setContacts(JSON.parseArray(JSON.toJSONString(entry.getValue()), ContactsBean.ContactBean.class));
                                list.add(wf.get());
                            }

                            for (ContactsBean contactsBean : list) {
                                for (ContactsBean.ContactBean contactBean : contactsBean.getContacts()) {
                                    WeakReference<ContactShowBean> wf = new WeakReference<ContactShowBean>(new ContactShowBean());
                                    wf.get().setFirstWord(contactsBean.getFirstWord());
                                    wf.get().setName(contactBean.getName());
                                    wf.get().setId(contactBean.getId());
                                    wf.get().setDetail(contactBean.getDetail());
                                    ((ContactsActivity) t).showList.add(wf.get());
                                }
                            }

                            //设置初始选中
                            if (((ContactsActivity)t).choosedContacts != null){
                                for (int i = 0; i < ((ContactsActivity)t).choosedContacts.size(); i++) {
                                    for (int j = 0; j < ((ContactsActivity)t).showList.size(); j++) {
                                        if (((ContactsActivity)t).choosedContacts.get(i).getName().equals(((ContactsActivity)t).showList.get(j).getName())){
                                            ((ContactsActivity)t).showList.get(j).setChoosed(true);
                                        }
                                    }
                                }
                            }

                            //空布局
                            if (((ContactsActivity) t).emptyLayout != null) {
                                if (list.size() == 0) {
                                    emptyLayoutShowOrHide(((ContactsActivity) t).emptyLayout, true);
                                } else {
                                    emptyLayoutShowOrHide(((ContactsActivity) t).emptyLayout, false);
                                }
                            }

                            //2、排序
                            Collections.sort(((ContactsActivity) t).showList);

                            //3、设置adapter
                            ((ContactsActivity) t).listview.setAdapter(((ContactsActivity) t).adapter = new ContactsAdapter(t, ((ContactsActivity) t).showList));
                            //4、点击右侧快速导航字母
                            ((ContactsActivity) t).touchQuickIndexBar(((ContactsActivity) t).showList);


                            if (!TextUtils.isEmpty(((ContactsActivity) t).from) && ((ContactsActivity) t).from.equals(Constants.FROM_WRITEFILE)) {
                                if (null != ((ContactsActivity) t).adapter) {
                                    ((ContactsActivity) t).adapter.setToShowCb(true);
                                    ((ContactsActivity) t).adapter.notifyDataSetChanged();
                                }
                            } else {
                                if (null != ((ContactsActivity) t).adapter) {
                                    ((ContactsActivity) t).adapter.setToShowCb(false);
                                    ((ContactsActivity) t).adapter.notifyDataSetChanged();
                                }
                            }

                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private EmptyLayout emptyLayout;
    private String from;
    private List<ContactShowBean> choosedContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        emptyLayout =  findViewById(R.id.emptyLayout);

        if (getIntent() != null) {
            from = getIntent().getStringExtra(Constants.KEY_FROM);
            try {
                choosedContacts = (List<ContactShowBean>) getIntent().getSerializableExtra(Constants.KEY_CHOOCECONTACT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setListener();

        setTitle();
        initObject();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    private void initObject() {

        filterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        characterParser = CharacterParser.getInstance();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactShowBean> filterDataList = new ArrayList<ContactShowBean>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDataList = showList;
        } else {
            filterDataList.clear();
            for (ContactShowBean tempList : showList) {
                String name = tempList.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDataList.add(tempList);
                }
            }
        }

        //根据a-z进行排序
        Collections.sort(filterDataList);
        if (null != adapter) {
            adapter.updateListView(filterDataList);
        }

        //空布局
        if (emptyLayout != null) {
            if (filterDataList.size() == 0) {
                emptyLayout.showEmpty(R.drawable.nothing, "");
            } else {
                emptyLayout.hide();
            }
        }
    }

    private void touchQuickIndexBar(final List<ContactShowBean> list) {
        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                // 根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
                for (int i = 0; i < list.size(); i++) {
                    String firstWord = list.get(i).getFirstWord();
                    if (letter.equals(firstWord)) {
                        // 说明找到了，那么应该讲当前的item放到屏幕顶端
                        listview.setSelection(i);
                        break;//只需要找到第一个就行
                    }
                }

                if (letter.equals("☆")) {
                    listview.setSelection(0);
                }
                // 显示当前触摸的字母
                showCurrentWord(letter);
            }
        });

        // 通过缩小currentWord来隐藏
        ViewHelper.setScaleX(currentWord, 0);
        ViewHelper.setScaleY(currentWord, 0);
    }

    private boolean isScale = false;
    private Handler handler = new Handler();

    protected void showCurrentWord(String letter) {
        currentWord.setText(letter);
        if (!isScale) {
            isScale = true;
            ViewPropertyAnimator.animate(currentWord).scaleX(1f).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
            ViewPropertyAnimator.animate(currentWord).scaleY(1f).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
        }

        // 先移除之前的任务
        handler.removeCallbacksAndMessages(null);

        // 延时隐藏currentWord
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // currentWord.setVisibility(View.INVISIBLE);
                ViewPropertyAnimator.animate(currentWord).scaleX(0f).setDuration(450).start();
                ViewPropertyAnimator.animate(currentWord).scaleY(0f).setDuration(450).start();
                isScale = false;
            }
        }, 1500);
    }

    /**
     * token	是	string	token值
     * keyword	是	string	关键字搜索
     */
    private void getData() {
        if (null != emptyLayout) {
            emptyLayout.showLoading();
        }
        final String url = UrlManager.URL_MINE_CONTACT;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("keyword", filterEdit.getText().toString());
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = Constants.WHAT_GETDATA;
                                msg.obj = resInfo;
                                mHandler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);

                        if (null != emptyLayout) {
                            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
                            emptyLayout.showEmpty(R.drawable.nothing, "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);

                if (emptyLayout != null) {
                    emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                    emptyLayout.showError();
                }
            }
        });
    }

    private void setListener() {
        if (!TextUtils.isEmpty(from) && from.equals(Constants.FROM_WRITEFILE)) {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (null != showList && showList.size() > position && null != adapter) {
                        adapter.setSelectItem(position);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WeakReference<ShowWebBean> wf = new WeakReference<ShowWebBean>(new ShowWebBean());
                    wf.get().setUrl(showList.get(position).getDetail());
                    wf.get().setTitle("详情");
                    SystemUtil.getInstance().intentToWebInfoActivity(ContactsActivity.this, wf.get());
                }
            });
        }


    }

    private void setTitle() {
        headCenterTitleTv.setText("通讯录");
        if (!TextUtils.isEmpty(from) && from.equals(Constants.FROM_WRITEFILE)) {
            headRightTv.setVisibility(View.VISIBLE);
            headRightTv.setText("完成");
        } else {
            headRightTv.setVisibility(View.GONE);
        }
    }


    /**
     * 全选
     */
    public void btnSelectAllList() {
        if (null != adapter && adapter.isToShowCb()) {
            for (int i = 0; i < showList.size(); i++) {
                showList.get(i).setChoosed(true);
            }

            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 全不选
     */
    public void btnNoList() {

        if (null != adapter && adapter.isToShowCb()) {
            for (int i = 0; i < showList.size(); i++) {
                showList.get(i).setChoosed(false);
            }

            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取选中数据
     */
    public String btnOperateList() {
        String retIds = "";

        if (null != adapter && adapter.isToShowCb()) {
            StringBuilder ids = new StringBuilder();

            for (int i = 0; i < showList.size(); i++) {
                if (showList.get(i).isChoosed()) {
                    ids.append(String.valueOf(showList.get(i).getId()));
                    ids.append(",");
                }
            }

            if (ids.length() > 0) {
                retIds = ids.substring(0, ids.lastIndexOf(","));
            }
            if (Constants.IS_DEVELOPING){
                Toast.makeText(ContactsActivity.this,retIds, Toast.LENGTH_SHORT).show();
            }
            Log.e("TAG", retIds);
        }

        return retIds;
    }

    @OnClick({R.id.head_left_iv, R.id.head_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                finish();
                break;
            case R.id.head_right_tv:
                if (null != adapter) {
                    if (TextUtils.isEmpty(btnOperateList())) {
                        ToastUtils.showOneToast(ContactsActivity.this, Constants.CONTACTS_NULL);
                        return;
                    }

                    List<ContactShowBean> contactShowBeans = adapter.getSelectedData();
                    Intent intent = getIntent();
                    intent.putExtra(Constants.KEY_CHOOCECONTACT, (Serializable) contactShowBeans);
                    setResult(Constants.RET_CHOOCECONTACT, intent);
                    ContactsActivity.this.finish();
                }
                break;
            default:break;
        }
    }
}
