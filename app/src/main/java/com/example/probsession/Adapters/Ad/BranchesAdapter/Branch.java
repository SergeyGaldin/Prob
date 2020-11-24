package com.example.probsession.Adapters.Ad.BranchesAdapter;

public class Branch {
    String address, place, work, clock;

    public Branch(String address, String place, String work, String clock) {
        this.address = address;
        this.place = place;
        this.work = work;
        this.clock = clock;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }
}
