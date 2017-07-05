package com.example.wangchang.testbottomnavigationbar.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.ImageButton;

import com.example.wangchang.testbottomnavigationbar.AccountBeenDao;
import com.example.wangchang.testbottomnavigationbar.Been.AccountBeen;
import com.example.wangchang.testbottomnavigationbar.DaoMaster;
import com.example.wangchang.testbottomnavigationbar.DaoSession;
import com.example.wangchang.testbottomnavigationbar.MonthAdapter;
import com.example.wangchang.testbottomnavigationbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChang on 2016/5/15.
 */
public class GameFragment extends Fragment{

    private ArrayList<String> mMonthList;
    private ArrayList<String> mInList;
    private ArrayList<String> mOutList;
    private ArrayList<String> mAllList;

    private GridView gridView;

    private View add;

    private List<AccountBeen> accountBeens_in;

    private List<AccountBeen> accountBeens_out;

    private double insum = 0.00;

    private double outsum = 0.00;

    private double allsum = 0.00;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_select_month, container, false);
        mMonthList = new ArrayList<String>();
        mInList = new ArrayList<String>();
        mOutList = new ArrayList<String>();
        mAllList = new ArrayList<String>();


        //初始化数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(),"account-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountBeenDao accountBeenDao = daoSession.getAccountBeenDao();

        //插入
//        AccountBeen acc1 = new AccountBeen(1,"name",1,100.00,"10月","2017-7-5","1");
//        accountBeenDao.insert(acc1);

//        AccountBeen acc2 = new AccountBeen(2,"name2",1,200.00,"10月","2017-7-5","1");
//        accountBeenDao.insert(acc2);
//
//        AccountBeen acc3 = new AccountBeen(3,"name3",2,100.00,"10月","2017-7-5","1");
//        accountBeenDao.insert(acc3);

        //查询
        accountBeens_in = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq(1))
                .orderAsc(AccountBeenDao.Properties.Id)
                .limit(5)
                .build().list();


        accountBeens_out = accountBeenDao.queryBuilder().where(AccountBeenDao.Properties.Type.eq(2))
                .orderAsc(AccountBeenDao.Properties.Id)
                .limit(5)
                .build().list();


        for(int i = 0;i<12;i++){
            for (int j=0;j<accountBeens_in.size();j++){
                if (accountBeens_in.get(j).getMonth().equals(i+1+"月")) {
                    insum += accountBeens_in.get(j).getMoney();
                }
            }
            for (int k=0;k<accountBeens_out.size();k++){
                if (accountBeens_out.get(k).getMonth().equals(i+1+"月")){
                    outsum += accountBeens_out.get(k).getMoney();
                }
            }
            allsum = insum - outsum;
            if (allsum<0){
                mAllList.add("-"+allsum);
            }else {
                mAllList.add("+"+allsum);
            }
            mMonthList.add(i+1+"月");
            mInList.add("+"+insum);
            mOutList.add("-"+outsum);

            insum = 0.00;
            outsum = 0.00;
            allsum = 0.00;
        }


        gridView = (GridView) view.findViewById(R.id.select_month);




        gridView.setAdapter(new MonthAdapter(getActivity(), mMonthList, mInList, mOutList, mAllList));



        add = view.findViewById(R.id.addbtn);




        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position+1+"月");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("add");
            }
        });





    }

    public static GameFragment newInstance(String content) {
        Bundle args = new Bundle();

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
