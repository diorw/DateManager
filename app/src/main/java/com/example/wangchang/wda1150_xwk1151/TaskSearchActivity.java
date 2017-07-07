package com.example.wangchang.wda1150_xwk1151;

import android.app.SearchManager;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

/**
 * Created by ACM on 2017/7/7.
 */

public class TaskSearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView search_button;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);

        setSupportActionBar(toolbar);
        search_button = (ImageView)findViewById(android.support.v7.appcompat.R.id.search_button);
        search_button.setImageResource(R.drawable.ic_search_btn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
