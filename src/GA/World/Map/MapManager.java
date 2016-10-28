package GA.World.Map;

import GA.World.Entity.TileType;

import java.io.*;
import java.util.logging.Logger;


public class MapManager {

    public static final String MAIN_MAP = "mainMap.MAP";
    public static final String DEFAULT_MAP = "defaultMap.MAP";
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
        for (int i = 0; i < map.getMapHeight(); i++) {
            for (int j = 0; j < map.getMapWidth(); j++) {
                mapData += TileType.extractTileID(map.getTile(j, i));
                if(j == map.getMapWidth() - 1 && i < map.getMapHeight() - 1){
                    mapData += "\n";
                }
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
        return loadMap(MAIN_MAP, true);
    }

    public static Map loadDefaultMap() {
        return loadMap(DEFAULT_MAP, false);
    }

    private static Map loadMap(String mapName, boolean first) {
        Map map = new Map();
        String data = "";
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(DEFAULT_FILE_PATH + mapName));
            while ( (line = bufferedReader.readLine()) != null){
                data += line;
            }
        } catch (Exception e) {
            if (first) {
                loadDefaultMap();
            }
            System.out.println("Map does not exist! Generating default map");
        }
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                map.setTile(x, y, TileType.extractTileType(data.substring(y * map.getMapWidth() + x, y * map.getMapWidth() + x + 1)));
            }
        }
        return map;
    }
}
