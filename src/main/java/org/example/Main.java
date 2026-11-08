package org.example;

import org.example.suffix.Kasai;
import org.example.suffix.SuffixArray;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String s = "banana";
        int[] sa = SuffixArray.build(s);
        int[] lcp = Kasai.buildLcp(s, sa);
        System.out.println(Arrays.toString(sa));    // [5, 3, 1, 0, 4, 2]
        System.out.println(Arrays.toString(lcp));   // [0, 1, 3, 0, 0, 2]
    }
}