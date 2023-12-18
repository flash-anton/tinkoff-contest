package exam;

import java.io.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=6">Не отправлен</a>
 *
 * 6 задание
 * Ограничение времени
 * 3 секунды
 * Ограничение памяти
 * 256 МБ
 *
 * Поздравляю, вы дошли до серьёзной задачи, больше никаких легенд, только хардкор.
 *
 * Дан массив a из n целых чисел. Требуется выполнить q запросов такого вида (1≤l≤r≤n,0≤k,b,x≤10^9):
 *     + l r x — прибавить x ко всем ai на отрезке i∈[l,r]
 *     ? l r k b — вывести max из min(ai,k*i+b), где l≤i≤r
 *
 * Формат входных данных
 * В первой строке заданы два числа n, q (1≤n≤2*10^5,1≤q≤5*10^5).
 * Во второй строке задан массив a (0≤ai≤10^9).
 * Следующие q строк содержат запросы в заданном формате.
 * Гарантируется, что будет хотя бы один запрос типа ?.
 *
 * Формат выходных данных
 * Для каждого запроса типа ? выведите ответ в отдельной строке.
 * </pre>
 */
public class F {
    public static void main(String[] args) throws IOException {
        try (InputStream reader = new BufferedInputStream(System.in, 3_000_000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            alg(reader, writer);
        }
    }

    public static void alg(InputStream is, BufferedWriter writer) throws IOException {
        Scanner reader = new Scanner(is);
        int n = reader.nextInt();
        int q = reader.nextInt();
        long[] a = reader.nextLongs(new long[n]); // ai <= 10^9 + q(5*10^5) * x(10^9) ~= 5*10^14

        StringBuilder sb = new StringBuilder();

        // O(q*n) = O(5*10^5 * 2*10^5) = O(100*10^9) - медленно
        long[] kib = new long[n + 1];
        long[] delta = new long[n + 1];
        int deltaLInclusive = n;
        int deltaRExclusive = 0;
        for (int i = 0; i < q; i++) { // O(q)
            char type = reader.nextWord().charAt(0);
            int l = reader.nextInt();
            int r = reader.nextInt();
            if (type == '+') {
                int x = reader.nextInt();
                delta[l - 1] += x;
                delta[r] -= x;
                deltaLInclusive = min(deltaLInclusive, l - 1);
                deltaRExclusive = max(deltaRExclusive, r);
                continue;
            }

            int k = reader.nextInt();
            int b = reader.nextInt();
            f1(l, r, k, b, kib); // O(n)

            f2(a, delta, deltaLInclusive, deltaRExclusive); // O(n)
            delta = new long[n + 1];
            deltaLInclusive = n;
            deltaRExclusive = 0;

            long max = f3(l, r, a, kib); // O(n)
            sb.append(max).append('\n');
        }

        // O(q*log(n)+n) = O(5*10^5 * log(2*10^5) + 2*10^5) ~= O(10^7) - норм на дереве отрезков

        String solution = sb.toString();

        writer.write(solution);
        writer.flush();
    }

    public static long f3(int l, int r, long[] a, long[] kib) {
        long max = 0;
        for (int i = l - 1; i < r; i++)
            max = max(max, min(a[i], kib[i]));
        return max;
    }

    public static void f2(long[] a, long[] delta, int deltaLInclusive, int deltaRExclusive) {
        long D = 0;
        for (int i = deltaLInclusive; i < deltaRExclusive; i++) {
            D += delta[i];
            a[i] += D;
        }
    }

    public static void f1(int l, int r, int k, int b, long[] kib) {
        kib[l - 1] = (long) k * l + b;
        for (int i = l; i < r; i++)
            kib[i] = kib[i - 1] + k;
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
