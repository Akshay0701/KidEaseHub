package com.example.autismproject.Models;

public class Category {
    // cID is category id which is going to later used for deleting the category data
    // pID is parent id primary reason for pID is to identify access of deletion for particular parent for specific data
    String name, imgUrl, cID, pID;

    public Category(String name, String imgUrl, String cID, String pID) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.cID = cID;
        this.pID = pID;
    }

    public Category() {
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }
}
