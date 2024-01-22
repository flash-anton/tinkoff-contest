package exam;

import java.io.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exams">АиСД 2024</a>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exam/15450?task=2">OK 9809799</a>
 *
 * 2 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Павел обучает свою модель машинного обучения для генерации текста.
 * Обучение искусственного интеллекта — очень непростая задача,
 * однако наш герой уже добился каких-то результатов и теперь хочет оценить качество текстов, которые у него получаются.
 *
 * Паша пока не придумал хорошего способа оценить получающиеся тексты,
 * поэтому для каждого из них он хочет узнать две простые характеристики — длину минимальной и максимальной строки в полученном тексте.
 *
 * Помогите начинающему исследователю и напишите программу, которая будет находить необходимые характеристики для сгенерированного текста.
 *
 * Формат входных данных
 * В первой строке входного файла дано одно целое число n — длина сгенерированного текста (1≤n≤10^6).
 * Во второй строке записан сгенерированный текст, состоящий из строчных и заглавных букв латинского алфавита,
 * нижнего подчеркивания и символа «#».
 * Символ «#» используется в качестве символа переноса строки.
 *
 * Формат выходных данных
 * В одной строке выведите два целых числа — минимальную и максимальную длину строки в тексте.
 *
 * Система оценки
 * Баллы за каждую подзадачу начисляются только в случае, если все тесты этой подзадачи и необходимых подзадач успешно пройдены.
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
        Reader reader = new Reader(is);
        int n = reader.nextInt();
        char[] line = reader.nextWord().toCharArray();

        int min = Integer.MAX_VALUE;
        int max = 0;
        int L = 0;
        for (int R = 0; R < n; R++) {
            char c = line[R];
            if (c == '#') {
                int size = R - L;
                min = min(min, size);
                max = max(max, size);
                L = R;
                L++;
            }
        }

        if (L == n) {
            min = 0;
        } else {
            int size = n - L;
            min = min(min, size);
            max = max(max, size);
        }

        String solution = min + " " + max;

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
