package PixelSeeker;

import PixelSeeker.storage.Context;
import PixelSeeker.expressions.Expression;

import java.lang.reflect.InvocationTargetException;

public class InstructionHandler {
    private final static String library = "instruction_internal_library";
    private InstructionHandler(){}

    public static Instruction retrieve(String string, Expression paramExpression, InstructionSet instructionSet, Context context) {
        try {
            return (Instruction) Util.
                    getClassFromLibrary(string, library)
                    .getConstructor (Expression.class, InstructionSet.class, Context.class)
                    .newInstance(paramExpression, instructionSet, context);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            if(e instanceof NoSuchMethodException)
                System.out.println("Internal error: incorrect instruction constructor");
            else
                System.out.println("Internal error: unknown instantiation error");
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
    public static boolean check(String string) {
        try {
            return Util.getClassFromLibrary(string, library).getField("identifier").get(null) != null;
        }catch (NoSuchFieldException | IllegalAccessException e){
            System.out.println("Internal error: static field not present in class");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

}
