package GA.State;


import GA.Gfx.HUD;
import GA.Gfx.Window;
import GA.World.Logger.Logger;
import GA.World.Map.Map;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import javax.swing.*;

import static GA.Gfx.Helper.Artist.drawRectTexture;
import static GA.Gfx.Helper.Artist.quickLoadTexture;

class Init implements State {

    private Texture background;
    private HUD menuHUD;
    private final StateManager stateManager;

    private boolean initialized = false;

    Init(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void init(Logger logger, Map map) {
        this.background = quickLoadTexture("bk");
        this.menuHUD = new HUD();
        this.menuHUD.addButton("start", "i_start", "h_start", null, Window.getWIDTH() / 2 - (int) (130 * Window.SCALE), (int) (Window.getHEIGHT() * 0.5f));
        this.menuHUD.addButton("map editor", "i_map editor", "h_map editor", null, Window.getWIDTH() / 2 - (int) (130 * Window.SCALE), (int) (Window.getHEIGHT() * 0.5f) + (int) (100 * Window.SCALE));
        this.menuHUD.addButton("exit", "i_exit", "h_exit", null, Window.getWIDTH() / 2 - (int) (130 * Window.SCALE), (int) (Window.getHEIGHT() * 0.5f) + (int) (200 * Window.SCALE));
        this.initialized = true;
    }

    private void updateButtons() {
        if (this.menuHUD.isButtonClicked("start")) {
            this.stateManager.changeToSpectatingState();
            Window.changeToSpectate();
        }
        if (this.menuHUD.isButtonClicked("map editor")) {
            this.stateManager.changeToMapEditState();
            Window.changeToMapEditor();
        }
        if (this.menuHUD.isButtonClicked("exit")) {
            System.exit(0);
        }
    }

    @Override
    public void update() {
        drawRectTexture(background, 0, 0, 960 * Window.SCALE, 695 * Window.SCALE);
        menuHUD.update();
        menuHUD.draw();
        updateButtons();
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

}
