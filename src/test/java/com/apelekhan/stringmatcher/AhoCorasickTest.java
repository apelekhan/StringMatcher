package com.apelekhan.stringmatcher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class AhoCorasickTest {

    @Test
    void matchWholeString() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"Aho-Corasick"}, "Aho-Corasick", true);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "Aho-Corasick");
    }

    @Test
    void matchStringWithNewline() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"hello world"}, "hello world\nhello\nworld", true);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "hello world");
    }

    @Test
    void matchPrefixStrings() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"aa", "aab"}, "baab", true);

        assertEquals(results.size(), 2);
        assertEquals(results.get(0).getPosition(), 1);
        assertEquals(results.get(1).getPosition(), 1);
        assertEquals(results.get(0).getString(), "aa");
        assertEquals(results.get(1).getString(), "aab");
    }

    @Test
    void matchRepeatingString() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"aa", "aac"}, "aaacaac", true);

        assertEquals(results.size(), 5);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "aa");
        assertEquals(results.get(1).getPosition(), 1);
        assertEquals(results.get(1).getString(), "aa");
        assertEquals(results.get(2).getPosition(), 1);
        assertEquals(results.get(2).getString(), "aac");
        assertEquals(results.get(3).getPosition(), 4);
        assertEquals(results.get(3).getString(), "aa");
        assertEquals(results.get(4).getPosition(), 4);
        assertEquals(results.get(4).getString(), "aac");
    }

    @Test
    void matchStringFail() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"aaa", "bbb"}, "aabb", true);

        assertEquals(results.size(), 0);
    }

    @Test
    void matchLastSymbol() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"a"}, "cbcbcbcbca", true);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 9);
        assertEquals(results.get(0).getString(), "a");
    }

    // bug injection
    @Test
    void matchThreePrefixStrings() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"a", "aa", "aaa"}, "aaa", true);

        assertEquals(results.size(), 6);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "a");
        assertEquals(results.get(1).getPosition(), 0);
        assertEquals(results.get(1).getString(), "aa");
        assertEquals(results.get(2).getPosition(), 0);
        assertEquals(results.get(2).getString(), "aaa");
    }

    @Test
    void matchCaseInsensitive() {
        List<MatchResult> results =
                AhoCorasick.matchStrings(new String[]{"aaa"}, "Aaa", false);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "Aaa");
    }
}
