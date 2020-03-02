package data_structure;

/**
 * 红黑树是平衡搜索树的一种
 * 可以保证最坏情况下基本动态集合操作的时间复杂度为 lg(n)
 *
 * 1. 每个节点或黑或红
 * 2. 根节点是黑色的
 * 3. 叶节点，NIL是黑色的
 * 4. 红色节点的子节点必定是黑色的
 * 5. 对每个节点，从该节点到所有后代节点的简单路径上，均包含相同数目的黑色节点 (实际限定了黑色节点层的同增减)
 *  从而可证明，n个节点红黑树的高至多为 2*lg(n+1)
 *
 */
public class ARedBlackTree {
    private Node root;

    private final Node NIL = new Node(0);

    private static class Node{
        // red or black
        private boolean red;

        private int v;
        private Node left;
        private Node right;
        private Node parent;

        Node(int v){
            this.v = v;
        }

        Node(int v, boolean red){
            this.v = v;
            this.red = red;
        }
    }

    /**
     *          |
     *          y
     *        /   \
     *       x    ｒ
     *     /  \
     *   ａ   ｂ
     *
     *   右旋转为:
     *
     *          |
     *          x
     *        /   \
     *      ａ     y
     *           /  \
     *         ｂ   ｒ
     * 旋转操作保持了二叉树的性质。
     */
    private void rightRotate(Node x){
        Node y = x.parent;
        if (y == null){
            return;
        }

        y.left = x.right;
        x.right = y;
        if (y.left != null){
            y.left.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == null){
            this.root = x;
        } else if (y.parent.left == y){
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        y.parent = x;
    }
    /**
     *          |
     *          x
     *        /   \
     *      ａ     y
     *           /  \
     *         ｂ   ｒ
     *   左旋转为:
     *
     *          |
     *          y
     *        /   \
     *       x    ｒ
     *     /  \
     *   ａ   ｂ
     * 旋转操作保持了二叉树的性质。
     */
    private void leftRotate(Node x){
        // 这里假设x.right != null 且跟节点的父节点为T.nil
        Node y = x.right;

        x.right = y.left;
        if (y.left != null){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null){
            this.root = y;
        } else if (x == x.parent.right){
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.left = x;
        x.parent = y;
    }

    /**
     * RB-INSERT
     */
    public void insert(int z){

    }


    private void insertFixup(Node z){

    }

}
