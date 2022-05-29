package PixelSeeker.instructions;

import PixelSeeker.Parcel;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.MissingInstructionSetException;
import PixelSeeker.expressions.Expression;

public class WhileStatement extends Instruction{
    final static String identifier = "while";
    static String type = "WhileStatement";
    public WhileStatement(Expression[] param, InstructionSet instructionSet) throws IncorrectParametersException, MissingInstructionSetException {
        super(param, 1, instructionSet);
        if(instructionSet == null)
            throw new MissingInstructionSetException();
    }

    @Override
    public void execute() throws PixelSeeker.exceptions.InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException{
        param[0].extract();
        Parcel p = param[0].getParcel();
        if(p.getClassObject().equals(Var.class))
            p = ((Var)param[0].getParcel().getObject()).getValue();
        if(!p.getClassObject().equals(Integer.class))
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        while (param[0].getBool()) {
            instructionSet.execute();
            param[0].extract();
            p = param[0].getParcel();
            if(!p.getClassObject().equals(Integer.class))
                throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        }
    }
}
