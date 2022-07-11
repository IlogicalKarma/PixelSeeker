package PixelSeeker.DataStorage;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.expressions.Expression;

import java.util.ArrayList;

public class ArrayElement extends Element{
    private static int type = 3;

    public ArrayElement(String name, ArrayList<Element> elements, NameManagement context) {
        super(name, type, context);
        set(elements);
        super.initialized = true;
    }
    public ArrayElement(ArrayList<Element> elements, NameManagement context) {
        super(type, context);
        set(elements);
    }
    public ArrayElement(String name, NameManagement context) {
        this(name, new ArrayList<Element>(), context);
    }
    public ArrayElement(ArrayList<Element> elements){
        super(type);
        set(elements);
    }
    public ArrayElement(){
        super(type);
        set(new ArrayList<Element>());
    }
    public Element getElement(int i) throws ExpressionExtractionFailureException {
        return get().get(i).isExpr() ? ((Expression)get().get(i)).extract() : get().get(i);
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

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Element e: get())
            sb.append(e.toString());
        return sb.toString();
    }*/
}
