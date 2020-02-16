package sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶排序假定输入在[0,1)中均匀分布
 * 核心推理点：⌊nA[i]⌋ <= ⌊nA[j]⌋
 */
public class BucketSort {

    public static double[] bucketSort(double[] array){
        List<Double>[] bucket = new ArrayList[array.length];
        for (int i=0; i<array.length; i++){
            bucket[i] = new ArrayList<>();
        }
        for (int i=0; i<array.length; i++){
            int h = (int) (array[i] * array.length);
            bucket[h].add(array[i]);
        }
        for (int i=0; i<array.length; i++){
            bucket[i].sort(Double::compareTo);
        }
        int index = 0;
        for (int i=0; i<array.length; i++){
            for (int j=0; j<bucket[i].size(); j++){
                array[index] = bucket[i].get(j);
                index ++;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        double[] result = bucketSort(Util.genRandomDoubleArray(12));
        Util.printDoubleArray(result);
    }

}
