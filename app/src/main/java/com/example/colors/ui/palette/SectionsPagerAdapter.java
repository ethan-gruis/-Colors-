package com.example.colors.ui.palette;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.colors.AnalogousFrag;
import com.example.colors.MonochromaticFrag;
import com.example.colors.R;
import com.example.colors.TriadicFrag;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_1};

    private final Context mContext;
    private float redValue;
    private float greenValue;
    private float blueValue;

    public SectionsPagerAdapter(Context context, FragmentManager fm, float redValue, float greenValue, float blueValue) {
        super(fm);
        mContext = context;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MonochromaticFrag(redValue,greenValue,blueValue);
                break;
            case 1:
                fragment = new TriadicFrag(redValue,greenValue,blueValue);
                break;
            case 2:
                fragment = new AnalogousFrag(redValue,greenValue,blueValue);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }

}