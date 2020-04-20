package com.example.metronome.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MetronomeFragment.newInstance();
            case 1:
                return PlayerFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "METRONOME";
            case 1:
                return "PLAYER";
            default:
                return " ";
        }
    }

    @Override
    public int getCount() {
        // return CONTENT.length;
        return NUM_ITEMS;
    }
}
