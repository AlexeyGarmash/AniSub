package com.example.alex.player_demo_2.mvp.presenters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.example.alex.player_demo_2.mvp.views.VideoPlayerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;

@InjectViewState
public class PlayerPresenter extends BasePresenter<VideoPlayerView>  implements Player.EventListener{

    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String videoLinkIntent;
    private Context context;




    public PlayerPresenter(String uri, Context context) {
        this.videoLinkIntent = uri;
        this.context = context;
    }

    public PlayerPresenter(){}

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void setPlayer(){
        SimpleExoPlayer pl = initializePlayer();
        getViewState().setPlayer(pl);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        setPlayer();
    }


    private SimpleExoPlayer initializePlayer() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DefaultAllocator allocator = new DefaultAllocator(true, C.DEFAULT_VIDEO_BUFFER_SIZE);
        // Here
        DefaultLoadControl loadControl = new DefaultLoadControl(allocator, 360000, 600000, 2500, 5000, -1, true);
        player = ExoPlayerFactory.newSimpleInstance(context,
                new DefaultRenderersFactory(context),
                trackSelector,
                loadControl);
        //playerView.setPlayer(player);
        playWhenReady = player.getPlayWhenReady();
        currentWindow = player.getCurrentWindowIndex();
        playbackPosition = player.getCurrentPosition();
        player.seekTo(currentWindow, playbackPosition);
        player.addListener(this);





        MediaSource mediaSource = buildMediaSource(Uri.parse(videoLinkIntent), context);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        return player;
    }

    private MediaSource buildMediaSource(Uri uri, Context context) {
        //Toast.makeText(this, "BuildPlayer:  " + uri.toString(), Toast.LENGTH_LONG).show();
        String userAgent = "SubAnime";
        /*if(uri.getLastPathSegment().contains("m3u8")){
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        }
        else{*/
            /*DefaultDashChunkSource.Factory dashChunkSourceFactoty = new DefaultDashChunkSource.Factory(
                    new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
            DefaultHttpDataSourceFactory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
            return new DashMediaSource.Factory(dashChunkSourceFactoty, manifestDataSourceFactory).createMediaSource(uri);*/
        //}
        // Create a data source factory.
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "SubAnime"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        return mediaSource;
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                //status = PlaybackStatus.LOADING;
                makeSnackShow("Buffering");
                showSpinner(true);
                //spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                makeSnackShow("Ended");
                //status = PlaybackStatus.STOPPED;
                break;
            case Player.STATE_IDLE:
                makeSnackShow("Idle " + "playWR: "+playWhenReady+" pBs: " + playbackState);
                //status = PlaybackStatus.IDLE;
                break;
            case Player.STATE_READY:
                makeSnackShow("Ready");
                showSpinner(false);
                //spinnerVideoDetails.setVisibility(View.GONE);
                //status = playWhenReady ? PlaybackStatus.PLAYING : PlaybackStatus.PAUSED;
                break;
            default:
                makeSnackShow("Default Idle");
                //status = PlaybackStatus.IDLE;
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        String errorS = error.getCause().getMessage();
        makeSnackShow(errorS);
    }


    private void makeSnackShow(String message){
        getViewState().showPlayerState(message);
    }

    private void showSpinner(boolean state){
        if(state){
            getViewState().showRefreshing();
        }
        else {
            getViewState().hideRefreshing();
        }
    }


}
