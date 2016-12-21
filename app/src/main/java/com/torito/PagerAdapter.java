package com.torito;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Windows on 05/12/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    boolean rescate;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        rescate = false;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Perfil tab1 = new Perfil();
                return tab1;
            case 1:
                Rescatenme tab2 = new Rescatenme();
                return tab2;
            case 2:
                    Legal tab3 = new Legal();
                    return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
