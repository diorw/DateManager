package com.example.wangchang.wda1150_xwk1151.Been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dell-pc on 2017/7/5.
 */
@Entity
public class AccountBeen {
    //收支表的Been类
    @Id(autoincrement = true)
    private long id;
    @NotNull
    private String name;
    //显示收入还是支出
    private String type;
    private Float money;
    private String month;
    private String date;
    private String introduce;
    @Generated(hash = 2095425094)
    public AccountBeen(long id, @NotNull String name, String type, Float money,
            String month, String date, String introduce) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.money = money;
        this.month = month;
        this.date = date;
        this.introduce = introduce;
    }
    @Generated(hash = 2120149367)
    public AccountBeen() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Float getMoney() {
        return this.money;
    }
    public void setMoney(Float money) {
        this.money = money;
    }
    public String getMonth() {
        return this.month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getIntroduce() {
        return this.introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }



    



}
