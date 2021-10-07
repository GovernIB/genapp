package org.fundaciobit.genappsqltutorial.tutorial.utils.compiler;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 
 * @author anadal
 *
 */
public class JavaCompileAndExecute {

    public static Object compileAndExecute(String paquet, String className, String methodName,
            String source, ClassLoader cl) throws Exception {
        File sourceFile = null;
        try {
            // Save source in .java file.
            String tmpDir = System.getProperty("java.io.tmpdir");
            File root = new File(tmpDir); // new File("/java"); // On Windows running on C:\, this
                                          // is
                                          // C:\java.
            sourceFile = new File(root, paquet.replace('.', '/') + "/" + className + ".java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            //compiler.run(null, null, null, sourceFile.getPath());
            
            
            List<File> filesToCompile = new ArrayList<File>();
            filesToCompile.add(sourceFile);
            
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            
            StandardJavaFileManager stdFileManager =
                    compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> fileObjects = stdFileManager
                    .getJavaFileObjectsFromFiles(filesToCompile);

            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);

            CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, fileObjects);
            Boolean result = task.call();
            System.out.println("Success: " + result);
            if (!result) {
                
                
                for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                    System.err.println(" =================================== ");
                  System.err.println(diagnostic.getCode());
                  System.err.println(diagnostic.getKind());
                  System.err.println(diagnostic.getPosition());
                  System.err.println(diagnostic.getStartPosition());
                  System.err.println(diagnostic.getEndPosition());
                  System.err.println(diagnostic.getSource());
                  System.err.println(diagnostic.getMessage(null));

                }
                

                
                
                throw new Exception("Compilation FAILED");
            }
            
            

            // Load and instantiate compiled class.
            URLClassLoader classLoaderFile = URLClassLoader
                    .newInstance(new URL[] { root.toURI().toURL() });
            
            
            JoinClassLoader classLoader = new JoinClassLoader(cl, classLoaderFile);
            
            
            Class<?> cls = Class.forName(paquet + "." + className, true, classLoader); 
            Object instance = cls.newInstance();
            Method instanceMethod = cls.getMethod(methodName);
            instanceMethod.invoke(instance); // Should print "!!!!!"
            return instance;
        } finally {
            if (sourceFile != null) {
                //sourceFile.delete();
            }
        }
    }
}
