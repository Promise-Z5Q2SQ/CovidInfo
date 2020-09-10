package com.java.zhushuqi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.News;
import com.java.zhushuqi.backend.NewsLoader;
import com.java.zhushuqi.backend.Server;
import com.java.zhushuqi.ui.dashboard.DashboardFragment;
import com.java.zhushuqi.ui.news.NewsFragment;
import com.java.zhushuqi.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments;
    private int lastFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.server = new Server();
        ConnectInterface.InitServer().subscribe(new Consumer<List<News>>() {
            @Override
            public void accept(List<News> currentNews) {
                initView();
            }
        });
    }

    private void initView() {
        NewsFragment newsFragment = new NewsFragment();
        DashboardFragment dashboardFragment = new DashboardFragment();
        NotificationsFragment notificationsFragment = new NotificationsFragment();
        fragments = new Fragment[]{newsFragment, dashboardFragment, notificationsFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, newsFragment).show(newsFragment).commit();
        BottomNavigationView bottomNavigation = findViewById(R.id.nav_view);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    if (lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_data:
                    if (lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    return true;
                case R.id.navigation_knowledge:
                    if (lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastFragment]);
        if (!fragments[index].isAdded())
            transaction.add(R.id.nav_host_fragment, fragments[index]);
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
