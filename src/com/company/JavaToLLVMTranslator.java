package com.company;

import antlr.JavaGenerateLLVMCodeVisitor;
import antlr.JavaLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class JavaToLLVMTranslator {

    public JavaToLLVMTranslator() {
        CountLabels.resetCounters();
    }

    public String translate(String source) {
        //System.out.println(source);
        var javaLexer = new JavaLexer(CharStreams.fromString(source));
        var commonTokenStream = new CommonTokenStream(javaLexer);
        var javaParser = new antlr.JavaParser(commonTokenStream);

        var fileContext = javaParser.compilationUnit();
        var result = new StringBuilder();
        var visitor = new JavaGenerateLLVMCodeVisitor(result);
        visitor.visit(fileContext);
        return result.toString();
    }
}
