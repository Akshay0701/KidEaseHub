package com.example.autismproject.Models;

import android.graphics.drawable.Drawable;

public class ColorHelper {

    Drawable gradient;
    String title;

    public ColorHelper(Drawable gradient, String title) {
        this.title = title;
        this.gradient = gradient;
    }

    public Drawable getGradient() {
        return gradient;
    }

    public String getTitle() {
        return title;
    }
}


