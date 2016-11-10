package GA.Gfx;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;

import java.awt.*;

import static GA.State.MapEditor.MAP_EDITOR_ID;
import static GA.State.Spectating.SPECTATING_ID;
import static org.lwjgl.opengl.GL11.*;

public final class Window extends JFrame {

    private static String title;
    public static int WIDTH;
    public static int HEIGHT;
    public static float SCALE;
    private static Canvas scene;
    private static JPanel spectating;
    private static JPanel mapeditor;
    private static JPanel cards;

    private static final String INIT_ID = "init";

    public Window(int width, int height, String title, float scale) {
        Window.title = title;
        SCALE = scale;
        WIDTH = (int)(width * scale);
        HEIGHT = (int)(height * scale);
        scene = new Canvas();
        scene.setFocusable(true);
        spectating = new JPanel();
        mapeditor = new JPanel();
        cards = new JPanel(new CardLayout());
        spectating.setFocusable(false);
        mapeditor.setFocusable(false);
        cards.setFocusable(false);
        cards.setEnabled(true);
        Dimension s = new Dimension(WIDTH + 100, HEIGHT);
        Dimension c =new Dimension(WIDTH + 100, 80);
        cards.setPreferredSize(c);
        JPanel init = new JPanel();
        init.setPreferredSize(c);
        cards.add(mapeditor, MAP_EDITOR_ID);
        cards.add(spectating, SPECTATING_ID);
        cards.add(init, INIT_ID);
        scene.setPreferredSize(s);
        scene.setMaximumSize(s);
        scene.setMinimumSize(s);
        Dimension d = new Dimension(WIDTH + 100, HEIGHT + 100);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.add(scene, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.PAGE_END);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        try {
            Display.setParent(scene);
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
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

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void changeToSpectate(){
        changeCard(SPECTATING_ID);
    }

    public static void changeToMapEditor(){
        changeCard(MAP_EDITOR_ID);
    }

    private static void changeCard(String ID) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, ID);
    }

    public static void changeToInit(){
        changeCard(INIT_ID);
    }

    public static JPanel getSpectatingPanel(){
        return spectating;
    }

    public static JPanel getMapeditorPanel(){
        return mapeditor;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static float getSCALE() {
        return SCALE;
    }
}
