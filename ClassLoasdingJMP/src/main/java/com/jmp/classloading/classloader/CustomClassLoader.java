package com.jmp.classloading.classloader;

import com.jmp.classloading.utils.JarFinder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomClassLoader extends ClassLoader {

    public static final String CLASS_EXTENSION = ".class";
    private static CustomClassLoader customClassLoader = new CustomClassLoader();

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        JarFile jarFile = JarFinder.getInstance().getJarByUserEnteredPath();
        if (jarFile != null) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry != null) {
                    String entryName = entry.getName();
                    if (!entry.isDirectory() && entryName.contains(name + CLASS_EXTENSION)) {
                        try {
                            InputStream jarFileInputStream = jarFile.getInputStream(entry);
                            byte[] array = IOUtils.toByteArray(jarFileInputStream);
                            return defineClass(name, array, 0, array.length);
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        }
        throw new ClassNotFoundException();
    }

    private CustomClassLoader() {
        System.out.println("TRACE: CustomClassLoader instance was created.");
    }

    public static CustomClassLoader getInstance() {
        return customClassLoader;
    }
}