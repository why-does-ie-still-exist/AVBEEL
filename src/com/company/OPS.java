package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public enum OPS {
    WRAP(false, OPS::wrap),
    ABS(true, (ArrayList<Object> input) -> {
        return unarytemplate(input, (x) -> (Math.abs((Double) x)));
    }),
    PLUS("+", true, (ArrayList<Object> input) -> {
        return binarytemplate(input, (a, b) -> (((Double) a + (Double) b)));
    }),
    MINUS("-", false, (ArrayList<Object> input) -> {
        return binarytemplate(input, (a, b) -> (((Double) a - (Double) b)));
    }),
    DIV("/", false, (ArrayList<Object> input) -> {
        return binarytemplate(input, (a, b) -> (((Double) a / (Double) b)));
    }),
    MULT("*", true, (ArrayList<Object> input) -> {
        return binarytemplate(input, (a, b) -> (((Double) a * (Double) b)));
    }),
    NEGATE(false, (ArrayList<Object> input) -> {
        return unarytemplate(input, (x) -> (0.0 - ((Double) x)));
    }),
    RECIP(false, (ArrayList<Object> input) -> {
        return unarytemplate(input, (x) -> (1.0 / (Double) x));
    }),
    FLOOR(false, (ArrayList<Object> input) -> {
        return unarytemplate(input, (x) -> (Math.floor((Double) x)));
    }
    );

    private String token;
    private FUNC function;
    private char first;
    private int numerical;
    private Boolean binarycommutivity;

    OPS(Boolean binarycommutative, FUNC f) {
        this.function = f;
        this.binarycommutivity = binarycommutative;
        this.token = this.name().toUpperCase();
        this.first = this.token.charAt(0);
        this.numerical = this.first;
    }

    OPS(String newtoken, Boolean commutative, FUNC f) {
        this.function = f;
        this.binarycommutivity = commutative;
        this.token = newtoken;
        this.first = this.token.charAt(0);
        this.numerical = this.first;
    }

    public static ArrayList<Object> wrap(ArrayList<Object> input) {
        return new ArrayList<Object>(Arrays.asList(new Wrapped(new ArrayList<>(input.subList(0, input.size() - 1)))));
    }

    public static ArrayList<Object> unarytemplate(ArrayList input, Unary u) {
        if (input.get(input.size() - 2) instanceof Variable || input.get(input.size() - 2) instanceof Wrapped) {
            return OPS.wrap(input);
        }
        var list = new ArrayList<>(input.subList(0, input.size() - 2));
        list.add(u.apply(input.get(input.size() - 2)));
        return list;
    }

    public static ArrayList<Object> binarytemplate(ArrayList input, Binary b) {
        if (input.get(input.size() - 2) instanceof Variable || input.get(input.size() - 2) instanceof Wrapped || input.get(input.size() - 3) instanceof Variable || input.get(input.size() - 3) instanceof Wrapped) {
            return OPS.wrap(input);
        }
        var list = new ArrayList<>(input.subList(0, input.size() - 3));
        list.add(b.apply(input.get(input.size() - 3), input.get(input.size() - 2)));
        return list;
    }

    public String toString() {
        return this.token;
    }

    public int getNumerical() {
        return this.numerical;
    }

    public FUNC getFunction() {
        return this.function;
    }
}