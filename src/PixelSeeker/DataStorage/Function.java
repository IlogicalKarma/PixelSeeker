package PixelSeeker.DataStorage;

import PixelSeeker.DataStorage.ArrayElement;
import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.NamingException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.instructions.InstructionSet;


/*public class Function extends Element{
    private ArrayElement param;
    private InstructionSet instructionSet;
    private static int type = 4;
    public Function(String name, ArrayElement param, InstructionSet instructionSet, NameManagement context) throws NamingException {
        super(name, 4, context);
        this.param = param;
        this.instructionSet = instructionSet;
    }
    public Function(ArrayElement param, InstructionSet instructionSet) throws IncorrectParametersException{
        super(4);
        this.param = param;
        this.instructionSet = instructionSet;
    }

    public Element execute(ArrayElement param) throws IncorrectParametersException, RuntimeErrorException {
        if(this.param.getLength() != param.getLength())
            throw new IncorrectParametersException("Incorrect number of parameters");

        // MAKE LOCAL VARIABLES !!!

        Element r = instructionSet.execute();
        return r;
    }
}*/
