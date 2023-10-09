package model;

import java.util.Map;

/**
 *  Car vehicle class that should see which direction to choose and
 *  see if terrain is valid to pass.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Car extends AbstractVehicle {

    /** The death time of the car. */
    private static final int DEATH_TIME = 15;

    /**
     * Constructor method for car that will initialize everything.
     * @param theX X coordinate of car.
     * @param theY Y coordinate of car.
     * @param theDir Direction of the car.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }


    /**
     * A car ignores yellow and green lights and drive through green crosswalk lights.
     * A car also drives on the streets.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the terrain is valid, false otherwise.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT && theLight != Light.RED
                || theTerrain == Terrain.CROSSWALK && theLight != Light.RED;
    }


    /**
     * Driving only on the streets, a car prefers to drive straight ahead on the street if it
     * can and if it cannot, it turns left if possible; if it can't turn left, it turns
     * right if possible; as a last resort, it turns around.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the car wants to move in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction returnDir;

        if (theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT
                || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            returnDir = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
            //using else if because going straight doesn't have street or light
            returnDir = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
            returnDir = getDirection().right();
        } else {
            returnDir = getDirection().reverse(); //if we can't go right left or straight
        }
        return returnDir;
    }
}
