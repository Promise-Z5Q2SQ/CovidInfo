package com.java.zhushuqi.ui.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.java.zhushuqi.NewsPageActivity;
import com.java.zhushuqi.R;
import com.java.zhushuqi.backend.*;
import com.pchmn.materialchips.ChipView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ClusterHolderFragment extends Fragment {
    private int num;
    private RecyclerView mRecyclerView;
    public ClusterAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    View root;

    ClusterHolderFragment(final int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.cluster_list, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerViewww);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(Objects.requireNonNull(this.getContext()), LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ClusterAdapter(num, this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        ChipView key_word0 = root.findViewById(R.id.key_word0);
        ChipView key_word1 = root.findViewById(R.id.key_word1);
        ChipView key_word2 = root.findViewById(R.id.key_word2);
        ChipView key_word3 = root.findViewById(R.id.key_word3);
        ConnectInterface.GetKeyword(num, this.getContext()).subscribe(new Consumer<List<Keyword>>() {
            @Override
            public void accept(List<Keyword> keywords) {
                key_word0.setLabel(keywords.get(0).toString());
                key_word1.setLabel(keywords.get(1).toString());
                key_word2.setLabel(keywords.get(2).toString());
                key_word3.setLabel(keywords.get(3).toString());
            }
        });

        return root;
    }

    public static class ClusterAdapter extends RecyclerView.Adapter<ClusterAdapter.ViewHolder> {
        public List<News> data = new ArrayList<>();
        int num;

        ClusterAdapter(int num, Context context) {
            this.num = num;
            System.out.println(num);
            ConnectInterface.GetCluster(num, context).subscribe(new Consumer<List<News>>() {
                @Override
                public void accept(List<News> currentScholar) {
                    data = currentScholar;
                    notifyDataSetChanged();
                    System.out.println(num + " cluster has " + currentScholar.size());
                }
            });
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NotNull final ViewHolder viewHolder, final int position) {
            viewHolder.mTextView_title.setText(data.get(position).title);
            if (data.get(position).viewed)
                viewHolder.mTextView_title.setTextColor(Color.rgb(150, 150, 150));
            else
                viewHolder.mTextView_title.setTextColor(Color.rgb(0, 0, 0));
            viewHolder.mTextView_source.setText(data.get(position).source);
            viewHolder.mTextView_time.setText(data.get(position).date);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(data.get(position).type);
                    Context context = viewHolder.itemView.getContext();
                    Intent intent = new Intent(context, NewsPageActivity.class);
                    intent.putExtra("title", data.get(position).title);
                    intent.putExtra("content", data.get(position).content);
                    context.startActivity(intent);
                    data.get(position).viewed = true;
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView_title;
            public TextView mTextView_source;
            public TextView mTextView_time;

            public ViewHolder(View view) {
                super(view);
                mTextView_title = view.findViewById(R.id.text_title);
                mTextView_source = view.findViewById(R.id.text_source);
                mTextView_time = view.findViewById(R.id.text_time);
            }
        }
    }
}
