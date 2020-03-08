package data_structure;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 红黑树是平衡搜索树的一种
 * 可以保证最坏情况下基本动态集合操作的时间复杂度为 lg(n)
 *
 * 1. 每个节点或黑或红
 * 2. 根节点是黑色的
 * 3. 叶节点，NIL是黑色的
 * 4. 红色节点的子节点必定是黑色的
 * 5. 对每个节点，从该节点到所有后代节点的简单路径上，均包含相同数目的黑色节点 (实际限定了黑色节点层的同增减)
 *
 *  从而可证明，n个节点红黑树的高至多为 2*lg(n+1)
 *
 */
public class ARedBlackTree {

    private Node root;

    // 用颜色为black的NIL代替null，简化代码。
    private final Node NIL = new Node(-1, Color.BLACK);

    ARedBlackTree(){
        this.root = NIL;
    }

    private  class Node{
        private Color color;

        private int v;
        private Node left;
        private Node right;
        private Node parent;

        Node(int v){
            this.v = v;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }

        Node(int v, Color color){
            this.v = v;
            this.color = color;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }
    }

    private enum Color{
        RED, BLACK;
    }

    /**
     *          |
     *          x
     *        /   \
     *       y    ｒ
     *     /  \
     *   ａ   ｂ
     *
     *   右旋转为:
     *
     *          |
     *          y
     *        /   \
     *      ａ     x
     *           /  \
     *         ｂ   ｒ
     * 旋转操作保持了二叉树的性质。
     */
    private void rightRotate(Node x){
        // 这里假设x.left != nil 且根节点的父节点为nil，
        Node y = x.left;

        x.left = y.right;
        if (y.right != NIL){
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL){
            this.root = y;
        } else if(x == x.parent.right){
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        x.parent = y;
        y.right = x;
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
        // 这里假设x.right != null 且根节点的父节点为nil
        Node y = x.right;

        x.right = y.left;
        if (y.left != NIL){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL){
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
     * 每个插入的节点都着色为红色
     *
     * 插入时对性质1、3、5无破坏，仅破坏性质2或性质4
     * 若破坏的是性质2，则直接将z染色为black
     * 若破坏的是性质4，
     * 这时分为case1、case2、case3逐步递进。每步维护循环不变式:
     *  a. 结点z是红色
     *  b. 若z.p是根节点，则z.p是黑色
     *  c. 如果有任何红黑树性质被破坏，那么至多被破坏1条。
     *
     *  仅讨论对称化的其中之一情形，z.p = z.p.p.left
     *  case1:
     *      z的父节点为红色，且叔节点y = z.p.p.right 是红色
     *      此时通过重新染色z.p.p=red,z.p=black,y=black维持性质5，
     *      但z.p.p变为红色，可能破坏性质
     *      故赋值z = z.p.p，再次迭代
     *  case2:
     *      z的父节点为红色，且叔节点y为黑色。而z = z.p.right
     *      此时通过左旋将z上升=z.p,然后左旋下降，置为 z(原z.p) = z.p(原z).left;
     *  case3:
     *      z的父节点为红色，且叔节点y为黑色。而z = z.p.left
     *      对z.p重新着色为black，而z.p.p着色为red，对z.p.p右旋，让z.p代替z.p.p
     *      此时性质4已经得到了满足。
     *
     *  所有的递进操作是为了最后一步做铺垫。
     *  若叔节点y为黑色，那么染色+旋转后，z.p.p和y同时为RED，破坏性质4
     *  若z、z.p、z.p.p不在同一条边，那么染色+旋转后z.p.p和z同为RED，破坏性质4.
     */
    public void insert(int v){
        Node z = new Node(v, Color.RED);
        Node y = NIL;
        Node x = this.root;
        while (x != NIL){
            y = x;
            if (x.v >= v){
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == NIL){
            this.root = z;
        } else if (y.v >= v){
            y.left = z;
        } else {
            y.right = z;
        }
        insertFixup(z);
    }
    private void insertFixup(Node z){
        while (z.parent.color == Color.RED){
            if (z.parent == z.parent.parent.left){
                Node y = z.parent.parent.right; // 叔节点
                if (y.color == Color.RED){
                    // 进入case1
                    z.parent.parent.color = Color.RED;
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z = z.parent.parent;
                } else if (z == z.parent.right){
                    // 进入case2
                    z = z.parent;
                    leftRotate(z);
                } else {
                    // 进入case3
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                /**
                 * 对称时，对case2、case3的处理变化，
                 * 实质就是从case2-》case3 是为了让z、z.p、z.p.p同一边，
                 * 这样最后一步旋转 + 变色不会破坏性质4
                 */
                Node y = z.parent.parent.left;
                if (y.color == Color.RED){
                    // case1
                    z.parent.parent.color = Color.RED;
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z = z.parent.parent;
                } else if (z == z.parent.left){
                    // case 2
                    z = z.parent;
                    rightRotate(z);
                } else {
                    // case 3
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        this.root.color = Color.BLACK;
    }

    // 为打印而造
    private static class Node4Print{
        Node node;
        int deep;// root的deep为1
        private Node4Print(Node n, int deep){
            this.node = n;
            this.deep = deep;
        }
    }

    public void printSelf(){
        ArrayList<Node4Print> stack = new ArrayList<>();
        if (this.root!=NIL){
            stack.add(new Node4Print(this.root, 1));
        }
        int lastDeep = -1;
        while (stack.size() > 0){
            Node4Print node = stack.get(0);
            stack.remove(0);
            if (lastDeep < node.deep){
                lastDeep = node.deep;
                System.out.println();
            }
            System.out.print("  " + node.node.color + "-" + node.node.v);
            if (node.node.left != NIL){
                stack.add(new Node4Print(node.node.left, node.deep + 1));
            }
            if (node.node.right != NIL){
                stack.add(new Node4Print(node.node.right, node.deep + 1));
            }
        }
    }

    public static void main(String[] args) {
        ARedBlackTree rbTree = new ARedBlackTree();
        rbTree.insert(41);
        rbTree.insert(38);
        rbTree.insert(31);
        rbTree.insert(12);
        rbTree.insert(19);
        rbTree.insert(8);
        rbTree.printSelf();
    }

}
