package com.belonginterview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.model.Product;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by SuperProfs on 23/09/15.
 */
public class ProductListAdapter extends ArrayAdapter {


    private ArrayList<Product> products;
    private Context context;
    public ProductListAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
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
        TextView productPriceView;
        TextView storeCount;
        TextView productRatingView;
        TextView voteCount;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = convertView;
        if (view == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_product, null);
            vh.productImageView = (ImageView)view.findViewById(R.id.product_image);
            vh.productNameView = (TextView)view.findViewById(R.id.product_name);
            vh.productPriceView = (TextView)view.findViewById(R.id.product_price);
            vh.storeCount = (TextView)view.findViewById(R.id.store_count);
            vh.productRatingView = (TextView)view.findViewById(R.id.product_rating);
            vh.voteCount = (TextView)view.findViewById(R.id.votes_count);
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
            vh.productPriceView.setText(product.getMinPriceStr());
            vh.storeCount.setText(String.valueOf(product.getStoreCount())+ " stores");
            vh.voteCount.setText(String.valueOf(product.getRatingCount()) + " votes");
            vh.productRatingView.setText(product.getAvgRating());
        }

        return view;
    }
}
