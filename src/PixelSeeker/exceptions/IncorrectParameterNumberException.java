package PixelSeeker.exceptions;

public class IncorrectParameterNumberException extends Exception {
    public IncorrectParameterNumberException(int exp, int act) {
        super("Incorrect number of parameters supplied. Expected: \"" + exp + "\" Supplied: \"" + act + '"');
    }
}
