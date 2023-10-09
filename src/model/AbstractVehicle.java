package model;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * This class will act as the parent class for all vehicle objects.
 *
 * @author Alan To
 * @Version Winter 2023
 */
public abstract class AbstractVehicle implements Vehicle {

    /** The X coordinate of the vehicle. */
    private int myX;
    /** The initial X coordinate of the vehicle. */
    private final int myInitialX;
    /** The Y coordinate of the vehicle. */
    private int myY;
    /** The initial Y coordinate of the vehicle. */
    private final int myInitialY;
    /** The direction of the vehicle. */
    private Direction myDirection;
    /** The initial direction of the vehicle. */
    private final Direction myInitialDirection;
    /** Number of steps the vehicle should remain dead after collision. */
    private final int myDeathTime;
    /** True if vehicle is alive; false otherwise. */
    private boolean myAlive;
    /** Numbers of times vehicle has been poke after since death. */
    private int myPokeCount;


    /**
     * Constructor method used to initialize and give values to the fields.
     * @param theX the initial X coordinate.
     * @param theY the initial Y coordinate.
     * @param theDir the initial direction for a vehicle.
     * @param theDeathTime the numbers of steps the vehicle should remain dead.
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir,
                              final int theDeathTime) {
        myX = theX;
        myInitialX = theX;
        myY = theY;
        myInitialY = theY;
        myDirection = theDir;
        myInitialDirection = theDir;
        myDeathTime = theDeathTime;
        myAlive = true;
        reset();
    }

    /**
     * Method used to notify that a vehicle has collided with another vehicle object. When used
     * the method should check if the vehicle that's been collided is dead or not.
     * @param theOther The other object.
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (isAlive() && theOther.isAlive() && getDeathTime() > theOther.getDeathTime()) {
            myAlive = false;
        }
    }

    /**
     * What is the death time of the vehicle?
     * @return the death time of the vehicle.
     */
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /**
     * What is the vehicle image file name?
     * @return the image file name.
     */
    public String getImageFileName() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName().toLowerCase(Locale.US));
        if (!isAlive()) {
            sb.append("_dead");
        }
        sb.append(".gif");
        return sb.toString();
    }

    /**
     * Which direction is the vehicle facing?
     * @return the direction the vehicle is facing.
     */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /**
     * What is the X coordinate of the vehicle?
     * @return the X coordinate of the vehicle
     */
    @Override
    public int getX() {
        return myX;
    }

    /**
     * What is the Y coordinate of the vehicle?
     * @return the Y coordinate of the vehicle.
     */
    @Override
    public int getY() {
        return myY;
    }

    /**
     * Is the vehicle alive?
     * @return true if vehicle is alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return myAlive;
    }


    /**
     * Method is used to allow dead vehicles to keep track of how long they've been dead
     * and revive themselves appropriately. Once reviving, it must set its direction to be
     * a random direction.
     */
    @Override
    public void poke() {
        myPokeCount++;
        if (myPokeCount == myDeathTime) {
            myAlive = true;
            setDirection(Direction.random());
            myPokeCount = 0;
        }
    }

    /**
     * Method that instructs the vehicle object to
     */
    @Override
    public void reset() {
        myX = myInitialX;
        myY = myInitialY;
        myDirection = myInitialDirection;
        myPokeCount = 0;
    }

    /**
     * Changing the direction.
     * @param theDir The new direction.
     */
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }

    /**
     * Changing the X coordinate.
     * @param theX The new x-coordinate.
     */
    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    /**
     * Changing the Y coordinate.
     * @param theY The new y-coordinate.
     */
    @Override
    public void setY(final int theY) {
        myY = theY;
    }

    /**
     * Helper method that returns a stream of Direction in order of straight, left, and right.
     * @return Stream of direction.
     */
    protected Stream<Direction> getStraightLeftRightStream() {
        return Stream.of(getDirection(), getDirection().left(), getDirection().right());
    }

}
