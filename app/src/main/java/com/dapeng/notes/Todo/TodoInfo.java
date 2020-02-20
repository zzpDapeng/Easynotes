package com.dapeng.notes.Todo;

/**
 * Created by Dapeng on 2018/6/9.
 */

public class TodoInfo {
    public  String id;
    public String thing;
    public String lable;
    public String finishTime;

    public String getFinishTime() {
        return finishTime;
    }

    public String getId() {
        return id;
    }

    public String time;

    public TodoInfo( String id,String thing, String finishTime,String lable, String time) {
        this.id=id;
        this.thing = thing;
        this.finishTime = finishTime;
        this.lable = lable;
        this.time = time;
    }

    public String getThing() {
        return thing;
    }

    public String getLable() {
        return lable;
    }

    public String getTime() {
        return time;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
