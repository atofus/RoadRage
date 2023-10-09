package tests;


import model.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for Truck class.
 *
 * @author Alan To
 * @version Winter 2023
 */
class TruckTest {


    /** Test method for truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(19, 9, Direction.WEST);

        assertEquals(19, t.getX(), "Truck x coordinate not initialized correctly!");
        assertEquals(9, t.getY(), "Truck y coordinate not initialized correctly!");
        assertEquals(Direction.WEST, t.getDirection(), "Truck direction not "
                + "initialized correct");
        assertEquals(0, t.getDeathTime(), "Truck death time not initialized correct");
        assertTrue(t.isAlive(), "Truck isAlive() fails initially");
    }

    /** Test method for truck setters. */
    @Test
    public void testTruckSetters() {
        final Truck t = new Truck(19, 9, Direction.WEST);

        t.setX(10);
        assertEquals(10, t.getX(), "Truck setX failed");
        t.setY(17);
        assertEquals(17, t.getY(), "Truck setY failed");
        t.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, t.getDirection(), "Truck setDirection failed");
    }


    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    void testCanPass() {
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);

        final Truck truck = new Truck(0, 0, Direction.EAST);
        for (final Terrain destinationTerrain : Terrain.values()) {
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                    assertTrue(truck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.LIGHT) { //if the terrain is light
                    if (currentLightCondition == Light.GREEN) {
                        //check if we can pass green light
                        assertTrue(truck.canPass(destinationTerrain, currentLightCondition));
                    } else { //if the light is red or yellow
                        assertTrue(truck.canPass(destinationTerrain, currentLightCondition));
                    }
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    if (currentLightCondition == Light.RED) {
                        assertFalse(truck.canPass(destinationTerrain, currentLightCondition));
                    } else { //if the light is red or yellow
                        assertTrue(truck.canPass(destinationTerrain, currentLightCondition));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(truck.canPass(destinationTerrain, currentLightCondition));
                }
            }
        }
    }


    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    void testChooseDirectionOnStreetMustReverse() {
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {

                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);

                final Truck truck = new Truck(0, 0, Direction.NORTH);
                //the truck MUST reverse and go SOUTH because there's a street behind it
                assertEquals(Direction.SOUTH, truck.chooseDirection(neighbors),
                        "Truck chooseDirection() failed "
                                + "when reverse was the only valid choice");
            }
        }
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    void testChooseDirectionNearCrosswalk() {
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {

                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.CROSSWALK);
                neighbors.put(Direction.SOUTH, t);

                final Truck truck = new Truck(0, 0, Direction.NORTH);
                //the truck MUST reverse and go SOUTH because there's a street behind it
                assertEquals(Direction.EAST, truck.chooseDirection(neighbors),
                        "Truck chooseDirection() is wrong: "
                                + "Going right was the only valid choice");
            }
        }
    }
}