package com.example.wangchang.wda1150_xwk1151.Been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dell-pc on 2017/7/12.
 */
@Entity
public class UserBeen {
    //用户的Been类
    @Id(autoincrement = true)
    Long id;
    @NotNull
    String email;
    @NotNull
    String password;
    @Generated(hash = 945803044)
    public UserBeen(Long id, @NotNull String email, @NotNull String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    @Generated(hash = 1404545435)
    public UserBeen() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
