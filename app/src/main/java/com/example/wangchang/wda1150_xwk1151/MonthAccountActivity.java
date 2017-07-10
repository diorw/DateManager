package com.example.wangchang.wda1150_xwk1151;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wangchang.wda1150_xwk1151.Been.AccountBeen;

import java.util.List;

import static com.example.wangchang.wda1150_xwk1151.R.color.colorAccent;

/**
 * Created by dell-pc on 2017/7/9.
 */

public class MonthAccountActivity extends AppCompatActivity {

    private String month;
    private String month_in;
    private String month_out;
    private String month_all;

    private TextView tv_month_in;
    private TextView tv_month_out;
    private TextView tv_month_all;

    private ActionMenuItemView add;

    private List<AccountBeen> accountBeens_in;

    private List<AccountBeen> accountBeens_out;

    private double insum = 0.00;

    private double outsum = 0.00;

    private double allsum = 0.00;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthaccount);

        month = getIntent().getExtras().getString("month");
//        month_in = getIntent().getExtras().getString("month_in");
//        month_out = getIntent().getExtras().getString("month_out");
//        month_all = getIntent().getExtras().getString("month_all");


        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.month_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(colorAccent));
        toolbar.setTitle(month+"月收支项目");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.monthaccount_toolbar_menu);

        add = (ActionMenuItemView) findViewById(R.id.add_account);


        //初始化数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MonthAccountActivity.this,"account-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountBeenDao accountBeenDao = daoSession.getAccountBeenDao();

        //查询
        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"),AccountBeenDao.Properties.Month.eq(month))
                .orderAsc(AccountBeenDao.Properties.Id)
                .build().list();


        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("支出"),AccountBeenDao.Properties.Month.eq(month))
                .orderAsc(AccountBeenDao.Properties.Id)
                .build().list();

        for (int j=0;j<accountBeens_in.size();j++){
            insum += accountBeens_in.get(j).getMoney();

        }

        for (int k=0;k<accountBeens_out.size();k++){
            outsum += accountBeens_out.get(k).getMoney();

        }
        allsum = insum - outsum;
        if (allsum<0){
            month_all = (""+allsum);
        }else {
            month_all = ("+"+allsum);
        }
        month_in = ("+"+insum);
        month_out = ("-"+outsum);

        tv_month_in = (TextView) findViewById(R.id.month_in);
        tv_month_in.setText(month_in);
        tv_month_out = (TextView) findViewById(R.id.month_out);
        tv_month_out.setText(month_out);
        tv_month_all = (TextView) findViewById(R.id.month_all);
        tv_month_all.setText(month_all);










        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthAccountActivity.this,AddAccount_forMonthActivity.class);

                intent.putExtra("month",month);

                startActivity(intent);
            }
        });

    }

}
