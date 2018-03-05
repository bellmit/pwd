package com.newland.wstdd.find.find;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.newland.wstdd.R;

/**
 * 首页网格布局    通过SimpleAdapter适配器实现     GridView界面的实现
 * @author linh
 */
public class GridViewHomeAdapter {
	
	private Context context=null;
	private int[] businessImages=null;
	private String[] businessNames=null;
	
	public GridViewHomeAdapter(Context context){
		this.context=context;
		//8个图标
		this.businessImages=new int[]{R.drawable.find_fragment_grid_icon1,
				R.drawable.find_fragment_grid_icon2,R.drawable.find_fragment_grid_icon3,R.drawable.find_fragment_grid_icon4,R.drawable.find_fragment_grid_icon5,R.drawable.find_fragment_grid_icon6
				,R.drawable.find_fragment_grid_icon7,R.drawable.find_fragment_grid_icon8};
		this.businessNames=context.getResources().getStringArray(R.array.find_8icon);//通过读取xml资源文件实现
	}
	
	public SimpleAdapter getAdapter(){
		ArrayList<HashMap<String, Object>> items=new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < businessImages.length; i++) {
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("images", businessImages[i]);
			map.put("names", businessNames[i]);
			items.add(map);//加入到Arraylist中
		}
		SimpleAdapter adapter=new SimpleAdapter(context, items, R.layout.fragment_find_grid, 
				new String[]{"images","names"}, new int[]{R.id.iv_businessImage,R.id.tv_businessName});
		return adapter;
	}
}
