package exam;

import java.io.*;
import java.util.Arrays;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=1">OK 9310114</a>
 *
 * 1 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Для вывески нового офиса Тинькофф были заказаны неоновые буквы.
 *
 * В офис привезли какой-то набор из больших латинских букв.
 * Проверьте, что из них действительно можно составить строку «TINKOFF» для вывески.
 * Тинькофф не хочет платить за лишние буквы, поэтому должны быть использованы все привезённые буквы.
 *
 * Формат входных данных
 * Каждый тест состоит из нескольких наборов входных данных.
 * В первой строке находится одно целое число t (1≤t≤100) — количество наборов входных данных.
 * Далее следует описание наборов входных данных.
 * Единственная строка каждого набора входных данных содержит одну непустую строку из больших латинских букв длиной
 * не более 20 символов — привезённый набор букв.
 *
 * Формат выходных данных
 * Для каждого набора входных данных, в отдельной строке, выведите «Yes» (без кавычек),
 * если из всех привезённых букв можно составить строку «TINKOFF», и «No» иначе.
 * Вы можете выводить каждую букву в любом регистре (строчную или заглавную).
 * Например, строки «yEs», «yes», «Yes» и «YES» будут приняты как положительный ответ..
 * </pre>
 */
public class A {
    public static void main(String[] args) throws IOException {
        try (InputStream reader = new BufferedInputStream(System.in, 3_000_000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            alg(reader, writer);
        }
    }

    public static void alg(InputStream is, BufferedWriter writer) throws IOException {

        StringBuilder sb = new StringBuilder();

        int[] TINKOFF = f("TINKOFF");

        Scanner reader = new Scanner(is);
        int t = reader.nextInt();
        for (int i = 0; i < t; i++) {
            int[] word = f(reader.nextWord());
            boolean eq = Arrays.equals(TINKOFF, word);
            sb.append(eq ? "Yes" : "No").append('\n');
        }

        String solution = sb.toString();

        writer.write(solution);
        writer.flush();
    }

    public static int[] f(String s) {
        int[] b = new int[26];
        for (byte aByte : s.getBytes())
            b[aByte - 'A']++;
        return b;
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
