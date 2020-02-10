package sort;

/**
 * 计数排序不依赖比较，
 * 但假定了n个输入的数据范围在 [o,k) 之间
 */
public class CountSort {

    /**
     * @param k 输入的数据上届
     */
    public static int[] countSort(int[] a, int k){
        int[] result = new int[a.length];
        int[] count = new int[k];

        // 第一次迭代n，count数组中保存当前下标对应的值，在数组a中出现了多少次
        for (int i=0; i<a.length; i++){
            count[a[i]] += 1;
        }

        // 第二次迭代k，count数组中保存当前下表对应的值，数组a中有多少个元素小于等于它
        for (int i=1; i<k; i++){
            count[i] += count[i-1];
        }

        // 第三次迭代n, 填充数组result, 从后向前迭代，保证 '稳定性'
//        for (int i=a.length-1; i>=0; i--){
//            // a[i]的值中有多少个小于等于它，则放到result数组中对应的次序中去
//            result[count[a[i]] - 1] = a[i];
//            count[a[i]] -= 1;
//        }
        // 第三次迭代若从前向后迭代，仍然正确，但无稳定性.
        for (int i=0; i<a.length; i++){
            result[count[a[i]] - 1] = a[i];
            count[a[i]] -= 1;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[]{2, 8, 7, 1, 4, 0, 0, 4, 9, 5, 5, 5, 5, 3, 5, 6, 4};
        int[] result = countSort(a, 10);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ", ");
        }
        System.out.println();
    }
}
