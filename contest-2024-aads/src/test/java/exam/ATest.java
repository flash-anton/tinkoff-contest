package exam;

import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.function.BiConsumer;

@DisplayName("АиСД 2024 - A")
class ATest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            A.alg(reader, new BufferedWriter(new OutputStreamWriter(writer)));
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
                0.0 0.0
                0.1 0.0
                0.5 0.5
                """, """
                8
                """);
    }

    @Test
    void example2() {
        check("""
                0.5 0.5
                2.0 1.0
                0.05 0.05
                """, """
                5
                """);
    }

    @Test
    void example3() {
        check("""
                0.0 0.1
                0.0 0.8
                0.0 1.0
                """, """
                6
                """);
    }
}