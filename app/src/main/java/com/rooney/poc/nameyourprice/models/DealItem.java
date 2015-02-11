package com.rooney.poc.nameyourprice.models;

import android.graphics.Bitmap;

public class DealItem {
    public int index;
    public String _id;
    public String title;
    public String description;
    public String price;
    public String salePrice;
    public String image;
    public Bitmap imageBitmap;
    public String aisle;
    public String guid;






    public DealItem(int index, String id, String title, String description, String originalPrice, String salePrice, String image, String aisle) {
        this.index = index;
        this._id = id;
        this.title = title;
        this.description = description;
        this.price = originalPrice;
        this.salePrice = salePrice;
        this.image = image;
        this.aisle = aisle;
    }

    @Override
    public String toString() {
        return title;
    }
}