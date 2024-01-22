package common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinSearchTest {
    @Test
    void test() {
        int[] a = {1, 2, 8, 9};
        int L = 0;
        int R = 4;

        // [----] 4 => [--++] 2 => [++++] 0
        assertEquals(4, BinSearch.binSearchFromRight(L, R, i -> a[i] < 0));
        assertEquals(2, BinSearch.binSearchFromRight(L, R, i -> a[i] > 5));
        assertEquals(0, BinSearch.binSearchFromRight(L, R, i -> a[i] > 0));

        // [----] -1 => [++--] 1 => [++++] 3
        assertEquals(-1, BinSearch.binSearchFromLeft(L, R, i -> a[i] < 0));
        assertEquals(1, BinSearch.binSearchFromLeft(L, R, i -> a[i] < 5));
        assertEquals(3, BinSearch.binSearchFromLeft(L, R, i -> a[i] > 0));
    }
}