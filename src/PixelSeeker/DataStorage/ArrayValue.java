package PixelSeeker.DataStorage;

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
    public void add(Data val){
        Data[] array = new Data[this.array.length];
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
    @Override
    public String toString(){
        boolean first = true;
        StringBuilder sb = new StringBuilder("{ ");
        for(Data data: array) {
            if (first)
                first = false;
            else
                sb.append(", ");
            sb.append(data == null ? "null" : data.toString());
        }
        return sb.append(" }").toString();
    }
}
