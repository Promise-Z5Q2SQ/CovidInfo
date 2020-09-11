package com.java.zhushuqi.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.zhushuqi.HistoryAdapter;
import com.java.zhushuqi.R;
import com.google.android.material.tabs.TabLayout;
import com.java.zhushuqi.TabSelectActivity;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.News;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClusterFragment extends Fragment {
    private ListView mListView;
    private final List<String> mStrings = new ArrayList<>();
    List<ClusterHolderFragment> placeholderFragments = new ArrayList<>();
    ClusterHolderFragment fragment_0 = new ClusterHolderFragment("类型0");
    ClusterHolderFragment fragment_1 = new ClusterHolderFragment("类型1");
    ClusterHolderFragment fragment_2 = new ClusterHolderFragment("类型2");
    ClusterHolderFragment fragment_3 = new ClusterHolderFragment("类型3");
    ClusterAdapter newsPagerAdapter;
    ViewPager viewPager;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placeholderFragments.add(fragment_0);
        placeholderFragments.add(fragment_1);
        placeholderFragments.add(fragment_2);
        placeholderFragments.add(fragment_3);

        root = inflater.inflate(R.layout.activity_cluster, container, false);
        newsPagerAdapter = new ClusterAdapter(
                this.requireActivity().getSupportFragmentManager(), placeholderFragments);
        viewPager = root.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(newsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mListView = root.findViewById(R.id.list_view);
        final Context context = this.getContext();
        mListView.setTextFilterEnabled(true);

        System.out.println("NewsFragment CreatingView…");
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        exist.put("News", data.getBooleanExtra("News", true));
        exist.put("Paper", data.getBooleanExtra("Paper", true));
        placeholderFragments = new ArrayList<>();
        placeholderFragments.add(fragment_all);
        if (exist.get("News")) placeholderFragments.add(fragment_news);
        if (exist.get("Paper")) placeholderFragments.add(fragment_paper);
        newsPagerAdapter = new NewsPagerAdapter(
                this.requireActivity().getSupportFragmentManager(), placeholderFragments);
        viewPager.setAdapter(newsPagerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("NewsFragment Stopping…");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("NewsFragment DestroyingView…");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("NewsFragment Destroying…");
    }
}