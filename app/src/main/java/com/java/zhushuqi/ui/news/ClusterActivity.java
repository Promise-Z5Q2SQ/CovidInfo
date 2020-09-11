package com.java.zhushuqi.ui.news;

import android.view.View;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.java.zhushuqi.R;

import java.util.ArrayList;
import java.util.List;

public class ClusterActivity extends AppCompatActivity {
    private ListView mListView;
    private final List<String> mStrings = new ArrayList<>();
    List<ClusterHolderFragment> clusterHolderFragments = new ArrayList<>();
    ClusterHolderFragment fragment_0 = new ClusterHolderFragment(0);
    ClusterHolderFragment fragment_1 = new ClusterHolderFragment(1);
    ClusterHolderFragment fragment_2 = new ClusterHolderFragment(2);
    ClusterHolderFragment fragment_3 = new ClusterHolderFragment(3);
    ClusterPagerAdapter newsPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster);

        clusterHolderFragments.add(fragment_0);
        clusterHolderFragments.add(fragment_1);
        clusterHolderFragments.add(fragment_2);
        clusterHolderFragments.add(fragment_3);

        newsPagerAdapter = new ClusterPagerAdapter(getSupportFragmentManager(), clusterHolderFragments);
        viewPager = findViewById(R.id.viewpagerrr);
        // viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(newsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsss);
        tabs.setupWithViewPager(viewPager);
    }
}