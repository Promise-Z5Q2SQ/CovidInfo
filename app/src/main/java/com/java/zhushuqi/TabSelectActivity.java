package com.java.zhushuqi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.pchmn.materialchips.ChipView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabSelectActivity extends AppCompatActivity {
    private ChipsAdapter mTagsAdapter;
    private RecyclerView mRecyclerView;
    Intent resultIntent = new Intent();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_select_page);
        Context context = this;
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.tabpage_toolbar_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mTagsAdapter = new ChipsAdapter(
                intent.getBooleanExtra("News", true), intent.getBooleanExtra("Paper", true));
        mRecyclerView.setAdapter(mTagsAdapter);
        ChipsLayoutManager tagsLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER)
                .withLastRow(true)
                .build();
        mRecyclerView.setLayoutManager(tagsLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        toolbar.findViewById(R.id.action_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = mTagsAdapter.unavailableTags();
                if (list.isEmpty()) return;
                String[] array = new String[list.size()];
                list.toArray(array);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请选择要添加的首页标签").setNegativeButton("取消", null);
                builder.setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTagsAdapter.exist.put(array[i], true);
                        mTagsAdapter.mChips.add(array[i]);
                        mTagsAdapter.notifyItemInserted(mTagsAdapter.mChips.size() - 1);
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        resultIntent.putExtra("News", mTagsAdapter.exist.get("News"));
        resultIntent.putExtra("Paper", mTagsAdapter.exist.get("Paper"));
        setResult(0, resultIntent);
        super.onBackPressed();
    }

    public static class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ViewHolder> {
        public final List<String> mChips;
        public HashMap<String, Boolean> exist;

        public ChipsAdapter(boolean news, boolean paper) {
            mChips = new ArrayList<>();
            mChips.add("All");
            exist = new HashMap<>();
            exist.put("All", true);
            exist.put("News", news);
            exist.put("Paper", paper);
            if (news) mChips.add("News");
            if (paper) mChips.add("Paper");
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tag_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        public List<String> unavailableTags() {
            List<String> list = new ArrayList<>();
            if (!exist.get("News")) list.add("News");
            if (!exist.get("Paper")) list.add("Paper");
            return list;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (position == 0)
                viewHolder.mTag.setDeletable(false);
            viewHolder.mTag.setLabel(mChips.get(position));
            viewHolder.mTag.setOnDeleteClicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exist.put(mChips.get(position), false);
                    mChips.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mChips.size() - position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mChips.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ChipView mTag;

            public ViewHolder(View view) {
                super(view);
                mTag = view.findViewById(R.id.chip_view);
            }
        }
    }
}

