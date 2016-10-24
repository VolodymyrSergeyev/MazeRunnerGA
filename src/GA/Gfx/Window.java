package GA.Gfx;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public final class Window {

    public static String title;
    public static int WIDTH;
    public static int HEIGHT;
    public static float SCALE;

    public Window(int width, int height, String title, float scale) {
        this.title = title;
        this.SCALE = scale;
        this.WIDTH = width;
        this.HEIGHT = height;
        try {
            Display.setTitle(this.title);
            Display.setDisplayMode(new DisplayMode(this.WIDTH, this.HEIGHT));
            Display.create();
        } catch (LWJGLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void init() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static String getTitle() {
        return title;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static float getSCALE() {
        return SCALE;
    }
}
