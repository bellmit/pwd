/**
 * 
 */
package com.newland.wstdd.find.categorylist.detail.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;

/**发现-8类-详情-我要报名-编辑报名界面
 * @author H81 2015-11-23
 *
 */
public class EditRegistrationActivity extends BaseFragmentActivity implements OnClickListener{

	private TddActivity tddActivity;
	
	private TextView mActivity_mine_personalcenter_icon_tv;//标题
	private EditText mRegistreation_name;//姓名
	private EditText mRegistreation_phone;//手机
	private EditText mRegistreation_department;//部门
	private EditText mRegistreation_mail;//邮箱
	private ExpandableListView mRegistration_listview;
	private TextView mRegistration_add_people;//添加随行人员
	
	/**添加随行人员*/
	List<String> groupList = new ArrayList<String>();//组集合
	List<Map<String, String>> subList = new ArrayList<Map<String,String>>();//子集合
	String groupString ;
	/**添加随行人员*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getIntentData();
		setContentView(R.layout.activity_editregistration);
		initView();
		initTitle();
	}
	
	private void getIntentData() {
		tddActivity = (TddActivity) getIntent().getSerializableExtra("tddActivity");
	}
	
	/**设置标题
	 * 
	 */
	private void initTitle() {
		ImageView leftIv = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		ImageButton rightBtn = (ImageButton) findViewById(R.id.head_right_btn);
		leftIv.setVisibility(View.VISIBLE);
		centerTitle.setText(StringUtil.intType2Str(tddActivity.getActivityType()) + "报名");
		rightTv.setText("提交");
		rightTv.setVisibility(View.VISIBLE);
		rightBtn.setVisibility(View.GONE);
		rightBtn.setImageDrawable(getResources().getDrawable(R.drawable.find));
		leftIv.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}
	

    public void initView() {
        mActivity_mine_personalcenter_icon_tv = (TextView) findViewById(R.id.activity_mine_personalcenter_icon_tv);
        mRegistreation_name = (EditText) findViewById(R.id.registreation_name);
        mRegistreation_phone = (EditText) findViewById(R.id.registreation_phone);
        mRegistreation_department = (EditText) findViewById(R.id.registreation_department);
        mRegistreation_mail = (EditText) findViewById(R.id.registreation_mail);
        mRegistration_listview = (ExpandableListView) findViewById(R.id.registration_listview);
        mRegistration_add_people = (TextView) findViewById(R.id.registration_add_people);
        mActivity_mine_personalcenter_icon_tv.setText(tddActivity.getActivityTitle());
        mRegistreation_name.setText(tddActivity.getUserName());
        mRegistreation_phone.setText(tddActivity.getUserMobilePhone());
        mRegistreation_department.setText("");
        mRegistreation_mail.setText("");
        
        mRegistration_add_people.setOnClickListener(this);
    }
    
	@Override
	protected void processMessage(Message msg) {
		
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.head_left_iv://返回
			finish();
			break;
		case R.id.head_right_tv://提交
			UiHelper.ShowOneToast(EditRegistrationActivity.this, "提交");
			break;
		case R.id.registration_add_people://添加随行人员
			UiHelper.ShowOneToast(EditRegistrationActivity.this, "添加随行人员");
			//进行判断，只有添加完最后一个才可以进行下一个的判断
//			if(registrationEditAdapterDatas.get(registrationEditAdapterDatas.size()-1).getName()==null&&
//			registrationEditAdapterDatas.get(registrationEditAdapterDatas.size()-1).getPhone()==null){
//				UiHelper.ShowOneToast(this, "还有随行人员信息未填！无法添加");
//			}else {
//				RegistrationEditAdapterData data=new RegistrationEditAdapterData();
//				data.setName(null);
//				data.setPhone(null);
//				data.setDepartment(null);
//				data.setEmail(null);
//				registrationEditAdapterDatas.add(data);
//				registrationEditAdapter.notifyDataSetChanged();
//			}
			
			groupList.add(groupString);
			break;
		default:
			break;
		}
	}
}
