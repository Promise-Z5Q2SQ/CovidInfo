package com.java.zhushuqi.ui.Scholar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.java.zhushuqi.R;
import com.java.zhushuqi.ScholarPageActivity;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.Scholar;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScholarHolderFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    String type;

    ScholarHolderFragment(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.scholar_list, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        ScholarAdapter scholarAdapter = new ScholarAdapter(type);
        mRecyclerView.setAdapter(scholarAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return root;
    }

    public static class ScholarAdapter extends RecyclerView.Adapter<ScholarAdapter.ViewHolder> {
        List<Scholar> data = new ArrayList<>();
        String type;

        public ScholarAdapter(String type) {
            this.type = type;
            if (type.equals("高关注学者"))
                ConnectInterface.GetScholar().subscribe(new Consumer<List<Scholar>>() {
                    @Override
                    public void accept(List<Scholar> currentScholar) {
                        data = currentScholar;
                        notifyDataSetChanged();
                        System.out.println(type + " load " + currentScholar.size());
                    }
                });
            else
                ConnectInterface.GetP_Scholar().subscribe(new Consumer<List<Scholar>>() {
                    @Override
                    public void accept(List<Scholar> currentScholar) {
                        data = currentScholar;
                        notifyDataSetChanged();
                        System.out.println(type + " load " + currentScholar.size());
                    }
                });
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scholar_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(root);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.scholar_image.setImageBitmap(data.get(position).img);
            viewHolder.scholar_name.setText(data.get(position).name);
            viewHolder.scholar_index.setText("h指数: " + data.get(position).hindex + ", g指数: " + data.get(position).gindex);
            viewHolder.scholar_activity.setText("学术活跃度: " + data.get(position).activity);
            viewHolder.scholar_sociability.setText("社会性: " + data.get(position).sociability);
            viewHolder.scholar_citations.setText("引用数: " + data.get(position).citations + "");
            viewHolder.scholar_position.setText("职务: " + data.get(position).position);
            viewHolder.scholar_affiliation.setText("机构: " + data.get(position).affiliation);
            viewHolder.scholar_diversity.setText("多样性: " + data.get(position).diversity);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = viewHolder.itemView.getContext();
                    Intent intent = new Intent(context, ScholarPageActivity.class);
                    intent.putExtra("name", data.get(position).name);
                    intent.putExtra("position", data.get(position).position);
                    intent.putExtra("affiliation", data.get(position).affiliation);
                    intent.putExtra("work", data.get(position).work);
                    intent.putExtra("bio", data.get(position).bio);
                    intent.putExtra("edu", data.get(position).edu);
                    intent.putExtra("positionNum", position);
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView scholar_image;
            TextView scholar_name;
            TextView scholar_index;
            TextView scholar_activity;
            TextView scholar_sociability;
            TextView scholar_citations;
            TextView scholar_position;
            TextView scholar_affiliation;
            TextView scholar_diversity;
            CardView cardView;

            public ViewHolder(View view) {
                super(view);
                cardView = view.findViewById(R.id.card_view);
                scholar_image = view.findViewById(R.id.scholar_image);
                scholar_name = view.findViewById(R.id.scholar_name);
                scholar_index = view.findViewById(R.id.scholar_index);
                scholar_activity = view.findViewById(R.id.scholar_activity);
                scholar_sociability = view.findViewById(R.id.scholar_sociability);
                scholar_citations = view.findViewById(R.id.scholar_citations);
                scholar_position = view.findViewById(R.id.scholar_position);
                scholar_affiliation = view.findViewById(R.id.scholar_affiliation);
                scholar_diversity = view.findViewById(R.id.scholar_diversity);
            }
        }
    }
}
