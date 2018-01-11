package com.example.user.arlearn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by user on 11/24/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new TabAll();
            case 1:
                return new TabAnimal();
            case 2:
                return new TabElement();
            case 3:
                return new TabHuman();
            case 4:
                return new TabSpace();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
