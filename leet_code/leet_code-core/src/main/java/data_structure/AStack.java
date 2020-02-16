package data_structure;

/**
 * 栈，先进先出。
 * 用数组模拟
 */
public class AStack<E> {
    Object[] array;
    int capacity;
    int top;


    public AStack(int length){
        if (length < 0){
            throw new IllegalArgumentException("Illegal Capacity: " + length);
        }
        capacity = length;
        top = -1;
        array = new Object[length];
    }

    public boolean empty(){
        return (top==0);
    }

    public void push(E e){
        if ((top + 1) >= capacity){
            throw new RuntimeException("overflow");
        }
        array[top] = e;
        top++;
    }

    @SuppressWarnings("unchecked")
    public <E> E pop(){
        if (empty()){
            throw new RuntimeException("underflow");
        }
        top--;
        return (E) array[top];
    }

}
