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
import com.belonginterview.model.Constants;
import com.belonginterview.model.Product;
import com.belonginterview.utils.HttpAgent;
import com.belonginterview.utils.JsonHandler;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class ProductListFragment extends Fragment {

    private ListView productListView;
    private ProgressBar progressBar;
    private GetProductDetails getProductDetails;
    private static final String ONE = "1";

    private static final String GET_PRODUCTS_URL = "http://api.buyingiq.com/v1/search/?page={0}&{1}&facet=1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        productListView = (ListView)view.findViewById(R.id.product_list);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);

        String apiTag = getArguments().getString(Constants.API_TAG);
        getProductDetails = new GetProductDetails();
        getProductDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag);

        return view;
    }


    private class GetProductDetails extends AsyncTask<String, Void, Product> {

        @Override
        protected Product doInBackground(String... params) {
            String apiTag = params[0];
            String url = MessageFormat.format(GET_PRODUCTS_URL, ONE, "tags="+apiTag);
            String response = HttpAgent.get(url);
            Product product = null;
            if(response != null){
                product = JsonHandler.parseUnderScoredResponse(response, Product.class);
            }
            return product;
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);
            if(isAdded() && product!= null){

            }
        }
    }
}
