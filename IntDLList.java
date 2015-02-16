//************************  IntDLList.java  ***************************/

public class IntDLList {
    private IntDLLNode head, tail;
    public IntDLList() {
        head = tail = null;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public void setToNull() {
        head = tail = null;
    }
    public int firstEl() {
        if (!isEmpty())
             return head.info;
        else return 0;
    }
    public void addToDLListHead(int el) {
        if (!isEmpty()) {
             head = new IntDLLNode(el,head,null);
             head.next.prev = head;
        }
        else head = tail = new IntDLLNode(el);
    }
    public void addToDLListTail(int el) {
        if (!isEmpty()) {
             tail = new IntDLLNode(el,null,tail);
             tail.prev.next = tail;
        }
        else head = tail = new IntDLLNode(el);
    }
    public int deleteFromDLListHead() {
        if (!isEmpty()) {        // if at least one node in the list;
             int el = head.info;
             if (head == tail)   // if only one node in the list;
                  head = tail = null;
             else {              // if more than one node in the list;
                  head = head.next;
                  head.prev = null;
             }
             return el;
        }
        else return 0;
    }
    public int deleteFromDLListTail() {
        if (!isEmpty()) {
             int el = tail.info;
             if (head == tail)   // if only one node on the list;
                  head = tail = null;
             else {              // if more than one node in the list;
                  tail = tail.prev;
                  tail.next = null;
             }
             return el;
        }
        else return 0;
    }
    public void printAll() { //OutputStream Out) {
        for (IntDLLNode tmp = head; tmp != null; tmp = tmp.next)
             System.out.print(tmp.info + " ");
    }
    public int find(int el) {
        IntDLLNode tmp;
        for (tmp = head; tmp != null && tmp.info != el; tmp = tmp.next);
        if (tmp == null)
             return 0;
        else return tmp.info;
    }
}
