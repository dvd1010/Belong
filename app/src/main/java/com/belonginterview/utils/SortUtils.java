package com.belonginterview.utils;

import com.belonginterview.model.Constants;

import java.util.ArrayList;
import java.util.List;

/*Arranges sort criterias for product list*/
public class SortUtils {

    public static ArrayList<String> getSortCriteria(){
        ArrayList<String> sortCretirias = new ArrayList<>();
        sortCretirias.add(Constants.SORT_BY_POPULARITY);
        sortCretirias.add(Constants.SORT_RATING_HIGH_TO_LOW);
        sortCretirias.add(Constants.SORT_PRICE_HIGH_TO_LOW);
        sortCretirias.add(Constants.SORT_PRICE_LOW_TO_HIGH);
        return sortCretirias;
    }
}
