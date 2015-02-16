/***********************  SplayTreeNode.java  ***********************/

public class SplayTreeNode {
    public Comparable el;
    protected SplayTreeNode left, right, parent;
    public SplayTreeNode() {
        left = right = parent = null;
    }
    public SplayTreeNode(Comparable el) {
        this(el,null,null,null);
    }
    public SplayTreeNode(Comparable ob, SplayTreeNode lt,
                         SplayTreeNode rt, SplayTreeNode pr) {
        el = ob; left = lt; right = rt; parent = pr;
    }
}
