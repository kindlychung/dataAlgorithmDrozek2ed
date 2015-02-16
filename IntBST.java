/************************  IntBST.java  **************************
 *                 binary search tree of integers
 */

public class IntBST {
    protected IntBSTNode root;
    protected void visit(IntBSTNode p) {
        System.out.print(p.key + " ");
    }
    public IntBST() {
        root = null;
    }
    public IntBSTNode search(int el) {
        return search(root,el);
    }
    protected IntBSTNode search(IntBSTNode p, int el) {
        while (p != null)
            if (el == p.key)
                 return p;
            else if (el < p.key)
                 p = p.left;
            else p = p.right;
        return null;
    }
    public void breadthFirst() {
        IntBSTNode p = root;
        Queue queue = new Queue();
        if (p != null) {
             queue.enqueue(p);
             while (!queue.isEmpty()) {
                 p = (IntBSTNode) queue.dequeue();
                 visit(p);
                 if (p.left != null)
                      queue.enqueue(p.left);
                 if (p.right != null)
                      queue.enqueue(p.right);
             }
        }
    }
    public void preorder() {
        preorder(root);
    }
    protected void preorder(IntBSTNode p) {
        if (p != null) {
             visit(p);
             preorder(p.left);
             preorder(p.right);
        }
    }
    public void inorder() {
        inorder(root);
    }
    protected void inorder(IntBSTNode p) {
        if (p != null) {
             inorder(p.left);
             visit(p);
             inorder(p.right);
        }
    }
    public void postorder() {
        postorder(root);
    }
    protected void postorder(IntBSTNode p) {
        if (p != null) {
             postorder(p.left);
             postorder(p.right);
             visit(p);
        }
    }
    public void iterativePreorder() {
        IntBSTNode p = root;
        Stack travStack = new Stack();
        if (p != null) {
             travStack.push(p);
             while (!travStack.isEmpty()) {
                 p = (IntBSTNode) travStack.pop();
                 visit(p);
                 if (p.right != null)
                      travStack.push(p.right);
                 if (p.left  != null)        // left child pushed after right
                      travStack.push(p.left);// to be on the top of the stack;
             }
        }
    }
    public void iterativeInorder() {
        IntBSTNode p = root;
        Stack travStack = new Stack();
        while (p != null) {
            while(p != null) {               // stack the right child (if any)
                 if (p.right != null)        // and the node itself when going
                    travStack.push(p.right); // to the left;
                 travStack.push(p);
                 p = p.left;
            }
            p = (IntBSTNode) travStack.pop();   // pop a node with no left child
            while (!travStack.isEmpty() && p.right == null) { // visit it and all
                 visit(p);                   // nodes with no right child;
                 p = (IntBSTNode) travStack.pop();
            }
            visit(p);                        // visit also the first node with
            if (!travStack.isEmpty())        // a right child (if any);
                 p = (IntBSTNode) travStack.pop();
            else p = null;
        }
    }
    public void iterativePostorder() {
        IntBSTNode p = root;
        Stack travStack = new Stack(), output = new Stack();
        if (p != null) {        // left-to-right postorder = right-to-left preorder
             travStack.push(p);
             while (!travStack.isEmpty()) {
                 p = (IntBSTNode) travStack.pop();
                 output.push(p);
                 if (p.left != null)
                      travStack.push(p.left);
                 if (p.right != null)
                      travStack.push(p.right);
             }
             while (!output.isEmpty()) {
                 p = (IntBSTNode) output.pop();
                 visit(p);
             }
        }
    }
    public void MorrisInorder() {
        IntBSTNode p = root, tmp;
        while (p != null)
            if (p.left == null) {
                 visit(p);
                 p = p.right;
            }
            else {
                 tmp = p.left;
                 while (tmp.right != null && // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                      tmp = tmp.right;   // to the temporary parent of p;
                 if (tmp.right == null) {// if 'true' rightmost node was
                      tmp.right = p;     // reached, make it a temporary
                      p = p.left;        // parent of the current root,
                 }
                 else {                  // else a temporary parent has been
                      visit(p);          // found; visit node p and then cut
                      tmp.right = null;  // the right pointer of the current
                      p = p.right;       // parent, whereby it ceases to be
                 }                       // a parent;
            }
    }
    public void insert(int el) {
        IntBSTNode p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            if (p.key < el)
                 p = p.right;
            else p = p.left;
        }
        if (root == null)    // tree is empty;
             root = new IntBSTNode(el);
        else if (prev.key < el)
             prev.right = new IntBSTNode(el);
        else prev.left  = new IntBSTNode(el);
    }
    public void deleteByCopying(int el) {
        IntBSTNode node, p = root, prev = null;
        while (p != null && p.key != el) {       // find the node p
             prev = p;                            // with element el;
             if (p.key < el)
                  p = p.right;
             else p = p.left;
        }
        node = p;
        if (p != null && p.key == el) {
             if (node.right == null)              // node has no right child;
                  node = node.left;
             else if (node.left == null)          // no left child for node;
                  node = node.right;
             else {
                  IntBSTNode tmp = node.left;     // node has both children;
                  IntBSTNode previous = node;     // 1.
                  while (tmp.right != null) {     // 2. find the rightmost
                      previous = tmp;             //    position in the
                      tmp = tmp.right;            //    left subtree of node;
                  }
                  node.key = tmp.key;             // 3. overwrite the reference  
                  if (previous == node)           //    of the key being deleted;
                       previous.left  = tmp.left; // 4.
                  else previous.right = tmp.left; // 5.
             }
             if (p == root)
                  root = node;
             else if (prev.left == p)
                  prev.left = node;
             else prev.right = node;
        }    
        else if (root != null)
             System.out.println("key " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }
    public void deleteByMerging(int el) {
        IntBSTNode tmp, node, p = root, prev = null;
        while (p != null && p.key != el) { // find the node p
             prev = p;                      // with element el;
             if (p.key < el)
                  p = p.right;
             else p = p.left;
        }
        node = p;
        if (p != null && p.key == el) {
             if (node.right == null) // node has no right child: its left
                  node = node.left;  // child (if any) is attached to its parent;
             else if (node.left == null) // node has no left child: its right
                  node = node.right; // child is attached to its parent;
             else {                  // be ready for merging subtrees;
                  tmp = node.left;   // 1. move left
                  while (tmp.right != null) // 2. and then right as far as
                      tmp = tmp.right;      //    possible;
                  tmp.right =        // 3. establish the link between the
                      node.right;    //    the rightmost node of the left
                                     //    subtree and the right subtree;
                  node = node.left;  // 4.
             }
             if (p == root)
                  root = node;
             else if (prev.left == p)
                  prev.left = node;
             else prev.right = node;
        }
        else if (root != null)
             System.out.println("key " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }
    public void balance(int data[], int first, int last) {
        if (first <= last) {
            int middle = (first + last)/2;
            System.out.print(data[middle]+" ");
            insert(data[middle]);
            balance(data,first,middle-1);
            balance(data,middle+1,last);
        }
    }
    public void clear() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }
    public boolean isInTree(int el) {
        return search(root,el) != null;
    }
}
