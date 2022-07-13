package PixelSeeker.DataStorage;

public abstract class Value {
    private byte typeCode;
    protected Value(byte typeCode){
        this.typeCode = typeCode;
    }
    public byte type(){
        return typeCode;
    }
    public abstract boolean toBool();
}
