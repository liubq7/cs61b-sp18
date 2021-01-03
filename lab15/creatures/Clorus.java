package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    /** units of energy to lose when moving. */
    private double moveEnergyLost = 0.03;
    /** units of energy to lose when staying. */
    private double stayEnergyLost = 0.01;


    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** always return the color red = 34, green = 0, blue = 231. */
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

    /** If a Clorus attacks another creature, it should gain that creature’s energy. */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /** Cloruses should lose 0.03 units of energy on a MOVE action. */
    public void move() {
        energy -= moveEnergyLost;
    }


    /** Cloruses should lose 0.01 units of energy on a STAY action. */
    public void stay() {
        energy -= stayEnergyLost;
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Clorus.
     */
    public Clorus replicate() {
        energy = energy / 2;
        double babyEnergy = energy;
        return new Clorus(babyEnergy);
    }

    /** Cloruses take exactly the following actions based on NEIGHBORS:
     *  1. If there are no empty squares, the Clorus will STAY
     *  (even if there are Plips nearby they could attack).
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     *  3. Otherwise, if the Clorus has energy greater than or equal to one,
     *  it will REPLICATE to a random empty square.
     *  4. Otherwise, the Clorus will MOVE to a random empty square.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        } else if (energy >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
    }
}
