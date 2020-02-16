package data_structure;


/**
 * 队列，先进先出
 * 一样用数组维护
 * 长为n的数组，可容纳数量为n-1的队列
 */
public class AQueue<E> {
    Object array[];

    int length;

    // 指向第一个元素
    int head;

    // 指向最后一个元素的末尾。
    int tail;

    public AQueue(int capacity){
        Object[] array = new Object[capacity + 1];
        this.length = capacity + 1;
        tail = 0;
        head = tail-1;
    }

    public void enQueue(E e){
        if ( ((tail + 1) % length) == head){
            throw new RuntimeException("overflow");
        }
        array[tail] = e;
        tail = (tail + 1) % length;
    }

    @SuppressWarnings("unchecked")
    public <E> E deQueue(){
        if ( ((head + 1) % length) == tail){
            throw new RuntimeException("underflow");
        }
        E result = (E) array[head];
        head = (head + 1) % length;
        return result;
    }
}
