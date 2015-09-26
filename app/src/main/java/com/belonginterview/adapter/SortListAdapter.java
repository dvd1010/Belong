package com.belonginterview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.model.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperProfs on 26/09/15.
 */
public class SortListAdapter extends ArrayAdapter {


    private ArrayList<String> sortCriterias;
    public SortListAdapter(Context context, int resource, ArrayList<String> sortCriterias) {
        super(context, resource, sortCriterias);
        this.sortCriterias = sortCriterias;
    }

    @Override
    public int getCount() {
        return sortCriterias.size();
    }

    @Override
    public String getItem(int position) {
        return sortCriterias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView criteriaNameView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = convertView;
        if (view == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_sort, null);
            vh.criteriaNameView = (TextView)view.findViewById(R.id.criteria_name);
            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
        String criteria = sortCriterias.get(position);
        if(criteria != null){
            vh.criteriaNameView.setText(criteria);
        }

        return view;
    }

}
