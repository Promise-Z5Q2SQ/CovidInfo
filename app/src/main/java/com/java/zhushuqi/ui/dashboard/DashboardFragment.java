package com.java.zhushuqi.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.java.zhushuqi.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        System.out.println("DashFragment CreatingView…");
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("DashFragment Stopping…");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("DashFragment DestroyingView…");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("DashFragment Destroying…");
    }
}