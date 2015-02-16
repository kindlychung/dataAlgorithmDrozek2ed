//************************  IntSLList.java  **************************
//           singly-linked list class to store integers

public class IntSLList {
    protected IntSLLNode head, tail;
    public IntSLList() {
        head = tail = null;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public void addToHead(int el) {
        head = new IntSLLNode(el,head);
        if (tail == null)
            tail = head;
    }
    public void addToTail(int el) {
        if (!isEmpty()) {
            tail.next = new IntSLLNode(el);
            tail = tail.next;
        }
        else head = tail = new IntSLLNode(el);
    }
    public int deleteFromHead() { // delete the head and return its info; 
        int el = head.info;
        if (head == tail)    // if only one node on the list;
             head = tail = null;
        else head = head.next;
        return el;
    }
    public int deleteFromTail() { // delete the tail and return its info;
        int el = tail.info;
        if (head == tail)    // if only one node in the list;
             head = tail = null;
        else {               // if more than one node in the list,
             IntSLLNode tmp;    // find the predecessor of tail;
             for (tmp = head; tmp.next != tail; tmp = tmp.next);
             tail = tmp;     // the predecessor of tail becomes tail;
             tail.next = null;
        }
        return el;
    }
    public void printAll() {
        for (IntSLLNode tmp = head; tmp != null; tmp = tmp.next)
            System.out.print(tmp.info + " ");                
    }
    public boolean isInList(int el) {
        IntSLLNode tmp;
        for (tmp = head; tmp != null && tmp.info != el; tmp = tmp.next);
        return tmp != null;
    }
    public void delete(int el) {  // delete the node with an element el;
        if (!isEmpty())
            if (head == tail && el == head.info) // if only one
                 head = tail = null;             // node on the list;
            else if (el == head.info) // if more than one node on the list;
                 head = head.next;    // and el is in the head node;
            else {                    // if more than one node in the list
                 IntSLLNode pred, tmp;   // and el is in a non-head node;
                 for (pred = head, tmp = head.next;  
                      tmp != null && tmp.info != el; 
                      pred = pred.next, tmp = tmp.next);
                 if (tmp != null) {   // if el was found;
                     pred.next = tmp.next;
                     if (tmp == tail) // if el is in the last node;
                        tail = pred;
                 }
            }
    }
}

