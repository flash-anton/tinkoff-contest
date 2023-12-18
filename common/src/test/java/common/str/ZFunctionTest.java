package common.str;

import common.BinSearch;
import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ZFunctionTest")
public class ZFunctionTest extends ContestChecker {
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
                abracadabra
                """, """
                0 0 0 1 0 1 0 4 0 0 1\s
                """);
    }

    @Test
    public void stressTest() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 100_000; i++) {
            int N = r.nextInt(0, 11);
            String S = str(N);

            String expected = alg1(S);
            String actual3 = alg3(S);

            assertEquals(expected, actual3, "\n" + S + "\n");
        }
    }

    @Test
    void testTL() {
        String S = str(1_000_000);
        assertTimeout(Duration.ofSeconds(2), () -> alg3(S), "alg3() is too slow");
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

        String solution = alg3(S);

        writer.write(solution);
        writer.flush();
    }

    public static String alg1(String S) {
        int N = S.length();
        int[] zf = new int[N];
        for (int i = 1; i < N; i++) { // O(N*N*LogN)
            int B = i;
            int L = N - B;
            int max = 0;
            if (S.charAt(0) == S.charAt(B)) {
                max = BinSearch.binSearchFromLeft(1, L + 1, l -> { // O(NlogN)
                    String s1 = S.substring(0, l);
                    String s2 = S.substring(B, B + l);
                    return s1.equals(s2); // O(N)
                });
            }
            zf[i] = max;
        }
        if (zf.length > 0) {
            zf[0] = 0;
        }
        return Arrays.stream(zf).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }

    public static String alg3(String S) {
        int[] zf = ZFunction.zFunction(S);
        if (zf.length > 0) {
            zf[0] = 0;
        }
        return Arrays.stream(zf).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }
}
