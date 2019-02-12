package com.example.acer.bakeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {


    StepsModel obtainedStepsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Log.e("ItemDetailActivity","onCreate");

        obtainedStepsModel = getIntent().getParcelableExtra("stepInfo");
        Log.i("ItemDetailAct: ", obtainedStepsModel.getShortDescription());
        Bundle b = new Bundle();
        b.putParcelable("stepInfo", obtainedStepsModel);
        if(savedInstanceState == null) {
            ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
            itemDetailFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, itemDetailFragment).commit();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
