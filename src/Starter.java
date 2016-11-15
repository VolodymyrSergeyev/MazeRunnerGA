import GA.Core;

import java.io.File;

public class Starter {
    public static void main (String[] args){

        System.setProperty("org.lwjgl.librarypath", new File("native/").getAbsolutePath());

        int width = 600;
        int height = 600;
        float scale = 1.4f;
        if(args.length > 0 &&  args[0].equals("-small")){
            width = 840;
            height = 600;
            scale = 1f;
        }

        Core ga = new Core(width,height,scale);
        ga.start();
    }
}
