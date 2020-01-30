package code;

import java.util.ArrayList;
import java.util.List;

public class Soluton54 {
    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            return new ArrayList<Integer>(0);
        }
        int m = matrix[0].length;
        int n = matrix.length;
        List<Integer> list = new ArrayList<>(m * n);
        int mIndex = -1;
        int nIndex = 0;
        n -= 1;
        boolean mIncre = true;
        int mOrder = 1;
        int nOrder = 1;
        for (int limiter = m; ; ) {
            for (int i = 0; i < limiter; i++) {
                if (mIncre) {
                    mIndex += mOrder;
                } else {
                    nIndex += nOrder;
                }
                list.add(matrix[nIndex][mIndex]);
            }
            if (mIncre) {
                m--;
                mOrder *= -1;
                limiter = n;
                mIncre = false;
                if (n == 0) break;
            } else {
                n--;
                nOrder *= -1;
                limiter = m;
                mIncre = true;
                if (m == 0) break;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(spiralOrder(matrix));
    }
}
