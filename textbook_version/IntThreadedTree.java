public class IntThreadedTree {
    private IntThreadedTreeNode root;
    public IntThreadedTree() {
        root = null;
    }
    protected void visit(IntThreadedTreeNode p) {
        System.out.print(p.key + " ");
    }
    public void threadedInorder() {
        threadedInorder(root);
    }
    public void threadedInsert(int el) {
        IntThreadedTreeNode newNode = new IntThreadedTreeNode(el);
        if (root == null) {              // tree is empty
              root = newNode;
              return;
        }
        IntThreadedTreeNode p = root, prev = null;
        while (p != null) {              // find a place to insert newNode;
             prev = p;
             if (el < p.key)
                  p = p.left;
             else if (!p.hasSuccessor)   // go to the right node only if it is
                  p = p.right;           // a descendant, not a successor;
             else break;                 // don't follow successor link;
        }
        if (el < prev.key) {             // if newNode is left child of
             prev.left  = newNode;       // its parent, the parent becomes
             newNode.hasSuccessor = true;// also its successor;
             newNode.right = prev;
        }
        else if (prev.hasSuccessor) {    // if parent of the newNode
             newNode.hasSuccessor = true;// is not the right-most node,
             prev.hasSuccessor = false;  // make parent's successor
             newNode.right = prev.right; // newNode's successor,
             prev.right = newNode;
        }
        else prev.right = newNode;       // otherwise has no successor;
    }
    protected void threadedInorder(IntThreadedTreeNode p) {
        IntThreadedTreeNode prev;
        if (p != null) {                 // process only non-empty trees;
            while (p.left != null)       // go to the leftmost node;
                p = p.left;
            while (p != null) {
                visit(p);
                prev = p;
                p = p.right;             // go to the right node and only
                if (p != null && !prev.hasSuccessor)// if it is a descendant
                    while (p.left != null)          // go to the leftmost node,
                        p = p.left;      // otherwise visit the successor;
            }
        }
    }
}
