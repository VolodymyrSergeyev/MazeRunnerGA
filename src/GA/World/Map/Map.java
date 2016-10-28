package GA.World.Map;

import GA.Gfx.Window;
import GA.World.Entity.Tile;
import GA.World.Entity.TileType;

public class Map {
    private int blockSize = 16;
    private int mapWidth = Window.WIDTH / blockSize;
    private int mapHeight = (Window.HEIGHT / blockSize) - 1;
    private int frameWidth;
    private int frameHeight;
    private float SCALE;

    public Tile[][] map;

    public Map() {
        frameWidth = Window.getWIDTH();
        frameHeight = Window.getHEIGHT();
        this.SCALE = Window.getSCALE();
        map = new Tile[mapWidth][mapHeight];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(i * blockSize, j * blockSize, blockSize,
                        blockSize, TileType.Floor);
            }
        }
    }

    public Map(int[][] newMap) {
        frameWidth = Window.getWIDTH();
        frameHeight = Window.getHEIGHT();
        this.SCALE = Window.getSCALE();
        map = new Tile[mapWidth][mapHeight];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (newMap[j][i] == 0) {

                } else {
                    map[i][j] = new Tile(i * blockSize, j * blockSize,
                            blockSize, blockSize, TileType.Floor);
                }
                switch (newMap[j][i]) {
                    case 0:
                        map[i][j] = new Tile(i * blockSize, j * blockSize,
                                blockSize, blockSize, TileType.Floor);
                        break;
                    case 1:
                        map[i][j] = new Tile(i * blockSize, j * blockSize,
                                blockSize, blockSize, TileType.Wall);
                        break;
                    case 2:
                        map[i][j] = new Tile(i * blockSize, j * blockSize,
                                blockSize, blockSize, TileType.RunnerSpawn);
                        break;
                    case 3:
                        map[i][j] = new Tile(i * blockSize, j * blockSize,
                                blockSize, blockSize, TileType.GhostSpawn);
                        break;
                    case 4:
                        map[i][j] = new Tile(i * blockSize, j * blockSize,
                                blockSize, blockSize, TileType.Food);
                        break;
                }
            }
        }
    }

    public void setTile(int xCoord, int yCoord, TileType type) {
        map[xCoord][yCoord] = new Tile((int) (xCoord * this.blockSize), (int) (yCoord * this.blockSize), this.blockSize, this.blockSize, type);
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
}
