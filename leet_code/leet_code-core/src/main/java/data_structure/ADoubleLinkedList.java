package data_structure;

public class ADoubleLinkedList<T> {

    // 哨兵对象
    private Node sentinel;

    public ADoubleLinkedList(){
        sentinel = new Node<>();
        sentinel.val = null;
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }

    /**
     * LIST-INSERT
     */
    @SuppressWarnings("unchecked")
    public void add(T v){
        Node<T> n = new Node<>(v);
        n.next = sentinel.next;
        sentinel.next.pre = n;
        sentinel.next = n;
        n.pre = sentinel;
    }

    public <T> Node<T> search(T val){
        Node<T> x = sentinel.next;
        while (!x.isSentinel() && !x.val.equals(val)){
            x = x.next;
        }
        if (x.val == null){
            return null;
        }
        return x;
    }

    public void delete(Node<T> n){
        n.pre.next = n.next;
        n.next.pre = n.pre;
    }

    private class Node<T>{
        private Node<T> next;
        private Node<T> pre;
        private T val;

        private Node(){
        }
        private Node(T val){
            this.val = val;
        }

        private boolean isSentinel(){
            return val == null;
        }
    }
}
