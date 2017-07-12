package com.example.wangchang.wda1150_xwk1151.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;

import com.example.wangchang.wda1150_xwk1151.AccountBeenDao;
import com.example.wangchang.wda1150_xwk1151.AddAccount_forFloatingActivity;
import com.example.wangchang.wda1150_xwk1151.Been.AccountBeen;
import com.example.wangchang.wda1150_xwk1151.DaoMaster;
import com.example.wangchang.wda1150_xwk1151.DaoSession;
import com.example.wangchang.wda1150_xwk1151.LoginActivity;
import com.example.wangchang.wda1150_xwk1151.MainActivity;
import com.example.wangchang.wda1150_xwk1151.MonthAccountActivity;
import com.example.wangchang.wda1150_xwk1151.MonthAdapter;
import com.example.wangchang.wda1150_xwk1151.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChang on 2016/5/15.
 */
public class AccountFragment extends Fragment{

    private ArrayList<String> mMonthList;
    private ArrayList<String> mInList;
    private ArrayList<String> mOutList;
    private ArrayList<String> mAllList;

    private GridView gridView;

    private View add;

    private List<AccountBeen> accountBeens_in;

    private List<AccountBeen> accountBeens_out;

    private Float insum = 0.00f ;

    private Float outsum =0.00f ;

    private Float allsum =0.00f ;

    private Float in = 0.00f;
    private Float out  = 0.00f;
    private Float all = 0.00f;

    private String month;

    private FloatingActionsMenu menuMultipleActions;
    private FloatingActionButton actionA;
    AccountBeenDao accountBeenDao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_select_month, container, false);
        mMonthList = new ArrayList<String>();
        mInList = new ArrayList<String>();
        mOutList = new ArrayList<String>();
        mAllList = new ArrayList<String>();
        DecimalFormat df = new DecimalFormat("#.00");


        //初始化数据库
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(),"account-db",null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        accountBeenDao = daoSession.getAccountBeenDao();

//        accountBeenDao.deleteAll();
//
//        //插入
//        AccountBeen acc1 = new AccountBeen(1,"name","收入",100.00,"10","2017-07-5","1");
//        accountBeenDao.insert(acc1);
//
//        AccountBeen acc2 = new AccountBeen(2,"name2","收入",200.00,"10","2017-07-5","1");
//        accountBeenDao.insert(acc2);
//
//        AccountBeen acc3 = new AccountBeen(3,"name3","支出",100.00,"10","2017-07-5","1");
//        accountBeenDao.insert(acc3);
//
//        AccountBeen acc4 = new AccountBeen(4,"name3","支出",100.00,"09","2017-07-5","1");
//        accountBeenDao.insert(acc4);

        //查询
//        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"))
//                .orderAsc(AccountBeenDao.Properties.Id)
//                .build().list();
//
//
//        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("支出"))
//                .orderAsc(AccountBeenDao.Properties.Id)
//                .build().list();
//
//
//        for(int i = 0;i<12;i++){
//            String month_every;
//            if (i<9)
//            {
//                month_every = ("0"+(i+1));
//            }
//            else
//            {
//                month_every = (""+(i+1));
//            }
//            DecimalFormat   fnum   =   new   DecimalFormat("##0.00");
//            for (int j=0;j<accountBeens_in.size();j++){
//                if (accountBeens_in.get(j).getMonth().equals(month_every)) {
//                    insum += accountBeens_in.get(j).getMoney();
//
//                }
//            }
//            for (int k=0;k<accountBeens_out.size();k++){
//                if (accountBeens_out.get(k).getMonth().equals(month_every)){
//                    outsum += accountBeens_out.get(k).getMoney();
//                }
//            }
//            allsum = insum - outsum;
//            String all = fnum.format(allsum);
//            String in = fnum.format(insum);
//            String out = fnum.format(outsum);
//            if (allsum<0){
//
//                mAllList.add(""+all);
//            }else {
//                mAllList.add("+"+all);
//            }
//            mMonthList.add(i+1+"月");
//            mInList.add("+"+in);
//            mOutList.add("-"+out);
//
//            insum = 0.00f;
//            outsum = 0.00f;
//            allsum = 0.00f;
//        }
//
//
//        gridView = (GridView) view.findViewById(R.id.select_month);
//
//
//
//
//        gridView.setAdapter(new MonthAdapter(getActivity(), mMonthList, mInList, mOutList, mAllList));
//
//
//
//        add = view.findViewById(R.id.addbtn);
//
//
//
//        menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
//
//
//        actionA = (FloatingActionButton) view.findViewById(R.id.action_a);
//
//
//
//        return view;

        initData(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position+1+"月");
                if (position<9){
                    month = ("0"+(position+1));
                }else {
                    month = (""+(position+1));
                }
                Intent intent = new Intent(getContext(), MonthAccountActivity.class);
                intent.putExtra("month",month);
                intent.putExtra("month_in",mInList.get(position));
                intent.putExtra("month_out",mOutList.get(position));
                intent.putExtra("month_all",mAllList.get(position));
                startActivityForResult(intent,1);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                actionA.setTitle("Action A clicked");
                Intent intent = new Intent(getContext(), AddAccount_forFloatingActivity.class);
                startActivityForResult(intent,1);

            }
        });





    }
    public void initData(View view){

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(),"account-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        accountBeenDao = daoSession.getAccountBeenDao();


        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("收入"))
                .orderAsc(AccountBeenDao.Properties.Id)
                .build().list();


        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq("支出"))
                .orderAsc(AccountBeenDao.Properties.Id)
                .build().list();


        for(int i = 0;i<12;i++){
            String month_every;
            if (i<9)
            {
                month_every = ("0"+(i+1));
            }
            else
            {
                month_every = (""+(i+1));
            }
            DecimalFormat   fnum   =   new   DecimalFormat("##0.00");
            for (int j=0;j<accountBeens_in.size();j++){
                if (accountBeens_in.get(j).getMonth().equals(month_every)) {
                    insum += accountBeens_in.get(j).getMoney();

                }
            }
            for (int k=0;k<accountBeens_out.size();k++){
                if (accountBeens_out.get(k).getMonth().equals(month_every)){
                    outsum += accountBeens_out.get(k).getMoney();
                }
            }
            allsum = insum - outsum;
            String all = fnum.format(allsum);
            String in = fnum.format(insum);
            String out = fnum.format(outsum);
            if (allsum<0){

                mAllList.add(""+all);
            }else {
                mAllList.add("+"+all);
            }
            mMonthList.add(i+1+"月");
            mInList.add("+"+in);
            mOutList.add("-"+out);

            insum = 0.00f;
            outsum = 0.00f;
            allsum = 0.00f;
        }


        gridView = (GridView) view.findViewById(R.id.select_month);




        gridView.setAdapter(new MonthAdapter(getActivity(), mMonthList, mInList, mOutList, mAllList));



        add = view.findViewById(R.id.addbtn);



        menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);


        actionA = (FloatingActionButton) view.findViewById(R.id.action_a);

    }

    public static AccountFragment newInstance(String content) {
        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == 2)){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.gotoAccountFragment();
        }
    }
}
