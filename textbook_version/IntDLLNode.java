//***********************  IntDLLNode.java  *********************

class IntDLLNode {
    int info;
    IntDLLNode next = null, prev = null;
    IntDLLNode() {
    }
    IntDLLNode(int el) {
        info =  el;
    }
    IntDLLNode(int el, IntDLLNode n, IntDLLNode p) {
        info = el; next = n; prev = p; 
    }
} 

