package org.example.bench;

import org.example.dp.EditDistance;
import org.example.dp.LCS;
import org.example.matching.KMPMatcher;
import org.example.matching.NaiveMatcher;
import org.example.matching.RabinKarpMatcher;
import org.example.matching.StringMatcher;
import org.example.suffix.Kasai;
import org.example.suffix.SuffixArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Benchmark {
    static final String ALPHABET = "ACGT";
    static final long SEED = 42;
    static final int RUNS = 5;
    static final int WARMUP_RUNS = 5;
    static final int PATTERN_LEN = 20;
    static final int WORST_CASE_PATTERN_LEN = 100;

    static volatile long sink;

    public static void main(String[] args) throws IOException {
        Path out = Paths.get("results");
        Files.createDirectories(out);

        warmUp();

        StringBuilder md = new StringBuilder();
        md.append("Alphabet: ").append(ALPHABET).append(", seed: ").append(SEED)
                .append(", runs per measurement: ").append(RUNS).append("\n\n");

        runDp(out, md);
        runMatching(out, md);
        runKasai(out, md);

        Files.writeString(out.resolve("results.md"), md.toString());
        System.out.println("\nResults written to " + out.toAbsolutePath());
    }

    static void runDp(Path out, StringBuilder md) throws IOException {
        int[] sizes = {100, 500, 1000};
        System.out.println("== LCS and Edit Distance ==");
        md.append("### LCS and Edit Distance (average of ").append(RUNS).append(" runs)\n\n");
        md.append("| Length | LCS time (ms) | Edit Distance time (ms) | LCS result | Edit Distance result |\n");
        md.append("|---|---|---|---|---|\n");

        try (PrintWriter w = new PrintWriter(out.resolve("dp_results.csv").toFile())) {
            w.println("size,lcs_ms,edit_ms,lcs_result,edit_result");
            for (int n : sizes) {
                Random rnd = new Random(SEED + n);
                String a = randomString(rnd, n);
                String b = randomString(rnd, n);

                int lcsResult = LCS.length(a, b);
                int editResult = EditDistance.distance(a, b);
                double lcsMs = timeMs(() -> sink += LCS.length(a, b));
                double editMs = timeMs(() -> sink += EditDistance.distance(a, b));

                System.out.printf("n=%d  LCS=%.3f ms  Edit=%.3f ms%n", n, lcsMs, editMs);
                w.printf(java.util.Locale.US, "%d,%.4f,%.4f,%d,%d%n", n, lcsMs, editMs, lcsResult, editResult);
                md.append(String.format(java.util.Locale.US, "| %d | %.3f | %.3f | %d | %d |%n",
                        n, lcsMs, editMs, lcsResult, editResult));
            }
        }
        md.append("\n");
    }

    static void runMatching(Path out, StringBuilder md) throws IOException {
        int[] textLens = {10_000, 50_000, 100_000};
        System.out.println("\n== String matching ==");
        md.append("### String matching (average of ").append(RUNS).append(" runs)\n\n");
        md.append("| Scenario | Text length | Algorithm | Time (ms) | Comparisons | Matches |\n");
        md.append("|---|---|---|---|---|---|\n");

        try (PrintWriter w = new PrintWriter(out.resolve("matching_results.csv").toFile())) {
            w.println("scenario,text_length,algorithm,avg_ms,comparisons,matches");
            for (int n : textLens) {
                Random rnd = new Random(SEED + n);
                String text = randomString(rnd, n);
                String present = text.substring(n / 2, n / 2 + PATTERN_LEN);
                String absent = makeAbsentPattern(rnd, text, PATTERN_LEN);
                String worstText = "A".repeat(n);
                String worstPattern = "A".repeat(WORST_CASE_PATTERN_LEN - 1) + "C";

                benchScenario("present", text, present, n, w, md);
                benchScenario("absent", text, absent, n, w, md);
                benchScenario("worst-case", worstText, worstPattern, n, w, md);
            }
        }
        md.append("\n");
    }

    static void benchScenario(String scenario, String text, String pattern, int n,
                              PrintWriter w, StringBuilder md) {
        StringMatcher[] matchers = {new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher()};
        for (StringMatcher m : matchers) {
            double ms = timeMs(() -> sink += m.search(text, pattern).size());
            long comps = m.getComparisons();
            int matches = m.search(text, pattern).size();
            System.out.printf("%-10s n=%-7d %-10s %.4f ms  comparisons=%d  matches=%d%n",
                    scenario, n, m.name(), ms, comps, matches);
            w.printf(java.util.Locale.US, "%s,%d,%s,%.5f,%d,%d%n", scenario, n, m.name(), ms, comps, matches);
            md.append(String.format(java.util.Locale.US, "| %s | %d | %s | %.4f | %d | %d |%n",
                    scenario, n, m.name(), ms, comps, matches));
        }
    }

    static void runKasai(Path out, StringBuilder md) throws IOException {
        int[] sizes = {1000, 5000, 10_000};
        System.out.println("\n== Kasai ==");
        md.append("### Kasai's algorithm (average of ").append(RUNS).append(" runs)\n\n");
        md.append("| String length | Kasai time (ms) | Max LCP value |\n|---|---|---|\n");

        try (PrintWriter w = new PrintWriter(out.resolve("kasai_results.csv").toFile())) {
            w.println("size,avg_ms,max_lcp");
            for (int n : sizes) {
                Random rnd = new Random(SEED + n);
                String s = randomString(rnd, n);
                int[] sa = SuffixArray.build(s);

                double ms = timeMs(() -> sink += Kasai.buildLcp(s, sa)[n - 1]);
                int[] lcp = Kasai.buildLcp(s, sa);
                int max = 0;
                for (int v : lcp) {
                    max = Math.max(max, v);
                }

                System.out.printf("n=%d  Kasai=%.4f ms  maxLCP=%d%n", n, ms, max);
                w.printf(java.util.Locale.US, "%d,%.5f,%d%n", n, ms, max);
                md.append(String.format(java.util.Locale.US, "| %d | %.4f | %d |%n", n, ms, max));
            }
        }
        md.append("\n");
    }

    static void warmUp() {
        Random rnd = new Random(SEED);
        String a = randomString(rnd, 500);
        String b = randomString(rnd, 500);
        String text = randomString(rnd, 20_000);
        String pattern = text.substring(1000, 1020);
        String small = text.substring(0, 2000);
        int[] sa = SuffixArray.build(small);
        StringMatcher[] ms = {new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher()};
        for (int i = 0; i < 200; i++) {
            sink += LCS.length(a, b);
            sink += EditDistance.distance(a, b);
            for (StringMatcher m : ms) {
                sink += m.search(text, pattern).size();
            }
            sink += Kasai.buildLcp(small, sa)[1999];
        }
    }

    static double timeMs(Runnable task) {
        for (int w = 0; w < WARMUP_RUNS; w++) {
            task.run();
        }
        System.gc();
        long total = 0;
        for (int r = 0; r < RUNS; r++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }

    static String randomString(Random rnd, int len) {
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = ALPHABET.charAt(rnd.nextInt(ALPHABET.length()));
        }
        return new String(c);
    }

    static String makeAbsentPattern(Random rnd, String text, int len) {
        String p;
        do {
            p = randomString(rnd, len);
        } while (text.contains(p));
        return p;
    }
}