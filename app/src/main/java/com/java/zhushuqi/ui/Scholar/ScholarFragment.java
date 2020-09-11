package com.java.zhushuqi.ui.Scholar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.java.zhushuqi.NewsPageActivity;
import com.java.zhushuqi.R;
import java.util.*;

public class ScholarFragment extends Fragment {
    ViewPager viewPager;
    List<ScholarHolderFragment> scholarHolderFragments = new ArrayList<>();
    ScholarHolderFragment gaoGuanZhu = new ScholarHolderFragment("高关注学者");
    ScholarHolderFragment zhuiYi = new ScholarHolderFragment("追忆学者");
    ScholarPagerAdapter scholarPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scholar, container, false);
        scholarHolderFragments.add(gaoGuanZhu);
        scholarHolderFragments.add(zhuiYi);

        scholarPagerAdapter = new ScholarPagerAdapter(
                this.requireActivity().getSupportFragmentManager(), scholarHolderFragments);
        viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(scholarPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(viewPager);

        return root;
    }
}
