package common.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.util.stream.Collectors.joining;

public class RadixSort {
    /**
     * Поразрядная (посвойственная) сортировка элементов (устойчивая) с логированием операций.
     */
    public static <E, R> List<E> radixSort(Logger<E, R> log,
                                           List<E> a, Supplier<Integer> radixSupplier, BiFunction<E, Integer, R> toBucket,
                                           List<R> bucketSequence) {
        log.initialArray(a);

        Integer radix;
        while ((radix = radixSupplier.get()) != null) {
            a = phase(log, a, radix, toBucket, bucketSequence);
        }

        log.sortedArray(a);

        return a;
    }

    /**
     * Разложение элементов по корзинам, соответствующим указанному разряду (свойству) элемента.
     */
    public static <E, R> Map<R, List<E>> parse(List<E> a, Integer radix, BiFunction<E, Integer, R> toBucket) {
        Map<R, List<E>> buckets = new HashMap<>();
        for (E e : a) {
            R bucket = toBucket.apply(e, radix);
            buckets.computeIfAbsent(bucket, k -> new ArrayList<>()).add(e);
        }
        return buckets;
    }

    /**
     * Слияние элементов из корзин в единый список, с сохранением взаимного порядка.
     */
    public static <E, R> List<E> merge(Map<R, List<E>> buckets, List<R> bucketSequence) {
        List<E> a = new ArrayList<>();
        for (R bucket : bucketSequence) {
            a.addAll(buckets.getOrDefault(bucket, List.of()));
        }
        return a;
    }

    /**
     * Фаза разложения элементов по корзинам и их последующего слияния для одного разряда (свойства) элемента.
     */
    public static <E, R> List<E> phase(Logger<E, R> log,
                                       List<E> a, Integer radix, BiFunction<E, Integer, R> toBucket,
                                       List<R> bucketSequence) {
        Map<R, List<E>> buckets = parse(a, radix, toBucket);
        log.phase(buckets, bucketSequence);
        return merge(buckets, bucketSequence);
    }

    // =================================================================================================================

    /**
     * Интерфейс логера операций алгоритма.
     */
    public interface Logger<E, R> {
        void initialArray(List<E> a);

        void phase(Map<R, List<E>> buckets, List<R> bucketSequence);

        void sortedArray(List<E> a);
    }

    /**
     * Заглушка логера операций алгоритма.
     */
    public static class DummyLogger<E, R> implements Logger<E, R> {
        @Override
        public void initialArray(List<E> a) {
        }

        @Override
        public void phase(Map<R, List<E>> buckets, List<R> bucketSequence) {
        }

        @Override
        public void sortedArray(List<E> a) {
        }
    }

    /**
     * Логер операций алгоритма, записывающий лог в {@link StringBuilder}.
     */
    public static class StringBuilderLogger<E, R> implements Logger<E, R> {
        private final StringBuilder sb = new StringBuilder();
        private int phase = 0;

        @Override
        public void initialArray(List<E> a) {
            sb.append("Initial array:\n");
            String s = a.stream().map(E::toString).collect(joining(", "));
            sb.append(s).append("\n");
        }

        @Override
        public void phase(Map<R, List<E>> buckets, List<R> bucketSequence) {
            sb.append("**********\n");
            sb.append("Phase ").append(++phase).append("\n");
            for (R bucket : bucketSequence) {
                List<E> list = buckets.getOrDefault(bucket, List.of());
                String s = list.isEmpty() ? "empty" : list.stream().map(E::toString).collect(joining(", "));
                sb.append("Bucket ").append(bucket).append(": ").append(s).append("\n");
            }
        }

        @Override
        public void sortedArray(List<E> a) {
            sb.append("**********\n");
            sb.append("Sorted array:\n");
            String s = a.stream().map(E::toString).collect(joining(", "));
            sb.append(s);
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}
