package common.sort;

import common.ContestChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.*;
import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static common.sort.RadixSort.*;

@DisplayName("RadixSortTest")
class RadixSortTest extends ContestChecker {
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
                9
                12
                32
                45
                67
                98
                29
                61
                35
                09
                """, """
                Initial array:
                12, 32, 45, 67, 98, 29, 61, 35, 09
                **********
                Phase 1
                Bucket 0: empty
                Bucket 1: 61
                Bucket 2: 12, 32
                Bucket 3: empty
                Bucket 4: empty
                Bucket 5: 45, 35
                Bucket 6: empty
                Bucket 7: 67
                Bucket 8: 98
                Bucket 9: 29, 09
                **********
                Phase 2
                Bucket 0: 09
                Bucket 1: 12
                Bucket 2: 29
                Bucket 3: 32, 35
                Bucket 4: 45
                Bucket 5: empty
                Bucket 6: 61, 67
                Bucket 7: empty
                Bucket 8: empty
                Bucket 9: 98
                **********
                Sorted array:
                09, 12, 29, 32, 35, 45, 61, 67, 98
                """);
    }

    static class ArgProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("45", "45"),

                    Arguments.of("5 5", "5 5"),
                    Arguments.of("4 5", "4 5"),
                    Arguments.of("4 5", "5 4"),

                    Arguments.of("5 5 5", "5 5 5"),

                    Arguments.of("4 5 5", "4 5 5"),
                    Arguments.of("4 5 5", "5 4 5"),
                    Arguments.of("4 5 5", "5 5 4"),

                    Arguments.of("5 5 6", "5 5 6"),
                    Arguments.of("5 5 6", "5 6 5"),
                    Arguments.of("5 5 6", "6 5 5"),

                    Arguments.of("4 5 6", "4 5 6"),
                    Arguments.of("4 5 6", "4 6 5"),
                    Arguments.of("4 5 6", "5 4 6"),
                    Arguments.of("4 5 6", "5 6 4"),
                    Arguments.of("4 5 6", "6 4 5"),
                    Arguments.of("4 5 6", "6 5 4")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ArgProvider.class)
    void test(String expected, String req) {
        String[] s = req.split(" ");
        s = alg1(s, new DummyLogger<>());
        String actual = String.join(" ", s);
        assertEquals(expected, actual);
    }

    @Test
    public void stressTest() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 10_000; i++) {
            int n = r.nextInt(1, 11);
            int size = r.nextInt(1, 21);
            String[] s = Stream.generate(() -> num(size)).limit(n).toArray(String[]::new);

            String expected = Arrays.stream(s).sorted(comparing(BigInteger::new)).collect(joining(" "));
            s = alg1(s, new DummyLogger<>());
            String actual = String.join(" ", s);

            assertEquals(expected, actual, String.format("\n%d\n%s\n", n, String.join(" ", s)));
        }
    }

    @Test
    void testTL() {
        for (int i = 0; i < 10; i++) {
            int n = 1000;
            int size = 20;
            String[] s = Stream.generate(() -> num(size)).limit(n).toArray(String[]::new);
            Assertions.assertTimeout(Duration.ofSeconds(1), () -> alg1(s, new DummyLogger<>()), "alg1() is too slow");
        }
    }

    private static String num(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }

    private static void alg(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine().trim());
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            s[i] = reader.readLine();
        }

        Logger<String, Integer> log = new StringBuilderLogger<>();
        alg1(s, log);

        writer.write(log.toString());
        writer.flush();
    }

    private static String[] alg1(String[] s, Logger<String, Integer> log) {
        ArrayDeque<Integer> radixes = Stream.iterate(s[0].length() - 1, i -> i >= 0, i -> --i)
                .collect(toCollection(ArrayDeque::new));
        List<Integer> bucketSequence = IntStream.range(0, 10).boxed().collect(toList());

        List<String> a = radixSort(log, List.of(s), radixes::poll, (ss, radix) -> ss.charAt(radix) - '0', bucketSequence);

        return a.toArray(String[]::new);
    }
}