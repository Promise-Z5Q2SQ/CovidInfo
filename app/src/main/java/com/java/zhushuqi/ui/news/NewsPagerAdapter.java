package com.java.zhushuqi.ui.news;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    List<NewsHolderFragment> placeholderFragmentList = new ArrayList<>();

    public NewsPagerAdapter( FragmentManager fm, List<NewsHolderFragment> list) {
        super(fm);
        placeholderFragmentList = list;
    }

    @Override
    public NewsHolderFragment getItem(int position) {
        return placeholderFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getName();
    }

    @Override
    public int getCount() {
        return placeholderFragmentList.size();
    }
}