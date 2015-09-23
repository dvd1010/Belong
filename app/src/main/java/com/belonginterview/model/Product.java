package com.belonginterview.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class Product implements Serializable {

    private String id;
    private String name;
    private String brand;
    private String category;
    private int categoryId;
    private String avgRating;
    private int ratingCount;
    private int reviewCount;
    private String stockInfo;
    private int dealCount;
    private int storeCount;
    private String minPrice;
    private String minPriceStr;
    private ArrayList<String> keyFeatures;
    private String url;
    private Image imageO;
    private int biqScore;
    private int curatedReviewCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(String stockInfo) {
        this.stockInfo = stockInfo;
    }

    public int getDealCount() {
        return dealCount;
    }

    public void setDealCount(int dealCount) {
        this.dealCount = dealCount;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinPriceStr() {
        return minPriceStr;
    }

    public void setMinPriceStr(String minPriceStr) {
        this.minPriceStr = minPriceStr;
    }

    public ArrayList<String> getKeyFeatures() {
        return keyFeatures;
    }

    public void setKeyFeatures(ArrayList<String> keyFeatures) {
        this.keyFeatures = keyFeatures;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImageO() {
        return imageO;
    }

    public void setImageO(Image imageO) {
        this.imageO = imageO;
    }

    public int getBiqScore() {
        return biqScore;
    }

    public void setBiqScore(int biqScore) {
        this.biqScore = biqScore;
    }

    public int getCuratedReviewCount() {
        return curatedReviewCount;
    }

    public void setCuratedReviewCount(int curatedReviewCount) {
        this.curatedReviewCount = curatedReviewCount;
    }
}
