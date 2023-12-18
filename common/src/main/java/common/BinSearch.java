package common;

import java.util.function.Predicate;

public class BinSearch {
    /**
     * Бинарный поиск справа налево. O(logN)<br>
     * [----] 4 => [--++] 2 => [++++] 0
     */
    public static int binSearchFromRight(int LInclusive, int RExclusive, Predicate<Integer> moveToLeft) {
        while (LInclusive < RExclusive) {
            int M = (LInclusive + RExclusive) / 2;
            if (moveToLeft.test(M)) {
                RExclusive = M;
            } else {
                LInclusive = M + 1;
            }
        }
        return LInclusive;
    }

    /**
     * Бинарный поиск слева направо. O(logN)<br>
     * [----] -1 => [++--] 1 => [++++] 3
     */
    public static int binSearchFromLeft(int LInclusive, int RExclusive, Predicate<Integer> moveToRight) {
        while (LInclusive < RExclusive) {
            int M = (LInclusive + RExclusive) / 2;
            if (moveToRight.test(M)) {
                LInclusive = M + 1;
            } else {
                RExclusive = M;
            }
        }
        return LInclusive - 1;
    }
}
