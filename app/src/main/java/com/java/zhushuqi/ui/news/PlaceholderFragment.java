package com.java.zhushuqi.ui.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.java.zhushuqi.NewsPageActivity;
import com.java.zhushuqi.R;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.News;
import com.java.zhushuqi.backend.Server;
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
import java.util.List;
import java.util.Objects;

public class PlaceholderFragment extends Fragment {
    private String name;
    private RecyclerView mRecyclerView;
    public MyAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    View root;

    PlaceholderFragment(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.news_list, container, false);
        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshHeader(
                new ClassicsHeader(Objects.requireNonNull(this.getContext())).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setRefreshFooter(
                new ClassicsFooter(Objects.requireNonNull(this.getContext())).setSpinnerStyle(SpinnerStyle.Scale));

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(Objects.requireNonNull(this.getContext()), LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(name);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mAdapter.refreshData();
                mRefreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mAdapter.loadMore();
                mRefreshLayout.finishLoadMore();
            }
        });
        return root;
    }

    public String getName() {
        return name;
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public List<News> data = new ArrayList<>();
        private final String name;

        MyAdapter(String s) {
            name = s;
            ConnectInterface.GetCurrentNews(name).subscribe(new Consumer<List<News>>() {
                @Override
                public void accept(List<News> currentNews) {
                    data = currentNews;
                    notifyDataSetChanged();
                    System.out.println(name + " " + currentNews.size());
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
                    Server.server.ViewedHistory.add(data.get(position));
                    System.out.println(data.get(position).type);
                    System.out.println("History " + Server.server.ViewedHistory.size());
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

        public void loadMore() {
            ConnectInterface.GetLatestNews(name).subscribe(new Consumer<List<News>>() {
                @Override
                public void accept(List<News> currentNews) {
                    data = currentNews;
                    System.out.println(name + " " + currentNews.size());
                    notifyDataSetChanged();
                }
            });
        }

        public void refreshData() {
            ConnectInterface.RetrievePresentNews(name).subscribe(new Consumer<List<News>>() {
                @Override
                public void accept(List<News> currentNews) {
                    data = currentNews;
                    System.out.println(name + " " + currentNews.size());
                    notifyDataSetChanged();
                }
            });
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