package PixelSeeker.expressions;

import PixelSeeker.Parcel;
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
    private final Operator[] operatorsArray = {equals,nequals,divide,minus,multiply,remainder,plus,less,more,lessE,moreE,and,or};
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
        public abstract Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException;
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

        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) (before.getObject()) == (int) (after.getObject()))
                    return new Parcel(Integer.valueOf(1),Integer.class);
                return new Parcel(Integer.valueOf(0),Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + "(numerical required) " + ", After: " + after.getClassObject() + "(numerical required)");
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

        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) (before.getObject()) != (int) (after.getObject()))
                    return new Parcel(Integer.valueOf(1),Integer.class);
                return new Parcel(Integer.valueOf(0),Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + "(numerical required) " + ", After: " + after.getClassObject() + "(numerical required)");
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {

                return new Parcel(Integer.valueOf((int) (before.getObject()) / (int) (after.getObject())), Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + "(numerical required) " + ", After: " + after.getClassObject() + "(numerical required)");
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class))
                return new Parcel(Integer.valueOf((int) before.getObject()*(int) after.getObject()),Integer.class);
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + "(numerical required) " + ", After: " + after.getClassObject() + "(numerical required)");
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class))
                return new Parcel(Integer.valueOf((int) before.getObject()%(int) after.getObject()),Integer.class);
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + "(numerical required) " + ", After: " + after.getClassObject() + "(numerical required)");
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Sum
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class))
                return new Parcel(Integer.valueOf((int) before.getObject()+(int) after.getObject()),Integer.class);
            //Concat
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class))
                return new Parcel(before.getObject() + (String) after.getObject(), String.class);
            //Offset before
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(Integer.class)){
                if((int) after.getObject() < 0)
                    return minus.apply(before, new Parcel(Math.abs((int) after.getObject()), Integer.class));
                if(((String) before.getObject()).length() > (int) after.getObject())
                    return new Parcel(((String) before.getObject()).substring((int) after.getObject()),String.class);
                return new Parcel(new String(),String.class);
            }
            //Offset after
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(String.class))
                return apply(after,before);
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //sub
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class))
                return new Parcel(Integer.valueOf((int) before.getObject()-(int) after.getObject()),Integer.class);
            //Offset before
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(Integer.class)){
                if((int) after.getObject() < 0)
                    return plus.apply(before, new Parcel(Math.abs((int) after.getObject()), Integer.class));
                if(((String) before.getObject()).length() > (int) after.getObject())
                    return new Parcel(((String) before.getObject()).substring(0,((String) before.getObject()).length()-(int) after.getObject()),String.class);
                return new Parcel(new String(),String.class);
            }
            //Offset after
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(String.class))
                return apply(after,before);
            throw new UnexpectedDataTypeException("The operator \"" + representations[0] + "\" only allows one string on either side.");
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) before.getObject() < (int) after.getObject())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length() < ((String) after.getObject()).length())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) before.getObject() > (int) after.getObject())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length() > ((String) after.getObject()).length())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) before.getObject() <= (int) after.getObject())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length() <= ((String) after.getObject()).length())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if ((int) before.getObject() >= (int) after.getObject())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length() >= ((String) after.getObject()).length())
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if (((int) before.getObject())%2 == 1 && (int) after.getObject()%2 == 1)
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length()%2 == 1 && ((String) after.getObject()).length()%2 == 1)
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
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
        public Parcel apply(Parcel before, Parcel after) throws UnexpectedDataTypeException{
            //Int
            if(before.getClassObject().equals(Integer.class) && after.getClassObject().equals(Integer.class)) {
                if (((int) before.getObject())%2 == 1 || (int) after.getObject()%2 == 1)
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            //Str
            if(before.getClassObject().equals(String.class) && after.getClassObject().equals(String.class)) {
                if (((String) before.getObject()).length()%2 == 1 || ((String) after.getObject()).length()%2 == 1)
                    return new Parcel(1, Integer.class);
                return new Parcel(0, Integer.class);
            }
            throw new UnexpectedDataTypeException("Before: " + before.getClassObject() + ", After: " + after.getClassObject());
        }
        @Override
        public String toString() {
            return representations[0];
        }
    }
}
