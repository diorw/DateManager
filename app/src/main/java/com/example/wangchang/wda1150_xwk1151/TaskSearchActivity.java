package com.example.wangchang.wda1150_xwk1151;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.wangchang.wda1150_xwk1151.Been.TaskBeen;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACM on 2017/7/7.
 */

public class TaskSearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    public List<TaskBeen> alltasks,tempalltasks;
    private ImageView search_button;
    private TaskBeenDao taskBeenDao;
    private TaskAdapter taskAdapter;
    private MaterialSearchView searchView;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        toolbar.setTitle("任务查询");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        recyclerView  = (RecyclerView)findViewById(R.id.task_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(this,"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        taskBeenDao = daosession.getTaskBeenDao();
        alltasks = taskBeenDao.loadAll();
        tempalltasks = new ArrayList<>();
        tempalltasks.addAll(alltasks);
        taskAdapter = new TaskAdapter(alltasks);
        recyclerView.setAdapter(taskAdapter);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        taskAdapter.setonItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TaskSearchActivity.this, TaskSettingactivity.class);
                intent.putExtra("taskId",alltasks.get(position).getId());
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                alltasks = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Title.like("%"+query+"%")).build().list();
                taskAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                List<TaskBeen> filterTaskBeenList = filter(tempalltasks,newText);
                alltasks.clear();
                alltasks.addAll(filterTaskBeenList);
                taskAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }
            @Override
            public void onSearchViewClosed() {
                alltasks.clear();
                alltasks.addAll(tempalltasks);
                taskAdapter.notifyDataSetChanged();

            }
        });
    }

    private List<TaskBeen> filter(List<TaskBeen> tasks, String query) {
        query = query.toLowerCase();

        List<TaskBeen> filteredModelList = new ArrayList<>();
        for (TaskBeen task : tasks) {

            final String taskTitle = task.getTitle().toLowerCase();
            final String desEn = task.getDescription();
            final String lowdesEn;
            if(desEn!=null)
                lowdesEn = desEn.toLowerCase();
            else
                lowdesEn = "";
            if (taskTitle.contains(query) || lowdesEn.contains(query)) {

                filteredModelList.add(task);
            }
        }
        return filteredModelList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.ab_search);
        searchView.setMenuItem(item);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
