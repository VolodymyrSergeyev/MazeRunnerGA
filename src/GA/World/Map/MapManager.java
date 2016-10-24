package GA.World.Map;

import GA.World.Entity.TileType;

import java.io.*;
import java.util.logging.Logger;


public class MapManager {

    public static final String MAIN_MAP = "mainMap";
    public static final String DEFAULT_MAP = "defaultMap";
    private static String DEFAULT_FILE_PATH = "res" + File.separator + "maps" + File.separator;
    private static BufferedReader bufferedReader;

    public static String getDEFAULT_FILE_PATH() {
        return DEFAULT_FILE_PATH;
    }

    public static void setDEFAULT_FILE_PATH(String DEFAULT_FILE_PATH) {
        MapManager.DEFAULT_FILE_PATH = DEFAULT_FILE_PATH;
    }

    public static void saveMap(Map map) {
        String mapData = "";
        for (int i = 0; i < map.getMapWidth(); i++) {
            for (int j = 0; j < map.getMapHeight(); j++) {
                mapData += TileType.extractTileID(map.getTile(i, j));
                mapData += map.getTile(i, j).getAngleID();
            }
        }

        try {
            File file = new File(DEFAULT_FILE_PATH + MAIN_MAP);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData);
            bw.close();
            System.out.println("File created in:" + file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(MapManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static Map loadMainMap() {
        return loadMap(MAIN_MAP);
    }

    public static Map loadDefaultMap() {
        return loadMap(DEFAULT_MAP);
    }

    private static Map loadMap(String mapName) {
        Map map = new Map();
        try {
            bufferedReader = new BufferedReader(new FileReader(DEFAULT_FILE_PATH + mapName));
            String data = bufferedReader.readLine();
            for (int x = 0; x < map.getMapWidth(); x++) {
                for (int y = 0; y < map.getMapHeight(); y++) {
                    map.setTile(x, y, TileType.extractTileType(data.substring((x * 2) * map.getMapHeight() + (y * 2), (x * 2) * map.getMapHeight() + (y * 2) + 1)), data.substring((x * 2) * map.getMapHeight() + (y * 2) + 1, (x * 2) * map.getMapHeight() + (y * 2) + 2));
                }
            }
        } catch (Exception e) {
            System.out.println("Map does not exist! Loading default map");
        }
        return map;
    }
}
