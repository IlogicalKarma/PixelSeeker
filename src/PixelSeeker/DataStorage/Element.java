package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.NamingException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.expressions.OperatorHandler;

public class Element {
    private static char[] nameBlacklist = new char[]{'(', ')', '[', ']', '{', '}'};
    protected boolean initialized = false;
    private String name = null;
    protected Object value = null;
    private int type = 0; // 1 - Num, 2 - String, 3 - Array, 4 - Func, 5 - Expr
    protected NameManagement context;
    protected Element(String name, int type, Object value, NameManagement context) throws NamingException{
        for(char c : nameBlacklist)
            for(int i = name.length()-1; i >= 0; i--)
                if(name.charAt(i) == c)
                    throw new NamingException("Illegal character found in name: " + c);
        for(char c : OperatorHandler.WHITELIST)
            for(int i = name.length()-1; i >= 0; i--)
                if(name.charAt(i) == c)
                    throw new NamingException("Illegal character found in name: " + c);
        this.context = context;
        this.value = value;
        this.type = type;
        this.name = name;
        context.set(name, this);
    }
    protected Element(String name, int type, NameManagement context) throws NamingException{
        this(name, type, 0, context);
    }
    protected Element(int type, Object value){
        this.type = type;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void name(String name) {
        context.set(name, this);        //NOT USEFUL, REMOVE FROM ASSIGN
    }
    public Object get() throws ExpressionExtractionFailureException {
        return value;
    }
    public void set(Object value){
        this.value = value;
    }
    public void setType(int type){
        this.type = type;
    }
    public void copyTo(Element element){ // BROKEN!?!?!?!!?
        element.setType(type);
        element.set(value);
    }
    public NameManagement getContext() {
        return context;
    }
    public boolean isInitialized(){
        return initialized;
    }
    public boolean isArray(){
        return this.type == 3;
    }
    public boolean isNum(){
        return type == 1;
    }
    public boolean isString(){
        return type == 2;
    }
    public boolean isNamed(){
        return name != null;
    }
    public boolean isExpr(){
        return type == 5;
    }
    public boolean isFunc(){
        return type == 4;
    }
    public boolean toBool() throws RuntimeErrorException{
        throw new RuntimeErrorException("Cannot cast to boolean");
    }
    @Override
    public String toString() {
        return value.toString();
    }
}
