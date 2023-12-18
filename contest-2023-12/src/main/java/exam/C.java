package exam;

import java.io.*;

import static java.lang.Math.max;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=3">OK 9318045</a>
 *
 * 3 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Максим пришёл в Тинькофф, чтобы взять кредит на покупку новогодних подарков. Ему предодобрен кредит в размере m бурлей,
 * значит он может взять любое целое количество бурлей от 0 до m включительно.
 *
 * У Максима есть заранее подготовленный список из n подарков. Он планирует идти по порядку вдоль списка и каждый раз,
 * когда он видит подарок, на который у него хватает денег, он обязательно моментально его покупает.
 *
 * Помогите ему посчитать, какое максимальное количество денег у него может остаться после закупки подарков,
 * если он возьмёт кредит оптимального размера
 * (то есть такого, чтобы у него осталось как можно больше денег после покупки подарков по алгоритму).
 *
 * Формат входных данных
 * Первая строка содержит два целых числа n и m (1≤n≤10^5,1≤m≤10^9) — длина списка подарков и размер предодобренного кредита.
 * Вторая строка содержит nn целых чисел — цены подарков ai (1≤ai≤10^9).
 *
 * Формат выходных данных
 * Выведите одно число — максимальное количество бурлей, которое могло остаться у Максима после закупки подарков.
 *
 * Замечание 1
 * В первом примере Максим может взять в кредит 3 бурля, и тогда он купит только последний подарок.
 * Во втором примере Максим может взять кредит на все 10 бурлей и купить все подарки.
 *
 * Замечание 2
 * Ошибок в примерах нет.
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
        Scanner reader = new Scanner(is);
        int n = reader.nextInt();
        int m = reader.nextInt();
        for (int i = 0; i < n; i++) {
            int a = reader.nextInt();
            if (a <= m)
                m = max(m - a, a - 1);
        }

        String solution = String.valueOf(m);

        writer.write(solution);
        writer.flush();
    }

    /**
     * "Быстрый" ридер потока.
     */
    public static class Scanner {
        private final InputStream is;
        private int lastReadByte = '\n';

        public Scanner(InputStream is) {
            this.is = is;
        }

        private long nextLong() throws IOException {
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
