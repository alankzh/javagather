package sort;


import java.util.Random;
import java.util.Stack;

/**
 * 快速排序的性能依赖于划分是否平衡
 */
public class QuickSort {

    /**
     * QUICKSORT
     * 下标 p < r
     */
    public static void quickSort(int[] a, int p, int r) {
        if (p >= r) {
            // 递归出口
            return;
        }
        int k = partition(a, p, r);
        quickSort(a, p, k - 1);
        quickSort(a, k + 1, r);
    }

    /**
     * PARTITION
     * 这里用前后指针法做分隔
     * i指针指向最后一个小于主元的下标，j向后迭代到r-1，以更新i的值
     */
    static Random random = new Random();

    private static int partition(int[] a, int p, int r) {
        // 主元 pivot element
        // 也可以随机化选取主元
//        int rp = random.nextInt(r+1);
//        int temp = a[r];
//        a[r] = a[rp];
//        a[rp] = a[r];
        int pivot = a[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                i = i + 1;
                // 交换a[i] 与 a[j]
                swap(a, i, j);
            }
        }
        // 最后交换第一个大于主元的值和主元
        swap(a, i + 1, r);
        // 返回主元的位置
        return i + 1;
    }


    public static void quickSortEqualsElements(int[] a, int p, int r) {
        if (p >= r){
            return;
        }
        int[] k = partitionEqualsElements(a, p, r);
        quickSortEqualsElements(a, p, k[0] - 1);
        quickSortEqualsElements(a, k[1] + 1, r);
    }

    /**
     * 针对会出现相同元素的快速排序，
     * 返回2位数组，p和t，其中p指向第一个等于主元的元素下表，t指向最后一个等于主元的下标
     *
     * @return
     */
    private static int[] partitionEqualsElements(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p - 1;
        int t = i;
        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                i = i + 1;
                t = t + 1;
                swap(a, i, j);
                if (i != t){
                    swap(a, t, j);
                }
            } else if (a[j] == pivot) {
                t = t + 1;
                swap(a, t, j);
            }
        }
        swap(a, t + 1, r);
        return new int[]{i+1, t+1};
    }

    /**
     * 尾递归的办法来尝试快排
     */
    public static void quickSortByStack(int[] a, int p, int r){
        Stack<Integer> stack = new Stack<>();
        stack.push(p);
        stack.push(r);
        while (!stack.empty()){
            int right = stack.pop();
            int left = stack.pop();
            int k = partition(a, left, right);
            if (left < k-1){
                stack.push(left);
                stack.push(k-1);
            }
            if (right > k+1){
                stack.push(k+1);
                stack.push(right);
            }
        }
    }


    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
//        int[] array = new int[]{2, 8, 7, 1, 3, 5, 6, 4};
//        quickSort(array, 0, array.length - 1);
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i] + ", ");
//        }
//        System.out.println();

//        int[] a = new int[]{2, 8, 7, 1, 4, 4, 4, 4, 4, 5, 5, 5, 5, 3, 5, 6, 4};
//        quickSortEqualsElements(a, 0, a.length - 1);
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i] + ", ");
//        }
//        System.out.println();

        int[] a = new int[]{2, 8, 7, 1, 4, 4, 4, 4, 4, 5, 5, 5, 5, 3, 5, 6, 4};
        quickSortByStack(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println();
    }
}
