package com.java.zhushuqi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.Scholar;
import io.reactivex.functions.Consumer;

import java.util.List;

public class ScholarPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();

        TextView scholar_detail_name = findViewById(R.id.scholar_detail_name);
        TextView scholar_detail_position = findViewById(R.id.scholar_detail_position);
        TextView scholar_detail_affiliation = findViewById(R.id.scholar_detail_affiliation);
        TextView scholar_detail_work = findViewById(R.id.scholar_detail_work);
        TextView scholar_detail_edu = findViewById(R.id.scholar_detail_edu);
        TextView scholar_detail_bio = findViewById(R.id.scholar_detail_bio);
        ImageView scholar_detail_image = findViewById(R.id.scholar_detail_image);

        scholar_detail_name.setText(intent.getCharSequenceExtra("name"));
        scholar_detail_position.setText("职务: " + intent.getCharSequenceExtra("position"));
        scholar_detail_affiliation.setText("机构: " + intent.getCharSequenceExtra("affiliation"));
        scholar_detail_work.setText(intent.getCharSequenceExtra("work"));
        scholar_detail_edu.setText(intent.getCharSequenceExtra("edu"));
        scholar_detail_bio.setText(intent.getCharSequenceExtra("bio"));
        int position = intent.getIntExtra("positionNum",0);
        final Scholar[] scholar = new Scholar[1];
        ConnectInterface.GetScholar().subscribe(new Consumer<List<Scholar>>() {
            @Override
            public void accept(List<Scholar> currentScholar) {
                scholar[0] = currentScholar.get(position);
                scholar_detail_image.setImageBitmap(scholar[0].img);
            }
        });
    }
}