package com.belonginterview.activity;


import android.os.AsyncTask;
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
import com.belonginterview.adapter.SearchResultListAdapter;
import com.belonginterview.model.ItemList;
import com.belonginterview.utils.GetProductDetailsTask;
import com.belonginterview.utils.GetSearchResultsTask;
import com.belonginterview.utils.HttpAgent;
import com.belonginterview.utils.JsonHandler;

import org.apache.http.client.fluent.Async;

import java.text.MessageFormat;

public class SearchActivity extends AppCompatActivity {

    private FrameLayout containerLayout;
    private ListView searchResultListView;
    private TextView staticTextView;
    private TextView noResultView;

    public static String searchString;
    public GetSearchResultsTask getSearchResultsTask;
    private SearchResultListAdapter searchResultListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        loadViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                if (getSearchResultsTask != null) {
                    getSearchResultsTask.cancel(true);
                }
                searchString = newText;
                if(!searchString.isEmpty()) {
                    getSearchResultsTask = new GetSearchResultsTask(SearchActivity.this);
                    getSearchResultsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, searchString);
                }
                return false;
            }
        });
    }

    private void loadViews() {
        containerLayout = (FrameLayout) findViewById(R.id.empty_container);
        searchResultListView = (ListView) findViewById(R.id.search_product_list);
        staticTextView = (TextView) findViewById(R.id.show_result_text);
        noResultView = (TextView) findViewById(R.id.no_result_found);
    }

    @Override
    protected void onStop() {
        if (getSearchResultsTask != null) {
            getSearchResultsTask.cancel(true);
        }
        super.onStop();
    }

    public void onTaskCompleted(ItemList itemList) {
        noResultView.setVisibility(View.GONE);
        containerLayout.setVisibility(View.GONE);
        staticTextView.setText("Showing results for " + searchString);
        staticTextView.setVisibility(View.VISIBLE);
        searchResultListView.setVisibility(View.VISIBLE);
        if (itemList.getProducts().size() > 0) {
            searchResultListAdapter = new SearchResultListAdapter(this, R.layout.list_item_search_result, itemList.getProducts(), this);
            searchResultListView.setAdapter(searchResultListAdapter);
            searchResultListAdapter.notifyDataSetChanged();
        } else {
            containerLayout.setVisibility(View.GONE);
            noResultView.setVisibility(View.VISIBLE);
            staticTextView.setVisibility(View.GONE);
            searchResultListView.setVisibility(View.GONE);
        }

    }


}
