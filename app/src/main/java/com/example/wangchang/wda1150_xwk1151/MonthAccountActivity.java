package com.example.wangchang.wda1150_xwk1151;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.wangchang.wda1150_xwk1151.Been.AccountBeen;

import java.text.DecimalFormat;
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
    private ActionMenuItemView pie;

    private List<AccountBeen> accountBeens_in;

    private List<AccountBeen> accountBeens_out;
    private List<AccountBeen> accountBeens;

    private Float insum = 0.00f;

    private Float outsum = 0.00f;

    private Float allsum = 0.00f;

    private LinearLayout in_btn;
    private LinearLayout out_btn;

    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    private AccountAdapter accountAdapter;
    private AccountBeenDao accountBeenDao;
    DecimalFormat decimalFormat;



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
//        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_48dp);

        add = (ActionMenuItemView) findViewById(R.id.add_account);
        pie = (ActionMenuItemView) findViewById(R.id.pie);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });


        //初始化数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MonthAccountActivity.this,"account-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();

        accountBeenDao = daoSession.getAccountBeenDao();

        //查询
        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"),AccountBeenDao.Properties.Month.eq(month))
                .orderDesc(AccountBeenDao.Properties.Date)
                .build().list();


        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("支出"),AccountBeenDao.Properties.Month.eq(month))
                .orderDesc(AccountBeenDao.Properties.Date)
                .build().list();

        decimalFormat = new DecimalFormat("#,##0.00");
        for (int j=0;j<accountBeens_in.size();j++){
            insum += accountBeens_in.get(j).getMoney();

        }

        for (int k=0;k<accountBeens_out.size();k++){
            outsum += accountBeens_out.get(k).getMoney();

        }
        allsum = insum - outsum;
        if (allsum<0){

            month_all = (""+decimalFormat.format(allsum));
        }else {
            month_all = ("+"+decimalFormat.format(allsum));
        }

        month_in = ("+"+decimalFormat.format(insum));
        month_out = ("-"+decimalFormat.format(outsum));

        tv_month_in = (TextView) findViewById(R.id.month_in);
        tv_month_in.setText(month_in);
        tv_month_out = (TextView) findViewById(R.id.month_out);
        tv_month_out.setText(month_out);
        tv_month_all = (TextView) findViewById(R.id.month_all);
        tv_month_all.setText(month_all);

        in_btn = (LinearLayout) findViewById(R.id.in_btn);
        out_btn = (LinearLayout) findViewById(R.id.out_btn);
        in_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
        out_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));


        mRecyclerView  = (RecyclerView) findViewById(R.id.accountrecyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        accountBeens =accountBeens_in;
        accountAdapter = new AccountAdapter(accountBeens);
        mRecyclerView.setAdapter(accountAdapter);

        accountAdapter.setonItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                //点击选项，弹窗显示
                final MaterialDialog dialog = new MaterialDialog.Builder(MonthAccountActivity.this)
                        .customView(R.layout.showaccount, false)
                        .positiveText("修改")
                        .negativeText("取消")
                        .neutralText("删除")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.NEGATIVE){
                                    //取消点击事件
                                    dialog.dismiss();
                                }
                                else if (which == DialogAction.POSITIVE){
                                    //修改点击事件
                                    Intent intent = new Intent(MonthAccountActivity.this,ChangedAccountActivity.class);

                                    intent.putExtra("month",month);
                                    intent.putExtra("accountId",accountBeens.get(position).getId());

                                    startActivity(intent);
                                }
                                else if (which == DialogAction.NEUTRAL) {
                                    //删除点击事件
                                    String thistype = accountBeens.get(position).getType();
                                    if (thistype.equals("收入")){
                                        in_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
                                        out_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));
                                    }else if (thistype.equals("支出")){
                                        out_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
                                        in_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));
                                    }
                                    accountBeenDao.deleteByKey(accountBeens.get(position).getId());
                                    initDataAfterDelete();
                                    accountBeens.clear();
                                    accountBeens.addAll(accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq(thistype),AccountBeenDao.Properties.Month.eq(month))
                                            .orderDesc(AccountBeenDao.Properties.Date)
                                            .build().list());
                                    accountAdapter.notifyDataSetChanged();
                                    dialog.dismiss();


                                }
                            }
                        })
                        .show();
                View customeView = dialog.getCustomView();


                TextView name = (TextView) customeView.findViewById(R.id.accountname);
                TextView money = (TextView) customeView.findViewById(R.id.accountmoney);
                TextView type = (TextView) customeView.findViewById(R.id.accounttype);
                TextView time = (TextView) customeView.findViewById(R.id.accounttime);
                TextView intro = (TextView) customeView.findViewById(R.id.accountintroduce);

                name.setText(accountBeens.get(position).getName());
                money.setText(decimalFormat.format(accountBeens.get(position).getMoney()));
                type.setText(accountBeens.get(position).getType());
                time.setText(accountBeens.get(position).getDate());
                intro.setText(accountBeens.get(position).getIntroduce());

            }
        });





        in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
                out_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));
                accountBeens.clear();
                accountBeens.addAll(accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"),AccountBeenDao.Properties.Month.eq(month))
                        .orderDesc(AccountBeenDao.Properties.Date)
                        .build().list());
                accountAdapter.notifyDataSetChanged();
            }
        });
        out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
                in_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));
                accountBeens.clear();
                accountBeens.addAll(accountBeens_out);
                accountAdapter.notifyDataSetChanged();
            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthAccountActivity.this,AddAccount_forMonthActivity.class);

                intent.putExtra("month",month);

                startActivityForResult(intent,1);
            }
        });
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthAccountActivity.this,PieChartActivity.class);
                startActivity(intent);
            }
        });

    }
    public void initDataAfterDelete(){
        insum = 0f;
        outsum = 0f;
        allsum = 0f;

        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"),AccountBeenDao.Properties.Month.eq(month))
                .orderDesc(AccountBeenDao.Properties.Date)
                .build().list();


        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("支出"),AccountBeenDao.Properties.Month.eq(month))
                .orderDesc(AccountBeenDao.Properties.Date)
                .build().list();


        decimalFormat = new DecimalFormat("#,##0.00");
        for (int j=0;j<accountBeens_in.size();j++){
            insum += accountBeens_in.get(j).getMoney();

        }

        for (int k=0;k<accountBeens_out.size();k++){
            outsum += accountBeens_out.get(k).getMoney();

        }
        allsum = insum - outsum;
        if (allsum<0){

            month_all = (""+decimalFormat.format(allsum));
        }else {
            month_all = ("+"+decimalFormat.format(allsum));
        }

        month_in = ("+"+decimalFormat.format(insum));
        month_out = ("-"+decimalFormat.format(outsum));


        tv_month_in.setText(month_in);
        tv_month_out.setText(month_out);
        tv_month_all.setText(month_all);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1)&&(resultCode == 2)){
            initDataAfterDelete();
            in_btn.setBackground(getDrawable(R.drawable.select_tittle_backgroud));
            out_btn.setBackground(getDrawable(R.drawable.tittle_backgroud2));
            accountBeens.clear();
            accountBeens.addAll(accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"),AccountBeenDao.Properties.Month.eq(month))
                    .orderDesc(AccountBeenDao.Properties.Date)
                    .build().list());
            accountAdapter.notifyDataSetChanged();
        }
    }
}
