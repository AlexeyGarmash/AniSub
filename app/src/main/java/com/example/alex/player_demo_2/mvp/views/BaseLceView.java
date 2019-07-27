package com.example.alex.player_demo_2.mvp.views;

import com.arellomobile.mvp.MvpView;

public interface BaseLceView extends MvpView {
    void showError(String message);
    void hideError();
    void onStartLoading();
    void onFinishLoading();
    void showRefreshing();
    void hideRefreshing();
}
