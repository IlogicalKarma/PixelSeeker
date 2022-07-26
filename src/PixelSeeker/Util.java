package PixelSeeker;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Util {
    public static Class getClassFromLibrary(String expectedId, String library){
        String id;
        List<String> lines = null;
        Class c,defaultC = null;
        URL res;
        try {
            res = Main.class.getResource(library + "/manifest") ;
            if(res == null)
                throw new IOException();
            lines = Files.readAllLines(Paths.get(res.getFile().substring(1)));
            for (String line : lines) {
                try{
                    c = Class.forName("PixelSeeker." + library + '.' + line.trim());
                } catch (ClassNotFoundException e){
                    System.out.println("Internal warning: " + line.trim() + " could not be found");
                    continue;
                }
                id = (String) c.getField("identifier").get(null);
                if(id == null) {
                    defaultC = c;
                }else if (id.equals(expectedId))
                        return c;

            }
        }catch (IOException | IllegalAccessException | NoSuchFieldException e){
            if(e instanceof IOException)
                System.out.println("Internal error: inaccessible instruction library");
            else if(e instanceof IllegalAccessException)
                System.out.println("Internal error: cannot access class");
            else
                System.out.println("Internal error: library classes must have an identifier field");
            e.printStackTrace();
            System.exit(-1);
        }
        return defaultC;
    }
}
