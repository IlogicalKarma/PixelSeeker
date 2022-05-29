package PixelSeeker.instructions;

import PixelSeeker.Parcel;

import java.util.HashMap;

public class VarManagement {
    private static HashMap<String, Var> vars = new HashMap<>();
    public static Parcel getVarValue(String n) {
        return vars.get(n).getValue();
    }
    public static Var getVar(String n) {
        return vars.get(n);
    }
    public static void setVar(String n, Var var) {
        vars.put(n, var);
    }
}
