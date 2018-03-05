package com.newland.wstdd.find;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private FragmentManager fm;

	/**
	 * 
	 */
	
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    	this.fm = fm;
    }
	public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		if (fragments !=null && fragments.size()>0 ) {
			return fragments.get(position);
		}else {
			return null;
		}
	}

	@Override
	public int getCount() {
		return fragments.size() == 0 ? 0 : fragments.size();
	}

	public void setFragments(ArrayList<Fragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}
	  @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	 super.destroyItem( container, position, object);
	    }
	
	
}