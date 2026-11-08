package org.example;

import org.example.matching.KMPMatcher;
import org.example.matching.NaiveMatcher;
import org.example.matching.RabinKarpMatcher;
import org.example.matching.StringMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchingTest {

    private void checkAll(String text, String pattern, List<Integer> expected) {
        StringMatcher[] matchers = {new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher()};
        for (StringMatcher m : matchers) {
            assertEquals(expected, m.search(text, pattern), m.name());
        }
    }

    @Test
    void patternAtBeginning() {
        checkAll("abcdefg", "abc", List.of(0));
    }

    @Test
    void patternInMiddle() {
        checkAll("abcdefg", "cde", List.of(2));
    }

    @Test
    void patternAtEnd() {
        checkAll("abcdefg", "efg", List.of(4));
    }

    @Test
    void multipleOccurrences() {
        checkAll("abababa", "aba", List.of(0, 2, 4));
    }

    @Test
    void patternAbsent() {
        checkAll("abcdefg", "xyz", List.of());
    }

    @Test
    void patternLongerThanText() {
        checkAll("ab", "abc", List.of());
    }

    @Test
    void emptyPattern() {
        checkAll("abc", "", List.of());
    }

    @Test
    void patternEqualsText() {
        checkAll("abc", "abc", List.of(0));
    }

    @Test
    void repeatedSingleCharacter() {
        checkAll("aaaaa", "aa", List.of(0, 1, 2, 3));
    }
}