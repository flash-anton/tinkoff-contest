package exam;

import java.io.*;
import java.util.function.Predicate;

import static java.lang.Math.*;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exams">АиСД 2024</a>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exam/15450?task=3">OK 9812153</a>
 *
 * 3 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Мария занимается строительством города в известной мобильной игре.
 *
 * Каждый район города имеет свою целочисленную высоту над поверхностью ai.
 * В этой игре проходит ровно одна дорога, которая соединяет собой n районов этого города.
 * Дорога плавно преодолевает перепады высот в городе.
 * Например, если два соседних района i и i+1 имеют разные высоты x и y (x<y),
 * то дорога плавно поднимается от района i к району i+1.
 * Точно так же дорога может спускаться.
 *
 * Сегодня Маша узнала, что наконец может превратить дорогу в автомобильную магистраль.
 * В отличие от обычной дороги, магистраль проходит на одном и том же расстоянии от поверхности во всех районах.
 *
 * Мария хочет перестроить имеющуюся у нее дорогу в магистраль таким образом,
 * чтобы суммарный уровень дороги во всех точках не изменился.
 * То есть она хочет выбрать такую высоту магистрали,
 * чтобы суммарная площадь в разрезе на которую придется поднять некоторые участки дороги была равна суммарной площади,
 * на которую нужно будет опустить другие участки дороги.
 *
 * Помогите Маше и найдите уровень над поверхностью, на котором необходимо построить магистраль.
 *
 * Формат входных данных
 * В первой строке входных данных дано одно число n — число районов в городе (1≤n≤5*10^5).
 * Во второй строке дано n целых чисел ai — уровни районов города (0≤ai≤10^5).
 *
 * Формат выходных данных
 * Выведите единственное вещественное число — ответ на задачу.
 * Точность ответа должна быть не меньше 10^−4.
 * </pre>
 */
public class C {
    public static void main(String[] args) throws IOException {
        try (InputStream reader = new BufferedInputStream(System.in, 3_000_000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            alg(reader, writer);
        }
    }

    public static void alg(InputStream is, BufferedWriter writer) throws IOException {
        Reader reader = new Reader(is);
        int n = reader.nextInt();
        int[] a = reader.nextInts(new int[n]);

        String solution = alg1(n, a);

        writer.write(solution);
        writer.flush();
    }

    public static String alg1(int n, int[] a) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < n; i++) {
            min = min(min, a[i]);
            max = max(max, a[i]);
        }

        double delta = 0.00001;
        double level = binSearch(min, max, delta, road -> totalSquare(n, a, road) <= 0);
        return "" + level;
    }

    public static double binSearch(double min, double max, double delta, Predicate<Double> moveToMax) {
        while (max - min > delta) {
            double M = (min + max) / 2;
            if (moveToMax.test(M))
                min = M;
            else
                max = M;
        }
        return min;
    }

    public static double totalSquare(int n, int[] a, double road) {
        double square = 0;
        for (int i = 0; i < n - 1; i++) {
            double ai = road - a[i];
            double aj = road - a[i + 1];
            square += (ai + aj) / 2;
        }
        return square;
    }

    /**
     * "Быстрый" ридер потока.
     */
    public static class Reader {
        private final InputStream is;
        private int lastReadByte = '\n';

        public Reader(InputStream is) {
            this.is = is;
        }

        public long nextLong() throws IOException {
            while (lastReadByte == ' ' || lastReadByte == '\n') {
                lastReadByte = is.read();
            }

            int sign = 1;
            if (lastReadByte == '-') {
                sign = -1;
                lastReadByte = is.read();
            }

            long num = 0;
            while (lastReadByte >= '0' && lastReadByte <= '9') {
                num = (num * 10) + sign * (lastReadByte - '0');
                lastReadByte = is.read();
            }
            return num;
        }

        public long[] nextLongs(long[] a) throws IOException {
            for (int i = 0; i < a.length; i++) {
                a[i] = nextLong();
            }
            return a;
        }

        public int nextInt() throws IOException {
            return (int) nextLong();
        }

        public int[] nextInts(int[] a) throws IOException {
            for (int i = 0; i < a.length; i++) {
                a[i] = nextInt();
            }
            return a;
        }

        public String nextWord() throws IOException {
            while (lastReadByte == ' ' || lastReadByte == '\n') {
                lastReadByte = is.read();
            }

            StringBuilder sb = new StringBuilder();
            while (!(lastReadByte == -1 || lastReadByte == ' ' || lastReadByte == '\n')) {
                sb.append((char) lastReadByte);
                lastReadByte = is.read();
            }
            return sb.toString();
        }

        public String[] nextWords(String[] a) throws IOException {
            for (int i = 0; i < a.length; i++) {
                a[i] = nextWord();
            }
            return a;
        }

        public String readLine() throws IOException {
            if (lastReadByte == '\n') {
                lastReadByte = is.read();
            }

            StringBuilder sb = new StringBuilder();
            while (!(lastReadByte == -1 || lastReadByte == '\n')) {
                sb.append((char) lastReadByte);
                lastReadByte = is.read();
            }
            return sb.toString();
        }
    }
}
