package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.NamingException;
import sun.rmi.runtime.NewThreadAction;

public class StringElement extends Element{
    private static int type = 2;

    public StringElement(String name, String value, NameManagement context) throws NamingException{
        super(name, type, context);
        set(value);
        super.initialized = true;
    }
    public StringElement(String value, NameManagement context){
        super(type, context);
        set(value);
    }
    public StringElement(NameManagement context){
        super(type, context);
        set(new String());
    }

    public StringElement(String value){
        super(type, value);

    }
    public StringElement(){
        this(new String());
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String get() {
        return (String) value;
    }
    public int getLength() {
        return get().length();
    }
    /*public String toString() {
        return get();
    }*/
}
