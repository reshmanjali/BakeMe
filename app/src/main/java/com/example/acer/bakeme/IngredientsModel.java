package com.example.acer.bakeme;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 6/1/18.
 */

public class IngredientsModel implements Parcelable {

    public static final Creator<IngredientsModel> CREATOR = new Creator<IngredientsModel>() {
        @Override
        public IngredientsModel createFromParcel(Parcel in) {
            return new IngredientsModel(in);
        }

        @Override
        public IngredientsModel[] newArray(int size) {
            return new IngredientsModel[size];
        }
    };
    String quantity;
    String measure;
    String ingredient;

    IngredientsModel() {

    }

    protected IngredientsModel(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getQuantity());
        parcel.writeString(getMeasure());
        parcel.writeString(getIngredient());

    }
}
