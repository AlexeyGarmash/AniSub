package com.example.alex.player_demo_2;

import android.util.Log;

import com.example.alex.player_demo_2.common.Utils;
import com.example.alex.player_demo_2.models.SerialChapter;
import com.example.alex.player_demo_2.models.SerialInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParseSerialDetail {

    private Document document;

    public ParseSerialDetail(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }


    public SerialInfo getSerialDetails(){
        SerialInfo serInfo;
        String sujet = getTextBySelector("#fdesc");
        String generalDesc = getDescriptionBlock("#flist > li");
        serInfo = new SerialInfo(generalDesc, sujet);
        return  serInfo;
    }

    private String getTextBySelector(String selector){
        String text = document.selectFirst(selector).text();
        return  text;
    }

    private  String getDescriptionBlock(String selector){
        String result = "";
        Elements list = document.select(selector);
        int count = list.size();
        int i = 0;
        for(Element element : list){
            if(i == count - 1){
                result+=element.text();
            }
            else{
                result+=element.text()+"\n";
            }
            i++;
        }
        return result;
    }

    public ArrayList<SerialChapter> getChapters(){

        ArrayList<SerialChapter> chapters = new ArrayList<>();
        Element root = document.selectFirst("#fmain > div.ftabs.tabs-box > div");
        if(root == null){
            return chapters;
        }
        String numID = root.attr("data-news_id");
        String requestURL = String.format("https://aniplay.tv/engine/ajax/playlists.php?news_id=%s&xfield=sibnet", numID);
        JSONObject getObject = null;
        try {
            getObject = Utils.getJSONObjectFromURL(requestURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String VALUE_HTML = "";

        try {
            VALUE_HTML = getObject.getString("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        document.selectFirst("#fmain > div.ftabs.tabs-box > div")
                .append(VALUE_HTML);

        Elements list = document.select("#fmain > div.ftabs.tabs-box > div > div > div.playlists-videos > div > ul > li");
        Log.i("DDDDDDDDDDDDDDDDDjDDD", "fff "+list.size());
                                                //#fmain > div.ftabs.tabs-box > div > div > div.playlists-videos > div > ul
                                                //#fmain > div.ftabs.tabs-box > div > div > div.playlists-videos > div > ul > li.active
        for(Element element:list){
            String chapterNum = element.text();
            String link = element.attr("data-file");
            chapters.add(new SerialChapter(chapterNum, link));

        }
        return chapters;
    }

}
