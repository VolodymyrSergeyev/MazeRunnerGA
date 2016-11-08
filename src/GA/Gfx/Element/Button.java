package GA.Gfx.Element;

import GA.Gfx.Window;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Vovaxs
 */
public class Button {

    private String name;
    private Texture currentTexture;
    private Texture initTexture;
    private Texture hoverTexture;
    private Texture pressedTexture;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean LMBisDown;
    private boolean LMBisReleased;
    private boolean RMBisDown;
    private boolean hoveredOver;

    public Button(String name, Texture initTexture, int x, int y, int width, int height) {
        this.name = name;
        this.currentTexture = initTexture;
        this.initTexture = initTexture;
        this.hoverTexture = null;
        this.pressedTexture = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Button(String name, Texture initTexture, Texture hoverTexture, Texture pressedTexture, int x, int y, int width, int height) {
        this.name = name;
        this.currentTexture = initTexture;
        this.initTexture = initTexture;
        this.hoverTexture = hoverTexture;
        this.pressedTexture = pressedTexture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Button(String name, Texture initTexture, Texture hoverTexture, Texture pressedTexture, int x, int y) {
        this.name = name;
        this.currentTexture = initTexture;
        this.initTexture = initTexture;
        this.hoverTexture = hoverTexture;
        this.pressedTexture = pressedTexture;
        this.x = x;
        this.y = y;
        this.width = (int) (initTexture.getImageWidth() * Window.SCALE);
        this.height = (int) (initTexture.getImageHeight() * Window.SCALE);
    }

    public Button(String name, Texture initTexture, int x, int y) {
        this.name = name;
        this.currentTexture = initTexture;
        this.initTexture = initTexture;
        this.hoverTexture = null;
        this.pressedTexture = null;
        this.x = x;
        this.y = y;
        this.width = (int) (initTexture.getImageWidth() * Window.SCALE);
        this.height = (int) (initTexture.getImageHeight() * Window.SCALE);
    }

    public void update(){
        checkIsHoveredOver();
        checkIsClicked();
        checkIsReleased();
    }

    private void checkIsHoveredOver() {
        float mouseY = Window.HEIGHT - Mouse.getY() - 1;
        float mouseX = Mouse.getX();
        if (mouseX > this.x &&  mouseX < this.x + (this.width / 2) + (9 * Window.SCALE)
                && mouseY > this.y && mouseY < this.y + (this.height / 2) + (7 * Window.SCALE)) {
            hoveredOver = true;
            changeTexture(this.hoverTexture);
        }else {
            hoveredOver = false;
            changeTexture(this.initTexture);
        }
    }

    private void checkIsReleased() {
        float mouseY = Window.HEIGHT - Mouse.getY() - 1;
        float mouseX = Mouse.getX();
        LMBisReleased = false;
        if (!Mouse.isButtonDown(0) && LMBisDown) {
            this.x -= 2;
            this.y -= 2;
            LMBisDown = false;
            if(mouseX > this.x &&  mouseX < this.x + (this.width / 2) + 9
                    && mouseY > this.y && mouseY < this.y + (this.height / 2) + 7) {
                LMBisReleased = true;
            }
        }
    }

    private void checkIsClicked(){
        float mouseY = Window.HEIGHT - Mouse.getY() - 1;
        float mouseX = Mouse.getX();
        if (Mouse.isButtonDown(0) && mouseX > this.x &&  mouseX < this.x + (this.width / 2) + 9
                && mouseY > this.y && mouseY < this.y + (this.height / 2) + 7) {
            if(!LMBisDown) {
                this.x += 2;
                this.y += 2;
                changeTexture(this.pressedTexture);
            }
            LMBisDown = true;
            LMBisReleased = false;
        }
    }

    public boolean isLMBDown(){
        return this.LMBisDown;
    }

    public boolean isLMBReleased(){
        return this.LMBisReleased;
    }

    public boolean isHoveredOver(){
        return this.hoveredOver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getTexture() {
        return currentTexture;
    }

    private void changeTexture(Texture texture) {
        this.currentTexture = texture == null ? this.currentTexture : texture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
