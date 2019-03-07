package com.company;

import java.util.ArrayList;
import java.util.List;

public class Formatter {
    private Tokenizer tokenizer;
    private Interpreter interpreter;
    private Compiler compiler;
    private ArrayList<Object> items;
    private String output;

    public Formatter(String s) throws Exception {
        this.tokenizer = new Tokenizer();
        this.interpreter = new Interpreter();
        List<Object> list = new ArrayList<>();
        for (String s1 : s.split("\\s")) {
            if (filterEmpty(s1)) {
                Object itemize = itemize(s1);
                list.add(itemize);
            }
        }
        this.items = new ArrayList<Object>(list);
        this.output = interpreter.interpret(this.items);
    }

    private Boolean filterEmpty(String s) {
        return !toString().isEmpty();
    }

    private Object itemize(String s) throws Exception {
        try{
            return Double.parseDouble(s);
        }catch(Exception e) {}
        if (s.length() > 2) {
            if (s.charAt(0) == ';' && s.charAt(s.length() - 1) == ';') {
                return new Variable(s.substring(1, s.length() - 1));
            }
        }
        return this.tokenizer.parseToken(s);
    }

    public String getOutput() {
        return output;
    }
}
