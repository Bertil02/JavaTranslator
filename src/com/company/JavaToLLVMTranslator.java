package com.company;

import antlr.JavaBaseVisitor;
import antlr.JavaCodeGeneratingVisitor;
import antlr.JavaLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class JavaToLLVMTranslator {

    public JavaToLLVMTranslator() {
        LabelCounterUtil.resetCounters();
    }

    public String translate(String source) {
        var javaLexer = new JavaLexer(CharStreams.fromString(source));
        var commonTokenStream = new CommonTokenStream(javaLexer);
        var javaParser = new antlr.JavaParser(commonTokenStream);

        var fileContext = javaParser.compilationUnit();
        var result = new StringBuilder();
        var visitor = new JavaCodeGeneratingVisitor(result);
        visitor.visit(fileContext);
        //System.out.println(result.toString());
        return result.toString();
    }
}
