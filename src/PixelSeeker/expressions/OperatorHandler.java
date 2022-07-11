package PixelSeeker.expressions;

import PixelSeeker.DataStorage.ArrayElement;
import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NumericalElement;
import PixelSeeker.DataStorage.StringElement;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

public class OperatorHandler {
    private OperatorHandler(){}
    private static OperatorHandler operators = new OperatorHandler();
    private final Equals equals = new Equals();
    private final NEquals nequals = new NEquals();
    private final Divide divide = new Divide();
    private final Multiply multiply = new Multiply();
    private final Remainder remainder = new Remainder();
    private final Plus plus = new Plus();
    private final Minus minus = new Minus();
    private final Less less = new Less();
    private final More more = new More();
    private final LessE lessE = new LessE();
    private final MoreE moreE = new MoreE();
    private final And and = new And();
    private final Or or = new Or();
    private final Assign assign = new Assign();
    private final Operator[] operatorsArray = {equals,nequals,divide,minus,multiply,remainder,plus,less,more,lessE,moreE,and,or,assign};
    private final char[] whitelist = {'+','-','=','/','%','*','<','>','&','|','!'};
    public static OperatorHandler getInstance() {
        return operators;
    }
    public Operator operatorLookup(String id){
        for(int i = 0; i < operatorsArray.length; i++){
            if(operatorsArray[i].verify(id)) {
                return operatorsArray[i];
            }
        }
        return null;
    }
    public boolean operatorWhitelist(char c){
        for(char w : whitelist)
            if(c == w)
                return true;
        return false;
    }
    public abstract class Operator{
        public abstract boolean verify(String string);
        public abstract Element apply(Element before, Element after) throws UnexpectedDataTypeException;
    }
    public class Equals extends Operator{
        private String[] representations = {"=="};
        private Equals(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() == ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Before: " + ((NumericalElement) before).get() + "(numerical required) " + ", After: " + ((NumericalElement) after).get() + "(numerical required)");
        }

    }
    public class NEquals extends Operator{
        private String[] representations = {"!="};
        private NEquals(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() != ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical");
        }

    }
    public class Divide extends Operator{
        private String[] representations = {"/"};
        private Divide(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum()) {
                return new NumericalElement(((NumericalElement) before).get() / ((NumericalElement) after).get());
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical");
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
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return new NumericalElement(((NumericalElement) before).get()*((NumericalElement) after).get());
            throw new UnexpectedDataTypeException("Requires: numerical, numerical");
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
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return new NumericalElement(((NumericalElement) before).get()%((NumericalElement) after).get());
            throw new UnexpectedDataTypeException("Requires: numerical, numerical");
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
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Sum
            if(before.isNum() && after.isNum())
                return new NumericalElement(((NumericalElement) before).get()+((NumericalElement) after).get());
            //Concat
            if(before.isString() && after.isString())
                return new StringElement(((StringElement) before).get()+((StringElement) after).get());
            //Offset before
            if(before.isString() && after.isNum()){
                if(((NumericalElement) after).get() < 0)
                    return minus.apply(before, new NumericalElement(Math.abs(((NumericalElement) after).get())));
                if(((StringElement) before).getLength() > ((NumericalElement) after).get())
                    return new StringElement(((StringElement) before).get().substring((int) ((NumericalElement) after).get()));
                return new StringElement(new String());
            }
            //Offset after
            if(before.isNum() && after.isString())
                return apply(after,before);
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string/numerical, string/string, numerical");
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
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //sub
            if(before.isNum() && after.isNum())
                return new NumericalElement(Integer.valueOf(((NumericalElement) before).get()-((NumericalElement) after).get()));
            //Offset before
            if(before.isString() && after.isNum()){
                if(((NumericalElement) after).get() < 0)
                    return plus.apply(before, new NumericalElement(Math.abs(((NumericalElement) after).get())));
                if(((StringElement) before).get().length() > ((NumericalElement) after).get())
                    return new StringElement(((StringElement) before).get().substring(0,((StringElement) before).get().length()-((NumericalElement) after).get()));
                return new StringElement();
            }
            //Offset after
            if(before.isNum() && after.isString())
                return apply(after,before);
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, numerical/numerical, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class Less extends Operator{
        private String[] representations = {"<"};
        private Less(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() < ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length() < ((StringElement) after).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class More extends Operator{
        private String[] representations = {">"};
        private More(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() > ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length() > ((StringElement) after).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class LessE extends Operator{
        private String[] representations = {"<="};
        private LessE(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() <= ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length() <= ((StringElement) after).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class MoreE extends Operator{
        private String[] representations = {">="};
        private MoreE(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() >= ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length() >= ((StringElement) after).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class And extends Operator{
        private String[] representations = {"&&"};
        private And(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get()%2 == 1 && ((NumericalElement) after).get()%2 == 1)
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length()%2 == 1 && ((StringElement) after).get().length()%2 == 1)
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class Or extends Operator{
        private String[] representations = {"||"};
        private Or(){}
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get()%2 == 1 || ((NumericalElement) after).get()%2 == 1)
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().length()%2 == 1 || ((StringElement) after).get().length()%2 == 1)
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            throw new UnexpectedDataTypeException("Requires: numerical, numerical/string, string");
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    public class Assign extends Operator{
        private String[] representations = {"="};

        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public Element apply(Element before, Element after){
            after.copyTo(before);
            return before;
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
}
