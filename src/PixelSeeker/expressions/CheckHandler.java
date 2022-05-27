package PixelSeeker.expressions;

import PixelSeeker.exceptions.CheckLookupException;

import java.util.Scanner;

public class CheckHandler {
    private static Check[] list = {new AlwaysTrue(), new AlwaysFalse()};
    private static class Check{
        String representation;
        Check(String representation){
            this.representation = representation;
        }
        public boolean verify(String string) {
            return string.equals(representation);
        }
        public int extractProcedure(){
            return 0;
        }
    }
    private static class AlwaysTrue extends Check{
        protected AlwaysTrue(){
            super("true");
        }
        @Override
        public int extractProcedure() {
            return 1;
        }
    }
    private static class AlwaysFalse extends Check{
        protected AlwaysFalse(){
            super("false");
        }
    }
    private static class In extends Check{
        private static Scanner scanner = new Scanner(System.in);
        protected In(){
            super("in");
        }
        @Override
        public int extractProcedure(){
            return scanner.nextInt();
        }
    }
    public static int returnFind(String string) throws CheckLookupException {
        for(Check check : list){
            if(check.verify(string))
                return check.extractProcedure();
        }
        throw new CheckLookupException(string);
    }
}
