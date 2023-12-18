package common;

import java.io.IOException;
import java.io.InputStream;

/**
 * "Быстрый" ридер потока.
 */
public class Scanner {
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
