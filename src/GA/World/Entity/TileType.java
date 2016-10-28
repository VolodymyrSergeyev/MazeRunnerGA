package GA.World.Entity;


public enum TileType {

    Wall("wall", false, "1"), Floor("floor", true, "0"), RunnerSpawn("rs", true, "2"), GhostSpawn("gs", true, "3"), Food("food", true, "4");

    private String textureName;
    private boolean buildable;
    private String id;

    TileType(String textureName, boolean buildable, String id) {
        this.textureName = textureName;
        this.buildable = buildable;
        this.id = id;
    }

    public static String extractTileID(Tile t) {
        String id = TileType.Wall.name();

        for (TileType t1 : TileType.values()) {
            if (t1.name().equals(t.getType().name())) {
                return t1.getId();
            }
        }

        return id;
    }

    public static TileType extractTileType(String id) {
        TileType type = TileType.Wall;

        for (TileType t1 : TileType.values()) {
            if (t1.getId().equals(id)) {
                return t1;
            }
        }

        return type;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
