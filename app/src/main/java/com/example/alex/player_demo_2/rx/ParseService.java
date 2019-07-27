package com.example.alex.player_demo_2.rx;

import com.example.alex.player_demo_2.ParseAllSerials;
import com.example.alex.player_demo_2.ParseSerialDetail;
import com.example.alex.player_demo_2.models.SerialChapter;
import com.example.alex.player_demo_2.models.SerialInfo;
import com.example.alex.player_demo_2.models.SerialInfoShort;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;

public class ParseService implements ParseApi {


    @Override
    public Observable<ArrayList<SerialInfoShort>> getSerialsInfo(String url, String dataId) {
        return Observable.create(observableEmitter ->{
            try {
                String URL = url;
                Document doc = null;
                if(Integer.valueOf(dataId) > 1){
                    URL+=String.format("/page/%s/",dataId);
                }
                //tlucpk19eg5pqsedmp6vacb375
                doc = Jsoup.connect(URL)
                        .data("login_name", "test_account")
                        .data("login_password", "test_account")
                        .data("login","submit")
                        .post();
                ArrayList<SerialInfoShort> serials = new ParseAllSerials(doc).getSerialsBlock(dataId);
                observableEmitter.onNext(serials);
            }
            catch (IOException e){
                observableEmitter.onError(e);
            } finally {
                observableEmitter.onComplete();
            }
        });
    }

    @Override
    public Observable<ArrayList<SerialChapter>> getSerialsChapters(String url) {
        return Observable.create(observableEmitter ->{
            try {
                Document doc = null;
                doc = Jsoup.connect(url)
                        .data("login_name", "test_account")
                        .data("login_password", "test_account")
                        .data("login","submit")
                        .post();

                //ArrayList<SerialInfoShort> serials = new ParseAllSerials(doc).getSerialsBlock(dataId);
                ArrayList<SerialChapter> chapters = new ParseSerialDetail(doc).getChapters();
                observableEmitter.onNext(chapters);
            }
            catch (IOException e){
                observableEmitter.onError(e);
            } finally {
                observableEmitter.onComplete();
            }
        });
    }

    @Override
    public Observable<SerialInfo> getSerialDetails(String url) {
        return Observable.create(observableEmitter ->{
            try {
                Document doc = null;
                doc = Jsoup.connect(url)
                        .data("login_name", "test_account")
                        .data("login_password", "test_account")
                        .data("login","submit")
                        .post();
                //ArrayList<SerialInfoShort> serials = new ParseAllSerials(doc).getSerialsBlock(dataId);
                SerialInfo serInfo = new ParseSerialDetail(doc).getSerialDetails();
                observableEmitter.onNext(serInfo);
            }
            catch (IOException e){
                observableEmitter.onError(e);
            } finally {
                observableEmitter.onComplete();
            }
        });
    }
}
