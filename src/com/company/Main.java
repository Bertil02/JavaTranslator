package com.company;

public class Main {

    public static void main(String[] args) {
	    String fileString = "public class Main { int a = 2; int b = 3; int c = a+b;}";
        JavaToLLVMTranslator context = new JavaToLLVMTranslator();
        String result = context.translate(fileString);
        System.out.println(result);


    }
}
