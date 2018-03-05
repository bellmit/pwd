package com.newland.wstdd.message;

import com.newland.wstdd.R;
import com.newland.wstdd.R.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 消息的界面
 * @author Administrator
 *
 */
public class MessageFragment extends Fragment {
	private View view;

	@Override   
	public void onCreate(Bundle savedInstanceState)    {    
		// TODO Auto-generated method stub    
		super.onCreate(savedInstanceState);   
		System.out.println("ExampleFragment--onCreate");   
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 if (view != null) {
		        ViewGroup parent = (ViewGroup) view.getParent();
		        if (parent != null)
		            parent.removeView(view);
		    }else{
		    	try {
					view = inflater.inflate(R.layout.fragment_message, container, false);
				} catch (Exception e) {
					// TODO: handle exception
				}
		    }
	
		
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		
	}
	
	
}
