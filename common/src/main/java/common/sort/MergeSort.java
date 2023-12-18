package common.sort;

import java.util.Comparator;
import java.util.ListIterator;

import static java.util.Objects.nonNull;

public class MergeSort {
    /**
     * Сортировка массива слиянием (устойчивая).
     */
    public static <E> void mergeSort(Comparator<E> cmp, E[] a, int aL, int aR, E[] c, int cL) {
        if (aR - aL > 1) {
            int M = (aL + aR) / 2;
            mergeSort(cmp, a, aL, M, c, cL);
            mergeSort(cmp, a, M, aR, c, cL);
            merge(cmp, a, aL, M, a, M, aR, c, cL);
            System.arraycopy(c, cL, a, aL, aR - aL);
        }
    }

    /**
     * Сортировка связного списка слиянием (устойчивая).
     */
    public static <E> void mergeSort(Comparator<E> cmp, ListIterator<E> aL1, ListIterator<E> aR1,
                                     ListIterator<E> aL2, ListIterator<E> aR2, ListIterator<E> cL) {
        if (aR1.nextIndex() - aL1.nextIndex() > 1) {
            int l = aL1.nextIndex();
            int r = aR1.nextIndex();
            int m = (l + r) / 2;

            int cl = cL.nextIndex();
            int cr = cl + (r - l);

            mergeSort(cmp, move(aL1, l), move(aR1, m), move(aL2, l), move(aR2, m), move(cL, cl));
            mergeSort(cmp, move(aL1, m), move(aR1, r), move(aL2, m), move(aR2, r), move(cL, cl));
            merge(cmp, move(aL1, l), move(aR1, m), move(aL2, m), move(aR2, r), move(cL, cl));

            move(aL1, l);
            move(cL, cl);

            E previous;
            while (nonNull(previous = next(cL, cr))) {
                nextThenSet(aL1, previous);
            }
        }
    }

    // =================================================================================================================

    /**
     * Слияние (устойчивое) двух упорядоченных массивов в один упорядоченный.
     */
    public static <E> void merge(Comparator<E> cmp, E[] a, int aL, int aR, E[] b, int bL, int bR, E[] c, int cL) {
        while (aL < aR || bL < bR) {
            int r;
            if (aL < aR && bL < bR) {
                r = cmp.compare(a[aL], b[bL]);
            } else {
                r = aL < aR ? -1 : 1;
            }

            if (r <= 0) {
                c[cL++] = a[aL++];
            } else {
                c[cL++] = b[bL++];
            }
        }
    }

    /**
     * Слияние (устойчивое) двух упорядоченных списков в один упорядоченный.
     */
    public static <E> void merge(Comparator<E> cmp, ListIterator<E> aL, ListIterator<E> aR,
                                 ListIterator<E> bL, ListIterator<E> bR, ListIterator<E> cL) {
        int ar = aR.nextIndex();
        int br = bR.nextIndex();

        E aPrevious = next(aL, ar);
        E bPrevious = next(bL, br);
        while (nonNull(aPrevious) || nonNull(bPrevious)) {
            int r;
            if (nonNull(aPrevious) && nonNull(bPrevious)) {
                r = cmp.compare(aPrevious, bPrevious);
            } else {
                r = nonNull(aPrevious) ? -1 : 1;
            }

            if (r <= 0) {
                nextThenSet(cL, aPrevious);
                aPrevious = next(aL, ar);
            } else {
                nextThenSet(cL, bPrevious);
                bPrevious = next(bL, br);
            }
        }
    }

    // =================================================================================================================

    /**
     * Перемещение итератора к следующему элементу и получение его значения (null при достижении границы).
     */
    public static <E> E next(ListIterator<E> it, int nextIndexBound) {
        boolean hasNext = it.hasNext() && it.nextIndex() < nextIndexBound;
        return hasNext ? it.next() : null;
    }

    /**
     * Перемещение итератора к следующему элементу и изменение его значения.
     */
    public static <E> void nextThenSet(ListIterator<E> it, E e) {
        it.next();
        it.set(e);
    }

    /**
     * Перемещение итератора до nextIndex.
     */
    public static <E> ListIterator<E> move(ListIterator<E> it, int nextIndex) {
        while (it.nextIndex() < nextIndex) {
            it.next();
        }
        while (it.nextIndex() > nextIndex) {
            it.previous();
        }
        return it;
    }
}
