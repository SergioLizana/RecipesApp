package riviasoftware.recipesapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;

public class RecipeDetailFragment extends Fragment implements ExoPlayer.EventListener{


    private Unbinder unbinder;
    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_detail)
    TextView stepDetail;
    @BindView(R.id.marker_progress)
    ProgressBar loader;


    private Recipe recipe;
    private Step step;



    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance() {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments().containsKey("recipe") || getArguments().containsKey("step")) {
            recipe =getArguments().getParcelable("recipe");
            step = getArguments().getParcelable("step");
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        printView(step);


        return rootView;
    }

    public void printView(Step step){
        this.step = step;
        mPlayerView.setUseArtwork(false);
        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(step.getVideoURL()));
        }else{
            mPlayerView.setVisibility(View.GONE);
            stepDetail.setVisibility(View.VISIBLE);
        }

        stepDetail.setText(step.getDescription());


    }

    private void initializePlayer(Uri mediaUri) {
            // Create an instance of the ExoPlayer.
            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getActivity().getApplicationContext(), "recipesapp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getActivity().getApplicationContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.addListener(this);
                mExoPlayer.setPlayWhenReady(false);
            }

    }

    public void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (mExoPlayer != null) {
            releasePlayer();
        }
        super.onDestroyView();

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY) {
            loader.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            stepDetail.setVisibility(View.VISIBLE);
        }else{
            loader.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.INVISIBLE);
            stepDetail.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
