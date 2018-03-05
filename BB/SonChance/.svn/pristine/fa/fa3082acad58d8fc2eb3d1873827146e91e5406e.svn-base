package com.tastebychance.sonchance.personal.locate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.personal.locate.bean.GoodsReceiptInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;
import com.tastebychance.sonchance.view.CustomLoadingFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**新增收货地址
 *
 * 编辑收货地址
 * Created by shenbh on 2017/8/24.
 */

public class GoodsReceiptAddressAddOrEditActivity extends MyBaseActivity {

    private EditText person_address_add_username_edt;
    private RadioGroup person_address_add_sex_rg;
    private RadioButton person_address_add_sex_selected_rbt;

    private EditText person_address_add_tel_edt;
    private TextView person_address_add_address_tv;
    private EditText person_address_add_housenumber_edt;
    private CheckBox person_address_setdefault_cb;//设为默认地址
    private Button person_address_add_confirm_btn;

    private String longitude;
    private String latitude;
    private String sex = "0";//性别：男0，女1

    private GoodsReceiptInfo goodsReceiptInfo;//传递过来的值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_goodsreceiptaddress_addoredit);

        Intent intent = getIntent();
        if (null != intent && null != intent.getSerializableExtra("goodsReceiptInfo")){
            goodsReceiptInfo = (GoodsReceiptInfo) intent.getSerializableExtra("goodsReceiptInfo");
            longitude = goodsReceiptInfo.getLocation_longitude();
            latitude = goodsReceiptInfo.getLocation_latitude();
            sex = goodsReceiptInfo.getSex()+"";
        }

        bindViews();
        setTitle();
    }

    private void bindViews() {

        person_address_add_username_edt = (EditText) findViewById(R.id.person_address_add_username_edt);
        person_address_add_sex_rg = (RadioGroup) findViewById(R.id.person_address_add_sex_rg);
        person_address_add_sex_selected_rbt = (RadioButton) person_address_add_sex_rg.findViewById(person_address_add_sex_rg.getCheckedRadioButtonId());
        person_address_add_tel_edt = (EditText) findViewById(R.id.person_address_add_tel_edt);
        person_address_add_address_tv = (TextView) findViewById(R.id.person_address_add_address_tv);
        person_address_add_housenumber_edt = (EditText) findViewById(R.id.person_address_add_housenumber_edt);
        person_address_setdefault_cb = (CheckBox) findViewById(R.id.person_address_setdefault_cb);
        person_address_add_confirm_btn = (Button) findViewById(R.id.person_address_add_confirm_btn);

        //选择性别
        person_address_add_sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                person_address_add_sex_selected_rbt = (RadioButton) person_address_add_sex_rg.findViewById(checkedId);
                String content = person_address_add_sex_selected_rbt.getText().toString();
                if (content.equals("先生")){
                    sex = "0";
                }else{
                    sex = "1";
                }
            }
        });

        //点击从地图获取地址
        person_address_add_address_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsReceiptAddressAddOrEditActivity.this,MarkerAnimationActivity.class);
                startActivityForResult(intent, Constants.GETADDRESS_RETURNCODE);
            }
        });

        //点击确认提交到服务器
        person_address_add_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtil.isEmpty(person_address_add_username_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"用户名不能为空");
                    return;
                }

                if (StringUtil.isEmpty(person_address_add_tel_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"手机号码不能为空");
                    return;
                }
                if (!StringUtil.isMobileNO(person_address_add_tel_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"手机号码格式不正确");
                    return;
                }
                if (StringUtil.isEmpty(person_address_add_address_tv.toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"请选择收货地址");
                    return;
                }
                if (StringUtil.isEmpty(person_address_add_housenumber_edt.getText().toString())){
                    UiHelper.ShowOneToast(getApplicationContext(),"请设置门牌号");
                    return;
                }

                networkRequest();
                GoodsReceiptAddressAddOrEditActivity.this.finish();//需要在提交给服务器的时候就结束当前界面，让网络异步提交，否则会导致多次提交。
            }
        });

        initData();
    }

    /**
     * 初始化控件：编辑地址的数据
     */
    private void initData() {
        if (null != goodsReceiptInfo){
            person_address_add_username_edt.setText(goodsReceiptInfo.getUsername());
            if (goodsReceiptInfo.getSex() == 0){//男
                person_address_add_sex_rg.check(R.id.person_address_add_sex_man_rbt);
            }else{
                person_address_add_sex_rg.check(R.id.person_address_add_sex_lady_rbt);
            }
            person_address_add_tel_edt.setText(goodsReceiptInfo.getTel());
            person_address_add_address_tv.setText(goodsReceiptInfo.getVillage());
            //门牌号
            person_address_add_housenumber_edt.setText(goodsReceiptInfo.getAddress());
            person_address_setdefault_cb.setChecked(goodsReceiptInfo.getIs_check().equals("1"));
        }
    }

    /**
     * 设置标题
     */
    private void setTitle() {
        //动态设置状态栏下方的控件（view）的高度
        View view = (View) findViewById(R.id.view1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);

        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        TextView subTitle = (TextView) findViewById(R.id.head_center_subtitle);
        if (center_tv != null) {
            center_tv.setText("新增地址");
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    GoodsReceiptAddressAddOrEditActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.GETADDRESS_RETURNCODE && null != data){
            String address = data.getStringExtra("address");
            person_address_add_address_tv.setText(address);

            longitude = data.getStringExtra("latitude");
            latitude = data.getStringExtra("latitude");
        }

    }

    @Override
    public void networkRequest() {
        super.networkRequest();
        if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.root_layout),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }


        //取到已经保存的token（登录后的信息）
        String token = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), Constants.TEMP, "token");
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        String url =   UrlManager.URL_PERSON_ADDADDRESS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        /*username	是	string	收货人姓名
        tel	是	string	收货人手机号码
        address	是	string	收货地址
        is_check	是	int	是否设置为默认地址
        longitude	是	decimal	收货地址经度
        latitude	是	decimal	收货地址纬度
        village	是	string	收货地址所在小区
        token	是	string	token值
        sex	是	int	性别 0为男 1为女
        type	是	int	操作类型 1为新增 2为编辑
            */

        String username = person_address_add_username_edt.getText().toString();
        String tel = person_address_add_tel_edt.getText().toString();
        String address = person_address_add_housenumber_edt.getText().toString();
        String village = person_address_add_address_tv.getText().toString();
        boolean isDefault = person_address_setdefault_cb.isChecked();

        RequestBody formBody = null;
        if (null != goodsReceiptInfo){//不为空说明书是编辑，否则为增加
            CrashHandler.getInstance().handlerError("增加或编辑地址时formbody为空异常");
            formBody = new FormBody.Builder()
                    .add("id",goodsReceiptInfo.getId()+"")
                    .add("type","2")
                    .add("username",StringUtil.noNull(username))
                    .add("tel",StringUtil.noNull(tel))
                    .add("address",address)
                    .add("longitude",StringUtil.noNull(longitude))
                    .add("latitude",StringUtil.noNull(latitude))
                    .add("village",StringUtil.noNull(village))
//                    .add("city",StringUtil.isNotEmpty(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city"))
//                            ? SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city") : "福州市")
                    .add("token", token)
                    .add("sex",StringUtil.noNull(sex))
                    .add("is_check", (isDefault ? "1" : "0"))
                    .build();
        }else{
            formBody = new FormBody.Builder()
                    .add("type","1")
                    .add("username",StringUtil.noNull(username))
                    .add("tel",StringUtil.noNull(tel))
                    .add("address",StringUtil.noNull(address))
                    .add("longitude",StringUtil.noNull(longitude))
                    .add("latitude",StringUtil.noNull(latitude))
                    .add("village",StringUtil.noNull(village))
//                    .add("city",StringUtil.isNotEmpty(SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city"))
//                            ? SharedPreferencesUtils.getConfigStr(MyApplication.getContext(),Constants.TEMP,"city") : "福州市")
                    .add("token", token)
                    .add("sex",StringUtil.noNull(sex))
                    .add("is_check", (isDefault ? "1" : "0"))
                    .build();
        }

        if (null != formBody) {
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
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);
                    if (null != goodsReceiptInfo) {//不为空说明书是编辑，否则为增加
                        Log.i(Constants.TAG, "编辑地址成功");
                    }else{
                        Log.i(Constants.TAG, "增加地址成功");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != goodsReceiptInfo) {//不为空说明书是编辑，否则为增加
                                UiHelper.ShowOneToast(getApplicationContext(),"修改地址成功");
                            }else{
                                UiHelper.ShowOneToast(getApplicationContext(),"增加地址成功");
                            }
                        }
                    });
                }
            });
        } else {
            Log.e(Constants.TAG, "增加或编辑地址时formbody为空异常");
            CrashHandler.getInstance().handlerError("增加或编辑地址时formbody为空异常");
        }
    }


}
