package PixelSeeker;

import PixelSeeker.storage.Context;
import PixelSeeker.expressions.Expression;

import java.lang.reflect.InvocationTargetException;

public class InstructionHandler {
    private final static String library = "instruction_library";
    private InstructionHandler(){}

    public static Instruction retrieve(Class c, Expression paramExpression, InstructionSet instructionSet, Context context) {
        try {
            return (Instruction) c.getConstructor(Expression.class, InstructionSet.class, Context.class).newInstance(paramExpression, instructionSet, context);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            if(e instanceof NoSuchMethodException)
                System.out.println("Internal error: incorrect instruction constructor");
            else if(e instanceof IllegalAccessException)
                System.out.println("Illegal error: cannot access field");
            else
                System.out.println("Internal error: instantiation error");
            System.exit(-1);
        }
        return null;
    }
    public static Class getClass(String string){
        return Util.getClassFromLibrary(string, library);
    }
    public static boolean nullIdentifierCheck(Class c) {
        try {
            return c.getField("identifier").get(null) == null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            if(e instanceof NoSuchFieldException)
                System.out.println("Internal error: library classes must have an identifier field");
            else if(e instanceof IllegalAccessException)
                System.out.println("Internal error: cannot access field");
            System.exit(-1);
        }

        return false;
    }
    public static boolean requiresInstructionSetCheck(Class c) {
        try {
            return (Boolean) c.getField("requiresInstructionSet").get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            if(e instanceof NoSuchFieldException)
                System.out.println("Internal error: library classes must have an identifier field");
            else if(e instanceof IllegalAccessException)
                System.out.println("Internal error: cannot access field");
            System.exit(-1);
        }

        return false;
    }
}
