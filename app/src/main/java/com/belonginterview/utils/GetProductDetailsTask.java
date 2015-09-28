package com.belonginterview.utils;

import android.os.AsyncTask;

import com.belonginterview.fragment.ProductListFragment;
import com.belonginterview.model.ItemList;

import java.text.MessageFormat;

/*Gets data from the background thread via API call*/
public class GetProductDetailsTask extends AsyncTask<String, Void, ItemList> {

    private ProductListFragment fragment;

    private static final String GET_PRODUCTS_URL = "http://api.buyingiq.com/v1/search/?";

    public GetProductDetailsTask(){

    }
    public GetProductDetailsTask(ProductListFragment fragment){
        this.fragment = fragment;
    }


    @Override
    protected ItemList doInBackground(String... params) {
        String apiTag = params[0];
        String queryParam = params[1];
        String tempUrl = GET_PRODUCTS_URL + queryParam;
        String url = MessageFormat.format(tempUrl, "tags=" + apiTag);
        String response = HttpAgent.get(url);
        ItemList itemList = null;
        if (response != null) {
            itemList = JsonHandler.parseUnderScoredResponse(response, ItemList.class);
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(ItemList itemList) {
        super.onPostExecute(itemList);
        if (itemList != null) {
            fragment.onTaskCompleted(itemList);
        }
    }
}
