package com.newland.wstdd.find.find;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newland.wstdd.R;

public class FragmentOne extends Fragment {

	private String text = "";
	private int imgId;
	private Toast toast;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		imgId= args !=null ? args.getInt("id") : R.drawable.ic_launcher;
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 第一个参数是这个Fragment将要显示的界面布局,第二个参数是这个Fragment所属的Activity,第三个参数是决定此fragment是否附属于Activity
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment, container, false);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toast == null) {
		            toast = Toast.makeText(getActivity(), "点击选择了第"+text+"个页面", Toast.LENGTH_SHORT);
		        } else {
		            toast.setText("点击选择了第"+text+"个页面");
		        }
				toast.show();
			}
		});
		TextView tv_fragment = (TextView) view.findViewById(R.id.text);
		ImageView imageView=(ImageView)view.findViewById(R.id.imageView1);
		imageView.setImageDrawable(getResources().getDrawable(imgId));
		tv_fragment.setText(text);
		return view;
	}

}
