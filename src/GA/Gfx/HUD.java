package GA.Gfx;


import GA.Gfx.Element.Button;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

import static GA.Gfx.Helper.Artist.drawRectTexture;
import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class HUD {

    private final ArrayList<Button> buttonList;


    public HUD() {
        this.buttonList = new ArrayList<>();
    }

    public void addButton(String name, String textureName, int x, int y) {
        buttonList.add(new Button(name, quickLoadTexture(textureName), x, y));
    }

    public boolean isButtonClicked(String buttonName) {
        Button b = getButton(buttonName);
        float mouseY = Window.HEIGHT - Mouse.getY() - 1;
        return Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() / 2
                && mouseY > b.getY() && mouseY < b.getY() + b.getHeight() / 2;
    }

    public Button getButton(String buttonName) {
        for (Button b : buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void draw() {
        for (Button b : buttonList) {
            drawRectTexture(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
    }
}
