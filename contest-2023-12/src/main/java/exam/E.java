package exam;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=5">OK 9330444</a>
 *
 * 5 задание
 * Ограничение времени
 * 2 секунды
 * Ограничение памяти
 * 256 МБ
 *
 * Дети из кружка Тинькофф Поколение любят считать манулов в различных чатиках.
 * В этой задаче вам тоже придётся считать манулов.
 *
 * Всего есть n детей, некоторые из которых дружат друг с другом.
 * Формально, заданы m различных неупорядоченных пар (v,u), таких что ребёнок с номером v дружит с ребёнком u.
 *
 * Изначально, i-ый ребёнок уже досчитал до ai манулов.
 * Далее происходят q событий, каждое из которых имеет один из двух видов (1≤v≤n,0≤x≤10^4):
 *     + v x — ребёнок с номером v отправляет по x стикеров с манулом каждому из своих друзей.
 *             Каждый из его друзей сразу же считает этих полученных манулов, т.е. прибавляет к своим уже посчитанным
 *     ? v — мы просим вас посчитать, сколько манулов на данный момент уже посчитал ребёнок с номером v
 *
 * Формат входных данных
 * В первой строке даны три числа n, m и q (1≤n≤10^5,0≤m≤10^5,1≤q≤3*10^5).
 * Во второй строке заданы n чисел ai (0≤ai≤10^9) — количество уже посчитанных манулов у i-го ребёнка.
 * В каждой из следующих m строк заданы два числа v, u (1≤v≠u≤n) — пары друзей.
 * Гарантируется, что пары не повторяются.
 * В каждой из следующих q строк описаны события в описанном формате.
 * Гарантируется, что будет хотя бы одно событие типа ?.
 *
 * Формат выходных данных
 * Для каждого события типа ? выведите в отдельной строке требуемое значение.
 * </pre>
 */
public class E {
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
        int q = reader.nextInt();

        long[] a = reader.nextLongs(new long[n]);

        List<List<Integer>> friends = Stream.generate(ArrayList<Integer>::new).limit(n).collect(toList());
        for (int i = 0; i < m; i++) {
            int u = reader.nextInt() - 1;
            int v = reader.nextInt() - 1;
            friends.get(u).add(v);
            friends.get(v).add(u);
        }

        StringBuilder sb = new StringBuilder();
        Map<Integer, Long> totalXByV = new HashMap<>();
        for (int i = 0; i < q; i++) {
            String type = reader.nextWord();
            int v = reader.nextInt() - 1;
            if (type.equals("+")) {
                int x = reader.nextInt();
                totalXByV.compute(v, (key, val) -> (val == null) ? x : (val+x));
                continue;
            }

            for (Integer V : totalXByV.keySet()) {
                long x = totalXByV.get(V);
                for (int friend : friends.get(V)) {
                    a[friend] += x;
                }
            }
            totalXByV = new HashMap<>();

            sb.append(a[v]).append('\n');
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
