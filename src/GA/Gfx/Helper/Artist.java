package GA.Gfx.Helper;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

public class Artist {

    public static void drawRectTexture(Texture tex, float x, float y,
                                       float width, float height) {
        tex.bind();
        glTranslatef(x, y, 0);

        // Drawing the quad texure
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    public static void drawRotatableRectTexture(Texture tex, float x, float y,
                                                float width, float height, float angle) {
        tex.bind();

        //transtating 0.0 point to the middle of the texture
        glTranslatef(x + width / 2, y + height / 2, 0);

        //rotating quat texture
        glRotatef(angle, 0, 0, 1);

        //translating the 0.0 point of the texture back to original possition
        glTranslatef(-width / 2, -height / 2, 0);

        // Drawing the quad texure
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    private static Texture loadTexture(String path, String fileType) {
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException ignored) {
        }
        return tex;
    }

    public static Texture quickLoadTexture(String name) {
        return loadTexture("res" + File.separator + "img" + File.separator + name + ".png", "PNG");
    }
}
