package data_structure;


/**
 * 二叉搜索树
 * <p>
 * 右子树节点 >=节点 >= 左子树节点
 */
public class ABinarySearchTree {

    private ATreeNode root;

    /**
     * 中序遍历
     */
    public static void inorderWalk(ATreeNode node) {
        if (node != null) {
            inorderWalk(node.left);
            System.out.print(node.value + ", ");
            inorderWalk(node.right);
        }
    }

    public static void inorderWalk_2(ATreeNode root){
        ATreeNode min = miniMum(root);
        System.out.print(min.value + ", ");
        for (int i=0; i<5; i++){
            min = successor(min);
            System.out.print(min.value + ", ");
        }
    }

    /**
     * 先序遍历
     */
    public static void preorderWalk(ATreeNode node) {
        if (node != null) {
            System.out.print(node.value + ", ");
            inorderWalk(node.left);
            inorderWalk(node.right);
        }
    }

    /**
     * 后序遍历
     */
    public static void postorderWalk(ATreeNode node) {
        if (node != null) {
            inorderWalk(node.left);
            inorderWalk(node.right);
            System.out.print(node.value + ", ");
        }
    }

    /**
     * TREE-SEARCH
     */
    public static ATreeNode search(ATreeNode node, int key) {
        if (node == null || node.value == key) {
            return node;
        }
        if (node.value > key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    /**
     * TREE-MINIMUM
     */
    public static ATreeNode miniMum(ATreeNode node){
        if (node == null){
            return null;
        }
        if (node.left != null){
            return miniMum(node.left);
        } else {
            return node;
        }
    }

    /**
     * TREE-MAXIMUM
     */
    public static ATreeNode maxiMum(ATreeNode node){
        if (node == null){
            return null;
        }
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    /**
     * TREE-SUCCESSOR
     * 后继，一个节点x的后继是大于x.key的最小关键字的节点。
     * 若有右子树，即右子树的最小值
     * 若无，且x为父节点的右子节点，则继续往上搜寻，直到为父节点的左子节点为止。
     */
    public static ATreeNode successor(ATreeNode node){
        if (node.right != null){
            return miniMum(node.right);
        }
        ATreeNode y = node.parent;
        while (y != null && node == y.right){
            node = y;
            y = y.parent;
        }
        return y;
    }

    /**
     *  前驱
     *  前驱为小于x.key的最大节点。
     *  若有左子树，则为左子树的的最大值
     *  若无左子树，则为父节点延伸下来的是右枝时的值。
     */
    public static ATreeNode predecessor(ATreeNode node){
        if (node.left != null){
            return maxiMum(node.left);
        }
        ATreeNode y = node.parent;
        while (y != null && y.left == node){
            node = y;
            y = y.parent;
        }
        return y;
    }

    /**
     *  TREE-INSERT
     */
    public void insert(int v){
        ATreeNode z = new ATreeNode(v);

        ATreeNode x = root;
        ATreeNode y = null;
        while (x != null){
            y = x;
            if (x.value >= v){
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if (y == null){
            // tree is empty
            this.root = z;
            return;
        } else if(y.value >= v){
            y.left = z;
        } else {
            y.right = z;
        }
    }

    /**
     * TREE-DELETE
     * 删除分三种情况:
     * 1. z没有子节点。则可直接删除
     * 2. z只有一个子节点，则用这个子节点替换z
     * 3. z有两个子节点，找z的后继y，替换z。
     *      y的左子节点必为空，否则不为z后继
     *      3.1 若y为z的右子节点，则y直接替换z。
     *      3.2 若y不为z的右子节点，则y在z的右子树，此时用y的右子节点替换y，然后y替换z
     */
    public void delete( ATreeNode z){
        if (z.left == null){
            transplant(z, z.right);
        } else if (z.right == null){
            transplant(z, z.left);
        } else {
            ATreeNode y = successor(z);
            if (z.right != y){
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
        }
    }

    // 节点v替换u, 并未处理v的left和right，由调用者负责
    private void transplant( ATreeNode u, ATreeNode v){
        if (u.parent == null){
            this.root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null){
            v.parent = u.parent;
        }
    }


    public static void main(String[] args) {
        ATreeNode root = new ATreeNode(6);
        root.left = new ATreeNode(5, root);
        root.left.left = new ATreeNode(2, root.left);
        root.left.right = new ATreeNode(5, root.left);
        root.right = new ATreeNode(7, root);
        root.right.right = new ATreeNode(8, root.right);

    }
}

