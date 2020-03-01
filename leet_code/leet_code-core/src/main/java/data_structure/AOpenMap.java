package data_structure;


/**
 * open address map
 *
 * 开放寻址法构建散列表
 *
 * 若每个关键字的探查序列等可能的生成m！种排列的任意一种，则为均匀散列
 * 若为均匀散列，则期望的探查次数至多为 1 / (1 - ａ)
 */
public class AOpenMap <K,V> {

    Entry<K, V>[] slots;

    public AOpenMap(int m){
        slots = new Entry[m];
    }

    private class Entry<K, V>{
        K key;
        V value;

        Entry(){
        }
        Entry(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    private class DeleteLabel<K, V> extends Entry{
    }

    /**
     * HASH-INSERK
     * @return 返回槽位置
     */
    public int insert(K key, V value){
        for (int i=0; i<slots.length; i++){
            int j = hashProbing(key, i);
            if ((slots[j] == null) || slots[j] instanceof DeleteLabel){
                slots[j] = new Entry<>(key, value);
                return j;
            }
        }
        throw new RuntimeException("under overflow");
    }

    public <K, V> V get(K key){
        for (int i=0; i<slots.length; i++){
            int j = hashProbing(key, i);
            if (slots[j] == null){
                // 如果k在散列表中，那么它就将在此处。而删除元素会有特殊标识DELETE
                // 但使用了特殊标识DELETE，此时查找时间就不再依赖装载因子了。
                return null;
            }
            if ( !(slots[j] instanceof DeleteLabel) && slots[j].key.equals(key)){
                return (V) slots[j].value;
            }
        }
        return null;
    }

    private <K> int hashProbing(K a, int i){
        return linearProbing(a, i);
//        return quadraticProbing(a, i);
//        return doubleProbing(a, i);
    }

    /**
     *  线性探查
     *  这里辅助散列函数h′使用java的hashCode获取key，然后使用除法hash
     *
     *  h(k, i) = (h′(k) + i) mod m
     *  于是，由于i的递进，会生成不同的槽位置，且是m的一个排列
     *
     *  但存在primary clustering 现象，
     *  即当一个空槽前有i个满槽时，空槽被占用的概率是 (i+1)/m, 因为当线性函数是向后递进的
     *  故此时连续被占用的槽会越来越大，因而平均查找时间会越来越大
     */
    private <K> int linearProbing(K key, int i){
        return (key.hashCode() + i) / slots.length;
    }

    /**
     * 二次探查
     *
     * h(k, i) = (h′(k) + c1*i + c2*i*i) mod m
     * c1和c2为正的辅助常数，与线性探查相比，偏移量以二次形式依赖于i
     * 此外，如果k1和k2的初始探查值相同，那么探查序列也相同。
     * 这会导致secondary clustering
     */
    private <K> int quadraticProbing(K key, int i){
        return (key.hashCode() + c1*i + c2*i*i) / slots.length;
    }
    private static final int c1 = 1;
    private static final int c2 = 1;

    /**
     * 双重探查
     * 拥有两个辅助散列函数
     *
     * h(k, i) = (h1(k) + i * h2(k)) mod m
     *
     * 当m为素数或者2的幂时，双重散列法用到了 m^2 种探查序列
     */
    private <K> int doubleProbing(K key, int i){
        return (hash1(key) + i*hash2(key)) / slots.length;
    }

    private <K> int hash1(K key){
        return key.hashCode();
    }
    /**
     *  为了能查找整个散列表，h2(k) 必须与m互素
     *  这里可选取m为2的幂，而h2总产生奇数
     *  或选取m为素数，并设计一个返回较m小的正整数
     *  比如取m为素数时，h2(k) = 1 + (k mod m′)
     *  m′需比m略小，如m-1
     */
    private <K> int hash2(K key){
        return (int) (slots.length * (key.hashCode() * A));
    }
    // 乘法hash时，A可以选取任意值，比较理想的值为 (√5 - 1) / 2
    private static final double A =  0.61803398874989484820458683436564D;


}
