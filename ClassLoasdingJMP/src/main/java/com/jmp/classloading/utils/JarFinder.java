package com.jmp.classloading.utils;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFinder {
    private static JarFinder jarFinder = new JarFinder();
    private String jarPath;

    private JarFinder() {
    }

    public static JarFinder getInstance() {
        return jarFinder;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public JarFile getJarByUserEnteredPath() {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(new File(jarPath));
        } catch (IOException e) {
            System.out.println("Specified JAR was not found.");
        }
        return jarFile;
    }
}