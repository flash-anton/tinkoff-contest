package common.str;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ZFunction {
    /**
     * Z-функция от строки - это массив, Z[i] которого - длина наибольшего общего префикса у суффикса строки, начинающегося с i, и самой строки.
     */
    public static int[] zFunction(String s) {
        int n = s.length();
        int[] zf = new int[n];
        int left = 0, right = 0;
        for (int i = 1; i < n; i++) {
            zf[i] = max(0, min(right - i, zf[i - left]));
            while (i + zf[i] < n && s.charAt(zf[i]) == s.charAt(i + zf[i])) {
                zf[i]++;
            }
            if (i + zf[i] > right) {
                left = i;
                right = i + zf[i];
            }
        }
        return zf;
    }
}
