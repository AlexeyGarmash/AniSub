package com.example.alex.player_demo_2.models;

public class Rate {

    private String rateKinogo;
    private String rateImdb;

    public Rate(String rateKinogo, String rateImdb) {
        this.rateImdb = rateImdb;
        this.rateKinogo = rateKinogo;
    }

    public String getRateKinogo() {
        return rateKinogo;
    }

    public void setRateKinogo(String rateKinogo) {
        this.rateKinogo = rateKinogo;
    }

    public String getRateImdb() {
        return rateImdb;
    }

    public void setRateImdb(String rateImdb) {
        this.rateImdb = rateImdb;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rateKinogo='" + rateKinogo + '\'' +
                ", rateImdb='" + rateImdb + '\'' +
                '}';
    }
}
