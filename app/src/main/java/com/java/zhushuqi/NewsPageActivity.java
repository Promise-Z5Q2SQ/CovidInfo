package com.java.zhushuqi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

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
                List<ResolveInfo> list = packageManager.queryIntentActivities(share, 0);
                System.out.println(list.size());
                for (ResolveInfo info : list) {
                    System.out.println("" + info.activityInfo.packageName + "---" + info.activityInfo.name);
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, title);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "主题信息");
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.setType("text/plain");
//   sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//   sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//   sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//   sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
//
//   sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
                //sendIntent.setClassName("com.android.bluetooth", "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
                startActivity(sendIntent);
                /*ntent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                PackageManager packageManager = getPackageManager();
                List<Intent> intentlist = new ArrayList<Intent>();
                List<ResolveInfo> list = packageManager.queryIntentActivities(share, 0);
                System.out.println(list);
                if(!list.isEmpty()){
                    for(ResolveInfo info:list){
                        ActivityInfo activityInfo = info.activityInfo;
                        /*if (activityInfo.packageName.contains("email")
                                || activityInfo.packageName.contains("bluetooth")
                                || activityInfo.packageName.contains("mms")
                                || activityInfo.packageName.contains("weibo")) {*/
                            //Intent target = new Intent(Intent.ACTION_SEND);
                            //target.setType("text/plain");
                            //target.putExtra(Intent.EXTRA_TITLE, title);
                            //target.putExtra(Intent.EXTRA_SUBJECT, "subject");
                            //target.putExtra(Intent.EXTRA_TEXT, content);
                            //target.setPackage(activityInfo.packageName);
                            //intentlist.add(target);
                        //}
                    //}
                    //Intent chooser = Intent.createChooser(intentlist.remove(1), "选择APP");
                    //chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentlist.toArray(new Parcelable[] {}));
                    //startActivity(chooser);
                //}
            }
        });
        TextView textView_title = findViewById(R.id.text_title);
        TextView textView_content = findViewById(R.id.text_content);
        textView_title.setText(title);
        textView_content.setText(content);
    }
}