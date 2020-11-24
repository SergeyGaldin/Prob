package com.example.probsession.Constructors;

public class DateConstructor {
    String date, time;

    public DateConstructor() {
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

    public DateConstructor(String date, String time) {
        this.date = date;
        this.time = time;
    }
}
