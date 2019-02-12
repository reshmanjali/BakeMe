package com.example.acer.bakeme;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public long playVal = 0L;
    public boolean state = true;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    SimpleExoPlayerView playerView;
    ImageView thumbnail_imgview;
    TextView shrt_desc;
    TextView desc;
    SimpleExoPlayer simpleExoPlayer;
    StepsModel obtainedStepsModel;

    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            playVal = savedInstanceState.getLong("curr_pos");
            state = savedInstanceState.getBoolean("ready");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        thumbnail_imgview = rootView.findViewById(R.id.id_imgview_item_detail);
        shrt_desc = rootView.findViewById(R.id.id_shrt_desc_tv_item_detail);
        desc = rootView.findViewById(R.id.id_desc_tv_item_detail);
        playerView = rootView.findViewById(R.id.id_exo_in_list_detail);
        obtainedStepsModel = getArguments().getParcelable("stepInfo");


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("curr_pos", playVal);
        outState.putBoolean("ready", state);
        if (simpleExoPlayer != null) {
            playVal = simpleExoPlayer.getCurrentPosition();
            outState.putLong("curr_pos", playVal);
            state = simpleExoPlayer.getPlayWhenReady();
            outState.putBoolean("ready", state);
        }
    }


    public void exoPlayerDemo(String urlPlay) {

        if (simpleExoPlayer == null) {

            try {

                BandwidthMeter widthMeter = new DefaultBandwidthMeter();
                TrackSelector selector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(widthMeter));
                simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), selector);
                playerView.setPlayer(simpleExoPlayer);
                Uri uri_for_video = Uri.parse(urlPlay);

                DefaultHttpDataSourceFactory src_factory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(uri_for_video, src_factory, extractorsFactory, null, null);
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.seekTo(playVal);
                simpleExoPlayer.setPlayWhenReady(state);
                playerView.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                Log.i("Exception in Exoplayer", "error error");
            }
        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if (simpleExoPlayer != null) {

            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (simpleExoPlayer != null) {

            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (obtainedStepsModel != null) {
            if (!obtainedStepsModel.getVideoURL().isEmpty()) {
                exoPlayerDemo(obtainedStepsModel.getVideoURL());
            } else {
                if (!obtainedStepsModel.getThumbnailURL().isEmpty() && !obtainedStepsModel.getThumbnailURL().endsWith(".mp4")) {
                    thumbnail_imgview.setVisibility(View.VISIBLE);
                    Picasso.with(getActivity()).load(obtainedStepsModel.getThumbnailURL()).into(thumbnail_imgview);
                }
            }

            shrt_desc.setText(obtainedStepsModel.getShortDescription());
            desc.setText(obtainedStepsModel.getDescription());

        }
    }
}
