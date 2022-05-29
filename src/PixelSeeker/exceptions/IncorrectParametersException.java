package PixelSeeker.exceptions;

public class IncorrectParametersException extends Exception {
    public IncorrectParametersException(int exp, int act) {
        super("Incorrect number of parameters supplied. Expected: \"" + exp + "\" Supplied: \"" + act + '"');
    }
    public IncorrectParametersException(String str) {
        super(str);
    }
}
