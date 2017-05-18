package com.denis.learning.parser;

import jdk.nashorn.internal.runtime.ParserException;

public class ParserMain {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.addTokenInfo("sin|cos|exp|ln|sqrt", 1); // function
        tokenizer.addTokenInfo("\\(", 2); // open bracket
        tokenizer.addTokenInfo("\\)", 3); // close bracket
        tokenizer.addTokenInfo("[+-]", 4); // plus or minus
        tokenizer.addTokenInfo("[*/]", 5); // mult or divide
        tokenizer.addTokenInfo("\\^", 6); // raised
        tokenizer.addTokenInfo("[0-9]+",7); // integer number
        tokenizer.addTokenInfo("[a-zA-Z][a-zA-Z0-9_]*", 8); // variable

        try {
            tokenizer.tokenize(" sin(x) * (1 + var_12) ");

            for (Token tok : tokenizer.getTokens()) {
                System.out.println("" + tok.getToken() + " " + tok.getSequence());
            }
        }
        catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }
}
