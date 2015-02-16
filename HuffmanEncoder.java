import java.io.*;

class HuffmanEncoder {
    static public void main (String args[]) {
        String fileName = "";
        HuffmanCoding Htree = new HuffmanCoding();
        RandomAccessFile fIn;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        try {
            if (args.length == 0) {
                 System.out.print("Enter a file name: ");
                 fileName = buffer.readLine();
                 fIn = new RandomAccessFile(fileName,"r");
            }
            else {
                 fIn = new RandomAccessFile(args[0],"r");
                 fileName = args[0];
            }
            Htree.compressFile(fileName,fIn);
            fIn.close();
        } catch(IOException io) {
            System.err.println("Cannot open " + fileName);
        }
    }
}

