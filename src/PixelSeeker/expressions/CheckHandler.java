package PixelSeeker.expressions;

public class CheckHandler {
    private CheckHandler(){}
    True objectTrue = new True();
    Check[] checksArray = {objectTrue};
    static private class Check{
        protected int status;
        protected static String representation = " ";
        Check(int status, String representation){
            this.status = status;
            this.representation = representation;
        }
        public int getStatus(){
            return status;
        }
        public static boolean verify(String string){
            return string.equals(representation);
        }
        public static int extractProcedure(){
            return 0;
        }
    }
    static public class True extends Check{

        True(){
            super(1,"true");
        }
        @Override
        public static int extractProcedure(){

        }

    }
}
