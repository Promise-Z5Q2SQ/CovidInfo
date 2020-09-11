package com.java.zhushuqi.ui.data;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.java.zhushuqi.R;
import com.java.zhushuqi.backend.ConnectInterface;
import com.java.zhushuqi.backend.Dataset;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class DataFragment extends Fragment {
    public Dataset local_data = null;
    private List<PointValue> Confirmed = new ArrayList<PointValue>();
    private List<PointValue> Cured = new ArrayList<PointValue>();
    private List<PointValue> Dead = new ArrayList<PointValue>();
    private List<PointValue> Suspected = new ArrayList<PointValue>();
    private List<AxisValue> XValues = new ArrayList<AxisValue>();
    private LineChartView linechart;

    private void getX() {
        for (int i = 0; i < local_data.data.size(); i++) {
            XValues.add(new AxisValue(i).setLabel(String.valueOf(local_data.data.get(i).timefromstart)));
        }
    }

    private void getConfirmed() {
        for (int i = 0; i < local_data.data.size(); i++) {
            Confirmed.add(new PointValue(i, local_data.data.get(i).confirmed));
        }
    }

    private void getCured() {
        for (int i = 0; i < local_data.data.size(); i++) {
            Cured.add(new PointValue(i, local_data.data.get(i).cured));
        }
    }

    private void getDead() {
        for (int i = 0; i < local_data.data.size(); i++) {
            Dead.add(new PointValue(i, local_data.data.get(i).dead));
        }
    }

    private void initLineChart() {
        Line confirmed = new Line(Confirmed).setColor(Color.parseColor("#FFDA3A"));
        Line dead = new Line(Dead).setColor(Color.parseColor("#0000FF"));
        Line cured = new Line(Cured).setColor(Color.parseColor("#00FF00"));
        List<Line> lines = new ArrayList<Line>();
        confirmed.setShape(ValueShape.DIAMOND);
        confirmed.setCubic(false);
        confirmed.setFilled(true);
        confirmed.setHasLabelsOnlyForSelected(true);
        confirmed.setPointRadius(1);
        confirmed.setStrokeWidth(1);
        lines.add(confirmed);
        dead.setShape(ValueShape.CIRCLE);
        dead.setCubic(false);
        dead.setFilled(true);
        dead.setHasLabelsOnlyForSelected(true);
        dead.setPointRadius(1);
        dead.setStrokeWidth(1);
        lines.add(dead);
        cured.setShape(ValueShape.SQUARE);
        cured.setCubic(false);
        cured.setFilled(true);
        cured.setHasLabelsOnlyForSelected(true);
        cured.setPointRadius(1);
        cured.setStrokeWidth(1);
        lines.add(cured);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(30);
        axisX.setValues(XValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        axisX.setHasLines(true); //x 轴分割线
        Axis axisY = new Axis();  //Y轴
        axisY.setName("人数");
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        linechart.setInteractive(true);
        linechart.setZoomType(ZoomType.HORIZONTAL);
        linechart.setMaxZoom((float) 3);//最大方法比例
        linechart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        linechart.setLineChartData(data);
        linechart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(linechart.getMaximumViewport());
        v.left = 0;
        v.right = 9;
        linechart.setCurrentViewport(v);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        SearchView searchView = root.findViewById(R.id.datasearch);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Input region for data…");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ConnectInterface.GetData(query).subscribe(new Consumer<Dataset>() {
                    @Override
                    public void accept(Dataset cdataset) {
                        local_data = cdataset;
                        if (cdataset == null) return;
                        XValues.clear();
                        Confirmed.clear();
                        Cured.clear();
                        Dead.clear();
                        getX();
                        getConfirmed();
                        getCured();
                        getDead();
                        initLineChart();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        linechart = root.findViewById(R.id.line_chart);
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
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