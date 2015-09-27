package com.belonginterview.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.fragment.ProductListFragment;
import com.belonginterview.model.Product;
import com.belonginterview.utils.GetProductDetailsTask;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter {


    private ArrayList<Product> products;
    private Context context;
    private ProductListFragment fragment;

    private static final BigDecimal EIGHT = new BigDecimal("8");
    private static final BigDecimal SEVEN = new BigDecimal("7");
    private static final BigDecimal SIX = new BigDecimal("6");

    public ProductListAdapter(Context context, int resource, ArrayList<Product> products, ProductListFragment fragment) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.fragment = fragment;
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

            BigDecimal ratingDecimal = new BigDecimal(product.getAvgRating());
            ratingDecimal = ratingDecimal.setScale(1, RoundingMode.CEILING);
            setRatingBackground(vh.productRatingView, ratingDecimal);

            vh.productRatingView.setText(ratingDecimal.toString());
        }

        if(position == getCount() - 1) {
            new GetProductDetailsTask(fragment).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ProductListFragment.apiTag,
                    ProductListFragment.nextUrl+"&facet=1");
        }

        return view;
    }

    private void setRatingBackground(TextView view, BigDecimal ratingDecimal){
        ratingDecimal = ratingDecimal.setScale(1, RoundingMode.CEILING);
        if(ratingDecimal.compareTo(EIGHT)>=0){
            view.setBackgroundResource(R.drawable.button_green);
        }else if(ratingDecimal.compareTo(EIGHT)<0 && ratingDecimal.compareTo(SEVEN)>=0){
            view.setBackgroundResource(R.drawable.button_light_green);
        }else if(ratingDecimal.compareTo(SEVEN)<0 && ratingDecimal.compareTo(SIX)>=0){
            view.setBackgroundResource(R.drawable.button_yellow);
        }else {
            view.setBackgroundResource(R.drawable.button_orange);
        }

    }
}
