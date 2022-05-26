package PixelSeeker.expressions;

import java.util.Arrays;

public class OperatorHandler {
    private OperatorHandler(){}
    private static OperatorHandler operators = new OperatorHandler();
    private final Equals equals = new Equals();
    private final Divide divide = new Divide();
    private final Multiply multiply = new Multiply();
    private final Remainder remainder = new Remainder();
    private final Plus plus = new Plus();
    private final Minus minus = new Minus();
    private final Operator[] operatorsArray = {equals,divide,minus,multiply,remainder,plus};
    public static OperatorHandler getInstance() {
        return operators;
    }

    public Operator[] getOperatorsArray() {
        return operatorsArray;
    }

    public Divide getDivide() {
        return divide;
    }

    public Minus getMinus() {
        return minus;
    }

    public Equals getEquals() {
        return equals;
    }

    public Multiply getMultiply() {
        return multiply;
    }

    public Plus getPlus() {
        return plus;
    }

    public Remainder getRemainder() {
        return remainder;
    }
    public abstract class Operator{
        public abstract boolean verify(String string);
        public abstract int apply(int before, int after);
    }
    public class Equals extends Operator{
        private String[] representations = {"="};
        private Equals(){}
        public boolean verify(String string) {                                                                                                                                 
            for (int i = 0; i < representations.length; i++) {
                if (representations[i].equals(string))
                    return true;
            }
            return false;
        }

        public int apply(int before, int after) {
            if (before == after)
                return 1;
            return 0;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    public class Divide extends Operator{
        private String[] representations = {"/"};
        private Divide(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++) {
                if (representations[i].equals(string))
                    return true;
            }
            return false;
        }
        public int apply(int before, int after) {
            return before / after;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    public class Multiply extends Operator {
        private String[] representations = {"*"};
        private Multiply(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++) {
                if (representations[i].equals(string))
                    return true;
            }
            return false;
        }
        public int apply(int before, int after) {
            return before * after;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    public class Remainder extends Operator {
        private String[] representations = {"%"};
        private Remainder(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++) {
                if (representations[i].equals(string))
                    return true;
            }
            return false;
        }
        public int apply(int before, int after) {
            return before % after;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    public class Plus extends Operator{
        private String[] representations = {"+"};
        private Plus(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++) {
                if (representations[i].equals(string))
                    return true;
            }
            return false;
        }
        public int apply(int before, int after) {
            return before + after;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    public class Minus extends Operator {
        private String[] representations = {"-"};
        private Minus(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++) {
                if (representations[i] == string)
                    return true;
            }
            return false;
        }
        public int apply(int before, int after) {
            return before - after;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
}
