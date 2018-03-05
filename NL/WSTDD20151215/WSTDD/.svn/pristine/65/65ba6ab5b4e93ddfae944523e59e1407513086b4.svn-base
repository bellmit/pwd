package com.newland.wstdd.find.categorylist.registrationedit.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.newland.wstdd.R;
import com.newland.wstdd.find.categorylist.registrationedit.tests.TreeViewAdapter.TreeNode;

@SuppressWarnings({"unused", "rawtypes"})
public class EditRegistrationActivity extends Activity {
	EditRegistrationExpandListView expandableList;
	TreeViewAdapter adapter;
	
	public List<HashMap<String, Object>> categories;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration);
        setCategoris();
        adapter = new TreeViewAdapter(this);
		expandableList = (EditRegistrationExpandListView) findViewById(R.id.category_items);
		expandableList.setGroupIndicator(null);
		displayCategories();
    }
    
	public void displayCategories() {
		adapter.RemoveAll();
		adapter.notifyDataSetChanged();

		List<TreeViewAdapter.TreeNode> treeNode = adapter.GetTreeNode();
		for (int i = 0; i < categories.size(); i++) {
			TreeViewAdapter.TreeNode node = new TreeViewAdapter.TreeNode();
			node.parent = categories.get(i);
			List child = ((ArrayList) categories.get(i).get("children"));
			for (int ii = 0; ii < child.size(); ii++) {
				node.childs.add(child.get(ii));
			}
			treeNode.add(node);
		}

		adapter.UpdateTreeNode(treeNode);
		expandableList.setAdapter(adapter);
		
		expandableList.setOnGroupExpandListener(new OnGroupExpandListener() {  
	        @Override  
	        public void onGroupExpand(int groupPosition) {  
				if(groupPosition != 0){
					expandableList.setSelectedGroup(groupPosition);
				}
	        }  
	    }); 
		
		expandableList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int parent, int children, long arg4) {

				String str = "parent id:" + String.valueOf(parent)
						+ ",children id:" + String.valueOf(children);
				HashMap map = (HashMap) ((ArrayList) categories.get(parent).get("children")).get(children);
				int catId = (Integer) map.get("cat_id");
				String categoryName = (String) map.get("category_name");
				Toast.makeText(EditRegistrationActivity.this, "cat_id:" + catId + ", category_name:" + categoryName, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
	
	public void setCategoris() {
		categories = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String, Object> mapFather1 = new HashMap<String, Object>();
		mapFather1.put("cat_id", 1);
		mapFather1.put("drawable", R.drawable.ic_launcher);
		mapFather1.put("category_name", "New In");
		mapFather1.put("description", "Dress/Coats/Lace");
		
		ArrayList<HashMap<String, Object>> son1 = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> mapSon11 = new HashMap<String, Object>();
		mapSon11.put("cat_id", 6);
		mapSon11.put("drawable", R.drawable.ic_launcher);
		mapSon11.put("category_name", "Dress");
		
		HashMap<String, Object> mapSon12 = new HashMap<String, Object>();
		mapSon12.put("cat_id", 8);
		mapSon12.put("drawable", R.drawable.ic_launcher);
		mapSon12.put("category_name", "Coats");
		
		HashMap<String, Object> mapSon13 = new HashMap<String, Object>();
		mapSon13.put("cat_id", 12);
		mapSon13.put("drawable", R.drawable.ic_launcher);
		mapSon13.put("category_name", "Lace");
		
		HashMap<String, Object> mapSon14 = new HashMap<String, Object>();
		mapSon14.put("cat_id", 12);
		mapSon14.put("drawable", R.drawable.ic_launcher);
		mapSon14.put("category_name", "Chiffon");
		son1.add(mapSon11);
		son1.add(mapSon12);
		son1.add(mapSon13);
		son1.add(mapSon14);
		mapFather1.put("children", son1);
		
		
		
		HashMap<String, Object> mapFather2 = new HashMap<String, Object>();
		mapFather2.put("cat_id", 2);
		mapFather2.put("drawable", R.drawable.ic_launcher);
		mapFather2.put("category_name", "Shop Collection");
		mapFather2.put("description", "Wintage/Going out/Workwear");
		
		ArrayList<HashMap<String, Object>> son2 = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> mapSon21 = new HashMap<String, Object>();
		mapSon21.put("cat_id", 21);
		mapSon21.put("drawable", R.drawable.ic_launcher);
		mapSon21.put("category_name", "Vintage");
		
		HashMap<String, Object> mapSon22 = new HashMap<String, Object>();
		mapSon22.put("cat_id", 22);
		mapSon22.put("drawable", R.drawable.ic_launcher);
		mapSon22.put("category_name", "Going out");
		
		HashMap<String, Object> mapSon23 = new HashMap<String, Object>();
		mapSon23.put("cat_id", 23);
		mapSon23.put("drawable", R.drawable.ic_launcher);
		mapSon23.put("category_name", "Workwear");
		
		son2.add(mapSon21);
		son2.add(mapSon22);
		son2.add(mapSon23);
		mapFather2.put("children", son2);
		

		//第7个父类
		HashMap<String, Object> mapFather7 = new HashMap<String, Object>();
		mapFather7.put("cat_id", 7);
		mapFather7.put("drawable", R.drawable.ic_launcher);
		mapFather7.put("category_name", "Recommendation");
		mapFather7.put("description", "Hot/New");
		ArrayList<HashMap<String, Object>> son7 = new ArrayList<HashMap<String,Object>>();
		//第一个子类元素
		HashMap<String, Object> mapson71 = new HashMap<String, Object>();
		mapson71.put("cat_id", 71);
		mapson71.put("drawable", R.drawable.ic_launcher);
		mapson71.put("category_name", "Hot");
		//第二个子类元素
		HashMap<String, Object> mapson720 = new HashMap<String, Object>();
		mapson720.put("cat_id", 72);
		mapson720.put("drawable", R.drawable.ic_launcher);
		mapson720.put("category_name", "New");
		//将两个子类元素添加到子类中
		son7.add(mapson71);
		son7.add(mapson720);
		//将子类添加到父类中
		mapFather7.put("children", son7);
		
		categories.add(mapFather1);
		categories.add(mapFather2);
		categories.add(mapFather7);
		
	}
}