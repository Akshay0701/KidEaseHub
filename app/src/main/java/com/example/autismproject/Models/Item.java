package com.example.autismproject.Models;

public class Item {
    String iID, cID, imgUrl, text, pID;

    public Item(String iID, String cID, String imgUrl, String text, String pID) {
        this.iID = iID;
        this.cID = cID;
        this.imgUrl = imgUrl;
        this.text = text;
        this.pID = pID;
    }

    public Item() {
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getiID() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = iID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
