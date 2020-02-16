package sort;

import java.util.Random;

public class Util {
    public static int[] genPositiveRandomIntArray(int length){
        if (length<=0){
            throw new RuntimeException("fffffffffuck");
        }
        int[] ar = new int[length];
        Random random = new Random();
        for (int i=0;i<length;i++){
            ar[i] = random.nextInt(Integer.MAX_VALUE);
        }
        return ar;
    }

    public static double[] genRandomDoubleArray(int length){
        Random random = new Random();
        double[] dr = new double[length];
        for (int i=0; i<length; i++){
            dr[i] = random.nextDouble();
        }
        return dr;
    }

    public static void printIntArray(int[] array){
        for (int i=0; i<array.length; i++){
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }

    public static void printDoubleArray(double[] array){
        for (int i=0; i<array.length; i++){
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printIntArray(genPositiveRandomIntArray(10));
    }
}
