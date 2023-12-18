package exam;

import java.io.*;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=2">OK 9312438</a>
 *
 * 2 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Тинькофф начал разрабатывать новый проект. Для этого было подобрано n разработчиков. У i-го разработчика есть
 * порог социальности ai, это значит, что он готов контактировать напрямую с не более чем ai другими разработчиками.
 *
 * Определите, можно ли наладить контакт между какими-то парами разработчиков,
 * так чтобы любые два контактировали либо напрямую, либо через других разработчиков.
 *
 * Формат входных данных
 * Каждый тест состоит из нескольких наборов входных данных.
 * В первой строке находится одно целое число t (1≤t≤1000) — количество наборов входных данных.
 * Далее следует описание наборов входных данных.
 *
 * Первая строка каждого набора входных данных содержит одно число n (1≤n≤10^5) — количество разработчиков.
 * Вторая строка содержит nn натуральных чисел ai (1≤ai≤10^9) — пороги социальностей разработчиков.
 * Гарантируется, что сумма значений nn по всем наборам входных данных не превосходит 10^5.
 *
 * Формат выходных данных
 * Для каждого набора входных данных, выведите «Yes», если можно наладить контакт между программистами, и «NoNo» иначе.
 * Вы можете выводить каждую букву в любом регистре (строчную или заглавную).
 * Например, строки «yEs», «yes», «Yes» и «YES» будут приняты как положительный ответ.
 * </pre>
 */
public class B {
    public static void main(String[] args) throws IOException {
        try (InputStream reader = new BufferedInputStream(System.in, 3_000_000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            alg(reader, writer);
        }
    }

    public static void alg(InputStream is, BufferedWriter writer) throws IOException {

        StringBuilder sb = new StringBuilder();

        Scanner reader = new Scanner(is);
        int t = reader.nextInt();
        for (int i = 0; i < t; i++) {
            int n = reader.nextInt();
            long edgeSum = 0;
            for (int j = 0; j < n; j++)
                edgeSum += reader.nextInt();
            boolean yes = edgeSum >= (n - 1) * 2L;
            sb.append(yes ? "Yes" : "No").append('\n');
        }

        String solution = sb.toString();

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
