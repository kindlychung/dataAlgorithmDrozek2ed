/****************************  SplayTree.java  ************************
 *                     generic splaying tree class
 */

public class SplayTree {
    private SplayTreeNode root = null;
    public SplayTree() {
        super();
    }
    private void continueRotation(SplayTreeNode gr, SplayTreeNode par,
                                  SplayTreeNode ch, SplayTreeNode desc) {
        if (gr != null) {                    // if par has a grandparent;
             if (gr.right == ((SplayTreeNode)ch).parent)
                  gr.right = ch;
             else gr.left  = ch;
        }
        else root = ch;
        if (desc != null)
             ((SplayTreeNode)desc).parent = par;
        ((SplayTreeNode)par).parent = ch;
        ((SplayTreeNode)ch).parent = gr;
    }
    private void rotateR(SplayTreeNode p) {
        p.parent.left = p.right;
        p.right = p.parent;
        continueRotation(((SplayTreeNode)p.parent).parent,p.right,p,p.right.left);
    }
    private void rotateL(SplayTreeNode p) {
        p.parent.right = p.left;
        p.left = p.parent;
        continueRotation(((SplayTreeNode)p.parent).parent,p.left,p,p.left.right);
    }
    private void semisplay(SplayTreeNode p) {
        while (p != root) {
            if (((SplayTreeNode)p.parent).parent == null) // if p's parent is 
                 if (p.parent.left == p)              // the root;
                      rotateR(p);
                 else rotateL(p);
            else if (p.parent.left == p)     // if p is a left child;
                 if (((SplayTreeNode)p.parent).parent.left == p.parent) {
                      rotateR((SplayTreeNode)p.parent);
                      p = (SplayTreeNode)p.parent;
                 }
                 else {
                      rotateR((SplayTreeNode)p); // rotate p and its parent;
                      rotateL((SplayTreeNode)p); // rotate p and its new parent;
                 }
            else                             // if p is a rigth child;
                 if (((SplayTreeNode)p.parent).parent.right == p.parent) {
                      rotateL((SplayTreeNode)p.parent);
                      p = (SplayTreeNode)p.parent;
                 }
                 else {
                      rotateL(p);            // rotate p and its parent;
                      rotateR(p);            // rotate p and its new parent;
                 }
                 if (root == null)           // update the root;
                      root = p;
        }
    }
    public Comparable search(Comparable el) {
        return search(root,el);
    }
    protected Comparable search(SplayTreeNode p, Comparable el) {
        while (p != null)
            if (el.equals(p.el)) {
                 semisplay(p);
                 return p.el;
            }
            else if (el.compareTo(p.el) < 0)
                 p = p.left;
            else p = p.right;
        return null;
    }
    public void insert(Comparable el) {
        SplayTreeNode p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            if (p.el.compareTo(el) < 0)
                 p = p.right;
            else p = p.left;
        }
        if (root == null)    // tree is empty;
             root = new SplayTreeNode(el);
        else if (prev.el.compareTo(el) < 0)
             prev.right = new SplayTreeNode(el,null,null,prev);
        else prev.left  = new SplayTreeNode(el,null,null,prev);
    }
    protected void visit(SplayTreeNode p) {
        System.out.print(p.el + " ");
    }
    public void inorder() {
        inorder(root);
    }
    protected void inorder(SplayTreeNode p) {
        if (p != null) {
             inorder(p.left);
             visit(p);
             inorder(p.right);
        }
    }
}

