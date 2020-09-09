package com.java.zhushuqi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.SearchView;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<String> implements Filterable {
    Context context;
    List<String> titles;
    SearchView searchView;

    public HistoryAdapter(Context context, List<String> titles, SearchView searchView) {
        super(context, R.layout.search_history_item, titles);
        this.context = context;
        this.titles = titles;
        this.searchView = searchView;
    }

    @SuppressLint("ViewHolder")
    public View getView(final int position, View convertView, ViewGroup parent) {
        String user = getItem(position);
        LinearLayout userListItem = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
        vi.inflate(R.layout.search_history_item, userListItem, true);
        final TextView tvUsername = userListItem.findViewById(R.id.titleTv);
        View imgButton = userListItem.findViewById(R.id.imageButton);
        tvUsername.setText(user);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titles.remove(position);
                notifyDataSetChanged();
            }
        });
        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                editText.setText(titles.get(position));
            }
        });
        return userListItem;
    }
}