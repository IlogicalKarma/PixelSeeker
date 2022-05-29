package PixelSeeker.instructions;

import PixelSeeker.Parcel;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.expressions.Expression;

public class Out extends Instruction{
    final static String identifier = "out";
    static String type = "Out";
    private Expression[] param;
    public Out(Expression[] param) throws IncorrectParametersException {
        super(param, -1, null);
        this.param = param;
        paramNr = param.length;
    }
    @Override
    public void execute() throws ExpressionExtractionFailureException {
        if(paramNr == 0) {
            System.out.println("There are no values to output.");
            return;
        }
        System.out.print("Output values: ");
        for(int i = 0; i < paramNr; i++){
            if(i != 0)
                System.out.print(", ");
            param[i].extract();
            Parcel p = param[i].getParcel();
            if(p.getClassObject().equals(Var.class))
                p = ((Var)param[0].getParcel().getObject()).getValue();
            System.out.print(p.getClassObject().cast(p.getObject()));
        }
        System.out.print('\n');
    }
}
