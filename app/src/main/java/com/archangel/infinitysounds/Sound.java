package com.archangel.infinitysounds;

/**
 * Created by Archangel on 09/09/2014.
 */
public class Sound {

    private String title;
    private String file;

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return this.title;
    }
}