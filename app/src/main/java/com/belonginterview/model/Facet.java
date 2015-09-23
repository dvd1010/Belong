package com.belonginterview.model;

import java.io.Serializable;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class Facet implements Serializable {

    private String label;
    private String tag;
    private int count;
    private boolean isSelected;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
