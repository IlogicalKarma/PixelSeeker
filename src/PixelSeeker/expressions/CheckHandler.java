package PixelSeeker.expressions;

public class CheckHandler {

    public abstract class Check{
        public abstract boolean verify(String string);
        public abstract int extractProcedure();
    }
    public class AlwaysTrue extends Check{
        String representation = "AlwaysTrue";
        @Override
        public int extractProcedure() {
            return 1;
        }
        @Override
        public boolean verify(String string) {
            return string.equals(representation);
        }
    }
}
