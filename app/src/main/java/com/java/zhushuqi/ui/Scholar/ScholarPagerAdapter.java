package com.java.zhushuqi.ui.Scholar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScholarPagerAdapter extends FragmentPagerAdapter {
    List<ScholarHolderFragment> scholarHolderFragments = new ArrayList<>();

    public ScholarPagerAdapter(@NonNull @NotNull FragmentManager fm, List<ScholarHolderFragment> scholarHolderFragments) {
        super(fm);
        this.scholarHolderFragments = scholarHolderFragments;
    }

    @NonNull
    @NotNull
    @Override
    public ScholarHolderFragment getItem(int position) {
        return scholarHolderFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getCount() {
        return scholarHolderFragments.size();
    }
}
