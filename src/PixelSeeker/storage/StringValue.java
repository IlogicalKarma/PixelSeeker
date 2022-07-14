package PixelSeeker.storage;

public class StringValue extends Value {
    private final static byte typeCode = 2;
    public String string;

    public StringValue(String string){
        super(typeCode);
        this.string = string;
    }
    public StringValue(){
        super(typeCode);
        this.string = new String();
    }
    public boolean toBool(){
        return !(string.isEmpty());
    }
    @Override
    public String toString() {
        return '"' + string+ '"';
    }

    @Override
    public String toUserString() {
        return string;
    }
}
