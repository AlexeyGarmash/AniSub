package com.example.alex.player_demo_2.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.alex.player_demo_2.models.SerialInfoShort;
import com.example.alex.player_demo_2.mvp.views.AllSerialsView;
import com.example.alex.player_demo_2.rx.ParseService;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AllSerialsPresenter extends BasePresenter<AllSerialsView> {


    private int data_id = 1;

    private ParseService mParseService;

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public AllSerialsPresenter(ParseService parseService) {
        mParseService = parseService;
    }

    public AllSerialsPresenter() {
    }

    public void getSerialsInBlock(String url, int dataId, boolean isRefreshing) {
        data_id = dataId;
        if (dataId < 30) {
            parseBlock(url, dataId, isRefreshing);
        }
    }


    private void parseBlock(String url, int dataId, boolean isRefreshing) {
        getViewState().onStartLoading();
        showProgress();
        new ParseService().getSerialsInfo(url, String.valueOf(dataId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<SerialInfoShort>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArrayList<SerialInfoShort> serials) {
                        onLoadingFinish();
                        onLoadingSuccess(serials);
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
        getSerialsInBlock("https://aniplay.tv/", 1, true);
    }


    private void onLoadingFailed(Throwable e) {
        getViewState().showError(e.getMessage());
    }


    private void onLoadingSuccess(ArrayList<SerialInfoShort> serials) {
        getViewState().addToList(serials);
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
