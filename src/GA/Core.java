package GA;

import GA.Gfx.Window;
import org.lwjgl.opengl.Display;
import GA.State.StateManager;

public class Core {
    private Window window;
    private StateManager sm;
    public static boolean isRunning;
    public Core(int width, int height, float scale){
        this.window = new Window(width, height, "Maze Runner GA", scale);
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
        System.exit(0);
    }
}
