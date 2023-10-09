package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Human object should see which direction to choose and
 * see if terrain is valid to pass.
 *
 * @author Alan To
 * @version Winter 2023
 */
public class Human extends AbstractVehicle {


    /** The death time of the human. */
    private static final int DEATH_TIME = 45;

    /**
     * Constructs the human object with the given.
     * @param theX X coordinate of the human.
     * @param theY Y coordinate of the human.
     * @param theDir Direction of the human.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Sees if the human can pass through the terrain. Can travel through crosswalks when
     * crosswalk lights are yellow or red, not green.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return true if the human can pass, false otherwise
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.GRASS
                || theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW
                || theTerrain == Terrain.CROSSWALK && theLight == Light.RED;
    }

    /**
     * Humans should move in a random direction: straight, left, or right.
     * If a human is next to a crosswalk, it will always turn to choose the direction
     * of the crosswalk. Human never reverses unless there are no more options.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction the human is going to move
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        Direction returnDir = null;
        //used to pick a random direction out of all possibilites
        final Random rand = new Random();
        //we put all the directions that's possible to go towards at that moment
        final List<Direction> possibleDirectionsGrass = new ArrayList<>();
        //List<Direction> possibleDirectionsCrossWalk = new ArrayList<>();

        //will check the LEFT side of the human
        if (theNeighbors.get(getDirection().left()) == Terrain.GRASS) {
            //we'll add going left as a possibility if we can
            possibleDirectionsGrass.add(getDirection().left());
        }

        if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
            return getDirection().left();
        }

        //will check the RIGHT side of the human
        if (theNeighbors.get(getDirection().right()) == Terrain.GRASS) {
            //we'll add going right as possibility if we can
            possibleDirectionsGrass.add(getDirection().right());
        }

        if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
            return getDirection().right();
        }

        //will check the RIGHT side of the human
        if (theNeighbors.get(getDirection()) == Terrain.GRASS) {
            //we'll add going forward as a possibility if we can
            possibleDirectionsGrass.add(getDirection());
        }
        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            return getDirection();
        }

        if (possibleDirectionsGrass.size() == 0) {
            returnDir = getDirection().reverse();
        } else {
            final int randomDirectionIndex = rand.nextInt(possibleDirectionsGrass.size());
            returnDir = possibleDirectionsGrass.get(randomDirectionIndex);
        }
        return returnDir;
    }
}
