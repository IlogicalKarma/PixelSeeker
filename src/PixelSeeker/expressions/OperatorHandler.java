package PixelSeeker.expressions;

import PixelSeeker.DataStorage.ArrayElement;
import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NumericalElement;
import PixelSeeker.DataStorage.StringElement;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

public class OperatorHandler {
    private OperatorHandler(){}
    private static final OperatorHandler operators = new OperatorHandler();
    private static final Equals equals = new Equals();
    private static final NEquals nequals = new NEquals();
    private static final Divide divide = new Divide();
    private static final Multiply multiply = new Multiply();
    private static final Remainder remainder = new Remainder();
    private static final Plus plus = new Plus();
    private static final Minus minus = new Minus();
    private static final Less less = new Less();
    private static final More more = new More();
    private static final LessE lessE = new LessE();
    private static final MoreE moreE = new MoreE();
    private static final And and = new And();
    private static final Or or = new Or();
    private static final Assign assign = new Assign();
    private static final Index index = new Index();
    private static final Operator[] operatorsArray = {equals,nequals,divide,minus,multiply,remainder,plus,less,more,lessE,moreE,and,or,assign,index};
    public static final char[] WHITELIST = {'+','-','=','/','%','*','<','>','&','|','!','.'};
    public static Operator operatorLookup(String id){
        for(int i = 0; i < operatorsArray.length; i++){
            if(operatorsArray[i].verify(id)) {
                return operatorsArray[i];
            }
        }
        return null;
    }
    public static boolean operatorWhitelist(char c){
        for(char w : WHITELIST)
            if(c == w)
                return true;
        return false;
    }
    public static abstract class Operator{
        protected String[] representations;
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public abstract Element apply(Element before, Element after) throws UnexpectedDataTypeException, RuntimeException;
        protected Element defaultBehaviour(String[][] types) throws UnexpectedDataTypeException{
            StringBuilder errorMessage = new StringBuilder("Operator accepts these configurations: ");
            boolean first = true;
            for(int i = 0; i < types.length && types[i].length == 2; i++) {
                if (first)
                    first = false;
                else
                    errorMessage.append(" | ");
                errorMessage.append(types[i][0])
                            .append(' ')
                            .append(this.getClass().getSimpleName())
                            .append(' ')
                            .append(types[i][1]);
            }
            throw new UnexpectedDataTypeException(errorMessage.toString());
        }

    }
    private static class Equals extends Operator{
        private Equals(){
            representations = new String[]{"=="};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() == ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().equals(((StringElement) after).get()))
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }

    }
    private static class NEquals extends Operator{
        private NEquals(){
            representations = new String[]{"!="};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get() != ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Str
            if(before.isString() && after.isString()) {
                if (((StringElement) before).get().equals(((StringElement) after).get()))
                    return new NumericalElement(0);
                return new NumericalElement(1);
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }

    }
    private static class Divide extends Operator{
        private Divide(){
            representations = new String[]{"/"};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum()) {
                return new NumericalElement(((NumericalElement) before).get() / ((NumericalElement) after).get());
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }

        @Override
        public String toString() {
            return representations[0];
        }
    }

    private static class Multiply extends Operator {
        private Multiply(){
            representations = new String[]{"*"};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return new NumericalElement(((NumericalElement) before).get()*((NumericalElement) after).get());
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    private static class Remainder extends Operator {
        private Remainder(){
            representations = new String[]{"%"};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return new NumericalElement(((NumericalElement) before).get()%((NumericalElement) after).get());
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    private static class Plus extends Operator{
        private Plus(){
            representations = new String[]{"+"};
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
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }

    private static class Minus extends Operator {
        private Minus(){
            representations = new String[]{"-"};
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
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class Less extends Operator{
        private Less(){
            representations = new String[]{"<"};
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
            //Str, Int
            if(before.isString() && after.isNum()) {
                if (((StringElement) before).get().length() < ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Int, Str
            if(before.isNum() && after.isString()){
                if (((NumericalElement) after).get() < ((StringElement) before).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class More extends Operator{
        private More(){
            representations = new String[]{">"};
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
            //Str, Int
            if(before.isString() && after.isNum()) {
                if (((StringElement) before).get().length() > ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Int, Str
            if(before.isNum() && after.isString()){
                if (((NumericalElement) after).get() > ((StringElement) before).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class LessE extends Operator{
        private LessE(){
            representations = new String[]{"<="};
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
            //Str, Int
            if(before.isString() && after.isNum()) {
                if (((StringElement) before).get().length() <= ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Int, Str
            if(before.isNum() && after.isString()){
                if (((NumericalElement) after).get() <= ((StringElement) before).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class MoreE extends Operator{
        private MoreE(){
            representations = new String[]{">="};
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
            //Str, Int
            if(before.isString() && after.isNum()) {
                if (((StringElement) before).get().length() >= ((NumericalElement) after).get())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            //Int, Str
            if(before.isNum() && after.isString()){
                if (((NumericalElement) after).get() >= ((StringElement) before).get().length())
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour(new String[][]{ {"Num", "Num"}, {"Num", "Str"}, {"Str", "Num"}, {"Str", "Str"} });
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class And extends Operator{
        private And(){
            representations = new String[]{"&&"};
        }

        public Element apply(Element before, Element after) throws UnexpectedDataTypeException{
            //Int
            if(before.isNum() && after.isNum()) {
                if (((NumericalElement) before).get()%2 == 1 && ((NumericalElement) after).get()%2 == 1)
                    return new NumericalElement(1);
                return new NumericalElement(0);
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class Or extends Operator{
        private Or(){
            representations = new String[]{"||"};
        }
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
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class Assign extends Operator{
        private Assign(){
            representations = new String[]{"="};
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
    private static class Index extends Operator{
        private Index(){
            representations = new String[]{"."};
        }

        public Element apply(Element before, Element after) throws RuntimeException, UnexpectedDataTypeException{
            if(before.isArray() && after.isNum())
                return ((ArrayElement) before).getElement(((NumericalElement) after).get());
            return defaultBehaviour((new String[][]{ {"Arr","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
}
