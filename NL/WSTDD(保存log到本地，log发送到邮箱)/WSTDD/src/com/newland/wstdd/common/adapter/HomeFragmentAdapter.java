package com.newland.wstdd.common.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.ViewGroup;

/**底部四个fragment关联到viewpager中的适配器
 * @author H81 2015-11-4
 *
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private FragmentManager fm;
    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    	this.fm = fm;
    }
	public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	//除去旧的数据，放入新的数据
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
    public boolean isViewFromObject(View arg0, Object arg1) {
        return super.isViewFromObject( arg0, arg1);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	Object obj = super.instantiateItem(container, position);
		return obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	 super.destroyItem( container, position, object);
    }
	
	
	
}