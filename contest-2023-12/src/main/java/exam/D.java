package exam;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * <pre>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exams">Java-разработчик (зима 2024)</a>
 * <a href="https://edu.tinkoff.ru/selection/06554dc8-646a-79ed-8000-609cfb04260a/exam/16197?task=4">OK 9324973</a>
 *
 * 4 задание
 * Ограничение времени
 * 1 секунда
 * Ограничение памяти
 * 256 МБ
 *
 * Боб начинает свой путь в Тинькофф Инвестициях. Сейчас его интересуют k компаний,
 * и он обязательно хочет купить акции всех этих компаний, чтобы у каждой компании была хотя бы одна акция.
 *
 * Тинькофф Инвестиции предложили ему корневое дерево из n вершин, в каждой из которых лежит пакет акций какой-то из
 * интересующих его компаний, также задана стоимость каждого пакета.
 * Вершины корневого дерева пронумерованы целыми числами от 1 до n.
 *
 * Функционал нынешнего приложения позволяет Бобу купить поддерево этого дерева, на это он потратит столько денег,
 * сколько суммарно стоят пакеты в поддереве и получит все акции из этого поддерева.
 * В результате покупки Боб хочет, чтобы у него были акции всех интересующих его компаний.
 * Поскольку Боб ещё студент, он хочет потратить минимальное количество денег.
 *
 * Определите может ли Боб выкупить какое-то поддерево так, чтобы у него оказались все нужные ему акции, и если да,
 * то какое минимальное количество денег он для этого должен потратить.
 *
 * Формат входных данных
 * В первой строке заданы два целых числа n, k (1≤n≤3*10^5,1≤k≤30) — размер дерева и количество интересных для Боба компаний.
 * Следующие k строк содержат различные строки длиной не более 10 символов из латинских букв — названия компаний.
 * Следующие n строк содержат описание дерева. В i-ой строке находится описание i-й вершины дерева pi, ai, ci (0≤pi≤n,0≤ai≤10^4):
 *     pi — номер родителя i-ой вершины или 0, если вершина является корнем
 *     ai — стоимость пакета акций в i-ой вершине
 *     ci — название компании, пакет акции которой лежит в i-ой вершине
 * Гарантируется, что компании, акции которых лежат в вершинах, интересуют Боба.
 * Гарантируется, что входные данные задают корректное корневое дерево.
 *
 * Формат выходных данных
 * Выведите единственное число — минимальное количество денег, которое должен потратить Боб,
 * или −1, если выкупить акции всех компаний невозможно.
 *
 * Замечание
 * Определение корневого дерева можете посмотреть в Википедии.
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
        Scanner reader = new Scanner(is);
        int n = reader.nextInt();
        int k = reader.nextInt();

        HashMap<String, Integer> companyIndexByName = new HashMap<>();
        for (int i = 0; i < k; i++)
            companyIndexByName.put(reader.nextWord(), i);

        List<Node> nodes = Stream.generate(Node::new).limit(n + 1).toList();
        for (int i = 1; i <= n; i++) {
            Node p = nodes.get(reader.nextInt());
            int a = reader.nextInt();
            int c = companyIndexByName.get(reader.nextWord());
            nodes.get(i).fillInfo(p, a, c, k);
        }

        nodes.get(0).totalCompanyPrices = new long[k];
        nodes.get(0).addParentInfoToTotal();

        Node min = nodes.get(0).getRequiredChild();

        String solution = "" + (min == null ? -1 : min.totalPrice);

        writer.write(solution);
        writer.flush();
    }

    public static class Node {
        public Node parent;

        public int price;
        public int companyIndex;

        public long totalPrice;
        public Set<Integer> totalCompanyIndexes = new HashSet<>();
        public long[] totalCompanyPrices;

        public List<Node> children = new ArrayList<>();

        public void fillInfo(Node parent, int price, int companyIndex, int k) {
            this.parent = parent;
            this.price = price;
            this.companyIndex = companyIndex;

            parent.children.add(this);
            totalPrice = price;
            totalCompanyIndexes.add(companyIndex);
            totalCompanyPrices = new long[k];
            totalCompanyPrices[companyIndex] = price;
        }

        public void addParentInfoToTotal() {
            if (parent != null) {
                totalPrice += parent.totalPrice;
                totalCompanyIndexes.addAll(parent.totalCompanyIndexes);
                System.arraycopy(parent.totalCompanyPrices, 0, totalCompanyPrices, 0, totalCompanyPrices.length);
                totalCompanyPrices[companyIndex] += price;
            }
            children.forEach(Node::addParentInfoToTotal);
        }

        public Node getRequiredChild() {
            if (children.isEmpty())
                if (totalCompanyIndexes.size() == totalCompanyPrices.length)
                    return this;
                else
                    return null;

            Node min = null;
            for (Node child : children) {
                Node cur = child.getRequiredChild();
                if ((cur != null) && (min == null || cur.totalPrice < min.totalPrice))
                    min = cur;
            }
            return min;
        }
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
