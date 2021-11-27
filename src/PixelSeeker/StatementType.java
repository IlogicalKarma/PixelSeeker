package PixelSeeker;

import java.util.ArrayList;

public abstract class StatementType extends Instruction{
    private InstructionSet instructionSet;
    StatementType(InstructionSet instructionSet){
        super.type = "Statement";
        this.instructionSet = instructionSet;
    }
    public abstract ArrayList<Instruction> execute(ArrayList<Instruction> instructions);
    public abstract void edit();

}
