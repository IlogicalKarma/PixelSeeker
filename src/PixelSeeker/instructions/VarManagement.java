package PixelSeeker.instructions;

import java.util.HashMap;

public class VarManagement {
    private static HashMap<String,Integer> vars = new HashMap<>();
    public static Integer getVar(String n) {
        return vars.get(n);
    }

    public static void setVar(String n, int v) {
        vars.put(n,v);
    }
}
