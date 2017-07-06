package com.example.wangchang.wda1150_xwk1151;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.wangchang.wda1150_xwk1151.Been.TaskBeen;

import java.util.ArrayList;
import java.util.List;

import static com.example.wangchang.wda1150_xwk1151.R.color.colorAccent;

/**
 * Created by ACM on 2017/7/2.
 */

public class TaskSettingactivity extends AppCompatActivity {
    private EditText startTxtview;
    private EditText titleView;
    private EditText endTxtview;
    private ListPopupWindow mListPop;
    private EditText fre;
    private EditText remind_time;
    private List<String> lists =  new ArrayList<>();
    private ActionMenuItemView save;
    private long taskid;
    private TaskBeen tasknow;
    private Long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(colorAccent));
        toolbar.setTitle("任务详细设置");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.task_toolbar_menu);
        lists.add("一次性");
        lists.add("每天");
        lists.add("每周");
        taskid = getIntent().getExtras().getLong("taskId");
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        final TaskBeenDao taskBeenDao = daosession.getTaskBeenDao();

        startTxtview = (EditText)findViewById(R.id.starttime);
        remind_time = (EditText)findViewById(R.id.remind_time);
        titleView = (EditText)findViewById(R.id.tasktitle_set);
        fre = (EditText)findViewById(R.id.frequence);
        endTxtview = (EditText)findViewById(R.id.endtime);
        taskid = getIntent().getLongExtra("taskId",-1);
        if(taskid!=-1) {
            tasknow = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Id.eq(taskid)).build().unique();
            startTxtview.setText(tasknow.getStartTime());
            endTxtview.setText(tasknow.getEndTime());
            titleView.setText(tasknow.getTitle());
        }
     /*   mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter<String>(this,R.layout.frequenceitemlayout,lists));
        mListPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPop.setModal(true);
        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fre.setText(lists.get(i));
                mListPop.dismiss();
            }
        });
        fre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListPop.show();
            }
        });*/

        startTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener,1,0,true);
                dialog.show();


            }
        });
        remind_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener3,1,0,true);
                dialog.show();
            }
        });
        endTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener2,1,0,true);
                dialog.show();
            }
        });


        save = (ActionMenuItemView)findViewById(R.id.ok);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskid!=-1) {
                    tasknow.setEndTime(endTxtview.getText().toString());
                    // tasknow.setStartTime(remind_time.getText().toString());
                    tasknow.setTitle(titleView.getText().toString());
                    tasknow.setStartTime(startTxtview.getText().toString());
                    taskBeenDao.updateInTx(tasknow);
                }else{
                    TaskBeen temp = taskBeenDao.queryBuilder().orderAsc(TaskBeenDao.Properties.Id).unique();
                    if(temp==null){
                        id = 1L;
                    }else{
                        id = temp.getId()+1;
                    }
                    String date = getIntent().getStringExtra("date");
                    tasknow = new TaskBeen(id,date,startTxtview.getText().toString(),endTxtview.getText().toString(),null,false,remind_time.getText().toString(),titleView.getText().toString());
                    taskBeenDao.insert(tasknow);
                }
                Intent intent = new Intent(TaskSettingactivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

    }
    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            startTxtview.setText(i+":"+i1);
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener2 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            endTxtview.setText(i+":"+i1);
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener3 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            remind_time.setText(i+":"+i1);
        }

    };
}
