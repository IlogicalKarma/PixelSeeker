package PixelSeeker.expressions;

import PixelSeeker.exceptions.CheckLookupException;

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
        public AlwaysTrue(){
            super("true");
        }
        @Override
        public int extractProcedure() {
            return 1;
        }
        @Override
        public boolean verify(String string) {
            return string.equals(representation);
        }
    }
    private static class AlwaysFalse extends Check{
        public AlwaysFalse(){
            super("false");
        }
        @Override
        public boolean verify(String string) {
            return string.equals(representation);
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
