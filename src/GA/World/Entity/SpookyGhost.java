package GA.World.Entity;

import GA.World.Map.Map;

import static GA.Gfx.Helper.Artist.quickLoadTexture;

public class SpookyGhost extends Entity {
    public SpookyGhost(Map map, int genomeSize, boolean isRendered) {
        super(genomeSize,map, SpookyGhost.class, isRendered);
    }
}
