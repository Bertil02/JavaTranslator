package com.company;

public class Main {

    public static void main(String[] args) {
	    String fileString = "public class Main {public int a = 23; int b = 32; int c = a+b;}";
        JavaToLLVMTranslator context = new JavaToLLVMTranslator();
        String result = context.translate(fileString);
        System.out.println(result);
    }
}
