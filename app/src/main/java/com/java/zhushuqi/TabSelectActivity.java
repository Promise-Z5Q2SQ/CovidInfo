package com.java.zhushuqi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.java.zhushuqi.ui.news.PlaceholderFragment;
import com.pchmn.materialchips.ChipView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabSelectActivity extends AppCompatActivity {
    private ChipsAdapter mTagsAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    List<String> tagList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_select_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        tagList.add("All");
        tagList.add("News");
        tagList.add("Paper");
        mTagsAdapter = new ChipsAdapter(tagList);
        mRecyclerView.setAdapter(mTagsAdapter);
    }


    public static class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ViewHolder> {
        private final List<String> mChips;
        private OnRemoveChipListener mOnRemoveChipListener;
        private OnChipsCountChangeListener mOnChipsCountChangeListener;
        private int mPinnedChipIndex = -1;

        public ChipsAdapter(List<String> list) {
            mChips = list;
        }

        public ChipsAdapter(List<String> list, int pinnedChipIndex) {
            mChips = new ArrayList<String>(list);
            mPinnedChipIndex = pinnedChipIndex;
        }

        public void addChip(String chip) {
            mChips.add(chip);
            notifyItemInserted(mChips.size() - 1);
            if (mOnChipsCountChangeListener != null)
                mOnChipsCountChangeListener.onChipsCountChange(getItemCount());
        }

        public void removeChip(int position) {
            mChips.remove(position);
            notifyItemRemoved(position);
            if (mOnChipsCountChangeListener != null)
                mOnChipsCountChangeListener.onChipsCountChange(getItemCount());
        }

        public void setOnRemoveChipListener(OnRemoveChipListener listener) {
            mOnRemoveChipListener = listener;
        }

        public void setOnTagsCountChangeListener(OnChipsCountChangeListener listener) {
            mOnChipsCountChangeListener = listener;
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tag_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
            holder.mTag.setLabel(mChips.get(position));
        }

        @Override
        public int getItemCount() {
            return mChips.size();
        }

        interface OnRemoveChipListener {
            void onRemoveChip(View view, int position);
        }

        interface OnChipsCountChangeListener {
            void onChipsCountChange(int count);
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

