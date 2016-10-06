package com.example.caleb.myjourney;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by tinghaong on 6/10/16.
 */

public class ExploreFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Play", "Eat", "Service", "Shop" };

    public ExploreFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new PlayFragment();
            case 1:
                return new EatFragment();
            case 2:
                return new ServiceFragment();
            case 3:
                return new ShopFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

