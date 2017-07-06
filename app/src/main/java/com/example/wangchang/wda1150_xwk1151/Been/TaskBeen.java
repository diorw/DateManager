package com.example.wangchang.wda1150_xwk1151.Been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ACM on 2017/7/2.
 */
@Entity
public class TaskBeen {
    //任务的详细设置界面been
    @Id(autoincrement = true)
    private long Id;
    //开始结束时间
    @NotNull
    private String date;
    private String StartTime;
    private String EndTime;
    //任务描述
    private String Description;
    //任务是否已经完成
    private Boolean isCompleted;
    //提醒时间
    private String RemindTime;
    //任务标题

    @NotNull
    private String title;

    @Generated(hash = 626747351)
    public TaskBeen(long Id, @NotNull String date, String StartTime, String EndTime,
            String Description, Boolean isCompleted, String RemindTime,
            @NotNull String title) {
        this.Id = Id;
        this.date = date;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Description = Description;
        this.isCompleted = isCompleted;
        this.RemindTime = RemindTime;
        this.title = title;
    }

    @Generated(hash = 1922019252)
    public TaskBeen() {
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getRemindTime() {
        return this.RemindTime;
    }

    public void setRemindTime(String RemindTime) {
        this.RemindTime = RemindTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




   
}
