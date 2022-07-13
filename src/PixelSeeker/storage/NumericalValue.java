package PixelSeeker.storage;

public class NumericalValue extends Value {
    private final static byte typeCode = 1;
    public Integer value;
    public NumericalValue(Integer value){
        super(typeCode);
        this.value = value;
    }
    public NumericalValue(){
        super(typeCode);
        this.value = 0;
    }

    @Override
    public boolean toBool() {
        return value%2 == 1;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String toUserString() {
        return toString();
    }
}
