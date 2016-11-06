package GA.World.Entity;

import GA.World.Map.Element.Tile;
import GA.World.Map.Element.TileType;
import GA.World.Map.Map;

import java.util.ArrayList;

import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class MazeRunner extends Entity {

    private int genomeSize;
    private Map map;

    private int foodEaten;
    private ArrayList<Tile> changedTiles;

    public MazeRunner(int genomeSize, Map map, boolean isRendered) {
        super(genomeSize, map, MazeRunner.class, isRendered);
        if(isRendered){
            initTexture();
        }
        this.genomeSize = genomeSize;
        this.map = map;
        this.foodEaten = 0;
        this.changedTiles = new ArrayList<>();
    }

    public MazeRunner(ArrayList genome, int genomeSize, Map map, boolean isRendered) {
        super(genome, genomeSize, map, MazeRunner.class, isRendered);
        if(isRendered){
            initTexture();
        }
        this.genomeSize = genomeSize;
        this.map = map;
        this.foodEaten = 0;
        this.changedTiles = new ArrayList<>();
    }

    private void initTexture(){
        super.setTexture(quickLoadTexture("runner"));
    }

    @Override
    public void moveUp() {
        super.moveUp();
        checkCurrentTile();
    }

    @Override
    public void moveDown() {
        super.moveDown();
        checkCurrentTile();
    }

    @Override
    public void moveRight() {
        super.moveRight();
        checkCurrentTile();
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        checkCurrentTile();
    }

    private void checkCurrentTile() {
        Tile currentTile = super.getCurrentTile();
        if(currentTile.getType() == TileType.Food){
            changeCurrentTileTypeToFloor();
            this.foodEaten ++;
        }
        if(currentTile.getX()  == this.map.getCurrentGhostTile().getX() &&
                currentTile.getY()  == this.map.getCurrentGhostTile().getY()){
            die();
        }
    }

    private void die() {
        super.setAlive(false);
    }

    private void revertChangedTiles() {
        for(Tile t: this.changedTiles){
            this.map.setTile((int) t.getX(),(int) t.getY(),TileType.Food, super.isRendered());
        }
        this.changedTiles = new ArrayList<>();
        this.foodEaten = 0;
    }

    private void changeCurrentTileTypeToFloor() {
        this.map.setTile(super.getX(), super.getY(), TileType.Floor, super.isRendered());
        if(super.isRendered()) {
            this.changedTiles.add(this.map.getTile(super.getX(), super.getY()));
        }
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    @Override
    public void resetWithNewGenome(ArrayList<Integer> genome) {
        super.resetWithNewGenome(genome);
        revertChangedTiles();
    }
}
