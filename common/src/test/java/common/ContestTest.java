package common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ContestTest")
class ContestTest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            Contest.alg(reader, new BufferedWriter(new OutputStreamWriter(writer)));
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
                0
                1 1
                5
                6 6
                """, """
                11
                """);
    }

    @Test
    void example2() {
        check("""
                0
                2 1
                5 5
                6 6
                """, """
                12
                """);
    }

    @Test
    void example3() {
        check("""
                0
                2 2
                5 5
                6 6
                7 7
                """, """
                22
                """);
    }

    static class ArgProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("11", "0 \n 1 1 \n 5   \n 6 6"),
                    Arguments.of("12", "0 \n 2 1 \n 5 5 \n 6 6"),
                    Arguments.of("22", "0 \n 2 2 \n 5 5 \n 6 6 \n 7 7")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ArgProvider.class)
    void test(String expected, String req) {
        check(req, expected);
    }

    @Test
    public void stressTest() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 100_000; i++) {
            int N = r.nextInt(1, 11);
            int M = r.nextInt(1, 11);
            int[] a = r.ints(N, 1, 1001).toArray();
            long[][] req = new long[M][2];
            for (int j = 0; j < M; j++) {
                req[j][0] = r.nextInt(N);
                req[j][1] = r.nextInt((int) req[j][0], N);
            }

            String expected = Contest.alg1(a, req);
            String actual = Contest.alg2(a, req);

            assertEquals(expected, actual, () -> {
                StringBuilder sb = new StringBuilder("\n");
                sb.append(N).append(" ").append(M).append("\n");
                sb.append(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "))).append("\n");
                for (long[] re : req) {
                    sb.append(re[0]).append(" ").append(re[1]).append("\n");
                }
                return sb.toString();
            });
        }
    }

    @Test
    void testTL() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 100; i++) {
            int N = 100;
            int M = 1000;
            int[] a = r.ints(N, 1, 1001).toArray();
            long[][] req = new long[M][2];
            for (int j = 0; j < M; j++) {
                req[j][0] = r.nextInt(N);
                req[j][1] = r.nextInt((int) req[j][0], N);
            }

            assertAll(
                    () -> assertTimeout(Duration.ofSeconds(1), () -> Contest.alg1(a, req), "alg1() is too slow"),
                    () -> assertTimeout(Duration.ofSeconds(1), () -> Contest.alg2(a, req), "alg2() is too slow")
            );
        }
    }
}