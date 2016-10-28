package GA.State;


import GA.Gfx.HUD;
import GA.Gfx.Window;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static GA.Gfx.Helper.Artist.drawRectTexture;
import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class Init implements State {

    private Texture background;
    private HUD menuHUD;
    private final StateManager stateManager;
    
    private boolean LMBisDown;
    private boolean initialized = false;
    
    public Init(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void init() {
        this.background = quickLoadTexture("bk");
        this.menuHUD = new HUD();
        this.menuHUD.addButton("start", "start", Window.WIDTH / 2 - 130, (int) (Window.HEIGHT * 0.45f));
        this.menuHUD.addButton("map editor", "map editor", Window.WIDTH / 2 - 130, (int) (Window.HEIGHT * 0.45f) + 100);
        this.menuHUD.addButton("exit", "exit", Window.WIDTH / 2 - 130, (int) (Window.HEIGHT * 0.45f) + 200);
        this.initialized = true;
    }
    
    private void updateButtons() {
        if (Mouse.isButtonDown(0) && !LMBisDown) {
            if (this.menuHUD.isButtonClicked("start")) {
                this.stateManager.changeToSpectatingState();
            }
            if (this.menuHUD.isButtonClicked("map editor")) {
                this.stateManager.changeToMapEditState();
            }
            if (this.menuHUD.isButtonClicked("exit")) {
                System.exit(0);
            }
        }
        LMBisDown = Mouse.isButtonDown(0);
    }
    
    @Override
    public void update() {
        drawRectTexture(background, 0, 0, 1280, 640);
        menuHUD.draw();
        updateButtons();
    }
    
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
}
