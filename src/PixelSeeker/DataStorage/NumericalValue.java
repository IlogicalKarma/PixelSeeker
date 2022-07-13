package PixelSeeker.DataStorage;

public class NumericalValue extends Value {
    private final static byte typeCode = 1;
    public int value;
    public NumericalValue(int value){
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
}
