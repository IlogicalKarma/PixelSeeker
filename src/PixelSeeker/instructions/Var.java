package PixelSeeker.instructions;

import PixelSeeker.Parcel;

public class Var {
    private Parcel value;
    private String name;

    public Var(String name, Parcel value) {
        this.value = value;
        this.name = name;
        VarManagement.setVar(name, this);
    }

    public Var(String name) {
        this(name, new Parcel(0, Integer.class));
    }

    public Parcel getValue() {
        return value;
    }

    public void setValue(Parcel value) {
        this.value = value;
        VarManagement.setVar(name, this);
    }

    public String getName() {
        return name;
    }
}
