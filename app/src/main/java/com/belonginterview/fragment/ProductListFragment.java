package com.belonginterview.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.belonginterview.R;
import com.belonginterview.adapter.FolderListAdapter;
import com.belonginterview.adapter.ProductListAdapter;
import com.belonginterview.model.Constants;
import com.belonginterview.model.ItemList;
import com.belonginterview.model.Product;
import com.belonginterview.utils.HttpAgent;
import com.belonginterview.utils.JsonHandler;

import org.lucasr.twowayview.TwoWayView;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class ProductListFragment extends Fragment {

    private ListView productListView;
    private TwoWayView folderListView;
    private ProgressBar progressBar;
    private GetProductDetails getProductDetails;
    private ProductListAdapter productListAdapter;
    private FolderListAdapter folderListAdapter;
    private static final String ONE = "1";


    private static final String GET_PRODUCTS_URL = "http://api.buyingiq.com/v1/search/?page={0}&{1}&facet=1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        loadViews(view);

        String apiTag = getArguments().getString(Constants.API_TAG);
        getProductDetails = new GetProductDetails();
        getProductDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag);

        return view;
    }

    private void loadViews(View view) {
        productListView = (ListView)view.findViewById(R.id.product_list);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        folderListView = (TwoWayView)view.findViewById(R.id.folder_list);
    }


    private class GetProductDetails extends AsyncTask<String, Void, ItemList> {

        @Override
        protected ItemList doInBackground(String... params) {
            String apiTag = params[0];
            String url = MessageFormat.format(GET_PRODUCTS_URL, ONE, "tags="+apiTag);
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
            if(isAdded() && itemList!= null){
                productListAdapter = new ProductListAdapter(getActivity(), R.layout.list_item_product, itemList.getProducts());
                productListView.setAdapter(productListAdapter);
                productListAdapter.notifyDataSetChanged();

                folderListAdapter = new FolderListAdapter(getActivity(), R.layout.list_item_folder, itemList.getFolders());
                folderListView.setAdapter(folderListAdapter);
                folderListAdapter.notifyDataSetChanged();
            }
        }
    }
}
