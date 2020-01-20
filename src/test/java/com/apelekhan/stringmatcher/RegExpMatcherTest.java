package com.apelekhan.stringmatcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegExpMatcherTest {
    @Test
    void matchWholeString() {
        List<MatchResult> results =
                RegExpMatcher.matchStrings(new String[]{"line of text"}, "line of text");

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "line of text");
    }

    @Test
    void matchSimplePattern() {
        List<MatchResult> results =
                RegExpMatcher.matchStrings(new String[]{"ab*c"}, "abbbbbc");

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getPosition(), 0);
        assertEquals(results.get(0).getString(), "abbbbbc");
    }
}
