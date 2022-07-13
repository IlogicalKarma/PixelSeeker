package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.NamingErrorException;

import java.util.ArrayList;

public class ArrayElement extends Data {
    private final static byte type = 3;

    public ArrayElement(String name, ArrayList<Data> dataList, Context context) throws NamingErrorException {
        super(name, type, context);
        set(dataList);
        super.initialized = true;
    }
    public ArrayElement(ArrayList<Data> dataList, Context context) {
        super(type, context);
        set(dataList);
    }
    public ArrayElement(String name, Context context) throws NamingErrorException {
        this(name, new ArrayList<Data>(), context);
    }
    public ArrayElement(ArrayList<Data> dataList){
        super(type, dataList);
    }
    public ArrayElement(){
        super(type, new ArrayList<Data>());
    }
    public Data getElement(int i){
        return get().get(i);
    }
    public boolean areNamed(){
        for(Data data : get())
            if(!data.isNamed())
                return false;
        return true;
    }

    public int getLength() {
        return get().size();
    }

    @Override
    public ArrayList<Data> get() {
        return (ArrayList<Data>) value;
    }

    public String toString() {
        boolean first = true;
        StringBuilder sb = new StringBuilder("{ ");
        for(Data e: get()) {
            if (first)
                first = false;
            else
                sb.append(", ");
            sb.append(e == null ? "null" : e.toString());
        }
        return sb.append(" }").toString();
    }
}
