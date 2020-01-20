package com.apelekhan.stringmatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<MatchResult> matchStrings(String[] dictionary, String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return AhoCorasick.matchStrings(dictionary, content, true);
        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        String[] dictionary = {"the", "dictionary", "aa"};

        List<MatchResult> results = matchStrings(dictionary, "text.txt");

        for (MatchResult result : results) {
            System.out.println("Found " + result.getString() + " at " + result.getPosition());
        }
    }
}
