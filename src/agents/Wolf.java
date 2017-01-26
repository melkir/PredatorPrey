package agents;

public class Wolf extends AbstractAgent {

    public Wolf() {
        // TODO
    }

    public Wolf(double energy) {
        this.setEnergy(energy);
        this.setDirection(Math.random() * 360);
    }

    @Override
    public void step() {
        move();
    }

    @Override
    public AgentType getType() {
        return AgentType.Wolf;
    }

}
