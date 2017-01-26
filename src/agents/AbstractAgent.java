package agents;

public abstract class AbstractAgent {

    private double energy;
    private double direction;

    public abstract void step();

    public abstract AgentType getType();

    public void move() {
        this.energy -= 1;
        if (energy == 1) die();
        // TODO
    }

    public void die() {
        // TODO
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

}
