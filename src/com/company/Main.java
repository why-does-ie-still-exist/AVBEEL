package com.company;

public class Main {

    public static void main(String[] args) {
        try{
            System.out.println(new Formatter("2 WRAP 0 * NEGATE 1 + ;hi; 1 1 + ").getOutput());
        } catch (Exception e){
            System.out.println("ERROR:" + e.getMessage());
        }
    }
}
