package com.belonginterview.model;

import java.io.Serializable;
import java.util.ArrayList;

/*The main data response parsed from API */
public class ItemList implements Serializable {

    private int took;
    private int total;
    private ArrayList<SelectedTag> selectedTags;
    private ArrayList<Facet> selectedFacets;
    private ArrayList<Folder> folders;
    private ArrayList<Product> products;
    private String prev;
    private String next;

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SelectedTag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(ArrayList<SelectedTag> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public ArrayList<Facet> getSelectedFacets() {
        return selectedFacets;
    }

    public void setSelectedFacets(ArrayList<Facet> selectedFacets) {
        this.selectedFacets = selectedFacets;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
