package exam;

import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.function.BiConsumer;

@DisplayName("АиСД 2024 - D")
class DTest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            D.alg(reader, new BufferedWriter(new OutputStreamWriter(writer)));
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
                7
                1 3 4 5 7 8 12
                """, """
                1 3 ... 5 7 8 12
                """);
    }

    @Test
    void example2() {
        check("""
                6
                30 30 20 11 12 1
                """, """
                1 11 12 20 30
                """);
    }

    @Test
    void example3() {
        check("""
                8
                9 11 10 1 3 5 4 4
                """, """
                1 3 ... 5 9 ... 11
                """);
    }
}