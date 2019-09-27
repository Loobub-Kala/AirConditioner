package com.example.kala.airconditioner;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static boolean isSuccess;     //是否响应成功
    private static final int TIME = 3000;

    private IotApi iotApi;
    private WenduKu data;
    private boolean canAuto;     //能否自动控制

    private boolean isOpen;      //开关是否打开
    private double value;       //设置的自动控制的值
    private String lastTime = "";

    private TextView tem1;
    private TextView tem2;
    private TextView airState;
    private Button hand;
    private Button auto;
    private Button open;
    //private Button close;
    private EditText edit;
    private Button setting;

    private LinearLayout noChoose;
    private LinearLayout chooseHand;
    private LinearLayout chooseAuto;

    private LineChart lineChart1;
    private LineData lineData;
    private int numX = 0;
    private ArrayList<Double> datas = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tem1 = findViewById(R.id.tem1);
        tem2 = findViewById(R.id.tem2);
        airState = findViewById(R.id.airState);
        hand = findViewById(R.id.hand);
        auto = findViewById(R.id.auto);
        open = findViewById(R.id.open);
        //close=findViewById(R.id.close);
        edit = findViewById(R.id.edit);
        setting = findViewById(R.id.setting);

        noChoose = findViewById(R.id.noChoose);
        chooseHand = findViewById(R.id.chooseHand);
        chooseAuto = findViewById(R.id.chooseAuto);

        hand.setOnClickListener(this);
        auto.setOnClickListener(this);
        open.setOnClickListener(this);
        //close.setOnClickListener(this);
        setting.setOnClickListener(this);

        form1();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hand:
                noChoose.setVisibility(View.GONE);
                chooseHand.setVisibility(View.VISIBLE);
                chooseAuto.setVisibility(View.GONE);
                canAuto = false;
                hand.setEnabled(false);
                auto.setEnabled(true);
                tem2.setText("未设置");
                break;
            case R.id.auto:
                noChoose.setVisibility(View.GONE);
                chooseHand.setVisibility(View.GONE);
                chooseAuto.setVisibility(View.VISIBLE);
                canAuto = true;
                hand.setEnabled(true);
                auto.setEnabled(false);
                tem2.setText("未设置");
                break;
            case R.id.open:
                while (true) {
                    if (!isOpen) {
                        form2("air", "1");
                    } else {
                        form2("air", "0");
                    }
                    if (isSuccess) {
                        isSuccess = false;
                        if (!isOpen) {
                            airState.setText("空调状态：开");
                            Toast.makeText(MainActivity.this, "空调开启", Toast.LENGTH_SHORT);
                            open.setText("关");
                            isOpen = true;
                        } else {
                            airState.setText("空调状态：关");
                            Toast.makeText(MainActivity.this, "空调关闭", Toast.LENGTH_SHORT);
                            open.setText("开");
                            isOpen = false;
                        }
                        break;
                    }
                }
                break;
            case R.id.setting:
                value = Double.parseDouble(edit.getText().toString());
                tem2.setText(String.valueOf(value) + "°C");
                form3();
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                data = iotApi.getSensor("wendu");
                                if (!data.getTime().equals(lastTime)) {
                                    datas.add(Double.parseDouble(data.getData()));
                                    String timeStr = data.getTime();
                                    times.add(timeStr.substring(timeStr.length() - 5, timeStr.length()));
                                    numX++;
                                    initChart1(times);
                                    lastTime = data.getTime();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //此处进行UI操作，将结果显示
                                    tem1.setText(data.getData() + "°C");
                                }
                            });
                        }
                    }).start();
                    break;
                }
            }
        }
    };

    public void form1() {
        try {
            iotApi = new IotApi();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(TIME);
                            mHandler.sendEmptyMessage(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void form2(final String apiId, final String i) {
        try {
            iotApi = new IotApi();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    iotApi.sendCommand(apiId, i);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void form3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (canAuto) {
                    if (Double.parseDouble(data.getData()) > value)
                        form2("air", "1");
                    else
                        form2("air", "0");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //if (isSuccess) {
                            //isSuccess = false;
                            if (!isOpen) {
                                airState.setText("空调状态：开");
                                Toast.makeText(MainActivity.this, "空调开启", Toast.LENGTH_SHORT);
                                open.setText("关");
                                isOpen = true;
                            } else {
                                airState.setText("空调状态：关");
                                Toast.makeText(MainActivity.this, "空调关闭", Toast.LENGTH_SHORT);
                                open.setText("开");
                                isOpen = false;
                            }
                            // }
                        }
                    });
                }
            }
        }).start();
    }

    private void initChart1(ArrayList<String> times) {
        lineChart1 = (LineChart) findViewById(R.id.spread_line_chart);
        //设置图表的描述
        Description description = new Description();
        description.setText("时间");
        lineChart1.setDescription(description);
        //设置折线的名称
        LineChartManager.setLineName("室温");
        //创建两条折线的图表
        lineData = LineChartManager.initSingleLineChart(this, lineChart1, numX, datas, times);
        LineChartManager.initDataStyle(lineChart1, lineData, this, times);
        lineChart1.moveViewToX(numX);
    }

    public static boolean isIsSuccess() {
        return isSuccess;
    }

    public static void setIsSuccess(boolean isSuccess) {
        MainActivity.isSuccess = isSuccess;
    }
}
