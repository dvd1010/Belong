package com.belonginterview.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belonginterview.R;
import com.belonginterview.adapter.FacetListAdapter;
import com.belonginterview.adapter.FolderListAdapter;
import com.belonginterview.adapter.ProductListAdapter;
import com.belonginterview.adapter.SortListAdapter;
import com.belonginterview.enums.AnimationEnum;
import com.belonginterview.model.Constants;
import com.belonginterview.model.Facet;
import com.belonginterview.model.Folder;
import com.belonginterview.model.ItemList;
import com.belonginterview.utils.DropdownListAnimation;
import com.belonginterview.utils.GetProductDetailsTask;
import com.belonginterview.utils.SortUtils;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*The fragment defines the listing of products, folders and facets.*/
public class ProductListFragment extends Fragment {

    private ListView productListView;
    private TwoWayView folderListView;
    private ProgressBar progressBar;
    private ImageView sortImageView;
    private GetProductDetailsTask getProductDetails;
    private ProductListAdapter productListAdapter;
    private FolderListAdapter folderListAdapter;
    private FacetListAdapter facetListAdapter;
    private SortListAdapter sortListAdapter;
    private RelativeLayout containerLayout;
    private AlertDialog.Builder alertDialog;
    private ArrayList<Folder> folders;
    private ListView facetListView;
    private TextView noProductsView;
    public static String apiTag;
    public static String nextUrl;
    private TextView applyView;
    private TextView clearView;
    private int checkCount;
    private boolean filterApplied;
    private TextView openFolderView;

    public static String openFolderName;
    public static boolean isDropDownOpen;
    private static final String queryParamFirstPage = "page=1&{0}&facet=1";
    public static Map<String, ArrayList<Facet>> facetCheckMap;
    public static ArrayList<String> tagList;
    private DropdownListAnimation animation;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        loadViews(view);

        apiTag = getArguments().getString(Constants.API_TAG);
        getProductDetails = new GetProductDetailsTask(ProductListFragment.this);
        getProductDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag, queryParamFirstPage);

        handleSortIconClick();
        handleFolderListClick();
        handleApplyFilterClick();
        handleClearFilterClick();
        isDropDownOpen = false;
        tagList = new ArrayList<>();
        facetCheckMap = new HashMap<>();
        checkCount = 0;
        openFolderName = "";
        openFolderView = null;
        progressBar.setVisibility(View.VISIBLE);
        filterApplied = false;
        return view;
    }

    @Override
    public void onStop() {
        if (getProductDetails != null) {
            getProductDetails.cancel(true);
        }
        super.onStop();
    }

    /*Function handles clicks for the horizontal folder list and generates facets accoordingly*/
    private void handleFolderListClick() {
        folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.folder_name);
                if (containerLayout.getVisibility() == View.GONE ||
                        (openFolderView != null && !openFolderView.getText().equals(tv.getText()) && containerLayout.getVisibility() == View.VISIBLE)) {
                    openDropDownView(tv);
                } else if (openFolderView.equals(tv) && containerLayout.getVisibility() == View.VISIBLE) {
                    closeDropDownView();
                }

            }
        });
    }

    private void openDropDownView(TextView tv) {
        if (containerLayout.getVisibility() == View.GONE) {
            animation = new DropdownListAnimation(containerLayout, 500, AnimationEnum.EXPAND);
            containerLayout.startAnimation(animation);
            isDropDownOpen = true;
        }
        openFolderName = "" + tv.getText();
        if (openFolderView != null && !facetCheckMap.keySet().contains(openFolderView.getText())) {
            openFolderView.setTextColor(ContextCompat.getColor(getActivity().getBaseContext(), R.color.black));
        }
        tv.setTextColor(ContextCompat.getColor(getActivity().getBaseContext(), R.color.orange_dark));
        openFolderView = tv;
        fillFacetListView(openFolderName);
    }

    private void fillFacetListView(String folderName) {
        ArrayList<Facet> nonEmptyFacets;
        for (Folder folder : folders) {   //removing facets with with count = 0
            if (folderName.equalsIgnoreCase(folder.getName())) {
                nonEmptyFacets = new ArrayList<>();
                for (Facet facet : folder.getFacets()) {
                    if (facet.getCount() > 0) {
                        nonEmptyFacets.add(facet);
                    }
                }
                facetListAdapter = new FacetListAdapter(getActivity(), R.layout.list_facet_item, nonEmptyFacets);
                facetListView.setAdapter(facetListAdapter);
                facetListAdapter.notifyDataSetChanged();
                handleFacetListClick(folderName);
                break;
            }
        }
    }


    /*Handles the click on items of dropdown list visible by clicking on folder*/
    private void handleFacetListClick(final String folderName) {
        facetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView checkedView = (ImageView) view.findViewById(R.id.check);
                Facet facet = (Facet) adapterView.getAdapter().getItem(i);
                if (checkedView.getVisibility() == View.GONE) {
                    checkedView.setVisibility(View.VISIBLE);
                    ArrayList<Facet> checkedFacetList;
                    if (facetCheckMap.containsKey(folderName)) {
                        checkedFacetList = facetCheckMap.get(folderName);
                        checkedFacetList.add(facet);
                    } else {
                        checkedFacetList = new ArrayList<>();
                        checkedFacetList.add(facet);
                    }
                    tagList.add(facet.getTag());
                    facetCheckMap.put(folderName, checkedFacetList);
                    checkCount++;
                } else {
                    checkedView.setVisibility(View.GONE);
                    facetCheckMap.get(folderName).remove(facet);
                    tagList.remove(facet.getTag());
                    checkCount--;
                }
                if (checkCount > 0) {
                    clearView.setText("CLEAR ALL (" + checkCount + ")");
                } else {
                    clearView.setText("CLEAR ALL");
                }
            }
        });
    }


    /*function handles click on the sort icon along the bar of horizontal product list*/
    private void handleSortIconClick() {
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
                        Toast.makeText(getActivity(), "Sort Functionality not implemented", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.setView(sortDialogView);
                alertDialog.setCustomTitle(sortDialogHeaderView);
                alertDialog.setCancelable(true);
                alertDialog.show();
            }
        });
    }

    /*function handles the filter application based on selected facets criteria.*/
    private void handleApplyFilterClick() {
        applyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noProductsView.setVisibility(View.GONE);
                for (int i = 0; i < folderListView.getAdapter().getCount(); i++) {
                    TextView folderView = (TextView) folderListView.getAdapter().getView(i, null, folderListView);
                    String folderName = "" + folderView.getText();
                    if (facetCheckMap.keySet().contains(folderName.toUpperCase())) {
                        folderView.setTextColor(ContextCompat.getColor(getActivity().getBaseContext(), R.color.orange_dark));
                    }
                }
                openFolderView = null;
                openFolderName = "";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("page=1&");
                stringBuilder.append("tags=" + apiTag + "&");
                for (String tag : tagList) {
                    stringBuilder.append("tags=" + tag + "&");
                }
                String queryParams = stringBuilder.toString() + "facet=1";
                new GetProductDetailsTask(ProductListFragment.this).
                        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag, queryParams);
                filterApplied = true;
                containerLayout.setVisibility(View.GONE);
                isDropDownOpen = false;
            }
        });
    }

    /*clears all the filters which are already applied.*/
    private void handleClearFilterClick() {
        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                containerLayout.setVisibility(View.GONE);
                isDropDownOpen = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to clear all the filters?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void loadViews(View view) {
        productListView = (ListView) view.findViewById(R.id.product_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        folderListView = (TwoWayView) view.findViewById(R.id.folder_list);
        sortImageView = (ImageView) view.findViewById(R.id.sort_icon);
        alertDialog = new AlertDialog.Builder(getActivity());
        containerLayout = (RelativeLayout) view.findViewById(R.id.facet_dropdown);
        facetListView = (ListView) view.findViewById(R.id.facet_list);
        applyView = (TextView) view.findViewById(R.id.apply_filter);
        clearView = (TextView) view.findViewById(R.id.clear_filter);
        noProductsView = (TextView) view.findViewById(R.id.no_products);
    }

    /*Displays data received from the APIs*/
    public void onTaskCompleted(ItemList itemList) {
        progressBar.setVisibility(View.GONE);
        if (itemList.getProducts().size() > 0) {
            noProductsView.setVisibility(View.GONE);
            productListView.setVisibility(View.VISIBLE);
            nextUrl = itemList.getNext();
            if (productListAdapter == null || filterApplied) {
                productListAdapter = new ProductListAdapter(getActivity(), R.layout.list_item_product, itemList.getProducts(), ProductListFragment.this);
                productListView.setAdapter(productListAdapter);
            } else {
                productListAdapter.addAll(itemList.getProducts());
            }
            productListAdapter.notifyDataSetChanged();

        /*First Folder by default is Categories, as per mockup it is not being shown*/
            ArrayList<Folder> listWithoutCategoryField = new ArrayList<>();
            for (Folder folder : itemList.getFolders()) {
                if (!folder.getName().equals("Categories")) {
                    listWithoutCategoryField.add(folder);
                }
            }
            folders = listWithoutCategoryField;
            folderListAdapter = new FolderListAdapter(getActivity(), R.layout.list_item_folder, listWithoutCategoryField);
            folderListView.setAdapter(folderListAdapter);
            folderListAdapter.notifyDataSetChanged();
        } else {
            noProductsView.setVisibility(View.VISIBLE);
            productListView.setVisibility(View.GONE);
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    noProductsView.setVisibility(View.GONE);
                    for (int i = 0; i < folderListView.getAdapter().getCount(); i++) {
                        TextView folderView = (TextView) folderListView.getAdapter().getView(i, null, folderListView);
                        String folderName = "" + folderView.getText();
                        if (facetCheckMap.keySet().contains(folderName.toUpperCase())) {
                            folderView.setTextColor(ContextCompat.getColor(getActivity().getBaseContext(), R.color.black));
                        }
                    }
                    clearView.setText("CLEAR ALL");
                    checkCount = 0;
                    facetCheckMap = new HashMap<>();
                    openFolderView = null;
                    openFolderName = "";
                    new GetProductDetailsTask(ProductListFragment.this).
                            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiTag, queryParamFirstPage);

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void closeDropDownView(){
        animation = new DropdownListAnimation(containerLayout, 500, AnimationEnum.COLLAPSE);
        containerLayout.startAnimation(animation);
        isDropDownOpen = false;
        openFolderView.setTextColor(ContextCompat.getColor(getActivity().getBaseContext(), R.color.black));
        openFolderView = null;
        openFolderName = "";
    }


}
