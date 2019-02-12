package com.example.acer.bakeme;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.acer.bakeme.RecipeRetro.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.id_rv_main)
    RecyclerView rec_view_main;

    List<RecipeModel> infoObtained;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final RecipeRetro recipieRetro = retrofit.create(RecipeRetro.class);
        Call<List<RecipeModel>> ob = recipieRetro.gettingInfo();
        ob.enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                infoObtained = response.body();
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    rec_view_main.setLayoutManager(new GridLayoutManager(getApplicationContext(), noOfColumns()));
                    rec_view_main.setAdapter(new MyAdapter(infoObtained, MainActivity.this));
                } else {
                    rec_view_main.setLayoutManager(new GridLayoutManager(MainActivity.this, noOfColumns()));
                    rec_view_main.setAdapter(new MyAdapter(infoObtained, MainActivity.this));
                }

            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                Log.i("retro failure", "onFailure:  " + t.getMessage());
            }
        });
    }

    private int noOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDriver = 400;
        int width = displayMetrics.widthPixels;
        int ncolumns = width / widthDriver;

        if (ncolumns < 2) {
            return 2;
        }
        return ncolumns;
    }

    private Activity getActivity() {

        Context context = this;

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public List<RecipeModel> getInfoObtained() {
        onCreate(new Bundle());
        return infoObtained;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<RecipeModel> recipeModelList;
    Context c;

    public MyAdapter(List<RecipeModel> recipeModelList, MainActivity mainActivity) {
        this.recipeModelList = recipeModelList;
        c = mainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipetitles, null, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.titles_tv.setText(recipeModelList.get(position).getName());
        if(!recipeModelList.get(position).getImage().isEmpty())
            Picasso.with(c).load(Uri.parse(recipeModelList.get(position).getImage())).into(holder.recipeImage);
        else
            Picasso.with(c).load(R.mipmap.pic).into(holder.recipeImage);
    }

    @Override

    public int getItemCount() {
        return recipeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titles_tv;
        ImageView recipeImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.id_img_view_recipeTitles);

            titles_tv = itemView.findViewById(R.id.id_tv_recipetitles);
            titles_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), ItemListActivity.class);
                    intent.putExtra("recipeRetroData", recipeModelList.get(getLayoutPosition()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
