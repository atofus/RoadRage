package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Truck vehicle object that should see which direction to choose and
 * see if terrain is valid to pass.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Truck extends AbstractVehicle {

    /** The death time of the truck. */
    private static final int DEATH_TIME = 0;

    /**
     * Constructor for truck that will initialize everything.
     * @param theX X coordinate of the truck.
     * @param theY Y coordinate of the truck.
     * @param theDir The direction the truck is facing.
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Will check and specify what terrain the truck can pass through. Truck
     * can pass through: street, light, and crosswalks.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the terrain is valid, falose otherwise.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK && theLight != Light.RED;
    }

    /**
     * This method will choose the direction of the truck. Trucks are said to
     * randomly go straight, left or right when there's a street, light or crosswalk. If
     * none of these are legal, it'll turn around.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the truck is going to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        Direction returnDir = null;
        //used to pick a random direction out of all possibilites
        final Random rand = new Random();
        //we put all the directions that's possible to go towards at that moment
        final List<Direction> possibleDirections = new ArrayList<>();

        //will check the LEFT side of the truck
        if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
            //we'll add going left as a possibility if we can
            possibleDirections.add(getDirection().left());
        }

        //will check the RIGHT side of truck
        if (theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
            //we'll add going right as possibility if we can
            possibleDirections.add(getDirection().right());
        }

        //will check the RIGHT side of truck
        if (theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT
                || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            //we'll add going forward as a possibility if we can
            possibleDirections.add(getDirection());
        }

        if (possibleDirections.size() == 0) {
            returnDir = getDirection().reverse();
        } else {
            final int randomDirectionIndex = rand.nextInt(possibleDirections.size());
            returnDir = possibleDirections.get(randomDirectionIndex);
        }
        return returnDir;

        /*return getStraightLeftRightStream().
                filter(x -> isValidTerrain(theNeighbors.get(x))).
                sorted(Collections.shuffle).
                findFirst.orElse(
                        getDirection().reverse()); */
    }

    /**
     * Helper method for chooseDirection that checks if the terrain is valid for
     * truck to go on.
     * @param theTerrain the Terrain to test for validity.
     * @return true if we can go on that terrain, false otherwise.
     */
    private boolean isValidTerrain(final Terrain theTerrain) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK;
    }
}
