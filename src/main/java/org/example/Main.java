package org.example;

import org.example.dp.LCS;

public class Main {
    public static void main(String[] args) {
        System.out.println(LCS.length("ABCBDAB", "BDCABA"));
        System.out.println(LCS.length("abc", "xyz"));
    }
}