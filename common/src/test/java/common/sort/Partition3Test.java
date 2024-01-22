package common.sort;

import common.BinSearch;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Partition3Test {
    @Test
    public void stressTest() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        Comparator<Integer> c = Comparator.naturalOrder();
        for (int i = 0; i < 10_000; i++) {
            int n = r.nextInt(1, 11);
            Integer[] arr = r.ints(n).boxed().toArray(Integer[]::new);

            Integer pivot = arr[r.nextInt(0, n)];

            Integer[] a = Arrays.copyOf(arr, n);
            Arrays.sort(a);
            int[] expectedEG = {
                    BinSearch.binSearchFromLeft(0, n, j -> a[j] < pivot) + 1,
                    BinSearch.binSearchFromRight(0, n, j -> a[j] > pivot)
            };

            Integer[] b = Arrays.copyOf(arr, n);
            int[] actual1EG = Partition3.partition3(c, b, 0, n, pivot);

            List<Integer> list = Arrays.asList(arr);
            int[] actual2EG = Partition3.partition3(c, list, pivot);

            assertAll(
                    () -> assertArrayEquals(expectedEG, actual1EG),
                    () -> assertArrayEquals(expectedEG, actual2EG)
            );
        }
    }
}
