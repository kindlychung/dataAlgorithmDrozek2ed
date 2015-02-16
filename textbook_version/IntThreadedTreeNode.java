class IntThreadedTreeNode {
    protected int key;
    protected boolean hasSuccessor;
    protected IntThreadedTreeNode left, right;
    public IntThreadedTreeNode() {
        left = right = null; hasSuccessor = false;
    }
    public IntThreadedTreeNode(int el) {
        this(el,null,null);
    }
    public IntThreadedTreeNode(int el, IntThreadedTreeNode lt,
                                       IntThreadedTreeNode rt) {
        key = el; left = lt; right = rt; hasSuccessor = false;
    }
}
