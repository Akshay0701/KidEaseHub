package com.example.autismproject.Models;

public class Child {
    String cID, pID, name, age, scores, imgUrl;

    public Child() {
    }

    public Child(String cID, String pID, String name, String age, String scores, String imgUrl) {
        this.cID = cID;
        this.pID = pID;
        this.name = name;
        this.age = age;
        this.scores = scores;
        this.imgUrl = imgUrl;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}
