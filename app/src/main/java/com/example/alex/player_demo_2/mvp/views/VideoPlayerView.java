package com.example.alex.player_demo_2.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;


public interface VideoPlayerView extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void setPlayer(SimpleExoPlayer exoPlayer);

    void showRefreshing();
    void hideRefreshing();

    void showPlayerState(String mnessage);

}
