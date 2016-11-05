package GA.World.Entity;


import GA.World.Map.Element.Tile;
import GA.World.Map.Element.TileType;
import GA.World.Map.Map;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.Random;

import static GA.Gfx.Helper.Artist.drawRotatableRectTexture;

public abstract class Entity {
    private Map map;
    private int xCord;
    private int yCord;
    private int mapBlockSize;
    private Tile currentTile;

    private Texture texture;
    private float angle;

    private boolean isAlive;

    private ArrayList<Integer> genome;
    private int genomeSize;
    private int movesMade;

    private int tick = 0;
    private int currentGene = 0;

    private Random rand;
    private Class clas;

    private boolean isRendered;

    public Entity(int genomeSize, Map map, Class clas, boolean isRendered){
        this.clas = clas;
        this.isRendered = isRendered;
        this.rand = new Random();
        this.genomeSize = genomeSize;
        this.genome = generateRandomGenome(this.genomeSize);
        this.map = map;
        if(this.clas == MazeRunner.class){
            this.currentTile = map.getRunnerSpawn();
        }else if (this.clas == SpookyGhost.class){
            this.currentTile = map.getGhostSpawn();
        }
        if(this.currentTile == null){
            this.currentTile = getRandomSpawn();
        }
        this.mapBlockSize = this.map.getBlockSize();
        this.xCord = (int) this.currentTile.getX() * this.mapBlockSize;
        this.yCord = (int) this.currentTile.getY() * this.mapBlockSize;
        this.angle = 0;
        this.isAlive = true;
        this.movesMade = 0;
    }

    public Entity(ArrayList genome, int genomeSize, Map map, Class clas, boolean isRendered){
        this.clas = clas;
        this.rand = new Random();
        this.isRendered = isRendered;
        this.genome = genome;
        this.genomeSize = genomeSize;
        this.map = map;
        if(clas == MazeRunner.class){
            this.currentTile = map.getRunnerSpawn();
        }else if (clas == SpookyGhost.class){
            this.currentTile = map.getGhostSpawn();
        }
        if(this.currentTile == null){
            this.currentTile = getRandomSpawn();
        }
        this.mapBlockSize = this.map.getBlockSize();
        this.xCord = (int) this.currentTile.getX();
        this.yCord = (int) this.currentTile.getY();
        this.angle = 0;
        this.isAlive = true;
        this.movesMade = 0;
    }

    public void update(){
        this.tick++;
        if(this.tick == 15){
            if(this.currentGene < this.genomeSize) {
                moveByGenomeId(this.currentGene);
            }
            this.currentGene++;
            this.tick = 0;
        }
    }

    public void draw() {
        if (isAlive) {
            drawRotatableRectTexture(this.texture, this.xCord * this.map.getSCALE(), this.yCord * this.map.getSCALE(),
                    this.mapBlockSize * this.map.getSCALE(), this.mapBlockSize * this.map.getSCALE(), this.angle);
        }
    }

    public void moveUp(){
        this.angle = 0;
        int modXCord = xCord / this.mapBlockSize;
        int modYCord = yCord / this.mapBlockSize - 1;
        this.move(modXCord, modYCord);
    }

    public void moveDown(){
        this.angle = 180;
        int modXCord = xCord / this.mapBlockSize;
        int modYCord = yCord / this.mapBlockSize + 1;
        this.move(modXCord, modYCord);

    }

    public void moveRight(){
        this.angle = 90;
        int modXCord = xCord / this.mapBlockSize + 1;
        int modYCord = yCord / this.mapBlockSize;
        this.move(modXCord, modYCord);
    }

    public void moveLeft(){
        this.angle = 270;
        int modXCord = xCord / this.mapBlockSize - 1;
        int modYCord = yCord / this.mapBlockSize;
        this.move(modXCord, modYCord);
    }

    private void move(int modXCord, int modYCord) {
        Tile futureTile = this.map.getTile(modXCord, modYCord);
        if(futureTile.isWalkable()){
            this.movesMade++;
            this.yCord = modYCord * this.mapBlockSize;
            this.xCord = modXCord * this.mapBlockSize;
            this.currentTile = futureTile;
        }
    }

    public void moveByGenomeId(int geneId){
        switch (this.genome.get(geneId)){
            case 0:
                this.moveUp();
                break;
            case 1:
                this.moveDown();
                break;
            case 2:
                this.moveRight();
                break;
            case 3:
                this.moveLeft();
                break;
        }
    }

    private Tile getRandomSpawn() {
        Random r = new Random();
        int ranX = r.nextInt(this.map.getMapWidth() - 1) + 1;
        int ranY = r.nextInt(this.map.getMapHeight() - 1) + 1;
        Tile ranTile = this.map.getTile(ranX, ranY);
        if (ranTile.getType() == TileType.Floor) {
            return ranTile;
        }
        return getRandomSpawn();
    }

    private ArrayList<Integer> generateRandomGenome(int genomeSize){
        ArrayList<Integer> tmp = new ArrayList<>();
        for(int i = 0; i < genomeSize; i++){
            tmp.add(this.rand.nextInt(4));
        }
        return tmp;
    }

    public Tile  getCurrentTile(){
        return this.currentTile;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public int getX() {
        return xCord / this.mapBlockSize;
    }

    public int getY() {
        return yCord / this.mapBlockSize;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }

    public void setGenome(ArrayList<Integer> genome) {
        this.genome = genome;
    }

    public int getGenomeSize(){
        return this.genome.size();
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void resetWithNewGenome(ArrayList<Integer> genome){
        this.genome = genome;
        this.genomeSize = genome.size();
        this.currentGene = 0;
        returnToStartPos();
        this.movesMade = 0;
        this.isAlive = true;
    }

    public void returnToStartPos() {
        if(this.clas == MazeRunner.class){
            this.currentTile = map.getRunnerSpawn();
        }else if (this.clas == SpookyGhost.class){
            this.currentTile = map.getGhostSpawn();
        }
        if(this.currentTile == null){
            this.currentTile = getRandomSpawn();
        }
        this.mapBlockSize = this.map.getBlockSize();
        this.xCord = (int) this.currentTile.getX();
        this.yCord = (int) this.currentTile.getY();
        this.angle = 0;
    }

    public boolean isRendered() {
        return isRendered;
    }
}
