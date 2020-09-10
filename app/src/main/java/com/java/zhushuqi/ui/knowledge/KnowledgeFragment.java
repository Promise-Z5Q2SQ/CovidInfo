package com.java.zhushuqi.ui.knowledge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.java.zhushuqi.R;
import com.java.zhushuqi.TabSelectActivity;
import com.java.zhushuqi.backend.Entity;
import com.pchmn.materialchips.ChipView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KnowledgeFragment extends Fragment {
    SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_knowledge, container, false);

        mSearchView = root.findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search for entities…");

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        KnowledgeAdapter knowledgeAdapter = new KnowledgeAdapter();
        mRecyclerView.setAdapter(knowledgeAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return root;
    }

    public static class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> {
        List<Entity> data = new ArrayList<>();

        public KnowledgeAdapter() {
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tag_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        }

        @Override
        public int getItemCount() {
            return data.size();
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