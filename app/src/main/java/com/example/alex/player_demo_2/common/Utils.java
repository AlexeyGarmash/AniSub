package com.example.alex.player_demo_2.common;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alex.player_demo_2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Utils {

    public final static String BASE_URL = "https://aniplay.tv/";
    public final  static  String BASE_URL_SR = "https://sovetromantica.com/";
    public static String username = "awashwind";
    public static String password = "123shadowALEX";

    public static String getLoginData(){
        String login = username + ":" + password;
        String base64login = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            base64login = new String(Base64.getEncoder().encode(login.getBytes()));
        }
        return base64login;
    }


    /**
     * Устанавливает изображение в {@link ImageView} с помощью {@link Glide} по ссылке (URL)
     *
     * @param context контекст {@link AppCompatActivity}
     * @param view    {@link ImageView}, к которому ведется привязка изображения
     * @param width   новая ширина изображения
     * @param height  новая высота изображения
     * @param imgURL  ссылка на изображение
     */
    public static void setImageByURL(Context context, ImageView view, int width, int height, String imgURL) {
        Glide.with(context)
                .load(imgURL)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.anime_placeholder)
                                .fitCenter()
                        )
                .into(view);
    }
    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }
    public static String getAnyHTML(String urlString) throws IOException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return jsonString;
    }

    public static String getVideoStreamFile(String html, String secureCon){
        String finderElement = "},{url: ";
        int startIndex = html.lastIndexOf(finderElement);
        int endIndex = html.indexOf('}',startIndex + 2);
        String res = html.substring(startIndex, endIndex);
        res = res.replace(finderElement, "");
        res = res.replace("'","");
        //res = res.replace("//","");
        res = res.trim();
        return  secureCon+":"+res;
    }


    public static String getContainsFile(String html, String secureCon){

        String finderElement = "<iframe src=\"";
        int startIndex = html.indexOf(finderElement);
        int endIndex = html.indexOf('\"',startIndex + finderElement.length());
        String res = html.substring(startIndex, endIndex);
        res = res.replace(finderElement, "");
        res = res.trim();
        if(!res.contains("://")){
            res = res.replace("//", secureCon + "://");
        }
        return res;
    }

    public static Spanned getFormattedString(String text){
        String formattedStr = "";
        //Html.fromHtml
        String lines[] = text.split("\n");
        for(String line : lines){
            int endPos = line.indexOf(':');
            int startPos = 0;
            String newLine = "<b>"+line.substring(startPos, endPos)+"</b>"+line.substring(endPos, line.length() - 1);
            formattedStr += newLine + "<br/>";
        }
        return Html.fromHtml(formattedStr);
    }
}
