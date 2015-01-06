package com.jmp.classloading.beans;
import iface.ICustomLoadable;

public class InternalJarBean implements ICustomLoadable {

    @Override
    public void print() {
        System.out.println("InternalJarBean was loaded.");
    }
}
