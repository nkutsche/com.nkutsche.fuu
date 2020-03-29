package com.nkutsche.fuu.exec;

import com.nkutsche.fuu.core.ArgumentConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JExec {
    
    public static void main(String[] args){
        String className = args[0];


        ArrayList<String> argList = new ArrayList<String>(Arrays.asList(args));

        argList.remove(0);

        String[] invokeArgs = argList.toArray(new String[argList.size()]);

        invokeArgs = new ArgumentConverter(invokeArgs).convertArgs();

        ClassLoader classloader = JExec.class.getClassLoader();
        try {
            Class<?> clazz = classloader.loadClass(className);
            Method m = clazz.getMethod("main", String[].class);
            m.invoke(null, invokeArgs);

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("The class " + className + " is not executable!");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Failed to invoke static main method on class " + className + "!");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Failed to invoke static main method on class " + className + "!");
            e.printStackTrace();
        }
    }
}
