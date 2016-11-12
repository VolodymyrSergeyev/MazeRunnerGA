package GA.World.Map.Element;

import GA.Gfx.Window;
import org.newdawn.slick.opengl.Texture;

import static GA.Gfx.Helper.Artist.quickLoadTexture;
import static GA.Gfx.Helper.Artist.drawRotatableRectTexture;
/**
 *
 * @author Vovaxs
 */
public class Tile {

    private float x;
    private float y;
    private float width;
    private float height;
    private Texture texture;
    private TileType type;
    private final float SCALE;
    private String angleID;
    private final int[] angleTypes;
    private boolean isWalkable;

    public Tile(int x, int y, int width, int height, TileType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.SCALE = Window.getSCALE();
        this.angleTypes = new int[4];
        this.angleTypes[0] = 0;
        this.angleTypes[1] = 90;
        this.angleTypes[2] = 180;
        this.angleTypes[3] = 270;
        this.angleID = "0";
        this.isWalkable = type.isWalkable();
    }

    public void initTexture(){
        this.texture = quickLoadTexture(type.getTextureName());
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setBuildable(boolean isBuildable) {
        this.isWalkable = isBuildable;
    }

    public void draw() {
        drawRotatableRectTexture(texture, (x * width) * SCALE, (y * height) * SCALE, width * SCALE, height * SCALE, angleTypes[Integer.parseInt(angleID)]);
    }

    public float getX() {
        return x;
    }

    public Tile getCopy(){
        return new Tile((int)this.x, (int)this.y, (int)this.width, (int)this.height, this.type);
    }

    public float getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }
}
