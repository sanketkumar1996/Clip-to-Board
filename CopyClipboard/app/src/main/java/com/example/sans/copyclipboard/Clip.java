package com.example.sans.copyclipboard;

/**
 * Created by sans on 20/8/16.
 */
public class Clip {
    private String text;

    public Clip(String text) {
        this.text = text;
    }
    public Clip(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
