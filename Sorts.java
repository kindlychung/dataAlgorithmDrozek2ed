class RadixsortNode {
    public int[] arr;
    public RadixsortNode next = null;
    public RadixsortNode() {
    }
    public RadixsortNode(int[] a) {
        arr = new int[a.length];
        for (int i = 0; i < a.length; i++)
            arr[i] = a[i];
    }
    public RadixsortNode(int n) {
        arr = new int[n];
    }
}

public class Sorts {
    public void swap(Object[] a, int e1, int e2) {
        Object tmp = a[e1]; a[e1] = a[e2]; a[e2] = tmp;
    }
    public void insertionsort(Object[] data) {
        for (int i = 1, j; i < data.length; i++) {
            Comparable tmp = (Comparable)data[i];
            for (j = i; j > 0 && tmp.compareTo(data[j-1]) < 0; j--)
                data[j] = data[j-1];
            data[j] = tmp;
        }
    }
    public void selectionsort(Object[] data) {
        int i, j, least;
        for (i = 0; i < data.length-1; i++) {
            for (j = i+1, least = i; j < data.length; j++)
                if (((Comparable)data[j]).compareTo(data[least]) < 0)
                    least = j;
            if (least != i)
                swap(data,least,i);
        }
    }
    public void bubblesort(Object[] data) {
        for (int i = 0; i < data.length-1; i++)
            for (int j = data.length-1; j > i; --j)
                if (((Comparable)data[j]).compareTo(data[j-1]) < 0)
                    swap(data,j,j-1);
    }
    public void Shellsort (Object[] data) {
        int i, j, k, h, hCnt, increments[] = new int[20];
    //  create an appropriate number of increments h
        for (h = 1, i = 0; h < data.length; i++) {
            increments[i] = h;
            h = 3*h + 1;
        }
     // loop on the number of different increments h
        for (i--; i >= 0; i--) {
            h = increments[i];
         // loop on the number of subarrays h-sorted in ith pass
            for (hCnt = h; hCnt < 2*h; hCnt++) {
             // insertion sort for subarray containing every hth element of array data
                for (j = hCnt; j < data.length; ) {
                    Comparable tmp = (Comparable)data[j];
                    k = j;
                    while (k-h >= 0 && tmp.compareTo(data[k-h]) < 0) {
                        data[k] = data[k-h];
                        k -= h;
                    }
                    data[k] = tmp;
                    j += h;
                }
            }
        }
    }
    private void moveDown(Object[] data, int first, int last) {
        int largest = 2*first + 1;
        while (largest <= last) {
            if (largest < last && // first has two children (at 2*first+1 and
                                                          //    2*first+2)
                ((Comparable)data[largest]).compareTo(data[largest+1]) < 0)
                    largest++;
            if (((Comparable)data[first]).compareTo(data[largest]) < 0) {  
                 swap(data,first,largest);       // if necessary, swap values
                 first = largest;                // and move down;
                 largest = 2*first + 1;
            }
            else largest = last + 1;// to exit the loop: the heap property
        }                           // isn't violated by data[first]
    }
    public void heapsort(Object[] data) {
        for (int i = data.length/2 - 1; i >= 0; --i)
            moveDown(data,i,data.length-1);
        for (int i = data.length-1; i >= 1; --i) {
            swap(data,0,i);
            moveDown(data,0,i-1);
        }
    }
    private void quicksort(Object[] data, int first, int last) {
        int lower = first + 1, upper = last;
        swap(data,first,(first+last)/2);
        Comparable bound = (Comparable)data[first];
        while (lower <= upper) {
            while (bound.compareTo(data[lower]) > 0)
                 lower++;
            while (bound.compareTo(data[upper]) < 0)
                 upper--;
            if (lower < upper)
                 swap(data,lower++,upper--);
            else lower++;
        }
        swap(data,upper,first);
        if (first < upper-1)
            quicksort(data,first,upper-1);
        if (upper+1 < last)
            quicksort(data,upper+1,last);
    }
    public void quicksort(Object[] data) {
        if (data.length < 2)
            return;
        int max = 0;
        // find the largest element and put it at the end of data;
        for (int i = 1; i < data.length; i++)
            if (((Comparable)data[max]).compareTo(data[i]) < 0)
                max = i;
        swap(data,data.length-1,max);    // largest el is now in its
        quicksort(data,0,data.length-2); // final position;
    }
    public void insertionsort(Object[] data, int first, int last) {
        for (int i = first, j; i <= last; i++) {
            Comparable tmp = (Comparable)data[i];
            for (j = i; j > 0 && tmp.compareTo(data[j-1]) < 0; j--)
                data[j] = data[j-1];
            data[j] = tmp;
        }
    }
    public void quicksort2(Object[] data, int first, int last) {
        if (last - first < 30)
             insertionsort(data,first,last);
        else {
             int lower = first + 1, upper = last;
             swap(data,first,(first+last)/2);
             Comparable bound = (Comparable)data[first];
             while (lower <= upper) {
                 while (bound.compareTo(data[lower]) > 0)
                     lower++;
                 while (bound.compareTo(data[upper]) < 0)
                     upper--;
                 if (lower < upper)
                     swap(data,lower++,upper--);
                 else lower++;
             }
             swap(data,upper,first);
             if (first < upper-1)
                  quicksort2(data,first,upper-1);
             if (upper+1 < last)
                  quicksort2(data,upper+1,last);
        }
    }
    public void quicksort2(Object[] data) {
        if (data.length < 2)
            return;
        int max = 0;
        // find the largest element and put it at the end of data;
        for (int i = 1; i < data.length; i++)
            if (((Comparable)data[max]).compareTo(data[i]) < 0)
                max = i;
        swap(data,data.length-1,max);     // largest el is now in its
        quicksort2(data,0,data.length-2); // final position;
    }
    private Object[] temp; // used by merge();
    private void merge(Object[] data, int first, int last) {
        int mid = (first + last) / 2;
        int i1 = 0, i2 = first, i3 = mid + 1;
        while (i2 <= mid && i3 <= last)
            if (((Comparable)data[i2]).compareTo(data[i3]) < 0)
                 temp[i1++] = data[i2++];
            else temp[i1++] = data[i3++];
        while (i2 <= mid)
            temp[i1++] = data[i2++];
        while (i3 <= last)
            temp[i1++] = data[i3++];
        for (i1 = 0, i2 = first; i2 <= last; data[i2++] = temp[i1++]);
    }
    private void mergesort(Object[] data, int first, int last) {
        if (first < last) {
            int mid = (first + last) / 2;
            mergesort(data, first, mid);
            mergesort(data, mid+1, last);
            merge(data, first, last);
        }
    }
    public void mergesort(Object[] data) {
        temp = new Object[data.length];
        mergesort(data,0,data.length-1);
    }
    private final int radix = 10;
    private final int digits = 10;
    private final int bits = 31;
    public void radixsort(int[] data) {
        int d, j, k, factor;
        Queue[] queues = new Queue[radix]; // radix is 10;
        for (d = 0; d < radix; d++)
            queues[d] = new Queue();
        for (d = 1, factor = 1; d <= digits; factor *= radix, d++) {
            for (j = 0; j < data.length; j++)
                queues[(data[j] / factor) % radix].enqueue(new Integer(data[j]));
            for (j = k = 0; j < radix; j++)
                while (!queues[j].isEmpty())
                     data[k++] = ((Integer) queues[j].dequeue()).intValue();
        }
    }
    public void bitRadixsort(int[] data) {
        int d, j, k, factor, mask = 1;
        Queue[] queues = new Queue[2];
        queues[0] = new Queue();
        queues[1] = new Queue();
        for (d = 1; d <= bits; d++) {
            for (j = 0; j < data.length; j++)
                queues[(data[j] & mask) == 0 ? 0 : 1].enqueue(new Integer(data[j]));
            mask <<= 1;
            k = 0;
            while (!queues[0].isEmpty())
                data[k++] = ((Integer) queues[0].dequeue()).intValue();
            while (!queues[1].isEmpty())
                data[k++] = ((Integer) queues[1].dequeue()).intValue();
        }
    }
    private void clear(int[] arr, int q) {
        arr[q] = -1;
    }
    private boolean isEmpty(int q) {
        return q == -1;
    }
    public void radixsort2(int[] data) {
        int d, j, k, factor, where;
        int[] queues = new int[data.length], queueHeads = new int[radix];
        int[] queueTails = new int[radix];
        RadixsortNode n2 = new RadixsortNode(data), n1 = new RadixsortNode();
        n1.arr = data;
        n2.next = n1;
        n1.next = n2;
        for (j = 0; j < radix; j++)
            clear(queueHeads,j);
        for (d = 1, factor = 1; d <= digits; factor *= radix, d++) {
            for (j = 0; j < data.length; j++) {
                where = (n1.arr[j] / factor) % radix; // dth digit;
                if (isEmpty(queueHeads[where]))
                     queueTails[where] = queueHeads[where] = j;
                else {
                     queues[queueTails[where]] = j;
                     queueTails[where] = j;
                }
            }
            for (j = 0; j < radix; j++)
                if (!(isEmpty(queueHeads[j])))
                     clear(queues,queueTails[j]);
            for (j = k = 0; j < radix; j++)
                while (!(isEmpty(queueHeads[j]))) {
                     n2.arr[k++] = n1.arr[queueHeads[j]];
                     queueHeads[j] = queues[queueHeads[j]]; // also clears
                }                                           // queueHeads[];
            n2 = n2.next;
            n1 = n1.next;
        }
        if (digits % 2 != 0) // if digits is an odd number;
            for (d = 0; d < data.length; d++)
                data[d] = n1.arr[d];
    }
    public void bitRadixsort2(int[] data) {
        int d, j, k, factor, where, mask = 1;
        int[] queues = new int[data.length], queueHeads = new int[radix];
        int[] queueTails = new int[radix];
        RadixsortNode n2 = new RadixsortNode(data), n1 = new RadixsortNode();
        n1.arr = data;
        n2.next = n1;
        n1.next = n2;
        clear(queueHeads,0);
        clear(queueHeads,1);
        for (d = 1; d <= bits; d++, mask <<= 1) {
            for (j = 0; j < data.length; j++) {
                where = (n1.arr[j] & mask) == 0 ? 0 : 1;
                if (isEmpty(queueHeads[where]))
                     queueTails[where] = queueHeads[where] = j;
                else {
                     queues[queueTails[where]] = j;
                     queueTails[where] = j;
                }
            }
            for (j = 0; j < 2; j++)
                if (!(isEmpty(queueHeads[j])))
                     clear(queues,queueTails[j]);
            for (j = k = 0; j < 2; j++)
                while (!(isEmpty(queueHeads[j]))) {
                     n2.arr[k++] = n1.arr[queueHeads[j]];
                     queueHeads[j] = queues[queueHeads[j]];
                }
            n2 = n2.next;
            n1 = n1.next;
        }
        if (bits % 2 != 0) // if bits is an odd number;
            for (d = 0; d < data.length; d++)
                data[d] = n1.arr[d];
    }
}
