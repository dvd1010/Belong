package com.belonginterview.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.belonginterview.R;
import com.belonginterview.adapter.FolderListAdapter;
import com.belonginterview.adapter.ProductListAdapter;
import com.belonginterview.adapter.SortListAdapter;
import com.belonginterview.model.Constants;
import com.belonginterview.model.Folder;
import com.belonginterview.model.ItemList;
import com.belonginterview.model.Product;
import com.belonginterview.utils.GetProductDetailsTask;
import com.belonginterview.utils.SortUtils;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class ProductListFragment extends Fragment {

    private ListView productListView;
    private TwoWayView folderListView;
    private ProgressBar progressBar;
    private ImageView sortImageView;
    private GetProductDetailsTask getProductDetails;
    private ProductListAdapter productListAdapter;
    private FolderListAdapter folderListAdapter;
    private SortListAdapter sortListAdapter;
    private AlertDialog.Builder alertDialog;

    public static String apiTag;
    public static String nextUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        loadViews(view);

        apiTag = getArguments().getString(Constants.API_TAG);
        String queryParamFirstPage = "page=1&{0}&facet=1";
        getProductDetails = new GetProductDetailsTask(ProductListFragment.this);
        getProductDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag, queryParamFirstPage);

        sortImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View sortDialogHeaderView = inflater.inflate(R.layout.sort_dialog_header, null);
                View sortDialogView = inflater.inflate(R.layout.sort_dialog, null);
                ListView sortListCriteriaView = (ListView) sortDialogView.findViewById(R.id.sort_criteria_list);
                sortListAdapter = new SortListAdapter(getActivity(), R.layout.list_item_sort, SortUtils.getSortCriteria());
                sortListCriteriaView.setAdapter(sortListAdapter);
                sortListAdapter.notifyDataSetChanged();

                sortListCriteriaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity(), "Sort Functinality not implemented", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.setView(sortDialogView);
                alertDialog.setCustomTitle(sortDialogHeaderView);
                alertDialog.show();
            }
        });


        return view;
    }

    private void loadViews(View view) {
        productListView = (ListView) view.findViewById(R.id.product_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        folderListView = (TwoWayView) view.findViewById(R.id.folder_list);
        sortImageView = (ImageView) view.findViewById(R.id.sort_icon);
        alertDialog = new AlertDialog.Builder(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT);
    }

    public void onTaskCompleted(ItemList itemList) {
        nextUrl = itemList.getNext();
        //products.addAll(itemList.getProducts());
        if(productListAdapter == null) {
            productListAdapter = new ProductListAdapter(getActivity(), R.layout.list_item_product, itemList.getProducts(), ProductListFragment.this);
            productListView.setAdapter(productListAdapter);
        }else{
            productListAdapter.addAll(itemList.getProducts());
            productListAdapter.notifyDataSetChanged();
        }
        productListAdapter.notifyDataSetChanged();

        ArrayList<Folder> listWithoutCategoryField = new ArrayList<>();
        for (Folder folder : itemList.getFolders()) {
            if (!folder.getName().equals("Categories")) {
                listWithoutCategoryField.add(folder);
            }
        }

        folderListAdapter = new FolderListAdapter(getActivity(), R.layout.list_item_folder, listWithoutCategoryField);
        folderListView.setAdapter(folderListAdapter);
        folderListAdapter.notifyDataSetChanged();
    }

}
