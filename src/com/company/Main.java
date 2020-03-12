package com.company;

import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

class Zigwig implements Serializable {
    int age; // -100 < age < 100
    float height;
    String name; // note that all primitives and String is Serializable
    transient char abbreviation; // derivable from name...transient marks it so it will not serilized during default Servialization

    void init()
    {
        abbreviation = name.charAt(0);
    }
    Zigwig(int age, float height, String name)
    {
        this.age = age; this.height=height;this.name = name;
        init();
    }

    private void writeObject(ObjectOutputStream out) // extralinguistic mechanism: calling private method from outside class, instanitiating object with no c-tor
            throws IOException
    {
        out.writeInt(age);
        out.writeFloat(height);
        out.writeObject(name);
    }

    private void readObject(java.io.ObjectInputStream in) // LOOK MA - no c-tor!
            throws IOException, ClassNotFoundException
    {
        age = in.readInt();
        if (age < -100)
            throw new IOException();
        height = in.readFloat();
        name = (String) in.readObject(); // data bounds checks just like we do in a c-tor
        init();
    }
}

// Set interface and Serilizable and custom Serilaiztion
class MySet<T> implements Set<T>, Serializable
{
    // backing store is array

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // deep copy of elts in Set
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Zigwig zw = new Zigwig(96, 7, "Bob");
        Zigwig zw2 = new Zigwig(6, 17, "Slob");

        try(FileOutputStream fos = new FileOutputStream("zigwigsDecomposed.obj");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)) // Decorator Design Pattern
        {
            oos.writeObject(zw);
            oos.writeObject(zw2);
        }

        try(FileInputStream fis = new FileInputStream("zigwigsDecomposed.obj");
            ObjectInputStream ois = new ObjectInputStream(fis)) // Decorator Design Pattern
        {
            Zigwig zw3 = (Zigwig) ois.readObject(); // deserialization...DOES NOT require a Zigwig no-arg c-tor
            Zigwig zw4 = (Zigwig) ois.readObject(); // deserialization...DOES NOT require a Zigwig no-arg c-tor

            System.out.println(zw3.name);
            System.out.println(zw4.name);

        }


    }

    public static void main2(String[] args) throws IOException {
       // print("mco364am.txt", "ABCDEFGHIJKLMNOPqrstuv");
        //System.out.println(read("mco364am.txt"));

        try (PrintWriter pw = new PrintWriter("mco364aa.txt")) {
            for (char c = 'A'; c<='z'; c++)
                pw.println(c + "Bless the Queen");
        }
        try (Scanner scanner = new Scanner(new File("mco364aa.txt"))) {
            while (scanner.hasNextLine())
            {
                System.out.println(scanner.nextLine());
            }
        }
    }

    public static void printOld(String fileName, String s) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            fos.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public static void print(String fileName, String s) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) // try with resources
        {
            fos.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) // try with resources
        {
            return new String(fis.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
