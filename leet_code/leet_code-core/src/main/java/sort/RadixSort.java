package sort;

/**
 * 基数排序
 * 先按最低有效位排序，高有效位再依赖于原址排序
 *
 * 可推广到任意情况，例如32位数，可视作4个8位数组成的 d=4 位数
 */
public class RadixSort {

    /***
     * @param d 数组中元素的位数
     */
    public static void radixSort(int[] a, int d){

        int[] count = new int[10];
        int[] result = new int[a.length];
        for (int i=1; i<=d; i++){

            // 这里用10位数的count sort作为stable sort
            int[] temp = a;
            for (int j=0; j<10; j++){
                count[j] = 0;
            }
            for (int j=0; j<a.length; j++){
                result[j] = 0;
            }

            for (int j=0; j<a.length; j++){
                int v = pickValue(a[j], i);
                count[v] += 1;
            }

            for (int j=1; j<10; j++){
                count[j] += count[j-1];
            }

            for (int j=a.length-1; j>=0; j--){
                int v = pickValue(a[j], i);
                result[count[v] - 1] = a[j];
                count[v] -= 1;
            }
            a = result;
            result = temp;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println();
    }

    // 10进制取值
    private static int pickValue(int orgin, int digit){
        String s = String.valueOf(orgin);
        char[] ca = s.toCharArray();
        return Integer.parseInt("" + ca[ca.length - digit]);
    }

    /**
     * 更一般情况的排序。
     * @param array
     * @param b 数组中二进制不超过的位数
     * @param r 将数组中每个数分为由 d=⌈b/r⌉ 位2进制组成
     */
    public static int[] radixSortGeneral(int[] array, int b, int r){
        if (b<=0 || r<=0){
            throw new RuntimeException("fffffffffuck");
        }
        // 桶则由2^r个位置组成
        int[] c = new int[1<<r];
        int[] result = new int[array.length];
        int d = b/r + (b%r==0?0:1);
        for (int i=1; i<=d; i++){
            int[] temp = array;
            for (int j=0; j<c.length; j++){
                c[j] = 0;
            }
            for (int j=0; j<result.length; j++){
                result[j] = 0;
            }
            for (int j=0; j<array.length; j++){
                int v = pickValueBinary(array[j], i, r);
                c[v] +=1;
            }
            for (int j=1; j<c.length; j++){
                c[j] += c[j-1];
            }
            for (int j=array.length-1; j>=0; j--){
                int v = pickValueBinary(array[j], i, r);
                result[c[v] - 1] = array[j];
                c[v] -=1;
            }
            array = result;
            result = temp;
        }
        return array;
    }

    private static int pickValueBinary(int origin, int digit, int r){
        // r个1   111111
        int picker = (1<<r)-1;
        // 取第一位，picker不位移，取第二位，picker向左位移r位，即末尾增加r个0
        int shift = (digit-1)*r;
        return (origin & (picker << shift))>>shift;
    }

    public static void main(String[] args) {
        int[] array = new int[]{341,123,432,312,651,986,534,756,239,833};

        //java传入数组array的地址的值的副本进入函数
//        radixSort(array, 3);
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i] + ", ");
//        }
//        System.out.println();

        int[] result = radixSortGeneral(Util.genPositiveRandomIntArray(12),32,8);
        Util.printIntArray(result);
    }
}
