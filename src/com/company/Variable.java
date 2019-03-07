package com.company;

public class Variable {
    private String name;

    public Variable(String s){
        this.name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
