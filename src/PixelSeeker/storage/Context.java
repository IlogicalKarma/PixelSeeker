package PixelSeeker.storage;

import java.util.HashMap;

public class Context {
    private HashMap<String, Data> names = new HashMap<>();
    public Data get(String n){
        return names.get(n);
    }
    public Context(){}
    public Context(Context outerContext){
        this.names = outerContext.getHash();
        //Same variables come out here, no issues
    }
    public boolean has(String n){
        return names.containsKey(n);
    }
    public void rem(String n){
        names.remove(n);
    }
    public void set(String n, Data data) {
        names.put(n, data);
    }
    public HashMap<String, Data> getHash() {
        HashMap<String, Data> innerContext = new HashMap<>();
        names.forEach((String key, Data val) ->{
            innerContext.put(key, names.get(key));
        });
        return innerContext;
    }
}
