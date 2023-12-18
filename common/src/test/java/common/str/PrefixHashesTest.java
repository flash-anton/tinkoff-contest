package common.str;

import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PrefixHashesTest")
class PrefixHashesTest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            alg(new BufferedReader(new InputStreamReader(reader)), new BufferedWriter(new OutputStreamWriter(writer)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public BiConsumer<InputStream, OutputStream> getTaskAlgorithm() {
        return algorithm;
    }

    @Test
    void example1() {
        check("""
                acabaca
                3
                4 3 2
                3 4 0
                2 0 1
                """, """
                no
                yes
                no
                """);
    }

    @Test
    void example2() {
        check("""
                caeabaeadedcbdcdccec
                10
                13 4 3
                2 12 15
                10 1 3
                3 8 15
                13 5 6
                7 2 6
                9 8 8
                19 0 0
                19 0 0
                6 7 13
                """, """
                no
                no
                no
                no
                no
                no
                yes
                yes
                yes
                no
                """);
    }

    @Test
    public void stressTest() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 10_000; i++) {
            int N = r.nextInt(2, 11);
            String S = str(N);
            int Q = r.nextInt(1, 11);
            String[] requests = new String[Q];
            for (int j = 0; j < Q; j++) {
                int L = r.nextInt(1, N);
                int A = r.nextInt(1, (N - L) + 1);
                int B = r.nextInt(1, (N - L) + 1);
                requests[j] = String.format("%d %d %d", L, A, B);
            }

            String msg = "\n" + S + "\n" + Q + "\n" + String.join("\n", requests);

            String expected = alg1(S, Q, requests);
            String actual2 = alg2(S, Q, requests);

            String S2 = 'a' + S.substring(1);
            String actual3 = alg3(S, S2, Q, requests);

            assertAll(
                    () -> assertEquals(expected, actual2, msg),
                    () -> assertEquals(expected, actual3, msg)
            );
        }
    }

    @Test
    void testTL() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 1; i++) {
            int N = 200_000;
            String S = str(N);
            int Q = 200_000;
            String[] requests = new String[Q];
            for (int j = 0; j < Q; j++) {
                int L = r.nextInt(1, N + 1);
                int A = r.nextInt(0, (N - L) + 1);
                int B = r.nextInt(0, (N - L) + 1);
                requests[j] = String.format("%d %d %d", L, A, B);
            }

            assertAll(
                    () -> assertTimeout(Duration.ofSeconds(15), () -> alg2(S, Q, requests), "alg2() is too slow"),
                    () -> assertTimeout(Duration.ofSeconds(15), () -> alg3(S, S, Q, requests), "alg3() is too slow")
            );
        }
    }

    private static String str(int size) {
        byte[] s = new byte[size];
        for (int i = 0; i < size; i++) {
            s[i] = (byte) ('a' + ThreadLocalRandom.current().nextInt(0, 27));
        }
        return new String(s);
    }
    
    public static void alg(BufferedReader reader, BufferedWriter writer) throws IOException {
        String S = reader.readLine();
        int Q = Integer.parseInt(reader.readLine().trim());
        String[] requests = new String[Q];
        for (int i = 0; i < Q; i++) {
            requests[i] = reader.readLine().trim();
        }

        String solution = alg2(S, Q, requests);

        writer.write(solution);
        writer.flush();
    }

    public static String alg1(String S, int Q, String[] requests) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) { // O (Q * N)
            int[] ints = Arrays.stream(requests[i].split(" ")).mapToInt(Integer::valueOf).toArray();
            int L = ints[0];
            int A = ints[1];
            int B = ints[2];
            String sA = S.substring(A, A + L);
            String sB = S.substring(B, B + L);
            String s = sA.equals(sB) ? "yes" : "no";// O(L) -> O(N)
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public static String alg2(String S, int Q, String[] requests) {
        StringBuilder sb = new StringBuilder();
        PrefixHashes<Character> ph = PrefixHashes.of(S);
        for (int i = 0; i < Q; i++) { // O (Q + N)
            int[] ints = Arrays.stream(requests[i].split(" ")).mapToInt(Integer::valueOf).toArray();
            int L = ints[0];
            int A = ints[1];
            int B = ints[2];
            String s = ph.isSubstrEqual(A, B, L) ? "yes" : "no"; // first: O(L)->O(N), other: O(1)
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public static String alg3(String S, String S2, int Q, String[] requests) {
        StringBuilder sb = new StringBuilder();
        PrefixHashes<Character> ph1 = PrefixHashes.of(S);
        PrefixHashes<Character> ph2 = PrefixHashes.of(S2);
        for (int i = 0; i < Q; i++) { // O (Q + N)
            int[] ints = Arrays.stream(requests[i].split(" ")).mapToInt(Integer::valueOf).toArray();
            int L = ints[0];
            int A = ints[1];
            int B = ints[2];
            String s = PrefixHashes.isSubstrEqual(ph1, ph2, A, B, L) ? "yes" : "no"; // first: O(L)->O(N), other: O(1)
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}