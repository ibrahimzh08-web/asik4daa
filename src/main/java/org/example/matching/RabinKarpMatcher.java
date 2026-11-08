package org.example.matching;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpMatcher implements StringMatcher {
    private static final long BASE = 131;
    private static final long MOD = 1_000_000_007L;

    private long comparisons;
    private long spuriousHits;

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        spuriousHits = 0;
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || m > n) {
            return result;
        }

        long pow = 1;
        for (int k = 1; k < m; k++) {
            pow = (pow * BASE) % MOD;
        }


        long hashPattern = 0;
        long hashWindow = 0;
        for (int k = 0; k < m; k++) {
            hashPattern = (hashPattern * BASE + pattern.charAt(k)) % MOD;
            hashWindow = (hashWindow * BASE + text.charAt(k)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (hashPattern == hashWindow) {

                int j = 0;
                while (j < m) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                    j++;
                }
                if (j == m) {
                    result.add(i);
                } else {
                    spuriousHits++;
                }
            }


            if (i < n - m) {
                hashWindow = (hashWindow - text.charAt(i) * pow % MOD + MOD) % MOD;  // убрали text[i]
                hashWindow = (hashWindow * BASE + text.charAt(i + m)) % MOD;         // добавили text[i+m]
            }
        }
        return result;
    }

    public long getSpuriousHits() {
        return spuriousHits;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }

    @Override
    public String name() {
        return "Rabin-Karp";
    }
}