package com.example.alex.player_demo_2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SerialInfoShort implements Parcelable {

    private String link;
    private String title;
    private String category;
    private String imageUrl;
    private String status;

    public SerialInfoShort(String link, String title, String category, String imageUrl, String status) {
        this.link = link;
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public SerialInfoShort(String link, String title, String imageUrl, String status) {
        this.link = link;
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String rate) {
        this.category = rate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SerialInfoShort{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(imageUrl);
        dest.writeString(status);
    }

    public static final Parcelable.Creator<SerialInfoShort> CREATOR = new Parcelable.Creator<SerialInfoShort>() {
        public SerialInfoShort createFromParcel(Parcel in) {
            return new SerialInfoShort(in);
        }

        public SerialInfoShort[] newArray(int size) {
            return new SerialInfoShort[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private SerialInfoShort(Parcel in) {
        link = in.readString();
        title = in.readString();
        category = in.readString();
        imageUrl = in.readString();
        status = in.readString();
    }
}
