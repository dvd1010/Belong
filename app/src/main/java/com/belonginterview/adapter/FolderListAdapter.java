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
import com.belonginterview.model.Folder;
import com.belonginterview.model.Product;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperProfs on 24/09/15.
 */
public class FolderListAdapter extends ArrayAdapter {

    private ArrayList<Folder> folders;
    public FolderListAdapter(Context context, int resource, ArrayList<Folder> folders) {
        super(context, resource, folders);
        this.folders = folders;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Folder getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView folderNameView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = convertView;
        if (view == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_folder, null);
            vh.folderNameView = (TextView)view.findViewById(R.id.folder_name);
            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
        Folder folder = folders.get(position);
        if(folder != null){
            vh.folderNameView.setText(folder.getName().toUpperCase());
            if(!ProductListFragment.selectedFolder.isEmpty() && ProductListFragment.selectedFolder.equalsIgnoreCase(folder.getName())){
                vh.folderNameView.setTextColor(getContext().getResources().getColor(R.color.orange_dark));
            }else{
                vh.folderNameView.setTextColor(getContext().getResources().getColor(R.color.black));
            }

        }

        return view;
    }

}
