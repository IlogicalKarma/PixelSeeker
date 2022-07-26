package PixelSeeker.storage;

import PixelSeeker.exceptions.RuntimeErrorException;

public class ArrayValue extends Value {
    private static byte typeCode = 3;
    public Data[] array = new Data[0];
    public ArrayValue(Data[] array){
        super(typeCode);
        this.array = array;
    }
    public ArrayValue(){
        super(typeCode);
    }
    public void set(int index, Data val){
        array[index] =  val;
    }
    public Data get(int index){
        return array[index];
    }
    public void del(int index)  {
        Data[] array = new Data[this.array.length-1];
        for(int i = 0; i < array.length; i++)
            if(i >= index)
                array[i] = this.array[i+1];
            else
                array[i] = this.array[i];
    }
    public ArrayValue subArr(int i0, int i1){
        ArrayValue subArr = new ArrayValue();
        while(i0 < i1)
            subArr.array[i0] = array[i0++];
        return subArr;
    }
    public void add(Data val){
        Data[] array = new Data[this.array.length+1];
        for(int i = 0; i < this.array.length; i++)
            array[i] = this.array[i];
        array[this.array.length] = val;
        this.array = array;
    }
    /*public int indexOf(Element e){
        for(int i = 0; i < array.length; i++)
            if(e.equals(array[i]))
                return i;
        return -1;
    }*/
    //To implement after equals methods are added
    public boolean toBool(){
        for (Data e : array)
            if (!e.toBool())
                return false;
        return true;
    }
    private String toStrings(boolean forUser){
        boolean first = true;
        StringBuilder sb = new StringBuilder(forUser ? "" : "{ ");
        for(Data data: array) {
            if (first)
                first = false;
            else
                sb.append(forUser ? "" : ", ");
            sb.append(data == null ? "null" : (forUser && !data.isArr() ? data.toUserString() : data.toString()));
        }
        return sb.append(forUser ? "" : " }").toString();
    }
    @Override
    public String toString(){
        return toStrings(false);
    }

    @Override
    public String toUserString() {
        return toStrings(true);
    }
}
