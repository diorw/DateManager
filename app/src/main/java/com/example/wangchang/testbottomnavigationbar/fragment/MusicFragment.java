package com.example.wangchang.testbottomnavigationbar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.wangchang.testbottomnavigationbar.Been.SimpleTaskBeen;
import com.example.wangchang.testbottomnavigationbar.Been.TaskBeen;
import com.example.wangchang.testbottomnavigationbar.DaoMaster;
import com.example.wangchang.testbottomnavigationbar.DaoSession;
import com.example.wangchang.testbottomnavigationbar.EventDecorator;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.TaskAdapter;
import com.example.wangchang.testbottomnavigationbar.TaskBeenDao;
import com.example.wangchang.testbottomnavigationbar.TaskSettingactivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by WangChang on 2016/5/15.
 */
public class MusicFragment extends Fragment {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;
    private RecyclerView recyclerView;
    MaterialCalendarView widget;
    public List<TaskBeen> tasks;
    private TaskBeenDao taskBeenDao;
    private String now;
    private String[] tasktitle = {"背单词","给XXX打电话"};
    private Boolean[] iscomplete = {true,true};
    private TaskAdapter taskAdapter;
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
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(getContext(),"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        taskBeenDao = daosession.getTaskBeenDao();
        TaskBeen newTask = new TaskBeen(2,"2017-7-6",null,null,null,false,null,"约饭");
        taskBeenDao.insert(newTask);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tasks = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Date.eq("2017-7-1")).build().list();
        taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);
       // widget.addDecorators(new EventDecorator(R.color.green,hashset));
       widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String temp = date.toString();
                now = temp.substring(temp.lastIndexOf("{")+1,temp.lastIndexOf("}"));
                tasks.clear();
                tasks.addAll(taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Date.eq(now)).build().list());
                taskAdapter.notifyDataSetChanged();

                Log.d("change", "onDateSelected: "+tasks.size());
                //Toast.makeText(getContext(), now,Toast.LENGTH_LONG).show();
            }
        });
        taskAdapter.setonItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), TaskSettingactivity.class);
                startActivity(intent);
            }
        });




        //      tv.setText(getArguments().getString("ARGS"));

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            //是否可以滑动
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                //定义可以拖拽或滑动的方向 可以左右滑动
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                Collections.swap(tasks, viewHolder.getAdapterPosition(), target
                            .getAdapterPosition());

                return true;

            }
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                taskAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                TaskBeen temp= tasks.get(viewHolder.getAdapterPosition());
                taskBeenDao.delete(temp);
                tasks.remove(viewHolder.getAdapterPosition());
               // int temp = viewHolder.getAdapterPosition();
             //   Log.d("Debug__________", "onSwiped: "+temp);
                taskAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
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
   /* public void initdata(){
        tasks = new ArrayList();
        for(int i = 0;i<2;i++){
            SimpleTaskBeen simpleTaskBeen = new SimpleTaskBeen(tasktitle[i],iscomplete[i]);
            tasks.add(simpleTaskBeen);
        }
    }*/
    //为了解决在滑动删除元素时删除第一个元素造成的异常，重写了linearlayoutmanager，捕获该异常。
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}
