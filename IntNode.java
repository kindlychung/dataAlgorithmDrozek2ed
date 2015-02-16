public class IntNode {
    public int info;
    public IntNode next;
    public IntNode(int i) {
        this(i,null);
    }
    public IntNode(int i, IntNode n) {
        info = i; next = n;
    }
}


