package com.example.kala.airconditioner;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartManager {

    private static String lineName = null;
    private static String lineName1 = null;

    /**
     * 创建一条折线
     * @param context 上下文
     * @param mLineChart 对象
     * @param count X轴的数据
     * @param datas Y轴的数据
     * @param times
     * @return LineData
     */
    public static LineData initSingleLineChart(Context context, LineChart mLineChart, int count, ArrayList<Double> datas, ArrayList<String> times) {

        // 数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues.add(new Entry(i, datas.get(i).floatValue()));
        }
        //设置折线的样式
        LineDataSet dataSet = new LineDataSet(yValues, lineName);
        //用y轴的集合来设置参数
        dataSet.setLineWidth(1.75f); // 线宽
        dataSet.setCircleSize(2f);// 显示的圆形大小
        dataSet.setColor(Color.rgb(89, 194, 230));// 折线显示颜色
        dataSet.setCircleColor(Color.rgb(89, 194, 230));// 圆形折点的颜色
        dataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色
        dataSet.setHighlightEnabled(true);
        dataSet.setValueTextColor(Color.rgb(89, 194, 230)); //数值显示的颜色
        dataSet.setValueTextSize(8f);     //数值显示的大小

        //构建一个LineData  将dataSet放入
        LineData lineData = new LineData(dataSet);
        return lineData;
    }

    /**
     * @param context    上下文
     * @param mLineChart 折线图控件
     * @param count      折线在x轴的值
     * @param datas1     折线在y轴的值
     * @param datas2     另一条折线在y轴的值
     * @Description:创建两条折线
     */
    public static LineData initDoubleLineChart(Context context, LineChart mLineChart, int count, ArrayList<Double> datas1, ArrayList<Double> datas2) {

        // 数据1
        ArrayList<Entry> yValues1 = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues1.add(new Entry(i, datas1.get(i).floatValue()));
        }

        // 数据2
        ArrayList<Entry> yValues2 = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues2.add(new Entry(i, datas2.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(yValues1, lineName);
        //dataSet.enableDashedLine(10f, 10f, 0f);//将折线设置为曲线(即设置为虚线)
        //用y轴的集合来设置参数
        dataSet.setLineWidth(1.75f); // 线宽
        dataSet.setCircleSize(2f);// 显示的圆形大小
        dataSet.setColor(Color.rgb(89, 194, 230));// 折线显示颜色
        dataSet.setCircleColor(Color.rgb(89, 194, 230));// 圆形折点的颜色
        dataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色
        dataSet.setHighlightEnabled(true);
        dataSet.setValueTextColor(Color.rgb(89, 194, 230)); //数值显示的颜色
        dataSet.setValueTextSize(8f);     //数值显示的大小

        LineDataSet dataSet1 = new LineDataSet(yValues2, lineName1);

        //用y轴的集合来设置参数
        dataSet1.setLineWidth(1.75f);
        dataSet1.setCircleSize(2f);
        dataSet1.setColor(Color.rgb(252, 76, 122));
        dataSet1.setCircleColor(Color.rgb(252, 76, 122));
        dataSet1.setHighLightColor(Color.GREEN);
        dataSet1.setHighlightEnabled(true);
        dataSet1.setValueTextColor(Color.rgb(252, 76, 122));
        dataSet1.setValueTextSize(8f);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(dataSet, dataSet1);
        return lineData;
    }

    /**
     * @Description:初始化图表的样式
     */
    public static void initDataStyle(LineChart lineChart, LineData lineData, Context context, ArrayList<String> times) {
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        lineChart.setDrawBorders(false); //在折线图上添加边框
        //lineChart.setDescription("时间/数据"); //数据描述
        lineChart.setDrawGridBackground(false); //表格颜色
        lineChart.setGridBackgroundColor(Color.GRAY & 0x70FFFFFF); //表格的颜色，设置一个透明度
        lineChart.setTouchEnabled(true); //可点击
        lineChart.setDragEnabled(true);  //可拖拽
        lineChart.setScaleEnabled(true);  //可缩放
        lineChart.setPinchZoom(false);
        lineChart.setBackgroundColor(Color.WHITE); //设置背景颜色

        lineChart.setData(lineData);

        Legend mLegend = lineChart.getLegend(); //设置标示，就是那个一组y的value的
        mLegend.setForm(Legend.LegendForm.SQUARE); //样式
        mLegend.setFormSize(6f); //字体
        mLegend.setTextColor(Color.GRAY); //颜色
        lineChart.setVisibleXRange(0, 6);   //x轴可显示的坐标范围

        XAxis xAxis = lineChart.getXAxis();  //x轴的标示
        xAxis.setLabelCount(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x轴位置
        xAxis.setTextColor(Color.GRAY);    //字体的颜色
        xAxis.setTextSize(10f); //字体大小
        xAxis.setGridColor(Color.GRAY);//网格线颜色
        xAxis.setDrawGridLines(false); //不显示网格线
        xAxis.setValueFormatter(new MyXFormatter(times));

        YAxis axisLeft = lineChart.getAxisLeft(); //y轴左边标示
        YAxis axisRight = lineChart.getAxisRight(); //y轴右边标示
        axisLeft.setTextColor(Color.GRAY); //字体颜色
        axisLeft.setTextSize(10f); //字体大小
        //axisLeft.setAxisMaxValue(800f); //最大值
        axisLeft.setLabelCount(5, true); //显示格数
        axisLeft.setGridColor(Color.GRAY); //网格线颜色

        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        lineChart.invalidate();
    }

    /**
     * @param name
     * @Description:设置折线的名称
     */
    public static void setLineName(String name) {
        lineName = name;
    }

    /**
     * @param name
     * @Description:设置另一条折线的名称
     */
    public static void setLineName1(String name) {
        lineName1 = name;
    }

}
