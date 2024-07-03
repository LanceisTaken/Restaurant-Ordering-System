package com.example.demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class java_niofile {
    public static void main(String[] args) {
        Path f = Paths.get("C:\\Users\\User\\Downloads\\CSYear1Results.png");
        boolean irf = Files.isRegularFile(f);
        boolean ih = Files.isReadable(f);
        boolean iw = Files.isWritable(f);
        boolean ie = Files.isExecutable(f);
        boolean isl = Files.isSymbolicLink(f);

        System.out.println("File: " + f);
        System.out.println("Is regular file: " + irf);
        System.out.println("Is readable: " + ih);
        System.out.println("Is writable: " + iw);
        System.out.println("Is executable: " + ie);
        System.out.println("Is symbolic link: " + isl);

        Path dir = Paths.get("C:\\Users\\User\\Downloads");
        boolean id = Files.isDirectory(dir);
        boolean idirWritable = Files.isWritable(dir);

        System.out.println("Directory: " + dir);
        System.out.println("Is directory: " + id);
        System.out.println("Is writable: " + idirWritable);
    }
}
