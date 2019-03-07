package com.company;

import java.util.ArrayList;

public class Interpreter {
    public Interpreter() {

    }

    public String interpret(ArrayList<Object> input) {
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) instanceof OPS) {
                var temp = input;
                input = ((OPS) input.get(i)).getFunction().apply(new ArrayList<>(input.subList(0, i + 1)));
                input.addAll(temp.subList(i + 1, temp.size()));
                i -= temp.size() - input.size();
            }
        }
        return convert(input);
    }

    private String convert(ArrayList<Object> input) {
        var output = "";
        for (Object o : input) {
            if (o instanceof Double) {
                output += String.valueOf(o);
            } else if (o instanceof Wrapped) {
                output += convert(((Wrapped) o).unwrap());
                output += "WRAP";
            } else {
                output += o.toString();
            }
            output += " ";
        }
        return output;
    }
}
