package PixelSeeker;

import java.util.ArrayList;

public class MultiClassArray {
    private ArrayList<Parcel> parcels = new ArrayList<>();

    public void add(Object object, Class classObject){
        parcels.add(new Parcel(object, classObject));
    }
    public void add(Parcel parcel){
        parcels.add(parcel);
    }
    public void set(int i, Object object, Class classObject){
        parcels.set(i, new Parcel(object, classObject));
    }
    public void set(int i, Parcel parcel){
        parcels.set(i, parcel);
    }
    public void insert(int i, Object object, Class classObject){
        parcels.add(i, new Parcel(object, classObject));
    }
    public void insert(int i, Parcel parcel){
        parcels.add(i, parcel);
    }
    public int size(){ return parcels.size(); }
    public Parcel get(int i){
        return parcels.get(i);
    }
    public void remove(int i){
        parcels.remove(i);
    }

    @Override
    public String toString() {
        String output = "MultiClassArray: { ";
        for(int i = 0; i < parcels.size(); i++){
            output += "\n" + parcels.get(i).toString();
        }
        return output + "\n}";
    }
}
