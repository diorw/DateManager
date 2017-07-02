package com.example.wangchang.testbottomnavigationbar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.wangchang.testbottomnavigationbar.Been.SimpleTaskBeen;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.TaskAdapter;
import com.example.wangchang.testbottomnavigationbar.TaskSettingactivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChang on 2016/5/15.
 */
public class MusicFragment extends Fragment {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;
    private RecyclerView recyclerView;
    MaterialCalendarView widget;
    public List<SimpleTaskBeen> tasks;

    private String[] tasktitle = {"背单词","给XXX打电话"};
    private Boolean[] iscomplete = {true,true};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datetasklayout, container, false);
        recyclerView  = (RecyclerView)view.findViewById(R.id.taskrecycleview);
        widget = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initdata();
        TaskAdapter taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);
        widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getContext(),date.toString(),Toast.LENGTH_LONG).show();
            }
        });
        taskAdapter.setonItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), TaskSettingactivity.class);
                startActivity(intent);
            }
        });

        /*月份变化事件
        public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
            //noinspection ConstantConditions
            getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
        }*/

        //      tv.setText(getArguments().getString("ARGS"));
    }

    public static MusicFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }
    public void initdata(){
        tasks = new ArrayList();
        for(int i = 0;i<2;i++){
            SimpleTaskBeen simpleTaskBeen = new SimpleTaskBeen(tasktitle[i],iscomplete[i]);
            tasks.add(simpleTaskBeen);
        }
    }
}
