package com.example.lib;


import java.util.HashMap;

public class Card {
    /**
     * this value is the value of the card.
     */
    private int value;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getUrl() {
        return url;
    }

    public static HashMap<String, Integer> values;
    static {
        values = new HashMap<String, Integer>();
        for (int i = 2; i < 10; i++) {
            values.put(i + "", i);
        }
        values.put("A", 1);
        values.put("0", 10);
        values.put("J", 10);
        values.put("Q", 10);
        values.put("K", 10);
    }

    public Card(String url, int value) {
        this.url = url;
        this.value = value;


    }

}
