package GA.World.Map;

import GA.Gfx.Window;
import GA.World.Map.Element.Tile;
import GA.World.Map.Element.TileType;

public class Map {
    private int blockSize = 16;
    private int mapWidth = Window.WIDTH / blockSize;
    private int mapHeight = (Window.HEIGHT / blockSize) - 1;
    private int frameWidth;
    private int frameHeight;
    private float SCALE;

    private Tile runnerSpawn;
    private Tile ghostSpawn;

    private Tile currentRunnerTile;
    private Tile currentGhostTile;

    public Tile[][] map;

    private int maxFood = 0;

    public Map(final Map map) {
        this.frameWidth = map.getFrameWidth();
        this.frameHeight = map.getMapHeight();
        this.SCALE = map.getSCALE();
        this.map = getMapClone(map.getMap());
        this.runnerSpawn = map.getRunnerSpawn();
        this.ghostSpawn = map.getGhostSpawn();
        this.currentRunnerTile = map.getCurrentRunnerTile();
        this.currentGhostTile = map.getCurrentGhostTile();
        this.maxFood = map.getMaxFood();
    }

    public Map() {
        this.frameWidth = Window.getWIDTH();
        this.frameHeight = Window.getHEIGHT();
        this.SCALE = Window.getSCALE();
        this.map = new Tile[mapWidth][mapHeight];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(i * blockSize, j * blockSize, blockSize,
                        blockSize, TileType.Floor);
            }
        }
    }

    public Map(int[][] newMap) {
        this.frameWidth = Window.getWIDTH();
        this.frameHeight = Window.getHEIGHT();
        this.SCALE = Window.getSCALE();
        this.map = new Tile[mapWidth][mapHeight];
        Tile tmp;
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                if (newMap[j][i] == 0) {

                } else {
                    switch (newMap[j][i]) {
                        case 0:
                            this.map[i][j] = new Tile(i * blockSize, j * blockSize,
                                    blockSize, blockSize, TileType.Floor);
                            break;
                        case 1:
                            this.map[i][j] = new Tile(i * blockSize, j * blockSize,
                                    blockSize, blockSize, TileType.Wall);
                            break;
                        case 2:
                            tmp = new Tile(i * blockSize, j * blockSize,
                                    blockSize, blockSize, TileType.RunnerSpawn);
                            this.map[i][j] = tmp;
                            this.runnerSpawn = tmp;
                            break;
                        case 3:
                            tmp = new Tile(i * blockSize, j * blockSize,
                                    blockSize, blockSize, TileType.GhostSpawn);
                            this.map[i][j] = tmp;
                            this.ghostSpawn = tmp;
                            break;
                        case 4:
                            this.map[i][j] = new Tile(i * blockSize, j * blockSize,
                                    blockSize, blockSize, TileType.Food);
                            this.maxFood++;
                            break;
                    }
                }
            }
        }
    }

    public Map(Tile[][] map) {
        this.frameWidth = Window.getWIDTH();
        this.frameHeight = Window.getHEIGHT();
        this.SCALE = Window.getSCALE();
        this.map = (Tile[][]) map.clone();
        int count = 0;
        for(Tile[] tiles : map){
            for (Tile t: tiles){
                TileType tType = t.getType();
                if(tType == TileType.RunnerSpawn){
                    this.runnerSpawn = t;
                    this.currentRunnerTile = t;
                }
                if(tType == TileType.GhostSpawn){
                    this.ghostSpawn = t;
                    this.currentGhostTile = t;
                }
                if(tType == TileType.Food){
                    count++;
                }
            }
        }
        this.maxFood = count;
    }

    public void setTile(int xCoord, int yCoord, TileType type, boolean initRender) {
        Tile tile = new Tile((int) (xCoord * this.blockSize), (int) (yCoord * this.blockSize), this.blockSize, this.blockSize, type);
        map[xCoord][yCoord] = tile;
        if(initRender){
            tile.initTexture();
        }
    }

    public void setUnbuildable(int xCoord, int yCoord) {
        map[xCoord][yCoord].setBuildable(false);
    }

    public Tile getTile(int xPos, int yPos) {
        if (xPos < mapWidth && yPos < mapHeight && xPos > -1 && yPos > -1) {
            return map[xPos][yPos];
        } else {
            return new Tile(0, 0, 0, 0, TileType.Wall);
        }
    }

    public void initRendering(){
        for(Tile[] map1 : map){
            for (Tile t : map1){
                t.initTexture();
            }
        }
    }

    public void draw() {
        for (Tile[] map1 : map) {
            for (Tile t : map1) {
                t.draw();
            }
        }
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    public float getSCALE() {
        return SCALE;
    }

    public Tile getRunnerSpawn() {
        return runnerSpawn;
    }

    public void setRunnerSpawn(Tile runnerSpawn) {
        this.runnerSpawn = runnerSpawn;
    }

    public Tile getGhostSpawn() {
        return ghostSpawn;
    }

    public void setGhostSpawn(Tile ghostSpawn){
        this.ghostSpawn = ghostSpawn;
    }

    public Tile getCurrentRunnerTile() {
        return currentRunnerTile;
    }

    public void setCurrentRunnerTile(Tile currentRunnerTile) {
        this.currentRunnerTile = currentRunnerTile;
    }

    public Tile getCurrentGhostTile() {
        return currentGhostTile;
    }

    public void setCurrentGhostTile(Tile currentGhostTile) {
        this.currentGhostTile = currentGhostTile;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public void setMaxFood(int maxFood) {
        this.maxFood = maxFood;
    }

    public Tile[][] getMapClone(Tile[][] map) {
        Tile[][] mapClone = new Tile[this.mapWidth][this.mapHeight];
        for(int x =0; x < this.mapWidth; x++){
            for(int y = 0; y < this.mapHeight; y++){
                mapClone[x][y] = (map[x][y]).getCopy();
            }
        }
        return mapClone;
    }
}
