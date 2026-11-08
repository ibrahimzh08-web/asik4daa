package org.example;

import org.example.matching.KMPMatcher;
import org.example.matching.NaiveMatcher;
import org.example.matching.RabinKarpMatcher;
import org.example.matching.StringMatcher;

public class Main {
    public static void main(String[] args) {
        StringMatcher[] matchers = {new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher()};
        for (StringMatcher m : matchers) {
            System.out.println(m.name());
            System.out.println(m.search("abababa", "aba"));    // [0, 2, 4]
            System.out.println(m.search("abcdefg", "xyz"));    // []
            System.out.println(m.search("abc", ""));           // []
            System.out.println(m.search("ab", "abc"));         // []
        }
    }
}