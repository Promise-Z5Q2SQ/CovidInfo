package com.java.zhushuqi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsPageActivity extends AppCompatActivity {
    public int counter = 0;

    private void showLocationShare(int request) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "这是分享的内容！");//注意：这里只是分享文本内容
        sendIntent.setType("text/plain");
        startActivityForResult(sendIntent, request);
    }

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
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        toolbar.findViewById(R.id.action_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                PackageManager packageManager = getPackageManager();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, title);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "主题信息");
                String zhaiyao = "摘要：" + title;
                sendIntent.putExtra(Intent.EXTRA_TEXT, zhaiyao + "\n" + content);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        TextView textView_title = findViewById(R.id.text_title);
        TextView textView_content = findViewById(R.id.text_content);
        textView_title.setText(title);
        textView_content.setText(content);
    }
}