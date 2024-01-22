package exam;

import java.io.*;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exams">АиСД 2024</a>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exam/15450?task=1">OK 9808758</a>
 *
 * 1 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Саша и Дима собирают робота, который смог бы работать без помощи человека.
 * Для этого им необходимо обучить ИИ в голове робота.
 * Ребята решили начать с простого — научить робота определять результат одного хода в игре дартс.
 *
 * Напомним правила в этой игре. За один подход игрок делает 3 броска дротика в сторону мишени.
 * Для простоты будем считать, что мишень имеет форму круга с радиусом 1 и центром в точке (0,0)(0,0).
 * За каждый из трех бросков игроку начисляются очки.
 * • Если дротик попадает в точку на расстоянии не больше 0.1 от центра, к счету игрока добавляется три очка.
 * • Если дротик попадает в точку на расстоянии больше 0.1, но не больше 0.8 от центра, к счету игрока добавляется два очка.
 * • Если дротик попадает в точку на расстоянии больше 0.8, но не больше 1 от центра, к счету игрока добавляется одно очко.
 * • Если дротик не попадает в мишень, счет игрока не меняется.
 *
 * Вам даны координаты попадания дротиков в результате трех бросков.
 * Найдите суммарное число очков, которое было начислено в результате этого подхода.
 *
 * Формат входных данных
 * В трех строках входных данных даны пары вещественных чисел xi,yi — координаты попаданий дротиков (−2≤x1,y1,x2,y2,x3,y3≤2).
 *
 * Формат выходных данных
 * В качестве ответа выведите одно число — сумму очков, начисленную в результате трех бросков.
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
        Reader reader = new Reader(is);

        int result = 0;
        for (int i = 0; i < 3; i++) {
            double x = Double.parseDouble(reader.nextWord());
            double y = Double.parseDouble(reader.nextWord());

            double x2y2 = Math.round(100.0 * (x * x + y * y)) / 100.0;
            if (x2y2 <= 0.01)
                result += 3;
            else if (x2y2 <= 0.64)
                result += 2;
            else if (x2y2 <= 1)
                result += 1;
        }

        String solution = "" + result;

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
