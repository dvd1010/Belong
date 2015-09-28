package com.belonginterview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.fragment.ProductListFragment;
import com.belonginterview.model.Facet;

import java.util.ArrayList;

/*Vertical drop down list adapter, which shows and hides on click on horizontal folder list item*/
public class FacetListAdapter extends ArrayAdapter {

    private ArrayList<Facet> facets;

    public FacetListAdapter(Context context, int resource, ArrayList<Facet> facets) {
        super(context, resource, facets);
        this.facets = facets;
    }

    @Override
    public int getCount() {
        return facets.size();
    }

    @Override
    public Facet getItem(int position) {
        return facets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView facetNameView;
        TextView facetCountView;
        ImageView selectedCheckView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = convertView;
        if (view == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_facet_item, null);
            vh.facetNameView = (TextView)view.findViewById(R.id.facet_name);
            vh.facetCountView = (TextView)view.findViewById(R.id.facet_count);
            vh.selectedCheckView = (ImageView)view.findViewById(R.id.check);
            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
        Facet facet = facets.get(position);
        if(facet != null && facet.getCount()>0){
            vh.facetNameView.setText(facet.getLabel());
            vh.facetCountView.setText("("+facet.getCount()+")");
            if(facet.isSelected()){
                vh.selectedCheckView.setVisibility(View.VISIBLE);
            }else{
                vh.selectedCheckView.setVisibility(View.GONE);
            }
        }

        return view;
    }
}
