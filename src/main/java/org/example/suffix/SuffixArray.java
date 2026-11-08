package org.example.suffix;

import java.util.Arrays;

public class SuffixArray {

    public static int[] build(String s) {
        int n = s.length();
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (x, y) -> s.substring(x).compareTo(s.substring(y)));

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = idx[i];
        }
        return sa;
    }
}