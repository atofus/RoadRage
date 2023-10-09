package model;

import java.util.Map;

/**
 * ATV class will be used to see which direction the ATV will choose and
 * see if terrain is valid to pass.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Atv extends AbstractVehicle {

    /** The death time of the ATV. */
    private static final int DEATH_TIME = 25;

    /**
     * Constructor method for the ATV that will initialize everything.
     * @param theX X coordinate for the ATV.
     * @param theY Y coordinate for the ATV.
     * @param theDir Direction the ATV will face.
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Sees if the ATV can pass a certain terrain. Atv should be able
     * to pass through all terrains besides walls without stopping.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the ATV can pass, false otherwise.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain == Terrain.WALL) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Atv will randomly choose which direction it'll go to. Ignoring a wall,
     * an Atv should never reverse its direction.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the ATV will face.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = Direction.random();
        //if there's a wall in front OR what was chosen was reverse we want to keep choosing a
        //different random direction UNTIL it's not facing a wall or facing reverse.
        // To do this, I used a while loop.
        while (theNeighbors.get(result) == Terrain.WALL
                || result == getDirection().reverse()) {
            result = Direction.random();
        }

        return result;
    }
}
