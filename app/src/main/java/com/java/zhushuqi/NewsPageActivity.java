package com.java.zhushuqi;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class NewsPageActivity extends AppCompatActivity {
    public int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.newspage_toolbar_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.findViewById(R.id.action_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Share
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        TextView textView_title = findViewById(R.id.text_title);
        TextView textView_content = findViewById(R.id.text_content);
        textView_title.setText(title);
        textView_content.setText(content);
    }
}