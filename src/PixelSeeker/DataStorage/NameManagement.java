package PixelSeeker.DataStorage;

import java.util.HashMap;

public class NameManagement {
    private HashMap<String, Element> names = new HashMap<>();
    public Element get(String n){
        return names.get(n);
    }
    public NameManagement(){}
    public NameManagement(NameManagement outerContext){
        this.names = outerContext.getHash();
    }
    public boolean has(String n){
        return names.containsKey(n);
    }
    public boolean rem(String n){
        if(names.containsKey(n)){
            names.remove(n);
            return true;
        }
        return false;
    }
    public void set(String n, Element element) {
        names.put(n, element);
    }
    public HashMap<String, Element> getHash() {
        return new HashMap<String, Element>(names);
    }
}
