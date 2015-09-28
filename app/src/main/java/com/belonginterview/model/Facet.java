package com.belonginterview.model;

import java.io.Serializable;

/*The item on the drop down list*/
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Facet facet = (Facet) o;

        if (count != facet.count)
            return false;
        if (isSelected != facet.isSelected)
            return false;
        if (label != null ? !label.equals(facet.label) : facet.label != null)
            return false;
        return !(tag != null ? !tag.equals(facet.tag) : facet.tag != null);

    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
