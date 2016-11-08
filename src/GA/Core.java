package GA;

import GA.Gfx.Window;
import org.lwjgl.opengl.Display;
import GA.State.StateManager;

public class Core {
    private Window window;
    private StateManager sm;
    private boolean isRunning;
    public Core(){
        this.window = new Window(600, 648, "Maze Runner GA", 1.4f);
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
        this.sm.popManager.interrupt();
    }
}
