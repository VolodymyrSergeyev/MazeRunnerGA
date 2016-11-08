package GA.Gfx;


import GA.Gfx.Element.Button;

import java.util.ArrayList;

import static GA.Gfx.Helper.Artist.drawRectTexture;
import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class HUD {

    private final ArrayList<Button> buttonList;


    public HUD() {
        this.buttonList = new ArrayList<>();
    }

    public void addButton(String name, String initTextureName, String hoverTextureName, String pressedTextureName, int x, int y) {
        buttonList.add(new Button(name, initTextureName == null ? null : quickLoadTexture(initTextureName),
                hoverTextureName == null ? null : quickLoadTexture(hoverTextureName),
                pressedTextureName == null ? null : quickLoadTexture(pressedTextureName), x, y));
    }

    public boolean isButtonClicked(String buttonName) {
        return getButton(buttonName).isLMBReleased();
    }

    private Button getButton(String buttonName) {
        for (Button b : buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void update(){
        buttonList.forEach(Button::update);
    }

    public void draw() {
        for (Button b : buttonList) {
            drawRectTexture(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
    }
}
