package com.example.alex.player_demo_2;

import com.example.alex.player_demo_2.common.Utils;
import com.example.alex.player_demo_2.models.SerialInfoShort;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParseAllSerials {

    private Document document;

    public ParseAllSerials(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }


    public ArrayList<SerialInfoShort> getSerialsBlock(String dataId) {
        ArrayList<SerialInfoShort> serials = new ArrayList<>();
        Elements alphaList = document.select("#short > div");
        String baseUrl = document.baseUri();
        for (Element block : alphaList) {
            String titleRus = block.selectFirst("div.sh-desc > a > div").text();
            String titleEng = block.selectFirst("div.sh-desc > div:nth-child(2)").text();
            String title = String.format("%s%n%s", titleRus, titleEng);
            String link = block.selectFirst("div.sh-desc > a").attr("href");
            String descrip = block.selectFirst("div.sh-desc > div:nth-child(3)").text();
            String imgAnime = Utils.BASE_URL + block.selectFirst("a > img").attr("src");
            String status = block.selectFirst("a > div.short-meta.short-label.sl-y").text();
            SerialInfoShort serialInfo = new SerialInfoShort(link, titleRus, descrip, imgAnime, status);
            serials.add(serialInfo);
        }
        return serials;
    }

    public ArrayList<SerialInfoShort> getAnimeBlocks_SovetRom(){
        int i=1;
        ArrayList<SerialInfoShort> animeList = new ArrayList<>();
        while(true){
            String selectorContainer = String.format("body > div.block--full.mainContainer > div.block--container > div > div:nth-child(%s)", String.valueOf(i));

            SerialInfoShort anime = parseAnimeInfoShort(selectorContainer);
            if(anime != null){
                animeList.add(anime);
                i++;
            }
            else
            {
                i = 1;
                break;
            }
        }
        return  animeList;
    }

    private SerialInfoShort parseAnimeInfoShort(String selector){
        Element animeInfoDiv = document.selectFirst(selector);
        if(animeInfoDiv == null){
            return  null;
        }
        String status = animeInfoDiv.selectFirst("div > a > div").text();
        String titleRus = animeInfoDiv.selectFirst("div > div > span:nth-child(2)").text();
        String titleEng = animeInfoDiv.selectFirst("div > div > span:nth-child(1)").text();
        String link = Utils.BASE_URL_SR + animeInfoDiv.selectFirst("div > a").attr("href");
        String animeThumb = animeInfoDiv.selectFirst("meta:nth-child(2)").attr("content");
        SerialInfoShort animeInfoShort = new SerialInfoShort(link, titleRus, animeThumb, status);
        return  animeInfoShort;
    }



    /*private class DownloadPoster extends AsyncTask<String, Void, Document> {

        private String siteUrl;
        private SerialInfoShort serialInfoShort;

        public DownloadPoster(String siteUrl, SerialInfoShort serialInfoShort) {
            this.siteUrl = siteUrl;
            this.serialInfoShort = serialInfoShort;
        }

        public SerialInfoShort getSerialInfoShort() {
            return serialInfoShort;
        }

        public void setSerialInfoShort(SerialInfoShort serialInfoShort) {
            this.serialInfoShort = serialInfoShort;
        }

        public String getSiteUrl() {
            return siteUrl;
        }

        public void setSiteUrl(String siteUrl) {
            this.siteUrl = siteUrl;
        }


        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            try {
                doc = Jsoup.connect(siteUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            ParseSerialDetail parseSerialDetail = new ParseSerialDetail(document);

            String genre = parseSerialDetail.getGenre();
            String poster = parseSerialDetail.getPosterUrl();

            Log.i("GENREEEEEEEEEEE", genre);
            Log.i("POSTEEEER", poster);

            serialInfoShort.setGenre(genre);
            serialInfoShort.setPosterUrl(poster);
        }
    }*/

}
