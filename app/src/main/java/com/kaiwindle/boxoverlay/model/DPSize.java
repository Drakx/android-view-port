/*
 * Kai Windle Copyright (c) 16/06/17 09:00
 * DPSize.java
 * Boxoverlay
 */

package com.kaiwindle.boxoverlay.model;

/**
 * Created by Kai on 16/06/2017.
 * Boxoverlay
 */

public class DPSize {
    private Float width;
    private Float height;
    private Float density;

    public DPSize(Float width, Float height, Float density) {
        this.width = width;
        this.height = height;
        this.density = density;
    }

    public Float getWidth() {
        return width;
    }

    public Float getHeight() {
        return height;
    }

    public Float getDensity() {
        return density;
    }
}



