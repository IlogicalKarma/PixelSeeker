package PixelSeeker.instructions;

import java.util.ArrayList;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private ArrayList<Instruction> instructions = new ArrayList<>();
    private InstructionHandler(){}
    InstructionHandler getInstance(){
        return instructionHandler;
    }


    void execute() {
    }

}
