package PixelSeeker.DataStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class NameManagement {
    private HashMap<String, Element> names = new HashMap<>();
    public Element get(String n){
        return names.get(n);
    }
    public NameManagement(){}
    public NameManagement(NameManagement outerContext){
        this.names = outerContext.getHash();
        //Same variables come out here, no issues
    }
    public boolean has(String n){
        return names.containsKey(n);
    }
    public void rem(String n){
        names.remove(n);
    }
    public void set(String n, Element element) {
        names.put(n,element);
    }
    public HashMap<String, Element> getHash() {
        HashMap<String, Element> innerContext = new HashMap<>();
        names.forEach((String key, Element val) ->{
            innerContext.put(key, names.get(key));
        });
        return innerContext;
    }
}
