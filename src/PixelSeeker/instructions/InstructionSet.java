package PixelSeeker.instructions;

import java.util.ArrayList;

public class InstructionSet {
    private ArrayList<Instruction> instructions = new ArrayList<>();
    public void add(Instruction instruction){
        instructions.add(instruction);
    }
    public void remove(int index){

        instructions.remove(index);
    }
    public int size(){
        return  instructions.size();
    }
    public void execute() throws  PixelSeeker.exceptions.ExpressionExtractionFailureException, PixelSeeker.exceptions.InvalidVariableNameException, PixelSeeker.exceptions.IncorrectParametersException{
        int limit = instructions.size();
        for (int i = 0; i < limit; i++) {
            instructions.get(i).execute();
        }
    }
}
