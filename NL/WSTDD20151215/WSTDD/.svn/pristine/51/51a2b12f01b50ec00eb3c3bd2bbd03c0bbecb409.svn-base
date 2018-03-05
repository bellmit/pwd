package com.newland.wstdd.find.categorylist.registrationedit.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;

import com.newland.wstdd.R;

@SuppressWarnings({"rawtypes"})
public class TreeViewAdapter extends BaseExpandableListAdapter {
	public static final int itemHeight = 340;// 每项的高度

	static public class TreeNode {
		Object parent;
		List<Object> childs = new ArrayList<Object>();
	}

	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	Context parentContext;

	public TreeViewAdapter(Context view) {
		parentContext = view;
	}

	public List<TreeNode> GetTreeNode() {
		return treeNodes;
	}

	public void UpdateTreeNode(List<TreeNode> nodes) {
		treeNodes = nodes;
	}

	public void RemoveAll() {
		treeNodes.clear();
	}

	public HashMap getChild(int groupPosition, int childPosition) {
		return (HashMap) treeNodes.get(groupPosition).childs.get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return treeNodes.get(groupPosition).childs.size();
	}

	static public CustomImageButton getTextView(Context context, int itemHeight) {
		AttributeSet attrs = null;
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, itemHeight);
		CustomImageButton imageButton = new CustomImageButton(context, attrs);
		imageButton.setLayoutParams(lp);
		imageButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		return imageButton;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CustomImageButton imageButton = getTextView(this.parentContext, itemHeight/3 );
		imageButton.setName("category_name");
		imageButton.setPhone("description");
		imageButton.setRl1Gone(true);
		imageButton.setRl2Gone(false);
		imageButton.setLl1Gone(false);
		
//		imageButton.setBackgroundColor(android.graphics.Color.WHITE);
		return imageButton;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		CustomImageButton imageButton = getTextView(this.parentContext, itemHeight);
		imageButton.setText((String) getGroup(groupPosition).get("category_name"));
		imageButton.setEdit((String) getGroup(groupPosition).get("description"));
		imageButton.setRl1Gone(false);
		imageButton.setRl2Gone(true);
		imageButton.setLl1Gone(true);
		imageButton.setBackgroundColor(parentContext.getResources().getColor(R.color.bg_gray));
		return imageButton;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public HashMap getGroup(int groupPosition) {
		return (HashMap) treeNodes.get(groupPosition).parent;
	}

	public int getGroupCount() {
		return treeNodes.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}