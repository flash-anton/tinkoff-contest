package exam;

import common.ContestChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.function.BiConsumer;

@DisplayName("АиСД 2024 - C")
class CTest extends ContestChecker {
    private static final BiConsumer<InputStream, OutputStream> algorithm = (reader, writer) -> {
        try {
            C.alg(reader, new BufferedWriter(new OutputStreamWriter(writer)));
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
                3
                1 2 3
                """, """
                2.0
                """);
    }

    @Test
    void example2() {
        check("""
                4
                1 1 1 1
                """, """
                1.0
                """);
    }

    @Test
    void example3() {
        check("""
                4
                0 1 1 0
                """, """
                0.6666641235351562
                """); // actually 0.6666666666666666
    }
}