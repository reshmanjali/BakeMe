package com.example.acer.bakeme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    List<IngredientsModel> ingredientsListReceived;
    ScrollView sv;
    int positionIndex;
    int topView;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    View startView;
    int mScrollPosition;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        } else
            mTwoPane = false;
        RecipeModel recipeModel = getIntent().getParcelableExtra("recipeRetroData");

        TextView ingred_tv = findViewById(R.id.id_ingred_data_tv);
        setTitle("Recipe Details");

        ingredientsListReceived = recipeModel.getIngredients();
        String ing_data = recipeModel.getName() + "\n\n";

        for (int i = 0; i < ingredientsListReceived.size(); i++) {
            String q = ingredientsListReceived.get(i).getQuantity();
            String m = ingredientsListReceived.get(i).getMeasure();
            String ing = ingredientsListReceived.get(i).getIngredient();
            ing_data += "\uD83E\uDD44" + q + " " + m + "  " + ing;
            ing_data += "\n";
        }
        BakingService.myServiceMethod(this, ing_data);
        ingred_tv.setText(ing_data);


        recyclerView = findViewById(R.id.item_list);
        recyclerView.setFocusable(false);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

   /* @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        SavedState newState = new SavedState(superState);
        newState.mScrollPosition = mScrollPosition;
        return newState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if(state != null && state instanceof SavedState){
            mScrollPosition = ((SavedState) state).mScrollPosition;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager != null){
                int count = layoutManager.getChildCount();
                if(mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count){
                    layoutManager.scrollToPosition(mScrollPosition);
                }
            }
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public int mScrollPosition;
        SavedState(Parcel in) {
            super(in);
            mScrollPosition = in.readInt();
        }
        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mScrollPosition);
        }
        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }*/
/*
    @Override
    protected void onPause() {
        super.onPause();
        *//*positionIndex= llManager.findFirstVisibleItemPosition();
        View startView =recyclerView.getChildAt(0);
        topView = (startView == null) ? 0 : (startView.getTop() - rv.getPaddingTop());*//*
        positionIndex=recyclerView.getLayoutManager()

    }

    @Override
    protected void onResume() {
        super.onResume();

    }*/

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        Intent intent = getIntent();
        RecipeModel infoOfRecipeReceived = intent.getParcelableExtra("recipeRetroData");
        List<StepsModel> stepsModelListReceived = infoOfRecipeReceived.getSteps();
        llm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepsModelListReceived, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<StepsModel> mValues;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<StepsModel> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getShortDescription());

            holder.itemView.setTag(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            //arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                            arguments.putParcelable("stepInfo", mValues.get(getLayoutPosition()));

                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);

                            intent.putExtra("stepInfo", mValues.get(getLayoutPosition()));
                            context.startActivity(intent);
                        }
                    }
                });
            }
        }

    }







}
