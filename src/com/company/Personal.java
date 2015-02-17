package com.company;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by IDEA on 17/02/15.
 */
public class Personal extends IOmethods  implements DbObject {
    protected final int nameLen = 10, cityLen = 10;
    protected String SSN, name, city;
    protected int year;
    protected long salary;
    protected final int size = 9 * 2 + nameLen * 2 + cityLen * 2 + 4 + 8;

    Personal() {

    }

    Personal(String ssn, String n, String c, int y, long s) {
        SSN = ssn;
        name = n;
        city = c;
        year = y;
        salary = s;
    }

    public int size() {
        return size;
    }

    public boolean equals(Object pr) {
        return SSN.equals(((Personal)pr).SSN);
    }


    @Override
    public void writeToFile(RandomAccessFile out) throws IOException {
        writeString(SSN, out);
        writeString(name, out);
        writeString(city, out);
        out.writeInt(year);
        out.writeLong(salary);
    }

    @Override
    public void readFromFile(RandomAccessFile in) throws IOException {
        SSN = readString(9, in);
        name = readString(nameLen, in);
        city = readString(cityLen, in);
        year = in.readInt();
        salary = in.readLong();
    }


    @Override
    public void readFromConsole() throws IOException {
        System.out.println("enter ssn:");
        SSN = readLine();
        while(SSN.length() != 9) {
            System.out.println("ssn must be 9 digits!");
            SSN = readLine();
        }
        System.out.println("name: ");
        name = readLine();
        for(int i = name.length(); i < nameLen; i++) {
            name += " ";
        }
        System.out.println("city: ");
        city = readLine();
        for(int i = city.length(); i < cityLen; i++) {
            city += " ";
        }
        System.out.println("birth year: ");
        year = Integer.valueOf(readLine().trim()).intValue();
        System.out.println("salary: ");
        salary = Long.valueOf(readLine().trim()).longValue();
    }

    @Override
    public void writeLegibly() throws IOException {
        System.out.println("SSN  " + SSN);
    }

    @Override
    public void readKey() throws IOException {
        System.out.println("enter ssn: ");
        SSN = readLine();
    }

    @Override
    public void copy(DbObject[] d) {
        d[0] = new Personal(SSN, name, city, year, salary);
    }
}
