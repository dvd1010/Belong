package com.belonginterview.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.belonginterview.R;
import com.belonginterview.model.ItemList;
import com.belonginterview.utils.GetProductDetailsTask;

import org.apache.http.client.fluent.Async;

public class SearchActivity extends AppCompatActivity {

    private FrameLayout containerLayout;
    private ListView searchResultListView;
    private TextView staticTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        loadViews();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSearchView();
    }

    private void setSearchView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        SearchView searchView = new SearchView(this);
        actionBar.setCustomView(searchView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
        searchView.setQueryHint("Search...");
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(SearchActivity.this, newText, Toast.LENGTH_SHORT).show();
                containerLayout.setVisibility(View.GONE);
                staticTextView.setVisibility(View.VISIBLE);
                searchResultListView.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void loadViews(){
        containerLayout = (FrameLayout)findViewById(R.id.empty_container);
        searchResultListView = (ListView)findViewById(R.id.search_product_list);
        staticTextView = (TextView)findViewById(R.id.show_result_text);
    }

    public void onTaskCompleted(ItemList itemList){

    }

}
