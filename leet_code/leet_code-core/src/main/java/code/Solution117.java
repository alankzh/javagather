package code;


import java.util.ArrayList;
import java.util.List;

public class Solution117 {
    public static Node connect(Node root) {
        if (root == null){
            return null;
        }
        List<Node> queue = new ArrayList<Node>();
        queue.add(root);
        queue.add(null);
        Node last = null;
        for (int s=queue.size(); s>0; ){
            Node n = queue.get(0);
            if (last != null){
                last.next = n;
            }
            last = n;
            queue.remove(0);
            if (n == null){
                s = queue.size();
                if (s>0){
                    queue.add(null);
                }
            } else {
                if (n.left != null) {
                    queue.add(n.left);
                }
                if (n.right != null){
                    queue.add(n.right);
                }
            }
        }
        return root;
    };

    public static void main(String[] args) {

    }

    private static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
}
