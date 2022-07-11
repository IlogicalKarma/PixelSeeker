package PixelSeeker.DataStorage;

public class NumericalElement extends Element{
    private static int type = 1;

    public NumericalElement(String name, Integer value, NameManagement context) {
        super(name, type, context);
        set(value);
        super.initialized = true;
    }
    public NumericalElement(Integer value, NameManagement context) {
        super(type, context);
        set(value);
    }
    public NumericalElement(Integer value) {
        super(type);
        set(value);
    }
    public NumericalElement() {
        super(type);
        set(Integer.valueOf(0));
    }

    public NumericalElement(String name, NameManagement context) {
        super(name, context);
        set(Integer.valueOf(0));
    }
    @Override
    public Integer get() {
        return (Integer) value;
    }
    public boolean toBool(){
        return get()%2 == 1;
    }


    /*@Override
    public String toString() {
        return get().toString();
    }*/
}
