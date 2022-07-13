package PixelSeeker.storage;

import PixelSeeker.exceptions.NamingErrorException;
import PixelSeeker.expressions.OperatorHandler;

public class Data implements Element{
    public final static byte NUM = 1;
    public final static byte STR = 2;
    public final static byte ARR = 3;
    public final static byte FUN = 4;
    public final static byte EXPR = 5;
    private static char[] nameBlacklist = new char[]{'(', ')', '[', ']', '{', '}'};
    private boolean initialized = false;
    private String name = null;
    private Value value = null;
    private Context context;

    public static Data getNum(Integer num){
        return new Data(new NumericalValue(num));
    }
    public static Data getStr(String str){
        return new Data(new StringValue(str));
    }
    public static Data getArr(Data[] arr){
        return new Data(new ArrayValue(arr));
    }
    //public static Element getFunc()
    public Data(byte type, Object obj) throws NamingErrorException {
        switch (type){
            case NUM:
                this.value = new NumericalValue((Integer) obj);
                break;
            case STR:
                this.value = new StringValue((String) obj);
                break;
            case ARR:
                this.value = new ArrayValue((Data[]) obj);
                break;
            case FUN:
                break;
            default:
                throw new RuntimeException("Internal error: wrong type");
        }
    }
    public Data(String name, Value value, Context context) throws NamingErrorException {
        for(char c : nameBlacklist)
            for(int i = name.length()-1; i >= 0; i--)
                if(name.charAt(i) == c)
                    throw new NamingErrorException("Illegal character found in name: " + c);
        for(char c : OperatorHandler.WHITELIST)
            for(int i = name.length()-1; i >= 0; i--)
                if(name.charAt(i) == c)
                    throw new NamingErrorException("Illegal character found in name: " + c);
        this.context = context;
        this.value = value;
        this.name = name;
        context.set(name, this);
    }
    public Data(Value value){
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void name(String name) {
        context.set(name, this);        //NOT USEFUL, REMOVE FROM ASSIGN
    }
    public Value get() {
        return value;
    }
    public void set(Value value){
        this.value = value;
    }
    public void copyTo(Data data){
        data.set(value);
    }
    public Integer toNum(){
        if(isNum())
            return ((NumericalValue) get()).value;
        return null;
    }
    public String toStr(){
        if(isStr())
            return ((StringValue) get()).string;
        return null;
    }
    public Data[] toArr(){
        if(isArr())
            return ((ArrayValue) get()).array;
        return null;
    }
    public Context getContext() {
        return context;
    }
    public boolean isInitialized(){
        return initialized;
    }
    public boolean isArr(){
        return this.value.type() == 3;
    }
    public boolean isNum(){
        return this.value.type()  == 1;
    }
    public boolean isStr(){
        return this.value.type()  == 2;
    }
    public boolean isNamed(){
        return name != null;
    }
    public boolean isExpr(){
        return this.value.type()  == 5;
    }
    public boolean isFun(){
        return this.value.type()  == 4;
    }
    public boolean toBool(){
        return value.toBool();
    }
    @Override
    public String toString() {
        return value.toString();
    }
    public String toUserString(){
        return value.toUserString();
    }
}
