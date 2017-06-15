package riviasoftware.recipesapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    @Nullable @BindView(R.id.step_detail)
    TextView stepDetail;
    @BindView(R.id.marker_progress)
    ProgressBar loader;
    FloatingActionButton next;
    FloatingActionButton back;


    private Recipe recipe;
    private Step step;
    private long position = 0;



    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance() {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(false);
        if (getArguments().containsKey("recipe") || getArguments().containsKey("step")) {
            recipe =getArguments().getParcelable("recipe");
            step = getArguments().getParcelable("step");
        }



    }

    public interface CallbackStateReady {
        public void onStateReady();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CallbackStateReady)context;

    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    private CallbackStateReady callback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if(savedInstanceState != null && savedInstanceState.containsKey("bufferPosition")){
            position = savedInstanceState.getLong("bufferPosition");
        }

        if (!isTablet(getActivity().getApplicationContext())) {
            next = ((RecipeDetailActivity) getActivity()).next;
            back = ((RecipeDetailActivity) getActivity()).back;
        }

        printView(step);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && !isTablet(getActivity().getApplicationContext())) {
            hideSystemUI();
            mPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }


        return rootView;
    }

    public static boolean isTablet(Context context) {

        return context.getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("bufferPosition",mExoPlayer.getCurrentPosition());
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE || isTablet(getActivity().getApplicationContext())) {
            stepDetail.setText(step.getDescription());
        }
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && !isTablet(getActivity().getApplicationContext())){
            checkVisibilityButtons();
        }



    }

    public void checkVisibilityButtons(){

        if (step.getId() == 0){
            back.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }else if (step.getId() >= recipe.getSteps().size()-1) {
            next.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
        }else{
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }
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
                if (position == 0){
                    restExoPlayer(false);
                }else{
                    restExoPlayer(true);
                }



            }

    }

    private void restExoPlayer(boolean playWhenReady) {
        mExoPlayer.seekTo(position);
        mExoPlayer.setPlayWhenReady(playWhenReady);
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
            showUI();
            if (callback != null) {
                callback.onStateReady();
            }
        }else{
           hideUI();
        }

    }

    public void hideUI(){
        loader.setVisibility(View.VISIBLE);
        mPlayerView.setVisibility(View.INVISIBLE);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && !isTablet(getActivity().getApplicationContext())) {
            stepDetail.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            back.setVisibility(View.INVISIBLE);

        }
    }


    public void showUI(){
        loader.setVisibility(View.INVISIBLE);
        mPlayerView.setVisibility(View.VISIBLE);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE || isTablet(getActivity().getApplicationContext())) {
            stepDetail.setVisibility(View.VISIBLE);

        }

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && !isTablet(getActivity().getApplicationContext())) {
            checkVisibilityButtons();
        }


    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
