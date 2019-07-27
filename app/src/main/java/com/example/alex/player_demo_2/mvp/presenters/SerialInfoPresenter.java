package com.example.alex.player_demo_2.mvp.presenters;

import android.util.Log;


import com.arellomobile.mvp.InjectViewState;
import com.example.alex.player_demo_2.models.SerialChapter;
import com.example.alex.player_demo_2.models.SerialInfo;
import com.example.alex.player_demo_2.models.SerialInfoShort;
import com.example.alex.player_demo_2.mvp.views.SerialInfoView;
import com.example.alex.player_demo_2.rx.ParseService;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SerialInfoPresenter extends BasePresenter<SerialInfoView> {

    private ParseService mParseService;
    private SerialInfoShort serialInfoShort;

    public SerialInfoPresenter(ParseService parseService) {
        mParseService = parseService;
    }

    public SerialInfoPresenter(SerialInfoShort serialInfoShort) {
        this.serialInfoShort = serialInfoShort;
    }

    public SerialInfoPresenter() {

    }

    public void getSerialDetails(String url, boolean isRefreshing) {
        parseDetails(url, isRefreshing);
        parseChapters(url, isRefreshing);
    }


    private void parseDetails(String url, boolean isRefreshing) {
        getViewState().onStartLoading();
        showProgress();
        new ParseService().getSerialDetails(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SerialInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SerialInfo details) {
                        onLoadingFinish();
                        onLoadingSuccess(details);

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadingFinish();
                        onLoadingFailed(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void parseChapters(String url, boolean isRefreshing){

        showProgress();
        new ParseService().getSerialsChapters(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<SerialChapter>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArrayList<SerialChapter> chapters) {
                        onLoadingFinish();

                        onLoadingSuccess(chapters);

                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadingFinish();
                        onLoadingFailed(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i("INFO SHORT:  ", serialInfoShort == null?"NULL":"NOT NULL");
        getSerialDetails(serialInfoShort.getLink(), true);
    }


    private void onLoadingFailed(Throwable e) {
        getViewState().showError(e.getMessage());
    }


    private void onLoadingSuccess(SerialInfo details) {
        getViewState().showDetails(details);

    }

    private void onLoadingSuccess(ArrayList<SerialChapter> chapters) {
        getViewState().addToList(chapters);
    }


    private void onLoadingFinish() {
        getViewState().onFinishLoading();
        hideProgress();
    }

    private void hideProgress() {
        getViewState().hideRefreshing();
    }


    private void showProgress() {
        getViewState().showRefreshing();
    }
}
