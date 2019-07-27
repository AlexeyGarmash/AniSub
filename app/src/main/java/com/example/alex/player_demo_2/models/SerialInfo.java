package com.example.alex.player_demo_2.models;



public class SerialInfo {

    private SerialInfoShort shortInfo;
    private String generalDescription;
    private String description;

    public SerialInfo(String generalDescription, String description) {
        this.generalDescription = generalDescription;
        this.description = description;
    }


    public SerialInfoShort getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(SerialInfoShort shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    @Override
    public String toString() {
        return "SerialInfo{" +
                "shortInfo=" + shortInfo +
                ", generalDescription='" + generalDescription + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
