package exam;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exams">АиСД 2024</a>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exam/15450?task=4">OK 9812878</a>
 *
 * 4 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Иван очень любит читать. Раз в месяц он приходит в библиотеку и берет много книг, чтобы прочитать их за следующий месяц.
 *
 * Чтобы не забыть никакую книгу, Ваня ведет список номеров книг, которые он хотел бы прочитать.
 * Этот список пополняется достаточно хаотично, поэтому номера книг в этом списке могут повторяться.
 *
 * Перед очередным походом в библиотеку Иван открыл список и увидел в нем n записей.
 * Ваня боится, что в библиотеке его не примут с таким списком.
 * Поэтому он хочет получить отсортированный по номерам список минимальной длины, содержащий, возможно в неявном виде,
 * в точности только желаемые книги.
 *
 * Для достижения этой цели Иван может использовать следующее сокращение:
 * если он хочет взять книги с номерами x,x+1,…,y−1,y, то он может записать в список всего 3 записи:
 * x,…,y, что будет означить, что ему нужны книги с x по y.
 *
 * Помогите Ване получить желаемый список.
 *
 * Формат входных данных
 * В первой строке входного файла дано одно целое число n — число записей в исходном списке книг (1≤n≤2*105).
 * Во второй строке дано n чисел ai — элементы исходного списка книг (1≤ai≤10^9).
 *
 * Формат выходных данных
 * Выведите список книг с минимальным числом элементов такой, что в нем будут в точности все желаемые книги.
 * Если существует несколько списков с минимальным числом элементов,
 * выведите список с наименьшим числом номеров книг, указанных явно.
 *
 * Замечание
 * В третьем тесте из условия список «1 3 ... 5 9 ... 11» является ответом,
 * так как содержит минимальное число номеров книг, тогда как «1 3 ... 5 9 10 11» ответом не является.
 * </pre>
 */
public class D {
    public static void main(String[] args) throws IOException {
        try (InputStream reader = new BufferedInputStream(System.in, 3_000_000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            alg(reader, writer);
        }
    }

    public static void alg(InputStream is, BufferedWriter writer) throws IOException {
        Reader reader = new Reader(is);
        int n = reader.nextInt();
        Set<Integer> a = new TreeSet<>();
        for (int i = 0; i < n; i++)
            a.add(reader.nextInt());

        StringBuilder sb = new StringBuilder();

        Iterator<Integer> it = a.iterator();
        int begin = it.next();
        sb.append(begin);
        int end = begin;

        while (it.hasNext()) {
            int nextBegin = it.next();
            if (nextBegin - end > 1) {
                if (end - begin == 1) {
                    sb.append(' ').append(end);
                } else if (end - begin > 1) {
                    sb.append(" ... ").append(end);
                }
                sb.append(' ').append(nextBegin);
                begin = nextBegin;
            }
            end = nextBegin;
        }

        if (end - begin == 1) {
            sb.append(' ').append(end);
        } else if (end - begin > 1) {
            sb.append(" ... ").append(end);
        }

        String solution = sb.toString();

        writer.write(solution);
        writer.flush();
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
