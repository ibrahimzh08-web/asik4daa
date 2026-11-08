Alphabet: ACGT, seed: 42, runs per measurement: 5

### LCS and Edit Distance (average of 5 runs)

| Length | LCS time (ms) | Edit Distance time (ms) | LCS result | Edit Distance result |
|---|---|---|---|---|
| 100 | 0.066 | 0.083 | 61 | 56 |
| 500 | 1.577 | 1.219 | 320 | 262 |
| 1000 | 6.096 | 5.240 | 647 | 524 |

### String matching (average of 5 runs)

| Scenario | Text length | Algorithm | Time (ms) | Comparisons | Matches |
|---|---|---|---|---|---|
| present | 10000 | Naive | 0.0693 | 13416 | 1 |
| present | 10000 | KMP | 0.0699 | 12529 | 1 |
| present | 10000 | Rabin-Karp | 0.1746 | 20 | 1 |
| absent | 10000 | Naive | 0.0639 | 13308 | 0 |
| absent | 10000 | KMP | 0.0703 | 12455 | 0 |
| absent | 10000 | Rabin-Karp | 0.1426 | 0 | 0 |
| worst-case | 10000 | Naive | 1.6916 | 990100 | 0 |
| worst-case | 10000 | KMP | 0.0640 | 20098 | 0 |
| worst-case | 10000 | Rabin-Karp | 0.2002 | 0 | 0 |
| present | 50000 | Naive | 0.2573 | 66677 | 1 |
| present | 50000 | KMP | 0.2443 | 61831 | 1 |
| present | 50000 | Rabin-Karp | 0.5945 | 20 | 1 |
| absent | 50000 | Naive | 0.2984 | 66475 | 0 |
| absent | 50000 | KMP | 0.2978 | 62436 | 0 |
| absent | 50000 | Rabin-Karp | 0.5232 | 0 | 0 |
| worst-case | 50000 | Naive | 8.6174 | 4990100 | 0 |
| worst-case | 50000 | KMP | 0.2826 | 100098 | 0 |
| worst-case | 50000 | Rabin-Karp | 0.6909 | 0 | 0 |
| present | 100000 | Naive | 0.5567 | 132915 | 1 |
| present | 100000 | KMP | 0.4571 | 124696 | 1 |
| present | 100000 | Rabin-Karp | 1.0452 | 20 | 1 |
| absent | 100000 | Naive | 0.4377 | 133283 | 0 |
| absent | 100000 | KMP | 0.6479 | 125023 | 0 |
| absent | 100000 | Rabin-Karp | 1.2777 | 0 | 0 |
| worst-case | 100000 | Naive | 16.5052 | 9990100 | 0 |
| worst-case | 100000 | KMP | 0.5040 | 200098 | 0 |
| worst-case | 100000 | Rabin-Karp | 1.2276 | 0 | 0 |

### Kasai's algorithm (average of 5 runs)

| String length | Kasai time (ms) | Max LCP value |
|---|---|---|
| 1000 | 0.0146 | 9 |
| 5000 | 0.0538 | 12 |
| 10000 | 0.1831 | 13 |

