package com.example.wangchang.wda1150_xwk1151.Been;

/**
 * Created by ACM on 2017/7/2.
 */

public class SimpleTaskBeen {
    //显示在简略界面的任务
    private String title;
    private boolean isCompleted;
    public SimpleTaskBeen(String title,boolean isCompleted){
        this.title=title;
        this.isCompleted=isCompleted;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {

        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }



}
