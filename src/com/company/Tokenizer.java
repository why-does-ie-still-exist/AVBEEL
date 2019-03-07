package com.company;

import java.util.Arrays;
import java.util.Comparator;

public class Tokenizer {
    private OPS[] tokens = OPS.values();

    public Tokenizer() {
        LazySortTokens();
    }

    private void LazySortTokens() {
        var c = Comparator.comparing((OPS op) -> op.getNumerical());
        Arrays.sort(this.tokens, c);
    }

    public OPS parseToken(String s) throws Exception {
        if (s.length() == 0) {
            throw new Exception("Token passed is empty");
        }
        var stringFirst = s.charAt(0);
        var num = (int) stringFirst;
        var firstInArray = this.tokens[0];
        var lastInArray = this.tokens[this.tokens.length - 1];
        if (firstInArray.toString().equals(s)) {
            return firstInArray;
        }
        if (lastInArray.toString().equals(s)) {
            return lastInArray;
        }
        if (num < firstInArray.getNumerical()) {
            throw new Exception("Could not find matching token: " + s);
        }
        if (num > lastInArray.getNumerical()) {
            throw new Exception("Could not find matching token: " + s);
        }
        var start = lazyTokenIndex(stringFirst);
        if (this.tokens[start].toString().equals(s)) {
            return this.tokens[start];
        }
        lookabove:
        {
            for (int i = start + 1; i < this.tokens.length; i++) {
                if (this.tokens[i].getNumerical() == num) {
                    if (this.tokens[i].toString().equals(s)) {
                        return this.tokens[i];
                    }
                } else {
                    break lookabove;
                }
            }
        }
        lookbelow:
        {
            for (int i = start - 1; i < this.tokens.length; i--) {
                if (this.tokens[i].getNumerical() == num) {
                    if (this.tokens[i].toString().equals(s)) {
                        return this.tokens[i];
                    }
                } else {
                    break lookbelow;
                }
            }
        }
        throw new Exception("Could not find matching token: " + s);
    }

    private int lazyTokenIndex(char c) throws Exception {
        var val = (int) c;
        return recurseLTI(val, Arrays.copyOfRange(this.tokens, 1, this.tokens.length));
    }

    private int recurseLTI(int val, OPS[] a) throws Exception {
        int mid = a[a.length / 2].getNumerical();
        if (a.length == 0) {
            throw new Exception("Token Starting with: " + (char) val + " is not found");
        } else if (a.length == 1) {
            if (a[0].getNumerical() == val) {
                return 1;
            }
            throw new Exception("Token Starting with: " + (char) val + " is not found");
        } else if (mid == val) {
            return a.length / 2;
        } else if (mid > val) {
            return (recurseLTI(val, lowerHalf(a)));
        } else if (mid < val) {
            return (recurseLTI(val, upperHalf(a))) + a.length / 2;
        }
        throw new Exception("How did you get here??");
    }

    private OPS[] upperHalf(OPS[] a) {
        return Arrays.copyOfRange(a, (a.length / 2), a.length);
    }

    private OPS[] lowerHalf(OPS[] a) {
        return Arrays.copyOfRange(a, 0, a.length / 2);
    }
}
