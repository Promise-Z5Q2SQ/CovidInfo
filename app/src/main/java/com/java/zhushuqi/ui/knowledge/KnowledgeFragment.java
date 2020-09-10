package com.java.zhushuqi.ui.knowledge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
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

import java.util.*;

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
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knowledge_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            ItemAdapter itemAdapter_p = new ItemAdapter("properties");
            viewHolder.propertiesView.setAdapter(itemAdapter_p);
            ItemAdapter itemAdapter_r = new ItemAdapter("relations");
            viewHolder.relationsView.setAdapter(itemAdapter_r);

            viewHolder.knowledge_label.setText(data.get(position).label);
            if (data.get(position).baidu != null)
                viewHolder.knowledge_info.setText(data.get(position).baidu);
            else if (data.get(position).enwiki != null)
                viewHolder.knowledge_info.setText(data.get(position).enwiki);
            else if (data.get(position).zhwiki != null)
                viewHolder.knowledge_info.setText(data.get(position).zhwiki);
            viewHolder.knowledge_image.setImageBitmap(data.get(position).img);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView knowledge_image;
            public TextView knowledge_label;
            public TextView knowledge_info;
            public RecyclerView propertiesView;
            public RecyclerView relationsView;

            public ViewHolder(View view) {
                super(view);
                knowledge_image = view.findViewById(R.id.knowledge_image);
                knowledge_label = view.findViewById(R.id.knowledge_label);
                knowledge_info = view.findViewById(R.id.knowledge_info);
                propertiesView = view.findViewById(R.id.propertiesView);
                relationsView = view.findViewById(R.id.relationsView);
            }
        }
    }

    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        HashMap<String, String> data = new HashMap<>();
        Iterator<Map.Entry<String, String>> iterator;
        String type;

        public ItemAdapter(String type) {
            this.type = type;
            iterator = data.entrySet().iterator();
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.relation_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (type.equals("properties")) {
                viewHolder.text_label.setText(iterator.next().getKey());
                viewHolder.text_value.setText(iterator.next().getValue());
            } else {
                viewHolder.text_label.setText(iterator.next().getValue());
                viewHolder.text_value.setText(iterator.next().getKey());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView text_label;
            public TextView text_value;

            public ViewHolder(View view) {
                super(view);
                text_label = view.findViewById(R.id.text_label);
                text_value = view.findViewById(R.id.text_value);
            }
        }
    }
}