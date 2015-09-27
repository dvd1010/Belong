package com.belonginterview.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Folder implements Serializable {

    private String uri;
    private String name;
    private ArrayList<Facet> facets;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Facet> getFacets() {
        return facets;
    }

    public void setFacets(ArrayList<Facet> facets) {
        this.facets = facets;
    }
}
