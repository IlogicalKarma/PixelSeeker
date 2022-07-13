package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.NamingErrorException;

public class StringElement extends Data {
    private static int type = 2;

    public StringElement(String name, String value, Context context) throws NamingErrorException {
        super(name, type, context);
        set(value);
        super.initialized = true;
    }
    public StringElement(String value, Context context){
        super(type, context);
        set(value);
    }
    public StringElement(Context context){
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
