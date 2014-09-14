package edu.umd.cs.daemondash;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
	public FragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0){
		case 0:
		return new FoodDistFragment();
		case 1:
		return new DinerDistFragment();
		case 2:
		return new ContainerHistoryFragment();
		}

		return null;
		}

		@Override
		public int getCount() {
		// TODO Auto-generated method stub
		return 3;
		}
}
