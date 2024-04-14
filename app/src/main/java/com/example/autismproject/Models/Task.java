package com.example.autismproject.Models;

public class Task {
    // timer is number of seconds task will be performed
    // timestamp is the time at which child as to perform the task
    String tID, text, imgUrl, timestamp, timer, isComplete, pID, cID;

    public Task() {
    }

    public Task(String tID, String text, String imgUrl, String timestamp, String timer, String isComplete, String pID, String cID) {
        this.tID = tID;
        this.text = text;
        this.imgUrl = imgUrl;
        this.timestamp = timestamp;
        this.timer = timer;
        this.isComplete = isComplete;
        this.pID = pID;
        this.cID = cID;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }
}
