package com.java.zhushuqi.ui.Scholar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.java.zhushuqi.NewsPageActivity;
import com.java.zhushuqi.R;
import com.java.zhushuqi.ScholarPageActivity;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.Entity;
import com.java.zhushuqi.backend.Scholar;
import com.java.zhushuqi.ui.knowledge.KnowledgeFragment;
import com.java.zhushuqi.ui.news.NewsPagerAdapter;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.*;

public class ScholarFragment extends Fragment {
    ViewPager viewPager;
    List<ScholarHolderFragment> scholarHolderFragments = new ArrayList<>();
    ScholarHolderFragment gaoGuanZhu = new ScholarHolderFragment("高关注学者");
    ScholarHolderFragment zhuiyi = new ScholarHolderFragment("追忆学者");
    ScholarPagerAdapter scholarPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scholar, container, false);
        scholarHolderFragments.add(gaoGuanZhu);
        scholarHolderFragments.add(zhuiyi);

        scholarPagerAdapter = new ScholarPagerAdapter(
                this.requireActivity().getSupportFragmentManager(), scholarHolderFragments);
        viewPager = root.findViewById(R.id.viewpager);
        viewPager.setAdapter(scholarPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }
}
