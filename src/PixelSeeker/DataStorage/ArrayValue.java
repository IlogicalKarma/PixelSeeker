package PixelSeeker.DataStorage;

import java.util.ArrayList;

public class ArrayValue extends Value {
    private static byte typeCode = 3;
    public Element[] array = new Element[0];
    public ArrayValue(Element[] array){
        super(typeCode);
        this.array = array;
    }
    public ArrayValue(){
        super(typeCode);
    }
    public void set(int index, Element val){
        array[index] =  val;
    }
    public Element get(int index){
        return array[index];
    }
    public void add(Element val){
        Element[] array = new Element[this.array.length];
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
        for (Element e : array)
            if (!e.toBool())
                return false;
        return true;
    }
    @Override
    public String toString(){
        boolean first = true;
        StringBuilder sb = new StringBuilder("{ ");
        for(Element e: array) {
            if (first)
                first = false;
            else
                sb.append(", ");
            sb.append(e == null ? "null" : e.toString());
        }
        return sb.append(" }").toString();
    }
}
