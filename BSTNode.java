/************************  BSTNode.java  **************************
 *             node of a generic binary search tree
 */

public class BSTNode {
    protected Comparable el;
    protected BSTNode left, right;
    public BSTNode() {
        left = right = null;
    }
    public BSTNode(Comparable el) {
        this(el,null,null);
    }
    public BSTNode(Comparable el, BSTNode lt, BSTNode rt) {
        this.el = el; left = lt; right = rt;
    }
}
