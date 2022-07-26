package PixelSeeker;

import sun.nio.ch.IOUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class Util {
    public static Class getClassFromLibrary(String expectedId, String library){
        String id;
        List<String> lines;
        Class c,defaultC = null;
        String content;
        Scanner reader;
        try {
            reader = new Scanner(Util.class.getClassLoader().getResourceAsStream(  "PixelSeeker/libraries/" +library + "/manifest")).useDelimiter("[\n;]");
            lines =null;
            while (reader.hasNext()) {
                content = reader.next();
                try{
                    c = Class.forName("PixelSeeker.libraries." + library + '.' + content.trim());
                } catch (ClassNotFoundException e){
                    System.out.println("Internal warning: " + content.trim() + " could not be found");
                    continue;
                }
                id = (String) c.getField("identifier").get(null);
                if(id == null) {
                    defaultC = c;
                }else if (id.equals(expectedId))
                        return c;

            }
        }catch ( IllegalAccessException | NoSuchFieldException e){
            if(e instanceof IllegalAccessException)
                System.out.println("Internal error: cannot access filed");
            else
                System.out.println("Internal error: library classes must have an identifier field");
            e.printStackTrace();
            System.exit(-1);
        }
        return defaultC;
    }
}
