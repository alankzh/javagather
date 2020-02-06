package sort;



/**
 * 堆排序
 */
public class HeapSort {


    /**
     * 最大堆: A[parent(i)] >= A[i]
     * 最小堆: A[parent(i)] <= A[i]
     */
    private enum HeapType{
        MAX,MIN;
    }

    /**
     * 二叉堆
     */
    private static class Heap{

        private HeapType heapType;

        private int[] array;

        private int heapSize;

        public Heap(int[] array, HeapType heapType){
            if (heapType == null || array == null){
                throw new RuntimeException("fuck u");
            }
            this.heapType = heapType;
            this.array = array;

            init();
        }

        /**
         * 对于下标为 i-1的数组元素
         * parent = i/2 = i>>1
         * left = 2i
         * right = 2i + 1
         */
        public int parent(int i){
            return ( (i+1) >> 1) - 1;
        }
        public int left(int i){
            return ( (i+1) << 1) -1;
        }
        public int right(int i){
            return (i+1) << 1;
        }

        private void heapify(int i){
            switch (heapType){
                case MAX:
                    maxHeapify(i);
                    break;
                case MIN:
                    minHeapify(i);
                    break;
            }
        }
        /**
         *  MAX-HEAPIFY
         *  维护最大堆的性质
         */
        private void maxHeapify(int i){
            if (!heapType.equals(HeapType.MAX)){
                throw new RuntimeException("fuck u");
            }
            int l = left(i);
            int r = right(i);
            int largest = i;
            if (l < heapSize && array[l] > array[i]){
                largest = l;
            }
            if (r < heapSize && array[r] > array[largest]){
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
        private void minHeapify(int i){
            if (!heapType.equals(HeapType.MIN)){
                throw new RuntimeException("fuck u");
            }
            int l = left(i);
            int r = right(i);
            int least;
            if (l < heapSize && array[l] < array[i]){
                least = l;
            } else {
                least = i;
            }
            if (r < heapSize && array[r] < array[least]){
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
         * BUILD-MIN-HEAP
         * 自底向上，利用MAX-HEAPIFY 或MIN-HEAPIFY建堆。
         * 且由于n/2 + 1到n个节点皆为叶几点(n/2 + 1 的 left超过n，故为叶节点)，故从n/2个节点开始迭代递减
         */
        private void init(){
            this.heapSize = array.length;
            for (int i = array.length/2 - 1; i>=0; i--){
                heapify(i);
            }
        }

        /**
         * HEAP-SORT
         * 每次将跟节点置换到heapSize末尾，然后heapSize减1，
         * 并调用MAX-HEAPIFY或MIN-HEAPIFY来维护堆的性质
         * 直到heapSize为1时，数组排序完成
         */
        public void heapSort(){
            for (int i = array.length - 1; i>0; i--){
                int temp = array[0];
                array[0] = array[i];
                array[i] = temp;
                heapSize--;
                heapify(0);
            }
        }

        public void printHeap(){
            int k = 0;
            int s = 0;
            for (int i=0; i<heapSize; i++){
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

        public void printArray(){
            for (int i=0; i<array.length; i++){
                System.out.print(array[i]);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

    /**
     * 优先队列
     */
    private static class HeapPriorityQueue{
        private Heap heap;
        HeapPriorityQueue(int[] array, HeapType heapType) {
            if (array == null || array.length<=0){
                throw new RuntimeException("fuck u");
            }
            this.heap = new Heap(array, heapType);
        }

        public void printSelf(){
            heap.printHeap();
            heap.printArray();
        }

        /**
         * INSERT
         * @param x 插入x
         */
        public void insert(int x){
            if (heap.array.length <= heap.heapSize){
                // 扩容数组
                int minNewLength = heap.array.length + 1;
                int newLength = heap.array.length + heap.array.length>>1;
                int n;
                if (newLength > heap.array.length){
                    n = newLength;
                } else if (minNewLength > heap.array.length){
                    n = minNewLength;
                } else {
                    throw new RuntimeException("heap size overflow");
                }
                int[] newArray = new int[n];
                System.arraycopy(heap.array, 0, newArray,0, heap.array.length);
                heap.array = newArray;
            }
            heap.array[heap.heapSize] = Integer.MIN_VALUE;
            heap.heapSize ++;
            increasePriority(heap.heapSize-1, x);
        }

        /**
         * 返回最大或最小优先级的元素
         * @return
         */
        public int priority(){
            return heap.array[0];
        }

        /**
         * 去除并返回最大或最小优先级的元素
         * @return
         */
        public int extractPriority(){
            if (heap.heapSize < 1){
                throw new RuntimeException("heap underflow");
            }
            int max = heap.array[0];
            heap.array[0] = heap.array[heap.heapSize - 1];
//            heap.array[heap.heapSize - 1] = max;
            heap.heapSize --;
            heap.heapify(0);
            return max;
        }

        /**
         * 将元素优先级提升为k
         * 最大优先队列仅能提升优先级
         * @param i 下标
         * @param k 新优先级
         */
        private void increasePriority(int i, int k){
            if (heap.heapSize <= i || heap.array[i] >= k){
                return;
            }
            while (i>0 && heap.array[heap.parent(i)] < k){
                heap.array[i] = heap.array[heap.parent(i)];
                i = heap.parent(i);
            }
            heap.array[i] = k;
        }

    }


    public static void main(String[] args) {
        int[] array = new int[]{16,4,10,14,7,9,3,2,8,1};

//        Heap heap = new Heap(array, HeapType.MAX);
//        heap.printHeap();
//        heap.printArray();
//
//        heap.heapSort();
//        heap.printHeap();
//        heap.printArray();
        HeapPriorityQueue heapPriorityQueue = new HeapPriorityQueue(array, HeapType.MAX);

        System.out.println(heapPriorityQueue.priority());
        System.out.println(heapPriorityQueue.extractPriority());
        heapPriorityQueue.increasePriority(3, 100);
        heapPriorityQueue.printSelf();
//        heapPriorityQueue.insert(10086);
//        heapPriorityQueue.printSelf();
    }
}
