package GA.World.Entity;

import GA.World.Map.Element.Tile;
import GA.World.Map.Map;

import java.util.ArrayList;

import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class SpookyGhost extends Entity {

    private Map map;
    private Tile currentTile;

    public SpookyGhost(int genomeSize, Map map, boolean isRendered) {
        super(genomeSize,map, SpookyGhost.class, isRendered);
        if(isRendered){
            initTexture();
        }
        this.map = map;
        this.currentTile = super.getCurrentTile();
    }

    public SpookyGhost(ArrayList<Integer> genome, Map map, boolean isRendered) {
        super(genome, genome.size(), map, SpookyGhost.class, isRendered);
        if(isRendered){
            initTexture();
        }
        this.map = map;
        this.currentTile = super.getCurrentTile();
    }

    private void initTexture(){
        super.setTexture(quickLoadTexture("ghost"));
    }

    @Override
    public void moveUp() {
        super.moveUp();
        checkIfRunnerKilled();
    }

    @Override
    public void moveDown() {
        super.moveDown();
        checkIfRunnerKilled();
    }

    @Override
    public void moveRight() {
        super.moveRight();
        checkIfRunnerKilled();
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        checkIfRunnerKilled();
    }

    private void checkIfRunnerKilled() {
        Tile currentRunnerTile = this.map.getCurrentRunnerTile();
        if (currentRunnerTile.getX() != currentTile.getX() || currentRunnerTile.getY() != currentTile.getY()) {
            this.currentTile = super.getCurrentTile();
            if(this.currentTile.getX() == currentRunnerTile.getX() && this.currentTile.getY() == currentRunnerTile.getY()) {
                super.stop();
            }
        }else {
            super.stop();
        }
    }

    @Override
    public void resetWithNewGenome(ArrayList<Integer> genome) {
        super.resetWithNewGenome(genome);
        this.currentTile = super.getCurrentTile();
    }

    @Override
    public void resetWithNewMap(Map map) {
        super.resetWithNewMap(map);
        this.map = map;
        this.currentTile = super.getCurrentTile();
    }
}
