package com.java.zhushuqi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.News;
import com.java.zhushuqi.backend.Server;
import com.java.zhushuqi.ui.news.PlaceholderFragment;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public List<News> data = new ArrayList<>();

        MyAdapter() {
            data = Server.server.ViewedHistory;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NotNull final MyAdapter.ViewHolder viewHolder, final int position) {
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