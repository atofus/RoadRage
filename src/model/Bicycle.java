package model;

import java.util.Map;

/**
 * Bicycle vehicle class should be able to choose which direction the Bicycle will go towards
 * and see if the terrain for it is valid.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Bicycle extends AbstractVehicle {

    /** The death time of the bicycle. */
    private static final int DEATH_TIME = 35;

    /**
     * Constructor for bicycle, it should initialize all values.
     * @param theX X coordinate of the bicycle.
     * @param theY Y coordinate of the bicycle.
     * @param theDir Direction the bicycle is facing.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * A bicycle should be able to go on a trail. It should also go on a street if necessary.
     * A bicycle should also stop for all yellow and red lights.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the bicycle can pass, false otherwise.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.TRAIL
                || theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN
                || theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT && theLight == Light.GREEN;
    }

    /**
     * If there's terrain next to the bicycle that's a trail, it should always face that
     * direction where the trail is. If there's no trail straight, right or left, it should
     * move straight ahead on the street, crosswalk, or light. If it can't move straight ahead,
     * it turns left if possible; if it cannot turn left, it turns right. If none of these 3
     * directions is legal, the bicycle should reverse.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the bicycle would like to move in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction returnDir;
        if (theNeighbors.get(getDirection()) == Terrain.TRAIL) {
            returnDir = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.TRAIL) {
            returnDir = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.TRAIL) {
            returnDir = getDirection().right();
        } else if (theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT
                || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            //if there's no trail around but there's street ahead
            returnDir = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
            //if no trail and street ahead, we turn left
            returnDir = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
            //if no trail and street ahead and on the left, we go ahead
            returnDir = getDirection().right();
        } else { //if no cross walks, streets, lights and trails are around
            returnDir = getDirection().reverse();
        }

        return returnDir;
    }
}
