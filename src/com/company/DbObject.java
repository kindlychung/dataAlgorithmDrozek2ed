package com.company;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by IDEA on 17/02/15.
 */
public interface DbObject {
    public void writeToFile(RandomAccessFile out) throws IOException;
    public void readFromFile(RandomAccessFile in) throws IOException;
    public void readFromConsole() throws IOException;
    public void writeLegibly() throws IOException;
    public void readKey() throws IOException;
    public void copy(DbObject[] db);
    public int size();
}
