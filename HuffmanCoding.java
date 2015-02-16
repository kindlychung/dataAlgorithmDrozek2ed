import java.io.*;
import java.util.Date;

class HuffmanNode {
    public byte symbol;
    public int codeword;
    public int freq;
    public int runLen;
    public int codewordLen;
    public HuffmanNode left = null, right = null;
    public HuffmanNode() {
    }
    public HuffmanNode(byte s, int f, int r) {
        this(s,f,r,null,null);
    }
    public HuffmanNode(byte s, int f, int r, HuffmanNode lt, HuffmanNode rt) {
        symbol = s; freq = f; runLen = r; left = lt; right = rt;
    }
}

class ListNode {
    public HuffmanNode tree;
    public ListNode next = null, prev = null;
    public ListNode() {
    }
    public ListNode(ListNode p, ListNode n) {
        prev = p; next = n;
    }
}

class DataRec implements Comparable {
    public byte symbol;
    public int runLen;
    public int freq;
    public DataRec() {
    }
    public DataRec(byte s, int r) {
        symbol = s; runLen = r; freq = 1;
    }
    public boolean equals(Object el) {
        return symbol == ((DataRec)el).symbol && runLen == ((DataRec)el).runLen;
    }
    public int compareTo(Object el) {
        return freq - ((DataRec)el).freq;
    }
}

class HuffmanCoding {
    private final int ASCII = 256,
                  intBytes = 4, // bytes per int;
                  bits = 8;     // bits per byte;
    private HuffmanNode HuffmanTree;
    private HuffmanNode[] chars = new HuffmanNode[ASCII + 1];
    private java.util.ArrayList data = new java.util.ArrayList();
    private long charCnt;
    public HuffmanCoding() {
    }

    private void error(String s) {
        System.err.println(s); System.exit(-1);
    }

    private void garnerData(RandomAccessFile fIn) throws IOException {
        int ch, ch2, runLen, i;
        for (ch = fIn.read(); ch != -1; ch = ch2) {
            for (runLen = 1, ch2 = fIn.read(); ch2 != -1 && ch2 == ch; runLen++)
                ch2 = fIn.read();
            DataRec r = new DataRec((byte)ch,runLen);
            if ((i = data.indexOf(r)) == -1)
                 data.add(r);   
            else ((DataRec)data.get(i)).freq++;
        }
        java.util.Collections.sort(data);
    }

    private void outputFrequencies(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException {
        fOut.writeInt(data.size());
        fOut.writeLong(fIn.getFilePointer());
        for (int j = 0; j < data.size(); j++) {
            DataRec r = (DataRec)data.get(j);
            fOut.write(r.symbol);
            fOut.writeInt(r.runLen);
            fOut.writeInt(r.freq);
        }
    }

    private void inputFrequencies(RandomAccessFile fIn) throws IOException {
        int dataIndex = fIn.readInt();
        charCnt = fIn.readLong();
        data.ensureCapacity(dataIndex);
        for (int j = 0; j < dataIndex; j++) {
            DataRec r = new DataRec();
            r.symbol = (byte) fIn.read();
            r.runLen = fIn.readInt();
            r.freq = fIn.readInt();
            data.add(r);
        }
    }

    private void createHuffmanTree() {
        ListNode p, newNode, head, tail;
        head = tail = new ListNode();           // initialize list pointers;
        DataRec r = (DataRec)data.get(0);
        head.tree = new HuffmanNode(r.symbol,r.freq,r.runLen);
        for (int i = 1; i < data.size(); i++) { // create the rest of the list;
            tail.next = new ListNode(tail,null);
            tail = tail.next;
            r = (DataRec)data.get(i);
            tail.tree = new HuffmanNode(r.symbol,r.freq,r.runLen);
        }
        while (head != tail) {                  // create one Huffman tree;
            int newFreq = head.tree.freq + head.next.tree.freq; // two lowest frequencies
            for (p = tail; p != null && p.tree.freq > newFreq; p = p.prev);
            newNode = new ListNode(p,p.next);
            p.next = newNode;
            if (p == tail)
                 tail = newNode;
            else newNode.next.prev = newNode;
            newNode.tree =
                 new HuffmanNode((byte)0,newFreq,0,head.tree,head.next.tree);
            head = head.next.next;
            head.prev = null;
        }
        HuffmanTree = head.tree;
    }

    private void createCodewords(HuffmanNode p, int codeword, int lvl) {
        if (p.left == null && p.right == null) {   // if p is a leaf,
             p.codeword    = codeword;             // store codeword
             p.codewordLen = lvl;                  // and its lenght,
        }
        else {                                     // otherwise add 0
             createCodewords(p.left,  codeword<<1,   lvl+1);// for left branch
             createCodewords(p.right,(codeword<<1)+1,lvl+1);// and 1 for right;
        }
    }

    private void transformTreeToArrayOfLists(HuffmanNode p) {
        if (p.left == null && p.right == null) {   // if p is a leaf,
             p.right = chars[p.symbol+128];        // include it in
             chars[p.symbol+128] = p;              // a list associated
        }                                          // with symbol found in p;
        else {                                     // add 128 to change the
             transformTreeToArrayOfLists(p.left);  // range of bytes from
             transformTreeToArrayOfLists(p.right); // [-128, 127] to
        }                                          // [0, 255];
    }

    private void encode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException {
        int packCnt = 0, hold, maxPack = 4 * bits, pack = 0;
        int ch, ch2, bitsLeft, runLen;
        HuffmanNode p;
        for (ch = fIn.read(); ch != -1; ) {
            for (runLen = 1, ch2 = fIn.read();  ch2 != -1 && ch2 == ch; runLen++)
                ch2 = fIn.read();
            for (p = chars[(byte)ch+128]; p != null && runLen != p.runLen; p = p.right)
                ;
            if (p == null)
                 error("A problem in transmitCode()");
            if (p.codewordLen < maxPack - packCnt) {// if enough room in
                 pack = (pack << p.codewordLen) | p.codeword; // pack to store 
                 packCnt += p.codewordLen;          // new codeword, shift its
            }                                       // content to the left
                                                    // and attach new codeword;
            else {                                  // otherwise move
                 bitsLeft = maxPack - packCnt;      // pack's content to
                 pack <<= bitsLeft;                 // the left by the
                 if (bitsLeft != p.codewordLen) {   // number of left
                      hold = p.codeword;            // spaces and if new
                      hold >>>= p.codewordLen - bitsLeft;// codeword is longer 
                      pack |= hold;                 // than room left, transfer
                 }                                  // only as many bits as
						    // can be fitted in pack;
                 else pack |= p.codeword;           // if new codeword
						    // exactly fits in
						    // pack, transfer it;
                 fOut.writeInt(pack);               // output pack as
                                                    // four bytes;
                 if (bitsLeft != p.codewordLen) {   // transfer
                      pack = p.codeword;            // unprocessed bits
                      packCnt = maxPack - (p.codewordLen - bitsLeft);// of new
                      packCnt = p.codewordLen - bitsLeft;// codeword to pack;
                 }
                 else packCnt = 0;
            }
            ch = ch2;
        }
        if (packCnt != 0) {
            pack <<= maxPack - packCnt; // transfer left over codewords 
            fOut.writeInt(pack);        // and some 0's;
        }
    }
    public void compressFile(String inFileName, RandomAccessFile fIn) throws IOException {
        String outFileName = new String(inFileName+".z");
        RandomAccessFile fOut = new RandomAccessFile(outFileName,"rw");
        Date start = new Date();
        garnerData(fIn);
        outputFrequencies(fIn,fOut);
        createHuffmanTree();
        createCodewords(HuffmanTree,0,0);
        for (int i = 0; i <= ASCII; i++)
            chars[i] = null;
        transformTreeToArrayOfLists(HuffmanTree);
        fIn.seek(0);
        encode(fIn,fOut);
        Date end = new Date();
        System.out.println("\nfile size = " + fIn.getFilePointer()
                + " data.size() = " + data.size() + " elapsed time = "
                + (end.getTime() - start.getTime()) + " msec");
        int tableSize = 4 + 8 + data.size() * (1 + 4 + 4);
        System.out.println("Space for conversion table = "
                + tableSize + " bytes");
        double fpIn = fIn.getFilePointer(), fpOut = fOut.getFilePointer();
        System.out.println("Compression rate = "
                + ((long)(1000.0*(fpIn-fpOut)/fpIn)/10.0) + "%\n"
                + "Compression rate without table = " 
                + ((long)(1000.0*(fpIn-(fpOut-tableSize))/fpIn)/10.0) + "%");
    }

    private void decode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException {
        int chars, j, ch, bitCnt = 1, mask = 1;
        mask <<= bits - 1;  // change 00000001 to 100000000 
        for (chars = 0, ch = fIn.read(); ch != -1 && chars < charCnt; ) {
            for (HuffmanNode p = HuffmanTree; ; ) {
                if (p.left == null && p.right == null) {
                    for (j = 0; j < p.runLen; j++)
                        fOut.write(p.symbol);
                    chars += p.runLen;
                    break;
                }
                else if ((ch & mask) == 0)
                     p = p.left;
                else p = p.right;
                if (bitCnt++ == bits) {  // read next character from FIn 
                     ch = fIn.read();    // if all bits in ch are checked; 
                     bitCnt = 1;
                }                        // otherwise move all bits in ch  
                else ch <<= 1;           // to the left by one position;
            }
        }
    }

    public void decompressFile(String inFileName, RandomAccessFile fIn) throws IOException {
        String outFileName = new String(inFileName+".dec");
        RandomAccessFile fOut = new RandomAccessFile(outFileName,"rw");
        Date start = new Date();
        inputFrequencies(fIn);
        createHuffmanTree();
        createCodewords(HuffmanTree,0,0);
        for (int i = 0; i <= ASCII; i++)
            chars[i] = null;
        decode(fIn,fOut);
        Date end = new Date();
        System.out.println("\nfile size = " + fOut.getFilePointer()
                + " data.size() = " + data.size() + " elapsed time = "
                + (end.getTime() - start.getTime()) + " msec");
        int tableSize = 4 + 8 + data.size() * (1 + 4 + 4);
        System.out.println("Space for conversion table = "
                + tableSize + " bytes");
        double fpIn = fIn.getFilePointer(), fpOut = fOut.getFilePointer();
        System.out.println("Compression rate = "
                + ((long)(1000.0*(fpOut-fpIn)/fpOut)/10.0) + "%\n"
                + "Compression rate without table = " 
                + ((long)(1000.0*(fpOut-(fpIn-tableSize))/fpOut)/10.0) + "%");
    }

    private void printData() {
        DataRec r;
        System.out.println();
        for (int k = 0; k < data.size(); k++) {
            r = (DataRec)data.get(k);
            System.out.print((char)r.symbol+" "+r.runLen+" "+r.freq+"  ");
        }
        System.out.println();
    }

    private void sideView(int depth, HuffmanNode p) {
        if (p != null) {
            sideView(depth+1,p.right);
            for (int i = 1; i <= depth; i++) {
                System.out.print("   ");
            }
            if (p.left == null && p.right == null) {
                 System.out.print("(" + ((p.symbol >= ' ' && p.symbol <= '~') ? (char)p.symbol : 'X'));
                 System.out.print("," + p.codeword + "," + p.freq);
                 System.out.print("," + p.runLen + "," + p.codewordLen + ")\n");
            }
            else System.out.print(p.freq + "\n");
            sideView(depth+1,p.left);
        }
    }

    private void sideView() {
        System.out.print("Huffman tree:\n");
        sideView(0,HuffmanTree);
    }
}

