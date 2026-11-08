package org.example;

import org.example.suffix.Kasai;
import org.example.suffix.SuffixArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class KasaiTest {

    @Test
    void bananaWithManualSuffixArray() {
        String s = "banana";
        int[] sa = {5, 3, 1, 0, 4, 2};
        int[] expectedLcp = {0, 1, 3, 0, 0, 2};
        assertArrayEquals(expectedLcp, Kasai.buildLcp(s, sa));
    }

    @Test
    void suffixArrayBuilderMatchesManualResult() {
        assertArrayEquals(new int[]{5, 3, 1, 0, 4, 2}, SuffixArray.build("banana"));
    }

    @Test
    void bananaWithBuiltSuffixArray() {
        String s = "banana";
        assertArrayEquals(new int[]{0, 1, 3, 0, 0, 2}, Kasai.buildLcp(s, SuffixArray.build(s)));
    }
}