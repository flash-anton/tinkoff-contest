package common.sort;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class Partition3 {
    /**
     * Перестановка (неустойчивая) элементов массива: слева элементы меньше опорного, справа - больше.
     * Возвращает индекс первого элемента, равного опорному, и первого элемента больше опорного.
     */
    public static <E> int[] partition3(Comparator<E> c, E[] a, int L, int R, E pivot) {
        int LEqual = L;
        int LGreater = L;
        for (int i = L; i < R; i++) {
            E e = a[i];
            int r = c.compare(e, pivot);
            if (r == 0) {
                a[i] = a[LGreater];
                a[LGreater++] = e;
            } else if (r < 0) {
                a[i] = a[LGreater];
                a[LGreater++] = a[LEqual];
                a[LEqual++] = e;
            }
        }
        return new int[]{LEqual, LGreater};
    }

    /**
     * Перестановка (неустойчивая) элементов связного списка: слева элементы меньше опорного, справа - больше.
     * Возвращает индекс первого элемента, равного опорному, и первого элемента больше опорного.
     */
    public static <E> int[] partition3(Comparator<E> c, List<E> a, E pivot) {
        ListIterator<E> RSmaller = a.listIterator();
        ListIterator<E> REqual = a.listIterator();
        for (ListIterator<E> i = a.listIterator(); i.hasNext(); ) {
            E e = i.next();
            int r = c.compare(e, pivot);
            if (r == 0) {
                i.set(REqual.next());
                REqual.set(e);
            } else if (r < 0) {
                i.set(REqual.next());
                REqual.set(RSmaller.next());
                RSmaller.set(e);
            }
        }
        return new int[]{RSmaller.nextIndex(), REqual.nextIndex()};
    }
}
