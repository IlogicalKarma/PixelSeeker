package PixelSeeker.expressions;

public class CheckHandler {
    private CheckHandler(){}
    private static True objectTrue = new True();
    private static Check[] checksArray = {objectTrue};
    public static Check[] getChecksArray() {
        return checksArray;
    }

    static public class Check{
        protected int status;
        protected String representation = " ";
        protected Check(int status, String representation){
            this.status = status;
            this.representation = representation;
        }
        public int extractProcedure(){
            System.out.println("CHECK");
            return 1;
        }
        public int getStatus(){
            return status;
        }
        public boolean verify(String string){
            return string.equals(representation);
        }

    }
    static private class True extends Check{

        public True(){
            super(1,"alwaysTrue");
        }

        @Override
        public int extractProcedure() {


            return 1;

        }
    }
}
