package com.dapeng.notes.Money;

/**
 * Created by 10341 on 2018/3/18.
 */

public class MoneyInfo {
    public  String id;
    public double in;
    public double out;
    public String month;
    public String time;
    public MoneyInfo(String id, double in, double out, String month, String time) {
        this.id = id;
        this.in = in;
        this.out = out;
        this.month = month;
        this.time = time;
    }

    public double getIn() {
        return in;
    }
    public void setIn(double in) {
        this.in = in;
    }
    public double getOut() {
        return out;
    }
    public void setOut(double out) {
        this.out = out;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getId() {
        return id;
    }
}
