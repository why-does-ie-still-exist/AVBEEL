package com.company;

import java.util.ArrayList;

public class Wrapped {
    private ArrayList<Object> contents;
    public Wrapped(ArrayList<Object> input){
        this.contents = input;
    }
    public ArrayList unwrap(){
        return this.contents;
    }
}
