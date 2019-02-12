package com.example.acer.bakeme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by acer on 6/1/18.
 */

public interface RecipeRetro {

    String BASE_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    @GET("baking.json")
    Call<List<RecipeModel>> gettingInfo();
}
