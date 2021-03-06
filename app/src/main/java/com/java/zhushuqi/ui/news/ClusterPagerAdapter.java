package com.java.zhushuqi.ui.news;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClusterPagerAdapter extends FragmentPagerAdapter {
    List<ClusterHolderFragment> placeholderFragmentList = new ArrayList<>();

    public ClusterPagerAdapter(FragmentManager fm, List<ClusterHolderFragment> list) {
        super(fm);
        placeholderFragmentList = list;
    }

    @Override
    public ClusterHolderFragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return placeholderFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Cluster" + getItem(position).getNum();
    }

    @Override
    public int getCount() {
        return placeholderFragmentList.size();
    }
}
