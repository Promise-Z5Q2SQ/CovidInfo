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

public class NewsFragment extends Fragment {
    SearchView mSearchView;
    private ListView mListView;
    private final List<String> mStrings = new ArrayList<>();
    List<NewsHolderFragment> placeholderFragments = new ArrayList<>();
    NewsHolderFragment fragment_all = new NewsHolderFragment("all");
    NewsHolderFragment fragment_news = new NewsHolderFragment("news");
    NewsHolderFragment fragment_paper = new NewsHolderFragment("paper");
    NewsPagerAdapter newsPagerAdapter;
    ViewPager viewPager;
    View root;
    public HashMap<String, Boolean> exist = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placeholderFragments.add(fragment_all);
        placeholderFragments.add(fragment_news);
        placeholderFragments.add(fragment_paper);
        exist.put("All", true);
        exist.put("News", true);
        exist.put("Paper", true);

        root = inflater.inflate(R.layout.fragment_news, container, false);
        newsPagerAdapter = new NewsPagerAdapter(
                this.requireActivity().getSupportFragmentManager(), placeholderFragments);
        viewPager = root.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(newsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mListView = root.findViewById(R.id.list_view);
        mSearchView = root.findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search for news…");
        final Context context = this.getContext();
        mListView.setTextFilterEnabled(true);
        mSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                mStrings.remove(query);
                mStrings.add(query);
                HistoryAdapter historyAdapter = new HistoryAdapter(
                        context, mStrings, mSearchView);
                mListView.setAdapter(historyAdapter);
                mListView.setVisibility(4);
                ConnectInterface.GetSearchAnswer(query).subscribe(new Consumer<List<News>>() {
                    @Override
                    public void accept(List<News> currentNews) {
                        fragment_all.mAdapter.data = currentNews;
                        fragment_all.mAdapter.notifyDataSetChanged();
                        System.out.println("Find " + currentNews.size());
                    }
                });
                System.out.println("searching " + query);
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
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mListView.setVisibility(0);
            }
        });

        FloatingActionButton floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = root.getContext();
                Intent intent = new Intent(context, TabSelectActivity.class);
                intent.putExtra("News", exist.get("News"));
                intent.putExtra("Paper", exist.get("Paper"));
                startActivityForResult(intent, 0);
            }
        });
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