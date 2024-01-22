package exam;

import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@DisplayName("АиСД 2024 - B")
class BTest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            B.alg(reader, new BufferedWriter(new OutputStreamWriter(writer)));
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
                5
                a#aba
                """, """
                1 3
                """);
    }

    @Test
    void example2() {
        check("""
                31
                You_know#how_to_solve#this_task
                """, """
                8 12
                """);
    }

    @Test
    void example3() {
        check("""
                9
                a#b##c#d#
                """, """
                0 1
                """);
    }

    static class ArgProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("0 0", "1\n#"),
                    Arguments.of("0 0", "2\n##"),
                    Arguments.of("0 1", "3\n#1#"),
                    Arguments.of("1 1", "1\n1"),
                    Arguments.of("0 1", "2\n1#"),
                    Arguments.of("0 1", "2\n#1"),
                    Arguments.of("0 2", "3\n#12"),
                    Arguments.of("1 1", "3\n1#1"),
                    Arguments.of("1 2", "4\n12#1"),
                    Arguments.of("0 1", "5\n1#1##"),
                    Arguments.of("0 2", "6\n12#1##"),
                    Arguments.of("0 1", "5\n##1#1"),
                    Arguments.of("0 2", "6\n##12#1"),
                    Arguments.of("0 2", "5\n#1#12")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ArgProvider.class)
    void test(String expected, String req) {
        check(req, expected);
    }
}