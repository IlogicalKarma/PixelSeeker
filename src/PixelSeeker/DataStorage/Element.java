package PixelSeeker.DataStorage;

import PixelSeeker.Constants;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.instructions.Instruction;

public class Element {
    protected boolean initialized = false;
    private String name = null;
    protected Object value = null;
    private int type = 0; // 1 - Num, 2 - String, 3 - Array, 4 - Func, 5 - Expr
    protected NameManagement context;
    protected Element(String name, int type, NameManagement context){
        this.context = context;
        this.type = type;
        this.name = name;
        context.set(name, this);
    }
    protected Element(int type, NameManagement context){
        this.context = context;
        this.type = type;
    }
    protected Element(String name, NameManagement context){
        this.context = context;
        this.name = name;
    }
    protected Element(int type){
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void name(String name) {
        context.set(name, this);
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
    public void copyTo(Element element){
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
    @Override
    public String toString() {
        return value.toString();
    }
}
