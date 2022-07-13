package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.NamingErrorException;

import java.util.ArrayList;

public class ArrayElement extends Element{
    private final static byte type = 3;

    public ArrayElement(String name, ArrayList<Element> elements, NameManagement context) throws NamingErrorException {
        super(name, type, context);
        set(elements);
        super.initialized = true;
    }
    public ArrayElement(ArrayList<Element> elements, NameManagement context) {
        super(type, context);
        set(elements);
    }
    public ArrayElement(String name, NameManagement context) throws NamingErrorException {
        this(name, new ArrayList<Element>(), context);
    }
    public ArrayElement(ArrayList<Element> elements){
        super(type, elements);
    }
    public ArrayElement(){
        super(type, new ArrayList<Element>());
    }
    public Element getElement(int i){
        return get().get(i);
    }
    public boolean areNamed(){
        for(Element element : get())
            if(!element.isNamed())
                return false;
        return true;
    }

    public int getLength() {
        return get().size();
    }

    @Override
    public ArrayList<Element> get() {
        return (ArrayList<Element>) value;
    }

    public String toString() {
        boolean first = true;
        StringBuilder sb = new StringBuilder("{ ");
        for(Element e: get()) {
            if (first)
                first = false;
            else
                sb.append(", ");
            sb.append(e == null ? "null" : e.toString());
        }
        return sb.append(" }").toString();
    }
}
