package com.example.autismproject.Models;

public class Video {
    String vID, title, url, cID, pID;

    public Video() {
    }

    public Video(String vID, String title, String url, String cID, String pID) {
        this.vID = vID;
        this.title = title;
        this.url = url;
        this.cID = cID;
        this.pID = pID;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }
}
