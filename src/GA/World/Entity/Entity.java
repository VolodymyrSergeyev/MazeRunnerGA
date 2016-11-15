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
    private boolean stoped;

    private ArrayList<Integer> genome;
    private int genomeSize;

    private int tick = 0;
    private int currentGene = 0;

    private Random rand;
    private Class clas;

    private boolean isRendered;
    private int failedMovesMade;
    private int speed = 10;

    Entity(int genomeSize, Map map, Class clas, boolean isRendered){
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
            if(this.clas == MazeRunner.class){
                this.map.setCurrentRunnerTile(this.currentTile);
            }else if (this.clas == SpookyGhost.class){
                this.map.setCurrentGhostTile(this.currentTile);
            }
        }
        this.mapBlockSize = this.map.getBlockSize();
        this.xCord = (int) this.currentTile.getX();
        this.yCord = (int) this.currentTile.getY();
        this.angle = 0;
        this.isAlive = true;
        this.stoped = false;
        this.failedMovesMade = 0;
    }

    Entity(ArrayList<Integer> genome, int genomeSize, Map map, Class clas, boolean isRendered){
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
            if(this.clas == MazeRunner.class){
                this.map.setCurrentRunnerTile(this.currentTile);
            }else if (this.clas == SpookyGhost.class){
                this.map.setCurrentGhostTile(this.currentTile);
            }
        }
        this.mapBlockSize = this.map.getBlockSize();
        this.xCord = (int) this.currentTile.getX();
        this.yCord = (int) this.currentTile.getY();
        this.angle = 0;
        this.isAlive = true;
        this.stoped = false;
        this.failedMovesMade = 0;
    }

    public void update(){
        this.tick++;
        if(this.tick >= this.speed){
            if(this.currentGene < this.genomeSize && !stoped) {
                moveByGenomeId(this.currentGene);
                this.currentGene++;
            }
            this.tick = 0;
        }
    }

    public void draw() {
        float modXCord = (this.xCord * this.mapBlockSize) * this.map.getSCALE();
        float modYCord = (this.yCord * this.mapBlockSize) * this.map.getSCALE();
        if (isAlive) {
            drawRotatableRectTexture(this.texture, modXCord, modYCord,
                    this.mapBlockSize * this.map.getSCALE(), this.mapBlockSize * this.map.getSCALE(), this.angle);
        }
    }

    public void moveUp(){
        this.angle = 0;
        int modXCord = xCord;
        int modYCord = yCord - 1;
        this.move(modXCord, modYCord);
    }

    public void moveDown(){
        this.angle = 180;
        int modXCord = xCord;
        int modYCord = yCord + 1;
        this.move(modXCord, modYCord);

    }

    public void moveRight(){
        this.angle = 90;
        int modXCord = xCord + 1;
        int modYCord = yCord;
        this.move(modXCord, modYCord);
    }

    public void moveLeft(){
        this.angle = 270;
        int modXCord = xCord - 1;
        int modYCord = yCord;
        this.move(modXCord, modYCord);
    }

    private void move(int modXCord, int modYCord) {
        if(isAlive && !stoped) {
            Tile futureTile = this.map.getTile(modXCord, modYCord);
            if (futureTile.isWalkable()) {
                this.yCord = modYCord;
                this.xCord = modXCord;
            } else {
                this.failedMovesMade++;
            }
            this.currentTile = map.getTile(this.xCord, this.yCord);
            setCurrentMapTile();
        }
    }

    private void setCurrentMapTile() {
        if(clas == MazeRunner.class){
            this.map.setCurrentRunnerTile(this.currentTile);
        }else {
            this.map.setCurrentGhostTile(this.currentTile);
        }
    }

    public void moveByGenomeId(int geneId){
        if(isAlive) {
            switch (this.genome.get(geneId)) {
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
    }

    private Tile getRandomSpawn() {
        Random r = new Random();
        int ranX = r.nextInt(this.map.getMapWidth() - 1) + 1;
        int ranY = r.nextInt(this.map.getMapHeight() - 1) + 1;
        Tile ranTile = this.map.getTile(ranX, ranY);
        if (ranTile.getType() == TileType.Floor || ranTile.getType() == TileType.Food) {
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

    Tile  getCurrentTile(){
        return this.currentTile;
    }

    public int getX() {
        return xCord;
    }

    public int getY() {
        return yCord;
    }

    public boolean isAlive() {
        return isAlive;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    void stop(){
        this.stoped = true;
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }

    void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void resetWithNewGenome(ArrayList<Integer> genome){
        this.genome = genome;
        this.genomeSize = genome.size();
        reset();
        returnToStartPos();
    }

    private void reset() {
        this.currentGene = 0;
        this.failedMovesMade = 0;
        this.isAlive = true;
        this.stoped = false;
    }

    private void returnToStartPos() {
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

    boolean isRendered() {
        return isRendered;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void resetWithNewMap(Map map) {
        this.map = map;
        reset();
        returnToStartPos();
    }
}
