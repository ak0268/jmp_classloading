import com.jmp.classloading.beans.InternalJarBean;
import com.jmp.classloading.classloader.CustomClassLoader;
import com.jmp.classloading.utils.JarFinder;
import iface.ICustomLoadable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static ICustomLoadable loadedImplementation;

    public static void main(String[] args) {
        loadedImplementation = new InternalJarBean();
        showMainMenu();
    }

    public void configureApplication() {

    }

    public static void execute() {
        loadedImplementation.print();
    }

    public static void showMainMenu() {
        System.out.println("1. Execute Current Implementation");
        System.out.println("2. Change Implementation");
        System.out.println("3. Exit");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String result = reader.readLine();
            int input = Integer.parseInt(result);

            switch(input) {
                case 1 : execute();
                         break;
                case 2 : showImplementationMenu();
                         break;
                case 3 : System.exit(0);
            }
            showMainMenu();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch(NumberFormatException e){
            System.err.println("Invalid Format!");
        }
    }

    public static void showImplementationMenu() {
        System.out.println("Specify JAR PATH:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String enteredPath = reader.readLine();
            if (new File(enteredPath).exists()) {
               System.out.println("JAR found. Specify class name:");
               String enteredClassName = reader.readLine();
               JarFinder.getInstance().setJarPath(enteredPath);
               replaceImplementation(enteredClassName);
            } else {
                System.out.println("JAR not found. Verify entered path.");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void replaceImplementation(String className) {
        Object loadedBean = null;
        try {
            loadedBean = Class.forName(className, true, CustomClassLoader.getInstance()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            System.out.println("No such class found in a specified JAR.");
        }
        loadedImplementation = (ICustomLoadable) loadedBean;
    }
}