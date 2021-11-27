package PixelSeeker;

import java.beans.Expression;
import java.util.ArrayList;

public abstract class StatementType extends Instruction{
    private InstructionSet instructionSet;
    private Expression expression;
    StatementType(){
        super.type = "Statement";
    }
    public abstract void execute();
    public abstract void edit();
    public abstract void setInstructionSet(InstructionSet instructionSet);

}
