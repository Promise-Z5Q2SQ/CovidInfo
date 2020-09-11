package com.java.zhushuqi.ui.knowledge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.java.zhushuqi.HistoryAdapter;
import com.java.zhushuqi.R;
import com.java.zhushuqi.TabSelectActivity;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.Entity;
import com.java.zhushuqi.backend.News;
import com.pchmn.materialchips.ChipView;
import io.reactivex.functions.Consumer;
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
        KnowledgeAdapter knowledgeAdapter = new KnowledgeAdapter(mSearchView);
        mSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                ConnectInterface.GetEntities(query).subscribe(new Consumer<List<Entity>>() {
                    @Override
                    public void accept(List<Entity> currentEntities) {
                        knowledgeAdapter.data = currentEntities;
                        knowledgeAdapter.notifyDataSetChanged();
                        System.out.println("Find " + currentEntities.size());
                    }
                });
                System.out.println("searching " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(knowledgeAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return root;
    }

    public static class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> {
        public List<Entity> data = new ArrayList<>();
        SearchView searchView;

        public KnowledgeAdapter(SearchView searchView) {
            this.searchView = searchView;
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
            ItemAdapter itemAdapter_p = new ItemAdapter("properties", data.get(position).properties, searchView);
            viewHolder.propertiesView.setAdapter(itemAdapter_p);
            ItemAdapter itemAdapter_r = new ItemAdapter("relations", data.get(position).relations, searchView);
            viewHolder.relationsView.setAdapter(itemAdapter_r);

            viewHolder.propertiesView.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext()));
            viewHolder.relationsView.setLayoutManager(new GridLayoutManager(viewHolder.itemView.getContext(), 2));

            viewHolder.knowledge_label.setText(data.get(position).label);
            if (!data.get(position).baidu.equals(""))
                viewHolder.knowledge_info.setText(data.get(position).baidu);
            else if (!data.get(position).enwiki.equals(""))
                viewHolder.knowledge_info.setText(data.get(position).enwiki);
            else if (!data.get(position).zhwiki.equals(""))
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
        HashMap<String, String> data;
        Iterator<Map.Entry<String, String>> iterator;
        String type;
        SearchView searchView;

        public ItemAdapter(String type, HashMap<String, String> data, SearchView searchView) {
            this.type = type;
            this.data = data;
            this.searchView = searchView;
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
                Map.Entry<String, String> entry = iterator.next();
                viewHolder.text_label.setText(entry.getKey());
                viewHolder.text_value.setText(entry.getValue());
                viewHolder.card_view.setClickable(false);
            } else {
                Map.Entry<String, String> entry = iterator.next();
                viewHolder.text_label.setText(entry.getValue());
                viewHolder.text_value.setText(entry.getKey());
                viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                        editText.setText(entry.getKey());
                        ImageView searchButton = searchView.findViewById(androidx.appcompat.R.id.search_go_btn);
                        searchButton.performClick();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView text_label;
            public TextView text_value;
            public CardView card_view;

            public ViewHolder(View view) {
                super(view);
                text_label = view.findViewById(R.id.text_label);
                text_value = view.findViewById(R.id.text_value);
                card_view = view.findViewById(R.id.card_view);
            }
        }
    }
}