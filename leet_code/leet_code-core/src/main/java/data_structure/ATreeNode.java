package data_structure;

/**
 * 树的节点
 */
public class ATreeNode {
    public ATreeNode left;
    public ATreeNode right;
    public ATreeNode parent;
    public int value;

    public ATreeNode(int v){
        this.value = v;
    }

    public ATreeNode(int v, ATreeNode p){
        this.value = v;
        this.parent = p;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
