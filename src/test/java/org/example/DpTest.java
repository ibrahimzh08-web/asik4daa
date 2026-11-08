package org.example;

import org.example.dp.EditDistance;
import org.example.dp.LCS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DpTest {

    @Test
    void lcsIdenticalStrings() {
        assertEquals(5, LCS.length("abcde", "abcde"));
    }

    @Test
    void lcsCompletelyDifferent() {
        assertEquals(0, LCS.length("abc", "xyz"));
    }

    @Test
    void lcsEmptyString() {
        assertEquals(0, LCS.length("", "abc"));
        assertEquals(0, LCS.length("abc", ""));
        assertEquals(0, LCS.length("", ""));
    }

    @Test
    void lcsClassicExample() {
        assertEquals(4, LCS.length("ABCBDAB", "BDCABA"));
    }

    @Test
    void editIdenticalStrings() {
        assertEquals(0, EditDistance.distance("hello", "hello"));
    }

    @Test
    void editOneStringEmpty() {
        assertEquals(3, EditDistance.distance("", "abc"));
        assertEquals(3, EditDistance.distance("abc", ""));
    }

    @Test
    void editSubstitution() {
        assertEquals(1, EditDistance.distance("cat", "cut"));
    }

    @Test
    void editInsertion() {
        assertEquals(1, EditDistance.distance("cat", "cart"));
    }

    @Test
    void editDeletion() {
        assertEquals(1, EditDistance.distance("cart", "cat"));
    }

    @Test
    void editKittenSitting() {
        assertEquals(3, EditDistance.distance("kitten", "sitting"));
    }
}