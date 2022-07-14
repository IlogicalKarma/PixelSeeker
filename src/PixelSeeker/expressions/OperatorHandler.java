package PixelSeeker.expressions;

import PixelSeeker.storage.Data;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

import static PixelSeeker.storage.Data.getNum;
import static PixelSeeker.storage.Data.getStr;

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

    public static abstract class Operator implements Element {
        protected String[] representations;
        public boolean verify(String string) {
            for (int i = 0; i < representations.length; i++)
                if(representations[i].equals(string))
                    return true;
            return false;
        }
        public abstract Data apply(Data before, Data after) throws UnexpectedDataTypeException, RuntimeException;
        protected Data defaultBehaviour(String[][] types) throws UnexpectedDataTypeException{
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

        @Override
        public String toString() {
            return representations[0];
        }
    }
    private static class Equals extends Operator{
        private Equals(){
            representations = new String[]{"=="};
        }

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() == after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if (before.toStr().equals(after.toStr()))
                    return getNum(1);
                return getNum(0);
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }

    }
    private static class NEquals extends Operator{
        private NEquals(){
            representations = new String[]{"!="};
        }

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() != after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if ((before.toStr()).equals(after.toStr()))
                    return getNum(0);
                return getNum(1);
            }
            return defaultBehaviour((new String[][]{ {"Num","Num"} }));
        }

    }
    private static class Divide extends Operator{
        private Divide(){
            representations = new String[]{"/"};
        }

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum()) {
                return getNum(before.toNum() / after.toNum());
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return getNum(before.toNum()* after.toNum());
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            if(before.isNum() && after.isNum())
                return getNum(before.toNum()% after.toNum());
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Sum
            if(before.isNum() && after.isNum())
                return getNum(before.toNum() + after.toNum());
            //Concat
            if(before.isStr() && after.isStr())
                return getStr(before.toStr() + after.toStr());
            //Offset num before
            if(before.isStr() && after.isNum()){
                if(after.toNum() < 0)
                    return minus.apply(before, getNum(Math.abs(after.toNum())));
                if(before.toStr().length() > after.toNum())
                    return getStr(before.toStr().substring(after.toNum()));
                return getStr(new String());
            }
            //Offset num after
            if(before.isNum() && after.isStr())
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //sub
            if(before.isNum() && after.isNum())
                return getNum(before.toNum() - before.toNum());
            //Offset num after
            if(before.isStr() && after.isNum()){
                if(after.toNum() < 0)
                    return plus.apply(before, getNum(Math.abs(after.toNum())));
                if(before.toStr().length() > after.toNum())
                    return getStr(before.toStr().substring(0,before.toStr().length() - after.toNum()));
                return getStr(new String());
            }
            //Offset num before
            if(before.isNum() && after.isStr())
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() < after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if (before.toStr().length() < after.toStr().length())
                    return getNum(1);
                return getNum(0);
            }
            //Str, Num
            if(before.isStr() && after.isNum()) {
                if (before.toStr().length() < after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Num, Str
            if(before.isNum() && after.isStr()){
                if (before.toNum() < after.toStr().length())
                    return getNum(1);
                return getNum(0);
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() > after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if (before.toStr().length() > after.toStr().length())
                    return getNum(1);
                return getNum(0);
            }
            //Str, Num
            if(before.isStr() && after.isNum()) {
                if (before.toStr().length() > after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Num, Str
            if(before.isNum() && after.isStr()){
                if (before.toNum() > after.toStr().length())
                    return getNum(1);
                return getNum(0);
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() <= after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if (before.toStr().length() <= after.toStr().length())
                    return getNum(1);
                return getNum(0);
            }
            //Str, Num
            if(before.isStr() && after.isNum()) {
                if (before.toStr().length() <= after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Num, Str
            if(before.isNum() && after.isStr()){
                if (before.toNum() <= after.toStr().length())
                    return getNum(1);
                return getNum(0);
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum() >= after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Str
            if(before.isStr() && after.isStr()) {
                if (before.toStr().length() >= after.toStr().length())
                    return getNum(1);
                return getNum(0);
            }
            //Str, Num
            if(before.isStr() && after.isNum()) {
                if (before.toStr().length() >= after.toNum())
                    return getNum(1);
                return getNum(0);
            }
            //Num, Str
            if(before.isNum() && after.isStr()){
                if (before.toNum() >= after.toStr().length())
                    return getNum(1);
                return getNum(0);
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

        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum()%2 == 1 && after.toNum()%2 == 1)
                    return  getNum(1);
                return getNum(0);
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
        public Data apply(Data before, Data after) throws UnexpectedDataTypeException{
            //Num
            if(before.isNum() && after.isNum()) {
                if (before.toNum()%2 == 1 || after.toNum()%2 == 1)
                    return getNum(1);
                return getNum(0);
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

        public Data apply(Data before, Data after){
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

        public Data apply(Data before, Data after) throws RuntimeException, UnexpectedDataTypeException{
            if(before.isArr() && after.isNum())
                return before.toArr()[after.toNum()];
            return defaultBehaviour((new String[][]{ {"Arr","Num"} }));
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
}
