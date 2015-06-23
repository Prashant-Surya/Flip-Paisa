package com.prashant.android.flipv2;

import android.graphics.Bitmap;

/**
 * Created by Prashant on 26-05-2015.
 * Modified on 16-06-2015.
 */
public class rowHolder {
    String store;
    Bitmap bitmap;
    String largeBit;
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLargeBit() {
        return largeBit;
    }

    public void setLargeBit(String largeBit) {
        this.largeBit = largeBit;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getStore() {
        return store;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
