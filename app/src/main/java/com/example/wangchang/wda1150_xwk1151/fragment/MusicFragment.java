package com.example.wangchang.wda1150_xwk1151.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gc.materialdesign.views.ButtonFloatSmall;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangchang.wda1150_xwk1151.Been.TaskBeen;
import com.example.wangchang.wda1150_xwk1151.DaoMaster;
import com.example.wangchang.wda1150_xwk1151.DaoSession;
import com.example.wangchang.wda1150_xwk1151.EventDecorator;
import com.example.wangchang.wda1150_xwk1151.R;
import com.example.wangchang.wda1150_xwk1151.TaskAdapter;
import com.example.wangchang.wda1150_xwk1151.TaskBeenDao;
import com.example.wangchang.wda1150_xwk1151.TaskSettingactivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.gc.materialdesign.views.ButtonRectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by WangChang on 2016/5/15.
 */
public class MusicFragment extends Fragment {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;
    private RecyclerView recyclerView;
    MaterialCalendarView widget;
    public List<TaskBeen> tasks;
    public List<TaskBeen> alltasks;
    private TaskBeenDao taskBeenDao;
    private String now;
    private TaskAdapter taskAdapter;
    private HashSet<CalendarDay> hashset = new HashSet<>();
    private Date datetemp;
    private ButtonRectangle addtask;
    private java.sql.Date sdate;//当前选中的日期
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datetasklayout, container, false);
        recyclerView  = (RecyclerView)view.findViewById(R.id.taskrecycleview);
        widget = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(getContext(),"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        taskBeenDao = daosession.getTaskBeenDao();
        addtask = (ButtonRectangle) view.findViewById(R.id.addtask);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    //   taskBeenDao.deleteAll();
        datetemp= new Date();
        SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd");
        String now =dateformate.format(datetemp);
        Log.d("nowtime", "onActivityCreated: "+now);
      /*  TaskBeen newTask = new TaskBeen(1,now,null,null,null,false,null,"约饭2");
        taskBeenDao.insert(newTask);*/

        alltasks = taskBeenDao.loadAll();
     //   Log.d("tasklenth", "onActivityCreated: "+alltasks.size());
        for(int i = 0;i<alltasks.size();i++){
            String nowday = alltasks.get(i).getDate();

            String[] data= nowday.split("-");

            CalendarDay temp = (CalendarDay.from(Integer.valueOf(data[0]),Integer.valueOf(data[1])-1,Integer.valueOf(data[2])));
            Log.d("day", "onActivityCreated: "+data[0]);
            Log.d("day", "onActivityCreated: "+data[1]);
            Log.d("day", "onActivityCreated: "+data[2]);
            if(!hashset.contains(temp)){
                hashset.add(temp);
            }

        }
      //  Log.d("hashset", "onActivityCreated: "+hashset.size());
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tasks = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Date.eq(now)).build().list();
        taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);
        //给含有任务的天加绿点
        widget.addDecorators(new EventDecorator(R.color.green,hashset));
        widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                sdate = new java.sql.Date(date.getDate().getTime());
                tasks.clear();
                tasks.addAll(taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Date.eq(sdate.toString())).build().list());
                taskAdapter.notifyDataSetChanged();

                Log.d("change", "onDateSelected: "+sdate.toString());
            //    Toast.makeText(getContext(),date.getDate().toString(),Toast.LENGTH_LONG).show();
            }
        });
        taskAdapter.setonItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), TaskSettingactivity.class);
                intent.putExtra("taskId",tasks.get(position).getId());
                startActivity(intent);
            }
        });
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),TaskSettingactivity.class);
                intent.putExtra("taskId",-1);
                if(sdate==null){
                    Toast.makeText(getContext(),"请选择一个日期",Toast.LENGTH_LONG).show();
                }else {
                    intent.putExtra("date", sdate.toString());
                    startActivity(intent);
                }
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
