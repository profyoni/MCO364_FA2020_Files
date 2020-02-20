package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
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
