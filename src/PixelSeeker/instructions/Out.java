package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.expressions.Expression;

public class Out extends Instruction{
    final static String identifier = "out";
    public Out(Expression param[]) throws IncorrectParameterNumberException {
        super(param, -1, null, "Out");
        paramNr = param.length;
    }
    @Override
    public void execute() {
        if(paramNr == 0) {
            System.out.println("There are no values to output.");
            return;
        }
        System.out.print("The output values are: ");
        for(int i = 0; i < paramNr; i++){
            if(i != 0)
                System.out.print(", ");
            System.out.print(param[i].getValue());
        }
        System.out.print('\n');
    }
}
