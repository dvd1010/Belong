package com.belonginterview.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.activity.SearchActivity;
import com.belonginterview.fragment.ProductListFragment;
import com.belonginterview.model.Product;
import com.belonginterview.utils.GetProductDetailsTask;
import com.belonginterview.utils.GetSearchResultsTask;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by SuperProfs on 29/09/15.
 */
public class SearchResultListAdapter extends ArrayAdapter {

    private ArrayList<Product> products;
    private SearchActivity activity;


    public SearchResultListAdapter(Context context, int resource, ArrayList<Product> products, SearchActivity activity) {
        super(context, resource, products);
        this.products = products;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView productImageView;
        TextView productNameView;
        TextView productCategoryView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = convertView;
        if (view == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_search_result, null);
            vh.productImageView = (ImageView)view.findViewById(R.id.product_image);
            vh.productNameView = (TextView)view.findViewById(R.id.product_name);
            vh.productCategoryView = (TextView)view.findViewById(R.id.product_category);
            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
        Product product = products.get(position);
        if(product != null){
            if(product.getImagesO() != null) {
                UrlImageViewHelper.setUrlDrawable(vh.productImageView, product.getImagesO().getL());
            }
            vh.productNameView.setText(product.getName());
            vh.productCategoryView.setText("in "+product.getCategory());
        }

        if(position == getCount() - 1) {
            new GetSearchResultsTask(activity)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SearchActivity.searchString);
        }

        return view;
    }

}
