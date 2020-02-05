package sort;

/**
 * 堆排序
 */
public class HeapSort {


    /**
     * 最大堆: A[parent(i)] >= A[i]
     * 最小堆: A[parent(i)] <= A[i]
     */
    private static enum HeapType{
        MAX,MIN;
    }
    private static class Heap{

        private HeapType heapType;

        private int[] array;

        public Heap(int[] array, HeapType heapType){
            if (heapType == null || array == null){
                throw new RuntimeException("fuck u");
            }
            this.heapType = heapType;
            this.array = array;
            switch (heapType){
                case MAX:
                    initMax();
                    break;
                case MIN:
                    initMin();
                    break;
            }
        }

        /**
         * 对于下标为 i-1的数组元素
         * parent = i/2 = i>>1
         * left = 2i
         * right = 2i + 1
         */
        public int parent(int i){
            return ( (i+1) >> 1) -1;
        }
        public int left(int i){
            return ( (i+1) << 1) -1;
        }
        public int right(int i){
            return (i+1) << 1;
        }

        /**
         *  MAX-HEAPIFY
         *  维护最大堆的性质
         */
        public void maxHeapify(int i){
            if (!heapType.equals(HeapType.MAX)){
                throw new RuntimeException("fuck u");
            }
            int l = left(i);
            int r = right(i);
            int largest = i;
            if (l < array.length && array[l] > array[i]){
                largest = l;
            }
            if (r < array.length && array[r] > array[largest]){
                largest = r;
            }
            if (largest != i){
                int temp = array[i];
                array[i] = array[largest];
                array[largest] = temp;
                maxHeapify(largest);
            }
        }

        /**
         *  MIN-HEAPIFY
         *  维护最小堆的性质
         */
        public void minHeapify(int i){
            if (!heapType.equals(HeapType.MIN)){
                throw new RuntimeException("fuck u");
            }
            int l = left(i);
            int r = right(i);
            int least;
            if (l < array.length && array[l] < array[i]){
                least = l;
            } else {
                least = i;
            }
            if (r < array.length && array[r] < array[least]){
                least = r;
            }
            if (least != i){
                int temp = array[i];
                array[i] = array[least];
                array[least] = temp;
                minHeapify(least);
            }
        }

        /**
         * 建堆
         * BUILD-MAX-HEAP
         * 自底向上，利用MAX-HEAPIFY建堆。
         */
        private void initMax(){

        }

        private void initMin(){

        }

        public void printSelf(){
            int k = 0;
            int s = 0;
            for (int i=0; i<array.length; i++){
                System.out.print(array[i]);
                System.out.print(", ");
                if (i == s){
                    System.out.println();
                    s += 1<<(k +1);
                    k ++;
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        int[] array = new int[]{16,4,10,14,7,9,3,2,8,1};

        Heap heap = new Heap(array, HeapType.MAX);
        heap.printSelf();
        heap.maxHeapify(1);
        heap.printSelf();
    }
}
