package com.example.acer.bakeme;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by acer on 6/1/18.
 */

public class RecipeModel implements Parcelable{

    String id;
    String name;
    List<IngredientsModel> ingredients;
    List<StepsModel> steps;
    String servings;
    String image;

    protected RecipeModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredients = in.createTypedArrayList(IngredientsModel.CREATOR);
        steps = in.createTypedArrayList(StepsModel.CREATOR);
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

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

    public List<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsModel> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepsModel> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsModel> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(getName());
        parcel.writeTypedList(getIngredients());
        parcel.writeTypedList(getSteps());
        parcel.writeString(getServings());
        parcel.writeString(getImage());
    }
}
