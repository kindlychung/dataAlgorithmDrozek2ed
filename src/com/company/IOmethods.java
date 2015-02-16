package com.company;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by IDEA on 17/02/15.
 */
public class IOmethods {
    public void writeString(String s, RandomAccessFile out) throws IOException {
        for(int i = 0; i < s.length(); i++) {
            out.writeChar(s.charAt(i));
        }
    }

    public String readString(int len, RandomAccessFile in) throws  IOException {
        String s = "";
        for(int i = 0; i < len; i++) {
            s += in.readChar();
        }
        return s;
    }

    public String readLine() throws IOException {
        int ch;
        String s= "";
        while(true) {
            ch = System.in.read();
            if(ch == -1 || (char) ch == '\n') break;
            else if((char) ch != '\r') {
                s = s + (char) ch;
            }
        }
        return s;
    }
}
