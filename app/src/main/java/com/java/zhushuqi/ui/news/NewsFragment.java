package com.java.zhushuqi.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.java.zhushuqi.HistoryAdapter;
import com.java.zhushuqi.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    SearchView mSearchView;
    private ListView mListView;
    private final List<String> mStrings = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        List<PlaceholderFragment> placeholderFragments = new ArrayList<>();
        PlaceholderFragment fragment_all = new PlaceholderFragment("All");
        PlaceholderFragment fragment_news = new PlaceholderFragment("News");
        PlaceholderFragment fragment_paper = new PlaceholderFragment("Paper");
        placeholderFragments.add(fragment_all);
        placeholderFragments.add(fragment_news);
        placeholderFragments.add(fragment_paper);

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(),
                this.requireActivity().getSupportFragmentManager(), placeholderFragments);
        ViewPager viewPager = root.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mSearchView = root.findViewById(R.id.search_view);
        mListView = root.findViewById(R.id.list_view);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search for news…");
        final Context context = this.getContext();
        mListView.setTextFilterEnabled(true);
        mSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);
                mSearchView.clearFocus();
                mStrings.remove(query);
                mStrings.add(query);
                HistoryAdapter historyAdapter = new HistoryAdapter(
                        context, mStrings, mSearchView);
                mListView.setAdapter(historyAdapter);
                mListView.setVisibility(4);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    mListView.clearTextFilter();
                } else {
                    mListView.setFilterText(newText);
                    mListView.dispatchDisplayHint(View.INVISIBLE);
                }
                return true;
            }
        });
        EditText editText = mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                mListView.setVisibility(0);
            }
        });

        System.out.println("NewsFragment CreatingView…");
        return root;
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