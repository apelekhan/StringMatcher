package com.apelekhan.stringmatcher;

public class MatchResult {
    public MatchResult(String string, int position) {
        this.string = string;
        this.position = position;
    }

    public String getString() {
        return string;
    }

    public int getPosition() {
        return position;
    }

    private String string;
    private int position;
}
