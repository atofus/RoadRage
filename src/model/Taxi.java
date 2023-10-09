package model;

import java.util.Map;

/**
 * Taxi vehicle class that should see which direction to choose and
 * see if terrain is valid to pass.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Taxi extends AbstractVehicle {

    /** The death time of taxi. */
    private static final int DEATH_TIME = 15;
    /** The clock time for taxi to move during a red light crosswalk. */
    private static final int REQUIRED_CLOCK = 3;
    /** The clock counter until the taxi can move again during a red light crosswalk. */
    private int myClockCycle;

    /**
     * Constructor for taxi to initialize values.
     * @param theX X coordinates of the taxi.
     * @param theY Y coordinates of the taxi.
     * @param theDir Direction of the taxi.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
        myClockCycle = 0;
    }

    /**
     * Sees if the taxi can go through terrain at that moment depending on the light. It can
     * go through streets, lights and crosswalks. However, if the crosswalk light is red, then
     * the taxi will stay still until after 3 clock cycles.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the vehicle is allowed to pass, false otherwise.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        //int clockCycle = 0; //initialize the clock cycle
        boolean result = false;
        if (theTerrain == Terrain.STREET) {
            result = true;
        } else if (theTerrain == Terrain.LIGHT && theLight != Light.RED) {
            result = true;
        } else if (theTerrain == Terrain.CROSSWALK && (theLight == Light.GREEN
                || theLight == Light.YELLOW)) {
            result = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            if (myClockCycle == REQUIRED_CLOCK) {
                result = true;
                myClockCycle = 0; //reset the clock cycle
            } else {
                myClockCycle++; //adding another cycle
            }
        }
        return result;
    }

    /**
     * Taxi prefers to drive ahead on street, crosswalks or light if it can. If it can't,
     * it will face left. If it can't go left or straight, it goes right. If none of these
     * work, the taxi will reverse.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the taxi wants to move in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction returnDir;
        //SAME IDEA AS CAR
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
