package org.fundaciobit.genappsqltutorial.tutorial.utils.javaparser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * 
 * https://javaparser.org/
 * 
 * @author anadal
 *
 */
public class JavaParser {

    public static Map<String, String> getMethods(String fullClassName) {

        Map<String, String> bodymethodsByName = new HashMap<String, String>();

        String path = fullClassName.replaceAll("\\.", "/") + ".java";

        // System.out.println("PATH => ]" + path + "[");

        InputStream is = JavaParser.class.getClassLoader().getResourceAsStream(path);

        System.out.println(" IS: " + is);

        CompilationUnit compilationUnit = StaticJavaParser.parse(is);

        List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);

        for (MethodDeclaration m : methods) {
            // System.out.println(" =========== " + m.getNameAsString() + " ============");

            java.util.Optional<BlockStmt> code = m.getBody();

            // System.out.println(code.get());

            List<Node> codeLines = code.get().getChildNodes();

            StringBuilder str = new StringBuilder();

            for (Node n : codeLines) {
                str.append(n).append('\n');

            }

            bodymethodsByName.put(m.getNameAsString(), str.toString());

            // System.out.println(str.toString());
        }
        return bodymethodsByName;
    }
}
