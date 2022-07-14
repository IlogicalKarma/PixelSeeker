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
            System.exit(-1);
        }
        return null;
    }

}
