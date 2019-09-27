package com.example.kala.airconditioner;

import org.litepal.crud.DataSupport;

public class WenduKu extends DataSupport {
    private String time;
    private String data;

    public WenduKu(){
        this.time = "未检测到数据";
        this.data = "未检测到数据";
    }

    public WenduKu(String time, String data) {
        this.time = time;
        this.data = data;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setData(String data) {

        this.data = data;
    }

    public String getTime() {

        return time;
    }

    public String getData() {

        return data;
    }
}
