package com.example.alex.player_demo_2.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.alex.player_demo_2.R;
import com.example.alex.player_demo_2.mvp.presenters.PlayerPresenter;
import com.example.alex.player_demo_2.mvp.views.VideoPlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends MvpAppCompatActivity implements VideoPlayerView {

    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    @BindView(R.id.video_view)
    PlayerView playerView ;

    @BindView(R.id.spinnerVideoDetails)
    ProgressBar spinnerVideoDetails;


    @InjectPresenter
    PlayerPresenter mPlayerPresenter;

    @ProvidePresenter
    PlayerPresenter provideDetailsPresenter() {
        return new PlayerPresenter(getIntent().getStringExtra("LINK"), this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
    }


    @Override
    public void onPause() {
        /*super.onPause();
        if (videoView!=null) {
            videoView.pause();
        }*/
        super.onPause();
        mPlayerPresenter.releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlayerPresenter.releasePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPlayerPresenter.getPlayer() == null) {
            mPlayerPresenter.setPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (mPlayerPresenter.getPlayer() == null) {
            mPlayerPresenter.setPlayer();
        }
    }

    /*@SuppressLint("InlinedApi")
    private void hideSystemUiFullScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }*/

    private void makeSnackShow(String text){
        Snackbar.make(playerView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPlayer(SimpleExoPlayer exoPlayer) {
        playerView.setPlayer(exoPlayer);
    }

    @Override
    public void showRefreshing() {
        spinnerVideoDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshing() {
        spinnerVideoDetails.setVisibility(View.GONE);
    }

    @Override
    public void showPlayerState(String mnessage) {
        makeSnackShow(mnessage);
    }
}
