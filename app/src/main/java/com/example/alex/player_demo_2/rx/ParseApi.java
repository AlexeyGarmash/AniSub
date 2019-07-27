package com.example.alex.player_demo_2.rx;

import com.example.alex.player_demo_2.models.SerialChapter;
import com.example.alex.player_demo_2.models.SerialInfo;
import com.example.alex.player_demo_2.models.SerialInfoShort;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ParseApi {

    Observable<ArrayList<SerialInfoShort>> getSerialsInfo(String url, String dataId);
    Observable<ArrayList<SerialChapter>> getSerialsChapters(String url);
    Observable<SerialInfo> getSerialDetails(String url);
}
