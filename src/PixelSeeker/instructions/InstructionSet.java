package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.exceptions.RuntimeErrorException;

import java.util.ArrayList;

public class InstructionSet {
    private ArrayList<Instruction> instructions = new ArrayList<>();
    public Instruction get(int i){
        return instructions.get(i);
    }
    public void add(Instruction instruction){
        instructions.add(instruction);

    }
    public void add(int i, Instruction instruction){
        instructions.add(i, instruction);
    }
    public void add(InstructionSet instructionSet){
        for(int i = 0; i < instructionSet.size(); i++)
            instructions.add(instructionSet.get(i));
    }
    public void remove(int index){

        instructions.remove(index);
    }
    public int size(){
        return  instructions.size();
    }
    public Data execute() throws RuntimeErrorException{
        int limit = instructions.size();
        try {
            for (int i = 0; i < limit; i++) {
                Data p = instructions.get(i).execute();
                if(p != null)
                    return p;
            }
        }catch (PixelSeeker.exceptions.ExpressionExtractionFailureException | PixelSeeker.exceptions.InvalidVariableNameException | PixelSeeker.exceptions.IncorrectParametersException e){
            throw new RuntimeErrorException(e);
        }
        return null;
    }
}
