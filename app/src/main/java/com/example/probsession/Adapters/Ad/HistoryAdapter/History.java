package com.example.probsession.Adapters.Ad.HistoryAdapter;

public class History {
    String date, time;

    public History() {
    }

    public History(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
