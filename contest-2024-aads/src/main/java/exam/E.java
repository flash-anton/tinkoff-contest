package exam;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exams">АиСД 2024</a>
 * <a href="https://edu.tinkoff.ru/selection/06554828-ee2f-70b8-8000-e6e9e3a3546e/exam/15450?task=5">OK 9813739</a>
 *
 * 5 задание
 * Ограничение времени
 * 2 секунды
 * Ограничение памяти
 * 256 МБ
 *
 * Ирина работает в компании, которая занимается доставкой интернет заказов.
 *
 * У этой компании доставка осуществляется через n пунктов доставки.
 * Пункт с номером 1 — склад, все товары начинают свой путь оттуда.
 *
 * Между некоторыми пунктами доставки курсируют автомобили, которые могут доставить посылку из одного пункта в другой или наоборот.
 * В каждый пункт приезжает не более 100 автомобилей, причем каждому из них дан номер от 1 до 100.
 * У каждого из автомобилей, которые приезжают в пункт доставки x, номер среди автомобилей, приезжающих в пункт x, уникален,
 * однако не обязательно уникален среди всех автомобилей.
 * Могут быть пункты x и y, между которыми курсирует больше одного автомобиля, причем номера этих автомобилей будут разными.
 * Однако не может быть такого, что автомобиль перевозит посылки из пункта x в пункт x.
 *
 * Ира заметила, что некоторые посылки теряются и не доходят до пункта назначения, поэтому она решила рассмотреть то,
 * как будет перемещаться между пунктами доставки одна из посылок.
 * Ирине известна последовательность номеров автомобилей на которых должна перемещаться посылка, начиная свой путь в пункт 1.
 * Проверьте, сможет ли эта посылка преодолеть свой путь целиком и попасть в конченый пункт доставки,
 * или она однажды попадет в такой пункт в который не приезжает автомобиль с необходимым номером.
 *
 * Формат входных данных
 * В первой строке входных данных дано три числа n,m,k — количество пунктов доставки, количество курсирующих автомобилей
 * и длина пути посылки, который хочет рассмотреть Ирина (1≤n≤5*10^4,1≤m≤2*10^5,1≤k≤2*10^5).
 * В следующих m строках дано описание автомобилей, курсирующих между пунктами доставки.
 * Автомобиль описывается тремя числами ui,di,vi — автомобиль курсирует между пунктами ui,vi и он имеет номер di (1≤ui,vi≤n,ui≠vi;1≤di≤100).
 * Гарантируется, что в пункт ui будет приезжать единственный автомобиль с номером di.
 * Аналогичное утверждение верно и про пункт vi.
 * Последняя строка входных данных содержит k целых чисел ai (1≤ai≤100) - описание пути посылки,
 * которую хочет отследить Ирина, а именно последовательность номеров автомобилей на которых должна перемещаться посылка.
 *
 * Формат выходных данных
 * В качестве ответа выведите единственное целое число — номер пункта в котором она окажется посылка,
 * если маршрут посылки можно совершить целиком, используя описанные автомобили,
 * или число 0, если довести посылку до конца ее маршрута невозможно.
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
        Reader reader = new Reader(is);
        int n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();

        Place[] places = new Place[n + 1];
        for (int i = 1; i < n + 1; i++) {
            places[i] = new Place(i);
        }

        for (int i = 0; i < m; i++) {
            int u = reader.nextInt();
            int d = reader.nextInt();
            int v = reader.nextInt();
            places[u].autos.computeIfAbsent(v, key -> new HashSet<>()).add(d);
            places[v].autos.computeIfAbsent(u, key -> new HashSet<>()).add(d);
        }

        Deque<Integer> track = new ArrayDeque<>(k);
        for (int i = 0; i < k; i++) {
            track.offer(reader.nextInt());
        }

        String solution = "" + tryTrack(places, 1, track);

        writer.write(solution);
        writer.flush();
    }

    static int tryTrack(Place[] places, int currentPlaceIndex, Deque<Integer> track) {
        Place currentPlace = places[currentPlaceIndex];
        if (currentPlace.counter == 101)
            return 0;
        if (track.isEmpty())
            return currentPlaceIndex;

        int auto = track.pop();

        Map<Integer, Set<Integer>> currentPlaceAutos = places[currentPlaceIndex].autos;
        for (Integer toPlaceIndex : currentPlaceAutos.keySet()) {
            Set<Integer> autos = currentPlaceAutos.get(toPlaceIndex);
            if (autos.contains(auto)) {
                places[toPlaceIndex].counter++;
                int lastPlaceIndex = tryTrack(places, toPlaceIndex, track);
                places[toPlaceIndex].counter--;
                if (lastPlaceIndex != 0) {
                    return lastPlaceIndex;
                }
            }
        }

        track.push(auto);
        return 0;
    }

    public static class Place {
        final int index;
        final Map<Integer, Set<Integer>> autos = new HashMap<>(); // <toPlace, set<autoNum>>
        int counter = 0;

        public Place(int index) {
            this.index = index;
        }
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
