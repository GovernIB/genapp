package org.fundaciobit.genappsqltutorial.tutorial.otherstests;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestCompile {

    public static void main(String[] args) {
        try {
        
            
            
         // Prepare source somehow.
            String source = "package test;"
                    + "public class Test {"
                    + "  static { "
                    + "    System.out.print(\"hello \");"
                    + "  }"                    
                    + "  public Test() {"
                    + "    System.out.print(\"world \");"
                    + "  } "
                    + "  public void displayFinal() throws Exception {"
                    + "    System.out.println(\"!!!!!\");"
                    + "    org.fundaciobit.genappsqltutorial.persistence.test.SQL01Select test;"
                    + "    test = new org.fundaciobit.genappsqltutorial.persistence.test.SQL01Select();"
                    + "    test.fullSelect();"
                    + "    test.close();"
                    
                    + "  }"
                    + "}";

            // Save source in .java file.
            String tmpDir = System.getProperty("java.io.tmpdir");
            File root = new File(tmpDir); //new File("/java"); // On Windows running on C:\, this is C:\java.
            File sourceFile = new File(root, "test/Test.java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());

            // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
            Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
            Object instance = cls.newInstance(); // Should print "world".
            Method instanceMethod = cls.getMethod("displayFinal");
            instanceMethod.invoke(instance); // Should print "!!!!!"
            
            System.out.println(instance); // Should print "test.Test@hashcode".

        
        /*
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // Compiling the code

        File f = new File("HelloWorld.java");

        int result = compiler.run(null, null, null, f.getAbsolutePath());
        // "C:\\workspace\\Test\\src\\org\\test\\HelloWorld.java");
        
        compiler.

        System.out.println("result " + result);
        // Giving the path of the class directory where class file is generated..
        File classesDir = new File("genclasses");
        classesDir.mkdirs();
        // Load and instantiate compiled class.
        URLClassLoader classLoader;
        try {
            // Loading the class
            classLoader = URLClassLoader.newInstance(new URL[] { classesDir.toURI().toURL() });
            Class<?> cls;
            cls = Class.forName("org.test.HelloWorld", true, classLoader);
            // HelloWorld instance = (HelloWorld)cls.newInstance();
            // instance.displayMessage();

            Method instanceMethod = cls.getMethod("displayMessage");

            instanceMethod.invoke(null);
            */

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
