package common.str;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public class PrefixHashes<E> {
    private final E[] a;
    private final ToIntFunction<E> mapper;
    private final Map<Integer, Integer> xAndP;
    private final Map<Integer, Map<Integer, int[]>> prefixHashes = new HashMap<>();
    private final Map<Integer, Map<Integer, int[]>> poweredXsHashes = new HashMap<>();

    public static PrefixHashes<Character> of(String s) {
        Character[] a = IntStream.range(0, s.length()).mapToObj(s::charAt).toArray(Character[]::new);
        return new PrefixHashes<>(a, e -> e);
    }

    public PrefixHashes(E[] a, ToIntFunction<E> mapper) {
        this(a, mapper, Map.of(257, 1_000_000_007, 353, 1_000_000_009));
    }

    public PrefixHashes(E[] a, ToIntFunction<E> mapper, Map<Integer, Integer> xAndP) {
        this.a = Arrays.copyOf(a, a.length);
        this.mapper = mapper;
        this.xAndP = Map.copyOf(xAndP);
    }

    /**
     * Проверка эквивалентности подстрок одной строки за O(1)
     */
    public boolean isSubstrEqual(int offset1, int offset2, int len) {
        AtomicBoolean isEqual = new AtomicBoolean(true);
        xAndP.forEach((x_, p) -> isEqual.compareAndSet(true, isSubstrEqual(x_, p, offset1, offset2, len)));
        return isEqual.get();
    }

    /**
     * Проверка эквивалентности подстрок разных строк за O(1)
     */
    public static <E> boolean isSubstrEqual(PrefixHashes<E> ph1, PrefixHashes<E> ph2, int offset1, int offset2, int len) {
        Map<Integer, Set<Integer>> commonXAndP = new HashMap<>();
        ph1.xAndP.forEach((x_, p) -> commonXAndP.computeIfAbsent(x_, k -> new HashSet<>()).add(p));
        ph2.xAndP.forEach((x_, p) -> commonXAndP.computeIfAbsent(x_, k -> new HashSet<>()).add(p));

        AtomicBoolean isEqual = new AtomicBoolean(true);
        commonXAndP.forEach((x_, pList) -> pList.forEach(p -> {
            int[] h1 = ph1.prefixHashes(x_, p);
            int[] h2 = ph2.prefixHashes(x_, p);
            int[] x = ph1.poweredXsHashes(x_, p);
            isEqual.compareAndSet(true, isSubstrEqual(x, h1, h2, p, offset1, offset2, len));
        }));
        return isEqual.get();
    }

    private static boolean isSubstrEqual(int[] x, int[] h1, int[] h2, int p, int offset1, int offset2, int len) {
        int a1 = (int) ((h1[offset1 + len] + (long) h2[offset2] * x[len]) % p);
        int a2 = (int) ((h2[offset2 + len] + (long) h1[offset1] * x[len]) % p);
        return a1 == a2;
    }

    private boolean isSubstrEqual(int x_, int p, int offset1, int offset2, int len) {
        int[] h = prefixHashes(x_, p);
        int[] x = poweredXsHashes(x_, p);
        return isSubstrEqual(x, h, h, p, offset1, offset2, len);
    }

    private int[] prefixHashes(int x_, int p) {
        return prefixHashes.computeIfAbsent(x_, k -> new HashMap<>()).computeIfAbsent(p, k -> {
            int[] h = new int[a.length + 1];
            for (int i = 1; i < a.length + 1; i++) {
                h[i] = (int) (((long) h[i - 1] * x_ + mapper.applyAsInt(a[i - 1])) % p);
            }
            return h;
        });
    }

    private int[] poweredXsHashes(int x_, int p) {
        return poweredXsHashes.computeIfAbsent(x_, k -> new HashMap<>()).computeIfAbsent(p, k -> {
            int[] x = new int[a.length + 1];
            x[0] = 1;
            for (int i = 1; i < a.length + 1; i++) {
                x[i] = (int) (((long) x[i - 1] * x_) % p);
            }
            return x;
        });
    }
}
