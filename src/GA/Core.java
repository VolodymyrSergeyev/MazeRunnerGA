package GA;

import GA.Gfx.Window;
import org.lwjgl.opengl.Display;
import GA.State.StateManager;

public class Core {
    Window window;
    StateManager sm;
    boolean isRunning;
    public Core(){
        this.window = new Window(800, 600, "Maze Runner GA", 1);
        this.sm = new StateManager();
        this.isRunning = false;
    }
    public void start(){
        isRunning = true;
        this.window.init();
        while(isRunning && !Display.isCloseRequested()){
            this.sm.update();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
}
