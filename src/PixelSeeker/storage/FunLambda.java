package PixelSeeker.storage;

import PixelSeeker.exceptions.RuntimeErrorException;

@FunctionalInterface
public interface FunLambda {
    public Data run(Data param) throws RuntimeErrorException;
}
