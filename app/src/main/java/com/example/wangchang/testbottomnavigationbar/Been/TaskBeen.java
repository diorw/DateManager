package com.example.wangchang.testbottomnavigationbar.Been;

/**
 * Created by ACM on 2017/7/2.
 */

public class TaskBeen {
    //任务的详细设置界面been

    //开始结束事件
    private String DateStart;
    private String DateEnd;
    //任务描述
    private String Description;
    //任务是否已经完成
    private Boolean isCompleted;
    //提醒时间
    private Boolean RemindTime;
    //任务标题
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getRemindTime() {
        return RemindTime;
    }

    public void setRemindTime(Boolean remindTime) {
        RemindTime = remindTime;
    }
}
