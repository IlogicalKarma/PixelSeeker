package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.NamingException;

import java.util.ArrayList;

public class NumericalElement extends Element{
    private static int type = 1;

    public NumericalElement(String name, Integer value, NameManagement context) throws NamingException {
        super(name, type, context);
        set(value);
        super.initialized = true;
    }
    public NumericalElement(Integer value, NameManagement context) {
        super(type, context);
        set(value);
    }
    public NumericalElement(Integer value) {
        super(type, value);
    }
    public NumericalElement() {
        this(0);
    }

    public Integer get() {
        return (Integer) value;
    }
    public boolean toBool(){
        return get()%2 == 1;
    }


    @Override
    public String toString() {
        return get().toString();
    }
}
