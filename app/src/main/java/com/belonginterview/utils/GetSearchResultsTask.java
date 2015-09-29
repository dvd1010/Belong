package com.belonginterview.utils;

import android.app.Activity;
import android.os.AsyncTask;

import com.belonginterview.activity.SearchActivity;
import com.belonginterview.model.ItemList;

import java.text.MessageFormat;

public class GetSearchResultsTask extends AsyncTask<String, Void, ItemList> {

    private static final String GET_SEARCH_RESULTS = "http://api.buyingiq.com/v1/search/?page=1&q={0}&facet=1";

    private SearchActivity activity;
    public GetSearchResultsTask(SearchActivity activity){
        this.activity = activity;
    }

    @Override
    protected ItemList doInBackground(String... params) {
        String searchString = params[0];
        String url = MessageFormat.format(GET_SEARCH_RESULTS, searchString);
        String response = HttpAgent.get(url);
        ItemList itemList = null;
        if(response != null){
            itemList = JsonHandler.parseUnderScoredResponse(response, ItemList.class);
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(ItemList itemList) {
        super.onPostExecute(itemList);
        if(itemList != null){
            activity.onTaskCompleted(itemList);
        }
    }
}
