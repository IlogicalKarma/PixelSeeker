package PixelSeeker.storage;

public class FunctionValue extends Value{
    private FunLambda lambda;
    private Data lastData = null;
    private Data lastParameters = null;
    public FunctionValue(FunLambda lambda){
        super(Data.FUN);
        this.lambda = lambda;
    }
    public Data extract(Data parameters){
        lastData = lambda.run(parameters);
        lastParameters = parameters;
        return lastData;
    }

    @Override
    public boolean toBool() {
        return lastData.toBool();
    }

    @Override
    public String toString() {
        return "FunctionValue(" + lastParameters.toString() + ") | Value: " + lastData.toString();
    }

    @Override
    public String toUserString() {
        return lastData.toString();
    }
}
